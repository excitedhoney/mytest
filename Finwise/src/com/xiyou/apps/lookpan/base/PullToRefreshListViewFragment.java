package com.xiyou.apps.lookpan.base;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xiyou.apps.lookpan.R;

public abstract class PullToRefreshListViewFragment<T> extends BaseFragment
		implements OnItemClickListener, OnRefreshListener {

	protected PullToRefreshLayout mPullToRefreshLayout;
	protected ListView mListView;
	protected ArrayAdapter<T> mAdapter;
	protected int mPage;
	protected ImageView mLoadErrorView;
	protected LinearLayout mLoadingProgress;
	protected RelativeLayout mTopLayoutView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("tag", "Child====onCreateView");
		return LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_1,
				null, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mPullToRefreshLayout = (PullToRefreshLayout) view
				.findViewById(R.id.ptr_layout);
		mListView = (ListView) mPullToRefreshLayout
				.findViewById(android.R.id.list);
		// Now setup the PullToRefreshLayout
		ActionBarPullToRefresh.from(getActivity())
		// Mark All Children as pullable
				.allChildrenArePullable()
				// Set the OnRefreshListener
				.listener(this)
				// Finally commit the setup to our PullToRefreshLayout
				.setup(mPullToRefreshLayout);

		mLoadErrorView = (ImageView) view.findViewById(R.id.load_error);
		mLoadingProgress = (LinearLayout) view
				.findViewById(R.id.loading_progress);
		mTopLayoutView = (RelativeLayout) view.findViewById(R.id.top_layout);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAdapter = new ArrayAdapter<T>(getActivity(), 0) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return onGetItemView(getItem(position), position, convertView,
						parent);
			}
		};
		mListView.setAdapter(mAdapter);
		onLoadData(true);
		mListView.setOnItemClickListener(this);

	}

	protected abstract void onLoadData(boolean isFirstPage);

	protected abstract View onGetItemView(T item, int position,
			View convertView, ViewGroup parent);

	@Override
	public void onRefreshStarted(View view) {
		onLoadData(true);
	}
}
