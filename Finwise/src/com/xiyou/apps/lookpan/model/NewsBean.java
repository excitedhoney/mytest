package com.xiyou.apps.lookpan.model;

import com.google.api.client.util.Key;
import com.xiyou.apps.lookpan.adapter.ImageAdapter;

public class NewsBean {
	@Key
	String nid;
	@Key
	String node_title;
	@Key
	String node_created;
	@Key
	String file_managed_file_usage_uri;

	ImageAdapter mImageAdapter;

	public ImageAdapter getmImageAdapter() {
		return mImageAdapter;
	}

	@Override
	public String toString() {
		return "NewsBean [nid=" + nid + ", node_title=" + node_title
				+ ", node_created=" + node_created
				+ ", file_managed_file_usage_uri="
				+ file_managed_file_usage_uri + ", mImageAdapter="
				+ mImageAdapter + "]";
	}

	public void setmImageAdapter(ImageAdapter mImageAdapter) {
		this.mImageAdapter = mImageAdapter;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getNode_title() {
		return node_title;
	}

	public void setNode_title(String node_title) {
		this.node_title = node_title;
	}

	public String getNode_created() {
		return node_created;
	}

	public void setNode_created(String node_created) {
		this.node_created = node_created;
	}

	public String getFile_managed_file_usage_uri() {
		return file_managed_file_usage_uri;
	}

	public void setFile_managed_file_usage_uri(
			String file_managed_file_usage_uri) {
		this.file_managed_file_usage_uri = file_managed_file_usage_uri;
	}

}
