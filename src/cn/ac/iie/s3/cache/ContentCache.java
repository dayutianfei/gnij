package cn.ac.iie.s3.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

public class ContentCache {

	public static boolean neverNeedsRefresh = false;

	public static Cache<String, CachedContent> cache = CacheBuilder
			.newBuilder().expireAfterAccess(60, TimeUnit.SECONDS)
			.maximumSize(100*1024*1024).build(new CacheLoader<String, CachedContent>() {
				@Override
				public CachedContent load(String key) { // no checked exception
					return new CachedContent(key);
				}
				@Override
				public ListenableFuture<CachedContent> reload(final String key,
						CachedContent content) {
					if (neverNeedsRefresh) {
						return ListenableFutureTask
								.create(new Callable<CachedContent>() {
									public CachedContent call() {
										return new CachedContent(key);
									}
								});
					} else {
						// asynchronous!
						return ListenableFutureTask
								.create(new Callable<CachedContent>() {
									public CachedContent call() {
										return new CachedContent(key);
									}
								});
					}
				}
			});
}
