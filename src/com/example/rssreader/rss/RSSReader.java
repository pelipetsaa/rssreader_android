package com.example.rssreader.rss;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

import com.example.rssreader.model.RSSFeed;

public class RSSReader {

	 public static final String TAG = "RSSReader";
	
	  public static RSSFeed getFeed(String urlToRssFeed)
	    {
	    	try
	    	{
	    		// setup the url
	    	   URL url = new URL(urlToRssFeed);

	           // create the factory
	           SAXParserFactory factory = SAXParserFactory.newInstance();
	           // create a parser
	           SAXParser parser = factory.newSAXParser();

	           // create the reader (scanner)
	           XMLReader xmlreader = parser.getXMLReader();
	           // instantiate our handler
	           RSSHandler theRssHandler = new RSSHandler();
	           // assign our handler
	           xmlreader.setContentHandler(theRssHandler);
	           // get our data via the url class
	           InputSource is = new InputSource(url.openStream());
	           // perform the synchronous parse           
	           xmlreader.parse(is);
	           // get the results - should be a fully populated RSSFeed instance, or null on error
	           return theRssHandler.getFeed();
	    	}
	    	catch (Exception ex)
	    	{
	    		// if we have a problem, simply return null
	    		Log.e(TAG, ex.toString());
	    		return null;
	    	}
	    }
	  	

}
