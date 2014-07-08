package com.xiyou.apps.view;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.adapter.ImageAdapter;
import com.xiyou.apps.lookpan.fragment.showactivity.ShowNewsActivity;
import com.xiyou.apps.lookpan.model.News_Top_ImageBean;

@SuppressWarnings("deprecation")
public class AutoPlayGallery extends RelativeLayout {
	private final static int BASE_BACKGROUND_COLOR = Color.TRANSPARENT;
	Timer timer = null;
	TimerTask timerTask = null;
	Handler handler = null;
	private int duration = 5000; // switch
	// duration
	Context context = null;
	private MyGallery mGallery = null;
	private RadioGroup radioGroup = null;
	private Bitmap pointBg = null;
	private Bitmap pointPressedBg = null;
	private int height = 30; // base
	// height
	// ,can
	// be
	// modify
	// by
	// setHeight
	private boolean flag = false; // switch
	// for
	// playing
	// private Thread autoPlayThread;
	public static long nid;
	static int size;

	public AutoPlayGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setupContentView(context);
		this.context = context;
	}

	public AutoPlayGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setupContentView(context);
	}

	private void setupContentView(Context context) {
		pointBg = BitmapFactory
				.decodeResource(getResources(), R.drawable.point);
		pointPressedBg = BitmapFactory.decodeResource(getResources(),
				R.drawable.p);

		LinearLayout indicator = new LinearLayout(context);
		int heightPX = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, height, getResources()
						.getDisplayMetrics()); // change inch to pix
		indicator
				.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
						android.widget.LinearLayout.LayoutParams.FILL_PARENT,
						heightPX));
		indicator.setBackgroundColor(BASE_BACKGROUND_COLOR);
		indicator.setGravity(Gravity.CENTER);
		radioGroup = new RadioGroup(context);
		radioGroup.setOrientation(RadioGroup.HORIZONTAL);
		indicator.addView(radioGroup);
		mGallery = new MyGallery(context);
		Gallery.LayoutParams paramGallery = new Gallery.LayoutParams(
				Gallery.LayoutParams.FILL_PARENT,
				Gallery.LayoutParams.FILL_PARENT);
		mGallery.setLayoutParams(paramGallery);
		mGallery.setSpacing(1); // set the spacing between items
		mGallery.setUnselectedAlpha(1.3f); // make all the items light
		mGallery.setHorizontalFadingEdgeEnabled(false); // ɾ��߽���Ӱ
		addView(mGallery, new android.widget.RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT));

		android.widget.RelativeLayout.LayoutParams param = new android.widget.RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		addView(indicator, param);

	}

	/**
	 * set adapter to start the gallery
	 * 
	 * @param adapter
	 */
	public void setAdapter(ImageAdapter adapter) {

		ArrayList<News_Top_ImageBean> drawables = adapter.getDrawables();
		if (drawables != null && drawables.size() > 0) {
			setIndicator(adapter.getContext(), drawables.size());
			mGallery.setAdapter(adapter);
			mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View view,
						int position, final long arg3) {
					indicatePoint(position);
					nid = arg3;
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			flag = true;// make the switch true
			size = drawables.size();
			mGallery.setSelection(size * 00); // �������һ�
			// play(drawables.size());

		}
	}

	/**
	 * stop the play thread
	 */
	public void stop() {
		flag = false;
		// autoPlayThread.interrupt();
		timer.cancel();
		timer = null;
		// mGallery.setAdapter(new ImageAdapter(getContext(),
		// new ArrayList<News_Top_ImageBean>(), new ArrayList<String>()));
		radioGroup.removeAllViews();
	}

	/**
	 * is play?
	 * 
	 * @return
	 */
	public boolean isPlaying() {
		return flag;
	}

	/**
	 * set duration
	 * 
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * set layout height
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	private void setIndicator(Context context, int size) {
		// TODO change
		radioGroup.removeAllViews();
		for (int i = 0; i < size; i++) {
			ImageView point = new ImageView(context);
			point.setImageResource(R.drawable.point);
			radioGroup.addView(point);
		}
	}

	private void indicateImage(int position) {
		mGallery.setSelection(position);
		imageViewInAniamtion(0, 0);
		imageViewInAniamtion(1, 1);
		// System.out.println(position);
		indicatePoint(position);
	}

	/**
	 * setAnimation for auto play item
	 * 
	 * @param position
	 */
	private void imageViewInAniamtion(int position, int anistyle) {
		// TODO change
		// ImageView img = (ImageView) mGallery.getChildAt(position);
		RelativeLayout img = (RelativeLayout) mGallery.getChildAt(position);
		// RelativeLayout img2 = (RelativeLayout) mGallery.getChildAt(position +
		// 1);
		if (img != null) {
			img.startAnimation(getTranslateAnimation(anistyle));

			// img2.startAnimation(getTranslateAnimation(1));
		}
	}

	/**
	 * a tween animation
	 * 
	 * @return
	 */
	private Animation getTranslateAnimation(int type) {
		AnimationSet as = new AnimationSet(true);
		TranslateAnimation ta = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				-1.0f, Animation.RELATIVE_TO_PARENT, 0,
				Animation.RELATIVE_TO_PARENT, 0);
		TranslateAnimation tb = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0);
		ta.setDuration(500);
		if (type == 0) {
			as.addAnimation(ta);

			return as;
		} else {
			as.addAnimation(tb);
			return as;
		}
	}

	/**
	 * @param position
	 */
	private void indicatePoint(int position) {
		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			((ImageView) radioGroup.getChildAt(i)).setImageBitmap(pointBg);
		}
		((ImageView) radioGroup.getChildAt(position % size))
				.setImageBitmap(pointPressedBg);
	}

	public static int count = 200 * size;

	/**
	 * @param size
	 */
	@SuppressLint("HandlerLeak")
	private void play(final int size) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (mGallery.isTouched()) {
					count = mGallery.getFirstVisiblePosition() + 1;
					mGallery.setTouched(false);
				} else {
					count++;
				}
				indicateImage(count);
			}
		};
		// if (autoPlayThread != null)
		// autoPlayThread.interrupt();
		stopTimer();

		startTimer();
	}

	class MyGallery extends Gallery {
		public MyGallery(Context context) {
			super(context);
		}

		public MyGallery(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public MyGallery(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		// Convert the dips to pixels
		float scale = getResources().getDisplayMetrics().density;
		int FLINGTHRESHOLD = (int) (2.0f * scale + 0.5f);
		int SPEED = 1000;

		private boolean isTouched = false;

		public void setTouched(boolean isTouched) {
			this.isTouched = isTouched;
		}

		public boolean isTouched() {
			return isTouched;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			setTouched(true);
			// System.out.println("������ onfling ");
			// //����false ���Gallery��ק�������
			int keyCode;
			if (isScrollingLeft(e1, e2)) {
				keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
				count++;
			} else {
				keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
				count--;
			}

			onKeyDown(keyCode, null);
			// ��ֹ�ص�
			// stopTimer();
			return false;
		}

		private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
			// return (e2.getX() - e1.getX())>=FLINGTHRESHOLD;
			return e2.getX() > e1.getX();
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX * 1.3f, 0f);
			// return true;
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			int action = event.getAction();
			if (action == MotionEvent.ACTION_UP) {
				// startTimer();
			}
			if (action == MotionEvent.ACTION_DOWN) {
				// stopTimer();
			}
			return super.onTouchEvent(event);
		}

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			// startTimer();
			return super.onKeyUp(keyCode, event);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {

			Intent intent = new Intent(context, ShowNewsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("id", AutoPlayGallery.nid + "");
			if (android.os.Build.VERSION.SDK_INT >= 14) {
				// ScreenShot.shoot(MainActivity.getInstance());
			}
			context.startActivity(intent);
			return true;
		}

	}

	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}

	}

	public void startTimer() {
		stopTimer();
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		};
		timer.schedule(timerTask, duration, duration);

	}
}