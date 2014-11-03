package cn.ac.iie.s3.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class ShellCommand {
	private static Logger LOG = Logger.getLogger(ShellCommand.class);

	/**
	 * 执行shell命令
	 * 
	 * @param shellCommand
	 * @return 执行成功返回true
	 * @throws IOException
	 */
	public static boolean executShell(String shellCommand) throws IOException {

		int num = -10000000;

		LOG.info("Begin execute shell command:" + shellCommand);
		String[] cmd = { "/bin/sh", "-c", shellCommand };
		Process rt = Runtime.getRuntime().exec(cmd);
		LOG.info("Shell command process.....");

		try {
			num = rt.waitFor();
			LOG.info("End shell command process！");
		} catch (InterruptedException e) {
			LOG.error("Execute " + shellCommand + " error！", e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				rt.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line != null)
				LOG.info("The result of shell command process:" + line);
		}
		br.close();

		if (num == 0) {
			LOG.info("Shell command process successful!");
			return true;
		} else {
			LOG.error("Shell command process faild!");
			return false;
		}

	}
}
