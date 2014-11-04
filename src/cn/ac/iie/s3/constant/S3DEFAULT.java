package cn.ac.iie.s3.constant;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;


public class S3DEFAULT {
	/**
	 * 生命周期整点后开始间隔时间--秒
	 */
 	public static final long DATALIFE_EXE_START_TIME= 10*60;
	/**
	 * 生命周期单位（day）
	 */
	public static final long DATALIFE_TIME= 60*60*24*1000;
	
	/**
	 * 副本执行后的SLEEP时间
	 */
	public static final int REPLICATION_SLEEPING_TIME=10*60*1000;
	/**
	 * 节点数据删除操作的SLEEP时间
	 */
	public static final int NODE_DATA_DEL_SLEEPING_TIME=10*60*1000;
	/**
	 * 磁盘分区
	 */
	public static  final long MB = 1024 * 1024;
	/**
	 * 磁盘分区
	 */
	public static  final long MAX_SPACE = 1 * 1024;
	/**
	 * 磁盘分区 (毫秒)
	 */
	public static final long SLEEP_TIME = 30 * 1024;
	
	public static final long PATH_SLEEP_TIME = 24*1024;
	/**
	 * 获取shardName sleep时间--毫秒
	 */
	public static final long GETSHARDNAME_SLEEP_TIME = 1;
	
	/**
	 * 副本创建失败后的等待时间
	 */
	public static final long REPLICATION_FAILED_SLEEPTIME = 2000;
	 /**
	  * 获取innerShardName sleep时间--毫秒
	  */
	 public static final long  GETINNERSHARDNAME_SLEEP_TIME = 1;
	 /**
	  * 节点发送心跳周期（秒）
	  */
	 public static final long  NODE_HERRTBEAT_TIME = 60*1000;
	 /**
	  * 检查节点状态周期
	  */
	 public static final long  NODES_STATUS_LIFETIME = 3600*1000;
	 /**
	  * 副本任务生命周期
	  */
	 public static final long  FUBEN_LIFETIME = 60*10;
	/**
	 * 节点Event_handler
	 */
	 public static final String EVENT_HANDER = "";
	 /**
	  * 节点Msg_id
	  */
	 public static final long MSG_ID = -1;
	 /**
	  * 重启节点时节点等待时间(s)
	  */
	 public static final long SAFE_TIME = 10*1000;
	 /**
	  * file_status 0,1,2,3
	  */
	 public static final int FILE_WRITING = 0;
	 
	 public static final int FILE_CLOSE = 1;
	 /**
	  * 节点启动后，检查异常状态的文件的最大时间：1天
	  */
	 public static final long NODE_FILE_CHECK_MAX_TIME = 60*60*24;
	 
	 public static long SHARD_NUM = 10000000;//默认1千万（达到稳态）
	 
	 public static long SHARD_TIME = 3600*24*7;
	 
	 public static long DOWWN_TIME = 3600*24*10;
	 
	 public static int NODE_ON_LINE = 0;
	 
	 public static int NODE_OFF_LINE = 1;
	 
	 public static int DEV_ON_LINE = 0;
	 
	 public static int DEV_OFF_LINE = 1;
	 
	 public static int REPLICATION_ON_LINE = 1;
	 
	 public static int REPLICATION_OFF_LINE = 0;
	 
	 public static int DEV_BALANCE_NUM = 50;
	 
//	 public static String aa="";
//	 public static String aa="";
//	 public static String aa="";
//	 public static String aa="";
//	 public static String aa="";
//	 public static String aa="";
//	 public static String aa="";
//	
	 public static TreeMap<String, Integer> descByValue(Map<String, Integer> map) {
			ValueComparator vc = new ValueComparator(map);
			return new TreeMap<String, Integer>(vc);
		}

	private static class ValueComparator implements Comparator<String> {

			Map<String, Integer> base;

			public ValueComparator(Map<String, Integer> base) {
				this.base = base;
			}

			public int compare(String a, String b) {
				if (base.get(a) >= base.get(b)) {
					return -1;
				} else {
					return 1;
				}
			}
		}
	 
}
