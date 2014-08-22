package cn.precious.metal.view.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import cn.precious.metal.view.GoldPointValue;
import cn.precious.metal.view.KLineEntity;
import cn.precious.metal.view.entity.GLineToolsEntity;

public class GoldLineChart extends UnionGridChart {
	
	private boolean showHorLine ;

	private UnionCandleStickChart mUnionCandleStickChart;

	private GLineToolsEntity mGoldLineEntity;

	/**
	 * 默认表示柱条的边框颜色
	 */
	public static final int DEFAULT_STICK_BORDER_COLOR = Color.RED;

	/**
	 * 默认表示柱条的填充颜色
	 */
	public static final int DEFAULT_STICK_FILL_COLOR = Color.RED;

	/**
	 * 表示柱条的边框颜色
	 */
	private int stickBorderColor = DEFAULT_STICK_BORDER_COLOR;

	/**
	 * 表示柱条的填充颜色
	 */
	private int stickFillColor = DEFAULT_STICK_FILL_COLOR;

	public static final boolean DEFAULT_AUTO_CALC_VALUE_RANGE = false;

	public static final int DEFAULT_STICK_SPACING = 1;

	/**
	 * Y的最大表示值
	 */
	protected double maxValue;

	/**
	 * Y的最小表示值
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
	public GoldLineChart(Context context) {
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
	public GoldLineChart(Context context, AttributeSet attrs, int defStyle) {
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
	public GoldLineChart(Context context, AttributeSet attrs) {
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
		drawLine(canvas);
		if(showHorLine)
			drawGoldLine(canvas);
	}

	public void drawGoldLine(Canvas canvas) {
		if (mUnionCandleStickChart == null)
			return;

		if (mGoldLineEntity == null)
			return;

		if (mGoldLineEntity.getSecondValue() == null)
			return;

		Paint mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(4f);

		GoldPointValue first = mGoldLineEntity.getFirstValue();
		
		GoldPointValue second = mGoldLineEntity.getSecondValue();

		int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart
				.getStickWidth() + 1));

		float firstXAxisPosition = (1f - (first.getPosition() - mUnionCandleStickChart
				.getDrawOffset()) / (0f + drawStickNumber))
				* getDataQuadrantWidth();
		float firstYAxisPosition = (float) ((1f - (first.getAxisYvalue() - minValue)
				/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));

		float secondXAxisPosition = (1f - (second.getPosition() - mUnionCandleStickChart
				.getDrawOffset()) / (0f + drawStickNumber))
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

	public void calcRange() {
		if (mUnionCandleStickChart == null)
			return;

		List<KLineEntity> stickData = mUnionCandleStickChart.getStickData();

		if (stickData == null || stickData.isEmpty())
			return;

		int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart
				.getStickWidth() + 1)) + 1;
		
		
		int drawOffset = mUnionCandleStickChart.getDrawOffset()  ;
		
		if(drawOffset <=  4) {
			for (int i = 5; i < drawStickNumber + mUnionCandleStickChart.getDrawOffset(); i++) {
				if (i >= stickData.size()) {
					break;
				}
				if (i == 5) {
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
		}else{
			for (int i = drawOffset; i < drawStickNumber + mUnionCandleStickChart.getDrawOffset(); i++) {
				if (i >= stickData.size()) {
					break;
				}
				if (i == drawOffset) {
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

	private float mLastMotionX;
	private float mLastMotionY;
	
	private float xMoveOffset = 0;
	private float yMoveOffset = 0;

	private boolean isNearFirst = false;

	private boolean isNearSeconde = false;

	private boolean isRange;
	

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

		if (event.getX() < getDataQuadrantStartX() || event.getX() > getDataQuadrantEndX()) {
			return false;
		}

		if (event.getY() < getDataQuadrantStartY()
				|| event.getY() >= (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
			return false;
		}

		final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super
				.getWidth() / 50);
		
		getParent().requestDisallowInterceptTouchEvent(true);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			touchMode = DOWN;
			mLastMotionX = event.getX();
			mLastMotionY = event.getY();
			
			if (mGoldLineEntity == null) {
				mGoldLineEntity = new GLineToolsEntity();
				
				mGoldLineEntity.setGlineType(GLineToolsChart.GOLD_LINE_TYPE);
				double axisYvalue = (1f - (event.getY() / (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)))
						* (maxValue - minValue) + minValue;
				GoldPointValue firstPoint = new GoldPointValue(
						getIntersectionPosition(event.getX()), axisYvalue);
				mGoldLineEntity.setFirstValue(firstPoint);
				isNearFirst = true;
			} else {
				GoldPointValue firstPoint = mGoldLineEntity.getFirstValue();
				
				GoldPointValue secondPoint = mGoldLineEntity.getSecondValue();
				int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart.getStickWidth() + 1));

				float firstXaxisPosition = (float) (getDataQuadrantWidth())
						* (1f - (firstPoint.getPosition() - mUnionCandleStickChart.getDrawOffset()) / (0f + drawStickNumber));
				float firstYaxisPosition = (float) ((1f - (firstPoint.getAxisYvalue() - minValue) / (maxValue - minValue)) * (super
						.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));

				if (Math.abs(event.getX() - firstXaxisPosition) <= 25
						&& Math.abs(event.getY() - firstYaxisPosition) <= 25) {
					isNearFirst = true;
				}
				if (secondPoint != null) {
					float secondXaxisPosition = (float) (getDataQuadrantWidth())
							* (1f - (secondPoint.getPosition() - mUnionCandleStickChart.getDrawOffset()) 
							/ (0f + drawStickNumber));
					float secondYaxisPosition = (float) ((1f - (secondPoint
							.getAxisYvalue() - minValue)
							/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
					if (Math.abs(event.getX() - secondXaxisPosition) <= 25 && Math.abs(event.getY() - secondYaxisPosition) <= 25) {
						isNearSeconde = true;
					} else {
						float xMinPosition = Math.min(firstXaxisPosition,
								secondXaxisPosition);
						float yMinPosition = Math.min(firstYaxisPosition,
								secondYaxisPosition);
						float xMaxPosition = Math.max(firstXaxisPosition,
								secondXaxisPosition);
						float yMaxPosition = Math.max(firstYaxisPosition,
								secondYaxisPosition);

						GoldPointValue first = mGoldLineEntity.getFirstValue();
						float y1 = (float) ((1f - (first.getAxisYvalue() - minValue)
								/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
						float x1 = (float) ((1f - (first.getPosition() - mUnionCandleStickChart
								.getDrawOffset()) / (0f + drawStickNumber)) * getDataQuadrantWidth());

						GoldPointValue second = mGoldLineEntity.getSecondValue();
						float y2 = (float) ((1f - (second.getAxisYvalue() - minValue)
								/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
						float x2 = (float) ((1f - (second.getPosition() - mUnionCandleStickChart
								.getDrawOffset()) / (0f + drawStickNumber)) * getDataQuadrantWidth());

						float k = (y1 - y2) / (x1 - x2);
						float b = (x1 * y2 - x2 * y1) / (x1 - x2);
						float bLeft = (x1 * y2 - x2 * y1) / (x1 - x2) - 25 * k;
						float bRight = (x1 * y2 - x2 * y1) / (x1 - x2) + 25 * k;

						if (event.getX() < xMaxPosition
								&& event.getX() > xMinPosition
								&& event.getY() < yMaxPosition
								&& event.getY() > yMinPosition) {
							isRange = true;
						} else {
//							if (((k * event.getX() + bLeft - event.getY()) * (k
//									* event.getX() + bRight - event.getY())) <= 0) {
//								isRange = true;
//							}

						}
					}
				}
			}
			
			
			if(mGoldLineEntity != null) {
				if(showHorLine)
					mGoldLineEntity.setShowHorLine(true);
				else
					mGoldLineEntity.setShowHorLine(false);
			}
			break;
		case MotionEvent.ACTION_UP:
			isNearFirst = false;
			isNearSeconde = false;
			isRange = false;
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
				if (isNearFirst) {
					if (mGoldLineEntity.getSecondValue() == null) { // 优先给
						double axisYvalue = (1f - (event.getY() / (super
								.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)))
								* (maxValue - minValue) + minValue;
						GoldPointValue secondPoint = new GoldPointValue(
								getIntersectionPosition(event.getX()),
								axisYvalue);
						mGoldLineEntity.setSecondValue(secondPoint);

					} else {
						double axisYvalue = (1f - (event.getY() / (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)))
								* (maxValue - minValue) + minValue;
						GoldPointValue firstPoint = new GoldPointValue(getIntersectionPosition(event.getX()),axisYvalue);
						mGoldLineEntity.setFirstValue(firstPoint);
					}
					// redraw
					super.invalidate();
				} else if (isNearSeconde) {
					double axisYvalue = (1f - (event.getY() / (super
							.getHeight() * 2 / 3 - axisXTitleQuadrantHeight)))
							* (maxValue - minValue) + minValue;
					GoldPointValue secondPoint = new GoldPointValue(
							getIntersectionPosition(event.getX()), axisYvalue);
					mGoldLineEntity.setSecondValue(secondPoint);
					// redraw
					super.invalidate();
				} 
				
//				else if (isRange) {
//					
//					xMoveOffset += Math.abs(mLastMotionX - event.getX());
//					yMoveOffset += Math.abs(mLastMotionY - event.getY());
//
//					int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart
//							.getStickWidth() + 1));
//
//					float firstAxisYposition = (float) ((1f - (mGoldLineEntity
//							.getFirstValue().getAxisYvalue() - minValue)
//							/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))  + getDataQuadrantStartY();
//					float secondAxisYposition = (float) ((1f - (mGoldLineEntity
//							.getSecondValue().getAxisYvalue() - minValue)
//							/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
//
//					float firstAxisXposition = (1f - (mGoldLineEntity
//							.getFirstValue().getPosition() - mUnionCandleStickChart
//							.getDrawOffset())
//							/ (0f + drawStickNumber))
//							* getDataQuadrantWidth() + getDataQuadrantStartX();
//					float secondAxisXposition = (1f - (mGoldLineEntity
//							.getSecondValue().getPosition() - mUnionCandleStickChart
//							.getDrawOffset())
//							/ (0f + drawStickNumber))
//							* getDataQuadrantWidth() + getDataQuadrantStartX();
//
//					GoldPointValue first = mGoldLineEntity.getFirstValue();
//					GoldPointValue second = mGoldLineEntity.getSecondValue();
//
//					boolean isClearLastPosition = true ;
//					
//					if (mLastMotionX <= event.getX() && mLastMotionY <= event.getY()) { // 手指方向 向右 往左滑动
//						int firstXPosition = getIntersectionPosition(firstAxisXposition
//								+ xMoveOffset);
//						
//						int secondXPosition = getIntersectionPosition(secondAxisXposition
//								+ xMoveOffset);
//						
//						
//						double firstYValue = (1f - (firstAxisYposition + yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						first.setAxisYvalue(firstYValue);
//						first.setPosition(firstXPosition);
//
//						double secondYValue = (1f - (secondAxisYposition + yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						second.setAxisYvalue(secondYValue);
//						second.setPosition(secondXPosition);
//						mGoldLineEntity.setFirstValue(first);
//						mGoldLineEntity.setSecondValue(second);
//
//					} else if (mLastMotionX <= event.getX()
//							&& mLastMotionY >= event.getY()) {
//
//						int firstXPosition = getIntersectionPosition(firstAxisXposition + xMoveOffset);
//						
//						double firstYValue = (1f - (firstAxisYposition - yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						first.setAxisYvalue(firstYValue);
//						first.setPosition(firstXPosition);
//
//						int secondXPosition = getIntersectionPosition(secondAxisXposition + xMoveOffset);
//						double secondYValue = (1f - (secondAxisYposition - yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						second.setAxisYvalue(secondYValue);
//						second.setPosition(secondXPosition);
//						mGoldLineEntity.setFirstValue(first);
//						mGoldLineEntity.setSecondValue(second);
//
//					} else if (mLastMotionX >= event.getX() && mLastMotionY <= event.getY()) {
//
//						int firstXPosition = getIntersectionPosition(firstAxisXposition
//								- xMoveOffset);
//						double firstYValue = (1f - (firstAxisYposition + yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						
//						first.setAxisYvalue(firstYValue);
//						first.setPosition(firstXPosition);
//
//						int secondXPosition = getIntersectionPosition(secondAxisXposition
//								- xMoveOffset);
//						double secondYValue = (1f - (secondAxisYposition + yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						second.setAxisYvalue(secondYValue);
//						second.setPosition(secondXPosition);
//						mGoldLineEntity.setFirstValue(first);
//						mGoldLineEntity.setSecondValue(second);
//
//					} else if (mLastMotionX >= event.getX()
//							&& mLastMotionY >= event.getY()) {
//						int firstXPosition = getIntersectionPosition(firstAxisXposition
//								- xMoveOffset);
//						double firstYValue = (1f - (firstAxisYposition - yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						first.setAxisYvalue(firstYValue);
//						first.setPosition(firstXPosition);
//						int secondXPosition = getIntersectionPosition(secondAxisXposition
//								- xMoveOffset);
//						double secondYValue = (1f - (secondAxisYposition - yMoveOffset)
//								/ (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight))
//								* (maxValue - minValue) + minValue;
//						second.setAxisYvalue(secondYValue);
//						second.setPosition(secondXPosition);
//						mGoldLineEntity.setFirstValue(first);
//						mGoldLineEntity.setSecondValue(second);
//
//					}
//
//					xMoveOffset = 0f;
//					yMoveOffset = 0f;
//					
//					if(isClearLastPosition) {
//						mLastMotionX =  event.getX();
//						mLastMotionY =  event.getY();
//						super.invalidate();
//					}
//					// redraw
//				} 
				
				
				else {
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
							// redraw
							super.invalidate();
							// do notify
							notifyEventAll(this);
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
						// redraw
						super.invalidate();
						// do notify
						notifyEventAll(this);
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

	/**
	 * 通过当前触摸点 获取当前对象
	 */

