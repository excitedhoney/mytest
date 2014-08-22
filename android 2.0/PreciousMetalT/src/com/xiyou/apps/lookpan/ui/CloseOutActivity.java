package com.xiyou.apps.lookpan.ui;

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
import cn.precious.metal.config.AppSetting;
import cn.precious.metal.dao.OptionalDao;
import cn.precious.metal.dao.VirtualTradeDao;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.entity.VirtualTrade;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.base.BaseActivity;

public class CloseOutActivity extends BaseActivity implements OnClickListener{
	private TextView  tvChoose  , tvCurrentPrice ,tvAsset;
	
	private ImageView ivSubmit ;
	
	private PupupSeekBar mSeekBar ;
	
	private VirtualTradeDao tradeDao ;
	
	private OptionalDao optionalDao; 
	
	private List<VirtualTrade> trades  ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_close_out);
		tradeDao = new VirtualTradeDao(this) ;
		optionalDao = new OptionalDao(this) ;
		initView();
		initListen();
	}
	
	
	
	public void initView() {
		tvChoose  =  (TextView) findViewById(R.id.tv_choose) ;
		tvCurrentPrice  =  (TextView) findViewById(R.id.tv_current_price) ;
		tvAsset  =  (TextView) findViewById(R.id.tv_asset) ;
		
		mSeekBar = (PupupSeekBar) findViewById(R.id.seek_bar) ;
		mSeekBar.setClose(true);
		ivSubmit = (ImageView) findViewById(R.id.iv_submit) ;
	}
	
	public void initListen () {
		tvChoose.setOnClickListener(this);
		tvCurrentPrice.setOnClickListener(this);
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
				// TODO Auto-generated method stub
				if(chooseTrade == null) {
					showCusToast("请先选择一件持仓的商品");
					return ;
				}
				float volume  =  (progress * chooseTrade.getCloseVolume())/mSeekBar.getMax() ;
				if(currentOptional != null) {
					float currentPrice = 0 ;
					if(chooseTrade.getOrientation() == 1) {
						currentPrice = Float.parseFloat(currentOptional.getSellone()) ;
					}else{
						currentPrice = Float.parseFloat(currentOptional.getBuyone()) ;
					}
					tvAsset.setText(""+(volume * currentPrice)) ;
				}
				mSeekBar.setSeekBarText("" + volume );
			}
		});
	}
	
	public void back(View view) {
		finish();
	}
	
	private String[] items ;
	
	private VirtualTrade chooseTrade  ;
	
	private Optional currentOptional  ;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_submit:
			if(trades == null || trades.isEmpty()) {
				showCusToast("你还没有减仓，请先建仓");
				return ;
			}
			
			if(chooseTrade == null) {
				showCusToast("你先选择一个你持仓的商品");
				return ;
			}
			
			if(mSeekBar.getProgress()  == 0) {
				showCusToast("请使用大于0的虚拟货币");
				return  ;
			}
			
			float volume = ( mSeekBar.getProgress() * chooseTrade.getCloseVolume()) / mSeekBar.getMax() ;
			
			chooseTrade.setCloseVolume(chooseTrade.getCloseVolume() - volume);
			
			if(mSeekBar.getProgress() == mSeekBar.getMax()) {  //把建仓的  清仓 
				chooseTrade.setIsClose(1);
				chooseTrade.setIsOpening(1) ;
				chooseTrade.setUpdateTime(sdf.format(new Date()));
				tradeDao.updateTrade(chooseTrade);
			}else{ // 把建仓的部分清仓  
				chooseTrade.setIsOpening(1);
				chooseTrade.setIsClose(0);
				chooseTrade.setUpdateTime(sdf.format(new Date()));
				tradeDao.updateTrade(chooseTrade);
			}
			
			//生成一个平仓的记录
			VirtualTrade trade = new VirtualTrade() ;
			float useBalance  =  volume * chooseTrade.getTransactionPrice() * Float.parseFloat(currentOptional.getRatio()) ;
			
			if(chooseTrade.getOrientation()  == 1 ) {
				useBalance += (Float.parseFloat(currentOptional.getSellone()) - chooseTrade.getTransactionPrice() ) * volume  ;
			}else{
				useBalance += (chooseTrade.getTransactionPrice() - Float.parseFloat(currentOptional.getBuyone())) * volume  ;
			}
			
			AppSetting.getInstance(CloseOutActivity.this).setVirtualBalance(AppSetting.getInstance(CloseOutActivity.this).getVirtualBalance() + useBalance);
			
			trade.setTitle(chooseTrade.getTitle());
			trade.setOrientation(chooseTrade.getOrientation());
			trade.setIsClose(1);
			trade.setIsOpening(0);
			trade.setCreateTime(sdf.format(new Date()));
			trade.setUpdateTime(sdf.format(new Date()));
			if(chooseTrade.getOrientation()  == 1 ) {
				trade.setTransactionPrice(Float.parseFloat(currentOptional.getSellone()));
			}else{
				trade.setTransactionPrice(Float.parseFloat(currentOptional.getBuyone()));
			}
			trade.setTreaty(chooseTrade.getTreaty());
			trade.setCloseVolume(volume);
			trade.setOpenPrice(chooseTrade.getTransactionPrice());  //开仓的
			
			new SubmitTask(trade).execute("");
			
			break;
		case R.id.tv_choose:
			trades = tradeDao.queryOpeningTrades() ;
			
			if(trades != null && !trades.isEmpty()) {
				items = new String[trades.size()] ;
				for (int i = 0; i < trades.size(); i++) {
					if(trades.get(i).getOrientation() == 1)
						items[i] = ""+trades.get(i).getTitle() + "(做多)"   ;
					else
						items[i] = ""+trades.get(i).getTitle() + "(做空)"   ;
				}
				
				AlertDialog dialog = new AlertDialog.Builder(CloseOutActivity.this)
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						tvChoose.setText(items[which]);
						chooseTrade = trades.get(which) ;
						if(chooseTrade  == null ) 
							return ;
						currentOptional = optionalDao.queryOptionalsBySymbol(chooseTrade.getTreaty()) ;
						
						if(chooseTrade.getOrientation()  == 1) {
							tvCurrentPrice.setText(currentOptional.getSellone());
						}else{
							tvCurrentPrice.setText(currentOptional.getBuyone());
						}
						
						mSeekBar.setMax(Math.round(chooseTrade.getCloseVolume()));
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

		private VirtualTrade trade ;
		
		public SubmitTask(VirtualTrade trade) {
			this.trade = trade ;
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
			if(SUCCESS.equals(result)) {
				if(tradeDao.addTrade(trade)) {
					showCusToast("平仓成功");
					finish();
				}
			}
		}
	}
}
