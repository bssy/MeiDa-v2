package com.soul.project.application.util;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;
import com.soul.project.application.bean.News;

public class SaxParse extends DefaultHandler
{
	private static final String TAG = "XU";
	public List<News> beans;
	News news;
	String tagName;
	int flag;
	String content = "";
	boolean isItem = false;

	public List<News> getDatas()
	{
		return beans;
	}


	public void startDocument() throws SAXException
	{
		beans = new ArrayList<News>();
		news = new News();
		super.startDocument();
	}


	public void endDocument() throws SAXException
	{
		super.endDocument();
	}


	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if( localName.equals("title") )
		{
			flag = 1;
			return;
		} else if( localName.equals("description") )
		{
			flag = 2;
			return;
		} else if( localName.equals("pubDate") )
		{
			flag = 3;
			return;
		} else if( localName.equals("link") )
		{
			flag = 4;
			return;
		}
		flag = -1;
	}


	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if( localName.equals("item") )
		{
			beans.add(news);
			Log.i("XU", "new to String ="+news.toString());
			news = new News();
		}
	}


	public void characters(char[] ch, int start, int length) throws SAXException
	{
		String s = new String(ch, start, length);
		content = s;
		if(content.trim().length() < 2)
			return;
		if( flag == 1 )
		{
			news.setTitle(content);
			return;
		} else if( flag == 2 )
		{
			news.setDescription(content);
			return;
		} else if( flag == 3 )
		{
			news.setPubTime(content);
			return;
		} else if( flag == 4 )
		{
			news.setUrl(content);
			return;
		}
		flag = -1;
	}

}
