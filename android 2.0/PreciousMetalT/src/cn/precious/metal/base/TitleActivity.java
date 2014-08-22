package cn.precious.metal.base;

import cn.precious.metal.R;
import cn.precious.metal.ui.EditOptionalActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleActivity extends BaseActivity implements OnClickListener {

	private ImageView mLeftImageView;

	private ImageView mRightImageView;

	private ImageView mRightImageView2;

	private View mLeftLayout = null;

	private View mRightLayout = null;
	private View mRightLayout2 = null;
  
	
	
	private TextView mTitleText;

	private TextView mLeftTitleText;

	private View rootView;

	@Override
	public void setContentView(int contentId) {
		super.setContentView(R.layout.title_base);
		LinearLayout content = (LinearLayout) findViewById(R.id.content);
		rootView = LayoutInflater.from(this).inflate(contentId, null);
		content.addView(rootView);
		mLeftImageView = (ImageView) findViewById(R.id.leftImageView);
		mRightImageView = (ImageView) findViewById(R.id.rightImageView);
		mRightImageView2 = (ImageView) findViewById(R.id.rightImageView2);
		mLeftLayout = findViewById(R.id.leftLayout);
		mRightLayout = findViewById(R.id.rightLayout);
		mRightLayout2 = findViewById(R.id.rightLayout2);
		mTitleText = (TextView) findViewById(R.id.titleText);
		mLeftTitleText = (TextView) findViewById(R.id.leftTitleText);
		mTitleText.setText(getTitle());

		// mLeftImageView.setOnClickListener(this);
//		mRightImageView.setOnClickListener(this);

		mLeftLayout.setOnClickListener(this);
		mRightLayout.setOnClickListener(this);
		mRightLayout2.setOnClickListener(this);

		// mLeftTitleText.setOnClickListener(this);
	}

	public ImageView getLeftImageView() {
		return mLeftImageView;
	}

	public ImageView getRightImageView() {
		return mRightImageView;
	}

	public ImageView getRightImageView2() {
		return mRightImageView2;
	}

	public TextView getTitleText() {
		return mTitleText;
	}

	public TextView getLeftTitleText() {
		return mLeftTitleText;
	}

	public int getLeftLayoutId() {
		return R.id.leftLayout;
	}

	public int getRightLayoutId() {
		return R.id.rightLayout;
	}

	public int getRightLayoutId2() {
		return R.id.rightLayout2;
	}

	public View getLeftLayout() {
		return mLeftLayout;
	}

	public View getRightLayout() {
		return mRightLayout;
	}

	public View getRightLayout2() {
		return mRightLayout2;
	}

	public void showLeftLayout() {
		mLeftLayout.setVisibility(View.VISIBLE);
	}

	public void showRightLayout() {
		mRightLayout.setVisibility(View.VISIBLE);
	}

	public void hideLeftLayout() {
		mLeftLayout.setVisibility(View.GONE);
	}

	public void hideRightLayout() {
		mRightLayout.setVisibility(View.GONE);
	}

	public int getLeftImageViewId() {
		return R.id.leftImageView;
	}

	public int getLeftTitleTextId() {
		return R.id.leftTitleText;
	}

	public int getRightImageViewId() {
		return R.id.rightImageView;
	}

	public int getRightImageView2Id() {
		return R.id.rightImageView2;
	}

	public void showBackImage() {
		getLeftImageView().setVisibility(View.VISIBLE);
		getLeftTitleText().setVisibility(View.GONE);
	}

	public void showLeftTitleText() {
		getLeftImageView().setVisibility(View.GONE);
		getLeftTitleText().setVisibility(View.VISIBLE);
	}

	public void showRightTitleText() {
		getRightImageView().setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rightImageView:
			Intent i = new Intent();
			i.setClass(this, EditOptionalActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}
}