	protected int getIntersectionPosition(float clickPostX) {
		int currentPosition = -1;

		List<KLineEntity> stickData = mUnionCandleStickChart.getStickData();

		if (stickData == null || stickData.size() < 0)
			return currentPosition;

		float mXisaWidth = getDataQuadrantEndX() - clickPostX;

		currentPosition = (int) (mXisaWidth / (mUnionCandleStickChart
				.getStickWidth() + stickSpacing))
				+ mUnionCandleStickChart.getDrawOffset();

		if (currentPosition >= stickData.size())
			currentPosition = stickData.size() - 1;
		if (currentPosition < 0)
			currentPosition = 0;

		return currentPosition;

	}

	/**
	 * 通过当前触摸点 获取当前对象
	 */

	protected KLineEntity getIntersectionEntity(float xPosition) {
		List<KLineEntity> stickData = mUnionCandleStickChart.getStickData();

		if (stickData == null || stickData.size() < 0)
			return null;
		if (xPosition <= 0) {
			return null;
		}
		int currentPosition = -1;
		float mXisaWidth = getDataQuadrantEndX() - xPosition;
		currentPosition = (int) (mXisaWidth / (mUnionCandleStickChart
				.getStickWidth() + stickSpacing))
				+ mUnionCandleStickChart.getDrawOffset();
		if (currentPosition >= stickData.size())
			currentPosition = stickData.size() - 1;
		if (currentPosition < 0)
			currentPosition = 0;
		return stickData.get(currentPosition);
	}

