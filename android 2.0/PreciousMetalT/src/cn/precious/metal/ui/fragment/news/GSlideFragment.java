package cn.precious.metal.ui.fragment.news;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.tools.AndroidUtils;
import cn.precious.metal.ui.ShowNewsActivity;
import cn.precious.metal.utils.Utils;

public class GSlideFragment extends BaseFragment {

	private TextView title;
	private ImageView imageView;
	
	
	private ImageLoader mImageLoader ;
	
	public static GSlideFragment newInstance(String nid , String node_title,String file_managed_file_usage_uri){
		GSlideFragment fragment = new GSlideFragment() ;
		Bundle b = new Bundle() ;
		b.putString("nid", nid);
		b.putString("node_title", node_title);
		b.putString("file_managed_file_usage_uri", file_managed_file_usage_uri);
		fragment.setArguments(b);
		return fragment ;
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
		mImageLoader = ImageLoader.getInstance() ;
		
		title.setText(getArguments().getString("node_title"));
		
		String url = getArguments().getString("file_managed_file_usage_uri") ;
		
		if (url != null) {
			url = url.replaceAll("http://\\w+.wallstreetcn.com",
					"http://thumbnail.wallstreetcn.com/thumb");
			url = url.replaceAll(".jpg", ",c_fill,h_" + Utils.dip2px(getActivity(), 200f) +",w_"+ Utils.getWindowWidth(getActivity())+".jpg");
		}
		
		
		mImageLoader.displayImage(url, imageView, AndroidUtils.getImageLoaderOptions());
		
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
