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
package cn.ac.iie.s3.conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import cn.ac.iie.s3.constant.S3CONSTANT;
import cn.ac.iie.s3.util.PropertyUtil;

/**
 * Master配置信息，包括平台和功能服务两个模块
 * 
 * @author egret
 * @since s3-0.0.1
 */
@SuppressWarnings("unused")
public class S3MasterConfiguration extends S3Configuration {

	private static final long serialVersionUID = 1L;
	private final String SEP = S3CONSTANT._SPLIT_COMMA;
	private final String REPLICATION_SEP = S3CONSTANT._SEMICOLON;
	private final String MASTER = "/master"; //master文件，记录master的主机名或IP
	private final static String MASTER_CONF = "/s3.master.properties"; //master配置文件

	/**
	 * {s3.master.properties}
	 */
	// RPC配置项
	public final static String MASTER_RPC_PORT = "master.rpc.port"; // 20384 default
	public final static String MASTER_RPC_HANDLER_COUNT = "master.rpc.handler.count"; // 2000 default
	// COMMON配置项
	public final static String MASTER_SAFE_TIME = "master.safetime";

	/**
	 * 载入默认配置文件
	 */
	public S3MasterConfiguration() {
		super(MASTER_CONF);
	}

	public S3MasterConfiguration(File file) {
		super(file);
	}

	/**
	 * 从配置文件master中读取master的主机名（或IP）
	 * 
	 * @return
	 */
	public String getMasterHostName() {
		BufferedReader reader = null;
		String master = null;
		try {
			File file = new File(PropertyUtil.class.getResource(MASTER)
					.toURI());
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				master = tempString;
				break;
			}
			reader.close();
		} catch (IOException e) {
		} catch (URISyntaxException e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		// master不能为空
		if (null == master) {
			master = "localhost";
		}
		return master;
	}

	public int getMasterRpcPort() {
		return getInt(MASTER_RPC_PORT, 20384);
	}

	public int getMasterHandlerCount() {
		return getInt(MASTER_RPC_HANDLER_COUNT, 2000);
	}
	
	public int getMasterSafeTime() {
		return getInt(MASTER_SAFE_TIME, 300);
	}

	public static void main(String[] args) {
		S3MasterConfiguration mas = new S3MasterConfiguration();
		System.out.println(mas.getMasterHostName());
	}
}
