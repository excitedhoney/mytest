/*
 * GridChart.java
 * Android-Charts
 *
 * Created by limc on 2011/05/29.
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
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import cn.limc.androidcharts.event.ITouchEventNotify;
import cn.limc.androidcharts.event.ITouchEventResponse;

/**
 * GridChart是所有网格图表的基础类对象，它实现了基本的网格图表功能，这些功能将被它的继承类使用
 * 
 * @author limc
 * @version v1.0 2011/05/30 14:19:50
 * 
 */
public class UnionGridChart extends BaseChart implements ITouchEventNotify,
		ITouchEventResponse {

	protected int axisXPosition = DEFAULT_AXIS_X_POSITION;

	protected int axisYPosition = DEFAULT_AXIS_Y_POSITION;

	public static final int AXIS_X_POSITION_BOTTOM = 1 << 0;
	@Deprecated
	public static final int AXIS_X_POSITION_TOP = 1 << 1;
	public static final int AXIS_Y_POSITION_LEFT = 1 << 2;
	public static final int AXIS_Y_POSITION_RIGHT = 1 << 3;

	public static final int DEFAULT_AXIS_X_POSITION = AXIS_X_POSITION_BOTTOM;

	public static final int DEFAULT_AXIS_Y_POSITION = AXIS_Y_POSITION_RIGHT;


	/**
	 * <p>
	 * 默认背景色
	 * </p>
	 */
	public static final int DEFAULT_BACKGROUND_COLOR = Color.BLACK;

	/**
	 * <p>
	 * 默认网格经线的显示颜色
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_COLOR = Color.RED;

	/**
	 * <p>
	 * 默认网格纬线的显示颜色
	 * </p>
	 */
	public static final int DEFAULT_LAITUDE_COLOR = Color.RED;

	/**
	 * 
	 * 
	 */
	public static final float DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH = 16f;

	/**
	 * <p>
	 * 默认轴线下边距
	 * </p>
	 */
	public static final float DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT = 16f;

	/**
	 * <p>
	 * 网格纬线的数量
	 * </p>
	 */
	public static final int DEFAULT_LATITUDE_NUM = 6;

	/**
	 * <p>
	 * 网格经线的数量
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_NUM = 6;

	/**
	 * <p>
	 * 默认经线是否显示
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LONGITUDE = Boolean.FALSE;

	/**
	 * <p>
	 * 默认经线是否显示为虚线
	 * </p>
	 */
	public static final boolean DEFAULT_DASH_LONGITUDE = Boolean.FALSE;

	/**
	 * <p>
	 * 纬线是否显示
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LATITUDE = Boolean.FALSE;

	/**
	 * <p>
	 * 纬线是否显示为虚线
	 * </p>
	 */
	public static final boolean DEFAULT_DASH_LATITUDE = Boolean.FALSE;

	/**
	 * <p>
	 * X轴上的标题是否显示
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LONGITUDE_TITLE = Boolean.TRUE;

	/**
	 * <p>
	 * 默认Y轴上的标题是否显示
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LATITUDE_TITLE = Boolean.TRUE;

	/**
	 * <p>
	 * 默认控件是否显示边框
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_BORDER = Boolean.FALSE;
	/**
	 * <p>
	 * 默认XY轴是否显示边框
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_AXISXY = Boolean.FALSE;

	/**
	 * <p>
	 * 默认经线刻度字体颜色
	 * </p>
	 */
	public static final int DEFAULT_BORDER_COLOR = Color.RED;

	public static final float DEFAULT_BORDER_WIDTH = 1f;

	/**
	 * <p>
	 * 经线刻度字体颜色
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_FONT_COLOR = Color.WHITE;

	/**
	 * <p>
	 * 经线刻度字体大小
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_FONT_SIZE = 16;

	/**
	 * <p>
	 * 纬线刻度字体颜色
	 * </p>
	 */
	public static final int DEFAULT_LATITUDE_FONT_COLOR = Color.RED;

	/**
	 * <p>
	 * 默认纬线刻度字体大小
	 * </p>
	 */
	public static final int DEFAULT_LATITUDE_FONT_SIZE = 16;

	public static final int DEFAULT_CROSS_LINES_COLOR = Color.WHITE;
	public static final int DEFAULT_CROSS_LINES_FONT_COLOR = Color.WHITE;

	/**
	 * <p>
	 * 默认虚线效果
	 * </p>
	 */
	public static final PathEffect DEFAULT_DASH_EFFECT = new DashPathEffect(
			new float[] { 3, 3, 3, 3 }, 1);

	/**
	 * <p>
	 * 默认在控件被点击时，显示十字竖线线
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_CROSS_X_ON_TOUCH = true;

	/**
	 * 默认在控件被点击时，显示十字横线线 </p>
	 */
	public static final boolean DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH = true;

	/**
	 * <p>
	 * 网格经线的显示颜色
	 * </p>
	 */
	private int longitudeColor = DEFAULT_LONGITUDE_COLOR;

	/**
	 * <p>
	 * 网格纬线的显示颜色
	 * </p>
	 */
	private int latitudeColor = DEFAULT_LAITUDE_COLOR;

	/**
	 * <p>
	 * title 的宽度 Y轴标题的长度
	 * </p>
	 */
	protected float axisYTitleQuadrantWidth = DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH;

	/**
	 * <p>
	 * 轴线下边距 就是最上面的经线距离上面的高度
	 * </p>
	 */
	protected float axisXTitleQuadrantHeight = DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT;

	/**
	 * <p>
	 * X轴上的标题是否显示
	 * </p>
	 */
	private boolean displayLongitudeTitle = DEFAULT_DISPLAY_LONGITUDE_TITLE;

	/**
	 * <p>
	 * Y轴上的标题是否显示
	 * </p>
	 */
	private boolean displayLatitudeTitle = DEFAULT_DISPLAY_LATITUDE_TITLE;

	/**
	 * <p>
	 * 网格纬线的数量
	 * </p>
	 */
	protected int latitudeNum = DEFAULT_LATITUDE_NUM;
	
	protected int buttomLatitudeNum = DEFAULT_LATITUDE_NUM ;

	public int getButtomLatitudeNum() {
		return buttomLatitudeNum;
	}

	public void setButtomLatitudeNum(int buttomLatitudeNum) {
		this.buttomLatitudeNum = buttomLatitudeNum;
	}

	/**
	 * <p>
	 * 网格经线的数量
	 * </p>
	 */
	protected int longitudeNum = DEFAULT_LONGITUDE_NUM;

	/**
	 * <p>
	 * 经线是否显示
	 * </p>
	 */
	private boolean displayLongitude = DEFAULT_DISPLAY_LONGITUDE;

	/**
	 * <p>
	 * 经线是否显示为虚线
	 * </p>
	 */
	private boolean dashLongitude = DEFAULT_DASH_LONGITUDE;

	/**
	 * <p>
	 * 纬线是否显示
	 * </p>
	 */
	private boolean displayLatitude = DEFAULT_DISPLAY_LATITUDE;

	/**
	 * <p>
	 * 纬线是否显示为虚线
	 * </p>
	 */
	private boolean dashLatitude = DEFAULT_DASH_LATITUDE;

	/**
	 * <p>
	 * 虚线效果
	 * </p>
	 */
	private PathEffect dashEffect = DEFAULT_DASH_EFFECT;

	/**
	 * <p>
	 * 控件是否显示边框
	 * </p>
	 */
	private boolean displayBorder = DEFAULT_DISPLAY_BORDER;

	/**
	 * <p>
	 * 图边框的颜色
	 * </p>
	 */
	private int borderColor = DEFAULT_BORDER_COLOR;

	protected float borderWidth = DEFAULT_BORDER_WIDTH;

	/**
	 * <p>
	 * 经线刻度字体颜色
	 * </p>
	 */
	private int longitudeFontColor = DEFAULT_LONGITUDE_FONT_COLOR;

	/**
	 * <p>
	 * 经线刻度字体大小
	 * </p>
	 */
	private int longitudeFontSize = DEFAULT_LONGITUDE_FONT_SIZE;

	/**
	 * <p>
	 * 纬线刻度字体颜色
	 * </p>
	 */
	private int latitudeFontColor = DEFAULT_LATITUDE_FONT_COLOR;

	/**
	 * <p>
	 * 纬线刻度字体大小
	 * </p>
	 */
	private int latitudeFontSize = DEFAULT_LATITUDE_FONT_SIZE;

	/**
	 * <p>
	 * 十字交叉线颜色
	 * </p>
	 */
	private int crossLinesColor = DEFAULT_CROSS_LINES_COLOR;

	/**
	 * <p>
	 * 十字交叉线坐标轴字体颜色
	 * </p>
	 */
	private int crossLinesFontColor = DEFAULT_CROSS_LINES_FONT_COLOR;

	/**
	 * <p>
	 * X轴标题数组
	 * </p>
	 */
	private List<String> longitudeTitles;

	/**
	 * <p>
	 * 上半部分Y轴标题数组
	 * </p>
	 */
	private List<String> latitudeTopTitles;
	/**
	 * <p>
	 * 上半部分Y轴标题数组
	 * </p>
	 */
	private List<String> latitudeButtomTitles;


	/**
	 * <p>
	 * 在控件被点击时，显示十字竖线线
	 * </p>
	 */
	private boolean displayCrossXOnTouch = DEFAULT_DISPLAY_CROSS_X_ON_TOUCH;

	/**
	 * <p>
	 * 在控件被点击时，显示十字横线线
	 * </p>
	 */
	private boolean displayCrossYOnTouch = DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH;

	/**
	 * <p>
	 * 单点触控的选中点
	 * </p>
	 */
	private PointF touchPoint;

	/**
	 * <p>
	 * 单点触控的选中点的X
	 * </p>
	 */
	private float clickPostX;

	/**
	 * <p>
	 * 单点触控的选中点的Y
	 * </p>
	 */
	private float clickPostY;

	/**
	 * <p>
	 * 事件通知对象列表
	 * </p>
	 */
	private List<ITouchEventResponse> notifyList;

	/*
	 * (non-Javadoc)
	 * 
	 * @param context
	 * 
	 * @see cn.limc.androidcharts.view.BaseChart#BaseChart(Context)
	 */
	public UnionGridChart(Context context) {
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
	public UnionGridChart(Context context, AttributeSet attrs, int defStyle) {
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
	public UnionGridChart(Context context, AttributeSet attrs) {
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
		super.onDraw(canvas);
		if (displayLongitude || displayLongitudeTitle) {
			drawLongitudeLine(canvas);
			drawLongitudeTitle(canvas);
		}
		if (displayLatitude || displayLatitudeTitle) {
			drawLatitudeLine(canvas);
			drawLatitudeTitle(canvas);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
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
				|| event.getY() >= super.getHeight()) {
			return false;
		}

		// touched points, if touch point is only one
		clickPostX = event.getX();
		clickPostY = event.getY();
		PointF point = new PointF(clickPostX, clickPostY);
		touchPoint = point;
		// redraw
		super.invalidate();
		// do notify
		notifyEventAll(this);
		return true;

	}

	protected void drawAlphaTextBox(PointF ptStart, PointF ptEnd,
			String content, int fontSize, Canvas canvas) {

		Paint mPaintBox = new Paint();
		mPaintBox.setColor(Color.WHITE);
		mPaintBox.setAlpha(100);
		mPaintBox.setStyle(Style.FILL);

		Paint mPaintBoxLine = new Paint();
		mPaintBoxLine.setColor(crossLinesColor);
		mPaintBoxLine.setAntiAlias(true);
		mPaintBoxLine.setTextSize(fontSize);

		// draw a rectangle
		canvas.drawRect(ptStart.x, ptStart.y, ptEnd.x, ptEnd.y, mPaintBox);

		// draw a rectangle' border
		canvas.drawLine(ptStart.x, ptStart.y, ptStart.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptStart.x, ptEnd.y, ptEnd.x, ptEnd.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptEnd.y, ptEnd.x, ptStart.y, mPaintBoxLine);
		canvas.drawLine(ptEnd.x, ptStart.y, ptStart.x, ptStart.y, mPaintBoxLine);

		mPaintBoxLine.setColor(crossLinesFontColor);
		// draw text
		canvas.drawText(content, ptStart.x, ptStart.y + fontSize, mPaintBoxLine);
	}

	protected float getDataQuadrantStartY() {
		return borderWidth;
	}

	protected float getDataQuadrantEndY() {
		return super.getHeight() - borderWidth - axisXTitleQuadrantHeight;
	}

	/**
	 * 获取绘制的view 的实际宽度
	 * 
	 * @return
	 */
	protected float getDataQuadrantWidth() {
		return super.getWidth() - axisYTitleQuadrantWidth - borderWidth;
	}

	protected float getDataQuadrantHeight() {
		return super.getHeight() - axisXTitleQuadrantHeight - 2 * borderWidth;
	}

	protected float getDataQuadrantStartX() {
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			return borderWidth + axisYTitleQuadrantWidth;
		} else {
			return borderWidth;
		}
	}

	protected float getDataQuadrantEndX() {
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			return super.getWidth() - borderWidth;
		} else {
			return super.getWidth() - borderWidth - axisYTitleQuadrantWidth;
		}
	}

	/**
	 * <p>
	 * calculate degree title on X axis
	 * </p>
	 * <p>
	 * X軸の目盛を計算する
	 * </p>
	 * <p>
	 * 计算X轴上显示的坐标值
	 * </p>
	 * 
	 * @param value
	 *            <p>
	 *            value for calculate
	 *            </p>
	 *            <p>
	 *            計算有用データ
	 *            </p>
	 *            <p>
	 *            计算用数据
	 *            </p>
	 * 
	 * @return String
	 *         <p>
	 *         degree
	 *         </p>
	 *         <p>
	 *         目盛
	 *         </p>
	 *         <p>
	 *         坐标值
	 *         </p>
	 */
	public String getAxisXGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- getDataQuadrantStartX();
		return String.valueOf(valueLength / this.getDataQuadrantWidth());
	}

	/**
	 * <p>
	 * 计算Y轴上显示的坐标值
	 * </p>
	 * 
	 * <p>
	 * 计算用数据
	 * </p>
	 * 
	 * @return String
	 *         <p>
	 *         degree
	 *         </p>
	 *         <p>
	 *         目盛
	 *         </p>
	 *         <p>
	 *         坐标值
	 *         </p>
	 */
	public String getAxisYGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- getDataQuadrantStartY();
		return String.valueOf(valueLength / this.getDataQuadrantHeight());
	}

	


	/**
	 * <p>
	 * 绘制经线 Y轴 方向
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLongitudeLine(Canvas canvas) {
		if (null == longitudeTitles) {
			return;
		}
		if (!displayLongitude) {
			return;
		}
		int counts = longitudeTitles.size();
		float topLenght = (super.getHeight() * 2 / 3)
				- axisXTitleQuadrantHeight;

		Paint mPaintLine = new Paint();
		mPaintLine.setColor(longitudeColor);
		if (dashLongitude) {
			mPaintLine.setPathEffect(dashEffect);
		}
		if (axisYPosition == AXIS_Y_POSITION_RIGHT) {// 绘制Y轴在左边的
			if (counts > 1) {
				float postOffset = this.getDataQuadrantWidth() / (counts - 1);

				float offset = getDataQuadrantEndX();

				for (int i = counts - 1; i >= 0; i--) {
					canvas.drawLine(offset - (counts - i - 1) * postOffset,
							borderWidth,
							offset - (counts - i - 1) * postOffset, topLenght,
							mPaintLine);

					canvas.drawLine(offset - (counts - i - 1) * postOffset,
							super.getHeight() * 2 / 3, offset
									- (counts - i - 1) * postOffset,
							super.getHeight(), mPaintLine);
				}
			}
		} else {
			if (counts > 1) {
				float postOffset = this.getDataQuadrantWidth() / (counts - 1);

				float offset = getDataQuadrantStartX();

				for (int i = 0; i < counts; i++) {
					canvas.drawLine(offset + i * postOffset, borderWidth
							+ axisXTitleQuadrantHeight,
							offset + i * postOffset, topLenght, mPaintLine);

					canvas.drawLine(offset + i * postOffset,
							super.getHeight() * 4 / 5, offset + i * postOffset,
							super.getHeight(), mPaintLine);
				}
			}
		}
	}

	/**
	 * <p>
	 * 绘制X轴 标题
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLongitudeTitle(Canvas canvas) {

		if (null == longitudeTitles) {
			return;
		}
		if (!displayLongitude) {
			return;
		}
		if (!displayLongitudeTitle) {
			return;
		}

		if (longitudeTitles.size() <= 1) {
			return;
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(longitudeFontColor);
		mPaintFont.setTextSize(longitudeFontSize);
		mPaintFont.setAntiAlias(true);

		int counts = longitudeTitles.size();

		if (axisYPosition == AXIS_Y_POSITION_RIGHT) {// 绘制Y轴在左边的
			if (counts > 1) {
				float postOffset = this.getDataQuadrantWidth() / (counts - 1);

				float offset = getDataQuadrantEndX();

				for (int i = 0; i < counts; i++) {
					if (i == 0 || i == counts - 1)
						continue;
					canvas.drawText(longitudeTitles.get(i),
							offset - i * postOffset
									- (longitudeTitles.get(i).length())
									* longitudeFontSize / 4f,
							super.getHeight() * 2 / 3, mPaintFont);
				}
			}

		} else {
			
			
		}
	}

	/**
	 * <p>
	 * 绘制纬线
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLatitudeLine(Canvas canvas) {

		if (null == latitudeTopTitles) {
			return;
		}
		if (!displayLatitude) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}
		if (latitudeTopTitles.size() <= 1) {
			return;
		}

		float length = getDataQuadrantWidth();

		Paint mPaintLine = new Paint();
		mPaintLine.setColor(latitudeColor);
		if (dashLatitude) {
			mPaintLine.setPathEffect(dashEffect);
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(latitudeFontColor);
		mPaintFont.setTextSize(latitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)
				/ (getLatitudeNum());
		float buttomPostOffset = (super.getHeight() * 1 / 3)
				/ (getButtomLatitudeNum());

		float offset = super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight;
		float buttomYOffset = super.getHeight() * 2 / 3;

		float startFrom = getDataQuadrantStartX();
		if (axisYPosition == AXIS_Y_POSITION_RIGHT) {
			for (int i = 0; i <= getLatitudeNum(); i++) {
				if (i == 0) {
					canvas.drawLine(startFrom, offset, startFrom + length,
							offset, mPaintLine);
				} else {
					canvas.drawLine(startFrom, offset - i * postOffset + 1f,
							startFrom + length, offset - i * postOffset + 1f,
							mPaintLine);
				}

			}

			for (int i = 0; i <= getButtomLatitudeNum(); i++) {
				if (i == latitudeButtomTitles.size() - 1) {
					canvas.drawLine(startFrom, super.getHeight() - borderWidth,
							startFrom + length,
							super.getHeight() - borderWidth, mPaintLine);
				} else {
					canvas.drawLine(startFrom, buttomYOffset + i
							* buttomPostOffset, startFrom + length,
							buttomYOffset + i * buttomPostOffset, mPaintLine);

				}
			}

		} 

	}

	/**
	 * <p>
	 * 绘制纬线
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLatitudeTitle(Canvas canvas) {
		if (null == latitudeTopTitles) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}
		if (latitudeTopTitles.size() <= 1) {
			return;
		}
		Paint mPaintFont = new Paint();
		mPaintFont.setColor(latitudeFontColor);
		mPaintFont.setTextSize(latitudeFontSize);
		mPaintFont.setAntiAlias(true);

		if (axisYPosition == AXIS_Y_POSITION_RIGHT) {
			float topPostOffset = (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)
					/ (latitudeTopTitles.size() - 1);

			float offset = super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight;

			for (int i = 0; i < latitudeTopTitles.size(); i++) {
				if (i == 0) {
					canvas.drawText(
							latitudeTopTitles.get(i),
							getDataQuadrantEndX() + 5f,
							offset - 1f,
							mPaintFont);
				} else if (i == latitudeTopTitles.size() - 1) {
					canvas.drawText(latitudeTopTitles.get(i),
							getDataQuadrantEndX() + 5f, getTextHeight(mPaintFont,latitudeTopTitles.get(i)) + 1f,
							mPaintFont);
				} else {
					canvas.drawText(latitudeTopTitles.get(i),
							getDataQuadrantEndX() + 5f, offset - i
									* topPostOffset + latitudeFontSize / 2f,
							mPaintFont);
				}
			}

			float buttomPostOffset = (super.getHeight() * 1 / 3)
					/ (latitudeButtomTitles.size() - 1);

			float buttomOffset = super.getHeight();

			for (int i = 0; i < latitudeButtomTitles.size(); i++) {
				if (i == 0) {
					canvas.drawText(
							latitudeButtomTitles.get(i),
							getDataQuadrantEndX() + 5f,
							buttomOffset - 1f,
							mPaintFont);
				} else {
					canvas.drawText(latitudeButtomTitles.get(i),
							getDataQuadrantEndX() + 5f, buttomOffset - i
									* buttomPostOffset + latitudeFontSize / 2f,
							mPaintFont);
				}
			}

		} else {

		}

		// String max = "" + bVolumeMaxValue;
		//
		// canvas.drawText(max,
		// getDataQuadrantStartX() - mPaintFont.measureText(max) - 5f,
		// super.getHeight() * 4 / 5 + latitudeFontSize / 2f, mPaintFont);
		//
		// canvas.drawText("万股",
		// getDataQuadrantStartX() - mPaintFont.measureText("万股") - 5f,
		// super.getHeight() - latitudeFontSize + 2f, mPaintFont);

	}

	/**
	 * <p>
	 * 放大表示
	 * </p>
	 */
	protected boolean zoomIn() {
		// DO NOTHING
		return false ;
	}

	/**
	 * <p>
	 * 缩小
	 * </p>
	 */
	protected boolean zoomOut() {
		// DO NOTHING
		return false ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param event
	 * 
	 * @see
	 * cn.limc.androidcharts.event.ITouchEventResponse#notifyEvent(GridChart)
	 */
	public void notifyEvent(BaseChart chart) {
		PointF point = chart.getTouchPoint();
		if (null != point) {
			clickPostX = point.x;
			clickPostY = point.y;
		}
		touchPoint = new PointF(clickPostX, clickPostY);
		super.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param event
	 * 
	 * @see
	 * cn.limc.androidcharts.event.ITouchEventNotify#addNotify(ITouchEventResponse
	 * )
	 */
	public void addNotify(ITouchEventResponse notify) {
		if (null == notifyList) {
			notifyList = new ArrayList<ITouchEventResponse>();
		}
		notifyList.add(notify);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param event
	 * 
	 * @see cn.limc.androidcharts.event.ITouchEventNotify#removeNotify(int)
	 */
	public void removeNotify(int index) {
		if (null != notifyList && notifyList.size() > index) {
			notifyList.remove(index);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param event
	 * 
	 * @see cn.limc.androidcharts.event.ITouchEventNotify#removeNotify()
	 */
	public void removeAllNotify() {
		if (null != notifyList) {
			notifyList.clear();
		}
	}

	public void notifyEventAll(BaseChart chart) {
		if (null != notifyList) {
			for (int i = 0; i < notifyList.size(); i++) {
				ITouchEventResponse ichart = notifyList.get(i);
				ichart.notifyEvent(chart);
			}
		}
	}

	/**
	 * @return the longitudeColor
	 */
	public int getLongitudeColor() {
		return longitudeColor;
	}

	/**
	 * @param longitudeColor
	 *            the longitudeColor to set
	 */
	public void setLongitudeColor(int longitudeColor) {
		this.longitudeColor = longitudeColor;
	}

	/**
	 * @return the latitudeColor
	 */
	public int getLatitudeColor() {
		return latitudeColor;
	}

	/**
	 * @param latitudeColor
	 *            the latitudeColor to set
	 */
	public void setLatitudeColor(int latitudeColor) {
		this.latitudeColor = latitudeColor;
	}

	/**
	 * @return the axisMarginLeft
	 */
	@Deprecated
	public float getAxisMarginLeft() {
		return axisYTitleQuadrantWidth;
	}

	/**
	 * @param axisMarginLeft
	 *            the axisMarginLeft to set
	 */
	@Deprecated
	public void setAxisMarginLeft(float axisMarginLeft) {
		this.axisYTitleQuadrantWidth = axisMarginLeft;
	}

	/**
	 * @return the axisMarginLeft
	 */
	public float getAxisYTitleQuadrantWidth() {
		return axisYTitleQuadrantWidth;
	}

	/**
	 * @param axisYTitleQuadrantWidth
	 *            the axisYTitleQuadrantWidth to set
	 */
	public void setAxisYTitleQuadrantWidth(float axisYTitleQuadrantWidth) {
		this.axisYTitleQuadrantWidth = axisYTitleQuadrantWidth;
	}

	/**
	 * @return the axisXTitleQuadrantHeight
	 */
	@Deprecated
	public float getAxisMarginBottom() {
		return axisXTitleQuadrantHeight;
	}

	/**
	 * @param axisXTitleQuadrantHeight
	 *            the axisXTitleQuadrantHeight to set
	 */
	@Deprecated
	public void setAxisMarginBottom(float axisXTitleQuadrantHeight) {
		this.axisXTitleQuadrantHeight = axisXTitleQuadrantHeight;
	}

	/**
	 * @return the axisXTitleQuadrantHeight
	 */
	public float getAxisXTitleQuadrantHeight() {
		return axisXTitleQuadrantHeight;
	}

	/**
	 * @param axisXTitleQuadrantHeight
	 *            the axisXTitleQuadrantHeight to set
	 */
	public void setAxisXTitleQuadrantHeight(float axisXTitleQuadrantHeight) {
		this.axisXTitleQuadrantHeight = axisXTitleQuadrantHeight;
	}

	/**
	 * @return the displayLongitudeTitle
	 */
	public boolean isDisplayLongitudeTitle() {
		return displayLongitudeTitle;
	}

	/**
	 * @param displayLongitudeTitle
	 *            the displayLongitudeTitle to set
	 */
	public void setDisplayLongitudeTitle(boolean displayLongitudeTitle) {
		this.displayLongitudeTitle = displayLongitudeTitle;
	}

	/**
	 * @return the displayAxisYTitle
	 */
	public boolean isDisplayLatitudeTitle() {
		return displayLatitudeTitle;
	}

	/**
	 * @param displayLatitudeTitle
	 *            the displayLatitudeTitle to set
	 */
	public void setDisplayLatitudeTitle(boolean displayLatitudeTitle) {
		this.displayLatitudeTitle = displayLatitudeTitle;
	}

	/**
	 * @return the latitudeNum
	 */
	public int getLatitudeNum() {
		return latitudeNum;
	}

	/**
	 * @param latitudeNum
	 *            the latitudeNum to set
	 */
	public void setLatitudeNum(int latitudeNum) {
		this.latitudeNum = latitudeNum;
	}

	/**
	 * @return the longitudeNum
	 */
	public int getLongitudeNum() {
		return longitudeNum;
	}

	/**
	 * @param longitudeNum
	 *            the longitudeNum to set
	 */
	public void setLongitudeNum(int longitudeNum) {
		this.longitudeNum = longitudeNum;
	}

	/**
	 * @return the displayLongitude
	 */
	public boolean isDisplayLongitude() {
		return displayLongitude;
	}

	/**
	 * @param displayLongitude
	 *            the displayLongitude to set
	 */
	public void setDisplayLongitude(boolean displayLongitude) {
		this.displayLongitude = displayLongitude;
	}

	/**
	 * @return the dashLongitude
	 */
	public boolean isDashLongitude() {
		return dashLongitude;
	}

	/**
	 * @param dashLongitude
	 *            the dashLongitude to set
	 */
	public void setDashLongitude(boolean dashLongitude) {
		this.dashLongitude = dashLongitude;
	}

	/**
	 * @return the displayLatitude
	 */
	public boolean isDisplayLatitude() {
		return displayLatitude;
	}

	/**
	 * @param displayLatitude
	 *            the displayLatitude to set
	 */
	public void setDisplayLatitude(boolean displayLatitude) {
		this.displayLatitude = displayLatitude;
	}

	/**
	 * @return the dashLatitude
	 */
	public boolean isDashLatitude() {
		return dashLatitude;
	}

	/**
	 * @param dashLatitude
	 *            the dashLatitude to set
	 */
	public void setDashLatitude(boolean dashLatitude) {
		this.dashLatitude = dashLatitude;
	}

	/**
	 * @return the dashEffect
	 */
	public PathEffect getDashEffect() {
		return dashEffect;
	}

	/**
	 * @param dashEffect
	 *            the dashEffect to set
	 */
	public void setDashEffect(PathEffect dashEffect) {
		this.dashEffect = dashEffect;
	}

	/**
	 * @return the displayBorder
	 */
	public boolean isDisplayBorder() {
		return displayBorder;
	}

	/**
	 * @param displayBorder
	 *            the displayBorder to set
	 */
	public void setDisplayBorder(boolean displayBorder) {
		this.displayBorder = displayBorder;
	}

	/**
	 * @return the borderColor
	 */
	public int getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor
	 *            the borderColor to set
	 */
	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * @return the borderWidth
	 */
	public float getBorderWidth() {
		return borderWidth;
	}

	/**
	 * @param borderWidth
	 *            the borderWidth to set
	 */
	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
	}

	/**
	 * @return the longitudeFontColor
	 */
	public int getLongitudeFontColor() {
		return longitudeFontColor;
	}

	/**
	 * @param longitudeFontColor
	 *            the longitudeFontColor to set
	 */
	public void setLongitudeFontColor(int longitudeFontColor) {
		this.longitudeFontColor = longitudeFontColor;
	}

	/**
	 * @return the longitudeFontSize
	 */
	public int getLongitudeFontSize() {
		return longitudeFontSize;
	}

	/**
	 * @param longitudeFontSize
	 *            the longitudeFontSize to set
	 */
	public void setLongitudeFontSize(int longitudeFontSize) {
		this.longitudeFontSize = longitudeFontSize;
	}

	/**
	 * @return the latitudeFontColor
	 */
	public int getLatitudeFontColor() {
		return latitudeFontColor;
	}

	/**
	 * @param latitudeFontColor
	 *            the latitudeFontColor to set
	 */
	public void setLatitudeFontColor(int latitudeFontColor) {
		this.latitudeFontColor = latitudeFontColor;
	}

	/**
	 * @return the latitudeFontSize
	 */
	public int getLatitudeFontSize() {
		return latitudeFontSize;
	}

	/**
	 * @param latitudeFontSize
	 *            the latitudeFontSize to set
	 */
	public void setLatitudeFontSize(int latitudeFontSize) {
		this.latitudeFontSize = latitudeFontSize;
	}

	/**
	 * @return the crossLinesColor
	 */
	public int getCrossLinesColor() {
		return crossLinesColor;
	}

	/**
	 * @param crossLinesColor
	 *            the crossLinesColor to set
	 */
	public void setCrossLinesColor(int crossLinesColor) {
		this.crossLinesColor = crossLinesColor;
	}

	/**
	 * @return the crossLinesFontColor
	 */
	public int getCrossLinesFontColor() {
		return crossLinesFontColor;
	}

	/**
	 * @param crossLinesFontColor
	 *            the crossLinesFontColor to set
	 */
	public void setCrossLinesFontColor(int crossLinesFontColor) {
		this.crossLinesFontColor = crossLinesFontColor;
	}

	/**
	 * @return the longitudeTitles
	 */
	public List<String> getLongitudeTitles() {
		return longitudeTitles;
	}

	/**
	 * @param longitudeTitles
	 *            the longitudeTitles to set
	 */
	public void setLongitudeTitles(List<String> longitudeTitles) {
		this.longitudeTitles = longitudeTitles;
	}

	/**
	 * @return the displayCrossXOnTouch
	 */
	public boolean isDisplayCrossXOnTouch() {
		return displayCrossXOnTouch;
	}

	/**
	 * @param displayCrossXOnTouch
	 *            the displayCrossXOnTouch to set
	 */
	public void setDisplayCrossXOnTouch(boolean displayCrossXOnTouch) {
		this.displayCrossXOnTouch = displayCrossXOnTouch;
	}

	/**
	 * @return the displayCrossYOnTouch
	 */
	public boolean isDisplayCrossYOnTouch() {
		return displayCrossYOnTouch;
	}

	/**
	 * @param displayCrossYOnTouch
	 *            the displayCrossYOnTouch to set
	 */
	public void setDisplayCrossYOnTouch(boolean displayCrossYOnTouch) {
		this.displayCrossYOnTouch = displayCrossYOnTouch;
	}

	/**
	 * @return the clickPostX
	 */
	public float getClickPostX() {
		return clickPostX;
	}

	/**
	 * @param clickPostX
	 *            the clickPostX to set
	 */
	public void setClickPostX(float clickPostX) {
		this.clickPostX = clickPostX;
	}

	/**
	 * @return the clickPostY
	 */
	public float getClickPostY() {
		return clickPostY;
	}

	/**
	 * @param clickPostY
	 *            the clickPostY to set
	 */
	public void setClickPostY(float clickPostY) {
		this.clickPostY = clickPostY;
	}

	/**
	 * @return the notifyList
	 */
	public List<ITouchEventResponse> getNotifyList() {
		return notifyList;
	}

	/**
	 * @param notifyList
	 *            the notifyList to set
	 */
	public void setNotifyList(List<ITouchEventResponse> notifyList) {
		this.notifyList = notifyList;
	}

	/**
	 * @return the touchPoint
	 */
	public PointF getTouchPoint() {
		return touchPoint;
	}

	/**
	 * @param touchPoint
	 *            the touchPoint to set
	 */
	public void setTouchPoint(PointF touchPoint) {
		this.touchPoint = touchPoint;
	}

	public List<String> getLatitudeTopTitles() {
		return latitudeTopTitles;
	}

	public void setLatitudeTopTitles(List<String> latitudeTopTitles) {
		this.latitudeTopTitles = latitudeTopTitles;
	}

	public List<String> getLatitudeButtomTitles() {
		return latitudeButtomTitles;
	}

	public void setLatitudeButtomTitles(List<String> latitudeButtomTitles) {
		this.latitudeButtomTitles = latitudeButtomTitles;
	}


	public int getAxisYPosition() {
		return axisYPosition;
	}

	public void setAxisYPosition(int axisYPosition) {
		this.axisYPosition = axisYPosition;
	}


}
