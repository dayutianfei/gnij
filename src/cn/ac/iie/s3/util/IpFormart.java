package cn.ac.iie.s3.util;

public class IpFormart {
	
	public static String getHexIp(String ip){
		String[] ips = ip.split("\\.");
		String result = "";
		for(String s: ips){
			result += changeNumToHex(Integer.parseInt(s));
		}
		return result;
	}

	 private static String changeNumToHex(int n){
		  String str = "";
	      if(n >= 16){
	          str = numToHex(n/16)+numToHex(n%16);
	      }else{
	    	  str = "0"+numToHex(n);
	      }
	      return str;
	   }
	 
	   private static String numToHex(int n){
	      switch(n){
	         case 10 :
	           return "A";
	         case 11 :
	           return "B";
	         case 12 :
	           return "C";
	         case 13 :
	           return "D";
	         case 14 :
	           return "E";   
	         case 15 :
	           return "F";  
	      }
	      return ""+n;
	   }
}
