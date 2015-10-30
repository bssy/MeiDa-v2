package com.soul.project.application.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.soul.project.application.bean.News;

public class ListViewAdapter extends BaseAdapter
{
	Context context;
	List<News> list = new ArrayList<News>();
	private LayoutInflater inflater;
	
	public ListViewAdapter(Context context,List<News> list)
	{
		this.context = context;
		if(list != null)
		this.list = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		ViewHolder holder = null;	
		if (convertView == null) {
			convertView = inflater.inflate(com.soul.project.story.activity.R.layout.listview_layout, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView)convertView.findViewById(com.soul.project.story.activity.R.id.txt_title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		News info = list.get(position);
		String title = info.getTitle();
		if(title != null)
			holder.txtTitle.setText(title);
		else
			holder.txtTitle.setText("测试");
		
		return convertView;
	}
	
	 private final class ViewHolder
	 { 
		 public TextView txtTitle;
	 }
}
