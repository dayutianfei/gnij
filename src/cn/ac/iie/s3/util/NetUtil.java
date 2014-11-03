package cn.ac.iie.s3.util;

import java.net.InetAddress;
import java.net.UnknownHostException;


import cn.ac.iie.s3.constant.S3SearchConstant;


public class NetUtil {

	/**
	 * 返回的IP形式如："AC10007F" ----(172.16.0.127)
	 * @return "AC10007F"
	 * @throws UnknownHostException
	 */
	public static String getLocalIp() throws UnknownHostException {
		String[] ipStr;
		ipStr = InetAddress.getLocalHost().getHostAddress().split("\\.");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ipStr.length; i++) {
			ipStr[i] = ipStr[i].trim();
			short s = Short.parseShort(ipStr[i]);
			sb.append(String.format("%02X", s));
		}
		return sb.toString();
	}
	/**
	 * 返回的IP形式如："172.16.0.127"
	 * @return 点分十进制IP地址
	 * @throws UnknownHostException
	 */
	public static String getLocalIpAsDecimal() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress(); 
	}
	
	/**
	 * 该方法同 {@link NetUtil.getLocalIpByDecimal}
	 * @return
	 * @throws UnknownHostException
	 */
	@Deprecated
	public static String getHostName() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	public static  String getMask(String ip,int mask){
		if(ip.equals(S3SearchConstant.KATTA_MULL)){
			return ip;
		}
//		if(mask==0){
//			return 
//		}
		long maskNum=1;
		maskNum=((maskNum<<mask)-1)<<(32-mask);
		long ipLong=Long.parseLong(ip, 16);
		ipLong=ipLong&maskNum;
		return Long.toHexString(ipLong);
	}
}
