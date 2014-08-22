package com.xiyou.apps.lookpan.ui.fragment.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import widget.MyFragmentPagerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.entity.NewsInfo;
import cn.precious.metal.services.NewsService;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.adapter.GNewsAdapter;
import com.xiyou.apps.lookpan.base.BaseFragment;
import com.xiyou.apps.lookpan.pulltorefresh.PullToRefreshBase;
import com.xiyou.apps.lookpan.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiyou.apps.lookpan.pulltorefresh.PullToRefreshListView;
import com.xiyou.apps.lookpan.tools.ExcepUtils;
import com.xiyou.apps.lookpan.ui.ShowNewsActivity;

public class GlobalNewsFragment extends BaseFragment implements
		OnRefreshListener<ListView> {
	public static final String TAG = "GlobalNewsFragment";

	private ViewPager mViewPager;

	private GlobalFragmentAdapter mPagerAdapter;

	private List<Fragment> fragments  = new ArrayList<Fragment>();

	private PullToRefreshListView mPullRefreshListView;

	private ListView listView;

	private GNewsAdapter mGNewsAdapter;

	private List<NewsInfo> infos;
	
	private List<NewsInfo> topInfos;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_global_news, null);
		initView(view);
		return view;
	}

	
	public void initView(View view) {
		View heandView = LayoutInflater.from(getActivity()).inflate(
				R.layout.view_heand, null);
		mViewPager = (ViewPager) heandView.findViewById(R.id.view_pager);
		mPagerAdapter = new GlobalFragmentAdapter(getChildFragmentManager(),
				TAG,fragments);
		mViewPager.setAdapter(mPagerAdapter);
		
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.list_view);
		mPullRefreshListView.setOnRefreshListener(this);

		listView = mPullRefreshListView.getRefreshableView();
		listView.addHeaderView(heandView);

		mGNewsAdapter = new GNewsAdapter(getActivity(), infos);
		listView.setAdapter(mGNewsAdapter);

		long currentTime = System.currentTimeMillis();
		mPullRefreshListView.setLastUpdatedLabel("" + currentTime);
		AppSetting.getInstance(getActivity()).setGloalNewsRefreshTime(
				currentTime);
		mPullRefreshListView.doPullRefreshing(false, 20);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				NewsInfo newsInfo = mGNewsAdapter.getItem(arg2 - 1);
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

	}

	class GlobalFragmentAdapter extends MyFragmentPagerAdapter {

		private List<Fragment> items = new ArrayList<Fragment>() ;
		
		public GlobalFragmentAdapter(FragmentManager fm, String uniqueFlag,List<Fragment> list) {
			super(fm, uniqueFlag);
			// TODO Auto-generated constructor stub
			if(list != null) {
				items.addAll(list) ;
			}
		}
		
		
		public void setItems(List<Fragment> list){
			items.clear();
			if(list != null){
				items.addAll(list) ;
			}
			notifyDataSetChanged();
		}

		
		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return items.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.size();
		}
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
				infos = service.headLineNews();
				topInfos = service.topHeadLineNews() ;
				if(topInfos != null && topInfos.size() > 4) {
					topInfos = topInfos.subList(0, 4);
				}
				
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
				
				
				fragments.clear();
				if(topInfos != null && ! topInfos.isEmpty()) {
					NewsInfo infos = null ;
					for (int i = 0  ; i < topInfos.size() ; i++ ) {
						infos = topInfos.get(i) ;
						String mUrl = infos.getFile_managed_file_usage_uri() ;
//						if (mUrl != null) {
//							mUrl = mUrl.replaceAll("http://\\w+.wallstreetcn.com",
//									"http://thumbnail.wallstreetcn.com/thumb");
//							mUrl = mUrl.replaceAll(".jpg", ",c_fill,h_200,w_300.jpg");
//						}
						fragments.add(GSlideFragment.newInstance(""+infos.getNid(), infos.getNode_title(), mUrl,i));
					}
				}
				mPagerAdapter.setItems(fragments);
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
