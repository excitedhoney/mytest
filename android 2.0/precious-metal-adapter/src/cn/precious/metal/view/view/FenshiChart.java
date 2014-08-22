package cn.precious.metal.view.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import cn.precious.metal.view.KLineEntity;
import cn.precious.metal.view.entity.LineEntity;

public class FenshiChart extends IntervalGridView {

	protected float lineLength = 8f;

	protected int drawOffset = 0;

	/**
	 * 绘制线条用的数据
	 */
	protected List<LineEntity<KLineEntity>> linesData;

	protected double maxValue;

	protected double minValue;

	protected double realMinValue;

	protected double realMaxValue;

	public FenshiChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FenshiChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FenshiChart(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		calcRange();
		// 初始化 X Y轴的坐标值
		initAxisY();
		initAxisX();
		super.onDraw(canvas);
		drawLines(canvas);

	}

	public void calcRange() {
		if (null == linesData || linesData.size() < 0)
			return;
		List<KLineEntity> lineData = linesData.get(0).getLineData();

		for (int i = 0; i < lineData.size(); i++) {
			if (i == 0) {
				maxValue = lineData.get(i).getZuoShou();
				minValue = lineData.get(i).getZuoShou();
			} else {
				if (maxValue < lineData.get(i).getZuoShou()) {
					maxValue = lineData.get(i).getZuoShou();
				}
				if (minValue > lineData.get(i).getZuoShou()) {
					minValue = lineData.get(i).getZuoShou();
				}
			}
		}
		
		double diver = Math.abs( maxValue - minValue) / (4 * getLatitudeNum());
		
		realMaxValue = maxValue + diver;
		realMinValue = minValue - diver;
	}

	/**
	 * <p>
	 * initialize degrees on Y axis
	 * </p>
	 * <p>
	 * Y軸の目盛を初期化
	 * </p>
	 * <p>
	 * 初始化Y轴的坐标值
	 * </p>
	 */
	protected void initAxisY() {
		List<String> titleY = new ArrayList<String>();
		titleY.add(String.valueOf(bigDecimalDoubleNum(realMinValue)));
		double average = (maxValue - minValue) / this.getLatitudeNum();
		// calculate degrees on Y axis
		for (int i = 0; i < this.getLatitudeNum(); i++) {
			String value = String.valueOf(bigDecimalDoubleNum(minValue + i
					* average));
			titleY.add(value);
		}
		// calculate last degrees by use max value
		String value = String.valueOf(bigDecimalDoubleNum(maxValue));
		titleY.add(value);
		titleY.add(String.valueOf(bigDecimalDoubleNum(realMaxValue)));
		super.setLatitudeTitles(titleY);
	}

