package cn.precious.metal.ui.fragment;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.precious.metal.R;
import cn.precious.metal.adapter.StrategyAdapter;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.Strategy;
import cn.precious.metal.pulltorefresh.PullToRefreshBase;
import cn.precious.metal.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.precious.metal.pulltorefresh.PullToRefreshListView;
import cn.precious.metal.services.StrategyService;
import cn.precious.metal.tools.ExcepUtils;
import cn.precious.metal.ui.DetailStrategyActivity;

public class StrategyFragment extends BaseFragment implements OnClickListener {

	private PullToRefreshListView refreshListView;

	private ListView listView;

	private StrategyAdapter adapter;

	private List<Strategy> lists;

	private int page = 1;

	private ImageLoader mImageLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_startegy, null);
		initView(view);
		initListener();
		return view;
	}

	public void initView(View view) {
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		listView = refreshListView.getRefreshableView();

		initImageLoader(getActivity());

		adapter = new StrategyAdapter(getActivity(), lists, mImageLoader);
		listView.setAdapter(adapter);
	}
 
	
	@Override 
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			MemoryCacheAware<String, Bitmap> cache = mImageLoader
					.getMemoryCache();
			Collection<String> c = cache.keys();
			Iterator<String> it = c.iterator();
			Bitmap bitmap = null;
			while (it.hasNext()) {
				bitmap = cache.get(it.next());
				bitmap.recycle();
				System.gc();
			}
			cache.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void initImageLoader(Context cont) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				cont).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()
				.build();

		mImageLoader = ImageLoader.getInstance();
		mImageLoader.init(config);

	}

	public void initListener() {
		// Set a listener to be invoked when the list should be refreshed.
		refreshListView.setPullLoadEnabled(true);
		refreshListView.setScrollLoadEnabled(false);
		refreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				refreshListView.setLastUpdatedLabel(sdf.format(new Date(
						AppSetting.getInstance(getActivity())
								.getStrategyRefreshTime())));
				AppSetting.getInstance(getActivity()).setStrategyRefreshTime(
						System.currentTimeMillis());

				new StrategyTask(true, page).execute();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if (lists != null && lists.size() > 0) {
					// 加载下一页
					new StrategyTask(false, page).execute();
				}
			}
		});
		refreshListView.setLastUpdatedLabel(sdf.format(new Date(AppSetting
				.getInstance(getActivity()).getStrategyRefreshTime())));
		AppSetting.getInstance(getActivity()).setStrategyRefreshTime(
				System.currentTimeMillis());

		refreshListView.doPullRefreshing(false, 30);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Strategy stra = adapter.getItem(arg2);
				if (stra != null) {
					Intent intent = new Intent(getActivity(),
							DetailStrategyActivity.class);
					intent.putExtra("id", stra.getArticle_id());
					intent.putExtra("image_url", stra.getImage_url());
					intent.putExtra("title", stra.getArticle_title());
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	class StrategyTask extends AsyncTask<String, Void, String> {

		private boolean isClear;

		public StrategyTask(boolean isClear, int page) {
			this.isClear = isClear;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (isClear)
				page = 1;

			StrategyService service = new StrategyService(getActivity());
			try {
				lists = service.getStrategys("" + page);
				if (lists != null && !lists.isEmpty()) {
					page++;
					Log.i("tag", lists.toString());
					return "success";
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
				return "error";
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			refreshListView.onPullDownRefreshComplete();
			refreshListView.onPullUpRefreshComplete();
			if ("success".equals(result)) {
				adapter.setItems(lists, isClear);
			}

		}
	}

}
