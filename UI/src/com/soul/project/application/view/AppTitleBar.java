/**
 * 
 */
package com.soul.project.application.view;

import com.soul.project.application.util.Activity2Activity;
import com.soul.project.story.activity.MainActivity;
import com.soul.project.story.activity.MyImageActivity;
import com.soul.project.story.activity.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 应用统一风格标题栏
 * @file AppTitleBar.java
 * @author 许仕永(xsy)
 * @package_name com.soul.project.application.view
 * @todo  TODO
 * @date 2015-11-2 下午5:11:58
 */
public class AppTitleBar extends RelativeLayout
{
	private LayoutInflater inflater;
	private TextView leftView;
	private TextView rightView;
	private TextView centerView;
	private Context context;
	
	public AppTitleBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		init(context,attrs,0);
	}
	
	public AppTitleBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs);
		this.context = context;
		init(context,attrs,defStyle);
	}


	private void init(Context context, AttributeSet attrs, int defStyle)
	{
		// TODO Auto-generated method stub
		inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.titlebar, null);
		this.addView(view);
		initView(view);
		
		initValue(context,attrs,defStyle);
		initEvent();
	}


	private void initEvent()
	{
		// TODO Auto-generated method stub
		leftView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				((Activity)context).finish();
			}
		});
		
		rightView.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Activity2Activity.gotoNewActivity(context, MainActivity.class);
			}
		});
	}

	private void initView(View view)
	{
		// TODO Auto-generated method stub
		leftView = (TextView)view.findViewById(R.id.include_view_btnLeft);
		rightView = (TextView)view.findViewById(R.id.include_view_btnRight);
		centerView = (TextView)view.findViewById(R.id.include_view_titlebar_text);
	}


	private void initValue(Context context, AttributeSet attrs, int defStyle)
	{
		// TODO Auto-generated method stub
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.apptitlebar);//.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);
		
		int leftIcon = a.getResourceId(R.styleable.apptitlebar_leftIcon, -1);
		String leftString  = a.getString(R.styleable.apptitlebar_leftString);
		int leftStringColor = a.getColor(R.styleable.apptitlebar_leftStringColor , -1);
		
		int rightIcon = a.getResourceId(R.styleable.apptitlebar_rightIcon, -1);
		String rightString = a.getString(R.styleable.apptitlebar_rightString);
		int rightStringColor = a.getColor(R.styleable.apptitlebar_rightStringColor, -1);

		String titlebarString = a.getString(R.styleable.apptitlebar_titlebarString);
		int titlebarStringColor = a.getColor(R.styleable.apptitlebar_titlebarStringColor, -1);
		
		
		if(leftIcon != -1)
			leftView.setBackgroundResource(leftIcon);
		if(leftString != null)
			leftView.setText(leftString);
		if(leftStringColor != -1)
			leftView.setTextColor(leftStringColor);
		
		if(rightIcon != -1)
			rightView.setBackgroundResource(rightIcon);
		if(rightString != null)
			rightView.setText(rightString);
		if(rightStringColor != -1)
			rightView.setTextColor(rightStringColor);
		
		if(titlebarString != null)
			centerView.setText(titlebarString);
		if(titlebarStringColor != -1)
			centerView.setTextColor(titlebarStringColor);
		
		a.recycle();

	}
	
	
	/****
	 * 下列方法是直接用對象調用來設置的
	 * @author 许仕永(xsy)
	 * des: 
	 * @param title
	 */
	public void setRightViewOnClickEvent(OnClickListener clickListener)
	{
		rightView.setOnClickListener(clickListener);
	}
	
	public void setTitle(String title)
	{
		centerView.setText(title);
	}
	
	public void setTitleColor(int color)
	{
		centerView.setTextColor(color);
	}

	public void setLeftString(String leftString)
	{
		leftView.setText(leftString);
	}
	
	public void setrightString(String rightString)
	{
		rightView.setText(rightString);
	}

}
