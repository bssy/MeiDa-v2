/**
 * 
 */
package com.soul.project.application.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.soul.project.application.util.AndroidTools;
import com.soul.project.story.activity.R;

/**
 * 创建时间：2013-5-22 下午10:38:02  
 * 项目名称：MainDemo  
 * @author Linhj  
 * @version V1.0   
 * @JKD JDK 1.6.0_21  
 * @filename MainToolsBar.java  
 * @Description：  
 * @copyright Copyright (c) 2013 Soul,Inc. All Rights Reserved.
 */
public class MainToolsBar extends RelativeLayout{

	/**
	 * @param context
	 * @param attrs
	 */
	private Context context;
	private ImageButton iBtnUser;
	private ImageButton iBtnHome;
	private ImageButton iBtnExit;
	private ImageButton iBtnSetting;
	
	public MainToolsBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	@SuppressLint("NewApi")
	private void init(){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toolbar_main, this);
		iBtnUser = (ImageButton)view.findViewById(R.id.imgbtn_toolsbar_user);
		iBtnHome = (ImageButton)view.findViewById(R.id.imgbtn_toolsbar_home);
		iBtnExit = (ImageButton)view.findViewById(R.id.imgbtn_toolsbar_exit);
		iBtnSetting = (ImageButton)view.findViewById(R.id.imgbtn_toolsbar_setting);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		int height = AndroidTools.getWindowsHeight(this.context);
		float scale = context.getResources().getDisplayMetrics().density;
		int width = AndroidTools.getWindowsWidth(this.context)
				- (int) (65 * scale + 0.5f * (65 >= 0 ? 1 : -1));
		int w = (width - ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 8)) / 2;
		int i = height - w * 4
				- ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 13);
		System.out.println("i:" + i);

		if ((i < 20) && (i >= 0)) {
			w = 20 - i / 4;
		} else if (i < 0) {
			w = w + i / 4 - (int) (10 * scale + 0.5f * (10 >= 0 ? 1 : -1));
			i = height - w * 4
					- ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 13);
			w = width
					- ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 4 + w * 2);
			setPadding(0, i / 2 + (int) (5 * scale + 0.5f * (5 >= 0 ? 1
					: -1)) * 2,
					0 ,
					i / 2);
		} else {
			setPadding(
					0, i / 2 + (int) (5 * scale + 0.5f * (5 >= 0 ? 1
							: -1)) * 2,0,
					i / 2);
		}
	}
}
