package com.example.rssreader.activity;


import com.example.rssreader.AppContext;
import com.example.rssreader.Application;
import com.example.rssreader.R;
import com.example.rssreader.model.RSSItem;
import com.example.rssreader.utils.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RSSDetailsActivity extends Activity {
	
	private ImageView itemImage;
	private TextView  itemDescription;
	
	private RSSItem item;
	private ImageLoader imageLoader;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_details);
		initViews();
		initImageLoader();
		fillData();

	}
	
	private void initViews(){
		itemImage = (ImageView)findViewById(R.id.item_image);
		itemDescription = (TextView)findViewById(R.id.item_description);
	}
	
	private void initImageLoader(){
		//imageLoader = new ImageLoader(this);
		imageLoader = Application.getInstance().getAppContext().getImageLoader();
	}
	
	private void fillData(){
		item = (RSSItem)getIntent().getSerializableExtra(AppContext.RSS_ITEM_KEY);
		imageLoader.displayImage(item.getImageUrl(), itemImage);
		itemDescription.setText(item.getDescription());
	}

}
