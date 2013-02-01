package com.example.rssreader.activity;


import com.example.rssreader.AppContext;
import com.example.rssreader.R;
import com.example.rssreader.adapter.RSSItemAdapter;
import com.example.rssreader.model.RSSFeed;
import com.example.rssreader.model.RSSItem;
import com.example.rssreader.rss.RSSTask;
import com.example.rssreader.task.*;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;


public class RSSListActivity extends ListActivity implements CustomAsyncTask.AsyncTaskListener {
	
	public static final String TAG = "RSSListActivity";
	
	private RSSTask rssTask = null;
	private RSSFeed feed = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_list);
		processRSS();
	}
	
	private void processRSS(){
		if (rssTask == null){
			rssTask = new RSSTask(this);
			rssTask.setShowProgress(true);
			rssTask.setAsyncTaskListener(this);
			rssTask.execute(AppContext.RSS_URL);	
		}
	}
	
	private void fillRSSList(){
        if (feed == null){
        	//feedtitle.setText("No RSS Feed Available");
        	return;
        }

        RSSItemAdapter adapter = new RSSItemAdapter(this,feed.getAllItems());
        setListAdapter(adapter);
	}
	


	@Override
	public void onBeforeTaskStarted(CustomAsyncTask<?, ?, ?> task) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void onTaskFinished(CustomAsyncTask<?, ?, ?> task) {
		if (task == rssTask){
			boolean result = (Boolean)task.getResult();
			if (result){
				feed = ((RSSTask)task).getFeed();
				fillRSSList();
			} else{
				
			}
		}
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RSSItem item = feed.getAllItems().get(position);
		Intent intent = new Intent(this, RSSDetailsActivity.class);
		intent.putExtra(AppContext.RSS_ITEM_KEY, item);
		startActivity(intent);
	}
	
	

}
