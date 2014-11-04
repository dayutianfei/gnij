package cn.ac.iie.s3.master.handler.rpc.test;

import cn.ac.iie.s3.lib.rpc.RPCResult;
import cn.ac.iie.s3.master.handler.rpc.MasterRPCClient;


public class MasterRPCClientTest {
	public static void main(String[] args) {
		try {
			MasterRPCClient client = new MasterRPCClient("localhost",20384);
			RPCResult res = client.getThings("test","testa");
			System.out.println(res.getMessage());
//			client.getThingsResult("test");
			client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
