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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.UnknownHostException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;
import org.apache.log4j.Logger;

import cn.ac.iie.s3.conf.S3MasterConfiguration;
import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;
import cn.ac.iie.s3.master.IMasterRPCHandler;

import com.google.common.base.Preconditions;

/**
 * MasterRPC服务的主类
 * 
 * @author egret
 * @since s3-0.0.1
 */
public class MasterRPCServer {

	protected final static Logger LOG = LoggerUtil.getInstance(LogType.S3
			.toString());

	private String _nodeName;
	private int _rpcServerport;
	private int _rpcHandlerCount;
	private Server _rpcServer;

	public MasterRPCServer() throws FileNotFoundException, IOException {
		this(new S3MasterConfiguration());
	}

	public MasterRPCServer(S3MasterConfiguration configuration)
			throws FileNotFoundException, UnknownHostException {
		this._rpcServerport = configuration.getMasterRpcPort();
		this._rpcHandlerCount = configuration.getMasterHandlerCount();
		this._nodeName = configuration.getMasterHostName() +":"+ _rpcServerport;
		LOG.info("The master RPC sever will start at "+ this._nodeName + " on port " + this._rpcServerport 
				+ " with handler number " + this._rpcHandlerCount);
	}

	public synchronized void start() {
		Preconditions.checkState(!isShutdown(), "master was already shut-down");
		becomePrimaryOrSecondaryMaster();
	}

	private synchronized void becomePrimaryOrSecondaryMaster() {
		if (isShutdown()) {
			LOG.error("Master is shutdown already.");
			return;
		}
		try {
			IMasterRPCHandler _handler = new MasterRPCHandler();
			LOG.info("starting rpc server with server class = "
					+ _handler.getClass().getCanonicalName());
			this._rpcServer = startRPCServer(this._nodeName, this._rpcServerport,
					_handler, _rpcHandlerCount);
		} catch (Exception e) {
			LOG.error("error", e);
		}
	}

	private synchronized boolean isShutdown() {
		return false;
	}

	public String getNodeName() {
		return _nodeName;
	}

	public synchronized void shutdown() {
		this._rpcServer.stop();
	}

	@SuppressWarnings("deprecation")
	private static Server startRPCServer(String hostName, final int startPort,
			IMasterRPCHandler handler, int handlerCount) {
		int serverPort = startPort;
		int tryCount = 10000;
		int triedTimes = 0;
		Server _rpcServer = null;
		while (_rpcServer == null) {
			try {
				LOG.info("start rpc server,host:" + hostName + ",port:"
						+ serverPort);
				_rpcServer = RPC.getServer(handler, "0.0.0.0", serverPort,
						handlerCount, false, new Configuration());
				LOG.info(handler.getClass().getSimpleName()
						+ " server started on : " + hostName + ":" + serverPort);
				break;
			} catch (final BindException e) {
				if (triedTimes < tryCount) {
					triedTimes++;
				} else {
					throw new RuntimeException("tried " + tryCount
							+ " ports and no one is free...");
				}
			} catch (final IOException e) {
				throw new RuntimeException("unable to create rpc server", e);
			}
		}
		_rpcServer.start();
		return _rpcServer;
	}
}
