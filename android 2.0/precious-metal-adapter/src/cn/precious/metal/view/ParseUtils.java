package cn.precious.metal.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import cn.precious.metal.view.entity.LineEntity;

public class ParseUtils {

	public static final String TAG = "StockDataUtils ";

	/**
	 * @param is
	 * @return
	 */

	public static List<KLineEntity> getKLineDatas(InputStream is) {
		List<KLineEntity> list = new ArrayList<KLineEntity>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;
			KLineEntity entity = null;
			while ((line = reader.readLine()) != null) {
				if (!"".equalsIgnoreCase(line)) {
					String[] sDatas = line.split(",");
					try {
						String date = sDatas[0];
						double zuoShou = Double.parseDouble(sDatas[4]);
						double currentPrice = Double.parseDouble(sDatas[5]);
						double open = Double.parseDouble(sDatas[6]);
						double high = Double.parseDouble(sDatas[7]);
						double low = Double.parseDouble(sDatas[8]);
						double volume = Double.parseDouble(sDatas[9]);

						entity = new KLineEntity(date, zuoShou, open, high,
								low, currentPrice, volume);
						list.add(entity);
					} catch (Exception e) {
						// TODO: handle exception
						continue;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

//	/**
//
//	 * 
//	 * @param datas
//	 * @param n
//	 * @param k
//	 * @return
//	 */
//	public static List<LineEntity<KLineEntity>> boll(List<KLineEntity> datas,
//			int n, int k) {
//		
//		if (datas == null || datas.isEmpty())
//			return null;
//		List<LineEntity<KLineEntity>> linesDatas = new ArrayList<LineEntity<KLineEntity>>();
//		List<KLineEntity> zhongList = new ArrayList<KLineEntity>();
//		List<KLineEntity> shangList = new ArrayList<KLineEntity>();
//		List<KLineEntity> xiaList = new ArrayList<KLineEntity>();
//
//		KLineEntity zhongEntity;
//		KLineEntity shangEntity;
//		KLineEntity xiaEntity;
//
//		double sum = 0;
//		double standtard = 0; // 
//		double squarSum = 0;
//
//		int cycle = n;
//
//		if (cycle < 20) {
//			cycle = 20;
//		}
//
//		for (int i = cycle - 1; i < datas.size(); i++) {
//			if (i == cycle - 1) {
//				
//				for (int j = cycle - n; j <= cycle - 1; j++) {
//					sum += datas.get(j).getZuoShou();
//				}
//				
//				for (int j = cycle - n; j <= cycle - 1; j++) {
//					standtard += (datas.get(j).getZuoShou() - (sum / n))
//							* (datas.get(j).getZuoShou() - (sum / n));
//				}
//				squarSum = Math.sqrt(standtard / n);
//
//			} else {
//				sum = sum + datas.get(i).getZuoShou()
//						- datas.get(i - n).getZuoShou();
//				standtard = Math.abs((standtard
//						+ (datas.get(i).getZuoShou() - sum / n)
//						* (datas.get(i).getZuoShou() - sum / n) - (datas.get(
//						i - n).getZuoShou() - sum / n)
//						* (datas.get(i - n).getZuoShou() - sum / n)));
//				squarSum = Math.sqrt(standtard / n);
//			}
//			zhongEntity = new KLineEntity(sum / n);
//			shangEntity = new KLineEntity((sum / n) + squarSum * k);
//			xiaEntity = new KLineEntity((sum / n) - squarSum * k);
//			zhongList.add(zhongEntity);
//			xiaList.add(xiaEntity);
//			shangList.add(shangEntity);
//		}
//
//		LineEntity<KLineEntity> shangLine = new LineEntity<KLineEntity>();
//		shangLine.setDisplay(true);
//		shangLine.setLineColor(Color.RED);
//		shangLine.setLineData(shangList);
//		shangLine.setTitle("Boll 上轨");
//		LineEntity<KLineEntity> zhongLine = new LineEntity<KLineEntity>();
//		zhongLine.setDisplay(true);
//		zhongLine.setLineColor(Color.GREEN);
//		zhongLine.setLineData(zhongList);
//		zhongLine.setTitle("Boll 中轨");
//		LineEntity<KLineEntity> xiaLine = new LineEntity<KLineEntity>();
//		xiaLine.setDisplay(true);
//		xiaLine.setLineColor(Color.BLUE);
//		xiaLine.setLineData(xiaList);
//		xiaLine.setTitle("Boll 下轨");
//
//		linesDatas.add(shangLine);
//		linesDatas.add(zhongLine);
//		linesDatas.add(xiaLine);
//
//		return linesDatas;
//	}
	
	
	
	/**

	 * 
	 * @param datas
	 * @param n
	 * @param k
	 * @return
	 */
	public static List<LineEntity<KLineEntity>> boll(List<KLineEntity> datas,
			int n, int k) {
		
		if (datas == null || datas.isEmpty())
			return null;
		List<LineEntity<KLineEntity>> linesDatas = new ArrayList<LineEntity<KLineEntity>>();
		List<KLineEntity> zhongList = new ArrayList<KLineEntity>();
		List<KLineEntity> shangList = new ArrayList<KLineEntity>();
		List<KLineEntity> xiaList = new ArrayList<KLineEntity>();

		KLineEntity zhongEntity;
		KLineEntity shangEntity;
		KLineEntity xiaEntity;

		double standtard = 0; // 
		double squarSum = 0;

		int cycle = n;

		if (cycle < 20) {
			cycle = 20;
		}
		
		List<KLineEntity> sma = initSma(datas, n) ;

		for (int i = cycle - 1; i < datas.size(); i++) {
			double smaValue = sma.get(i - n + 1).getNormValue()  ;
			
			standtard = 0 ;
			
			for (int j = i - n + 1; j <= i ; j++) {
				standtard += (datas.get(j).getZuoShou() - smaValue)
					* (datas.get(j).getZuoShou() - smaValue);
			}
			
			squarSum = Math.sqrt(standtard / n);
			
			
//			if (i == cycle - 1) {
//				
//				for (int j = cycle - n; j <= cycle - 1; j++) {
//					standtard += (datas.get(j).getZuoShou() - smaValue)
//							* (datas.get(j).getZuoShou() - smaValue);
//				}
//				squarSum = Math.sqrt(standtard / n);
//
//			} else {
//				standtard = Math.abs((standtard
//						+ (datas.get(i).getZuoShou() - smaValue)
//						* (datas.get(i).getZuoShou() - smaValue) - (datas.get(
//						i - n).getZuoShou() - smaValue)
//						* (datas.get(i - n).getZuoShou() - smaValue)));
//				squarSum = Math.sqrt(standtard / n);
//			}
			zhongEntity = new KLineEntity(smaValue);
			shangEntity = new KLineEntity(smaValue + squarSum * k);
			xiaEntity = new KLineEntity(smaValue - squarSum * k);
			zhongList.add(zhongEntity);
			xiaList.add(xiaEntity);
			shangList.add(shangEntity);
		}
		
		shangList.add(new KLineEntity()) ;
		shangList.add(new KLineEntity()) ;
		shangList.add(new KLineEntity()) ;
		shangList.add(new KLineEntity()) ;
		shangList.add(new KLineEntity()) ;
		
		zhongList.add(new KLineEntity()) ;
		zhongList.add(new KLineEntity()) ;
		zhongList.add(new KLineEntity()) ;
		zhongList.add(new KLineEntity()) ;
		zhongList.add(new KLineEntity()) ;
		
		xiaList.add(new KLineEntity()) ;
		xiaList.add(new KLineEntity()) ;
		xiaList.add(new KLineEntity()) ;
		xiaList.add(new KLineEntity()) ;
		xiaList.add(new KLineEntity()) ;
		
		

		LineEntity<KLineEntity> shangLine = new LineEntity<KLineEntity>();
		shangLine.setDisplay(true);
		shangLine.setLineColor(Color.RED);
		shangLine.setLineData(shangList);
		shangLine.setTitle("Boll 上轨");
		LineEntity<KLineEntity> zhongLine = new LineEntity<KLineEntity>();
		zhongLine.setDisplay(true);
		zhongLine.setLineColor(Color.GREEN);
		zhongLine.setLineData(zhongList);
		zhongLine.setTitle("Boll 中轨");
		LineEntity<KLineEntity> xiaLine = new LineEntity<KLineEntity>();
		xiaLine.setDisplay(true);
		xiaLine.setLineColor(Color.BLUE);
		xiaLine.setLineData(xiaList);
		xiaLine.setTitle("Boll 下轨");

		linesDatas.add(shangLine);
		linesDatas.add(zhongLine);
		linesDatas.add(xiaLine);

		return linesDatas;
	}
	
	
	

	/***
	 * @param list
	 * @param days
	 */
	private static List<KLineEntity> initSma(List<KLineEntity> list, int days) {
		if (days < 2) {
			return null;
		}

		int cycle = days;

		if (cycle < 20) {
			cycle = 20;
		}

		double sum = 0;

		List<KLineEntity> ma5Values = new ArrayList<KLineEntity>();

		for (int i = cycle - 1; i < list.size(); i++) {
			if (i == cycle - 1) {
				for (int j = i - days + 1; j <= i; j++) {
					sum += list.get(j).getZuoShou();
				}
			} else {
				sum = sum + list.get(i).getZuoShou()
						- list.get(i - days).getZuoShou();
			}
			ma5Values.add(new KLineEntity(sum / days));

		}

		return ma5Values;
	}

	/**
	 * 
	 * @param list
	 * @param ma1
	 * @param ma2
	 * @param ma3
	 * @return
	 */

	public static List<LineEntity<KLineEntity>> getSimpleMaLineData(
			List<KLineEntity> list, int ma1, int ma2, int ma3) {
		List<LineEntity<KLineEntity>> lineDatas = new ArrayList<LineEntity<KLineEntity>>();

		LineEntity<KLineEntity> lineMa1 = new LineEntity<KLineEntity>();
		lineMa1.setTitle("MA" + ma1);
		lineMa1.setLineColor(Color.RED);
		List<KLineEntity> ma1List= initSma(list, ma1) ;
		
		if(ma1List != null && !ma1List.isEmpty()){
			ma1List.add(new KLineEntity()) ;
			ma1List.add(new KLineEntity()) ;
			ma1List.add(new KLineEntity()) ;
			ma1List.add(new KLineEntity()) ;
			ma1List.add(new KLineEntity()) ;
		}
		
		lineMa1.setLineData(ma1List);
		
		
		

		LineEntity<KLineEntity> lineMa2 = new LineEntity<KLineEntity>();
		lineMa2.setTitle("MA" + ma2);
		lineMa2.setLineColor(Color.GREEN);
		List<KLineEntity> ma2List= initSma(list, ma2) ;
		if(ma2List != null && !ma2List.isEmpty()){
			ma2List.add(new KLineEntity()) ;
			ma2List.add(new KLineEntity()) ;
			ma2List.add(new KLineEntity()) ;
			ma2List.add(new KLineEntity()) ;
			ma2List.add(new KLineEntity()) ;
		}
		lineMa2.setLineData(ma2List);

		LineEntity<KLineEntity> lineMa3 = new LineEntity<KLineEntity>();
		lineMa3.setTitle("MA" + ma3);
		lineMa3.setLineColor(Color.BLUE);
		
		List<KLineEntity> ma3List= initSma(list, ma3) ;
		if(ma3List != null && !ma3List.isEmpty()){
			ma3List.add(new KLineEntity()) ;
			ma3List.add(new KLineEntity()) ;
			ma3List.add(new KLineEntity()) ;
			ma3List.add(new KLineEntity()) ;
			ma3List.add(new KLineEntity()) ;
		}
		lineMa3.setLineData(ma3List);

		lineDatas.add(lineMa1);
		lineDatas.add(lineMa2);
		lineDatas.add(lineMa3);

		return lineDatas;
	}

	/**
	 * 
	 * @param list
	 * @param n
	 * @return
	 */
	public static LineEntity<KLineEntity> getEMALineData(
			List<KLineEntity> list, int n) {
		LineEntity<KLineEntity> line = new LineEntity<KLineEntity>();

		if (list == null || list.size() < 20)
			return null;

		int cycle = n;
		if (n < 20) {
			cycle = 20;
		}

		List<KLineEntity> datas = new ArrayList<KLineEntity>();
		double lastEma = list.get(0).getZuoShou(); // 
		for (int i = 1; i < list.size(); i++) {
			if (i <= cycle - 1) {
				double currentEma = 2 * (list.get(i).getZuoShou() - lastEma)
						/ (n + 1) + lastEma;
				lastEma = currentEma;
				continue;
			} else {
				lastEma = 2 * (list.get(i).getZuoShou() - lastEma) / (n + 1)
						+ lastEma;
				datas.add(new KLineEntity(lastEma));
			}
		}
		
		datas.add(new KLineEntity()) ;
		datas.add(new KLineEntity()) ;
		datas.add(new KLineEntity()) ;
		datas.add(new KLineEntity()) ;
		datas.add(new KLineEntity()) ;
		
		line.setLineColor(Color.RED);
		line.setLineData(datas);
		line.setTitle("EMA");
		return line;
	}

	/**
	 * @param list
	 * @param n
	 * @return
	 */
	public static List<KLineEntity> getEmaValueByIndex(List<KLineEntity> list,
			int n) {

		List<KLineEntity> lines = new ArrayList<KLineEntity>();

		double lastEma = list.get(0).getZuoShou();
		lines.add(new KLineEntity(lastEma));
		for (int i = 1; i < list.size(); i++) {
			double currentEma = 2 * (list.get(i).getZuoShou() - lastEma)
					/ (n + 1) + lastEma;
			lastEma = currentEma;
			lines.add(new KLineEntity(lastEma));
		}
		return lines;
	}

	private static List<KLineEntity> getDifDatas(List<KLineEntity> list, int x,
			int y) {
		int cycle = Math.max(x, y);
		if (cycle < 20) {
			cycle = 20;
		}
		List<KLineEntity> listX = getEmaValueByIndex(list, x);
		List<KLineEntity> listY = getEmaValueByIndex(list, y);

		List<KLineEntity> difs = new ArrayList<KLineEntity>();

		for (int i = cycle - 1; i < list.size(); i++) {
			difs.add(new KLineEntity(listX.get(i).getNormValue()
					- listY.get(i).getNormValue()));
		}
		return difs;
	}

	/**
	 * 
	 * @param list
	 * @param z
	 * 
	 * 
	 * @return
	 */
	private static List<KLineEntity> getDeaDatas(List<KLineEntity> list, int x,
			int y, int z) {

		List<KLineEntity> difDatas = getDifDatas(list, x, y); // 鑾峰彇 dif 鍊�
		if (difDatas == null || difDatas.isEmpty())
			return null;

		List<KLineEntity> datas = new ArrayList<KLineEntity>();
		double lastEma = difDatas.get(0).getNormValue();
		double lastDEA = 0;
		for (int i = 1; i < difDatas.size(); i++) {
			if (i < z) {
				double currentEma = 2
						* (difDatas.get(i).getNormValue() - lastEma) / (z + 1)
						+ lastEma;
				lastEma = currentEma;
				if (i == z - 1)
					lastDEA = lastEma;
				continue;
			} else {
				lastDEA = (lastDEA * (z - 1) / (z + 1))
						+ (difDatas.get(i).getNormValue() * 2 / (z + 1));
				datas.add(new KLineEntity(lastDEA));
			}
		}
		return datas;
	}

	public static List<LineEntity<KLineEntity>> getMacdLineDatas(
			List<KLineEntity> list, int x, int y, int z) {

		List<LineEntity<KLineEntity>> lineDatas = new ArrayList<LineEntity<KLineEntity>>();

		List<KLineEntity> difData = getDifDatas(list, x, y);

		List<KLineEntity> deaData = getDeaDatas(list, x, y, z);

		LineEntity<KLineEntity> difLine = new LineEntity<KLineEntity>();
		difLine.setTitle("DIF");
		difLine.setLineData(difData);
		difLine.setLineColor(Color.RED);
		lineDatas.add(difLine);
		LineEntity<KLineEntity> deaLine = new LineEntity<KLineEntity>();
		deaLine.setTitle("DEA");
		deaLine.setLineData(deaData);
		deaLine.setLineColor(Color.GREEN);
		lineDatas.add(deaLine);

		return lineDatas;
	}

	public static List<KLineEntity> getMaceStickDatas(List<KLineEntity> list,
			int x, int y, int z) {
		List<KLineEntity> difList = getDifDatas(list, x, y);

		List<KLineEntity> deaList = getDeaDatas(list, x, y, z);

		List<KLineEntity> stickData = new ArrayList<KLineEntity>();

		double macd = 0;
		
		if(deaList != null && !deaList.isEmpty()) {
			for (int i = 0; i < deaList.size(); i++) {
				if (i + z >= difList.size()) {
					break;
				}
				macd = difList.get(i + z).getNormValue()
						- deaList.get(i).getNormValue();
				if (macd > 0) {
					stickData.add(new KLineEntity(macd, 0));
				} else {
					stickData.add(new KLineEntity(0, macd));
				}
			}
		}
		
		return stickData;
	}

	/**
	 */
	public static List<KLineEntity> getRsvMaxValue(List<KLineEntity> list, int n) {
		int cycle = n;
		if (n < 20) {
			cycle = 20;
		}
		double maxValue = 0;

		List<KLineEntity> maxList = new ArrayList<KLineEntity>();

		for (int i = cycle - 1; i < list.size(); i++) {
			maxValue = 0;
			for (int j = i - n + 1; j <= i; j++) {
				if (j == i - n + 1) {
					maxValue = list.get(j).getHigh();
				} else {
					if (maxValue < list.get(j).getHigh()) {
						maxValue = list.get(j).getHigh();
					}
				}
			}
			maxList.add(new KLineEntity(maxValue));
		}
		return maxList;
	}

	public static List<KLineEntity> getRsvMinValue(List<KLineEntity> list, int n) {
		int cycle = n;
		if (n < 20) {
			cycle = 20;
		}
		double minValue = 0;

		List<KLineEntity> mixList = new ArrayList<KLineEntity>();
		
		
	
		

		for (int i = cycle - 1; i < list.size(); i++) {
			minValue = 0;
			for (int j = i - n + 1; j <= i ; j++) {
				if (j == i - n + 1) {
					minValue = list.get(j).getLow();
				} else {
					if (minValue > list.get(j).getLow()) {
						minValue = list.get(j).getLow();
					}
				}
			}
			
			
			mixList.add(new KLineEntity(minValue));
		}
		return mixList;
	}

	/**
	 * 
	 * @param list
	 * @param n
	 * @return
	 */
	public static List<LineEntity<KLineEntity>> getKDJLinesDatas(
			List<KLineEntity> list, int n) {
		int cycle = n;
		if (n < 20) {
			cycle = 20;
		}

		List<KLineEntity> kValue = new ArrayList<KLineEntity>();
		List<KLineEntity> dValue = new ArrayList<KLineEntity>();
		List<KLineEntity> jValue = new ArrayList<KLineEntity>();

		double lastK = 50;
		double lastD = 50;

		List<KLineEntity> maxs = getRsvMaxValue(list, n);
		List<KLineEntity> mins = getRsvMinValue(list, n);
		int count = 0;
		double rsv = 0;

		for (int i = cycle - 1; i < list.size(); i++) {
			
			
			if((maxs.get(count).getNormValue() - mins.get(count)
					.getNormValue())==0){
				rsv = 0 ;
			}else{
				rsv = 100
						* (list.get(i).getZuoShou() - mins.get(count)
								.getNormValue())
								/ (maxs.get(count).getNormValue() - mins.get(count)
										.getNormValue());
			}
			double k = lastK * 2 / 3.0 + rsv / 3.0;
			double d = lastD * 2 / 3.0 + k / 3.0;
			double j = 3 * k - 2 * d;
			lastK = k;
			lastD = d;
			count++;
			kValue.add(new KLineEntity(k));
			dValue.add(new KLineEntity(d));
//			jValue.add(new KLineEntity(j));
		}

		List<LineEntity<KLineEntity>> lineDatas = new ArrayList<LineEntity<KLineEntity>>();

		LineEntity<KLineEntity> kLine = new LineEntity<KLineEntity>();
		kLine.setLineData(kValue);
		kLine.setLineColor(Color.RED);
		lineDatas.add(kLine);
		
		LineEntity<KLineEntity> dLine = new LineEntity<KLineEntity>();
		dLine.setLineData(dValue);
		dLine.setLineColor(Color.GREEN);
		lineDatas.add(dLine);

//		LineEntity<KLineEntity> jLine = new LineEntity<KLineEntity>();
//		jLine.setLineData(jValue);
//		jLine.setLineColor(Color.BLUE);
//		jLine.setTitle("J");
//		lineDatas.add(jLine);

		return lineDatas;
	}

	/**
	 * ===============
	 */

	public static List<KLineEntity> initRSIdatas(List<KLineEntity> list,
			int days) {
		List<KLineEntity> rsi = new ArrayList<KLineEntity>();

		int cycle = days;
		if (days < 20) {
			cycle = 20;
		}

		double riseSum = 0;
		double fallSum = 0;

		double lastVlaue = 0;

		for (int i = cycle - 1; i < list.size(); i++) {
			for (int j = i - days + 1; j <= i; j++) {
				if (j == i - days + 1) {
					if ( (i - days)  >= 0) {
						lastVlaue = list.get(i - days).getZuoShou();
						if (list.get(j).getZuoShou() >= lastVlaue) {
							riseSum += list.get(j).getZuoShou();
						} else {
							fallSum += list.get(j).getZuoShou();
						}
						
					} else {
						lastVlaue = list.get(i - days + 1).getZuoShou();
					}
				} else {
					if (j < 0) {
						continue;
					} else {
						if (list.get(j).getZuoShou() >= lastVlaue) {
							riseSum += list.get(j).getZuoShou();
						} else {
							fallSum += list.get(j).getZuoShou();
						}
						lastVlaue = list.get(j).getZuoShou();
					}
				}
			}
			rsi.add(new KLineEntity(Math.abs((riseSum / days)
					/ (riseSum / days + fallSum / days) * 100)));

			riseSum = 0;
			fallSum = 0;
			lastVlaue = 0;

		}
		return rsi;
	}

	/**
	 * 鐩稿寮哄急鎸囨爣 x y z 鍒嗗埆鏄笁绉嶆寚鏍� *
	 * 
	 * @param list
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */

	public static List<LineEntity<KLineEntity>> getRsiLineDatas(
			List<KLineEntity> list, int x, int y, int z) {
		List<LineEntity<KLineEntity>> lineDatas = new ArrayList<LineEntity<KLineEntity>>();

		LineEntity<KLineEntity> xLine = new LineEntity<KLineEntity>();
		xLine.setLineColor(Color.RED);
		xLine.setTitle("RSI" + x);
		xLine.setLineData(initRSIdatas(list, x));
		lineDatas.add(xLine);

		LineEntity<KLineEntity> yLine = new LineEntity<KLineEntity>();
		yLine.setLineColor(Color.GREEN);
		yLine.setTitle("RSI" + y);
		yLine.setLineData(initRSIdatas(list, y));
		lineDatas.add(yLine);

		LineEntity<KLineEntity> zLine = new LineEntity<KLineEntity>();
		zLine.setLineColor(Color.BLUE);
		zLine.setTitle("RSI" + z);
		zLine.setLineData(initRSIdatas(list, z));
		lineDatas.add(zLine);

		return lineDatas;

	}
}
