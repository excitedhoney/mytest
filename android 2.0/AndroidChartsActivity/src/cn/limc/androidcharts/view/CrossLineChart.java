package cn.limc.androidcharts.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import cn.limc.KLineEntity;
import cn.limc.androidcharts.entity.DateValueEntity;
import cn.limc.androidcharts.entity.LineEntity;

public class CrossLineChart extends UnionGridChart {
	
	public static final String NORMAL_CROSS_LINE_TYPE = "NORMAL_CROSS_LINE_TYPE" ;
	
	public static final String HIGH_CROSS_LINE_TYPE = "HIGH_CROSS_LINE_TYPE" ;
	
	public static final String LOW_CROSS_LINE_TYPE = "LOW_CROSS_LINE_TYPE" ;
	
	private boolean isShowBorderText = false ;
	
	private String glineType = HIGH_CROSS_LINE_TYPE ;
	
	private float lastPositionX ;

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
	 * 绘制柱条用的数据
	 * </p>
	 */
	protected List<KLineEntity> stickData;


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
	public CrossLineChart(Context context) {
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
	public CrossLineChart(Context context, AttributeSet attrs, int defStyle) {
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
	public CrossLineChart(Context context, AttributeSet attrs) {
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
		drawVerticalLine(canvas);
		if(isShowBorderText)
		    drawAlphaTextBox(canvas);
	}
	
	public void calcRange(){
		if(stickData == null || stickData.isEmpty())
			return ;
		
		int drawStickNumber = (int )(getDataQuadrantWidth() / (stickWidth + 1 )) + 1 ;
		
		for (int i = drawOffset; i < drawStickNumber + drawOffset; i++) {
			if(i >= stickData.size()){
				break ;
			}
			if(i == drawOffset){
				minValue = stickData.get(i).getLow() ;
				maxValue = stickData.get(i).getHigh() ;
			}else{
				if(maxValue < stickData.get(i).getHigh()){
					maxValue = stickData.get(i).getHigh() ;
				}
				
				if(minValue > stickData.get(i).getLow()){
					minValue = stickData.get(i).getLow() ;
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
//		if (null != entity) {
//			// add
//			this.stickData.add(entity);
//
//			if (this.maxValue < entity.getHigh()) {
//				this.maxValue = ((int) entity.getHigh()) / 100 * 100;
//			}
//
//			if (this.maxValue < entity.getLow()) {
//				this.minValue = ((int) entity.getLow()) / 100 * 100;
//			}
//
//			if (stickData.size() > maxSticksNum) {
//				maxSticksNum = maxSticksNum + 1;
//			}
//		}
	}

	private final int NONE = 0;
	private final int ZOOM = 1;
	private final int DOWN = 2;
	private final int MOVE = 3;

	private float olddistance = 0f;
	private float newdistance = 0f;

	private int touchMode;

	private int mLastMotionX, mLastMotionY;
	// 移动的阈值
	private static final int TOUCH_SLOP = 20;
	
	private long downTime = 0 ; 
	
	private boolean isNear = false ;
	
	/*
	 * (non-Javadoc)
	 * <p>图表点击时调用<p>
	 * 
	 * @param event
	 * 
	 * @see android.view.View#onTouchEvent(MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			touchMode = DOWN;
			mLastMotionX = x;
			mLastMotionY = y;
			isShowBorderText = true ;
			downTime= System.currentTimeMillis() ;
			if(lastPositionX == 0){
				lastPositionX = event.getX() ;
				isNear = true ;
			}else{
				if(Math.abs(lastPositionX - event.getX())  < 40){
					isNear = true ;
				}else{
					isNear = false ;
				}
			}
			if(isNear == true){
				return super.onTouchEvent(event);
			}else{
				break ;
			}
		case MotionEvent.ACTION_UP:
			if(touchMode == DOWN){
				if((System.currentTimeMillis() - downTime) < 1000){
					performClick();
				}
			}
			lastPositionX = event.getX() ;
			break ;
		case MotionEvent.ACTION_POINTER_UP:
			touchMode = NONE;
			break ;
		case MotionEvent.ACTION_POINTER_DOWN:
			olddistance = calcDistance(event);
			break ;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(mLastMotionX - (int) event.getX()) > TOUCH_SLOP
					|| Math.abs(mLastMotionY - (int) event.getY()) > TOUCH_SLOP) {
				// 移动超过阈值，则表示移动了
				touchMode = MOVE ;
			}
			return super.onTouchEvent(event);
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

	

	protected void drawAlphaTextBox(Canvas canvas) {

		Paint mPaint = new Paint();
		mPaint.setColor(getCrossLinesColor());

		PointF ptStart;
		PointF ptEnd;
		
		
		float fontSize = 18f ;

		if (getClickPostX() < getDataQuadrantWidth() / 2f) {
			ptStart = new PointF(getDataQuadrantStartX(),getDataQuadrantStartY());

			ptEnd = new PointF(getDataQuadrantStartX() +  5 * fontSize + 5f,
					getDataQuadrantStartY() + fontSize * 5 + 5f);
		} else {
			ptStart = new PointF(getDataQuadrantEndX() - (5 * fontSize + 5f),getDataQuadrantStartY());

			ptEnd = new PointF(getDataQuadrantEndX(), getDataQuadrantStartY()+ fontSize * 5 + 5f);
		}

		Paint mPaintBox = new Paint();
		mPaintBox.setColor(Color.WHITE);
		mPaintBox.setAlpha(80);
		mPaintBox.setStyle(Style.FILL);

		Paint mPaintBoxLine = new Paint();
		mPaintBoxLine.setColor(getCrossLinesColor());
		mPaintBoxLine.setAntiAlias(true);
		mPaintBoxLine.setTextSize(fontSize);

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
			canvas.drawText(entity.getDate(), ptStart.x, ptStart.y + 1f
					+  (fontSize + 1)  , mPaintBoxLine);
			canvas.drawText("今开" + entity.getOpen(), ptStart.x, ptStart.y
					+  (fontSize + 1)  * 2, mPaintBoxLine);
			canvas.drawText("最高" + entity.getHigh(), ptStart.x, ptStart.y
					+  (fontSize + 1)  * 3, mPaintBoxLine);
			canvas.drawText("昨收" + entity.getZuoShou(), ptStart.x, ptStart.y
					+  (fontSize + 1)  * 4, mPaintBoxLine);
			canvas.drawText("最低" + entity.getLow(), ptStart.x, ptStart.y
					+  (fontSize + 1) * 5, mPaintBoxLine);
		}
	}
	
	private float stickWidth ;
	
	private int drawOffset ;
	
	public float getStickWidth() {
		return stickWidth;
	}

	public void setStickWidth(float stickWidth) {
		this.stickWidth = stickWidth;
	}

	public int getDrawOffset() {
		return drawOffset;
	}

	public void setDrawOffset(int drawOffset) {
		this.drawOffset = drawOffset;
	}

	/**
	 * 通过当前触摸点 获取当前对象
	 */

	protected KLineEntity getIntersectionEntity() {
		if(stickData == null || stickData.size() < 0)
			return null ;
		if (getClickPostX() <= 0) {
			return null;
		}
		int currentPosition = -1;
		float mXisaWidth = getDataQuadrantEndX() - getClickPostX() ;
		currentPosition = (int) (mXisaWidth / (stickWidth + stickSpacing)) + drawOffset;
		if (currentPosition >= stickData.size())
			currentPosition = stickData.size() - 1;
		if (currentPosition < 0)
			currentPosition = 0;
		return stickData.get(currentPosition);
	}
	
	
	
	/**
	 * 通过当前触摸点 获取当前对象
	 */
	

	protected int getIntersectionPosition() {
		int currentPosition = -1;

		if (stickData == null || stickData.size() < 0)
			return currentPosition;

		if (getClickPostX() <= 0)
			return currentPosition;

		float mXisaWidth = getDataQuadrantEndX() - getClickPostX() ;
		currentPosition = (int) (mXisaWidth / (stickWidth + stickSpacing)) + drawOffset;

		if (currentPosition >= stickData.size())
			currentPosition = stickData.size() - 1;
		if (currentPosition < 0)
			currentPosition = 0;
		
		return currentPosition;
		
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


	public String getDateRanage() {
		if(stickData != null && stickData.size() > 0){
			return stickData.get(0).getDate() + "~" +stickData.get(stickData.size() -1).getDate() ;
		}
		return null;
	}
	
	protected void drawHorizontalLine(Canvas canvas) {
		
		float  axisYposition = getClickPostY() ;
		
		if (getClickPostY() <= 0) {
			axisYposition = super.getHeight() / 2 ;
		}


		Paint mPaint = new Paint();
		mPaint.setStrokeWidth(2f);
		mPaint.setColor(getCrossLinesColor());
		
		KLineEntity entity = getIntersectionEntity() ;

		float lineHLength = getDataQuadrantWidth();
		
		if(getClickPostY() <= 0){
			canvas.drawLine(getDataQuadrantStartX() , axisYposition,
					getDataQuadrantStartX() + lineHLength,
					axisYposition, mPaint);
		}else{
			if(LOW_CROSS_LINE_TYPE.equalsIgnoreCase(glineType)){
				float xPosition = (float)(( 1f - (entity.getLow() - minValue)/(maxValue - minValue)) * (super.getHeight() * 2 / 3 - getAxisXTitleQuadrantHeight())) ;
				canvas.drawLine(borderWidth , xPosition,
						borderWidth + axisYTitleQuadrantWidth + lineHLength,
						xPosition, mPaint);
			}else if(HIGH_CROSS_LINE_TYPE.equalsIgnoreCase(glineType)){
				float xPosition = (float)(( 1f - (entity.getHigh() - minValue)/(maxValue - minValue)) * (super.getHeight() * 2 / 3 - getAxisXTitleQuadrantHeight())) ;
				canvas.drawLine(borderWidth , xPosition,
						borderWidth + axisYTitleQuadrantWidth + lineHLength,
						xPosition, mPaint);
			}else{
				canvas.drawLine(borderWidth , axisYposition,
						borderWidth + axisYTitleQuadrantWidth + lineHLength,
						axisYposition, mPaint);
			}
		}
	}
	

	/**
	 * <p>
	 * 在图表被点击后绘制十字线
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawVerticalLine(Canvas canvas) {
		
		float  axisXposition = getClickPostX() ;
		
		if (getClickPostX() <= 0) {
			axisXposition = super.getWidth() / 2 ;
		}

		Paint mPaint = new Paint();
		mPaint.setStrokeWidth(2f);
		mPaint.setColor(getCrossLinesColor());

		float lineVLength = super.getHeight();
		canvas.drawLine(axisXposition, borderWidth,
				axisXposition, lineVLength, mPaint);
	}

	public String getGlineType() {
		return glineType;
	}

	public void setGlineType(String glineType) {
		this.glineType = glineType;
	}

	public boolean isNear() {
		return isNear;
	}

	public void setNear(boolean isNear) {
		this.isNear = isNear;
	}

}
