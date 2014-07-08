package com.venus.carpapa.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * 封装异步任务类的操作
 * 
 * @author 子旺
 * 
 */
public class HandleAsync {

	public void excute(Listener dealListener) {
		new MyAsync(dealListener).execute();
	}

	class MyAsync extends AsyncTask<Void, Void, String> {
		Listener listener;

		public MyAsync(Listener listener) {
			this.listener = listener;
		}

		@Override
		protected String doInBackground(Void... parse) {
			String result = listener.getResult();
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			onComplete(result);

		}

		private void onComplete(String result) {
			Message msg = handler.obtainMessage();
			if (result == null) {
				msg.what = -1;
			} else {
				msg.what = 0;
				msg.obj = result;
			}
			msg.sendToTarget();
		}

		/**
		 * 处理结果的Handler
		 */
		private Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					listener.parse(msg.obj.toString());
				} else
					listener.parse(null);
			};
		};

	}

	public interface Listener {
		/**
		 * 获取接口的数据
		 * 
		 * @return
		 */
		String getResult();

		/**
		 * 解析得到的数据
		 * 
		 * @param result
		 */
		void parse(String result);
	}
}
