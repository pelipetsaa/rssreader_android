package com.example.rssreader.rss;

import com.example.rssreader.model.RSSFeed;
import com.example.rssreader.task.CustomAsyncTask;

import android.content.Context;

public class RSSTask extends CustomAsyncTask<String, Void, Boolean>{

	private RSSFeed feed;	
	
	public RSSTask(Context mContext) {
		super(mContext);
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String urlToRssFeed = params[0];
		feed = RSSReader.getFeed(urlToRssFeed);
 		return (feed != null);
	}

	public RSSFeed getFeed() {
		return feed;
	}
	
}
