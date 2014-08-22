package com.xiyou.apps.lookpan.ui;

import widget.ObservableScrollView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.entity.DetailStrategy;
import cn.precious.metal.services.StrategyService;
import cn.precious.metal.utils.Utils;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;
import com.xiyou.apps.lookpan.listener.ScrollViewListener;
import com.xiyou.apps.lookpan.tools.ExcepUtils;

public class DetailStrategyActivity extends BaseActivity {

	private TextView title, artTilte, artAuthor, artTime ,artContent;
	
	private WebView webview ;

	private ImageView artImage;
	private ImageView artImageTwo;

	private ObservableScrollView scrollView;

	private DetailStrategy strategy;

	private ImageLoader mImageLoader;

	private View parentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentView = LayoutInflater.from(this).inflate(
				R.layout.activity_detail_strategy, null);
		setContentView(parentView);
		initImageLoader(this);
		initView();

		new DetStraTask().execute(new String[] { getIntent().getStringExtra(
				"id") });
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			MemoryCacheAware<String, Bitmap> cache = mImageLoader
					.getMemoryCache();
			cache.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void back(View view) {
		finish();
	}

	private int offsent = 0;
	private boolean isMove = false;

	public void initView() {
		title = (TextView) findViewById(R.id.title);
		artTilte = (TextView) findViewById(R.id.art_title);
		artAuthor = (TextView) findViewById(R.id.art_auther);
		artTime = (TextView) findViewById(R.id.art_time);
		artContent = (TextView) findViewById(R.id.art_content);
		
//		webview= (WebView) findViewById(R.id.web_view) ;

		artImage = (ImageView) findViewById(R.id.art_image);
		artImageTwo = (ImageView) findViewById(R.id.art_image_two);

		scrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
		title.setText(getIntent().getStringExtra("title"));

		offsent = Utils.dip2px(this, 130f);

		artImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// showFullImage(getIntent().getStringExtra("image_url"),
				// DetailStrategyActivity.this,arg0 );

				Intent intent = new Intent(DetailStrategyActivity.this,
						FullImageActivity.class);
				intent.putExtra("url", getIntent().getStringExtra("image_url"));
				startActivity(intent);
			}
		});

		artImageTwo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// showFullImage(getIntent().getStringExtra("image_url"),
				// DetailStrategyActivity.this,arg0 );

				Intent intent = new Intent(DetailStrategyActivity.this,
						FullImageActivity.class);
				intent.putExtra("url", getIntent().getStringExtra("image_url"));
				startActivity(intent);
			}
		});

		scrollView.setScrollViewListener(new ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x,
					int y, int oldx, int oldy) {
				// TODO Auto-generated method stub
				if (y >= oldy) { // 往上
					if (y <= offsent) {
						isMove = true;
					} else {
						isMove = false;
					}
				} else {
					if (oldy <= offsent) {
						isMove = true;
					} else {
						isMove = false;
					}
				}
				if (isMove) {
					artImage.setVisibility(View.VISIBLE);
					artImageTwo.setVisibility(View.GONE);

				} else {
					artImage.setVisibility(View.GONE);
					artImageTwo.setVisibility(View.VISIBLE);

				}

			}
		});

	}

	private DisplayImageOptions options;

	public void initOption() {
		options = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().build();
	}


	public void initContent() {
		if (strategy != null) {
			artTilte.setText(strategy.getArticle_title());
			artAuthor.setText(strategy.getAuthor());
			artTime.setText(strategy.getArticle_date());
			artContent.setText(Html.fromHtml(strategy.getArticle_content()));
			
			
//			LoadData(strategy.getArticle_content());
			initOption();

			mImageLoader.displayImage(getIntent().getStringExtra("image_url"),
					artImage, options);

			mImageLoader.displayImage(getIntent().getStringExtra("image_url"),
					artImageTwo, options);
		}
	}
	
	
//	private void LoadData(String body) {
//		
//		if(body == null || "".equals(body))
//			return ;
//		
//		
//		body = body.replaceAll("style=\"width: 700px; height: 420px;\"", "");
//		
//		StringBuilder sb = new StringBuilder();
//
//		if (!AppSetting.getInstance(this).isBigTextFont()) {
//			sb.append(API.img);
//		} else {
//			sb.append(API.imgBig);
//		}
//
//		sb.append(body);
//		sb.append("</br>"); // 网页底部留白
//		
//		webview.loadDataWithBaseURL(null,matchP(sb.toString()), "text/html", "utf-8",null);
//		
//	}
//	
//	
//	public String matchP (String s) {
//		if(s == null )
//			return "" ;
//		 Pattern p = Pattern.compile("<p[^>]+.*?>");
//	     Matcher m = p.matcher(s);
//	     while(m.find()){
//	    	String  find = m.group() ;
//	    	s = s.replace(find, "<p style='color:black;'>") ;
//	     }
//	     return  s; 
//	}
	
	
	

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

	// private View winView;
	//
	// private ImageView back ;
	//
	// private PopupWindow mPopupWindows;
	//
	// public void showFullImage(String imageUrl, Context cont, View view) {
	// winView = LayoutInflater.from(cont).inflate(R.layout.full_image_layout,
	// null);
	//
	// back = (ImageView) winView.findViewById(R.id.back_image) ;
	//
	//
	//
	// back.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // TODO Auto-generated method stub
	// mPopupWindows.dismiss();
	// winView = null;
	// System.gc();
	// }
	// });
	//
	// mPopupWindows = new PopupWindow(winView,
	// ViewGroup.LayoutParams.FILL_PARENT,
	// ViewGroup.LayoutParams.FILL_PARENT);
	// mPopupWindows.setFocusable(true);
	// mPopupWindows.setTouchable(true);
	// mPopupWindows.setBackgroundDrawable(new ColorDrawable(getResources()
	// .getColor(R.color.black)));
	// mPopupWindows.update();
	// mPopupWindows.showAtLocation(view.findViewById(R.id.art_image),
	// Gravity.CENTER, 0, 0);
	// mPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
	//
	// @Override
	// public void onDismiss() {
	// // TODO Auto-generated method stub
	// winView = null;
	// System.gc();
	// }
	// });
	// ImageView imageView = (ImageView) winView.findViewById(R.id.fullImage);
	// mImageLoader.displayImage(imageUrl, imageView,options);
	// }

	class DetStraTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showNetLoadingProgressDialog("加载中...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			StrategyService service = new StrategyService(
					DetailStrategyActivity.this);
			try {
				strategy = service.getDetailStrategyById(params[0]);
				if (strategy != null) {
					return SUCCESS;
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(DetailStrategyActivity.this,
						null, e);
				return "error";
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			hideNetLoadingProgressDialog();
			if (SUCCESS.equals(result)) {
				initContent();
			}
		}
	}
}
