package cn.ac.iie.s3.constant;

public class S3TableConstant {

	public static final String TABLE_TYPE="tableType";
	public static String BLOCKFIELD="binary";
	
	public class TableType{
		public static final String NORMAL_TYPE = "normal";
		public static final String KV_TYPE = "kv";
		public static final String MIX_TYPE = "mix";
	}
	
	public static class KVTableColumn {
		public static String CREAT_TIME = "create_time";
		public static String FILE_SIZE = "file_size";
	}
}
