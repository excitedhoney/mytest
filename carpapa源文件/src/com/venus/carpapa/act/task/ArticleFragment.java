/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.venus.carpapa.act.task;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.venus.carpapa.R;
import com.venus.carpapa.util.BMapUtil;
import com.venus.carpapa.util.DialogUtil;

public class ArticleFragment extends Fragment {
	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	BMapUtil mapUtil;
	Activity act = null;
	String photoPath;
	String path = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/Download/";
	ImageView q45duImageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		act = this.getActivity();
		mapUtil = new BMapUtil(act);
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.activity_upload_right, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		q45duImageView = (ImageView) getView()
				.findViewById(R.id.q45duImageView);
		q45duImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogUtil.toast(act, photoPath);
				mapUtil.getPicture(path);
			}
		});
	}

	public void onar(int requestCode, int resultCode, Intent data) {
		// DialogUtil.toast(act, "---onActivityResult");
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
				q45duImageView.setImageBitmap(photo);
				mapUtil.writeToFile(photoPath, photo, 85);
				// isHeadChanged = true;
			}

		}

	}

	@Override
	public void onStart() {
		super.onStart();

		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already
		// been
		// applied to the fragment at this point so we can safely call the
		// method
		// below that sets the article text.
		Bundle args = getArguments();
		if (args != null) {

			// Set article based on argument passed in
			updateArticleView(args.getInt(ARG_POSITION));
		} else if (mCurrentPosition != -1) {
			// Set article based on saved instance state defined during
			// onCreateView
			updateArticleView(mCurrentPosition);
		}
	}

	public void updateArticleView(int position) {
		TextView article = (TextView) getActivity().findViewById(R.id.article);
		article.setText(Ipsum.Articles[position]);
		mCurrentPosition = position;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// Save the current article selection in case we need to recreate the
		// fragment
		outState.putInt(ARG_POSITION, mCurrentPosition);
	}
}