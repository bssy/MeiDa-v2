package com.soul.project.application.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.soul.project.application.data.dummy.HomeGridView;
import com.soul.project.application.util.AndroidTools;
import com.soul.project.story.activity.R;

/**
 * @author Linhj
 * @date 2013-5-18
 * @version v1.0
 * @copyright Copyright (c) 2013 Soul,Inc. All Rights Reserved.
 */
public class HomeGridViewAdapter extends BaseAdapter {

	private Context context;
	private List<HomeGridView> list;
	private LayoutInflater layout_inflater = null;

	public HomeGridViewAdapter(Context context, List<HomeGridView> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		try {
			ViewHolder holder = null;
			if (holder == null) {
				holder = new ViewHolder();
				layout_inflater = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = layout_inflater.inflate(R.layout.gridview_home, null);

				holder.img = (ImageView) view
						.findViewById(R.id.imgView_home_gridview_item);
				holder.txt = (TextView) view
						.findViewById(R.id.txt_home_gridview_item);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.img.setImageResource(list.get(position).getIcoId());
			holder.txt.setText(list.get(position).getName().toString());
			float scale = context.getResources().getDisplayMetrics().density;
			int width = AndroidTools.getWindowsWidth(this.context)
					- (int) (65 * scale + 0.5f * (65 >= 0 ? 1 : -1));
			int w = (width - ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 8)) / 2;

			int height = AndroidTools.getWindowsHeight(this.context);
			int i = height - w * 4
					- ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 13);
			if ((i < 20) && (i >= 0)) {
				w = 20 - i / 4;
			} else if (i < 0) {
				w = w + i / 4 - (int) (10 * scale + 0.5f * (10 >= 0 ? 1 : -1));
			} else {
				i = 0;
			}
			GridView.LayoutParams params = new GridView.LayoutParams(w, w);
			view.setLayoutParams(params);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// view.setMinimumHeight(w);
		// view.setMinimumWidth(w);
		return view;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView txt;
	}
}
