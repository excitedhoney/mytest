package cn.limc.androidcharts.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import cn.limc.KLineEntity;
import cn.limc.androidcharts.GoldPointValue;
import cn.limc.androidcharts.entity.GLineToolsEntity;

public class UnionCandleStickChart extends UnionGridChart {

	private float stickWidth = 5f;
	
	private float headspace = 100f ;
	
	private int spaceDrawOffset = (int) (headspace / stickWidth);

	private int drawOffset = 0 ;

	private List<GLineToolsEntity> lineTools = new ArrayList<GLineToolsEntity>();
	
	private CrossLineChart mCrossLineChart ;

	/**
	 * <p>
	 * 默认表示柱条的边框颜色
	 * </p>
	 */
	public static final int DEFAULT_STICK_BORDER_COLOR = Color.RED;

	/**
	 * <p>
	 * 默认表示柱条的填充颜色
	 * </p>
	 */
	public static final int DEFAULT_STICK_FILL_COLOR = Color.RED;

	/**
	 * <p>
	 * 表示柱条的边框颜色
	 * </p>
	 */
	private int stickBorderColor = DEFAULT_STICK_BORDER_COLOR;

	/**
	 * <p>
	 * 表示柱条的填充颜色
	 * </p>
	 */
	private int stickFillColor = DEFAULT_STICK_FILL_COLOR;

	public static final int DEFAULT_STICK_SPACING = 1;
	/**
	 * <p>
	 * 绘制柱条用的数据
	 * </p>
	 */
	protected List<KLineEntity> stickData;

	protected double buttomMaxValue = 0;

	protected double buttomMinValue = 0;

	public double getButtomMaxValue() {
		return buttomMaxValue;
	}

	public void setButtomMaxValue(double buttomMaxValue) {
		this.buttomMaxValue = buttomMaxValue;
	}

	public double getButtomMinValue() {
		return buttomMinValue;
	}

	public void setButtomMinValue(double buttomMinValue) {
		this.buttomMinValue = buttomMinValue;
	}

	/**
	 * <p>
	 * Y的最大表示值
	 * </p>
	 */
	protected double maxValue;

	/**
	 * <p>
	 * Y的最小表示值
	 * </p>
	 */
	protected double minValue;

