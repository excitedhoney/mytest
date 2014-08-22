package cn.precious.metal.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.adapter.RemindListAdapter;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.dao.NoticeDao;
import cn.precious.metal.entity.CustomNotice;
import cn.precious.metal.ui.RemindActivity;

public class RemindListFragment extends BaseFragment {

	private ListView listView ;
	
	private TextView empty ;
	
	private Button btnAddRemind ;
	
	private RemindListAdapter adapter;

	private List<CustomNotice> lists;
	
	private NoticeDao dao ;

	public static RemindListFragment newInstance(int oritation) {
		RemindListFragment fragment = new RemindListFragment();
		Bundle b = new Bundle();
		b.putInt("fangshi", oritation);
		fragment.setArguments(b);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_remind_list, null) ;
		initView(view);
		return view;
	}
	
	public void initView(View view) {
		listView = (ListView) view.findViewById(R.id.listview);
		empty = (TextView) view.findViewById(R.id.empty);
		btnAddRemind = (Button) view.findViewById(R.id.btn_add_remind);
		
		
		adapter = new RemindListAdapter(getActivity(), lists) ;
		
		listView.setAdapter(adapter);	
		
		btnAddRemind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent  it = new Intent(getActivity(), RemindActivity.class) ;
				startActivity(it);
			}
		});
	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		dao = new NoticeDao(getActivity()) ;
		if(getArguments().getInt("fangshi") == 1) {
			lists = dao.queryNoteNotices() ;
			if(lists == null || lists.isEmpty()) {
				empty.setVisibility(View.VISIBLE);
				empty.setText("你还没有设置短信通知");
				
			}else{
				empty.setVisibility(View.GONE);
				adapter.setItems(lists);
			}
		}else{
			lists = dao.queryAlarmNotices();
			if(lists == null || lists.isEmpty()) {
				empty.setVisibility(View.VISIBLE);
				empty.setText("你还没有本地设置通知");
			}else{
				empty.setVisibility(View.GONE);
				adapter.setItems(lists);
			}
		}
	}
	
}
