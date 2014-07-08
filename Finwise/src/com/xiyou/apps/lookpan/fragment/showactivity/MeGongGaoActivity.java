package com.xiyou.apps.lookpan.fragment.showactivity;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.model.MingJiaBean;
import com.xiyou.apps.lookpan.utils.JsonUtil;

public class MeGongGaoActivity extends BaseActivity implements
		OnRefreshListener {

	private PullToRefreshLayout mPullToRefreshLayout;
	private ListView mListView;
	private ImageView mLoadErrorView;
	private LinearLayout mLoadingProgress;
	private RelativeLayout mTopLayoutView;
	private String URL = "http://www.06866.com/api/gonggaos";
	private myadapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_1);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.title_shang));
		mActionBar.setTitle("公告");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
		mListView = (ListView) mPullToRefreshLayout
				.findViewById(android.R.id.list);
		// Now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(this)
		// Mark All Children as pullable
				.allChildrenArePullable()
				// Set the OnRefreshListener
				.listener(this)
				// Finally commit the setup to our PullToRefreshLayout
				.setup(mPullToRefreshLayout);

		mLoadErrorView = (ImageView) findViewById(R.id.load_error);
		mLoadingProgress = (LinearLayout) findViewById(R.id.loading_progress);
		mTopLayoutView = (RelativeLayout) findViewById(R.id.top_layout);
		mAdapter = new myadapter(this);
		mListView.setAdapter(mAdapter);
		onLoadData();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MingJiaBean mingJiaBean = (MingJiaBean) parent.getAdapter()
						.getItem(position);
				Intent i = new Intent();
				i.putExtra("id", mingJiaBean.getArticle_id());
				i.setClass(MeGongGaoActivity.this,
						ShowGongGaoInfoActivity.class);
				startActivity(i);
			}

		});
	}

	@Override
	public void onRefreshStarted(View view) {
		// TODO Auto-generated method stub
		onLoadData();
	}

	private void onLoadData() {

		mLoadingProgress.setVisibility(View.VISIBLE);
		mLoadErrorView.setVisibility(View.GONE);
		mListView.setVisibility(View.GONE);
		new AsyncTask<Object, Object, ArrayList<MingJiaBean>>() {
			@Override
			protected ArrayList<MingJiaBean> doInBackground(Object... params) {
				return JsonUtil.getMingJiaBean2JSON(URL);
			}

			protected void onPostExecute(java.util.ArrayList<MingJiaBean> result) {
				mLoadingProgress.setVisibility(View.GONE);
				if (null != result) {
					mListView.setVisibility(View.VISIBLE);
					mAdapter.clear();
					mAdapter.addAll(result);
					mListView.setAdapter(mAdapter);
					mPullToRefreshLayout.setRefreshComplete();
				} else {
					mListView.setVisibility(View.GONE);
					mLoadErrorView.setVisibility(View.VISIBLE);
					mTopLayoutView.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							onLoadData();
						}
					});
				}
			};

		}.execute();

	}

	class myadapter extends ArrayAdapter<MingJiaBean> {

		public myadapter(Context context) {
			super(context, 0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHolder holder;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.adapter_item_gonggao, parent, false);
				holder = new ViewHolder();
				holder.article_title = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.time = (TextView) convertView
						.findViewById(R.id.textView2);
				holder.author = (TextView) convertView
						.findViewById(R.id.textView3);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.article_title.setText(getItem(position).getArticle_title());
			holder.time.setText(getItem(position).getArticle_date());
			holder.author.setText(getItem(position).getAuthor());
			return convertView;
		}
	}

	class ViewHolder {
		TextView article_title;
		TextView author;
		TextView time;
	}
}
