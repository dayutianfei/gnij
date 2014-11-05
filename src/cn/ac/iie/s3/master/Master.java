package cn.ac.iie.s3.master;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import cn.ac.iie.s3.conf.S3MasterConfiguration;
import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;
import cn.ac.iie.s3.master.handler.rpc.MasterRPCServer;

/**
 * Master主入口
 * 该类主要用于启动相关的服务线程
 * 
 * @author dayutianfei
 * @since s3-0.0.1
 */
public class Master {
	protected final static Logger LOG = LoggerUtil
			.getInstance(LogType.PLATFORM.toString());
	
	private int SAFE_TIME_IN_SENCONDS = 300; 
	private static boolean IN_SAFE_MODE = true;
	private S3MasterConfiguration conf = null;	
	private String masterName = null;
	private MasterRPCServer masterRPCserver= null;
	
	public Master() throws FileNotFoundException, IOException{
		this.conf = new S3MasterConfiguration();
		this.SAFE_TIME_IN_SENCONDS = this.conf.getMasterSafeTime();
		this.masterName = this.conf.getMasterHostName();
		this.masterRPCserver = new MasterRPCServer();
	}
	
	public synchronized void start() throws UnknownHostException, InterruptedException{
		LOG.info("the master's name is : " + masterName);
		int serverStartLastingTimeInMS = 0;
		this.masterRPCserver.start();
		LOG.info("the master rpc server is started successfully!");
		LOG.info("the master server will enter safemod on "+System.currentTimeMillis());
		while(IN_SAFE_MODE){
			if(serverStartLastingTimeInMS > SAFE_TIME_IN_SENCONDS*1000){
				IN_SAFE_MODE = false;
				break;
			}
			Thread.sleep(100);
			serverStartLastingTimeInMS +=100;
		}
		LOG.info("the master server will leave safemod which last "+serverStartLastingTimeInMS + " ms");
	}
	
	public void close(){
		this.masterRPCserver.shutdown();
	}
}
