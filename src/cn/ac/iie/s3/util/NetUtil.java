package cn.ac.iie.s3.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class NetUtil {

	/**
	 * 首先返回第一个不是127.0.0.1的IP地址(多网卡需要验证)，如果没有非127.0.0.1的IP，则返回127.0.0.1对应的值
	 * 返回的IP形式如："AC10007F" ----(172.16.0.127)
	 * @return "7F000001"  --- 127.0.0.1
	 * @throws UnknownHostException
	 */
	public static String getLocalIp() throws UnknownHostException {
		String[] ipStr;
		Collection<InetAddress> colInetAddress =getAllHostAddress();
		for (InetAddress address : colInetAddress) {
			System.out.println(address.getHostAddress());
			if (!address.isLoopbackAddress()){
				if(checkIPv4Address(address.getHostAddress())){
					ipStr = address.getHostAddress().split("\\.");
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < ipStr.length; i++) {
						ipStr[i] = ipStr[i].trim();
						short s = Short.parseShort(ipStr[i]);
						sb.append(String.format("%02X", s));
					}
					return sb.toString();
				}
			}
		}   
		return "7F000001";
	}
	/**
	 * 返回的IP形式如："172.16.0.127"
	 * @return 点分十进制IP地址
	 * @throws UnknownHostException
	 */
	public static String getLocalIpAsDecimal() throws UnknownHostException {
		Collection<InetAddress> colInetAddress =getAllHostAddress();
		for (InetAddress address : colInetAddress) {   
			if (!address.isLoopbackAddress()){
				if(checkIPv4Address(address.getHostAddress())){
					return address.getHostAddress();
				}
			}
		}
		return "127.0.0.1";
	}
	
	/**
	 * 该方法获取机器的主机名
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getHostName() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}
	
	public static  String getMask(String ip,int mask){
		long maskNum=1;
		maskNum=((maskNum<<mask)-1)<<(32-mask);
		long ipLong=Long.parseLong(ip, 16);
		ipLong=ipLong&maskNum;
		return Long.toHexString(ipLong);
	}
	
	private static Collection<InetAddress> getAllHostAddress() {   
        try {   
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();   
            Collection<InetAddress> addresses = new ArrayList<InetAddress>();   
               
            while (networkInterfaces.hasMoreElements()) {   
                NetworkInterface networkInterface = networkInterfaces.nextElement();   
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();   
                while (inetAddresses.hasMoreElements()) {   
                    InetAddress inetAddress = inetAddresses.nextElement();   
                    addresses.add(inetAddress);   
                }   
            }   
               
            return addresses;   
        } catch (SocketException e) {   
            throw new RuntimeException(e.getMessage(), e);   
        }   
    }
	
	/** 
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小) 
     * @param ipAddress IPv4标准地址 
     * @return 验证成功返回true，验证失败返回false 
     */   
    public static boolean checkIPv4Address(String ipAddress) {   
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";   
        return Pattern.matches(regex, ipAddress);   
    }
}
