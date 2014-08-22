package cn.precious.metal.ui.fragment.chart;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.precious.metal.R;
import cn.precious.metal.base.BaseFragment;
import cn.precious.metal.common.ServiceException;
import cn.precious.metal.services.KlineService;
import cn.precious.metal.tools.ExcepUtils;
import cn.precious.metal.ui.fragment.chart.KLineFragment.GetKlineDataTask;
import cn.precious.metal.view.KLineEntity;
import cn.precious.metal.view.ParseUtils;
import cn.precious.metal.view.entity.LineEntity;
import cn.precious.metal.view.view.SlipFenshiAreaChart;

/**
 * 分时趋线图 
 * @author mac
 *
 */

public class FenshiChartFragment extends BaseFragment{
	
	public static final String TAG = "FenshiChartFragment"  ;
			
	private SlipFenshiAreaChart linechart ;
	
	private double maxValue = 0;
	
	private double minValue = 0;
	
	
	
	public static FenshiChartFragment newInstance(String treaty,String type,String interval){
		FenshiChartFragment fragment = new FenshiChartFragment() ;
		Bundle bundle = new Bundle() ;
		bundle.putString("treaty", treaty);
		bundle.putString("type", type);
		bundle.putString("interval", interval);
		fragment.setArguments(bundle); 
		return fragment ;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_fenshi_chart, null) ;
		initView(view);
		return view;
	}
	
	private GetKlineDataTask task ;
	
	public void initView(View view){
		linechart = (SlipFenshiAreaChart) view.findViewById(R.id.line_chart) ;
		task = new GetKlineDataTask() ;
		task.execute(new String[]{getArguments().getString("treaty"),getArguments().getString("type"),getArguments().getString("interval")}) ;
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(task != null && task.getStatus() == Status.RUNNING) {
			task.cancel(true) ;
		}
	}
	
	
	public void initLineChart(){
		List<LineEntity<KLineEntity>> lines = new ArrayList<LineEntity<KLineEntity>>();

		LineEntity<KLineEntity> line1 = new LineEntity<KLineEntity>() ;
		line1.setLineColor(Color.WHITE);
		
		if(kLineEntities != null && !kLineEntities.isEmpty()) {
			for (int i = 0; i < kLineEntities.size(); i++) {
				if(i == 0){
					maxValue = kLineEntities.get(i).getZuoShou() ;
					minValue = kLineEntities.get(i).getZuoShou() ;
				}else{
					if(maxValue < kLineEntities.get(i).getZuoShou()){
						maxValue = kLineEntities.get(i).getZuoShou() ;
					}
					if(minValue > kLineEntities.get(i).getZuoShou()){
						minValue = kLineEntities.get(i).getZuoShou() ;
					}
				}
			}
		}
		line1.setLineData(kLineEntities);
		lines.add(line1);
		linechart.setAxisXColor(Color.parseColor("#2D2D2D"));
		linechart.setAxisYColor(Color.parseColor("#2D2D2D"));
		linechart.setBorderColor(Color.parseColor("#2D2D2D"));
		linechart.setLongitudeFontSize(18);
		linechart.setLatitudeFontSize(18);
		linechart.setLongitudeFontColor(Color.WHITE);
		linechart.setLatitudeColor(Color.parseColor("#2D2D2D"));
		linechart.setLatitudeFontColor(Color.WHITE);
		linechart.setLongitudeColor(Color.parseColor("#2D2D2D"));
		linechart.setDisplayLongitudeTitle(true);
		linechart.setDisplayLatitudeTitle(true);
		linechart.setDisplayLatitude(true);
		linechart.setDisplayLongitude(true);
		linechart.setLatitudeNum(5);
		linechart.setLongitudeNum(5);
		linechart.setDataQuadrantPaddingTop(0);
		linechart.setDataQuadrantPaddingBottom(0);
		linechart.setDataQuadrantPaddingLeft(0);
		linechart.setDataQuadrantPaddingRight(0);
		linechart.setAxisYTitleQuadrantWidth(80);
		linechart.setAxisXTitleQuadrantHeight(20);
//		linechart.setBackgroundColor(Color.TRANSPARENT);
		linechart.setBackgroundColor(Color.parseColor("#060A0B"));
		
		// 为chart1增加均线
		linechart.setLinesData(lines);
	}
	
	private List<KLineEntity> kLineEntities;
	
	class GetKlineDataTask extends AsyncTask<String, Void, String>{
		
		private List<KLineEntity> list ;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			KlineService service = new KlineService(getActivity());
			try {
				kLineEntities = service.getKlineByinterval(params[0], params[1], params[2]) ;
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				ExcepUtils.showImpressiveException(getActivity(), null, e);
				return "error" ;
			}
			
			if (kLineEntities != null && kLineEntities.size() > 0) {
				return "success" ;
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if("success".equals(result)) {
				initLineChart();
			}else if("error".equals(result)){
				
			}else{
				Toast.makeText(getActivity(), "获取数据失败", 1000).show() ;
			}
		}
		
	}
	
}
