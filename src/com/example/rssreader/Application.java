package com.example.rssreader;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class Application extends android.app.Application {

	public static final String TAG = "Application";
	public static final String APP_NAME = "RSSReader";

	private static Application instance;

	private Context context;
	private String version;
	private boolean isDebuggable;

	private AppContext appContext = null;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		context = getApplicationContext();
		isDebuggable = (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

		try {
			ComponentName comp = new ComponentName(context, getClass());
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(comp.getPackageName(), 0);
			version = packageInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException(e);
		}

		appContext = new AppContext(context);
	}

	public Context getContext() {
		return context;
	}

	public String getVersion() {
		return version;
	}

	public boolean isDebuggable() {
		return isDebuggable;
	}

	public AppContext getAppContext() {
		return appContext;
	}

	public void setAppContext(AppContext appContext) {
		this.appContext = appContext;
	}

	public static Application getInstance() {
		if (instance == null) {
			Log.d(TAG, "Application instance is null!");
		}
		return instance;
	}

	@Override
	public void onLowMemory() {
		Log.d(TAG, "Application: onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		Log.d(TAG, "Application: onTerminate");
		super.onTerminate();
	}

}
