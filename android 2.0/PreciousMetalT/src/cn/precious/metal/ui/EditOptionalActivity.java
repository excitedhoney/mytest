package cn.precious.metal.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.precious.metal.R;
import cn.precious.metal.adapter.EditOptionalAdapter;
import cn.precious.metal.adapter.EditOptionalAdapter.CheckChangeListener;
import cn.precious.metal.base.TitleActivity;
import cn.precious.metal.drag.DragSortListView;
import cn.precious.metal.entity.Optional;
import cn.precious.metal.services.OptionalService;

@SuppressLint("ResourceAsColor")
public class EditOptionalActivity extends TitleActivity {

	private DragSortListView mDragSortListView;

	private EditOptionalAdapter mAdapter;

	private List<Optional> options = new ArrayList<Optional>();

	private CheckBox checkAll;

	private ImageView delete;

	private TextView deleteNum;

	private LinearLayout linearlayot;

	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				Optional item = mAdapter.getItem(from);
				mAdapter.remove(item);
				mAdapter.insert(item, to);
				mAdapter.updateCheck(from, to);
				mAdapter.notifyDataSetChanged();
				new OptionalService(EditOptionalActivity.this)
						.updateDrag(mAdapter.getItems());
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_optional);
		initView();
		initData();
	}

	@SuppressLint("NewApi")
	public void initView() {

		showLeftTitleText();
		showRightTitleText();

		mDragSortListView = (DragSortListView) findViewById(R.id.drag_list_view);
		mDragSortListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mAdapter = new EditOptionalAdapter(this, options);
		mDragSortListView.setAdapter(mAdapter);
		mDragSortListView.setDropListener(onDrop);

		mAdapter.changeListener = checkChangeListener;

		checkAll = (CheckBox) findViewById(R.id.checkAll);
		delete = (ImageView) findViewById(R.id.delete);
		deleteNum = (TextView) findViewById(R.id.delete_number);

		getRightImageView().setBackground(
				getResources().getDrawable(R.drawable.gengduo));

		getRightLayout().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(EditOptionalActivity.this, AddOptionalActivity.class);
				startActivity(i);
				EditOptionalActivity.this.finish();
			}
		});
		linearlayot = (LinearLayout) findViewById(R.id.linearlayot);

		checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				mAdapter.setChecks(isChecked);
				if (isChecked) {
					deleteNum.setText("删除(" + mAdapter.getCount() + ")");
				} else {
					deleteNum.setText("删除(" + 0 + ")");
				}
			}
		});
		linearlayot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkAll.isChecked()) {
					checkAll.setChecked(false);
					deleteNum.setText("删除(" + 0 + ")");
				} else {
					checkAll.setChecked(true);
					deleteNum.setText("删除(" + mAdapter.getCount() + ")");
				}

			}
		});

		delete.setOnClickListener(this);
		deleteNum.setOnClickListener(this);
	}

	private CheckChangeListener checkChangeListener = new CheckChangeListener() {

		@Override
		public void checkChange() {
			// TODO Auto-generated method stub
			deleteNum.setText("删除(" + mAdapter.getCheckCount() + ")");
		}
	};

	public void initData() {
		options = new OptionalService(this).queryAllMyOptional();
		mAdapter.setItems(options);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v.getId() == R.id.delete || v.getId() == R.id.delete_number) {
			List<Optional> deleteOptionals = mAdapter.getCheckItems();
			boolean flag = new OptionalService(EditOptionalActivity.this)
					.deleteOptionals(deleteOptionals);
			if (flag) {
				initData();
				deleteNum.setText("删除(" + 0 + ")");
			}
		} else if (v.getId() == getLeftLayoutId()) {
			finish();
		} else if (v.getId() == getRightLayoutId()) {
			// intentTo(this, AddOptionalActivity.class);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
