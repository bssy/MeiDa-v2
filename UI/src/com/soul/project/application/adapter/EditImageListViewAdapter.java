package com.soul.project.application.adapter;

import java.util.List;

import com.sky.utils.BitmapUtil;
import com.sky.utils.IceFilter;
import com.sky.utils.SoftGlowFilter;
import com.soul.project.application.bean.GridViewBean;
import com.soul.project.story.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EditImageListViewAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	// 图片效果
	private List<GridViewBean> list;
	private String modes[];
	// 原图
	private Bitmap bitmap;
	private Bitmap dealBitmap;
	private Bitmap littleBitmap;
	private Handler handler;

	public EditImageListViewAdapter(Context context, String[] modes,
			List<GridViewBean> list, Handler handler) {
		this.bitmap = bitmap;
		this.modes = modes;
		this.list = list;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.handler = handler;
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
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.edit_image_listview_item, parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.edit_image_content);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.edit_image_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		GridViewBean gridViewBean = list.get(position);
		// dealBitmaps(position);

		viewHolder.imageView.setImageBitmap(gridViewBean.getBitmap());
		viewHolder.textView.setText(modes[gridViewBean.getModeId()]);

		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
	}

	public Bitmap dealBitmaps(int position, Bitmap bitmap) {
		Bitmap tempBitmap = null;
		switch (position) {
		case 0:
			// 原始
			tempBitmap = this.bitmap;
			break;
		case 1:
			// 模糊
			tempBitmap = BitmapUtil.getBlurImage(bitmap);
			break;
		case 2:
			// 素描
			tempBitmap = BitmapUtil.getSketchImage(bitmap);
			break;
		case 3:
			// 光照
			tempBitmap = BitmapUtil.getSunshineImage(bitmap, bitmap.getWidth()
					- bitmap.getWidth() / 4,
					bitmap.getHeight() - bitmap.getHeight() / 4);
			break;
		case 4:
			// 锐化
			tempBitmap = BitmapUtil.getSharpenImageAmeliorate(bitmap);
			break;
		case 5:
			// 浮雕
			tempBitmap = BitmapUtil.getSmbossImage(bitmap);
			break;
		case 6:
			// 柔化
			tempBitmap = BitmapUtil.getBlurImageAmeliorate(bitmap);
			break;
		case 7:
			// 怀旧
			tempBitmap = BitmapUtil.getOldRemeberImage(bitmap);
			break;
		case 8:
			// 水平镜像
			tempBitmap = BitmapUtil.getReverseBitmap(bitmap, 0);
			break;
		case 9:
			// 负片
			tempBitmap = BitmapUtil.getFilmImage(bitmap);
			break;
		case 10:
			// 倒影
			tempBitmap = BitmapUtil.getReflectionImageWithOrigin(bitmap);
			break;
		case 11:
			// 圆角
			tempBitmap = BitmapUtil.getRoundedCornerBitmap(bitmap, 22);
			break;
		case 12:
			// 黑白
			tempBitmap = BitmapUtil.getBlackAndWhiteImage(bitmap);
			break;
		case 13:
			// 垂直镜像
			tempBitmap = BitmapUtil.getReverseBitmap(bitmap, 1);
			break;
		case 14:
			// 灰度
			tempBitmap = BitmapUtil.getGrayImage(bitmap);
			break;
		case 15:
			// 彩画
			tempBitmap = new IceFilter(bitmap).imageProcess().getDstBitmap();
			break;
		case 16:
			// 美白
			tempBitmap = new SoftGlowFilter(bitmap, 10, 0.1f, 0.1f)
					.imageProcess().getDstBitmap();
			break;

		}

		return tempBitmap;
	}

	public Bitmap getArtwork() {
		return bitmap;
	}
}
