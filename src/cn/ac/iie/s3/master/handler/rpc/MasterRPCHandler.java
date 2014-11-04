package cn.ac.iie.s3.master.handler.rpc;

import java.io.IOException;
import org.apache.hadoop.ipc.ProtocolSignature;
import org.apache.log4j.Logger;

import cn.ac.iie.s3.lib.rpc.RPCResult;
import cn.ac.iie.s3.log.LogType;
import cn.ac.iie.s3.log.LoggerUtil;
import cn.ac.iie.s3.master.IMasterRPCHandler;

/**
 * Master对外服务（接口）的实现类
 * 
 * @author egret
 *
 */
public class MasterRPCHandler implements IMasterRPCHandler {
	private final static Logger LOG = LoggerUtil.getInstance(LogType.S3.toString());
	
	public MasterRPCHandler() {}
	
	public ProtocolSignature getProtocolSignature(String arg0, long arg1,
			int arg2) throws IOException {
		return null;
	}

	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		return 0;
	}

	public RPCResult getThings(String arg1) {
		LOG.info("invoke the getThings method");
		RPCResult ss = new RPCResult();
		LOG.info("thing is "+arg1);
		return ss;
	}

	public RPCResult getThings(String thingName1, String thingName2) {
		LOG.info("invoke the getThings method");
		RPCResult ss = new RPCResult();
		ss.setMessage("hello "+thingName1+" "+thingName2);
		return ss;
	}

	public RPCResult getThingsResult(String thing) {
		// TODO Auto-generated method stub
		RPCResult ss = new RPCResult();
		ss.setMessage(thing);
		return ss;
	}
}
