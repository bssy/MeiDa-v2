package com.soul.project.application.fragment;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.soul.project.application.adapter.HomeGridViewAdapter;
import com.soul.project.application.data.dummy.HomeGridView;
import com.soul.project.application.util.AndroidTools;
import com.soul.project.application.util.LogUtil;
import com.soul.project.application.util.ToastUtil;
import com.soul.project.story.activity.R;
import com.zhend.Slait;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment
{

	private static final String tag = "HomeFragment";
	private Context context;
	private GridView gridView;
	private HomeGridViewAdapter homeGridViewAdapter;
	private ArrayList<HomeGridView> list;
	private Animation animation;
	private Slait sckoel;


	@SuppressLint("ValidFragment")
	public HomeFragment(Context context, ArrayList<HomeGridView> list, Slait sckoel)
	{
		super();
		this.context = context;
		this.list = list;
		this.sckoel = sckoel;
	}


	@Override
	public View getView()
	{
		return super.getView();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragmen_home, container, false);
		gridView = (GridView) view.findViewById(R.id.girdViewHome);
		homeGridViewAdapter = new HomeGridViewAdapter(getActivity(), list);
		gridView.setAdapter(homeGridViewAdapter);

		gridView.setNumColumns(2);
		gridView.setGravity(Gravity.CENTER);

		int height = AndroidTools.getWindowsHeight(this.context);
		float scale = context.getResources().getDisplayMetrics().density;
		int width = AndroidTools.getWindowsWidth(this.context) - (int) (65 * scale + 0.5f * (65 >= 0 ? 1 : -1));
		int w = (width - ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 8)) / 2;
		int i = height - w * 4 - ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 13);
		System.out.println("i:" + i);

		if( (i < 20) && (i >= 0) )
		{
			w = 20 - i / 4;
		} else if( i < 0 )
		{
			w = w + i / 4 - (int) (10 * scale + 0.5f * (10 >= 0 ? 1 : -1));
			i = height - w * 4 - ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 13);
			w = width - ((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 4 + w * 2);
			gridView.setPadding(w / 2, i / 2, (int) (65 * scale + 0.5f * (65 >= 0 ? 1 : -1)) + w / 2, i / 2);
		} else
		{
			gridView.setPadding((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 2, i / 2, (int) (65 * scale + 0.5f * (65 >= 0 ? 1 : -1))
					+ (int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 2, i / 2);
		}
		Log.i("i:" + i, "bar.getH():" + (int) (65 * scale + 0.5f * (65 >= 0 ? 1 : -1)));

		gridView.setHorizontalSpacing((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 2);
		gridView.setVerticalSpacing((int) (5 * scale + 0.5f * (5 >= 0 ? 1 : -1)) * 2);
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, final int position, long id)
			{
				try
				{
					if( null == list.get(position).getClassName() )
					{
						ToastUtil.show(context, "模块加载出错", ToastUtil.ERROR);
					}
					else
					{
						animation = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_scale);
						animation.setAnimationListener(new AnimationListener()
						{
							@Override
							public void onAnimationStart(Animation animation)
							{
								// TODO Auto-generated method stub

							}


							@Override
							public void onAnimationRepeat(Animation animation)
							{
								// TODO Auto-generated method stub

							}


							@Override
							public void onAnimationEnd(Animation animation)
							{
								// TODO Auto-generated method stub
								Intent intent = null;
								try
								{
									intent = new Intent(getActivity(), Class.forName(list.get(position).getClassName().toString()));
									intent.putExtra("type", list.get(position).getType());
									startActivity(intent);
								}
								catch (ClassNotFoundException e)
								{
									ToastUtil.show(context, "模块加载出错", ToastUtil.ERROR);
								}
							}
						});
						view.startAnimation(animation);
					}
				}
				catch (Exception e)
				{
					LogUtil.i(tag, e.toString());
					ToastUtil.show(context, "模块加载出错", ToastUtil.ERROR);
				}
			}
		});

		return view;
	}


	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}
}
