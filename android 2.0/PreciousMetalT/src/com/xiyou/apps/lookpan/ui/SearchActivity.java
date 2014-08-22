package com.xiyou.apps.lookpan.ui;

import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.services.OptionalService;

import com.xiyou.apps.lookpan.R;
import com.xiyou.apps.lookpan.adapter.AddOptionAdapter;
import com.xiyou.apps.lookpan.base.BaseActivity;

public class SearchActivity extends BaseActivity implements OnClickListener{ 

	private ListView listView ;
	
	private EditText mEditText ;
	
	private ImageView clear ;
	
	private AddOptionAdapter mAdapter;
	
	private List<Optional> optional ;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initListener();
	}
	
	public void back(View view) {
		
		finish(); 
	}
	
	
	public void initView(){
		
		listView = (ListView) findViewById(R.id.listview);
		
		mAdapter = new AddOptionAdapter(this, null, true);
		
		listView.setAdapter(mAdapter);
		
		mEditText = (EditText) findViewById(R.id.et_keyword);
		
		clear = (ImageView) findViewById(R.id.clear_keyword);
	}
	
	public void initListener(){
		clear.setOnClickListener(this);
		
		mEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				loadDataByKeyword(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				optional = null ;
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.clear_keyword){
			mEditText.setText("");
		}
	}
	
	
	public void loadDataByKeyword(String keyword){
		if(keyword == null || "".equalsIgnoreCase(keyword))
			return  ;
		
		optional = new OptionalService(this).queryOptionalsByKeyword(keyword);
		mAdapter.setItems(optional, true);
	}
}
