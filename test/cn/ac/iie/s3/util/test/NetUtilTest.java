package cn.ac.iie.s3.util.test;

import java.net.UnknownHostException;

import cn.ac.iie.s3.util.NetUtil;
import junit.framework.TestCase;

public class NetUtilTest extends TestCase{

    public void setUp() throws Exception {
    }

    public void tearDown() throws Exception {
    }

    public void testGetHostName() throws UnknownHostException {
        String expResult = "dayutianfei.local";
        String result = NetUtil.getHostName();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    public void testGetLocalIp() throws UnknownHostException {
        String expResult = "7F000001";
        String result = NetUtil.getLocalIp();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    public void testGetLocalIpAsDecimal() throws UnknownHostException {
        String expResult = "127.0.0.1";
        String result = NetUtil.getLocalIpAsDecimal();
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
}
