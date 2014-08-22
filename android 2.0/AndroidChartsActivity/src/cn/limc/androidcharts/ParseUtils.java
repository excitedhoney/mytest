package cn.limc.androidcharts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import cn.limc.KLineEntity;
import cn.limc.androidcharts.entity.LineEntity;

public class ParseUtils {

	public static final String TAG = "StockDataUtils ";

	/**
	 * 
	 * 浜ゆ槗鏃堕棿,甯傚満绫诲瀷,鍟嗗搧浠ｇ爜,鏄ㄧ粨,鏄ㄦ敹,鐜颁环,浠婂紑,鏈�珮,鏈�綆,鎴愪氦閲�鎴愪氦棰�鎸佷粨閲�涔颁环,鍗栦环,涔伴噺,
	 * 鍗栭噺
	 * 
	 * 閫氳繃鑲＄エ鏁版嵁娴佹潵鑾峰彇鑲＄エ鐨勯泦鍚� 浜ゆ槗鏃堕棿,甯傚満绫诲瀷,鍟嗗搧浠ｇ爜,鏄ㄧ粨,鏄ㄦ敹 4,鐜颁环 5,浠婂紑 6,鏈�珮
	 * 7,鏈�綆 8,鎴愪氦閲� * 9,鎴愪氦棰�鎸佷粨閲�涔颁环,鍗栦环,涔伴噺,鍗栭噺
	 * 
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

	/**
	 * 鑾峰彇boll 鐨勭嚎鏉″�
	 * 
	 * 娉ㄩ噴 鍓嶉潰浼氭湁20鏉＄殑 濉厖鍊� *
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

		double sum = 0;
		double standtard = 0; // 鏍囧噯宸钩鏂瑰拰
		double squarSum = 0;

		int cycle = n;

		if (cycle < 20) {
			cycle = 20;
		}

		for (int i = cycle - 1; i < datas.size(); i++) {
			if (i == cycle - 1) {
				for (int j = cycle - n; j <= cycle - 1; j++) {
					sum += datas.get(j).getZuoShou();
				}
				for (int j = cycle - n; j <= cycle - 1; j++) {
					standtard += (datas.get(j).getZuoShou() - (sum / n))
							* (datas.get(j).getZuoShou() - (sum / n));
				}
				squarSum = Math.sqrt(standtard / n);

			} else {
				sum = sum + datas.get(i).getZuoShou()
						- datas.get(i - n).getZuoShou();
				standtard = Math.abs((standtard
						+ (datas.get(i).getZuoShou() - sum / n)
						* (datas.get(i).getZuoShou() - sum / n) - (datas.get(
						i - n).getZuoShou() - sum / n)
						* (datas.get(i - n).getZuoShou() - sum / n)));
				squarSum = Math.sqrt(standtard / n);// 鏍囧噯宸�
			}
			zhongEntity = new KLineEntity(sum / n);
			shangEntity = new KLineEntity((sum / n) + squarSum * k);
			xiaEntity = new KLineEntity((sum / n) - squarSum * k);
			zhongList.add(zhongEntity);
			xiaList.add(xiaEntity);
			shangList.add(shangEntity);
		}

		LineEntity<KLineEntity> shangLine = new LineEntity<KLineEntity>();
		shangLine.setDisplay(true);
		shangLine.setLineColor(Color.RED);
		shangLine.setLineData(shangList);
		shangLine.setTitle("Boll 楂樼嚎");
		LineEntity<KLineEntity> zhongLine = new LineEntity<KLineEntity>();
		zhongLine.setDisplay(true);
		zhongLine.setLineColor(Color.GREEN);
		zhongLine.setLineData(zhongList);
		zhongLine.setTitle("Boll 涓嚎");
		LineEntity<KLineEntity> xiaLine = new LineEntity<KLineEntity>();
		xiaLine.setDisplay(true);
		xiaLine.setLineColor(Color.BLUE);
		xiaLine.setLineData(xiaList);
		xiaLine.setTitle("Boll 浣庣嚎");

		linesDatas.add(shangLine);
		linesDatas.add(zhongLine);
		linesDatas.add(xiaLine);

		return linesDatas;
	}

	/***
	 * @param list
	 * @param days
	 *            鍛ㄦ湡鏁� * @return
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
	 * 鑾峰彇涓夌鎸囨爣鐨凪A 鏁版嵁
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
		lineMa1.setLineData(initSma(list, ma1));

		LineEntity<KLineEntity> lineMa2 = new LineEntity<KLineEntity>();
		lineMa2.setTitle("MA" + ma2);
		lineMa2.setLineColor(Color.GREEN);
		lineMa2.setLineData(initSma(list, ma2));

		LineEntity<KLineEntity> lineMa3 = new LineEntity<KLineEntity>();
		lineMa3.setTitle("MA" + ma3);
		lineMa3.setLineColor(Color.BLUE);
		lineMa3.setLineData(initSma(list, ma3));

		lineDatas.add(lineMa1);
		lineDatas.add(lineMa2);
		lineDatas.add(lineMa3);

		return lineDatas;
	}

	/**
	 * 鎸囨暟骞冲潎鎸囨爣
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
		double lastEma = list.get(0).getZuoShou(); // 榛樿鐨勭涓�釜ema
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

		double lastEma = list.get(0).getZuoShou(); // 榛樿鐨勭涓�釜ema
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
	 * 鏍规嵁鍓嶉潰璁＄畻鍑烘潵鐨�DIF 璁＄畻浠栫殑DEA
	 * 
	 * @param list
	 * @param z
	 * 
	 *            绗竴涓�index Math.max(x,y) + z
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

	// MACD dif 鍜�dea 绾�
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
		return stickData;
	}

	/**
	 * =========== 闅忔満鎸囨爣 KDJ RSV n=(Cn锛峀n)/(Hn锛峀n)*100
	 * K鍊硷細Kn=K(n-1)*2/3+RSVn*1/3 D鍊硷細Dn=D(n-1)*2/3+Kn*1/3 J鍊硷細Jn=3*Kn-2*Dn
	 * 鍏朵粬锛氳嫢鏃犲墠涓�棩鐨凨銆丏鍊�鍙互瀹氫负50
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
			for (int j = i - n + 1; j <= i; j++) {
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
	 * RSV n=(Cn锛峀n)/(Hn锛峀n)*100 K鍊硷細Kn=K(n-1)*2/3+RSVn*1/3
	 * D鍊硷細Dn=D(n-1)*2/3+Kn*1/3 J鍊硷細Jn=3*Kn-2*Dn
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

		List<KLineEntity> rsvValue = new ArrayList<KLineEntity>();
		List<KLineEntity> jValue = new ArrayList<KLineEntity>();

		double lastK = 50;
		double lastD = 50;

		List<KLineEntity> maxs = getRsvMaxValue(list, n);
		List<KLineEntity> mins = getRsvMinValue(list, n);
		int count = 0;
		double rsv = 0;

		for (int i = cycle - 1; i < list.size(); i++) {
			rsv = 100
					* (list.get(i).getZuoShou() - mins.get(count)
							.getNormValue())
					/ (maxs.get(count).getNormValue() - mins.get(count)
							.getNormValue());
			double k = lastK * 2 / 3.0 + rsv / 3.0;
			double d = lastD * 2 / 3.0 + k / 3.0;
			double j = 3 * k - 2 * d;
			lastK = k;
			lastD = d;
			count++;
			rsvValue.add(new KLineEntity(rsv));
			jValue.add(new KLineEntity(j));
		}

		List<LineEntity<KLineEntity>> lineDatas = new ArrayList<LineEntity<KLineEntity>>();

		LineEntity<KLineEntity> revLine = new LineEntity<KLineEntity>();
		revLine.setLineData(rsvValue);
		revLine.setLineColor(Color.RED);
		revLine.setTitle("RSV" + n);
		lineDatas.add(revLine);

		LineEntity<KLineEntity> jLine = new LineEntity<KLineEntity>();
		jLine.setLineData(jValue);
		jLine.setLineColor(Color.BLUE);
		jLine.setTitle("J");
		lineDatas.add(jLine);

		return lineDatas;
	}

	/**
	 * 鐩稿寮哄急鎸囨爣 rsi
	 * 
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
			for (int j = i - days; j <= i; j++) {
				if (j == i - days) {
					if (i - days >= 0) {
						lastVlaue = list.get(i - days).getZuoShou();
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
