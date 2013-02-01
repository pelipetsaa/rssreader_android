package com.example.rssreader.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RSSFeed 
{
	private String title;
	private String pubdate;
	private int itemCount;
	private List<RSSItem> itemList = new ArrayList<RSSItem>();
	
	
	public RSSFeed(){ }
	
	public int addItem(RSSItem item)
	{
		itemList.add(item);
		itemCount++;
		return itemCount;
	}
	
	public RSSItem getItem(int location)
	{
		return itemList.get(location);
	}
	public List<RSSItem> getAllItems()
	{
		return itemList;
	}
	
	public int getItemCount()
	{
		return itemCount;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setPubDate(String pubdate)
	{
		this.pubdate = pubdate;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getPubDate()
	{
		return pubdate;
	}
	
	
}
