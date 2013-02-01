package com.example.rssreader.utils;

import java.io.File;
import java.net.URLEncoder;

import com.example.rssreader.AppContext;

import android.content.Context;
import android.util.Log;

public class FileCache {

	private File cacheDir;
	private String cacheDirFileName = AppContext.APP_NAME;

	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					cacheDirFileName);
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
			//Log.d("dating", "Creating temp directory " + cacheDirFileName);
		} //else Log.d("dating", "Temp directory " + cacheDirFileName+" is already exists");
	}

	public File getFile(String url) {
		String fileName = url.substring(url.lastIndexOf('/') + 1, url.length());
		File f = new File(cacheDir, fileName);
		return f;
	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}
}