package cn.ac.iie.s3.conf;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ac.iie.s3.util.PropertyUtil;

/**
 * 本类提供S3服务中的SALVE节点配置文件参数读取，包括平台和功能服务
 * 
 * @author egret
 * @since s3-0.0.1
 */
public class S3NodeConfiguration extends S3Configuration {
	private static final long serialVersionUID = 1L;
	public final String SEP = ",";

	/**
	 * {dataplatform.node.properties}
	 * Platform
	 */
	// 磁盘挂载路径信息：磁盘数量和对应磁盘挂载路径配置文件前缀
	public static final String DATA_FOLDER_NUM = "node.data.folder.num";
	public static final String DATA_FOLDER = "node.data.folder.";
	
	public static final String SHARD_SEGMENT_NUM = "shard.segment.num";

	// RPC配置
	public static final String NODE_RPC_PORT = "dataplatform.node.rpc.port";

	//================================V0.2.5=================================
	//[OfflineDeviceCheck] the interval of execute OfflineDeviceCheck(hour)
	public static final String OFFLINE_DEVICE_CHECK = "offlinedevicecheck.execute.intercal";
	//[HeartBeat] the interval of send heart beat(s)
	public static final String HEARTBEAT_SEND_INTERVAL = "node.heartbeat.send.interval";
	//[FileAccessCheck]the interval of execute FileAccessCheck(hour)
	public static final String FILE_ACCESS_CHECK_INTERVAL = "fileaccesscheck.execute.intercal";
	
	/**
	 * {dataplatform.node.properties}
	 * Service
	 */
	// search
		public final static String SERVICE_NODE_RPC_PORT = "service.node.rpc.port";
		public final static String SEARCH_NODE_RPC_HANDLER_COUNT = "service.node.rpc.handler.count";
//		private static final String SERVER_CLASS = "node.server.class";
		private static final String MAXSHARD_NUM = "node.maxshard.num";
		private static final String MAXRECORD_NUM = "node.default.maxRecords";

		// stat
		public final static String STAT_DISK_PATH = "stat.disk.path";

		// load
		public final static String NODE_UPLOAD_PORT = "node.load.http.port";
		public final static String MAX_SHARD_SIZE = "table.kv.max.record.byte";
		public final static String MAX_SHARD_RECORD_NUM = "table.normal.max.record.num";
		public final static String MAX_WAIT_CLOSE_TIME = "shard.close.maxtime";
		public final static String CHCHE_TIME = "shard.commit.check.interval";
		public final static String SHARD_STEADY_NUM = "shard.steady.record.num";
		
	public S3NodeConfiguration() {
		super("/dstore.node.properties");
	}

	public S3NodeConfiguration(File file) {
		super(file);
	}

	/**
	 * 获取磁盘上的数据存储路径集合.
	 * 
	 * @return
	 */
	public List<String> getDataPath() {
		List<String> paths = new ArrayList<String>();
		String[] pathNum = getProperty(DATA_FOLDER_NUM).split(",");
		for (int i = 0; i < pathNum.length; i++) {
			paths.add(getProperty(DATA_FOLDER + pathNum[i]));
		}
		return paths;
	}

	/**
	 * 获取磁盘分区编号-磁盘分区路径对应关系集合.
	 * 
	 * @return
	 */
	public Map<Integer, String> getDiskNumToPath() {
		Map<Integer, String> paths = new HashMap<Integer, String>();
		String[] pathNum = getProperty(DATA_FOLDER_NUM).split(",");
		List<Integer> pathOrdered = new ArrayList<Integer>();
		for(String s: pathNum){
			pathOrdered.add(Integer.parseInt(s));
		}
		Collections.sort(pathOrdered);
		for (int i = 0; i < pathOrdered.size(); i++) {
			paths.put(pathOrdered.get(i), getProperty(DATA_FOLDER
					+ pathOrdered.get(i)));
		}
		return paths;

	}
	
	public int getShardSegmentNum() {
		return getInt(SHARD_SEGMENT_NUM, 10);
	}

	public int getNodeRpcPort() {
		return getInt(NODE_RPC_PORT, 20383);
	}

//	public int getMasterRpcPort() {
//		S3MasterConfiguration masterConf = new S3MasterConfiguration();
//		return masterConf.getMasterPort();
//	}
	//================================V0.2.5=================================
	public int getOfflineDeviceCheck() {
		return getInt(OFFLINE_DEVICE_CHECK, 24);
	}
	
	public int getHeartbeatSendInterval() {
		return getInt(HEARTBEAT_SEND_INTERVAL, 60);
	}
	
	public int getFileAccessCheckIntercal() {
		return getInt(FILE_ACCESS_CHECK_INTERVAL, 24);
	}
	
	public String getMaster() {
        BufferedReader reader = null;
        String master = null;
        try {
        	//TODO:设置成常量
        	File file = new File(PropertyUtil.class.getResource("/master").toURI());
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
        if(null == master){
        	master = "localhost";
        }
		return master;
	}
	
	public int getServiceNodeRpcPort() {
		return getInt(SERVICE_NODE_RPC_PORT, 60002);
	}

	public int getSearchNodeHandlerCount() {
		return getInt(SEARCH_NODE_RPC_HANDLER_COUNT, 12);
	}

	public int getMaxShardNum() {
		return getInt(MAXSHARD_NUM, 2);
	}

	public int getMaxRecordNum() {
		return getInt(MAXRECORD_NUM, 1000);
	}

	public String getStatDiskPath() {
		return getProperty(STAT_DISK_PATH);
	}

	// load
	public int getNodeUploadPort() {
		return getInt(NODE_UPLOAD_PORT, 50012);
	}

	public long getMaxShardSize() {
		return getLong(MAX_SHARD_SIZE, 1024 * 1024 * 10 * 1l);
	}

	public int getMaxShardRecordNum() {
		return getInt(MAX_SHARD_RECORD_NUM, 10000);// 1000000
	}

	public long getMaxWaitCloseTime() {
		return getLong(MAX_WAIT_CLOSE_TIME, 60 * 60);
	}

	public long getCacheTime() {
		return getLong(CHCHE_TIME, 60);
	}
	
	public int getShardSteadyNum() {
		return getInt(SHARD_STEADY_NUM, 250000);
	}
}