	/**
	 * 初始化Y轴的坐标值
	 */
	protected void initAxisX() {
		int drawLineNumber = (int) Math.floor(getDataQuadrantWidth()
				/ (lineLength + 1)) + 1;
		List<String> titleX = new ArrayList<String>();
		if (null != linesData && linesData.size() > 0) {
			List<KLineEntity> lineData = linesData.get(0).getLineData();
			float average = drawLineNumber / this.getLongitudeNum();
			for (int i = 0; i < this.getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average) + drawOffset;
				if (index > lineData.size() - 1) {
					index = lineData.size() - 1;
				}
				titleX.add(String.valueOf(lineData.get(index).getDate())
						.substring(10));
			}
			if ((drawLineNumber + drawOffset) <= (lineData.size() - 1)) {
				titleX.add(String.valueOf(
						lineData.get(drawLineNumber + drawOffset).getDate())
						.substring(10));
			}

		}
		super.setLongitudeTitles(titleX);
	}

	/**
	 * 
	 * @param canvas
	 */
	protected void drawLines(Canvas canvas) {
		if (null == this.linesData) {
			return;
		}
		float startX;

		// draw lines
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
					/ (lineLength + 1)) + 1; // 一个界面应该画的数量

			if (drawLineNum >= lineData.size()) {
				drawLineNum = lineData.size() - 1;
			}

			startX = getDataQuadrantPaddingEndX();

			if (axisYPosition == AXIS_Y_POSITION_RIGHT) {

				for (int j = drawOffset; j <= drawLineNum + drawOffset; j++) {
					if (j >= lineData.size())
						break;
					float value = (float) lineData.get(j).getZuoShou();
					// calculate Y
					float valueY = (float) ((1f - (value - realMinValue)
							/ (realMaxValue - realMinValue)) * getDataQuadrantPaddingHeight())
							+ getDataQuadrantPaddingStartY();

					// if is not last point connect to previous point
					if (j > drawOffset) {
						canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
								mPaint);
					}
					// reset
					ptFirst = new PointF(startX, valueY);
					startX = startX - 1 - lineLength;
					if (startX < 0)
						startX = 0;
				}
			}
		}
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

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			touchMode = DOWN;
			mLastMotionX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP:
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
					if (xMoveOffset < lineLength)
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
						super.postInvalidate();
						super.notifyEventAll(this);
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
						super.postInvalidate();
						super.notifyEventAll(this);
					}
				}
			}
			break;
		}
		return true;
	}

	protected boolean moveLeft(float offent) {
		if (linesData == null || linesData.size() <= 0)
			return false;

		List<KLineEntity> list = linesData.get(0).getLineData();

		if (list == null || list.isEmpty())
			return false;

		int slideNumber = (int) (offent / (lineLength + 1)); // 滑动时偏移多少个数据
		int drawLineNumber = (int) (getDataQuadrantWidth() / (lineLength + 1));
		int size = list.size() - 1;
		if (drawLineNumber >= size) {
			return false;
		}
		if (drawOffset > size - drawLineNumber) {
			return false;
		} else {
			drawOffset += slideNumber;
			return true;
		}
	}

	protected boolean moveRight(float offent) {
		if (linesData == null || linesData.size() <= 0)
			return false;

		List<KLineEntity> list = linesData.get(0).getLineData();

		if (list == null || list.isEmpty())
			return false;

		int slideNumber = (int) (offent / (lineLength + 1)); // 滑动时偏移多少个数据
		drawOffset -= slideNumber;
		if (drawOffset <= 0) {
			drawOffset = 0;
			return false;
		}
		return true;
	}

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
		if (lineLength < 40) {
			lineLength = lineLength + 2;
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
		if (linesData == null || linesData.size() <= 0)
			return false;

		List<KLineEntity> list = linesData.get(0).getLineData();

		if (list == null || list.isEmpty())
			return false;

		int drawLineNumber = (int) (getDataQuadrantWidth() / (lineLength + 1));
		if (drawLineNumber >= list.size()) {
			return false;
		} else {
			if (drawOffset >= (list.size() - drawLineNumber - 1))
				return false;

			if (lineLength > 1) {
				lineLength = lineLength - 1;
				return true;
			}
			return false;
		}
	}

	public float getLineLength() {
		return lineLength;
	}

	public void setLineLength(float lineLength) {
		this.lineLength = lineLength;
	}

	public int getDrawOffset() {
		return drawOffset;
	}

	public void setDrawOffset(int drawOffset) {
		this.drawOffset = drawOffset;
	}

	public List<LineEntity<KLineEntity>> getLinesData() {
		return linesData;
	}

	public void setLinesData(List<LineEntity<KLineEntity>> linesData) {
		this.linesData = linesData;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getRealMinValue() {
		return realMinValue;
	}

	public void setRealMinValue(double realMinValue) {
		this.realMinValue = realMinValue;
	}

	public double getRealMaxValue() {
		return realMaxValue;
	}

	public void setRealMaxValue(double realMaxValue) {
		this.realMaxValue = realMaxValue;
	}
}
