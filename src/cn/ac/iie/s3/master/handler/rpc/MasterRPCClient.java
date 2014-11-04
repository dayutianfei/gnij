/**
 * Copyright 2008 the original author or authors.
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
package cn.ac.iie.s3.master.handler.rpc;

import java.lang.reflect.Method;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

import cn.ac.iie.s3.lib.rpc.RPCProxyManager;
import cn.ac.iie.s3.lib.rpc.RPCResult;
import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;
import cn.ac.iie.s3.master.IMasterRPCHandler;

/**
 * MasterRPC的客户端程序，使用者需要调用该方法的构造方法，传入节点IP或名称（含端口）
 * 该方法需要将暴露给调用者的方法进行描述（方法名称、参数等）
 * 
 * @author egret
 * 
 */
public class MasterRPCClient {

	protected final static Logger LOG = LoggerUtil.getInstance(LogType.S3
			.toString());
	private RPCProxyManager nodeProxyManager;

	/**
	 * 对外提供的方法，提供校验的功能，如有异常则抛出
	 */
	// 方法列表
	private static Method GetThings_METHOD = null;
	private static Method GetThingsResult_METHOD = null;
	static {
		try {
			GetThings_METHOD = IMasterRPCHandler.class.getMethod("getThings",
					new Class[] { String.class, String.class });
			GetThingsResult_METHOD = IMasterRPCHandler.class.getMethod(
					"getThingsResult", new Class[] { String.class });
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Could not find methods in IRPCHandler!");
		}
	}

	public MasterRPCClient(String node, int port) throws Exception {
		nodeProxyManager = new RPCProxyManager(IMasterRPCHandler.class,
				new Configuration());
		// 为每个RPCClient创建代理
		boolean createProxyOk = false;
		nodeProxyManager.createProxy(node + ":" + port);
		createProxyOk = true;
		if (!createProxyOk) {
			LOG.info("Create node proxy false");
			throw new Exception("Create node proxy false");
		}
	}

	/**
	 * 执行master中的方法.
	 * 
	 * @param method
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Object broadtoMaster(Method method, Object... args) throws Exception {

		Object proxy = null;
		for (Object p : nodeProxyManager.getProxys()) {
			if (p != null) {
				proxy = p;
				break;
			}
		}
		if (proxy == null) {
			throw new Exception("no proxy is availiable");
		}
		if (method == null || args == null) {
			throw new IllegalArgumentException("Null method or args!");
		}

		// method types
		Class<?>[] types = method.getParameterTypes();
		if (args.length != types.length) {
			throw new IllegalArgumentException("Wrong number of args: found "
					+ args.length + ", expected " + types.length + "!");
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				Class<?> from = args[i].getClass();
				Class<?> to = types[i];
				if (!to.isAssignableFrom(from)
						&& !(from.isPrimitive() || to.isPrimitive())) {
					throw new IllegalArgumentException(
							"Incorrect argument type for param " + i
									+ ": expected " + types[i] + "!");
				}
			}
		}
		return method.invoke(proxy, args);
	}

	public void close() {
		nodeProxyManager.shutdown();
	}

	public RPCResult getThings(String arg1, String arg2) throws Exception {
		RPCResult re = (RPCResult) broadtoMaster(GetThings_METHOD, arg1, arg2);
		return re;
	}

	public String getThingsResult(String nodeName) throws Exception {
		RPCResult re = (RPCResult) broadtoMaster(GetThingsResult_METHOD,
				nodeName);
		return re.getMessage();
	}
}
