package cn.limc.androidcharts.view;

import java.util.ArrayList;
import java.util.List;

import cn.limc.KLineEntity;
import cn.limc.androidcharts.entity.LineEntity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.Button;

public class NormUnionCandleStickChart extends UnionCandleStickChart {

	private String buttomNormType = NORM_MACD; // 指标类型 KDJ、MACD、RSI EMA

	private String topNormType = NORM_SMA; // 指标类型 SMA BOOL

	//
	public static final String NORM_SMA = "NORM_SMA";
	public static final String NORM_BOLL = "NORM_BOLL";

	public static final String NORM_MACD = "NORM_MACD";
	public static final String NORM_KDJ = "NORM_KDJ";
	public static final String NORM_RSI = "NORM_RSI";
	public static final String NORM_EMA = "NORM_EMA";

	public NormUnionCandleStickChart(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public NormUnionCandleStickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NormUnionCandleStickChart(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		calcButtonRange();
		super.onDraw(canvas);
		if (NORM_SMA.equalsIgnoreCase(topNormType)) { // 绘制移动平均线
			drawSMaLines(canvas);
		} else if (NORM_BOLL.equalsIgnoreCase(topNormType)) {
			drawBoolLines(canvas);
		} else if (NORM_EMA.equalsIgnoreCase(topNormType)) {
			drawEMALines(canvas);
		}

		if (NORM_MACD.equalsIgnoreCase(buttomNormType)) {
			drawMacdLine(canvas);
			drawMacdStick(canvas);
		} else if (NORM_KDJ.equalsIgnoreCase(buttomNormType)) {
			drawKDJLines(canvas);
		} else if (NORM_RSI.equalsIgnoreCase(buttomNormType)) {
			drawRSILines(canvas);
		}
	}

	public void calcButtonRange() {
		
		if(NORM_KDJ.equalsIgnoreCase(buttomNormType)){
			buttomMaxValue = 100 ;
			buttomMinValue = 0 ;
			return  ;
		}
		if(null == buttomNormLineDatas || buttomNormLineDatas.size() < 0)
			return  ;
		
		
		for (int j = 0; j < buttomNormLineDatas.size(); j++) {
			List<KLineEntity> lineData = buttomNormLineDatas.get(j).getLineData() ;
			for (int i = 0; i < lineData.size(); i++) {
				if (j == 0 && i == 0 ) {
					buttomMaxValue = lineData.get(i).getNormValue();
					buttomMinValue = lineData.get(i).getNormValue();
				} else {
					if (buttomMaxValue < lineData.get(i).getNormValue()) {
						buttomMaxValue = lineData.get(i).getNormValue();
					}

					if (buttomMinValue > lineData.get(i).getNormValue()) {
						buttomMinValue = lineData.get(i).getNormValue();
					}
				}
			}
		}
		
	}

	// 绘制 boll sma 的线数据
	private List<LineEntity<KLineEntity>> linesData;

	/**
	 * @return the linesData
	 */
	public List<LineEntity<KLineEntity>> getLinesData() {
		return linesData;
	}

	/**
	 * @param linesData
	 *            the linesData to set
	 */
	public void setLinesData(List<LineEntity<KLineEntity>> linesData) {
		this.linesData = linesData;
	}

	/**
	 * 绘制移动平均线条
	 * 
	 * @param canvas
	 */
	protected void drawSMaLines(Canvas canvas) {
		if (null == linesData) {
			return;
		}
		float startX;
		for (int i = 0; i < linesData.size(); i++) {
			LineEntity<KLineEntity> line = linesData.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<KLineEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}

			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// start point
			PointF ptFirst = null;
			// set start point’s X

			int drawLineNum = (int) Math.floor(getDataQuadrantWidth()
					/ (getStickWidth() + 1)) + 1; // 一个界面应该画的数量

			if (drawLineNum >= lineData.size()) {
				drawLineNum = lineData.size() - 1;
			}

			startX = getDataQuadrantEndX() ;

			for (int j = lineData.size() - 1 - getDrawOffset() ; j >  lineData.size() - 1 -drawLineNum  - getDrawOffset(); j--) {
				if (j < 0) {
					break;
				}
				float value = (float) lineData.get(j).getNormValue();
				// calculate Y
				float valueY = (float) ((1f - (value - minValue)
						/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));

				// if is not last point connect to previous point
				if (j != lineData.size() - 1 - getDrawOffset()) {
					canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
							mPaint);
				}
				// reset
				ptFirst = new PointF(startX, valueY);
				startX = startX - 1 - getStickWidth();
			}
		}

