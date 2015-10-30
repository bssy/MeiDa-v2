package com.soul.project.application.adapter;

import java.util.List;

import com.soul.project.application.bean.CommentBean;
import com.soul.project.story.activity.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapter {

	private List<CommentBean> list;
	private Context context;
	private LayoutInflater inflater;
	
	public CommentsAdapter(Context context,List<CommentBean> list)
	{
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		CommentBean bean = list.get(position);
		
		if(convertView == null)
		{
			convertView = (View)inflater.inflate(R.layout.comments_list_item_layout, null);
			
			holder = new ViewHolder();
			
			holder.ivHead = (ImageView)convertView.findViewById(R.id.ivHead);
			holder.txtCommentAuthor = (TextView)convertView.findViewById(R.id.txtCommenter);
			holder.txtCommentPubDate = (TextView)convertView.findViewById(R.id.txtPubDate);
			holder.txtCommentText = (TextView)convertView.findViewById(R.id.txtCommentText);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtCommentAuthor.setText(bean == null ? "null":bean.getCommentAuthor());
		holder.txtCommentPubDate.setText(bean.getCommentPubDate());
		holder.txtCommentText.setText(bean.getCommentText());
		
		return convertView;
	}
	
	private static class ViewHolder 
	{
		TextView txtCommentText;
		TextView txtCommentAuthor;
		TextView txtCommentPubDate;
		ImageView ivHead;
	}

}
