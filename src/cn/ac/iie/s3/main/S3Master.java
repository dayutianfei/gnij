package cn.ac.iie.s3.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;
import cn.ac.iie.s3.master.Master;

public class S3Master {

	protected final static Logger LOG = LoggerUtil.getInstance(LogType.S3
			.toString());

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		final Master master;
		LOG.info("init master ...");
		master = new Master();
		LOG.info("init master success...");
		master.start();
		LOG.info("start master success...");
		synchronized (master) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					synchronized (master) {
						master.close();
						master.notifyAll();
					}
				}
			});
			master.wait();
		}
	}
}
