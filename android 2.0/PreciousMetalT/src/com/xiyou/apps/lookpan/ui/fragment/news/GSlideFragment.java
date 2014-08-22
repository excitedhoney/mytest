package com.xiyou.apps.lookpan.ui.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.precious.metal.utils.Utils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseFragment;
import com.xiyou.apps.lookpan.tools.AndroidUtils;
import com.xiyou.apps.lookpan.ui.ShowNewsActivity;

public class GSlideFragment extends BaseFragment {

	private TextView title;
	private ImageView imageView;
	private TextView textView1;
	private ImageLoader mImageLoader;

	public static GSlideFragment newInstance(String nid, String node_title,
			String file_managed_file_usage_uri, int i) {
		GSlideFragment fragment = new GSlideFragment();
		Bundle b = new Bundle();
		b.putString("nid", nid);
		b.putString("node_title", node_title);
		b.putString("file_managed_file_usage_uri", file_managed_file_usage_uri);
		b.putInt("num", i);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.global_slide_topview, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		title = (TextView) view.findViewById(R.id.title);
		imageView = (ImageView) view.findViewById(R.id.imageView);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		mImageLoader = ImageLoader.getInstance();
		textView1.setText(getArguments().getInt("num") + 1 + "/4");
		title.setText(getArguments().getString("node_title"));

		String url = getArguments().getString("file_managed_file_usage_uri");

		if (url != null) {
			url = url.replaceAll("http://\\w+.wallstreetcn.com",
					"http://thumbnail.wallstreetcn.com/thumb");
			url = url.replaceAll(".jpg",
					",c_fill,h_" + Utils.dip2px(getActivity(), 200f) + ",w_"
							+ Utils.getWindowWidth(getActivity()) + ".jpg");
		}

		mImageLoader.displayImage(url, imageView,
				AndroidUtils.getImageLoaderOptions());

		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						ShowNewsActivity.class);
				intent.putExtra("id", "" + getArguments().getString("nid"));
				intent.putExtra("title", getArguments().getString("node_title"));
				intent.putExtra("url",
						getArguments().getString("file_managed_file_usage_uri"));
				startActivity(intent);
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
