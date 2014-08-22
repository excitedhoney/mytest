/*
 * SlipAreaChart.java
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

package cn.precious.metal.view.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import cn.precious.metal.view.KLineEntity;
import cn.precious.metal.view.entity.LineEntity;

/**
 * <p>
 * en
 * </p>
 * <p>
 * jp
 * </p>
 * <p>
 * cn
 * </p>
 * 
 * @author limc
 * @version v1.0 2014/01/22 16:19:37
 * 
 */
public class SlipFenshiAreaChart extends FenshiChart {

	/**
	 * <p>
	 * Constructor of SlipAreaChart
	 * </p>
	 * <p>
	 * SlipAreaChart类对象的构造函数
	 * </p>
	 * <p>
	 * SlipAreaChartのコンストラクター
	 * </p>
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlipFenshiAreaChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * Constructor of SlipAreaChart
	 * </p>
	 * <p>
	 * SlipAreaChart类对象的构造函数
	 * </p>
	 * <p>
	 * SlipAreaChartのコンストラクター
	 * </p>
	 * 
	 * @param context
	 * @param attrs
	 */
	public SlipFenshiAreaChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * Constructor of SlipAreaChart
	 * </p>
	 * <p>
	 * SlipAreaChart类对象的构造函数
	 * </p>
	 * <p>
	 * SlipAreaChartのコンストラクター
	 * </p>
	 * 
	 * @param context
	 */
	public SlipFenshiAreaChart(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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
		// draw lines
		drawAreas(canvas);
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
	protected void drawAreas(Canvas canvas) {
		if (null == linesData) {
			return;
		}
		// distance between two points
		// start point‘s X
		float startX;

		// draw lines
		for (int i = 0; i < linesData.size(); i++) {
			LineEntity<KLineEntity> line = (LineEntity<KLineEntity>) linesData
					.get(i);
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

			int daraLineNumber = (int) Math.floor(getDataQuadrantWidth()
					/ (lineLength + 1)) + 1; // 一个界面应该画的数量

			Paint mPaint = new Paint();
			mPaint.setColor(Color.parseColor("#5ccccc"));
			mPaint.setAlpha(70);
			mPaint.setAntiAlias(true);

			// set start point’s X

			if (axisYPosition == AXIS_Y_POSITION_RIGHT) {
				startX = getDataQuadrantEndX() ;
				Path linePath = new Path();
				for (int j = getDrawOffset(); j < getDrawOffset()
						+ daraLineNumber; j++) {
					if (j >= lineData.size()){
						linePath.lineTo(startX + 1 + getLineLength(), getDataQuadrantPaddingEndY());
						break;
					}
					float value = (float) lineData.get(j).getZuoShou() ;
					// calculate Y
					float valueY = (float) ((1f - (value - realMinValue)
							/ (realMaxValue - realMinValue)) * getDataQuadrantPaddingHeight())
							+ getDataQuadrantPaddingStartY();
					// if is not last point connect to previous point
					if (j == getDrawOffset()) {
						linePath.moveTo(startX, getDataQuadrantPaddingEndY());
						linePath.lineTo(startX, valueY);
					} else if (j == getDrawOffset() + daraLineNumber - 1) {
						linePath.lineTo(startX, valueY);
						linePath.lineTo(startX, getDataQuadrantPaddingEndY());
					} else {
						linePath.lineTo(startX, valueY);
					}
					startX = startX - getLineLength() - 1;
				}
				linePath.close();
				canvas.drawPath(linePath, mPaint);
			} else {

			}
		}
	}
}
