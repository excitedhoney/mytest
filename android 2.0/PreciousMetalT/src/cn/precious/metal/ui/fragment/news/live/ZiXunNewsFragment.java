package cn.precious.metal.ui.fragment.news.live;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.pulltorefresh.PullToRefreshBase;
import cn.precious.metal.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.precious.metal.pulltorefresh.PullToRefreshListView;
import cn.precious.metal.services.NewsService;
import cn.precious.metal.tools.ExcepUtils;

public class ZiXunNewsFragment extends BaseFragment implements
		OnRefreshListener<ListView> {
	public static final String TAG = "ZiXunNewsFragment";

	private PullToRefreshListView mPullRefreshListView;

	private ListView listView;

	private LiveAdapter mLiveAdapter;
	private LayoutInflater mLayoutInflater;

	private List<cn.precious.metal.entity.LivesBean> infos;

	SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.getDefault());

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_global_news, null);

		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mLayoutInflater = getActivity().getLayoutInflater();
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

		mLiveAdapter = new LiveAdapter(getActivity());
		listView.setAdapter(mLiveAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						ShowLiveActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				cn.precious.metal.entity.LivesBean mLivesBean = (cn.precious.metal.entity.LivesBean) arg0
						.getAdapter().getItem(arg2);
				Bundle bundle = new Bundle();
				Date compare = new Date(Long.parseLong(mLivesBean
						.getNode_created()) * 1000l);
				
				
				bundle.putString("title", mLivesBean.getNode_title());
				bundle.putString("node_icon", mLivesBean.getNode_icon() + "");
				bundle.putString("time", time.format(compare) + "");
				bundle.putString("content", mLivesBean.getNode_content() + "");
				bundle.putString("laiyuan", mLivesBean.getSource() + "");
				bundle.putString("lianjie", mLivesBean.getSourcelink() + "");
				intent.putExtras(bundle);
				startActivity(intent);
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
				infos = service.LiveNews();
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
				mLiveAdapter.addAll(infos);
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

	class LiveAdapter extends ArrayAdapter<cn.precious.metal.entity.LivesBean> {

		public LiveAdapter(Context context) {
			super(context, 0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				convertView = mLayoutInflater.inflate(
						R.layout.adapter_item_live, parent, false);
				holder = new ViewHolder();
				holder.live_time = (TextView) convertView
						.findViewById(R.id.live_time);
				holder.live_icon = (ImageView) convertView
						.findViewById(R.id.node_icon);
				holder.live_content = (TextView) convertView
						.findViewById(R.id.live_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (getItem(position).getNode_icon().equals("折线")) {
				holder.live_icon.setImageResource(R.drawable.live_zhexian);
			} else if (getItem(position).getNode_icon().equals("柱状")) {
				holder.live_icon.setImageResource(R.drawable.cheng);
			} else if (getItem(position).getNode_icon().equals("提醒")) {
				holder.live_icon.setImageResource(R.drawable.live_tixing);
			} else if (getItem(position).getNode_icon().equals("传言")) {
				holder.live_icon.setImageResource(R.drawable.hong);
			} else if (getItem(position).getNode_icon().equals("警告")) {
				holder.live_icon.setImageResource(R.drawable.live_zhongbang);
			} else {
				holder.live_icon.setImageResource(R.drawable.lan);
			}
			if (getItem(position).getNode_color().equals("红色")) {

				holder.live_content.setTextColor(getActivity().getResources()
						.getColor(R.color.red));
			} else {
				holder.live_content.setTextColor(getActivity().getResources()
						.getColor(R.color.white));
			}
			if (getItem(position).getNode_format().equals("加粗")) {
				holder.live_content.setTypeface(Typeface
						.defaultFromStyle(Typeface.BOLD));
			} else {
				holder.live_content.setTypeface(Typeface
						.defaultFromStyle(Typeface.NORMAL));
			}

			holder.live_content.setText(Util.filterHtml_new(getItem(position)
					.getNode_content()));
			Date compare = new Date(Long.parseLong(getItem(position)
					.getNode_created()) * 1000l);
			holder.live_time.setText(time.format(compare));

			return convertView;
		}

	}

	private static class ViewHolder {
		ImageView live_icon;
		TextView live_time;
		TextView live_content;
	}
}