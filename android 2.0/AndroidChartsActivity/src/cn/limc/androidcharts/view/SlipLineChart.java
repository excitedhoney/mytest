/*
 * SlipLineChart.java
 * Android-Charts
 *
 * Created by limc on 2014.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.limc.androidcharts.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import cn.limc.androidcharts.entity.DateValueEntity;
import cn.limc.androidcharts.entity.LineEntity;

/**
 * @author limc
 * @version v1.0 2014/01/21 14:20:35
 * 
 */
public class SlipLineChart extends GridChart {

	private int drawOffset = 0;

	private float lineLength = 6f;

	/**
	 * <p>
	 * data to draw lines
	 * </p>
	 * <p>
	 * ラインを書く用データ
	 * </p>
	 * <p>
	 * 绘制线条用的数据
	 * </p>
	 */
	protected List<LineEntity<DateValueEntity>> linesData;

	/**
	 * <p>
	 * min value of Y axis
	 * </p>
	 * <p>
	 * Y軸の最小値
	 * </p>
	 * <p>
	 * Y的最小表示值
	 * </p>
	 */
	protected double minValue;

	/**
	 * <p>
	 * max value of Y axis
	 * </p>
	 * <p>
	 * Y軸の最大値
	 * </p>
	 * <p>
	 * Y的最大表示值
	 * </p>
	 */
	protected double maxValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @see cn.limc.androidcharts.view.GridChart#GridChart(Context)
	 */
	public SlipLineChart(Context context) {
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
	 * @see cn.limc.androidcharts.view.GridChart#GridChart(Context,
	 * AttributeSet, int)
	 */
	public SlipLineChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @param attrs
	 * 
	 * 
	 * 
	 * @see cn.limc.androidcharts.view.GridChart#GridChart(Context,
	 * AttributeSet)
	 */
	public SlipLineChart(Context context, AttributeSet attrs) {
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
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		caleRange();
		initAxisY();
		initAxisX();
		super.onDraw(canvas);
		if (null != this.linesData) {
			drawLines(canvas);
		}
	}

	public void caleRange() {
		if (linesData == null || linesData.size() <= 0)
			return;

		for (int i = 0; i < linesData.size(); i++) {

			List<DateValueEntity> list = linesData.get(i).getLineData();

			for (int j = 0; j < list.size(); j++) {
				if (j == 0 && i == 0) {
					maxValue = list.get(j).getValue();
					minValue = list.get(j).getValue();
				} else {
					if (maxValue < list.get(j).getValue()) {
						maxValue = list.get(j).getValue();
					}
					if (minValue > list.get(j).getValue()) {
						minValue = list.get(j).getValue();
					}
				}

			}

		}

	}

	protected void drawHorizontalLine(Canvas canvas) {

	}

	protected void drawVerticalLine(Canvas canvas) {

	}
	
	
	/**
	 * <p>
	 * draw longitude lines
	 * </p>
	 * <p>
	 * 経線を書く
	 * </p>
	 * <p>
	 * 绘制经线
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLongitudeTitle(Canvas canvas) {

		if (null == getLongitudeTitles()) {
			return;
		}

		if (getLongitudeTitles().size() <= 1) {
			return;
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(getLongitudeFontColor());
		mPaintFont.setTextSize(getLongitudeFontSize());
		mPaintFont.setAntiAlias(true);

		float postOffset = this.getDataQuadrantPaddingWidth()
				/ (getLongitudeTitles().size() - 1);

		float offset = getDataQuadrantEndX() ;
		
		for (int i = 0; i < getLongitudeTitles().size(); i++) {
			 if (0 == i) {
			 canvas.drawText(getLongitudeTitles().get(i), offset - mPaintFont.measureText(getLongitudeTitles().get(i)),  super.getHeight() - 2f, mPaintFont);
			 }else if(i == getLongitudeTitles().size() - 1){
				canvas.drawText(getLongitudeTitles().get(i), getDataQuadrantStartX(),
						super.getHeight() - 2f, mPaintFont);
			} else {
				canvas.drawText(getLongitudeTitles().get(i), offset - i * postOffset
						 - mPaintFont.measureText(getLongitudeTitles().get(i)) / 2f,
						super.getHeight() - 2f, mPaintFont);
			}

		}
	}

	/***
	 * 
	 * 
	 */
	protected void initAxisY() {
		if(linesData == null || linesData.isEmpty())
			return  ;
		
		List<String> titleY = new ArrayList<String>();
		double average = (maxValue - minValue) / this.getLatitudeNum();
		// calculate degrees on Y axis
		for (int i = 0; i < this.getLatitudeNum(); i++) {
			String value = String.valueOf(bigDecimalDoubleNum(minValue + i
					* average));
			titleY.add(value);
		}
		// calculate last degrees by use max value
		String value = String.valueOf(maxValue);
		titleY.add(value);

		super.setLatitudeTitles(titleY);
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
	protected void initAxisX() {
		if(linesData == null || linesData.isEmpty())
			return  ;
		
		List<String> titleX = new ArrayList<String>();
		if (null != linesData && linesData.size() > 0) {
			int drawLineNumber = (int) (getDataQuadrantWidth() / lineLength);
			float average = drawLineNumber / this.getLongitudeNum();
			for (int i = 0; i <= this.getLongitudeNum(); i++) {
				int index = (int) Math.floor(i * average) + drawOffset;
				if (index > linesData.get(0).getLineData().size() - 1) {
					break;
				}
				titleX.add(linesData.get(0).getLineData().get(index).getDate());
			}
		}
		super.setLongitudeTitles(titleX);
	}

	/**
	 * <p>
	 * draw lines
	 * </p>
	 * <p>
	 * ラインを書く
	 * </p>
	 * <p>
	 * 绘制线条
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLines(Canvas canvas) {
		if (null == this.linesData) {
			return;
		}
		// distance between two points
		// start point‘s X
		float startX;

		int drawLineNumber = (int) (getDataQuadrantWidth() / lineLength);
		// draw lines
		for (int i = 0; i < linesData.size(); i++) {
			LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) linesData
					.get(i);
			if (line == null) {
				continue;
			}
			if (line.isDisplay() == false) {
				continue;
			}
			List<DateValueEntity> lineData = line.getLineData();
			if (lineData == null) {
				continue;
			}
			Paint mPaint = new Paint();
			mPaint.setColor(line.getLineColor());
			mPaint.setAntiAlias(true);
			// set start point’s X

			if (axisYPosition == AXIS_Y_POSITION_LEFT) {
				startX = getDataQuadrantEndX() ;
				// start point
				PointF ptFirst = null;
				for (int j = drawOffset; j < drawOffset + drawLineNumber; j++) {
					if (j >= lineData.size())
						break;

					float value = (float) lineData.get(j).getValue();
					// calculate Y
					float valueY = (float) ((1f - (value - minValue)
							/ (maxValue - minValue)) * getDataQuadrantPaddingHeight())
							+ getDataQuadrantPaddingStartY();

					// if is not last point connect to previous point
					if (j > drawOffset) {
						canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
								mPaint);
					}
					// reset
					ptFirst = new PointF(startX, valueY);
					startX = startX - lineLength;
				}
			} else {

			}
		}

	}

	protected final int NONE = 0;
	protected final int ZOOM = 1;
	protected final int DOWN = 2;

	protected float olddistance = 0f;
	protected float newdistance = 0f;

	protected int touchMode;

	protected PointF startPoint;
	protected PointF startPointA;
	protected PointF startPointB;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (null == linesData || linesData.size() == 0) {
			return false;
		}

		final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super
				.getWidth() / 50);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 设置拖拉模式
		case MotionEvent.ACTION_DOWN:
			touchMode = DOWN;
			if (event.getPointerCount() == 1) {
				startPoint = new PointF(event.getX(), event.getY());
			}
			break;
		case MotionEvent.ACTION_UP:
			touchMode = NONE;
			startPointA = null;
			startPointB = null;
			return super.onTouchEvent(event);
		case MotionEvent.ACTION_POINTER_UP:
			touchMode = NONE;
			startPointA = null;
			startPointB = null;
			break;
		// 设置多点触摸模式
		case MotionEvent.ACTION_POINTER_DOWN:
			olddistance = calcDistance(event);
			if (olddistance > MIN_LENGTH) {
				touchMode = ZOOM;
				startPointA = new PointF(event.getX(0), event.getY(0));
				startPointB = new PointF(event.getX(1), event.getY(1));
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (touchMode == ZOOM) {
				newdistance = calcDistance(event);
				if (newdistance > MIN_LENGTH) {
					// if (startPointA.x >= event.getX(0)
					// && startPointB.x >= event.getX(1)) {
					// if (displayFrom + displayNumber + 2 < linesData.get(0)
					// .getLineData().size()) {
					// displayFrom = displayFrom + 2;
					// }
					// } else if (startPointA.x <= event.getX(0)
					// && startPointB.x <= event.getX(1)) {
					// if (displayFrom > 2) {
					// displayFrom = displayFrom - 2;
					// }
					// } else {
					// if (Math.abs(newdistance - olddistance) > MIN_LENGTH) {
					//
					// if (newdistance > olddistance) {
					// zoomIn();
					// } else {
					// zoomOut();
					// }
					// // 重置距离
					// olddistance = newdistance;
					// }
					// }
					// startPointA = new PointF(event.getX(0), event.getY(0));
					// startPointB = new PointF(event.getX(1), event.getY(1));
					//
					// super.postInvalidate();
					// super.notifyEventAll(this);
				}
			} else {
				// 单点拖动效果
				if (event.getPointerCount() == 1) {
					float moveXdistance = Math.abs(event.getX() - startPoint.x);
					float moveYdistance = Math.abs(event.getY() - startPoint.y);

					if (moveXdistance > 1 || moveYdistance > 1) {

						super.onTouchEvent(event);

						startPoint = new PointF(event.getX(), event.getY());
					}
				}
			}
			super.onTouchEvent(event);
		}
		return true;
	}

	// private final int NONE = 0;
	// private final int ZOOM = 1;
	// private final int DOWN = 2;
	// private final int MOVE = 3;
	//
	// private float olddistance = 0f;
	// private float newdistance = 0f;
	//
	// private int touchMode;
	//
	// private int mLastMotionX;
	//
	// private float xMoveOffset = 0;
	//
	// /*
	// * (non-Javadoc)
	// *
	// * <p>Called when chart is touched<p> <p>チャートをタッチしたら、メソッドを呼ぶ<p>
	// * <p>图表点击时调用<p>
	// *
	// * @param event
	// *
	// * @see android.view.View#onTouchEvent(MotionEvent)
	// */
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	//
	// final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super
	// .getWidth() / 50);
	//
	// switch (event.getAction() & MotionEvent.ACTION_MASK) {
	// case MotionEvent.ACTION_DOWN:
	// touchMode = DOWN;
	// mLastMotionX = (int) event.getX();
	// break;
	// case MotionEvent.ACTION_UP:
	// case MotionEvent.ACTION_POINTER_UP:
	// touchMode = NONE;
	// break;
	// case MotionEvent.ACTION_POINTER_DOWN:
	// olddistance = calcDistance(event);
	// if (olddistance > MIN_LENGTH) {
	// touchMode = ZOOM;
	// }
	// break;
	// case MotionEvent.ACTION_MOVE:
	// if (touchMode != ZOOM && touchMode != NONE) {
	// if (Math.abs(mLastMotionX - (int) event.getX()) > MIN_LENGTH) {
	// xMoveOffset += Math.abs(mLastMotionX - event.getX());
	// if (xMoveOffset < lineLength)
	// break;
	// touchMode = MOVE;
	//
	// boolean isInvalidate = false;
	// Log.e("offent", ">>>>>" + xMoveOffset + "<<<<<");
	// if (mLastMotionX < (int) event.getX()) { // 手指方向 向右 往左滑动
	// isInvalidate = moveLeft(xMoveOffset);
	// } else {
	// isInvalidate = moveRight(xMoveOffset);
	// }
	// xMoveOffset = 0f;
	// mLastMotionX = (int) event.getX();
	// if (isInvalidate) {
	// super.postInvalidate();
	// super.notifyEventAll(this);
	// }
	// }
	// }
	//
	// if (touchMode == ZOOM) {
	// newdistance = calcDistance(event);
	// if (newdistance > MIN_LENGTH
	// && Math.abs(newdistance - olddistance) > MIN_LENGTH) {
	// boolean isInvalidate = false;
	// if (newdistance > olddistance) {
	// isInvalidate = zoomIn();
	// } else {
	// isInvalidate = zoomOut();
	// }
	// olddistance = newdistance;
	// if (isInvalidate) {
	// super.postInvalidate();
	// super.notifyEventAll(this);
	// }
	// }
	// }
	// break;
	// }
	// return true;
	// }

	protected boolean moveLeft(float offent) {
		if (linesData == null || linesData.size() <= 0)
			return false;
		int slideNumber = (int) (offent / lineLength); // 滑动时偏移多少个数据
		int drawStickNumber = (int) (getDataQuadrantWidth() / lineLength);
		int size = linesData.get(0).getLineData().size() - 1;
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
		if (linesData == null || linesData.size() <= 0)
			return false;
		int slideNumber = (int) (offent / lineLength); // 滑动时偏移多少个数据
		drawOffset -= slideNumber;
		if (drawOffset <= 0) {
			drawOffset = 0;
			return false;
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
		if (lineLength < 60) {
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
		int drawStickNumber = (int) (getDataQuadrantWidth() / lineLength);
		if (drawStickNumber >= linesData.get(0).getLineData().size()) {
			return false;
		} else {
			if (drawOffset >= (linesData.get(0).getLineData().size()
					- drawStickNumber - 1))
				return false;

			if (lineLength > 2) {
				lineLength = lineLength - 2;
				return true;
			}
			return false;
		}

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
	protected float calcDistance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
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
	 * @return the maxValue
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            the maxValue to set
	 */
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return the linesData
	 */
	public List<LineEntity<DateValueEntity>> getLinesData() {
		return linesData;
	}

	/**
	 * @param linesData
	 *            the linesData to set
	 */
	public void setLinesData(List<LineEntity<DateValueEntity>> linesData) {
		this.linesData = linesData;
	}

	public int getDrawOffset() {
		return drawOffset;
	}

	public void setDrawOffset(int drawOffset) {
		this.drawOffset = drawOffset;
	}

	public float getLineLength() {
		return lineLength;
	}

	public void setLineLength(float lineLength) {
		this.lineLength = lineLength;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

}
