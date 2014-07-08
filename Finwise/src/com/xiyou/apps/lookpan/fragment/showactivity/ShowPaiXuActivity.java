package com.xiyou.apps.lookpan.fragment.showactivity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.db.BaseDataBase;
import com.xiyou.apps.lookpan.listview.DragSortListView;
import com.xiyou.apps.lookpan.listview.EditOptionalAdapter;
import com.xiyou.apps.lookpan.utils.Util;

public class ShowPaiXuActivity extends Activity {

	private EditOptionalAdapter mAdapter;
	private com.xiyou.apps.lookpan.listview.DragSortListView mDragSortListView;
	private ArrayList<String> lists;
	protected ActionBar mActionBar;
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String item = mAdapter.getItem(from);
				mAdapter.remove(item);
				mAdapter.insert(item, to);
				mAdapter.updateCheck(from, to);
				mAdapter.notifyDataSetChanged();
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_paixu);
		mDragSortListView = (DragSortListView) findViewById(R.id.listView);
		try {
			lists = new ArrayList<String>();
			BaseDataBase.getInstance(this).open();
			ArrayList<String> content_list = BaseDataBase.getInstance(this)
					.selectZiXuanChannel();
			if (null != content_list && content_list.size() > 1) {
				for (String str : content_list) {
					lists.add(Util.getChineseName(str));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDataBase.getInstance(this).close();
		}

		mAdapter = new EditOptionalAdapter(this, lists);
		mDragSortListView.setAdapter(mAdapter);
		mDragSortListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mDragSortListView.setDropListener(onDrop);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setTitle("编辑排序");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.hangqing_paixu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			ArrayList<String> list = (ArrayList<String>) mAdapter.getItems();
			ArrayList<String> list2 = new ArrayList<String>();
			for (String str : list) {
				list2.add(Util.getEnglishName(str));
			}
			try {
				BaseDataBase.getInstance(ShowPaiXuActivity.this).open();
				BaseDataBase.getInstance(ShowPaiXuActivity.this)
						.DelectAllZiXuanChannel();
				BaseDataBase.getInstance(ShowPaiXuActivity.this)
						.inserZiXuanChannel(list2);
				Log.i("tag",
						"退出前做的数据库操作:"
								+ BaseDataBase
										.getInstance(ShowPaiXuActivity.this)
										.selectZiXuanChannel().toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				BaseDataBase.getInstance(ShowPaiXuActivity.this).close();
			}
			finish();

			return true;
		case R.id.action_item_share:
			Intent i = new Intent();
			i.setClass(ShowPaiXuActivity.this, ZiXuanAddActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
