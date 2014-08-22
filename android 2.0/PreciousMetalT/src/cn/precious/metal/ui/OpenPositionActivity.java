package cn.precious.metal.ui;

import java.util.Date;
import java.util.List;

import widget.PupupSeekBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseActivity;
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.dao.VirtualTradeDao;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.entity.VirtualTrade;
import cn.precious.metal.utils.Utils;

public class OpenPositionActivity extends BaseActivity implements
		OnClickListener {

	private TextView tvMore, tvLess, tvChoose, tvCurrentPrice, tvVolume,priceType;

	private ImageView ivSubmit;

	private PupupSeekBar mSeekBar;

	private boolean isMore = true;

	private List<Optional> allOptionals;

	private VirtualTradeDao tradeDao;
	private OptionalDao optionalDao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_position);
		tradeDao = new VirtualTradeDao(this);
		optionalDao = new OptionalDao(this);
		initView();
		initListen();
	}

	public void initView() {
		priceType = (TextView) findViewById(R.id.price_type);
		tvMore = (TextView) findViewById(R.id.tv_more);
		tvLess = (TextView) findViewById(R.id.tv_less);
		tvChoose = (TextView) findViewById(R.id.tv_choose);
		tvCurrentPrice = (TextView) findViewById(R.id.tv_current_price);
		tvVolume = (TextView) findViewById(R.id.tv_volume);

		mSeekBar = (PupupSeekBar) findViewById(R.id.seek_bar);
		mSeekBar.setMax(Math.round(AppSetting.getInstance(this)
				.getVirtualBalance()));

		ivSubmit = (ImageView) findViewById(R.id.iv_submit);
	}

	public void initListen() {
		tvMore.setOnClickListener(this);
		tvLess.setOnClickListener(this);
		tvChoose.setOnClickListener(this);
		tvCurrentPrice.setOnClickListener(this);
		tvVolume.setOnClickListener(this);

		ivSubmit.setOnClickListener(this);

		mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				//当前所用的价钱
				float currentPrice  =  (progress * AppSetting.getInstance(OpenPositionActivity.this).getVirtualBalance())/mSeekBar.getMax() ;
				if(optional != null) {
					
					double volume = Math.floor((currentPrice / Float.parseFloat(optional.getRatio())) / Float.parseFloat(optional.getNewest())) ;
					tvVolume.setText(""+volume) ;
					
					double price = volume * Float.parseFloat(optional.getNewest()) * Float.parseFloat(optional.getRatio()) ;
					
					mSeekBar.setSeekBarText("￥" + Utils.getBigDecimal(price));
				}
			}
		});
	}

	public void back(View view) {
		finish();
	}

	private String[] items;

	private Optional optional;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_more:
			priceType.setText("买入价格 :");
			isMore = true;
			if(optional != null)
				tvCurrentPrice.setText(optional.getBuyone());
			
			tvMore.setBackgroundResource(R.drawable.register_3);
			tvLess.setBackgroundResource(R.color.transparent);
			break;
		case R.id.tv_less:
			priceType.setText("卖出价格 :");
			isMore = false;
			if(optional != null)
				tvCurrentPrice.setText(optional.getSellone());
			
			tvLess.setBackgroundResource(R.drawable.register_3);
			tvMore.setBackgroundResource(R.color.transparent);
			break;
		case R.id.iv_submit:
			if (optional == null) {
				showCusToast("请先选择一个商品");
				return;
			}

			if (mSeekBar.getProgress() == 0) {
				showCusToast("你用是大于0的虚拟货币");
				return;
			}

			float balance = AppSetting.getInstance(OpenPositionActivity.this)
					.getVirtualBalance();
			
			float volume =  "".equals(tvVolume.getText().toString())  ? 0 : Float.parseFloat(tvVolume.getText().toString());
			
			float useBalance = 0 ;
			
			if(isMore) {
				useBalance = volume * Float.parseFloat(optional.getBuyone()) * Float.parseFloat(optional.getRatio()) ;
			}else{
				useBalance = volume * Float.parseFloat(optional.getSellone()) * Float.parseFloat(optional.getRatio()) ;
			}
			
			AppSetting.getInstance(OpenPositionActivity.this).setVirtualBalance(balance - useBalance);

			VirtualTrade trade = new VirtualTrade();
			if (isMore) {
				trade.setOrientation(1);
				trade.setTransactionPrice(Float.parseFloat(optional.getBuyone()));
			} else {
				trade.setOrientation(0);
				trade.setTransactionPrice(Float.parseFloat(optional.getSellone()));
			}
			
			trade.setOpenVolume(volume);
			trade.setCloseVolume(volume);
			trade.setType(optional.getType());
			trade.setCreateTime(sdf.format(new Date()));
			trade.setUpdateTime(sdf.format(new Date()));
			trade.setPhoneNumber(AppSetting.getInstance(
					OpenPositionActivity.this).getPhoneNumber());
			trade.setTreaty(optional.getTreaty());
			trade.setIsOpening(1);
			trade.setIsClose(0);
			trade.setTitle(optional.getTitle());
			new SubmitTask(trade).execute("");

			break;
		case R.id.tv_choose:
			if (allOptionals == null) {
				allOptionals = optionalDao.queryAllMyOptionals();
			}

			if (allOptionals != null && !allOptionals.isEmpty()) {
				items = new String[allOptionals.size()];
				for (int i = 0; i < allOptionals.size(); i++) {
					items[i] = allOptionals.get(i).getTitle();
				}
				
				AlertDialog dialog = new AlertDialog.Builder(OpenPositionActivity.this)
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						tvChoose.setText(items[which]);
						optional = allOptionals.get(which) ;
						if(isMore) {
							tvCurrentPrice.setText(""+allOptionals.get(which).getBuyone());
						}else{
							tvCurrentPrice.setText(""+allOptionals.get(which).getSellone());
						}
						
						if(mSeekBar.getProgress() > 0) {
							if(isMore) {
								float volume = (mSeekBar.getProgress() / Float.parseFloat(allOptionals.get(which).getRatio()))  / Float.parseFloat(allOptionals.get(which).getBuyone()) ;
								tvVolume.setText( "" + volume )  ;
							}else{
								float volume = (mSeekBar.getProgress() / Float.parseFloat(allOptionals.get(which).getRatio()))  / Float.parseFloat(allOptionals.get(which).getSellone()) ;
								tvVolume.setText( "" + volume )  ;
							}
							
						}
						mSeekBar.getProgress()  ;
					}
				}).create() ;
				dialog.setCanceledOnTouchOutside(true);
				dialog.show();
			}
			break;

		default:
			break;
		}
	}

	class SubmitTask extends AsyncTask<String, Void, String> {

		private VirtualTrade trade;

		public SubmitTask(VirtualTrade trade) {
			this.trade = trade;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showNetLoadingProgressDialog("上传中...");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}

			return SUCCESS;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			hideNetLoadingProgressDialog();
			if (SUCCESS.equals(result)) {
				if (tradeDao.addTrade(trade)) {
					showCusToast("建仓成功");
					finish(); 
				}
			}
		}
	}
}
