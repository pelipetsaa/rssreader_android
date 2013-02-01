package com.example.rssreader;

import com.example.rssreader.utils.ImageLoader;

import android.content.Context;



public class AppContext {
	
	public static final String TAG = "AppContext";
	
	public static final String APP_NAME = "RSSReader";
	
	public static final String RSS_URL = "http://goo.gl/DrghO";
	
	public static final String RSS_ITEM_KEY = "RSS_ITEM";

	private Context context;
    
	private ImageLoader imageLoader;
	
    public AppContext(Context context){
    	this.context = context;
    	imageLoader = new ImageLoader(context);
    }
    
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

}
