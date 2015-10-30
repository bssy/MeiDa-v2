package com.soul.project.application.bean;

public class News
{
	String title;
	String description;
	String context;
	String url;
	String pubTime;
	
	public String getPubTime()
	{
		return pubTime;
	}
	public void setPubTime(String pubTime)
	{
		this.pubTime = pubTime;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getContext()
	{
		return context;
	}
	public void setContext(String context)
	{
		this.context = context;
	}
	@Override
	public String toString()
	{
		return "News [title=" + title + ", description=" + description + ", url=" + url + "]";
	}
	
	
}
