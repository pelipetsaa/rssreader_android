package com.example.rssreader.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.rssreader.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {
	
	final String TAG = "ImageLoader";
	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;
	
	public ImageLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	final int stub_id = R.drawable.loading_image;

	public void displayImage(String url, ImageView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url.substring(url.lastIndexOf('/') + 1,
				url.length()));
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			Log.d(TAG, "Received from cache : " + url.substring(url.lastIndexOf('/') + 1,url.length()));
		} else {
			queuePhoto(url, imageView);
			imageView.setImageResource(stub_id);
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		
		if (b != null)
		{
			Log.d(TAG,"Bitmap "+url.substring(url.lastIndexOf('/') + 1,
					url.length())+ " loaded from cache folder ");
			
			return b;
		}
		// from web
		try {
			
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			try{
				AppUtils.copyStream(is, os);
				//IOUtils.copyStreamEx(is, os);
			}
			finally{
			 os.close();
			 is.close();
			}
			
			bitmap = decodeFile(f);
			
			Log.d(TAG,"Bitmap "+url.substring(url.lastIndexOf('/') + 1,
					url.length())+ " loaded from internet ");
			return bitmap;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			/*BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			
			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			Log.d(TAG,"Decoding... Received width - "+width_tmp+" height "+height_tmp);
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Log.d(TAG,"Scale: "+scale+" Decoded width - "+o2.outWidth+" height "+o2.outHeight);
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);*/
			return BitmapFactory.decodeStream(new BufferedInputStream(new FileInputStream(f)), null, null);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmap(photoToLoad.url);
			
			//Log.d(TAG,"Resulting bitmap width: - "+bmp.getWidth()+" height - "+bmp.getHeight());
			
			memoryCache.put(photoToLoad.url.substring(
					photoToLoad.url.lastIndexOf('/') + 1,
					photoToLoad.url.length()), bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}
	// Use when nessesary
	public void clearCache() {
		Log.d("NailMall","Cache cleared");
		memoryCache.clear();
		fileCache.clear();
	}

}
