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

import java.io.File;
import java.util.Properties;

/**
 *  元数据相关的配置文件
 *  
 *  @author egret
 *  @since s3-0.0.1
 */
public class S3MetaConfiguration extends S3Configuration {

	private static final long serialVersionUID = 1L;
	/**
	 * {dataplatform.meta.properties}
	 */
	public static final String URIS = "metastore.uris";
	public static final String METACLIENTTIMEOUT = "metastore.client.socket.timeout";
	public static final String ZKCONNECTSTRING = "zk.notify.connectstring";

	public S3MetaConfiguration() {
		super("/dstore.meta.properties");
	}
	public S3MetaConfiguration(String metaurl,int time) {
		setProperty(METACLIENTTIMEOUT,time);
		setProperty(URIS,metaurl);
	}
	public S3MetaConfiguration(File file) {
		super(file);
	}

	public S3MetaConfiguration(Properties properties) {
		super(properties, "n/a");
		
	}

	public String getURIS() {
		return getProperty(URIS,null);
	}
	
	public void setURIS(String metaURI) {
		setProperty(URIS,metaURI);
	}
	
	public int getMetaClientTimeout() {
		return getInt(METACLIENTTIMEOUT,3600);
	}
	
	public void setMetaClientTimeout(int time) {
		setProperty(METACLIENTTIMEOUT,time);
	}
	
	public String getZKConnectString() {
		return getProperty(ZKCONNECTSTRING, "127.0.0.1:22182");
	}
	
	public void setZKConnectString(String zkconnection) {
		setProperty(ZKCONNECTSTRING, zkconnection);
	}
}
