package cn.ac.iie.s3.master;

import org.apache.hadoop.ipc.VersionedProtocol;

import cn.ac.iie.s3.lib.rpc.RPCResult;

/**
 * Master对外提供的RPC方法的接口
 * 对应的实现参见{@link MasterRPCHandler}
 * 
 * @author egret
 * 
 */
public interface IMasterRPCHandler extends VersionedProtocol {
	public static final long versionID = 1l;

	public RPCResult getThings(String arg1);

	public RPCResult getThings(String arg1, String arg2);

	public RPCResult getThingsResult(String arg1);

}