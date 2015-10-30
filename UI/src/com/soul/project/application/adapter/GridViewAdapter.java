package com.soul.project.application.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.makeramen.RoundedImageView;
import com.soul.project.application.bean.GridViewBean;
import com.soul.project.application.util.BitmapCache;
import com.soul.project.story.activity.R;

public class GridViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<GridViewBean> list;
	private RequestQueue mQueue;
	private ImageLoader mImageLoader;
	private int maxWidth;
	private int maxHeight;
	
	public GridViewAdapter(Context context,List<GridViewBean> list, int width, int height)
	{
		inflater = LayoutInflater.from(context);
		mQueue = Volley.newRequestQueue(context);  
		mImageLoader = new ImageLoader(mQueue, new BitmapCache()); 
		this.list = list;
		this.context = context;
		
		maxHeight = height / 6;
		maxWidth  = width / 3;
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
		GridViewBean bean = list.get(position);
		
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = (View)inflater.inflate(R.layout.gridview_item_layout, null);
			
			holder.imageView = (RoundedImageView)convertView.findViewById(R.id.gv_item_image);
			holder.txtTitle  = (TextView)convertView.findViewById(R.id.gv_item_title);
			holder.ivType = (ImageView)convertView.findViewById(R.id.gv_item_type);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
	    // imageView是一个ImageView实例  
	    // ImageLoader.getImageListener的第二个参数是默认的图片resource id  
	    // 第三个参数是请求失败时候的资源id，可以指定为0  
	    ImageListener listener = ImageLoader.getImageListener(holder.imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);  
	    mImageLoader.get(bean.getImageUrl(), listener, maxWidth, maxHeight);
		
	    holder.txtTitle.setText(bean.getTitle());
	    int type = bean.getType();
	    switch (type) {
		case 0:holder.ivType.setBackgroundResource(0);break;
		case 1:holder.ivType.setBackgroundResource(R.drawable.icon_type_vip);break;
		case 2:holder.ivType.setBackgroundResource(R.drawable.icon_type_boutique);break;
		case 3:holder.ivType.setBackgroundResource(R.drawable.icon_type_hot);break;
		default:
			break;
		}
	    
		return convertView;
	}

	
	private static class ViewHolder
	{
		RoundedImageView imageView;
		TextView txtTitle;
		TextView txtDesc;
		TextView txtAuthor;
		TextView txtGrade;
		ImageView ivType;
	}
	
}