	protected void drawLine(Canvas canvas) {

		if (mGoldLineEntity == null)
			return;

		Paint mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);
		mPaint.setStrokeWidth(4f);

		Paint mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.YELLOW);
		mCirclePaint.setPathEffect(getDashEffect());
		int drawStickNumber = (int) (getDataQuadrantWidth() / (mUnionCandleStickChart
				.getStickWidth() + 1));

		if (mGoldLineEntity.getSecondValue() == null) {
			float lineHLength = getDataQuadrantWidth()
					- axisYTitleQuadrantWidth;

			GoldPointValue first = mGoldLineEntity.getFirstValue();
			float yPosition = (float) ((1f - (first.getAxisYvalue() - minValue)
					/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
			float xPosition = (float) ((1f - (first.getPosition() - mUnionCandleStickChart
					.getDrawOffset()) / (0f + drawStickNumber)) * getDataQuadrantWidth());

			canvas.drawLine(borderWidth, yPosition, borderWidth
					+ axisYTitleQuadrantWidth + lineHLength, yPosition, mPaint);
			canvas.drawCircle(xPosition, yPosition, 15f, mCirclePaint);
		} else {
			GoldPointValue first = mGoldLineEntity.getFirstValue();
			float y1 = (float) ((1f - (first.getAxisYvalue() - minValue)
					/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
			float x1 = (float) ((1f - (first.getPosition() - mUnionCandleStickChart
					.getDrawOffset()) / (0f + drawStickNumber)) * getDataQuadrantWidth());

			GoldPointValue second = mGoldLineEntity.getSecondValue();

			float y2 = (float) ((1f - (second.getAxisYvalue() - minValue)
					/ (maxValue - minValue)) * (super.getHeight() * 2 / 3 - axisXTitleQuadrantHeight));
			float x2 = (float) ((1f - (second.getPosition() - mUnionCandleStickChart
					.getDrawOffset()) / (0f + drawStickNumber)) * getDataQuadrantWidth());

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
				canvas.drawCircle(x1, y1, 15f, mCirclePaint);
			}

			if (x2 > 0 && x2 < getDataQuadrantWidth() && y2 > 0
					&& y2 < (getHeight() * 2 / 3 - axisXTitleQuadrantHeight)) {
				canvas.drawCircle(x2, y2, 15f, mCirclePaint);
			}
		}
	}

	public UnionCandleStickChart getmUnionCandleStickChart() {
		return mUnionCandleStickChart;
	}

	public void setmUnionCandleStickChart(
			UnionCandleStickChart mUnionCandleStickChart) {
		this.mUnionCandleStickChart = mUnionCandleStickChart;
	}

	public GLineToolsEntity getmGoldLineEntity() {
		return mGoldLineEntity;
	}

	public void setmGoldLineEntity(GLineToolsEntity mGoldLineEntity) {
		this.mGoldLineEntity = mGoldLineEntity;
	}

	public boolean isShowHorLine() {
		return showHorLine;
	}

	public void setShowHorLine(boolean showHorLine) {
		this.showHorLine = showHorLine;
	}
	
}
