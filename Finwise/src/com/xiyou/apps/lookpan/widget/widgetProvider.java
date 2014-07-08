package com.xiyou.apps.lookpan.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.xiyou.apps.lookpan.MainActivity;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.utils.Util;

public class widgetProvider extends AppWidgetProvider {
	private static RemoteViews rv;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}

	}

	public static void updateAppWidget(Context context,
			AppWidgetManager appWidgeManger, int appWidgetId) {
		rv = new RemoteViews(context.getPackageName(), R.layout.widget);

		rv.setTextViewText(R.id.textView3, Util.getDate("MM-dd HH:MM:ss"));
		// util.index = settings.getInt("imageState", 0);
		// mEditText.setText(settings.getString("heart", ""));

		Intent intentClick = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intentClick, 0);
		rv.setOnClickPendingIntent(R.id.RelativeLayout1, pendingIntent);
		appWidgeManger.updateAppWidget(appWidgetId, rv);
	}
}
