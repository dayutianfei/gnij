package cn.ac.iie.s3.conf;

/**
 * 本类用于读取消息同步服务相关的配置文件
 * 
 * @author egret
 * @since s3-0.0.1
 */
public class S3SyncConfiguration extends S3Configuration{

	private static final long serialVersionUID = 1L;
	
	public static final String METASYNC = "sync.service.address";
	
	public S3SyncConfiguration(){
		super("/dstore.sync.properties");
	}
	
	public String getSyncServiceAddress(){
		return getProperty(METASYNC);
	}
}
