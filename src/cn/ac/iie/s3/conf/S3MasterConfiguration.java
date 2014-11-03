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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ac.iie.s3.constant.S3Constant;
import cn.ac.iie.s3.util.PropertyUtil;

/**
 * Master配置信息，包括平台和功能服务两个模块
 * 
 * @author egret
 * @since s3-0.0.1
 */
public class S3MasterConfiguration extends S3Configuration {

	private static final long serialVersionUID = 1L;
	private final String SEP = S3Constant._SPLIT_COMMA;
	private final String REPLICATION_SEP = S3Constant._SEMICOLON;
	
	/**
	 * {dataplatform.master.properties}
	 * Platform
	 */
	// 生命周期管理配置项
	public final static String DATALIFE_EXE_INTERVAL_TIME = "maintain.datalife.execute.interval";
	public final static String TABLE_MAITAIN = "maintain.table";
	public final static String MAITAIN_TIME = ".maintain";
	// 节点管理配置项
	public final static String NODE_LIVE_INTERVAL_TIME = "node.heartbeat.interval";
	// 副本管理配置项
	public final static String REPLICATION_TABLE = "replication.table";
	public final static String REPLICATION_NUM = ".replication";
	public final static String REPLICATION_TIME = "replicaton.execute.time";
	public final static String REPLICATION_NOSTEADY_TIMEOUT = "replication.nosteady.timeout";
	public final static String REPLICATION_NOIGNRING_TIMEOUT = "replication.noignoring.timeout";
	public final static String REFORM_MIN_RECORDNUMBER = "reform.min.recordnumber";
	public final static String REFORM_MAX_RECORDNUMBER = "reform.max.recordnumber";
	public final static String REFORM_STEADY_RECORDNUMBER = "reform.steady.recordnumber";
	public final static String REFORM_LUCENE_INTERVAL = "reform.lucene.interval";
	// 检测时间配置
	public final static String NODE_HEARTBEAT_CHECK_INTERVAL = "node.heartbeat.check.interval";
	public final static String FILE_ACCESS_CHECK_INERVAL = "file.access.check.interval";
	// RPC配置项
	public final static String MASTER_PORT = "dataplatform.master.rpc.port";// 20384 default

	/**
	 * {dataplatform.master.properties}
	 * Service 
	 */
	//search
	public final static String SEARCH_MASTER_RPC_PORT = "service.master.rpc.port";
	public final static String SEARCH_MASTER_RPC_HANDLER_COUNT = "service.master.rpc.handler.count";

	public static String SEARCH_MAX_RECORD = "search.max.record";
	/**
	 * 载入默认配置文件
	 */
	public S3MasterConfiguration() {
		super("/dstore.master.properties");
	}

	public S3MasterConfiguration(File file) {
		super(file);
	}

	/**
	 * 获取副本执行时间（范围）
	 * @return 
	 */
	public List<String[]> getReplicationExeTime() {
		List<String[]> exeTime = new ArrayList<String[]>();
		String[] times = getProperty(REPLICATION_TIME).split(REPLICATION_SEP);
		for (String time : times) {
			exeTime.add(time.split(SEP));
		}
		return exeTime;
	}

	/**
	 * 获取需要创建副本的表已经对应的副本数集合.
	 * @return map(key:tableName,value:rep-nums)
	 */
	public Map<String, Integer> getTableAndNumsToRep() {
		String[] tableNames = getProperty(REPLICATION_TABLE).split(SEP);
		Map<String, Integer> nums = new HashMap<String, Integer>();
		for (String tableName : tableNames) {
			int num;
			try {
				num = Integer
						.parseInt(getProperty(tableName + REPLICATION_NUM));
			} catch (NumberFormatException e) {
				num = 1;
			}
			nums.put(tableName.toUpperCase(), num);
		}
		return nums;
	}

	/**
	 * 获取表回收周期.
	 * @return map(key:tableName,value:timeInDays)
	 */
	public Map<String, Integer> getMaitainTime() {
		Map<String, Integer> times = new HashMap<String, Integer>();
		String[] tableNames = getTableMaitain();
		for (String tableName : tableNames) {
			int time = Integer.parseInt(getProperty(tableName + MAITAIN_TIME));
			times.put(tableName.toUpperCase(), time);
		}
		return times;
	}

	/**
	 * 获取带回收的表集合.
	 * @return string[table1,table2,...]
	 */
	public String[] getTableMaitain() {
		return getProperty(TABLE_MAITAIN).split(SEP);
	}

	/**
	 * 获取Master的RPC端口，默认20384
	 * @return
	 */
	public int getMasterPort() {
		return getInt(S3MasterConfiguration.MASTER_PORT, 20384);
	}

	public int getNodePort() {
		S3NodeConfiguration nodeConf = new S3NodeConfiguration();
		return nodeConf.getNodeRpcPort();
	}

	public int getNodeLiveTimeInterval() {
		return getTime(NODE_LIVE_INTERVAL_TIME, 600);
	}

	/**
	 * 生命周期执行时间.
	 * @return
	 */
	public Double getDataLifeExeIntervalTime() {
		return Double.parseDouble(getProperty(DATALIFE_EXE_INTERVAL_TIME));
	}

	public String getMasterHostName() {
		BufferedReader reader = null;
		String master = null;
		try {
			// TODO:设置成常量
			File file = new File(PropertyUtil.class.getResource("/master")
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

	// =======================V0.2.5===================================
	public int getNosteadyTimeout() {
		return getInt(S3MasterConfiguration.REPLICATION_NOSTEADY_TIMEOUT, 7);
	}

	public int getNoignoringTimeout() {
		return getInt(S3MasterConfiguration.REPLICATION_NOIGNRING_TIMEOUT, 7);
	}

	public int getMinRecordNum() {
		return getInt(S3MasterConfiguration.REFORM_MIN_RECORDNUMBER, 0);
	}

	public int getMaxRecordNum() {
		return getInt(S3MasterConfiguration.REFORM_MAX_RECORDNUMBER, 1000000);
	}

	public int getSteadyRecordNum() {
		return getInt(S3MasterConfiguration.REFORM_STEADY_RECORDNUMBER, 250000);
	}

	public int getLuceneInterval() {
		return getInt(S3MasterConfiguration.REFORM_LUCENE_INTERVAL, 1);
	}

	public int getHeartbeatCheckInterval() {
		return getInt(S3MasterConfiguration.NODE_HEARTBEAT_CHECK_INTERVAL,
				5 * 60 * 1000);
	}

	public int getFileAccessInterval() {
		return getInt(S3MasterConfiguration.FILE_ACCESS_CHECK_INERVAL, 3);
	}

	public int getSearchMasterRpcPort() {
		return getInt(SEARCH_MASTER_RPC_PORT, 50101);
	}

	public int getSearchMaxRecord() {
		return getInt(SEARCH_MAX_RECORD,100);
	}

	public int getSearchMasterHandlerCount() {
		return getInt(SEARCH_MASTER_RPC_HANDLER_COUNT, 2000);
	}
	// =======================V0.2.5===================================

	public static void main(String[] args) {
		S3MasterConfiguration mas = new S3MasterConfiguration();
		System.out.println(mas.getMasterHostName() + mas.getMaitainTime());
	}
}
