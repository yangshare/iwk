package com.iwk.yang.volley;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 图片缓存类(Lru算法)
 * @author Yan
 *
 */
public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageCache{
	private static final String TAG="BitmapLruCache";
	//图片缓存的软引用
	private BitmapSoftRefCache softRefCache;

	public BitmapLruCache(int maxSize) {
		super(maxSize);
		//初始化BitmapSoftRefCache
		softRefCache=new BitmapSoftRefCache();
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes()*value.getHeight();
	}

	@Override
	protected void entryRemoved(boolean evicted, String key, Bitmap oldValue,
			Bitmap newValue) {
		if(evicted){
			//将bitmap添加到软引用的缓存中
			softRefCache.putBitmap(key, oldValue);
		}

	}

	/**
	 * 从缓存中获取图片
	 */
	@Override
	public Bitmap getBitmap(String url) {
		Bitmap bitmap=get(url);
		if(bitmap==null){
			//从软引用缓存中获取
			bitmap=softRefCache.getBitmap(url);
		}
		return bitmap;
	}

	/**
	 * 将图片放入到缓存中
	 */
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}
}
