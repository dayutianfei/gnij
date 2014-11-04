package cn.ac.iie.s3.master.handler.rpc.test;

import cn.ac.iie.s3.master.handler.rpc.MasterRPCServer;


public class MasterRPCServerTest {
	public static void main(String[] args) {
		try{
			MasterRPCServer ss = new MasterRPCServer();
			ss.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
