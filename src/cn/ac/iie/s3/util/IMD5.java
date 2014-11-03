package cn.ac.iie.s3.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * MD5检测类，每个lucene文件夹下的小文件生成一个MD5值，将所有的MD5值异或
 */
public class IMD5 {
	
	private MessageDigest md = null;
	private final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * 构造函数是私有的，你必须使用getInstance方法
	 */
	public IMD5() throws NoSuchAlgorithmException {
		md = MessageDigest.getInstance("MD5");
	}
	
	public String hashCode(String dataToHash)
			throws NoSuchAlgorithmException {
		return hashData(dataToHash.getBytes());
	}

	public String hashCode(byte[] dataToHash)
			throws NoSuchAlgorithmException {
		return hashData(dataToHash);
	}

	public String hashData(byte[] dataToHash) {
		return hexStringFromBytes((calculateHash(dataToHash))).toLowerCase();
	}

	private byte[] calculateHash(byte[] dataToHash) {
		md.update(dataToHash, 0, dataToHash.length);
		return (md.digest());
	}

	public String hexStringFromBytes(byte[] b) {
		String hex = "";
		int msb;
		int lsb = 0;
		int i;
		// MSB maps to idx 0
		for (i = 0; i < b.length; i++) {
			msb = ((int) b[i] & 0x000000FF) / 16;
			lsb = ((int) b[i] & 0x000000FF) % 16;
			hex = hex + hexChars[msb] + hexChars[lsb];
		}
		return (hex);
	}
	
//静态方法
	public static String countMD5(String dirPath) throws NoSuchAlgorithmException, IOException {
		// TODO Auto-generated method stub
		
		byte[] md5 = new byte[16];

		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files != null){
			for (File f : files){
				if (f.isFile()&& !f.getName().endsWith(".lock")){
					FileInputStream fileInputStream = null;
				    DigestInputStream digestInputStream = null;
				    MessageDigest messageDigest =MessageDigest.getInstance("MD5");

				    fileInputStream = new FileInputStream(f);
				    digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
				    
			        while (digestInputStream.read() > 0);

			        messageDigest= digestInputStream.getMessageDigest();

			        byte[] resultByteArray = messageDigest.digest();
			        for(int i=0;i<16;i++){
						md5[i] = (byte) ((byte) md5[i] ^ resultByteArray[i]);
					}
			        digestInputStream.close();
			        fileInputStream.close();
			  }
			}
		}
		//将MD5 byte数组转化为 字符串
 	      // 首先初始化一个字符数组，用来存放每个16进制字符
 	     char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
 	 
 	      // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
 	     char[] resultCharArray =new char[md5.length * 2];
 	 
 	      // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
 	     int index = 0;
 	     for (byte b : md5) {
 	         resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
 	         resultCharArray[index++] = hexDigits[b& 0xf];
 	      }
 	      // 字符数组组合成字符串返回
 	     String md5_str = new String(resultCharArray);
 	     return md5_str;
	}

}
