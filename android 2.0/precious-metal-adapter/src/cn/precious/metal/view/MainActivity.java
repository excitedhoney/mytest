package cn.precious.metal.view;
//package cn.limc.androidcharts;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import cn.limc.KLineEntity;
//import cn.limc.androidcharts.entity.DateValueEntity;
//import cn.limc.androidcharts.entity.IChartData;
//import cn.limc.androidcharts.entity.LineEntity;
//import cn.limc.androidcharts.entity.OHLCEntity;
//import cn.limc.androidcharts.view.GridChart;
//import cn.limc.androidcharts.view.UnionCandleStickChart;
//
//public class MainActivity extends Activity {
//	IChartData<OHLCEntity> ohlc;
//
//	private double maxValue = 0;
//	private double minValue = 0;
//
//	private Button boll, other;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.grid_view);
//		initOHLC();
//		
//		initLineChart();
//
//		initButtomData();
//		initButtomData2();
//
//		initunionCaldleStick();
//
//		findViewById(R.id.boll).setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (!UnionCandleStickChart.BOLL
//						.equalsIgnoreCase(unionCaldleStick.getNormType())) {
//					unionCaldleStick.setNormType(UnionCandleStickChart.BOLL);
//				}
//			}
//		});
//
//		findViewById(R.id.kdj).setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (!UnionCandleStickChart.KDJ
//						.equalsIgnoreCase(unionCaldleStick.getNormType())) {
//					unionCaldleStick.setLinesData(lines);
//					unionCaldleStick.setNormType(UnionCandleStickChart.KDJ);
//				}
//			}
//		});
//
//	}
//
//	private UnionCandleStickChart unionCaldleStick;
//
//	private void initunionCaldleStick() {
//		unionCaldleStick = (UnionCandleStickChart) findViewById(R.id.union_candle_stick);
//		unionCaldleStick.setLatitudeColor(Color.GRAY);
//		unionCaldleStick.setLongitudeColor(Color.GRAY);
//		unionCaldleStick.setBorderColor(Color.LTGRAY);
//		unionCaldleStick.setLongitudeFontColor(Color.WHITE);
//		unionCaldleStick.setLatitudeFontColor(Color.WHITE);
//		
//		// 最大显示足数
//		// 最大纬线数
//		unionCaldleStick.setLatitudeNum(5);
//		unionCaldleStick.setButtomLatitudeNum(5);
//		// 最大经线数
//		unionCaldleStick.setLongitudeNum(5);
//		// 最大价格
//		unionCaldleStick.setMaxValue(maxValue);
//		// 最小价格
//		unionCaldleStick.setMinValue(minValue);
//
//		unionCaldleStick.setButtomMaxValue(90000);
//		unionCaldleStick.setButtomMinValue(-90000);
//
//		unionCaldleStick.setDisplayLongitudeTitle(true);
//		unionCaldleStick.setDisplayLatitudeTitle(true);
//		unionCaldleStick.setDisplayLatitude(true);
//		unionCaldleStick.setDisplayLongitude(true);
//		unionCaldleStick.setBackgroundColor(Color.BLACK);
//
//		unionCaldleStick.setAxisYTitleQuadrantWidth(50);
//		unionCaldleStick.setAxisXTitleQuadrantHeight(20);
//
//		unionCaldleStick
//				.setAxisYPosition(UnionCandleStickChart.AXIS_Y_POSITION_RIGHT);
//
//		// 为chart2增加均线
//		unionCaldleStick.setStickData(ohlc);
//
//		unionCaldleStick.setButtomStickData(data);
//	}
//
//	private void initOHLC() {
//		ohlc = ParseUtils.getStockDataByCode(getResources().openRawResource(
//				R.raw.table));
//		for (int i = 0; i < ohlc.size(); i++) {
//			if (i == 0) {
//				maxValue = ohlc.get(i).getHigh();
//				minValue = ohlc.get(i).getLow();
//			} else {
//				if (maxValue < ohlc.get(i).getHigh())
//					maxValue = ohlc.get(i).getHigh();
//				if (minValue > ohlc.get(i).getLow())
//					minValue = ohlc.get(i).getLow();
//			}
//
//		}
//	}
//
//	private List<KLineEntity> list;
//
//	private double buttomMaxValue = 0;
//	private double buttomMinValue = 0;
//
//	private void initButtomData() {
//		list = ParseUtils.getKLineDatas(getResources().openRawResource(
//				R.raw.test));
//		for (int i = 0; i < ohlc.size(); i++) {
//			if (i == 0) {
//				buttomMaxValue = list.get(i).getHigh();
//				buttomMinValue = list.get(i).getLow();
//			} else {
//				if (buttomMaxValue < list.get(i).getHigh())
//					buttomMaxValue = list.get(i).getHigh();
//				if (buttomMinValue > list.get(i).getLow())
//					buttomMinValue = list.get(i).getLow();
//			}
//
//		}
//	}
//
//	List<KLineEntity> data;
//
//	public void initButtomData2() {
//		data = new ArrayList<KLineEntity>();
//		data.add(new KLineEntity(30000, 0));
//		data.add(new KLineEntity(32300, 0));
//		data.add(new KLineEntity(50000, 0));
//		data.add(new KLineEntity(12330, 0));
//		data.add(new KLineEntity(22220, 0));
//		data.add(new KLineEntity(19800, 0));
//		data.add(new KLineEntity(90000, 0));
//		data.add(new KLineEntity(23100, 0));
//		data.add(new KLineEntity(33509, 0));
//		data.add(new KLineEntity(83000, 0));
//		data.add(new KLineEntity(76310, 0));
//		data.add(new KLineEntity(33700, 0));
//		data.add(new KLineEntity(67820, 0));
//		data.add(new KLineEntity(21310, 0));
//		data.add(new KLineEntity(12340, 0));
//		data.add(new KLineEntity(23450, 0));
//		data.add(new KLineEntity(34560, 0));
//		data.add(new KLineEntity(34570, 0));
//		data.add(new KLineEntity(67890, 0));
//		data.add(new KLineEntity(21110, 0));
//		data.add(new KLineEntity(23130, 0));
//		data.add(new KLineEntity(88830, 0));
//		data.add(new KLineEntity(0, -30000));
//		data.add(new KLineEntity(0, -72123));
//		data.add(new KLineEntity(0, -13131));
//		data.add(new KLineEntity(0, -90000));
//		data.add(new KLineEntity(0, -52123));
//		data.add(new KLineEntity(0, -13131));
//		data.add(new KLineEntity(0, -50000));
//		data.add(new KLineEntity(0, -22123));
//		data.add(new KLineEntity(22220, 0));
//		data.add(new KLineEntity(33610, 0));
//		data.add(new KLineEntity(23111, 0));
//		data.add(new KLineEntity(86151, 0));
//		data.add(new KLineEntity(23111, 0));
//		data.add(new KLineEntity(21111, 0));
//		data.add(new KLineEntity(64511, 0));
//		data.add(new KLineEntity(0, -12123));
//		data.add(new KLineEntity(0, -43131));
//		data.add(new KLineEntity(0, -80000));
//		data.add(new KLineEntity(0, -31123));
//		data.add(new KLineEntity(0, -13131));
//		data.add(new KLineEntity(0, -30000));
//		data.add(new KLineEntity(0, -72123));
//		data.add(new KLineEntity(0, -13131));
//		data.add(new KLineEntity(0, -20000));
//		data.add(new KLineEntity(0, -52123));
//		data.add(new KLineEntity(0, -13131));
//		data.add(new KLineEntity(0, -50000));
//		data.add(new KLineEntity(0, -22123));
//		data.add(new KLineEntity(0, -13131));
//		data.add(new KLineEntity(0, -82000));
//		data.add(new KLineEntity(0, -23213));
//		data.add(new KLineEntity(23100, 0));
//		data.add(new KLineEntity(33509, 0));
//		data.add(new KLineEntity(83000, 0));
//		data.add(new KLineEntity(76310, 0));
//		data.add(new KLineEntity(33700, 0));
//		data.add(new KLineEntity(67820, 0));
//		data.add(new KLineEntity(21310, 0));
//		data.add(new KLineEntity(12340, 0));
//		data.add(new KLineEntity(23450, 0));
//		data.add(new KLineEntity(34560, 0));
//		data.add(new KLineEntity(34570, 0));
//		data.add(new KLineEntity(67890, 0));
//		data.add(new KLineEntity(0, -90000));
//		data.add(new KLineEntity(0, -10000));
//		data.add(new KLineEntity(0, -12355));
//		data.add(new KLineEntity(0, -421311));
//		data.add(new KLineEntity(0, -55555));
//		data.add(new KLineEntity(0, -33333));
//		data.add(new KLineEntity(0, -43131));
//		data.add(new KLineEntity(0, -80000));
//		data.add(new KLineEntity(0, -23113));
//		data.add(new KLineEntity(0, -12231));
//		data.add(new KLineEntity(0, -34500));
//	}
//
//	
//	List<LineEntity<DateValueEntity>> lines ;
//	private void initLineChart() {
//		lines = new ArrayList<LineEntity<DateValueEntity>>();
//
//		// 计算5日均线
//		LineEntity<DateValueEntity> MA5 = new LineEntity<DateValueEntity>();
//		MA5.setTitle("MA5");
//		MA5.setLineColor(Color.WHITE);
//		MA5.setLineData(initMA(5));
//		lines.add(MA5);
//
//		// 计算10日均线
//		LineEntity<DateValueEntity> MA10 = new LineEntity<DateValueEntity>();
//		MA10.setTitle("MA10");
//		MA10.setLineColor(Color.RED);
//		MA10.setLineData(initMA(10));
//		lines.add(MA10);
//
//		// 计算25日均线
//		LineEntity<DateValueEntity> MA25 = new LineEntity<DateValueEntity>();
//		MA25.setTitle("MA25");
//		MA25.setLineColor(Color.GREEN);
//		MA25.setLineData(initMA(25));
//		lines.add(MA25);
//
//	}
//	
//	private List<DateValueEntity> initMA(int days) {
//
//		if (days < 2) {
//			return null;
//		}
//
//		List<DateValueEntity> MA5Values = new ArrayList<DateValueEntity>();
//
//		float sum = 0;
//		float avg = 0;
//		for (int i = 0; i < this.ohlc.size(); i++) {
//			float close = (float) ((OHLCEntity) ohlc.get(i)).getClose();
//			if (i < days) {
//				sum = sum + close;
//				avg = sum / (i + 1f);
//			} else {
//				sum = sum + close
//						- (float) ((OHLCEntity) ohlc.get(i - days)).getClose();
//				avg = sum / days;
//			}
//			MA5Values.add(new DateValueEntity(avg, ohlc.get(i).getDate()));
//		}
//
//		return MA5Values;
//	}
//
//}
