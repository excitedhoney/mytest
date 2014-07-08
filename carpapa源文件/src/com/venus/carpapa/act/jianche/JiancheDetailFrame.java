package com.venus.carpapa.act.jianche;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.venus.carpapa.R;
import com.venus.carpapa.adapter.JiancheAdapter;
import com.venus.carpapa.util.BMapUtil;
import com.venus.carpapa.util.DialogUtil;
import com.venus.carpapa.util.GetJiancheAdapterData;
import com.venus.carpapa.vo.JianCheItemVo;

@SuppressLint({ "NewApi", "ResourceAsColor" })
public class JiancheDetailFrame extends Fragment {
	RelativeLayout hexin;
	RelativeLayout fuzhu, qita, chenei, waiguan;
	TextView qdxtTextView, fadongjiTextView;// 异常

	ImageView exceptionTextView;
	Activity act = null;
	LinearLayout qdLine;
	BMapUtil mapUtil;
	String photoPath;
	String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/";// 临时目录，为了在手机上好找
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	ListView fuzhuListView;
	ListView hexingListView;
	ListView qitaListView;
	ListView lunkuoListView;//
	ListView chetaiListView;//车胎和其他外观
	ListView cheneiListView;//车辆内饰检测 cheneiListView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 在这里初始化fragment的页面
		act = this.getActivity();
		mapUtil = new BMapUtil(act);
		return inflater.inflate(R.layout.jianche_detail, container, false);
	}

	public void onar(int requestCode, int resultCode, Intent data){
		//DialogUtil.toast(act, "---onActivityResult");
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			mapUtil.startPhotoZoom(Uri.fromFile(new File(photoPath)));
		}

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			mapUtil.startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
																		// 100)压缩文件
				exceptionTextView.setImageBitmap(photo);
				mapUtil.writeToFile(photoPath, photo, 85);
				// isHeadChanged = true;
			}

		}

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// 以下是拍照相关
		
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 由于fragment不是activity，不是oncreated，而是onActivityCreated
		setView();
		setListener();
		setData();
		// startThread();// 启动控制button的线程，当wifi状态不是在1或者3的时候，不可点击，
		// if (frag != null && frag.isInLayout()) {
		// switch (arg2) {
		// case 0:
		// frag.setText("0000");
	}

	public void setView() {
		hexin = (RelativeLayout) getView().findViewById(R.id.hexin);
		fuzhu = (RelativeLayout) getView().findViewById(R.id.fuzhu);

		qita = (RelativeLayout) getView().findViewById(R.id.qita);
		chenei = (RelativeLayout) getView().findViewById(R.id.chenei);
		waiguan = (RelativeLayout) getView().findViewById(R.id.waiguan);
		qdLine = (LinearLayout) getView().findViewById(R.id.qdLine);

		qdxtTextView = (TextView) getView().findViewById(R.id.qdxtTextView);
		fadongjiTextView = (TextView) getView().findViewById(
				R.id.fadongjiTextView);

		exceptionTextView = (ImageView) getView().findViewById(
				R.id.exceptionTextView);
		
		fuzhuListView=(ListView) getView().findViewById(R.id.fuzhuListView);
		hexingListView=(ListView) getView().findViewById(R.id.hexingListView);
		
		qitaListView=(ListView) getView().findViewById(R.id.qitaListView);
		lunkuoListView=(ListView) getView().findViewById(R.id.lunkuoListView);
		chetaiListView=(ListView) getView().findViewById(R.id.chetaiListView);
		
		cheneiListView=(ListView) getView().findViewById(R.id.cheneiListView);
	}
	public void qianbaoxiangang(){
		DialogUtil.toast(act, "你们睡吧");
	}
	  public void setData() {   
	    	List<JianCheItemVo> result=new ArrayList<JianCheItemVo>();
	    	
	    	
	    	String[] sa = getResources().getStringArray(R.array.fuzhe);
	    	for(int i=0;i<sa.length;i++){
	    		result.add(new JianCheItemVo(sa[i],"正常"));
	    	}
	    	
	    	JiancheAdapter fuzh_adapter=new JiancheAdapter(this.getActivity(),result);
	    	fuzhuListView.setAdapter(fuzh_adapter);
	    	hexingListView.setAdapter(
	    			new JiancheAdapter(this.getActivity(),
	    			GetJiancheAdapterData.getData(R.array.hexing, act))
	    			);
	    	
	    	qitaListView.setAdapter(
	    			new JiancheAdapter(this.getActivity(),
	    			GetJiancheAdapterData.getData(R.array.waiguan_chedeng, act))
	    			);
	    	lunkuoListView.setAdapter(
	    			new JiancheAdapter(this.getActivity(),
	    			GetJiancheAdapterData.getData(R.array.waiguan_lk, act))
	    			);
	    	chetaiListView.setAdapter(
	    			new JiancheAdapter(this.getActivity(),
	    			GetJiancheAdapterData.getData(R.array.waiguan_ct, act))
	    			);
	    	
	    	cheneiListView.setAdapter(
	    			new JiancheAdapter(this.getActivity(),
	    			GetJiancheAdapterData.getData(R.array.neishi, act))
	    			);
	    }
	public void setHexing() {
		hexin.setVisibility(View.VISIBLE);
		fuzhu.setVisibility(View.GONE);

		qita.setVisibility(View.GONE);
		chenei.setVisibility(View.GONE);
		waiguan.setVisibility(View.GONE);
	}

	public void setFuzhu() {
		hexin.setVisibility(View.GONE);
		fuzhu.setVisibility(View.VISIBLE);

		qita.setVisibility(View.GONE);
		chenei.setVisibility(View.GONE);
		waiguan.setVisibility(View.GONE);
	}

	public void setQita() {
		hexin.setVisibility(View.GONE);
		fuzhu.setVisibility(View.GONE);

		qita.setVisibility(View.VISIBLE);
		chenei.setVisibility(View.GONE);
		waiguan.setVisibility(View.GONE);
	}

	public void setChenei() {
		hexin.setVisibility(View.GONE);
		fuzhu.setVisibility(View.GONE);

		qita.setVisibility(View.GONE);
		chenei.setVisibility(View.VISIBLE);
		waiguan.setVisibility(View.GONE);
	}

	public void setWaiguan() {
		hexin.setVisibility(View.GONE);
		fuzhu.setVisibility(View.GONE);

		qita.setVisibility(View.GONE);
		chenei.setVisibility(View.GONE);
		waiguan.setVisibility(View.VISIBLE);
	}

	public void setListener() {
		qdLine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// hexingDialog();
				hexingDialog_v3("驱传动系统");
			}
		});
		fadongjiTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hexingDialog_v3("发动机");
			}
		});
		exceptionTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// hexingDialog("异常");
				
				photoPath = path + "haed_photo.jpg";
				DialogUtil.toast(act, photoPath);
				mapUtil.getPicture(path);
			}
		});
	}

	// 核心检测
	public void hexingDialog(String title) {
		final String[] arrayFruit = new String[] { "拍照", "相册" };
		final boolean[] arrayFruitSelected = new boolean[] { true, true, false,
				false, true };
		Dialog alertDialog = new AlertDialog.Builder(this.getActivity())
				.setTitle(title)

				.setSingleChoiceItems(arrayFruit, 0,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						})
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).create();
		alertDialog.show();
	}

	Map map = new HashMap();
	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			int id = buttonView.getId();
			if (isChecked) {
				map.put(id, id);

				if (R.id.checkBoxZc == id) {

					checkBoxMhy.setChecked(false);
					checkBoxQwsy.setChecked(false);
					checkBoxCgz.setChecked(false);

					checkBoxFdjyx.setChecked(false);
					checkBoxFdjddyz.setChecked(false);
					checkBoxYzsy.setChecked(false);

					checkBoxFdjxybl.setChecked(false);
					checkBoxMly.setChecked(false);

					// qdxtTextView.setBackgroundColor(R.color.zhengc_bg);

				} else {

					checkBoxZc.setChecked(false);
					// qdxtTextView.setBackgroundColor(R.color.zhengc_bg);
				}
			} else {
				map.remove(id);
			}

		}

	};

	public void addTextView() {
		Set keyset = map.keySet();
		Iterator it = keyset.iterator();
		String t = "";
		qdLine.removeAllViews();
		while (it.hasNext()) {
			int id = (Integer) it.next();
			TextView tv = new TextView(act);
			//tv.setPadding(5, 0, 0, 0);
			LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 0, 0, 0);  
			tv.setLayoutParams(params);
			
			if (id == R.id.checkBoxMhy) {

				tv.setText("冒黑烟");
				tv.setBackgroundResource(R.color.yellow);
			} else if (id == R.id.checkBoxQwsy) {

				tv.setText("轻微渗油");
				tv.setBackgroundResource(R.color.yellow);
			} else if (id == R.id.checkBoxCgz) {
				tv.setText("曾改装");
				tv.setBackgroundResource(R.color.yellow);
			} else if (id == R.id.checkBoxFdjyx) {
				tv.setText("发动机异响");
				tv.setBackgroundResource(R.color.red);
			} else if (id == R.id.checkBoxFdjddyz) {
				tv.setText("发动机抖动严重");
				tv.setBackgroundResource(R.color.red);
			} else if (id == R.id.checkBoxYzsy) {
				tv.setText("严重渗油");
				tv.setBackgroundResource(R.color.red);
			} else if (id == R.id.checkBoxFdjxybl) {
				tv.setText("发动机相应不良");
				tv.setBackgroundResource(R.color.red);
			} else if (id == R.id.checkBoxMly) {

				tv.setText("冒蓝烟");
				tv.setBackgroundResource(R.color.red);
			}

			qdLine.addView(tv);
		}
		// fadongjiTextView.setText(t);

	}

	CheckBox checkBoxZc, checkBoxMhy, checkBoxQwsy, checkBoxCgz, checkBoxFdjyx,
			checkBoxFdjddyz,

			checkBoxYzsy, checkBoxFdjxybl, checkBoxMly;

	public void hexingDialog_v3(String title) {

		final Dialog mDialog = new Dialog(this.getActivity());
		View view = LayoutInflater.from(this.getActivity()).inflate(
				R.layout.dialog_hexing, null);
		mDialog.setTitle(title);
		mDialog.setContentView(view);

		checkBoxZc = (CheckBox) view.findViewById(R.id.checkBoxZc);
		checkBoxMhy = (CheckBox) view.findViewById(R.id.checkBoxMhy);
		checkBoxQwsy = (CheckBox) view.findViewById(R.id.checkBoxQwsy);

		checkBoxCgz = (CheckBox) view.findViewById(R.id.checkBoxCgz);
		checkBoxFdjyx = (CheckBox) view.findViewById(R.id.checkBoxFdjyx);
		checkBoxFdjddyz = (CheckBox) view.findViewById(R.id.checkBoxFdjddyz);

		checkBoxYzsy = (CheckBox) view.findViewById(R.id.checkBoxYzsy);
		checkBoxFdjxybl = (CheckBox) view.findViewById(R.id.checkBoxFdjxybl);
		checkBoxMly = (CheckBox) view.findViewById(R.id.checkBoxMly);

		checkBoxZc.setOnCheckedChangeListener(listener);
		checkBoxMhy.setOnCheckedChangeListener(listener);
		checkBoxQwsy.setOnCheckedChangeListener(listener);

		checkBoxCgz.setOnCheckedChangeListener(listener);
		checkBoxFdjyx.setOnCheckedChangeListener(listener);
		checkBoxFdjddyz.setOnCheckedChangeListener(listener);

		checkBoxYzsy.setOnCheckedChangeListener(listener);
		checkBoxFdjxybl.setOnCheckedChangeListener(listener);
		checkBoxMly.setOnCheckedChangeListener(listener);

		Button submitBtn = (Button) view.findViewById(R.id.submitBtn);
		Button canelBtn = (Button) view.findViewById(R.id.canelBtn);
		// final EditText nameEdit = (EditText)
		// view.findViewById(R.id.nameEdit);
		// nameEdit.setText(um.getUserName());
		//
		// final EditText phoneEdit = (EditText)
		// view.findViewById(R.id.phoneEdit);

		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				qdxtTextView.setBackgroundColor(R.color.yellow);
				mDialog.dismiss();
				addTextView();
				map.clear();
				// add(phone,name);
			}
		});
		canelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDialog.dismiss();

			}
		});

		mDialog.show();

	}

	public void hexingDialog_v2(String title) {
		final String[] arrayFruit = new String[] { "曾修复", "冒黑烟", "轻微渗油", "曾改装",
				"发动机异响" };
		final boolean[] arrayFruitSelected = new boolean[] { true, true, false,
				false, true };
		AlertDialog builder = new AlertDialog.Builder(this.getActivity())
				.setTitle(title)
				.setMultiChoiceItems(arrayFruit, arrayFruitSelected,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1,
									boolean arg2) {
								// TODO Auto-generated method stub

							}

						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setNegativeButton("取消", null).create();

		builder.show();

	}
}
