package cn.ac.iie.s3.constant;

import java.io.File;
import java.nio.charset.Charset;

public class S3SearchConstant {
	public static final String MDSS_HOME = "MDSS_HOME";
	public static final int KATTA_DEPLOY_FETCH_INTERVAL = 200;
	public static final String KATTA_SHARD_DELIMETER = "#";
	public static final String KATTA_MULL = "MULL";
	public static final String KATTA_NULL = "NULL";
	public static final String KATTA_ATATALL_NAME = "@@all";
	public static final String KATTA_MATCHALL = KATTA_ATATALL_NAME + ":0";

	public static final String KATTA_SEARCH_ALL = "*:*";//2014-03-13添加(对于没有where条的sql语句，luceneQuery值为“*:*”)
	
	public static final String KATTA_STAR = "*";
	public static final String KATTA_STATION = "station";
	public static final String KATTA_COLON = ":";
	public static final String KATTA_INDEX = "index";
	public static final String KATTA_ENABLE = "enable";
	public static final String KATTA_TMP_SHARD_EXTENSION = "_tmp";
	public static final String KATTA_TMP_MERGE_SHARD_EXTENSION = "_merge";
	
	public static final int KATTA_MAX_TRANSMIT_UNIT = 2000000;
	public static final int KATTA_MAX_ORDER_TOPK = 1000;
	public static final int KATTA_MAX_GROUP_TOPK = 2000000;
	public static final int NODE_PORT = 20000;
	public static final String KATTA_AND = "AND";
	public static final String KATTA_OR = "OR";
	public static final String KATTA_NOT = "NOT";
	
	public static final String KATTA_TO = "TO";
	
	public static final String KATTA_LP = "(";
	public static final String KATTA_RP = ")";
	public static final String KATTA_SPLIT_COMMA = ",";
	public static final String KATTA_SPLIT_HYPHEN = "-";
	public static final String KATTA_FORWARD_SLASH = "/";
	public static final String KATTA_SPLIT_UNDERLINE = "_";
	public static final String KATTA_REGEX_EXP = "/%s/";
	public static final String KATTA_STRING_EXP = "\"%s\"";
	public static final String digestFolder = "digest";
	public static final String shardFolder = "shard";
	public static final String storeFileFolder = "storeFile";
	public static final String recycleFolder = "recycleFolder";
	
	public static final String resultSepatator="\t";
	public static final int MAX_LEN=32768;
	public static final String home_path;
	static{
		home_path = new File(System.getProperty("user.dir")).getParent();
	}
	
	
	public static final Charset CHARSET_ISO_8859_1 = Charset.forName("iso-8859-1");
	public static final Charset CHARSET_UTF_8 = Charset.forName("utf-8");
	public static final Charset CHARSET_GBK = Charset.forName("gbk");
}
