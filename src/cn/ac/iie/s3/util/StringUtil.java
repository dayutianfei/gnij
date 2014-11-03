/**
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ac.iie.s3.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/***
 *  字符串操作类
 *
 */
public class StringUtil {

  /**
   * Usage:<br>
   * String callingMethod =
   * StringUtil.getCallingMethod(Thread.currentThread().getStackTrace());
   */
  public static String getCallingMethod(StackTraceElement[] stackTrace) {
    int indexOfCallingMethod = 2;
    if (stackTrace[0].toString().endsWith("(Native Method)")) {
      // on some jvm the native methods get printed on some not
      indexOfCallingMethod = 3;
    }
    return stackTrace[indexOfCallingMethod].toString();
  }
  
  /**
  public static void main(String[] args)
  {
	  String ss = formatTimeDuration(101010000);
	  System.out.println(ss);
  }
  */

  /***
   * 
   *  函数：根据整数毫秒（ms）值计算时间字符串
   *  输入：101010000
   *  输出："28 hrs, 3 mins, 30 sec"
   */
  public static String formatTimeDuration(long timeDuration) {
    StringBuilder builder = new StringBuilder();
    long hours = timeDuration / (60 * 60 * 1000);
    long rem = (timeDuration % (60 * 60 * 1000));
    long minutes = rem / (60 * 1000);
    rem = rem % (60 * 1000);
    long seconds = rem / 1000;

    if (hours != 0) {
      builder.append(hours);
      builder.append(" hrs, ");
    }
    if (minutes != 0) {
      builder.append(minutes);
      builder.append(" mins, ");
    }
    // return "0sec if no difference
    builder.append(seconds);
    builder.append(" sec");
    return builder.toString();
  }

  /**
   * 
   * @param string
   * @param length
   * @return the given path + as many whitespace that the given string reaches
   *         the given length
   */
  /***
   *  函数：在给定的字符串string后填补空格 以达到length长度
   *  
   */
  public static String fillWithWhiteSpace(String string, int length) {
    int neededWhiteSpace = length - string.length();
    if (neededWhiteSpace > 0) {
      StringBuilder builder = new StringBuilder(string);
      for (int i = 0; i < neededWhiteSpace; i++) {
        builder.append(" ");
      }
      return builder.toString();
    }
    return string;
  }

  /**
   * Gets all thread stack traces.
   * 
   * @return string of all thread stack traces
   */
  public static String getThreadDump() {
    StringBuilder sb = new StringBuilder();
    Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
    for (Thread thread : stacks.keySet()) {
      sb.append(thread.toString()).append('\n');
      for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
        sb.append("\tat ").append(stackTraceElement.toString()).append('\n');
      }
      sb.append('\n');
    }
    return sb.toString();
  }
  
  /**
	 * 获取分片的时间戳
	 * 文件的创建时间.
	 * @param shardName
	 * @return
	 */
	public static String getShardTimestamp(String shardName) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < shardName.length(); i++) {
			char c = shardName.charAt(i);
			if (c >= '0' && c <= '9') {
				buffer.append(c);
			} else {
				break;
			}
		}
		return buffer.toString();
	}
	
	public static String string2Unicode(String s) {  
	    try {  
	      StringBuffer out = new StringBuffer("");  
	      byte[] bytes = s.getBytes("unicode");  
	      for (int i = 2; i < bytes.length - 1; i += 2) {  
	        out.append("u");  
	        String str = Integer.toHexString(bytes[i + 1] & 0xff);  
	        for (int j = str.length(); j < 2; j++) {  
	          out.append("0");  
	        }  
	        String str1 = Integer.toHexString(bytes[i] & 0xff);  
	  
	        out.append(str);  
	        out.append(str1);  
	        out.append(" ");  
	      }  
	      return out.toString().toUpperCase();  
	    }  
	    catch (UnsupportedEncodingException e) {  
	      e.printStackTrace();  
	      return null;  
	    }  
	  }   
	  
	   
	  
	public static String unicode2String(String unicodeStr){  
	    StringBuffer sb = new StringBuffer();  
	    String str[] = unicodeStr.toUpperCase().split("U");  
	    for(int i=0;i<str.length;i++){  
	      if(str[i].equals("")) continue;  
	      char c = (char)Integer.parseInt(str[i].trim(),16);  
	      sb.append(c);  
	    }  
	    return sb.toString();  
	  }
	
	/**  
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]  
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程  
     *   
     * @param arrB  
     *            需要转换的byte数组  
     * @return 转换后的字符串  
     * @throws Exception  
     *             本方法不处理任何异常，所有异常全部抛出  
     */  
   public static String byteArr2HexStr(byte[] arrB) throws Exception {
       int iLen = arrB.length;
       // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
       StringBuffer sb = new StringBuffer(iLen * 2);
       for (int i = 0; i < iLen; i++) {
           int intTmp = arrB[i];
           // 把负数转换为正数
           while (intTmp < 0) {
               intTmp = intTmp + 256;
           }
           // 小于0F的数需要在前面补0
           if (intTmp < 16) {
               sb.append("0");
           }
           sb.append(Integer.toString(intTmp, 16));
       }
       return sb.toString();
   }

   /**
    * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
    * 互为可逆的转换过程
    * 
    * @param strIn
    *            需要转换的字符串
    * @return 转换后的byte数组
    * @throws Exception
    *             本方法不处理任何异常，所有异常全部抛出
    */
   public static byte[] hexStr2ByteArr(String strIn) throws Exception {
       byte[] arrB = strIn.getBytes();
       int iLen = arrB.length;

       // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
       byte[] arrOut = new byte[iLen / 2];
       for (int i = 0; i < iLen; i = i + 2) {
           String strTmp = new String(arrB, i, 2);
           arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
       }
       return arrOut;
   }
	
	public static void main(String[] args) {
		String i;
		try {
			i = StringUtil.byteArr2HexStr("testzhon中文，,测试1203".getBytes());
			System.out.println(new String(StringUtil.hexStr2ByteArr((i))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