		Paint mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(getLatitudeFontSize());
		canvas.drawText("SMA 移动均线", 2f, getLatitudeFontSize(), mTextPaint);
	}

	/**
	 * 绘制Boll线
	 * 
	 * @param canvas
	 */
	protected void drawBoolLines(Canvas canvas) {

		if (null == linesData) {
			return;
		}
		float startX;

		for (int i = 0; i < linesData.size(); i++) {
			LineEntity<KLineEntity> line = linesData.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<KLineEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}

			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// start point
			PointF ptFirst = null;
			// set start point’s X

			int drawLineNum = (int) Math.floor(getDataQuadrantWidth()
					/ (getStickWidth() + 1)) + 1; // 一个界面应该画的数量

			if (drawLineNum >= lineData.size()) {
				drawLineNum = lineData.size() - 1;
			}

			startX = getDataQuadrantEndX() ;

			for (int j = lineData.size() - 1 - getDrawOffset() ; j > lineData.size() - 1 - getDrawOffset() - drawLineNum; j --) {
				if (j < 0) {
					break;
				}
				float value = (float) lineData.get(j).getNormValue();
				// calculate Y
				float valueY = (float) ((1f - (value - minValue)
						/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
				// if is not last point connect to previous point
				if(valueY >= super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight){
					valueY = super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight ;
				}
				
				if (j != lineData.size() - 1 - getDrawOffset()) {
					canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
							mPaint);
				}
				// reset
				ptFirst = new PointF(startX, valueY);
				startX = startX - 1 - getStickWidth();
			}

		}
		Paint mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(getLatitudeFontSize());
		canvas.drawText("BOLL 轨道线", 2f, getLatitudeFontSize(), mTextPaint);
	}

	/**
	 * 绘制平均指数 指标
	 * 
	 * @param canvas
	 */
	protected void drawEMALines(Canvas canvas) {

		if (null == linesData) {
			return;
		}
		float startX;

		for (int i = 0; i < linesData.size(); i++) {
			LineEntity<KLineEntity> line = linesData.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<KLineEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}

			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			PointF ptFirst = null;
			// set start point’s X
			int drawLineNum = (int) Math.floor(getDataQuadrantWidth()
					/ (getStickWidth() + 1)) + 1; // 一个界面应该画的数量
			if (drawLineNum >= lineData.size()) {
				drawLineNum = lineData.size() - 1;
			}
			startX = getDataQuadrantEndX() - getStickWidth();
			for (int j =  lineData.size() - 1 - getDrawOffset(); j >  lineData.size() - 1 - getDrawOffset() - drawLineNum ; j--) {
				if (j < 0) {
					break;
				}
				float value = (float) lineData.get(j).getNormValue();
				// calculate Y
				float valueY = (float) ((1f - (value - minValue)
						/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
				// if is not last point connect to previous point
				if (j != lineData.size() - 1 - getDrawOffset()) {
					canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
							mPaint);
				}
				// reset
				ptFirst = new PointF(startX, valueY);
				startX = startX - 1 - getStickWidth();
			}
		}
		Paint mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(getLatitudeFontSize());
		canvas.drawText("EMA 平均指数", 2f, getLatitudeFontSize(), mTextPaint);
	}

	// 副图指标线的数据
	private List<LineEntity<KLineEntity>> buttomNormLineDatas;

	public List<LineEntity<KLineEntity>> getButtomNormLineDatas() {
		return buttomNormLineDatas;
	}

	public void setButtomNormLineDatas(
			List<LineEntity<KLineEntity>> buttomNormLineDatas) {
		this.buttomNormLineDatas = buttomNormLineDatas;
	}

	//
	protected void drawMacdLine(Canvas canvas) {
		if (null == buttomNormLineDatas) {
			return;
		}
		float startX;
		for (int i = 0; i < buttomNormLineDatas.size(); i++) {
			LineEntity<KLineEntity> line = buttomNormLineDatas.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<KLineEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}
			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// start point
			PointF ptFirst = null;
			// set start point’s X
			int drawLineNum = (int) Math.floor(getDataQuadrantWidth()
					/ (getStickWidth() + 1)) + 1; // 一个界面应该画的数量
			if (drawLineNum >= lineData.size()) {
				drawLineNum = lineData.size() - 1;
			}
			startX = getDataQuadrantEndX();

			for (int j =  lineData.size() - 1 - getDrawOffset() ; j >  lineData.size() - 1 - getDrawOffset() - drawLineNum ; j--) {
				if (j  < 0 ) {
					break;
				}
				float value = (float) lineData.get(j).getNormValue();
				// calculate Y
				float valueY = (float) ((1f - (value - buttomMinValue)
						/ (buttomMaxValue - buttomMinValue))
						* super.getHeight() * 1 / 3)
						+ super.getHeight() * 2 / 3;
				// if is not last point connect to previous point
				if (j != lineData.size() - 1 - getDrawOffset()) {
					canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
							mPaint);
				}
				// reset
				ptFirst = new PointF(startX, valueY);
				startX = startX - 1 - getStickWidth();
			}
		}
		Paint mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(getLatitudeFontSize());
		canvas.drawText("MACD 指数平均线", 2f, super.getHeight() * 2 / 3
				+ getLatitudeFontSize(), mTextPaint);

	}

	/**
	 * <p>
	 * 绘制最下面的 柱条
	 * </p>
	 * 
	 * @param canvas
	 */
	private List<KLineEntity> buttomStickData = new ArrayList<KLineEntity>();

	public List<KLineEntity> getButtomStickData() {
		return buttomStickData;
	}

	public void setButtomStickData(List<KLineEntity> buttomStickData) {
		this.buttomStickData = buttomStickData;
	}

	protected void drawMacdStick(Canvas canvas) {
		if (null == buttomStickData) {
			return;
		}
		if (buttomStickData.size() <= 0) {
			return;
		}

		Paint mPositivePaintFill = new Paint();
		mPositivePaintFill.setColor(Color.RED);
		mPositivePaintFill.setStyle(Style.STROKE);
		
		Paint mNegativePaintFill = new Paint();
		mNegativePaintFill.setColor(Color.parseColor("#99cc33"));
		
		int drawStickNumber = (int) (getDataQuadrantWidth() / (getStickWidth() + stickSpacing));

		float stickX = getDataQuadrantEndX() - getStickWidth() ;

		for (int i =  buttomStickData.size() - 1 - getDrawOffset(); i > buttomStickData.size() - 1 - getDrawOffset() -  drawStickNumber; i--) {
			if (i < 0) {
				break;
			}

			KLineEntity stick = buttomStickData.get(i);

			float highY = (float) ((1f - (stick.getHigh() - buttomMinValue)
					/ (buttomMaxValue - buttomMinValue))
					* (super.getHeight() * 1 / 3) + super.getHeight() * 2 / 3);
			float lowY = (float) ((1f - (stick.getLow() - buttomMinValue)
					/ (buttomMaxValue - buttomMinValue))
					* (super.getHeight() * 1 / 3) + super.getHeight() * 2 / 3);

			// draw stick
			if(stick.getHigh() == 0){
				canvas.drawRect(stickX, highY, stickX + getStickWidth(), lowY,
						mNegativePaintFill);
			}else if(stick.getLow() == 0){
				canvas.drawRect(stickX, highY, stickX + getStickWidth(), lowY,
						mPositivePaintFill);
			}

			stickX = stickX - stickSpacing - getStickWidth();
		}
	}

	//
	protected void drawRSILines(Canvas canvas) {
		if (null == buttomNormLineDatas) {
			return;
		}
		if (buttomNormLineDatas.size() <= 0) {
			return;
		}
		float startX;

		// draw lines
		for (int i = 0; i < buttomNormLineDatas.size(); i++) {
			LineEntity<KLineEntity> line = buttomNormLineDatas.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<KLineEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}

			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// start point
			PointF ptFirst = null;
			// set start point’s X

			int drawLineNum = (int) Math.floor(getDataQuadrantWidth()
					/ getStickWidth()) + 1; // 一个界面应该画的数量

			if (drawLineNum >= lineData.size()) {
				drawLineNum = lineData.size() - 1;
			}

			startX = getDataQuadrantEndX();

			for (int j = lineData.size() - 1 - getDrawOffset(); j > lineData.size() - 1 - getDrawOffset() -drawLineNum; j--) {
				if (j < 0) {
					break;
				}
				float value = (float) lineData.get(j).getNormValue() ;
				// calculate Y
				float valueY = (float) ((1f - (value - buttomMinValue)
						/ (buttomMaxValue - buttomMinValue))
						* super.getHeight() / 3 + super.getHeight() * 2 / 3);

				// if is not last point connect to previous point
				if (j != lineData.size() - 1 - getDrawOffset()) {
					canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
							mPaint);
				}
				// reset
				ptFirst = new PointF(startX, valueY);
				startX = startX - 1 - getStickWidth();
			}
		}
		
		Paint mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(getLatitudeFontSize());
		canvas.drawText("RSI 强弱指标", 2f, super.getHeight() * 2 / 3
				+ getLatitudeFontSize(), mTextPaint);
	}

	//
	protected void drawKDJLines(Canvas canvas) {
		if (null == buttomNormLineDatas) {
			return;
		}
		if (buttomNormLineDatas.size() <= 0) {
			return;
		}
		float startX;

		// draw lines
		for (int i = 0; i < buttomNormLineDatas.size(); i++) {
			LineEntity<KLineEntity> line = buttomNormLineDatas.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<KLineEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}

			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// start point
			PointF ptFirst = null;
			// set start point’s X

			int drawLineNum = (int) Math.floor(getDataQuadrantWidth()
					/ getStickWidth()) + 1; // 一个界面应该画的数量

			if (drawLineNum >= lineData.size()) {
				drawLineNum = lineData.size() - 1;
			}

			startX = getDataQuadrantEndX();

			for (int j = lineData.size() - 1 - getDrawOffset(); j > lineData.size() - 1 - getDrawOffset() - drawLineNum ; j--) {
				if (j  < 0 ) {
					break;
				}
				float value = (float) lineData.get(j).getNormValue();
				// calculate Y
				float valueY = (float) ((1f - (value - buttomMinValue)
						/ (buttomMaxValue - buttomMinValue))
						* super.getHeight() / 3 + super.getHeight() * 2 / 3);

				// if is not last point connect to previous point
				if (j != lineData.size() - 1 - getDrawOffset()) {
					canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
							mPaint);
				}
				// reset
				ptFirst = new PointF(startX, valueY);
				startX = startX - 1 - getStickWidth();
			}
		}

		Paint mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(getLatitudeFontSize());
		canvas.drawText("KDJ 随机指标", 2f, super.getHeight() * 2 / 3
				+ getLatitudeFontSize(), mTextPaint);
	}

	public String getButtomNormType() {
		return buttomNormType;
	}

	public void setButtomNormType(String buttomNormType) {
		this.buttomNormType = buttomNormType;
		super.postInvalidate();
		super.notifyEventAll(this);
	}

	public String getTopNormType() {
		return topNormType;
	}

	public void setTopNormType(String topNormType) {
		this.topNormType = topNormType;
		super.postInvalidate();
		super.notifyEventAll(this);
	}

}
