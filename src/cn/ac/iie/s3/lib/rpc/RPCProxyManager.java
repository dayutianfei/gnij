/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ac.iie.s3.lib.rpc;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.VersionedProtocol;
import org.apache.log4j.Logger;

import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * 本类为RPC服务提供访问代理的管理功能
 * node2ProxyMap为缓存RPC具体执行的对象
 * 
 * @author egret
 * 
 */
public class RPCProxyManager implements IRPCProxyManager {

	private final static Logger LOG = LoggerUtil.getInstance(LogType.S3.toString());
	private final Class<? extends VersionedProtocol> _handlerClass;
	private final Configuration _conf;
	/**
	 * 缓存节点的rpc代理
	 */
	private final Map<String, VersionedProtocol> node2ProxyMap = new ConcurrentHashMap<String, VersionedProtocol>();
	private int _successiveProxyFailuresBeforeReestablishing = 5;
	private final Multiset<String> _failedNodeInteractions = HashMultiset
			.create();

	public RPCProxyManager(Class<? extends VersionedProtocol> serverClass,
			Configuration conf) {
		_handlerClass = serverClass;
		_conf = conf;
	}

	/**
	 * @return how many successive proxy invocation errors must happen before
	 *         the proxy is re-established.
	 */
	public int getSuccessiveProxyFailuresBeforeReestablishing() {
		return _successiveProxyFailuresBeforeReestablishing;
	}

	public void setSuccessiveProxyFailuresBeforeReestablishing(
			int successiveProxyFailuresBeforeReestablishing) {
		_successiveProxyFailuresBeforeReestablishing = successiveProxyFailuresBeforeReestablishing;
	}

	public VersionedProtocol createProxy(final String nodeNameWithPort)
			throws IOException {
		LOG.debug("creating proxy for master: " + nodeNameWithPort);
		String[] hostName_port = nodeNameWithPort.split(":");
		if (hostName_port.length != 2) {
			throw new RuntimeException(
					"invalid node name format '"
							+ nodeNameWithPort
							+ "' (It should be a host name with a port number devided by a ':')");
		}
		final String hostName = hostName_port[0];
		final String port = hostName_port[1];
		final InetSocketAddress inetSocketAddress = new InetSocketAddress(
				hostName, Integer.parseInt(port));
		VersionedProtocol proxy = RPC.getProxy(_handlerClass, 0L,
				inetSocketAddress, _conf);
		LOG.debug(String.format("Created a proxy %s for %s:%s %s",
				Proxy.getInvocationHandler(proxy), hostName, port,
				inetSocketAddress));
		node2ProxyMap.put(nodeNameWithPort, proxy);
		return proxy;
	}

	public VersionedProtocol getProxy(String nodeName,
			boolean establishIfNoExists) {
		VersionedProtocol versionedProtocol = node2ProxyMap.get(nodeName);
		if (versionedProtocol == null && establishIfNoExists) {
			synchronized (nodeName.intern()) {
				if (!node2ProxyMap.containsKey(nodeName)) {
					try {
						versionedProtocol = createProxy(nodeName);
						node2ProxyMap.put(nodeName, versionedProtocol);
					} catch (Exception e) {
						LOG.warn("Could not create proxy for master '"
								+ nodeName + "' - "
								+ e.getClass().getSimpleName() + ": "
								+ e.getMessage());
					}
				}
			}
		}
		return versionedProtocol;
	}

	@SuppressWarnings("unchecked")
	public void reportNodeCommunicationFailure(String nodeName, Throwable t) {
		// TODO jz: not sure if there are cases a proxy is getting invalid and
		// re-establishing it would fix the communication. If so, we should
		// check
		// the for the exception which occurs in such cases and re-establish the
		// proxy.
		_failedNodeInteractions.add(nodeName);
		int failureCount = _failedNodeInteractions.count(nodeName);
		if (failureCount >= _successiveProxyFailuresBeforeReestablishing
				|| exceptionContains(t, ConnectException.class,
						EOFException.class)) {
			dropNodeProxy(nodeName, failureCount);
		}
	}

	@SuppressWarnings("unchecked")
	private boolean exceptionContains(Throwable t,
			Class<? extends Throwable>... exceptionClasses) {
		while (t != null) {
			for (Class<? extends Throwable> exceptionClass : exceptionClasses) {
				if (t.getClass().equals(exceptionClass)) {
					return true;
				}
			}
			t = t.getCause();
		}
		return false;
	}

	private void dropNodeProxy(String nodeName, int failureCount) {
		synchronized (nodeName.intern()) {
			if (node2ProxyMap.containsKey(nodeName)) {
				LOG.warn("removing proxy for node '" + nodeName + "' after "
						+ failureCount + " proxy-invocation errors");
				_failedNodeInteractions.remove(nodeName, Integer.MAX_VALUE);
				VersionedProtocol proxy = node2ProxyMap.remove(nodeName);
				RPC.stopProxy(proxy);
			}
		}
	}

	public void reportNodeCommunicationSuccess(String node) {
		_failedNodeInteractions.remove(node, Integer.MAX_VALUE);
	}

	public void shutdown() {
		Collection<VersionedProtocol> proxies = node2ProxyMap.values();
		for (VersionedProtocol search : proxies) {
			RPC.stopProxy(search);
		}
	}

	public Collection<VersionedProtocol> getProxys() {
		return node2ProxyMap.values();
	}

}
