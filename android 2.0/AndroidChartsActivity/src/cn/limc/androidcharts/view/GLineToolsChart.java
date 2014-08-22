package cn.limc.androidcharts.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import cn.limc.KLineEntity;
import cn.limc.androidcharts.entity.GLineToolsEntity;

public class GLineToolsChart extends UnionGridChart {

	private UnionCandleStickChart mUnionCandleStickChart;

	public static final String STRAIGHT_LINE_TYPE = "STRAIGHT_LINE_TYPE";

	public static final String GOLD_LINE_TYPE = "GOLD_LINE_TYPE";

	// private String glineType = STRAIGHT_LINE_TYPE;

	private GLineToolsEntity glineEntity;

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

	public static final boolean DEFAULT_AUTO_CALC_VALUE_RANGE = false;

	public static final int DEFAULT_STICK_SPACING = 1;

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

	protected boolean autoCalcValueRange = DEFAULT_AUTO_CALC_VALUE_RANGE;

	protected int stickSpacing = DEFAULT_STICK_SPACING;

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context)
	 */
	public GLineToolsChart(Context context) {
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
	public GLineToolsChart(Context context, AttributeSet attrs, int defStyle) {
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
	public GLineToolsChart(Context context, AttributeSet attrs) {
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
		drawHorizontalLine(canvas);
	}

	public void calcRange() {
		if (mUnionCandleStickChart == null)
			return;

		List<KLineEntity> stickData = mUnionCandleStickChart.getStickData();

		if (stickData == null || stickData.isEmpty())
			return;

		int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart
				.getStickWidth() + 1)) + 1;

		for (int i = mUnionCandleStickChart.getDrawOffset(); i < drawStickNumber
				+ mUnionCandleStickChart.getDrawOffset(); i++) {
			if (i >= stickData.size()) {
				break;
			}
			if (i == mUnionCandleStickChart.getDrawOffset()) {
				minValue = stickData.get(i).getLow();
				maxValue = stickData.get(i).getHigh();
			} else {
				if (maxValue < stickData.get(i).getHigh()) {
					maxValue = stickData.get(i).getHigh();
				}

				if (minValue > stickData.get(i).getLow()) {
					minValue = stickData.get(i).getLow();
				}
			}

		}
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

	private boolean isNear = false;

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

		if (event.getX() < getDataQuadrantStartX()
				|| event.getX() > getDataQuadrantEndX()) {
			return false;
		}

		if (event.getY() < getDataQuadrantStartY()
				|| event.getY() >= (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
			return false;
		}

		final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super
				.getWidth() / 50);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			touchMode = DOWN;
			mLastMotionX = (int) event.getX();
			if (glineEntity == null) {
				glineEntity = new GLineToolsEntity();
				glineEntity.setGlineType(STRAIGHT_LINE_TYPE);
				double yPositionValue = (1f - (event.getY() / (super
						.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)))
						* (maxValue - minValue) + minValue;
				glineEntity.setyPositionValue(yPositionValue);
				isNear = true;
			} else {
				float eventY = (float) (1f - (glineEntity.getyPositionValue() - minValue)
						/ (maxValue - minValue))
						* (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight);

				if (Math.abs(eventY - event.getY()) <= 20) {
					isNear = true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			isNear = false;
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
				if (isNear) {
					double yPositionValue = (1f - (event.getY() / (super
							.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)))
							* (maxValue - minValue) + minValue;

					glineEntity.setyPositionValue(yPositionValue);

					return super.onTouchEvent(event);
				} else {
					if (Math.abs(mLastMotionX - (int) event.getX()) > MIN_LENGTH) {
						xMoveOffset += Math.abs(mLastMotionX - event.getX());
						if (xMoveOffset < mUnionCandleStickChart
								.getStickWidth())
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
		if (mUnionCandleStickChart.getStickWidth() < 60) {
			mUnionCandleStickChart.setStickWidth(mUnionCandleStickChart
					.getStickWidth() + 2);
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

		if (mUnionCandleStickChart == null)
			return false;

		List<KLineEntity> stickData = mUnionCandleStickChart.getStickData();

		int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart
				.getStickWidth() + stickSpacing));

		if (drawStickNumber >= stickData.size()) {
			return false;
		} else {
			if (mUnionCandleStickChart.getDrawOffset() >= (stickData.size()
					- drawStickNumber - 1))
				return false;

			if (mUnionCandleStickChart.getStickWidth() > 2) {
				mUnionCandleStickChart.setStickWidth(mUnionCandleStickChart
						.getStickWidth() - 2);
				return true;
			}
			return false;
		}

	}

	protected boolean moveLeft(float offent) {
		if (mUnionCandleStickChart == null)
			return false;

		List<KLineEntity> stickData = mUnionCandleStickChart.getStickData();

		if (stickData == null || stickData.size() <= 0)
			return false;
		int slideNumber = (int) (offent / (mUnionCandleStickChart
				.getStickWidth() + stickSpacing)); // 滑动时偏移多少个数据
		int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart
				.getStickWidth() + stickSpacing));
		int size = stickData.size() - 1;
		if (drawStickNumber >= size) {
			return false;
		}
		if (mUnionCandleStickChart.getDrawOffset() > size - drawStickNumber) {
			return false;
		} else {
			mUnionCandleStickChart.setDrawOffset(mUnionCandleStickChart
					.getDrawOffset() + slideNumber);
			return true;
		}
	}

	protected boolean moveRight(float offent) {
		if (mUnionCandleStickChart == null)
			return false;

		List<KLineEntity> stickData = mUnionCandleStickChart.getStickData();

		if (stickData == null || stickData.size() < 0)
			return false;
		int slideNumber = (int) (offent / (mUnionCandleStickChart
				.getStickWidth() + stickSpacing)); // 滑动时偏移多少个数据

		mUnionCandleStickChart.setDrawOffset(mUnionCandleStickChart
				.getDrawOffset() - slideNumber);

		if (mUnionCandleStickChart.getDrawOffset() <= 0) {
			mUnionCandleStickChart.setDrawOffset(0);
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

	/**
	 * @return the autoCalcValueRange
	 */
	public boolean isAutoCalcValueRange() {
		return autoCalcValueRange;
	}

	/**
	 * @param autoCalcValueRange
	 *            the autoCalcValueRange to set
	 */
	public void setAutoCalcValueRange(boolean autoCalcValueRange) {
		this.autoCalcValueRange = autoCalcValueRange;
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
	public static final int DEFAULT_NEGATIVE_STICK_FILL_COLOR = Color.GREEN;

	/**
	 * <p>
	 * 默认十字线显示颜色
	 * </p>
	 */
	public static final int DEFAULT_CROSS_STAR_COLOR = Color.LTGRAY;

	protected void drawHorizontalLine(Canvas canvas) {
		if (glineEntity == null)
			return;

		if (glineEntity.getyPositionValue() == 0)
			return;

		float eventY = (float) (1f - (glineEntity.getyPositionValue() - minValue)
				/ (maxValue - minValue))
				* (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight);

		Paint mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);
		mPaint.setStrokeWidth(4f);
		float lineHLength = getDataQuadrantWidth() - axisYTitleQuadrantWidth;
		canvas.drawLine(borderWidth, eventY, borderWidth
				+ axisYTitleQuadrantWidth + lineHLength, eventY, mPaint);

	}

	public UnionCandleStickChart getmUnionCandleStickChart() {
		return mUnionCandleStickChart;
	}

	public void setmUnionCandleStickChart(
			UnionCandleStickChart mUnionCandleStickChart) {
		this.mUnionCandleStickChart = mUnionCandleStickChart;
	}

	public GLineToolsEntity getGlineEntity() {
		return glineEntity;
	}

	public void setGlineEntity(GLineToolsEntity glineEntity) {
		this.glineEntity = glineEntity;
	}

	
	
}
