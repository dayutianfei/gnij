package cn.ac.iie.s3.log;

/**
 * 本类用于定义日志输出的文件
 * 
 * @author egret
 * @since s3-0.0.1
 */
public enum LogType {
	SERVICE, SERVICE_LOAD, SERVICE_SEARCH, SERVICE_MASTER, SERVICE_SLAVE, 
	CLIENT, 
	PLATFORM, PLATFORM_MASTER, PLATFORM_SLAVE, 
	S3
}
