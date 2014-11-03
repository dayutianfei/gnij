package cn.ac.iie.s3.master;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;
import cn.ac.iie.s3.util.NetUtil;

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
	
	private String masterName = null;
	
	public synchronized void start() throws UnknownHostException{
		masterName = NetUtil.getLocalIpAsDecimal();
		LOG.debug("the master name is : " + masterName);
	}
}
