package cn.ac.iie.s3.cache;

import java.util.concurrent.ExecutionException;

public class Main {
	public static void main(String[] args) throws ExecutionException {
		long t0 = System.currentTimeMillis();
		System.out.println("cache before: "+ContentCache.cache.size());
		ContentCache.cache.put("/temp/test123.jar", new CachedContent("/temp/test123.jar"));
		System.out.println("cache after:"+ContentCache.cache.size());
		System.out.println("cache cost : "+(System.currentTimeMillis() - t0));
		long t1 = System.currentTimeMillis();
		CachedContent a = ContentCache.cache.getIfPresent("/temp/test123.jar");
		a.getKey();
		System.out.println("cost 1 : "+(System.currentTimeMillis() - t1));
		long t2 = System.currentTimeMillis();
		CachedContent b = ContentCache.cache.getIfPresent("/temp/test123.jar");
		b.getKey();
		System.out.println("cache stats"+ContentCache.cache.stats());
		System.out.println("cost 1 : "+(System.currentTimeMillis() - t2));
	}
}
