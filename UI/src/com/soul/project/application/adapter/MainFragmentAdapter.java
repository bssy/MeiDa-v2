package com.soul.project.application.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 创建时间�?013-5-18 下午5:28:40  
 * 项目名称：SmartSchool  
 * @author Linhj  
 * @version V1.0   
 * @JKD JDK 1.6.0_21  
 * @filename MainFragmentAdapter.java  
 * @Description�? 
 * @copyright Copyright (c) 2013 So5ul,Inc. All Rights Reserved.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter{

	private Context context;
	private List<Fragment> fragments;
	private int mCount = 0;
	
	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	/**
	 * 重写本类的构造函数，使在实例化本类的时�?把fragmanager作为参数导入
	 * @param context
	 * @param fm
	 * @param fragments
	 */
	public MainFragmentAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.context = context;
		this.fragments = fragments;
		this.mCount = fragments.size();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return this.mCount;
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			this.mCount = count;
			// notifyDataSetChanged()可以在修改�?配器绑定的数组后，不用重新刷新Activity，�?知Activity更新ListView
			notifyDataSetChanged();
		}
	}
}
