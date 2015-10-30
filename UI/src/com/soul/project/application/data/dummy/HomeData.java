package com.soul.project.application.data.dummy;

import java.util.ArrayList;
import com.soul.project.story.activity.R;

public class HomeData
{

	public static ArrayList<HomeGridView> getFunction4Teacher()
	{

		ArrayList<HomeGridView> list = new ArrayList<HomeGridView>();

		String[] array = new String[]{"美女","美男","美景","论坛","我的图片","健身计步器"};
		int[] iconIds = new int[]{R.drawable.icon_women,R.drawable.icon_man,R.drawable.icon_mountain,R.drawable.icon_chat,R.drawable.tab_book,R.drawable.icon_counter};

		for ( int i = 0; i < array.length; i++ )
		{
			HomeGridView g10 = new HomeGridView();
			g10.setName(array[i]);
			g10.setClassName("com.soul.project.story.activity.GridviewActivity");
			if(i < iconIds.length)
				g10.setIcoId(iconIds[i]);
			else 
				g10.setIcoId(R.drawable.tab_book);
			g10.setType(i+1);
			list.add(g10);
		}
		
		
		return list;
	}
}