	protected int stickSpacing = DEFAULT_STICK_SPACING;

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context)
	 */
	public UnionCandleStickChart(Context context) {
		super(context);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * @param defStyle
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context,
	 * AttributeSet, int)
	 */
	public UnionCandleStickChart(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context,
	 * AttributeSet)
	 */
	public UnionCandleStickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when is going to draw this chart<p> <p>チャートを書く前、メソッドを呼ぶ<p>
	 * <p>绘制图表时调用<p>
	 * 
	 * @param canvas
	 * 
	 * @see android.view.View#(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		calcRange();
		initButtomAxisY();
		initTopAxisY();
		initAxisX();
		super.onDraw(canvas);
		drawTopSticks(canvas);

		drawToolsLines(canvas);
	}

	// 画线工具
	public void drawToolsLines(Canvas canvas) {
		if (lineTools != null && lineTools.size() <= 0) {
			return;
		}

		for (int i = 0; i < lineTools.size(); i++) {
			GLineToolsEntity entity = lineTools.get(i);
			if (GLineToolsChart.STRAIGHT_LINE_TYPE.equalsIgnoreCase(entity
					.getGlineType())) {
				double yPositionValue = lineTools.get(i).getyPositionValue();

				float eventY = (float) (1f - (yPositionValue - minValue)
						/ (maxValue - minValue))
						* (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight);

				Paint mPaint = new Paint();
				mPaint.setColor(Color.YELLOW);
				mPaint.setStrokeWidth(4f);

				float lineHLength = getDataQuadrantWidth();
				canvas.drawLine(borderWidth, eventY, borderWidth
						+ axisYTitleQuadrantWidth + lineHLength, eventY, mPaint);
			} else if (GLineToolsChart.GOLD_LINE_TYPE.equalsIgnoreCase(entity
					.getGlineType())) {
				drawLine(canvas, entity);
				drawGoldLine(canvas, entity);
			}
		}
	}

	protected void drawLine(Canvas canvas, GLineToolsEntity mGoldLineEntity) {

		if (mGoldLineEntity == null)
			return;

		Paint mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);
		mPaint.setStrokeWidth(4f);

		Paint mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.YELLOW);
		mCirclePaint.setPathEffect(getDashEffect());
		int drawStickNumber = (int) (getDataQuadrantWidth() / (getStickWidth() + 1));

		if (mGoldLineEntity.getSecondValue() == null) {
			float lineHLength = getDataQuadrantWidth()
					- axisYTitleQuadrantWidth;

			GoldPointValue first = mGoldLineEntity.getFirstValue();
			float yPosition = (float) ((1f - (first.getAxisYvalue() - minValue)
					/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
			float xPosition = (float) ((1f - (first.getPosition() - getDrawOffset())
					/ (0f + drawStickNumber)) * getDataQuadrantWidth());

			canvas.drawLine(borderWidth, yPosition, borderWidth
					+ axisYTitleQuadrantWidth + lineHLength, yPosition, mPaint);
			canvas.drawCircle(xPosition, yPosition, 20f, mCirclePaint);
		} else {
			GoldPointValue first = mGoldLineEntity.getFirstValue();
			float y1 = (float) ((1f - (first.getAxisYvalue() - minValue)
					/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
			float x1 = (float) ((1f - (first.getPosition() - getDrawOffset())
					/ (0f + drawStickNumber)) * getDataQuadrantWidth());

			GoldPointValue second = mGoldLineEntity.getSecondValue();

			float y2 = (float) ((1f - (second.getAxisYvalue() - minValue)
					/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
			float x2 = (float) ((1f - (second.getPosition() - getDrawOffset())
					/ (0f + drawStickNumber)) * getDataQuadrantWidth());

			float axisYfocus = (x1 * y2 - x2 * y1) / (x1 - x2); // 与y轴的焦点

			float axisXfocus = (x1 * y2 - x2 * y1) / (y2 - y1); // 与X轴的交点

			float k = (y1 - y2) / (x1 - x2);
			float b = (x1 * y2 - x2 * y1) / (x1 - x2);

			float axisY2focus = k * getDataQuadrantWidth() + b;

			float startX = 0;
			float startY = 0;
			float endX = 0;
			float endY = 0;

			if (axisYfocus > (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)
					&& axisXfocus > getDataQuadrantWidth()) { // 与Y轴的焦点不在可是的范围内

				if (axisY2focus < (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
					startX = (super.getHeight() * 2 / 3
							- axisXTitleQuadrantHeight - b)
							/ k;
					startY = super.getHeight() * 2 / 3
							- axisXTitleQuadrantHeight;
					endX = getDataQuadrantWidth();
					endY = k * getDataQuadrantWidth() + b;
				}

			} else if (axisYfocus > (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)
					&& axisXfocus < getDataQuadrantWidth() && axisXfocus > 0) {
				startX = (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight - b)
						/ k;
				startY = super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight;
				endX = (0 - b) / k;
				endY = 0;
			} else if (axisYfocus > (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)
					&& axisXfocus < 0) {
				startX = (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight - b)
						/ k;
				startY = super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight;
				endX = (0 - b) / k;
				endY = 0;
			} else if (axisYfocus < 0 && axisXfocus < getDataQuadrantWidth()
					&& axisXfocus > 0) {
				if (axisY2focus > (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
					startX = (super.getHeight() * 2 / 3
							- axisXTitleQuadrantHeight - b)
							/ k;
					startY = super.getHeight() * 2 / 3
							- axisXTitleQuadrantHeight;
					endX = (0 - b) / k;
					endY = 0;
				} else if (axisY2focus > 0) {
					startX = getDataQuadrantWidth();
					startY = axisY2focus;
					endX = (0 - b) / k;
					endY = 0;
				}
			} else if (axisYfocus < 0 && axisXfocus < 0) { //

			} else if (axisYfocus < 0 && axisXfocus > getDataQuadrantWidth()) { //

			}

			else if (axisYfocus > 0
					&& axisYfocus < (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
				if (axisXfocus < 0) {
					if (axisY2focus < (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)
							&& axisY2focus > 0) {
						startX = getDataQuadrantWidth();
						startY = axisY2focus;
						endX = 0;
						endY = b;
					} else {
						startX = ((super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight) - b)
								/ k;
						startY = super.getHeight() * 2 / 3
								- axisXTitleQuadrantHeight;
						endX = 0;
						endY = b;
					}

				} else {
					startX = (0 - b) / k;
					startY = 0;
					endX = 0;
					endY = b;
				}
			}

			canvas.drawLine(startX, startY, endX, endY, mPaint);

			if (x1 > 0 && x1 < getDataQuadrantWidth() && y1 > 0
					&& y1 < (getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
				canvas.drawCircle(x1, y1, 20f, mCirclePaint);
			}

			if (x2 > 0 && x2 < getDataQuadrantWidth() && y2 > 0
					&& y2 < (getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
				canvas.drawCircle(x2, y2, 20f, mCirclePaint);
			}
		}
	}

	public void drawGoldLine(Canvas canvas, GLineToolsEntity mGoldLineEntity) {

		if (mGoldLineEntity == null)
			return;

		if (mGoldLineEntity.getSecondValue() == null)
			return;

		Paint mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(4f);

		GoldPointValue first = mGoldLineEntity.getFirstValue();
		GoldPointValue second = mGoldLineEntity.getSecondValue();

		int drawStickNumber = (int) (getDataQuadrantWidth() / (getStickWidth() + 1));

		float firstXAxisPosition = (1f - (first.getPosition() - getDrawOffset())
				/ (0f + drawStickNumber))
				* getDataQuadrantWidth();
		float firstYAxisPosition = (float) ((1f - (first.getAxisYvalue() - minValue)
				/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));

		float secondXAxisPosition = (1f - (second.getPosition() - getDrawOffset())
				/ (0f + drawStickNumber))
				* getDataQuadrantWidth();
		float secondYAxisPosition = (float) ((1f - (second.getAxisYvalue() - minValue)
				/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));

		if (Math.abs(secondYAxisPosition - firstYAxisPosition) < 40)
			return;

		float lineLength = Math.abs(firstXAxisPosition - secondXAxisPosition) < 40 ? 40
				: Math.abs(firstXAxisPosition - secondXAxisPosition);
		float absY = Math.abs(secondYAxisPosition - firstYAxisPosition);
		float maxY = Math.max(secondYAxisPosition, firstYAxisPosition);

		if ((maxY - absY * 0.618) > 0
				&& (maxY - absY * 0.618) < (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
			canvas.drawLine(firstXAxisPosition, (float) (maxY - absY * 0.618),
					secondXAxisPosition, (float) (maxY - absY * 0.618), mPaint);
		if ((maxY - absY * 0.5) > 0
				&& (maxY - absY * 0.5) < (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
			canvas.drawLine(firstXAxisPosition, (float) (maxY - absY * 0.5),
					secondXAxisPosition, (float) (maxY - absY * 0.5), mPaint);
		if ((maxY - absY * 0.238) > 0
				&& (maxY - absY * 0.238) < (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
			canvas.drawLine(firstXAxisPosition, (float) (maxY - absY * 0.238),
					secondXAxisPosition, (float) (maxY - absY * 0.238), mPaint);

	}

	private void calcRange() {
		double max = 0;
		double min = 0;
		if (stickData == null || stickData.isEmpty())
			return;

		int drawStickNumber = (int) (getDataQuadrantWidth() / stickWidth);

		for (int i = drawOffset; i < drawOffset + drawStickNumber; i++) {
			if (i >= stickData.size())
				break;

			if (i == drawOffset) {
				max = stickData.get(i).getHigh();
				min = stickData.get(i).getLow();
			} else {
				if (max < stickData.get(i).getHigh()) {
					max = stickData.get(i).getHigh();
				}
				if (min > stickData.get(i).getLow()) {
					min = stickData.get(i).getLow();
				}
			}
		}
		maxValue = max;
		minValue = min;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param value
	 * 
	 * @see
	 * cn.limc.androidcharts.event.ITouchEventResponse#notifyEvent(GridChart)
	 */
	@Override
	public void notifyEvent(BaseChart chart) {
		super.setDisplayCrossYOnTouch(false);
		// notifyEvent
		super.notifyEvent(chart);
		// notifyEventAll
		super.notifyEventAll(this);
	}

	/**
	 * <p>
	 * 初始化X轴的坐标值
	 * </p>
	 */
	protected void initAxisX() {
		if (stickWidth <= 0)
			stickWidth = 1;

		List<String> titleX = new ArrayList<String>();
		int drawStickNumber = (int) (getDataQuadrantWidth() / stickWidth);
		if (null != stickData && stickData.size() > 0) {
			float average = drawStickNumber / this.getLongitudeNum();
			for (int i = 0; i <= this.getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average) + drawOffset;
				if (index >= stickData.size()) {
					break;
				}
				titleX.add(String.valueOf(stickData.get(index).getDate()));
			}
		}
		super.setLongitudeTitles(titleX);
	}

	/**
	 * <p>
	 * 初始化Y轴的坐标值
	 * </p>
	 */
	protected void initTopAxisY() {
		List<String> titleY = new ArrayList<String>();

		// calculate degrees on Y axis
		for (int i = 0; i < this.getLatitudeNum(); i++) {
			double title = minValue + i * getAverage();
			title = bigDecimalDoubleNum(title);
			titleY.add(String.valueOf(title));
		}
		// calculate last degrees by use max value
		titleY.add(String.valueOf(bigDecimalDoubleNum(maxValue)));
		super.setLatitudeTopTitles(titleY);
	}

	/**
	 * <p>
	 * 初始化Y轴的坐标值
	 * </p>
	 */
	protected void initButtomAxisY() {
		List<String> titleY = new ArrayList<String>();
		// calculate degrees on Y axis
		double average = (buttomMaxValue - buttomMinValue)
				/ getButtomLatitudeNum();
		for (int i = 0; i <= this.getButtomLatitudeNum(); i++) {
			double title = buttomMinValue + i * average;
			title = bigDecimalDoubleNum(title);
			titleY.add(String.valueOf(title));
		}
		super.setLatitudeButtomTitles(titleY);
	}

	/**
	 * <p>
	 * 绘制上面的 蜡型图
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawTopSticks(Canvas canvas) {
		if (null == stickData) {
			return;
		}
		if (stickData.size() <= 0) {
			return;
		}

		// Paint mPaintPositiveFill = new Paint();
		// mPaintPositiveFill.setColor(Color.TRANSPARENT);

		Paint mPaintPositiveStroke = new Paint();
		mPaintPositiveStroke.setColor(positiveStickFillColor);
		mPaintPositiveStroke.setStyle(Style.STROKE);

		Paint mPaintNegative = new Paint();
		mPaintNegative.setColor(negativeStickFillColor);

		Paint mPaintCross = new Paint();
		mPaintCross.setColor(crossStarColor);

		float stickX = getDataQuadrantEndX() - stickWidth - borderWidth;

		int drawStickNumber = (int) Math.ceil(getDataQuadrantWidth()
				/ (stickWidth + stickSpacing));

		for (int i = drawOffset; i < drawOffset + drawStickNumber; i++) {
			if (i >= stickData.size()) {
				break;
			}

			KLineEntity ohlc = (KLineEntity) stickData.get(i);
			double realHeight = super.getHeight() * 2 / 3
					- axisXTitleQuadrantHeight;

			float openY = (float) ((1f - (ohlc.getOpen() - getMinValue())
					/ (getMaxValue() - getMinValue())) * realHeight);
			float closeY = (float) ((1f - (ohlc.getZuoShou() - getMinValue())
					/ (getMaxValue() - getMinValue())) * realHeight);
			float highY = (float) ((1f - (ohlc.getHigh() - getMinValue())
					/ (getMaxValue() - getMinValue())) * realHeight);
			float lowY = (float) ((1f - (ohlc.getLow() - getMinValue())
					/ (getMaxValue() - getMinValue())) * realHeight);

			if (ohlc.getOpen() < ohlc.getZuoShou()) {
				// stick or line

				if (stickWidth >= 2f) {
					canvas.drawRect(stickX, closeY, stickX + stickWidth, openY,
							mPaintPositiveStroke);
				}
				if (highY < closeY)
					canvas.drawLine(stickX + stickWidth / 2f, highY, stickX
							+ stickWidth / 2f, closeY, mPaintPositiveStroke);

				if (openY < lowY)
					canvas.drawLine(stickX + stickWidth / 2f, openY, stickX
							+ stickWidth / 2f, lowY, mPaintPositiveStroke);

			} else if (ohlc.getOpen() > ohlc.getZuoShou()) {
				// stick or line
				if (stickWidth >= 2f) {
					canvas.drawRect(stickX, openY, stickX + stickWidth, closeY,
							mPaintNegative);
				}
				canvas.drawLine(stickX + stickWidth / 2f, highY, stickX
						+ stickWidth / 2f, lowY, mPaintNegative);
			} else {
				// line or point
				if (stickWidth >= 2f) {
					canvas.drawLine(stickX, closeY, stickX + stickWidth, openY,
							mPaintCross);
				}
				canvas.drawLine(stickX + stickWidth / 2f, highY, stickX
						+ stickWidth / 2f, lowY, mPaintCross);
			}
			// next x
			stickX = stickX - stickSpacing - stickWidth;
		}
	}

	/**
	 * <p>
	 * 追加一条新数据并刷新当前图表
	 * </p>
	 * 
	 * @param entity
	 *            <p>
	 *            data
	 *            </p>
	 *            <p>
	 *            データ
	 *            </p>
	 *            <p>
	 *            新数据
	 *            </p>
	 */
	public void pushData(KLineEntity entity) {
		if (null != entity) {
			addData(entity);
			super.postInvalidate();
		}
	}

	/**
	 * <p>
	 * add a new stick data to sticks
	 * </p>
	 * <p>
	 * 新しいスティックデータを追加する
	 * </p>
	 * <p>
	 * 追加一条新数据
	 * </p>
	 * 
	 * @param entity
	 *            <p>
	 *            data
	 *            </p>
	 *            <p>
	 *            データ
	 *            </p>
	 *            <p>
	 *            新数据
	 *            </p>
	 */
	public void addData(KLineEntity entity) {
		// if (null != entity) {
		// // add
		// this.stickData.add(entity);
		//
		// if (this.maxValue < entity.getHigh()) {
		// this.maxValue = ((int) entity.getHigh()) / 100 * 100;
		// }
		//
		// if (this.maxValue < entity.getLow()) {
		// this.minValue = ((int) entity.getLow()) / 100 * 100;
		// }
		//
		// if (stickData.size() > maxSticksNum) {
		// maxSticksNum = maxSticksNum + 1;
		// }
		// }
	}

	private final int NONE = 0;
	private final int ZOOM = 1;
	private final int DOWN = 2;
	private final int MOVE = 3;

	private float olddistance = 0f;
	private float newdistance = 0f;

	private int touchMode;

	private int mLastMotionX;

	private float xMoveOffset = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when chart is touched<p> <p>チャートをタッチしたら、メソッドを呼ぶ<p>
	 * <p>图表点击时调用<p>
	 * 
	 * @param event
	 * 
	 * @see android.view.View#onTouchEvent(MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super
				.getWidth() / 50);
		long downTime= 0 ;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			touchMode = DOWN;
			mLastMotionX = (int) event.getX();
			downTime = System.currentTimeMillis() ;
			break;
		case MotionEvent.ACTION_UP:
			if(touchMode == DOWN){
				if((System.currentTimeMillis() - downTime) > 2000){
					if(mCrossLineChart != null) {
						mCrossLineChart.setClickPostX(event.getX());
						mCrossLineChart.setClickPostY(event.getY());
					}
					performLongClick();
				}
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			touchMode = NONE;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			olddistance = calcDistance(event);
			if (olddistance > MIN_LENGTH) {
				touchMode = ZOOM;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (touchMode != ZOOM && touchMode != NONE) {
				if (Math.abs(mLastMotionX - (int) event.getX()) > MIN_LENGTH) {
					xMoveOffset += Math.abs(mLastMotionX - event.getX());
					if (xMoveOffset < stickWidth)
						break;
					touchMode = MOVE;

					boolean isInvalidate = false;
					Log.e("offent", ">>>>>" + xMoveOffset + "<<<<<");
					if (mLastMotionX < (int) event.getX()) { // 手指方向 向右 往左滑动
						isInvalidate = moveLeft(xMoveOffset);
					} else {
						isInvalidate = moveRight(xMoveOffset);
					}
					xMoveOffset = 0f;
					mLastMotionX = (int) event.getX();
					if (isInvalidate) {
						return super.onTouchEvent(event);
					}
				}
			}

			if (touchMode == ZOOM) {
				newdistance = calcDistance(event);
				if (newdistance > MIN_LENGTH
						&& Math.abs(newdistance - olddistance) > MIN_LENGTH) {
					boolean isInvalidate = false;
					if (newdistance > olddistance) {
						isInvalidate = zoomIn();
					} else {
						isInvalidate = zoomOut();
					}
					olddistance = newdistance;
					if (isInvalidate) {
						return super.onTouchEvent(event);
					}
				}
			}
			break;
		}
		return true;
	}

	protected boolean moveLeft(float offent) {
		if (stickData == null || stickData.size() <= 0)
			return false;
		int slideNumber = (int) (offent / (stickWidth + stickSpacing)); // 滑动时偏移多少个数据
		int drawStickNumber = (int) (getDataQuadrantWidth() / (stickWidth + stickSpacing));
		int size = stickData.size() - 1;
		if (drawStickNumber >= size) {
			return false;
		}
		if (drawOffset > size - drawStickNumber) {
			return false;
		} else {
			drawOffset += slideNumber;
			return true;
		}
	}


	
	protected boolean moveRight(float offent) {
		if (stickData == null || stickData.size() < 0)
			return false;
		int slideNumber = (int) (offent / (stickWidth + stickSpacing)); // 滑动时偏移多少个数据
		drawOffset -= slideNumber;
		if (drawOffset <= 0) {
			drawOffset = 0;
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * calculate the distance between two touch points
	 * </p>
	 * <p>
	 * 複数タッチしたポイントの距離
	 * </p>
	 * <p>
	 * 计算两点触控时两点之间的距离
	 * </p>
	 * 
	 * @param event
	 * @return float
	 *         <p>
	 *         distance
	 *         </p>
	 *         <p>
	 *         距離
	 *         </p>
	 *         <p>
	 *         距离
	 *         </p>
	 */
	private float calcDistance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * <p>
	 * Zoom in the graph
	 * </p>
	 * <p>
	 * 拡大表示する。
	 * </p>
	 * <p>
	 * 放大表示
	 * </p>
	 */
	protected boolean zoomIn() {
		if (stickWidth < 60) {
			stickWidth = stickWidth + 2;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <p>
	 * Zoom out the grid
	 * </p>
	 * <p>
	 * 縮小表示する。
	 * </p>
	 * <p>
	 * 缩小
	 * </p>
	 */
	protected boolean zoomOut() {
		int drawStickNumber = (int) (getDataQuadrantWidth() / (stickWidth + stickSpacing));
		if (drawStickNumber >= stickData.size()) {
			return false;
		} else {
			if (drawOffset >= (stickData.size() - drawStickNumber - 1))
				return false;

			if (stickWidth > 2) {
				stickWidth = stickWidth - 2;
				return true;
			}
			return false;
		}

	}

	/**
	 * @return the stickBorderColor
	 */
	public int getStickBorderColor() {
		return stickBorderColor;
	}

	/**
	 * @param stickBorderColor
	 *            the stickBorderColor to set
	 */
	public void setStickBorderColor(int stickBorderColor) {
		this.stickBorderColor = stickBorderColor;
	}

	/**
	 * @return the stickFillColor
	 */
	public int getStickFillColor() {
		return stickFillColor;
	}

	/**
	 * @param stickFillColor
	 *            the stickFillColor to set
	 */
	public void setStickFillColor(int stickFillColor) {
		this.stickFillColor = stickFillColor;
	}

	/**
	 * @return the stickData
	 */
	public List<KLineEntity> getStickData() {
		return stickData;
	}

	/**
	 * @param stickData
	 *            the stickData to set
	 */
	public void setStickData(List<KLineEntity> stickData) {
		this.stickData = stickData;
	}

	/**
	 * @return the maxValue
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the minValue
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 *            the minValue to set
	 */
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	protected void drawAlphaTextBox(Canvas canvas) {

		Paint mPaint = new Paint();
		mPaint.setColor(getCrossLinesColor());

		PointF ptStart;
		PointF ptEnd;

		if (getClickPostX() < getDataQuadrantWidth() / 2f) {
			ptStart = new PointF(getDataQuadrantStartX(),
					getDataQuadrantStartY());

			ptEnd = new PointF(getDataQuadrantStartX() + 5
					* getLatitudeFontSize() + 5f, getDataQuadrantStartY()
					+ getLatitudeFontSize() * 5 + 5f);
		} else {
			ptStart = new PointF(getDataQuadrantEndX()
					- (5 * getLatitudeFontSize() + 5f), getDataQuadrantStartY());

			ptEnd = new PointF(getDataQuadrantEndX(), getDataQuadrantStartY()
					+ getLatitudeFontSize() * 5 + 5f);
		}

		Paint mPaintBox = new Paint();
		mPaintBox.setColor(Color.WHITE);
		mPaintBox.setAlpha(80);
		mPaintBox.setStyle(Style.FILL);

		Paint mPaintBoxLine = new Paint();
		mPaintBoxLine.setColor(getCrossLinesColor());
		mPaintBoxLine.setAntiAlias(true);
		mPaintBoxLine.setTextSize(getLatitudeFontSize());

		// draw a rectangle
		canvas.drawRect(ptStart.x, ptStart.y, ptEnd.x, ptEnd.y, mPaintBox);

		// draw a rectangle' border
		canvas.drawLine(ptStart.x, ptStart.y, ptStart.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptStart.x, ptEnd.y, ptEnd.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptEnd.y, ptEnd.x, ptStart.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptStart.y, ptStart.x, ptStart.y, mPaintBoxLine);

		mPaintBoxLine.setColor(getCrossLinesColor());
		// draw text
		KLineEntity entity = getIntersectionEntity();
		if (entity != null) {
			canvas.drawText(entity.getDate(), ptStart.x, ptStart.y
					+ getLatitudeFontSize(), mPaintBoxLine);
			canvas.drawText("开盘" + entity.getOpen(), ptStart.x, ptStart.y
					+ getLatitudeFontSize() * 2, mPaintBoxLine);
			canvas.drawText("最高" + entity.getHigh(), ptStart.x, ptStart.y
					+ getLatitudeFontSize() * 3, mPaintBoxLine);
			canvas.drawText("收盘" + entity.getZuoShou(), ptStart.x, ptStart.y
					+ getLatitudeFontSize() * 4, mPaintBoxLine);
			canvas.drawText("最低" + entity.getLow(), ptStart.x, ptStart.y
					+ getLatitudeFontSize() * 5, mPaintBoxLine);
		}
	}

	/**
	 * 通过当前触摸点 获取当前对象
	 */

	protected KLineEntity getIntersectionEntity() {
		if (stickData == null || stickData.size() < 0)
			return null;
		if (getClickPostX() <= 0) {
			return null;
		}
		int currentPosition = -1;

		float mXisaWidth = getDataQuadrantEndX() - getClickPostX();
		currentPosition = (int) (mXisaWidth / (stickWidth + stickSpacing))
				+ drawOffset;
		if (currentPosition >= stickData.size())
			currentPosition = stickData.size() - 1;
		if (currentPosition < 0)
			currentPosition = 0;
		return stickData.get(currentPosition);
	}

	public double getAverage() {
		return (getMaxValue() - getMinValue()) / this.getLatitudeNum() / 100.0
				* 100.0;
	}

	/**
	 * <p>
	 * 默认阳线的边框颜色
	 * </p>
	 */
	public static final int DEFAULT_POSITIVE_STICK_BORDER_COLOR = Color.RED;

	/**
	 * <p>
	 * 默认阳线的填充颜色
	 * </p>
	 */
	public static final int DEFAULT_POSITIVE_STICK_FILL_COLOR = Color.RED;

	/**
	 * <p>
	 * 默认阴线的边框颜色
	 * </p>
	 */
	public static final int DEFAULT_NEGATIVE_STICK_BORDER_COLOR = Color.GREEN;

	/**
	 * <p>
	 * 默认阴线的填充颜色
	 * </p>
	 */
	public static final int DEFAULT_NEGATIVE_STICK_FILL_COLOR = Color
			.parseColor("#99cc33");

	/**
	 * <p>
	 * 默认十字线显示颜色
	 * </p>
	 */
	public static final int DEFAULT_CROSS_STAR_COLOR = Color.LTGRAY;

	/**
	 * <p>
	 * 阳线的填充颜色
	 * </p>
	 */
	private int positiveStickFillColor = DEFAULT_POSITIVE_STICK_FILL_COLOR;

	/**
	 * <p>
	 * 阴线的填充颜色
	 * </p>
	 */
	private int negativeStickFillColor = DEFAULT_NEGATIVE_STICK_FILL_COLOR;

	/**
	 * <p>
	 * 十字线显示颜色（十字星,一字平线,T形线的情况）
	 * </p>
	 */
	private int crossStarColor = DEFAULT_CROSS_STAR_COLOR;

	public String getDateRanage() {
		if (stickData != null && stickData.size() > 0) {
			return stickData.get(0).getDate() + "~"
					+ stickData.get(stickData.size() - 1).getDate();
		}
		return null;
	}

	public float getStickWidth() {
		return stickWidth;
	}

	public void setStickWidth(float stickWidth) {
		this.stickWidth = stickWidth;
		// redraw
		super.invalidate();
		// do notify
		notifyEventAll(this);
	}

	public int getDrawOffset() {
		return drawOffset;
	}

	public void setDrawOffset(int drawOffset) {
		this.drawOffset = drawOffset;
		// redraw
		super.invalidate();
		// do notify
		notifyEventAll(this);
	}

	public void addLines(GLineToolsEntity glineEntity) {
		lineTools.add(glineEntity);
		// redraw
		super.invalidate();
		// do notify
		notifyEventAll(this);
	}

	public boolean isHaveGline() {
		return lineTools.size() > 0;
	}

	public void clearAllGlineLines() {
		lineTools.clear();
		// redraw
		super.invalidate();
		// do notify
		notifyEventAll(this);
	}

	public CrossLineChart getmCrossLineChart() {
		return mCrossLineChart;
	}

	public void setmCrossLineChart(CrossLineChart mCrossLineChart) {
		this.mCrossLineChart = mCrossLineChart;
	}
	
	
}
