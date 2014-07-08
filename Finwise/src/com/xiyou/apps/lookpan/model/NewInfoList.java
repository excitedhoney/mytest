package com.xiyou.apps.lookpan.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;

public class NewInfoList {
	NewInfoBean newInfoBean;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

	public NewInfoList(String source) throws Exception {
		JSONObject data = new JSONObject(source);
		NewInfoBean newInfoBean = new NewInfoBean();
		newInfoBean.setBody(data.getJSONObject("body").getJSONArray("und")
				.getJSONObject(0).getString("safe_value"));
		Date date = new Date(Long.parseLong(data.getString("created")) * 1000);
		newInfoBean.setCreated(sdf.format(date));
		newInfoBean.setPicture(data.getString("picture"));
		newInfoBean.setPath(data.getString("path"));
		newInfoBean.setTitle(data.getString("title"));
		newInfoBean.setNid(data.getString("nid"));
		newInfoBean.setName(data.getString("name"));
		if (!data.isNull("field_related")
				&& !data.getString("field_related").equals("[]")) {
			JSONArray field_related = data.getJSONObject("field_related")
					.getJSONArray("und");
			ArrayList<NewInfoBean> filed_re = new ArrayList<NewInfoBean>();
			for (int i = 0; i < field_related.length(); i++) {
				NewInfoBean infoBean = new NewInfoBean();
				infoBean.setTitle(field_related.getJSONObject(i).getString(
						"title"));
				infoBean.setNid(field_related.getJSONObject(i).getString(
						"target_id"));
				filed_re.add(infoBean);
			}
			newInfoBean.setField_related(filed_re);
		}

		if (!data.isNull("taxonomy_vocabulary_2")
				&& !data.getString("taxonomy_vocabulary_2").equals("[]")) {

			JSONArray taxonomy_vocabulary_2 = data.getJSONObject(
					"taxonomy_vocabulary_2").getJSONArray("und");
			ArrayList<String> tabs = new ArrayList<String>();
			for (int i = 0; i < taxonomy_vocabulary_2.length(); i++) {
				tabs.add(taxonomy_vocabulary_2.getJSONObject(i)
						.getString("tid"));
			}
			newInfoBean.setTaxonomy_vocabulary_2(tabs);
		}
		if (!data.isNull("taxonomy_vocabulary_3")
				&& !data.getString("taxonomy_vocabulary_3").equals("[]")) {

			JSONArray taxonomy_vocabulary_3 = data.getJSONObject(
					"taxonomy_vocabulary_3").getJSONArray("und");
			ArrayList<TV3Bean> tabs = new ArrayList<TV3Bean>();
			for (int i = 0; i < taxonomy_vocabulary_3.length(); i++) {
				TV3Bean tv3Bean = new TV3Bean();
				tv3Bean.setName(taxonomy_vocabulary_3.getJSONObject(i)
						.getString("name"));
				tv3Bean.setTid(taxonomy_vocabulary_3.getJSONObject(i)
						.getString("tid"));

				tabs.add(tv3Bean);
			}
			newInfoBean.setTaxonomy_vocabulary_3(tabs);
		}
		setNewInfoBean(newInfoBean);
	}

	public NewInfoBean getNewInfoBean() {
		return newInfoBean;
	}

	public void setNewInfoBean(NewInfoBean newInfoBean) {
		this.newInfoBean = newInfoBean;
	}

}
