package com.example.rssreader.model;

import java.io.Serializable;

import com.example.rssreader.utils.AppUtils;

public class RSSItem implements Serializable
{
	private String title;
	private String description;
	private String link;
	private String category;
	private String imageUrl;
	private String pubdate;

	
	public RSSItem()	{}
	
	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setDescription(String description)
	{
		this.description = description;
		this.imageUrl = AppUtils.getImgUrlFromHtml(description);
	}
	
	public void setLink(String link)
	{
		this.link = link;
	}
	
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	public void setPubDate(String pubdate)
	{
		this.pubdate = pubdate;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getLink()
	{
		return link;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPubDate()
	{
		return pubdate;
	}
	
	public String toString()
	{
		// limit how much text we display
		if (title.length() > 42)
		{
			return title.substring(0, 42) + "...";
		}
		return title;
	}
}
