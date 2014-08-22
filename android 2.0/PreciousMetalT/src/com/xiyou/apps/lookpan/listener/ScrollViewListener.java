package com.xiyou.apps.lookpan.listener;

import widget.ObservableScrollView;

public interface ScrollViewListener {
	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);  
}
