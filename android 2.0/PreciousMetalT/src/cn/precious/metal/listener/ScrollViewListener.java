package cn.precious.metal.listener;

import widget.ObservableScrollView;

public interface ScrollViewListener {
	void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);  
}
