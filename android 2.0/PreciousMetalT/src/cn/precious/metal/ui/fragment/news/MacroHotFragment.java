package cn.precious.metal.ui.fragment.news;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.precious.metal.R;
import cn.precious.metal.adapter.GNewsAdapter;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.NewsInfo;
import cn.precious.metal.pulltorefresh.PullToRefreshBase;
import cn.precious.metal.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.precious.metal.pulltorefresh.PullToRefreshListView;
import cn.precious.metal.services.NewsService;
import cn.precious.metal.tools.ExcepUtils;
import cn.precious.metal.ui.ShowNewsActivity;

/**
 * 宏观 热点
 * 
 * @author mac
 * 
 */
public class MacroHotFragment extends BaseFragment implements
		OnRefreshListener<ListView> {
	public static final String TAG = "MacroHotFragment";

	private PullToRefreshListView mPullRefreshListView;

	private ListView listView;

	private GNewsAdapter mGNewsAdapter;

	private List<NewsInfo> infos;

	private String url;

	public static MacroHotFragment getInstance(Bundle bundle) {
		MacroHotFragment mFragment = new MacroHotFragment();
		mFragment.setArguments(bundle);
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_global_news, null);
		initView(view);
		return view;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
	}

	public void initView(View view) {

		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.list_view);
		mPullRefreshListView.setOnRefreshListener(this);

		listView = mPullRefreshListView.getRefreshableView();

		mGNewsAdapter = new GNewsAdapter(getActivity(), infos);
		listView.setAdapter(mGNewsAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				NewsInfo newsInfo = mGNewsAdapter.getItem(arg2);
				if (newsInfo != null) {
					Intent intent = new Intent(getActivity(),
							ShowNewsActivity.class);
					intent.putExtra("id", "" + newsInfo.getNid());
					intent.putExtra("title", newsInfo.getNode_title());
					intent.putExtra("url",
							newsInfo.getFile_managed_file_usage_uri());
					startActivity(intent);
				}
			}
		});

		long currentTime = System.currentTimeMillis();
		mPullRefreshListView.setLastUpdatedLabel("" + currentTime);
		AppSetting.getInstance(getActivity()).setGloalNewsRefreshTime(
				currentTime);
		mPullRefreshListView.doPullRefreshing(false, 20);
	}

	class GetNewsTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			NewsService service = new NewsService(getActivity());

			try {
				infos = service.macroscopicHot(getArguments().getString("url"));
				if (infos != null && !infos.isEmpty())
					return SUCCESS;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
				return ERROR;
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			mPullRefreshListView.onPullDownRefreshComplete();

			if (SUCCESS.equalsIgnoreCase(result)) {
				mGNewsAdapter.setItems(infos);
			}
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		mPullRefreshListView.setLastUpdatedLabel(""
				+ sdf.format(new Date(AppSetting.getInstance(getActivity())
						.getGloalNewsRefreshTime())));
		new GetNewsTask().execute("");

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
	}
}
