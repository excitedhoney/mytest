package com.venus.carpapa;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.venus.carpapa.R;

public class MapActivity extends BaseActivity {
	BMapManager mBMapMan = null;  
	MapView mMapView = null;
	//搜索相关
	MKSearch mSearch = null;	// 搜索模块，也可去掉地图模块独立使用
	private List<String> busLineIDList = null;
	MKRoute route = null;//保存驾车/步行路线数据的变量，供浏览节点时使用
	@Override  
    public void onCreate(Bundle savedInstanceState){  
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(getApplication());
		//mBMapMan.init("02Gx9ayZArr4hqWPTLjKVQ3g", null); 
		//8PKCBvh7VRoQIe77viVTUMvU
		//0wf075Oj8QzFwg0VX9XGR2ha
		mBMapMan.init("0wf075Oj8QzFwg0VX9XGR2ha", null); 
		//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_map);
		mMapView=(MapView)findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		//设置启用内置的缩放控件
		MapController mMapController=mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);//设置地图中心点
		mMapController.setZoom(12);//设置地图zoom级别
		
		
		Drawable mark= getResources().getDrawable(R.drawable.ic_launcher);
		
		OverlayItem item1 = new OverlayItem(point,"item1","item1");
		item1.setMarker(mark);  
		
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView); 
		
		mMapView.getOverlays().clear();  
		mMapView.getOverlays().add(itemOverlay);  
		
		itemOverlay.addItem(item1);  
		 
		mMapView.refresh();  
		
		
        // 初始化搜索模块，注册事件监听
        mSearch = new MKSearch();
        
        mSearch.init(mBMapMan, new MKSearchListener(){

            @Override
            public void onGetPoiDetailSearchResult(int type, int error) {
            }
            
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
		        }
				
				// 找到公交路线poi node
                MKPoiInfo curPoi = null;
                int totalPoiNum  = res.getCurrentNumPois();
                //遍历所有poi，找到类型为公交线路的poi
                busLineIDList.clear();
				for( int idx = 0; idx < totalPoiNum; idx++ ) {
                    if ( 2 == res.getPoi(idx).ePoiType ) {
                        // poi类型，0：普通点，1：公交站，2：公交线路，3：地铁站，4：地铁线路
                        curPoi = res.getPoi(idx);
                        //使用poi的uid发起公交详情检索
                        busLineIDList.add(curPoi.uid);
                        System.out.println(curPoi.uid);
                        
                    }
				}
				SearchNextBusline();
				
				// 没有找到公交信息
                if (curPoi == null) {
                    Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
                    return;
                }
				route = null;
			}
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}
			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}
			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			/**
			 * 获取公交路线结果，展示公交线路
			 */
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
				if (iError != 0 || result == null) {
					Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
		        }

				RouteOverlay routeOverlay = new RouteOverlay(MapActivity.this, mMapView);
			    // 此处仅展示一个方案作为示例
			    routeOverlay.setData(result.getBusRoute());
			    //清除其他图层
			    mMapView.getOverlays().clear();
			    //添加路线图层
			    mMapView.getOverlays().add(routeOverlay);
			    //刷新地图使生效
			    mMapView.refresh();
			    //移动地图到起点
			    mMapView.getController().animateTo(result.getBusRoute().getStart());
			  //将路线数据保存给全局变量
			    route = result.getBusRoute();
			    //重置路线节点索引，节点浏览时使用
//			    nodeIndex = -1;
//			    mBtnPre.setVisibility(View.VISIBLE);
//				mBtnNext.setVisibility(View.VISIBLE);
				Toast.makeText(MapActivity.this, 
						       result.getBusName(), 
						       Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type,
					int error) {
				// TODO Auto-generated method stub
				
			}

        });

    } 
	class OverlayTest extends ItemizedOverlay<OverlayItem> {  
	    //用MapView构造ItemizedOverlay  
	    public OverlayTest(Drawable mark,MapView mapView){  
	            super(mark,mapView);  
	    }  
	    protected boolean onTap(int index) {  
	        //在此处理item点击事件  
	        System.out.println("item onTap: "+index);  
	        return true;  
	    }  
	        public boolean onTap(GeoPoint pt, MapView mapView){  
	                //在此处理MapView的点击事件，当返回 true时  
	                super.onTap(pt,mapView);  
	                return false;  
	        }  
	        // 自2.1.1 开始，使用 add/remove 管理overlay , 无需重写以下接口  
	        /* 
	        @Override 
	        protected OverlayItem createItem(int i) { 
	                return mGeoList.get(i); 
	        } 
	        
	        @Override 
	        public int size() { 
	                return mGeoList.size(); 
	        } 
	        */  
	}          
	public void bus(View v){
		
	}
	void SearchNextBusline(){
//		 if ( busLineIndex >= busLineIDList.size()){
//			 busLineIndex =0;
//		 }
//		 if ( busLineIndex >=0 && busLineIndex < busLineIDList.size() && busLineIDList.size() >0){
//			 mSearch.busLineSearch(((EditText)findViewById(R.id.city)).getText().toString(), busLineIDList.get(busLineIndex));
//			 busLineIndex ++;
//		 }
		 
	}
	@Override
	protected void onDestroy(){
	        mMapView.destroy();
	        if(mBMapMan!=null){
	                mBMapMan.destroy();
	                mBMapMan=null;
	        }
	        super.onDestroy();
	}
	@Override
	protected void onPause(){
	        mMapView.onPause();
	        if(mBMapMan!=null){
	               mBMapMan.stop();
	        }
	        super.onPause();
	}
	@Override
	protected void onResume(){
	        mMapView.onResume();
	        if(mBMapMan!=null){
	                mBMapMan.start();
	        }
	       super.onResume();
	}

}
