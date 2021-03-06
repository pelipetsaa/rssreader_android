package com.example.rssreader.rss;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import com.example.rssreader.model.RSSFeed;
import com.example.rssreader.model.RSSItem;

import android.util.Log;



public class RSSHandler extends DefaultHandler 
{
	private static final int RSS_TITLE = 1;
	private static final int RSS_LINK = 2;
	private static final int RSS_DESCRIPTION = 3;
	private static final int RSS_CATEGORY = 4;
	private static final int RSS_PUBDATE = 5;
	private static final int RSS_IMAGE = 6;
	
	private RSSFeed feed;
	private RSSItem item;
	private String lastElementName = "";
	private boolean foundChannel = false;

	private int depth = 0;
	private int currentstate = 0;
	
	/*
	 * Constructor 
	 */
	public RSSHandler()	{ }
	
	/*
	 * getFeed - this returns our feed when all of the parsing is complete
	 */
	public RSSFeed getFeed(){
		return feed;
	}
	
	
	public void startDocument() throws SAXException{
		// initialize our RSSFeed object - this will hold our parsed contents
		feed = new RSSFeed();
		// initialize the RSSItem object - we will use this as a crutch to grab the info from the channel
		// because the channel and items have very similar entries..
		item = new RSSItem();

	}
	public void endDocument() throws SAXException{
	}
	
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException
	{
		Log.i("RSSReader","startElement[" + localName + "]");
		depth++;
		if (localName.equals("channel"))
		{
			currentstate = 0;
			return;
		}
		if (localName.equals("image"))
		{
			// record our feed data - we temporarily stored it in the item :)
			feed.setTitle(item.getTitle());
			feed.setPubDate(item.getPubDate());
		}
		if (localName.equals("item"))
		{
			// create a new item
			item = new RSSItem();
			return;
		}
		if (localName.equals("title"))
		{
			currentstate = RSS_TITLE;
			return;
		}
		if (localName.equals("description"))
		{
			currentstate = RSS_DESCRIPTION;
			return;
		}
		if (localName.equals("link"))
		{
			currentstate = RSS_LINK;
			return;
		}
		if (localName.equals("category"))
		{
			currentstate = RSS_CATEGORY;
			return;
		}
		if (localName.equals("pubDate"))
		{
			currentstate = RSS_PUBDATE;
			return;
		}
		if (localName.equals("image"))
		{
			currentstate = RSS_IMAGE;
			return;
		}
		// if we don't explicitly handle the element, make sure we don't wind up erroneously 
		// storing a newline or other bogus data into one of our existing elements
		currentstate = 0;
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
	{
		depth--;
		if (localName.equals("item"))
		{
			// add our item to the list!
			feed.addItem(item);
			return;
		}
	}
	 
	public void characters(char ch[], int start, int length)
	{
		String theString = new String(ch,start,length);
		//Log.i("RSSReader","characters[" + theString + "]");
		
		switch (currentstate)
		{
			case RSS_TITLE:
				item.setTitle(theString);
				currentstate = 0;
				break;
			case RSS_LINK:
				item.setLink(theString);
				currentstate = 0;
				break;
			case RSS_DESCRIPTION:
				item.setDescription(theString);
				currentstate = 0;
				break;
			case RSS_CATEGORY:
				item.setCategory(theString);
				currentstate = 0;
				break;
			case RSS_PUBDATE:
				item.setPubDate(theString);
				currentstate = 0;
				break;
			case RSS_IMAGE:
				item.setImageUrl(theString);
				currentstate = 0;
				break;
			default:
				return;
		}
		
	}
}
