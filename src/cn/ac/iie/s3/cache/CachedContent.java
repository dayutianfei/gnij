package cn.ac.iie.s3.cache;

import java.io.File;

public class CachedContent {

	public String key = null;
	
	public CachedContent(){
		
	}
	public CachedContent(String key){
		this.key = key;
		File file = new File(key);
		System.out.println(file.length());
	}
	
	public void getKey(){
		System.out.println(this.key);
	}
}
