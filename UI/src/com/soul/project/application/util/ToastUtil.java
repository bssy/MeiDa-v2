package com.soul.project.application.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.soul.project.story.activity.R;

public class ToastUtil
{
//	enum TYPE
//	{
//		ERROR,WARN,INFO
//	}
	/**������ʽ**/
	public static final int ERROR = 1;
	/**������ʽ**/
	public static final int WARN  = 2;
	/**��ͨ��Ϣ��ʽ**/
	public static final int INFO  = 3;
	/**������ʽ**/
	public static final int LOAD  = 4;
	
	/**
	 * Toast��ʾ
	 * @param context
	 * @param str	��ʾ���ַ�
	 * @param typeOfToastShow	Toast���� ---> ERROR��WARN��INFO��LOAD
	 */
	public static void show(Context context,String str,int typeOfToastShow)
	{
		Toast toast = new Toast(context);
		View view = getView(typeOfToastShow, context , str);
		toast.setView(view);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
	
	private static View getView(int type,Context context,String str)
	{
		View view = null;
		ImageView img = null;
		TextView txt = null;
		switch(type)
		{
			case ERROR:
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_error);
				break;
			case WARN :
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_warn);
				break;
			case LOAD :
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_load);
				break;
			default :
				view = View.inflate(context, R.layout.cmp_rich_toast, null);
				img = (ImageView)view.findViewById(R.id.toast_img);
				txt = (TextView)view.findViewById(R.id.toast_txt);
				txt.setText(str);
				img.setBackgroundResource(R.drawable.toast_info);
		}
		return view;
	}

	/**
	 * ���Կ�����ʾʱ���Toast
	 * @param context
	 * @param str ��ʾ���ַ�
	 * @param typeOfToastShow	Toast���� ---> ERROR��WARN��INFO��LOAD
	 * @param showTime		��ʾʱ��
	 */
	public static void show(Context context,String str,int typeOfToastShow,int showTime)
	{
		Toast toast = new Toast(context);
		View view = getView(typeOfToastShow, context , str);
		toast.setView(view);
		toast.setGravity(Gravity.BOTTOM, 0, 100);
		toast.setDuration(showTime);
		toast.show();
	}
}
