package cn.ac.iie.s3.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * 本类用于日志输出的定义
 * 
 * @author egret
 * @since s3-0.0.1
 */
public class LoggerUtil {

	/**
	 * 对Logger中getLogger方法的封装
	 */
	public static Logger getInstance(Class<?> className) {
		return Logger.getLogger(className);
	}

	/**
	 * 用于指定某个日志文件的输出，需要配合LogType使用
	 * 
	 * @param name
	 * @return
	 */
	public static Logger getInstance(String name) {
		return Logger.getLogger(name);
	}

	/**
	 * 打印Exeption信息到LOG中，LOG级别为ERROR
	 * 
	 * @param LOG
	 * @param e
	 */
	public static void printExceptionMsg(Logger LOG, Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		try {
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			LOG.error(sw.toString());
		} catch (Exception einside) {
			LOG.error("The LoggerUtil error, msg is : " + einside.getMessage());
			StackTraceElement[] traces = einside.getStackTrace();
			for (StackTraceElement stackTraceElement : traces) {
				LOG.error(stackTraceElement.toString());
			}
		} finally {
			try {
				if (null != pw && null != sw) {
					pw.close();
					sw.close();
				}
			} catch (IOException e1) {
				LOG.error("The LoggerUtil error, msg is : " + e1.getMessage());
				StackTraceElement[] traces = e1.getStackTrace();
				for (StackTraceElement stackTraceElement : traces) {
					LOG.error(stackTraceElement.toString());
				}
			}
		}
	}
}
