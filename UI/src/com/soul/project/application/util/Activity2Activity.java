package com.soul.project.application.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.soul.project.story.activity.R;

public class Activity2Activity {

	public static void gotoNewActivity(Context context,Class classs)
	{
		Intent intent = new Intent(context, classs);
		((Activity)context).overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
		context.startActivity(intent);
	}
}
