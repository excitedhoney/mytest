package cn.precious.metal.view.view;

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
import cn.precious.metal.view.event.ITouchEventNotify;
import cn.precious.metal.view.event.ITouchEventResponse;

public class IntervalGridView extends BaseChart implements ITouchEventNotify,
		ITouchEventResponse {

	public static final int AXIS_X_POSITION_BOTTOM = 1 << 0;
	@Deprecated
	public static final int AXIS_X_POSITION_TOP = 1 << 1;
	public static final int AXIS_Y_POSITION_LEFT = 1 << 2;
	public static final int AXIS_Y_POSITION_RIGHT = 1 << 3;

	/**
	 * <p>
	 * default background color
	 * </p>
	 * <p>
	 * 鑳屾櫙銇壊銇儑銉曘偐銉儓鍊� *
	 * </p>
	 * <p>
	 * 榛樿鑳屾櫙鑹� *
	 * </p>
	 */
	public static final int DEFAULT_BACKGROUND_COLOR = Color.BLACK;

	/**
	 * <p>
	 * default color of X axis
	 * </p>
	 * <p>
	 * X杌搞伄鑹层伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 榛樿鍧愭爣杞碭鐨勬樉绀洪鑹� *
	 * </p>
	 */
	public static final int DEFAULT_AXIS_X_COLOR = Color.GRAY;

	/**
	 * <p>
	 * default color of Y axis
	 * </p>
	 * <p>
	 * Y杌搞伄鑹层伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 榛樿鍧愭爣杞碮鐨勬樉绀洪鑹� *
	 * </p>
	 */
	public static final int DEFAULT_AXIS_Y_COLOR = Color.GRAY;
	public static final float DEFAULT_AXIS_WIDTH = 1;

	public static final int DEFAULT_AXIS_X_POSITION = AXIS_X_POSITION_BOTTOM;

	public static final int DEFAULT_AXIS_Y_POSITION = AXIS_Y_POSITION_RIGHT;

	/**
	 * <p>
	 * default color of grid鈥榮 longitude line
	 * </p>
	 * <p>
	 * 绲岀窔銇壊銇儑銉曘偐銉儓鍊� *
	 * </p>
	 * <p>
	 * 榛樿缃戞牸缁忕嚎鐨勬樉绀洪鑹� *
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_COLOR = Color.GRAY;

	/**
	 * <p>
	 * default color of grid鈥榮 latitude line
	 * </p>
	 * <p>
	 * 绶窔銇壊銇儑銉曘偐銉儓鍊� *
	 * </p>
	 * <p>
	 * 榛樿缃戞牸绾嚎鐨勬樉绀洪鑹� *
	 * </p>
	 */
	public static final int DEFAULT_LAITUDE_COLOR = Color.GRAY;

	/**
	 * <p>
	 * default margin of the axis to the left border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞宸︽灎绶氥伄璺濋洟銇儑銉曘偐銉儓鍊� *
	 * </p>
	 * <p>
	 * 榛樿杞寸嚎宸﹁竟璺� *
	 * </p>
	 */
	@Deprecated
	public static final float DEFAULT_AXIS_MARGIN_LEFT = 42f;
	public static final float DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH = 16f;

	/**
	 * <p>
	 * default margin of the axis to the bottom border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞涓嬫灎绶氥伄璺濋洟銇儑銉曘偐銉儓鍊� *
	 * </p>
	 * <p>
	 * 榛樿杞寸嚎涓嬭竟璺� *
	 * </p>
	 */
	@Deprecated
	public static final float DEFAULT_AXIS_MARGIN_BOTTOM = 16f;
	public static final float DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT = 16f;

	/**
	 * <p>
	 * default margin of the axis to the top border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞涓婃灎绶氥伄璺濋洟銇儑銉曘偐銉儓鍊� *
	 * </p>
	 * <p>
	 * 榛樿杞寸嚎涓婅竟璺� *
	 * </p>
	 */
	@Deprecated
	public static final float DEFAULT_AXIS_MARGIN_TOP = 5f;
	public static final float DEFAULT_DATA_QUADRANT_PADDING_TOP = 5f;
	public static final float DEFAULT_DATA_QUADRANT_PADDING_BOTTOM = 5f;

	/**
	 * <p>
	 * default margin of the axis to the right border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞鍙虫灎绶氥伄璺濋洟銇儑銉曘偐銉儓鍊� *
	 * </p>
	 * <p>
	 * 杞寸嚎鍙宠竟璺� *
	 * </p>
	 */
	@Deprecated
	public static final float DEFAULT_AXIS_MARGIN_RIGHT = 5f;
	public static final float DEFAULT_DATA_QUADRANT_PADDING_LEFT = 5f;
	public static final float DEFAULT_DATA_QUADRANT_PADDING_RIGHT = 5f;

	/**
	 * <p>
	 * default numbers of grid鈥榮 latitude line
	 * </p>
	 * <p>
	 * 绶窔銇暟閲忋伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 缃戞牸绾嚎鐨勬暟閲� *
	 * </p>
	 */
	public static final int DEFAULT_LATITUDE_NUM = 4;

	/**
	 * <p>
	 * default numbers of grid鈥榮 longitude line
	 * </p>
	 * <p>
	 * 绲岀窔銇暟閲忋伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 缃戞牸缁忕嚎鐨勬暟閲� *
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_NUM = 3;

	/**
	 * <p>
	 * Should display longitude line?
	 * </p>
	 * <p>
	 * 绲岀窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 榛樿缁忕嚎鏄惁鏄剧ず
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LONGITUDE = Boolean.TRUE;

	/**
	 * <p>
	 * Should display longitude as dashed line?
	 * </p>
	 * <p>
	 * 绲岀窔銈掔偣绶氥伀銇欍倠銇�
	 * </p>
	 * <p>
	 * 榛樿缁忕嚎鏄惁鏄剧ず涓鸿櫄绾� *
	 * </p>
	 */
	public static final boolean DEFAULT_DASH_LONGITUDE = Boolean.FALSE;

	/**
	 * <p>
	 * Should display longitude line?
	 * </p>
	 * <p>
	 * 绶窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 绾嚎鏄惁鏄剧ず
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LATITUDE = Boolean.TRUE;

	/**
	 * <p>
	 * Should display latitude as dashed line?
	 * </p>
	 * <p>
	 * 绶窔銈掔偣绶氥伀銇欍倠銇�
	 * </p>
	 * <p>
	 * 绾嚎鏄惁鏄剧ず涓鸿櫄绾� *
	 * </p>
	 */
	public static final boolean DEFAULT_DASH_LATITUDE = Boolean.TRUE;

	/**
	 * <p>
	 * Should display the degrees in X axis?
	 * </p>
	 * <p>
	 * X杌搞伄銈裤偆銉堛儷銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * X杞翠笂鐨勬爣棰樻槸鍚︽樉绀� *
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LONGITUDE_TITLE = Boolean.TRUE;

	/**
	 * <p>
	 * Should display the degrees in Y axis?
	 * </p>
	 * <p>
	 * Y杌搞伄銈裤偆銉堛儷銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 榛樿Y杞翠笂鐨勬爣棰樻槸鍚︽樉绀� *
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_LATITUDE_TITLE = Boolean.TRUE;

	/**
	 * <p>
	 * Should display the border?
	 * </p>
	 * <p>
	 * 鏋犮倰琛ㄧず銇欍倠銇�
	 * </p>
	 * <p>
	 * 榛樿鎺т欢鏄惁鏄剧ず杈规
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_BORDER = Boolean.FALSE;

	/**
	 * <p>
	 * default color of text for the longitude銆�egrees display
	 * </p>
	 * <p>
	 * 绲屽害銇偪銈ゃ儓銉伄鑹层伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 榛樿缁忕嚎鍒诲害瀛椾綋棰滆壊
	 * </p>
	 */
	public static final int DEFAULT_BORDER_COLOR = Color.WHITE;

	public static final float DEFAULT_BORDER_WIDTH = 1f;

	/**
	 * <p>
	 * default color of text for the longitude銆�egrees display
	 * </p>
	 * <p>
	 * 绲屽害銇偪銈ゃ儓銉伄鑹层伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 缁忕嚎鍒诲害瀛椾綋棰滆壊
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_FONT_COLOR = Color.WHITE;

	/**
	 * <p>
	 * default font size of text for the longitude銆�egrees display
	 * </p>
	 * <p>
	 * 绲屽害銇偪銈ゃ儓銉伄銉曘偐銉炽儓銈点偆銈恒伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 缁忕嚎鍒诲害瀛椾綋澶у皬
	 * </p>
	 */
	public static final int DEFAULT_LONGITUDE_FONT_SIZE = 12;

	/**
	 * <p>
	 * default color of text for the latitude銆�egrees display
	 * </p>
	 * <p>
	 * 绶害銇偪銈ゃ儓銉伄鑹层伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 绾嚎鍒诲害瀛椾綋棰滆壊
	 * </p>
	 */
	public static final int DEFAULT_LATITUDE_FONT_COLOR = Color.GRAY;

	/**
	 * <p>
	 * default font size of text for the latitude銆�egrees display
	 * </p>
	 * <p>
	 * 绶害銇偪銈ゃ儓銉伄銉曘偐銉炽儓銈点偆銈恒伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 榛樿绾嚎鍒诲害瀛椾綋澶у皬
	 * </p>
	 */
	public static final int DEFAULT_LATITUDE_FONT_SIZE = 12;

	public static final int DEFAULT_CROSS_LINES_COLOR = Color.CYAN;
	public static final int DEFAULT_CROSS_LINES_FONT_COLOR = Color.CYAN;

	/**
	 * <p>
	 * default dashed line type
	 * </p>
	 * <p>
	 * 鐐圭窔銈裤偆銉椼伄銉囥儠銈┿儷銉堝�
	 * </p>
	 * <p>
	 * 榛樿铏氱嚎鏁堟灉
	 * </p>
	 */
	public static final PathEffect DEFAULT_DASH_EFFECT = new DashPathEffect(
			new float[] { 3, 3, 3, 3 }, 1);

	/**
	 * <p>
	 * Should display the Y cross line if grid is touched?
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堛亴銇傘倠鍫村悎銆佸崄瀛楃窔銇瀭鐩寸窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 榛樿鍦ㄦ帶浠惰鐐瑰嚮鏃讹紝鏄剧ず鍗佸瓧绔栫嚎绾� *
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_CROSS_X_ON_TOUCH = true;

	/**
	 * <p>
	 * Should display the Y cross line if grid is touched?
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堛亴銇傘倠鍫村悎銆佸崄瀛楃窔銇按骞崇窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 榛樿鍦ㄦ帶浠惰鐐瑰嚮鏃讹紝鏄剧ず鍗佸瓧妯嚎绾� *
	 * </p>
	 */
	public static final boolean DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH = true;

	/**
	 * <p>
	 * Color of X axis
	 * </p>
	 * <p>
	 * X杌搞伄鑹� *
	 * </p>
	 * <p>
	 * 鍧愭爣杞碭鐨勬樉绀洪鑹� *
	 * </p>
	 */
	private int axisXColor = DEFAULT_AXIS_X_COLOR;

	/**
	 * <p>
	 * Color of Y axis
	 * </p>
	 * <p>
	 * Y杌搞伄鑹� *
	 * </p>
	 * <p>
	 * 鍧愭爣杞碮鐨勬樉绀洪鑹� *
	 * </p>
	 */
	private int axisYColor = DEFAULT_AXIS_Y_COLOR;

	// private float axisWidth = DEFAULT_AXIS_WIDTH;

	protected int axisXPosition = DEFAULT_AXIS_X_POSITION;

	protected int axisYPosition = DEFAULT_AXIS_Y_POSITION;

	/**
	 * <p>
	 * Color of grid鈥榮 longitude line
	 * </p>
	 * <p>
	 * 绲岀窔銇壊
	 * </p>
	 * <p>
	 * 缃戞牸缁忕嚎鐨勬樉绀洪鑹� *
	 * </p>
	 */
	private int longitudeColor = DEFAULT_LONGITUDE_COLOR;

	/**
	 * <p>
	 * Color of grid鈥榮 latitude line
	 * </p>
	 * <p>
	 * 绶窔銇壊
	 * </p>
	 * <p>
	 * 缃戞牸绾嚎鐨勬樉绀洪鑹� *
	 * </p>
	 */
	private int latitudeColor = DEFAULT_LAITUDE_COLOR;

	/**
	 * <p>
	 * Margin of the axis to the left border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞宸︽灎绶氥伄璺濋洟
	 * </p>
	 * <p>
	 * 杞寸嚎宸﹁竟璺� *
	 * </p>
	 */
	protected float axisYTitleQuadrantWidth = DEFAULT_AXIS_Y_TITLE_QUADRANT_WIDTH;

	/**
	 * <p>
	 * Margin of the axis to the bottom border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞涓嬫灎绶氥伄璺濋洟
	 * </p>
	 * <p>
	 * 杞寸嚎涓嬭竟璺� *
	 * </p>
	 */
	protected float axisXTitleQuadrantHeight = DEFAULT_AXIS_X_TITLE_QUADRANT_HEIGHT;

	/**
	 * <p>
	 * Margin of the axis to the top border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞涓婃灎绶氥伄璺濋洟
	 * </p>
	 * <p>
	 * 杞寸嚎涓婅竟璺� *
	 * </p>
	 */
	protected float dataQuadrantPaddingTop = DEFAULT_DATA_QUADRANT_PADDING_TOP;

	/**
	 * <p>
	 * Margin of the axis to the right border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞鍙虫灎绶氥伄璺濋洟
	 * </p>
	 * <p>
	 * 杞寸嚎鍙宠竟璺� *
	 * </p>
	 */
	protected float dataQuadrantPaddingLeft = DEFAULT_DATA_QUADRANT_PADDING_LEFT;
	protected float dataQuadrantPaddingBottom = DEFAULT_DATA_QUADRANT_PADDING_BOTTOM;

	/**
	 * <p>
	 * Margin of the axis to the right border
	 * </p>
	 * <p>
	 * 杞寸窔銈堛倞鍙虫灎绶氥伄璺濋洟
	 * </p>
	 * <p>
	 * 杞寸嚎鍙宠竟璺� *
	 * </p>
	 */
	protected float dataQuadrantPaddingRight = DEFAULT_DATA_QUADRANT_PADDING_RIGHT;

	/**
	 * <p>
	 * Should display the degrees in X axis?
	 * </p>
	 * <p>
	 * X杌搞伄銈裤偆銉堛儷銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * X杞翠笂鐨勬爣棰樻槸鍚︽樉绀� *
	 * </p>
	 */
	private boolean displayLongitudeTitle = DEFAULT_DISPLAY_LONGITUDE_TITLE;

	/**
	 * <p>
	 * Should display the degrees in Y axis?
	 * </p>
	 * <p>
	 * Y杌搞伄銈裤偆銉堛儷銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * Y杞翠笂鐨勬爣棰樻槸鍚︽樉绀� *
	 * </p>
	 */
	private boolean displayLatitudeTitle = DEFAULT_DISPLAY_LATITUDE_TITLE;

	/**
	 * <p>
	 * Numbers of grid鈥榮 latitude line
	 * </p>
	 * <p>
	 * 绶窔銇暟閲� *
	 * </p>
	 * <p>
	 * 缃戞牸绾嚎鐨勬暟閲� *
	 * </p>
	 */
	protected int latitudeNum = DEFAULT_LATITUDE_NUM;

	/**
	 * <p>
	 * Numbers of grid鈥榮 longitude line
	 * </p>
	 * <p>
	 * 绲岀窔銇暟閲� *
	 * </p>
	 * <p>
	 * 缃戞牸缁忕嚎鐨勬暟閲� *
	 * </p>
	 */
	protected int longitudeNum = DEFAULT_LONGITUDE_NUM;

	/**
	 * <p>
	 * Should display longitude line?
	 * </p>
	 * <p>
	 * 绲岀窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 缁忕嚎鏄惁鏄剧ず
	 * </p>
	 */
	private boolean displayLongitude = DEFAULT_DISPLAY_LONGITUDE;

	/**
	 * <p>
	 * Should display longitude as dashed line?
	 * </p>
	 * <p>
	 * 绲岀窔銈掔偣绶氥伀銇欍倠銇�
	 * </p>
	 * <p>
	 * 缁忕嚎鏄惁鏄剧ず涓鸿櫄绾� *
	 * </p>
	 */
	private boolean dashLongitude = DEFAULT_DASH_LONGITUDE;

	/**
	 * <p>
	 * Should display longitude line?
	 * </p>
	 * <p>
	 * 绶窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 绾嚎鏄惁鏄剧ず
	 * </p>
	 */
	private boolean displayLatitude = DEFAULT_DISPLAY_LATITUDE;

	/**
	 * <p>
	 * Should display latitude as dashed line?
	 * </p>
	 * <p>
	 * 绶窔銈掔偣绶氥伀銇欍倠銇�
	 * </p>
	 * <p>
	 * 绾嚎鏄惁鏄剧ず涓鸿櫄绾� *
	 * </p>
	 */
	private boolean dashLatitude = DEFAULT_DASH_LATITUDE;

	/**
	 * <p>
	 * dashed line type
	 * </p>
	 * <p>
	 * 鐐圭窔銈裤偆銉�
	 * </p>
	 * <p>
	 * 铏氱嚎鏁堟灉
	 * </p>
	 */
	private PathEffect dashEffect = DEFAULT_DASH_EFFECT;

	/**
	 * <p>
	 * Should display the border?
	 * </p>
	 * <p>
	 * 鏋犮倰琛ㄧず銇欍倠銇�
	 * </p>
	 * <p>
	 * 鎺т欢鏄惁鏄剧ず杈规
	 * </p>
	 */
	private boolean displayBorder = DEFAULT_DISPLAY_BORDER;

	/**
	 * <p>
	 * Color of grid鈥榮 border line
	 * </p>
	 * <p>
	 * 鏋犵窔銇壊
	 * </p>
	 * <p>
	 * 鍥捐竟妗嗙殑棰滆壊
	 * </p>
	 */
	private int borderColor = DEFAULT_BORDER_COLOR;

	protected float borderWidth = DEFAULT_BORDER_WIDTH;

	/**
	 * <p>
	 * Color of text for the longitude銆�egrees display
	 * </p>
	 * <p>
	 * 绲屽害銇偪銈ゃ儓銉伄鑹� *
	 * </p>
	 * <p>
	 * 缁忕嚎鍒诲害瀛椾綋棰滆壊
	 * </p>
	 */
	private int longitudeFontColor = DEFAULT_LONGITUDE_FONT_COLOR;

	/**
	 * <p>
	 * Font size of text for the longitude銆�egrees display
	 * </p>
	 * <p>
	 * 绲屽害銇偪銈ゃ儓銉伄銉曘偐銉炽儓銈点偆銈� *
	 * </p>
	 * <p>
	 * 缁忕嚎鍒诲害瀛椾綋澶у皬
	 * </p>
	 */
	private int longitudeFontSize = DEFAULT_LONGITUDE_FONT_SIZE;

	/**
	 * <p>
	 * Color of text for the latitude銆�egrees display
	 * </p>
	 * <p>
	 * 绶害銇偪銈ゃ儓銉伄鑹� *
	 * </p>
	 * <p>
	 * 绾嚎鍒诲害瀛椾綋棰滆壊
	 * </p>
	 */
	private int latitudeFontColor = DEFAULT_LATITUDE_FONT_COLOR;

	/**
	 * <p>
	 * Font size of text for the latitude銆�egrees display
	 * </p>
	 * <p>
	 * 绶害銇偪銈ゃ儓銉伄銉曘偐銉炽儓銈点偆銈� *
	 * </p>
	 * <p>
	 * 绾嚎鍒诲害瀛椾綋澶у皬
	 * </p>
	 */
	private int latitudeFontSize = DEFAULT_LATITUDE_FONT_SIZE;

	/**
	 * <p>
	 * Color of cross line inside grid when touched
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堣〃绀虹敤鍗佸瓧绶氥伄鑹� *
	 * </p>
	 * <p>
	 * 鍗佸瓧浜ゅ弶绾块鑹� *
	 * </p>
	 */
	private int crossLinesColor = DEFAULT_CROSS_LINES_COLOR;

	/**
	 * <p>
	 * Color of cross line degree text when touched
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堣〃绀虹敤鍗佸瓧绶氬害鏁版枃瀛椼伄鑹� *
	 * </p>
	 * <p>
	 * 鍗佸瓧浜ゅ弶绾垮潗鏍囪酱瀛椾綋棰滆壊
	 * </p>
	 */
	private int crossLinesFontColor = DEFAULT_CROSS_LINES_FONT_COLOR;

	/**
	 * <p>
	 * Titles Array for display of X axis
	 * </p>
	 * <p>
	 * X杌搞伄琛ㄧず鐢ㄣ偪銈ゃ儓銉厤鍒� *
	 * </p>
	 * <p>
	 * X杞存爣棰樻暟缁� *
	 * </p>
	 */
	private List<String> longitudeTitles;

	/**
	 * <p>
	 * Titles for display of Y axis
	 * </p>
	 * <p>
	 * Y杌搞伄琛ㄧず鐢ㄣ偪銈ゃ儓銉厤鍒� *
	 * </p>
	 * <p>
	 * Y杞存爣棰樻暟缁� *
	 * </p>
	 */
	private List<String> latitudeTitles;

	/**
	 * <p>
	 * Should display the Y cross line if grid is touched?
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堛亴銇傘倠鍫村悎銆佸崄瀛楃窔銇瀭鐩寸窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 鍦ㄦ帶浠惰鐐瑰嚮鏃讹紝鏄剧ず鍗佸瓧绔栫嚎绾� *
	 * </p>
	 */
	private boolean displayCrossXOnTouch = DEFAULT_DISPLAY_CROSS_X_ON_TOUCH;

	/**
	 * <p>
	 * Should display the Y cross line if grid is touched?
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堛亴銇傘倠鍫村悎銆佸崄瀛楃窔銇按骞崇窔銈掕〃绀恒仚銈嬨亱?
	 * </p>
	 * <p>
	 * 鍦ㄦ帶浠惰鐐瑰嚮鏃讹紝鏄剧ず鍗佸瓧妯嚎绾� *
	 * </p>
	 */
	private boolean displayCrossYOnTouch = DEFAULT_DISPLAY_CROSS_Y_ON_TOUCH;

	/**
	 * <p>
	 * Touched point inside of grid
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉� *
	 * </p>
	 * <p>
	 * 鍗曠偣瑙︽帶鐨勯�涓偣
	 * </p>
	 */
	private PointF touchPoint;

	/**
	 * <p>
	 * Touched point鈥檚 X value inside of grid
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堛伄X
	 * </p>
	 * <p>
	 * 鍗曠偣瑙︽帶鐨勯�涓偣鐨刋
	 * </p>
	 */
	private float clickPostX;

	/**
	 * <p>
	 * Touched point鈥檚 Y value inside of grid
	 * </p>
	 * <p>
	 * 銈裤儍銉併仐銇熴儩銈ゃ兂銉堛伄Y
	 * </p>
	 * <p>
	 * 鍗曠偣瑙︽帶鐨勯�涓偣鐨刌
	 * </p>
	 */
	private float clickPostY;

	/**
	 * <p>
	 * Event will notify objects' list
	 * </p>
	 * <p>
	 * 銈ゃ儥銉炽儓閫氱煡瀵捐薄銉偣銉� *
	 * </p>
	 * <p>
	 * 浜嬩欢閫氱煡瀵硅薄鍒楄〃
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
	public IntervalGridView(Context context) {
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
	public IntervalGridView(Context context, AttributeSet attrs, int defStyle) {
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
	public IntervalGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when is going to draw this chart<p>
	 * <p>銉併儯銉笺儓銈掓浉銇忓墠銆併儭銈姐儍銉夈倰鍛笺伓<p> <p>缁樺埗鍥捐〃鏃惰皟鐢�p>
	 * 
	 * @param canvas
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (this.displayBorder) {
			drawBorder(canvas);
		}

		if (displayLongitude || displayLongitudeTitle) {
			drawLongitudeLine(canvas);
			drawLongitudeTitle(canvas);
		}
		if (displayLatitude || displayLatitudeTitle) {
			drawLatitudeLine(canvas);
			drawLatitudeTitle(canvas);
		}

		if (displayCrossXOnTouch || displayCrossYOnTouch) {
			// drawWithFingerClick(canvas);
			drawHorizontalLine(canvas);
			drawVerticalLine(canvas);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when chart is touched<p> <p>銉併儯銉笺儓銈掋偪銉冦儊銇椼仧銈夈�銉°偨銉冦儔銈掑懠銇�p>
	 * <p>鍥捐〃鐐瑰嚮鏃惰皟鐢�p>
	 * 
	 * @param event
	 * 
	 * @see android.view.View#onTouchEvent(MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getX() < getDataQuadrantPaddingStartX()
				|| event.getX() > getDataQuadrantPaddingEndX()) {
			return false;
		}

		if (event.getY() < getDataQuadrantPaddingStartY()
				|| event.getY() > getDataQuadrantPaddingEndY()) {
			return false;
		}

		// touched points, if touch point is only one
		if (event.getPointerCount() == 1) {
			// 鑾峰彇鐐瑰嚮鍧愭爣

			clickPostX = event.getX();
			clickPostY = event.getY();

			PointF point = new PointF(clickPostX, clickPostY);
			touchPoint = point;

			// redraw
			super.invalidate();

			// do notify
			notifyEventAll(this);

		} else if (event.getPointerCount() == 2) {

		}

		return super.onTouchEvent(event);
	}

	/**
	 * <p>
	 * draw some text with border
	 * </p>
	 * <p>
	 * 鏂囧瓧銈掓浉銇忋�鏋犮亗銈� *
	 * </p>
	 * <p>
	 * 缁樺埗涓�鏂囨湰锛屽苟澧炲姞澶栨
	 * </p>
	 * 
	 * @param ptStart
	 *            <p>
	 *            start point
	 *            </p>
	 *            <p>
	 *            闁嬪銉濄偆銉炽儓
	 *            </p>
	 *            <p>
	 *            寮�鐐� *
	 *            </p>
	 * 
	 * @param ptEnd
	 *            <p>
	 *            end point
	 *            </p>
	 *            <p>
	 *            绲愭潫銉濄偆銉炽儓
	 *            </p>
	 *            <p>
	 *            缁撴潫鐐� *
	 *            </p>
	 * 
	 * @param content
	 *            <p>
	 *            text content
	 *            </p>
	 *            <p>
	 *            鏂囧瓧鍐呭
	 *            </p>
	 *            <p>
	 *            鏂囧瓧鍐呭
	 *            </p>
	 * 
	 * @param fontSize
	 *            <p>
	 *            font size
	 *            </p>
	 *            <p>
	 *            鏂囧瓧銉曘偐銉炽儓銈点偆銈� *
	 *            </p>
	 *            <p>
	 *            瀛椾綋澶у皬
	 *            </p>
	 * 
	 * @param canvas
	 */
	private void drawAlphaTextBox(PointF ptStart, PointF ptEnd, String content,
			int fontSize, Canvas canvas) {

		Paint mPaintBox = new Paint();
		mPaintBox.setColor(Color.WHITE);
		mPaintBox.setAlpha(80);
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

	protected float getDataQuadrantWidth() {
		return super.getWidth() - axisYTitleQuadrantWidth;
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

	protected float getDataQuadrantPaddingStartX() {
		return getDataQuadrantStartX() + dataQuadrantPaddingLeft;
	}

	protected float getDataQuadrantEndX() {
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			return super.getWidth() - borderWidth;
		} else {
			return super.getWidth() - borderWidth - axisYTitleQuadrantWidth;
		}
	}

	protected float getDataQuadrantPaddingEndX() {
		return getDataQuadrantEndX() - dataQuadrantPaddingRight;
	}

	protected float getDataQuadrantStartY() {
		return borderWidth;
	}

	protected float getDataQuadrantPaddingStartY() {
		return getDataQuadrantStartY() + dataQuadrantPaddingTop;
	}

	protected float getDataQuadrantEndY() {
		return super.getHeight() - borderWidth - axisXTitleQuadrantHeight;
	}

	protected float getDataQuadrantPaddingEndY() {
		return getDataQuadrantEndY() - dataQuadrantPaddingBottom;
	}

	protected float getDataQuadrantPaddingWidth() {
		return getDataQuadrantWidth() - dataQuadrantPaddingLeft
				- dataQuadrantPaddingRight;
	}

	protected float getDataQuadrantPaddingHeight() {
		return getDataQuadrantHeight() - dataQuadrantPaddingTop
				- dataQuadrantPaddingBottom;
	}

	/**
	 * <p>
	 * calculate degree title on X axis
	 * </p>
	 * <p>
	 * X杌搞伄鐩洓銈掕▓绠椼仚銈� *
	 * </p>
	 * <p>
	 * 璁＄畻X杞翠笂鏄剧ず鐨勫潗鏍囧�
	 * </p>
	 * 
	 * @param value
	 *            <p>
	 *            value for calculate
	 *            </p>
	 *            <p>
	 *            瑷堢畻鏈夌敤銉囥兗銈� *
	 *            </p>
	 *            <p>
	 *            璁＄畻鐢ㄦ暟鎹� *
	 *            </p>
	 * 
	 * @return String
	 *         <p>
	 *         degree
	 *         </p>
	 *         <p>
	 *         鐩洓
	 *         </p>
	 *         <p>
	 *         鍧愭爣鍊� *
	 *         </p>
	 */
	public String getAxisXGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- getDataQuadrantPaddingStartX();
		return String.valueOf(valueLength / this.getDataQuadrantPaddingWidth());
	}

	/**
	 * <p>
	 * calculate degree title on Y axis
	 * </p>
	 * <p>
	 * Y杌搞伄鐩洓銈掕▓绠椼仚銈� *
	 * </p>
	 * <p>
	 * 璁＄畻Y杞翠笂鏄剧ず鐨勫潗鏍囧�
	 * </p>
	 * 
	 * @param value
	 *            <p>
	 *            value for calculate
	 *            </p>
	 *            <p>
	 *            瑷堢畻鏈夌敤銉囥兗銈� *
	 *            </p>
	 *            <p>
	 *            璁＄畻鐢ㄦ暟鎹� *
	 *            </p>
	 * 
	 * @return String
	 *         <p>
	 *         degree
	 *         </p>
	 *         <p>
	 *         鐩洓
	 *         </p>
	 *         <p>
	 *         鍧愭爣鍊� *
	 *         </p>
	 */
	public String getAxisYGraduate(Object value) {
		float valueLength = ((Float) value).floatValue()
				- getDataQuadrantPaddingStartY();
		return String
				.valueOf(valueLength / this.getDataQuadrantPaddingHeight());
	}

	/**
	 * <p>
	 * draw cross line ,called when graph is touched
	 * </p>
	 * <p>
	 * 鍗佸瓧绶氥倰鏇搞亸銆併偘銉┿儣銈掋偪銉冦儊銇熴倝銆併儭銈姐兗銉夈倰鍛笺伋
	 * </p>
	 * <p>
	 * 鍦ㄥ浘琛ㄨ鐐瑰嚮鍚庣粯鍒跺崄瀛楃嚎
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawVerticalLine(Canvas canvas) {

		if (!displayLongitudeTitle) {
			return;
		}
		if (!displayCrossXOnTouch) {
			return;
		}
		if (clickPostX <= 0) {
			return;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(crossLinesColor);

		float lineVLength = getDataQuadrantHeight();

		// TODO calculate points to draw
		PointF boxVS = new PointF(clickPostX - longitudeFontSize * 5f / 2f,
				borderWidth + lineVLength);
		PointF boxVE = new PointF(clickPostX + longitudeFontSize * 5f / 2f,
				borderWidth + lineVLength + axisXTitleQuadrantHeight);

		// draw text
		drawAlphaTextBox(boxVS, boxVE, getAxisXGraduate(clickPostX),
				longitudeFontSize, canvas);

		canvas.drawLine(clickPostX, borderWidth, clickPostX, lineVLength,
				mPaint);
	}

	protected void drawHorizontalLine(Canvas canvas) {

		if (!displayLatitudeTitle) {
			return;
		}
		if (!displayCrossYOnTouch) {
			return;
		}
		if (clickPostY <= 0) {
			return;
		}

		Paint mPaint = new Paint();
		mPaint.setColor(crossLinesColor);

		float lineHLength = getDataQuadrantWidth();

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			PointF boxHS = new PointF(borderWidth, clickPostY
					- latitudeFontSize / 2f - 2);
			PointF boxHE = new PointF(borderWidth + axisYTitleQuadrantWidth,
					clickPostY + latitudeFontSize / 2f + 2);

			// draw text
			drawAlphaTextBox(boxHS, boxHE, getAxisYGraduate(clickPostY),
					latitudeFontSize, canvas);

			canvas.drawLine(borderWidth + axisYTitleQuadrantWidth, clickPostY,
					borderWidth + axisYTitleQuadrantWidth + lineHLength,
					clickPostY, mPaint);
		} else {
			PointF boxHS = new PointF(super.getWidth() - borderWidth
					- axisYTitleQuadrantWidth, clickPostY - latitudeFontSize
					/ 2f - 2);
			PointF boxHE = new PointF(super.getWidth() - borderWidth,
					clickPostY + latitudeFontSize / 2f + 2);

			// draw text
			drawAlphaTextBox(boxHS, boxHE, getAxisYGraduate(clickPostY),
					latitudeFontSize, canvas);

			canvas.drawLine(borderWidth, clickPostY, borderWidth + lineHLength,
					clickPostY, mPaint);
		}

	}

	/**
	 * <p>
	 * draw border
	 * </p>
	 * <p>
	 * 銈般儵銉椼伄銉溿兗銉�兗銈掓浉銇� *
	 * </p>
	 * <p>
	 * 缁樺埗杈规
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawBorder(Canvas canvas) {
		Paint mPaint = new Paint();
		mPaint.setColor(borderColor);
		mPaint.setStrokeWidth(borderWidth);
		mPaint.setStyle(Style.STROKE);
		// draw a rectangle
		canvas.drawRect(borderWidth / 2, borderWidth / 2, super.getWidth()
				- borderWidth / 2, super.getHeight() - borderWidth / 2, mPaint);
	}

	/**
	 * <p>
	 * draw X Axis
	 * </p>
	 * <p>
	 * X杌搞倰鏇搞亸
	 * </p>
	 * <p>
	 * 缁樺埗X杞� *
	 * </p>
	 * 
	 * @param canvas
	 */
	// protected void drawXAxis(Canvas canvas) {
	//
	// float length = super.getWidth();
	// float postY;
	// if (axisXPosition == AXIS_X_POSITION_BOTTOM) {
	// postY = super.getHeight() - axisXTitleQuadrantHeight - borderWidth
	// / 2;
	// } else {
	// postY = super.getHeight() - borderWidth / 2;
	// }
	//
	// Paint mPaint = new Paint();
	// mPaint.setColor(axisXColor);
	// mPaint.setStrokeWidth(axisWidth);
	//
	// canvas.drawLine(borderWidth, postY, length, postY, mPaint);
	//
	// }

	/**
	 * <p>
	 * draw Y Axis
	 * </p>
	 * <p>
	 * Y杌搞倰鏇搞亸
	 * </p>
	 * <p>
	 * 缁樺埗Y杞� *
	 * </p>
	 * 
	 * @param canvas
	 */
	// protected void drawYAxis(Canvas canvas) {
	//
	// float length = super.getHeight() - axisXTitleQuadrantHeight
	// - borderWidth;
	// float postX;
	// if (axisYPosition == AXIS_Y_POSITION_LEFT) {
	// postX = borderWidth + axisYTitleQuadrantWidth / 2;
	// } else {
	// postX = super.getWidth() - borderWidth - axisYTitleQuadrantWidth
	// / 2;
	// }
	//
	// Paint mPaint = new Paint();
	// mPaint.setColor(axisXColor);
	// mPaint.setStrokeWidth(axisWidth);
	//
	// canvas.drawLine(postX, borderWidth, postX, length, mPaint);
	// }

	/**
	 * <p>
	 * draw longitude lines
	 * </p>
	 * <p>
	 * 绲岀窔銈掓浉銇� *
	 * </p>
	 * <p>
	 * 缁樺埗缁忕嚎
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLongitudeLine(Canvas canvas) {
		if (!displayLongitude) {
			return;
		}

		if (null == longitudeTitles || longitudeTitles.isEmpty()) {
			int counts = getLongitudeNum();
			float length = getDataQuadrantHeight();

			Paint mPaintLine = new Paint();
			mPaintLine.setColor(longitudeColor);
			if (dashLongitude) {
				mPaintLine.setPathEffect(dashEffect);
			}
			if (counts > 1) {
				float postOffset = this.getDataQuadrantPaddingWidth()
						/ (counts - 1);

				float offset;
				if (axisYPosition == AXIS_Y_POSITION_LEFT) {
					offset = axisYTitleQuadrantWidth + dataQuadrantPaddingLeft;
				} else {
					offset = borderWidth + dataQuadrantPaddingLeft;
				}

				for (int i = 0; i < counts; i++) {
					canvas.drawLine(offset + i * postOffset, borderWidth,
							offset + i * postOffset, length, mPaintLine);
				}
			}
		} else {
			int counts = longitudeTitles.size();
			float length = getDataQuadrantHeight();

			Paint mPaintLine = new Paint();
			mPaintLine.setColor(longitudeColor);
			if (dashLongitude) {
				mPaintLine.setPathEffect(dashEffect);
			}
			if (counts > 1) {
				float postOffset = this.getDataQuadrantPaddingWidth()
						/ (counts - 1);

				float offset;
				if (axisYPosition == AXIS_Y_POSITION_LEFT) {
					offset = axisYTitleQuadrantWidth + dataQuadrantPaddingLeft;
				} else {
					offset = borderWidth + dataQuadrantPaddingLeft;
				}

				for (int i = 0; i < counts; i++) {
					canvas.drawLine(offset + i * postOffset, borderWidth,
							offset + i * postOffset, length, mPaintLine);
				}
			}
		}

	}

	/**
	 * <p>
	 * draw longitude lines
	 * </p>
	 * <p>
	 * 绲岀窔銈掓浉銇� *
	 * </p>
	 * <p>
	 * 缁樺埗缁忕嚎
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLongitudeTitle(Canvas canvas) {
		if (!displayLongitude) {
			return;
		}
		if (!displayLongitudeTitle) {
			return;
		}

		if (null == longitudeTitles) {
			return;
		}

		if (longitudeTitles.size() <= 1) {
			return;
		}

		Paint mPaintFont = new Paint();
		mPaintFont.setColor(longitudeFontColor);
		mPaintFont.setTextSize(longitudeFontSize);
		mPaintFont.setAntiAlias(true);

		// float postOffset = this.getDataQuadrantPaddingWidth()
		// / (longitudeTitles.size() - 1);
		//
		// float offset = borderWidth + dataQuadrantPaddingLeft;

		int counts = longitudeTitles.size();

		if (axisYPosition == AXIS_Y_POSITION_RIGHT) {// 缁樺埗Y杞村湪宸﹁竟鐨� if (counts
														// > 1) {
			float postOffset = this.getDataQuadrantWidth() / (counts - 1);

			float offset = getDataQuadrantEndX();

			for (int i = 0; i < counts; i++) {
				if (i == 0 || i == counts - 1)
					continue;
				canvas.drawText(longitudeTitles.get(i), offset - i * postOffset
						- (longitudeTitles.get(i).length()) * longitudeFontSize
						/ 4f, super.getHeight() - 2f, mPaintFont);
			}
		}

	}

	// for (int i = 0; i < longitudeTitles.size(); i++) {
	// if (0 == i) {
	// canvas.drawText(longitudeTitles.get(i), offset,
	// super.getHeight() - 2f, mPaintFont);
	// } else {
	// canvas.drawText(longitudeTitles.get(i), offset + i * postOffset
	// - (longitudeTitles.get(i).length()) * longitudeFontSize / 4f,
	// super.getHeight() - 2f, mPaintFont);
	// }
	//
	// }
	/**
	 * <p>
	 * draw latitude lines
	 * </p>
	 * <p>
	 * 绶窔銈掓浉銇� *
	 * </p>
	 * <p>
	 * 缁樺埗绾嚎
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLatitudeLine(Canvas canvas) {

		if (!displayLatitude) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}

		if (null == latitudeTitles) {
			return;
		}
		if (latitudeTitles.size() <= 3) {
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

		float postOffset = this.getDataQuadrantPaddingHeight()
				/ (latitudeTitles.size() - 3 + 0.5f);

		float offset = super.getHeight() - borderWidth
				- axisXTitleQuadrantHeight - dataQuadrantPaddingBottom;

		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			float startFrom = borderWidth + axisYTitleQuadrantWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				canvas.drawLine(startFrom, offset - i * postOffset, startFrom
						+ length, offset - i * postOffset, mPaintLine);

			}
		} else {
			float startFrom = borderWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {
				if (i == 0) {
					canvas.drawLine(startFrom, offset - i * postOffset,
							startFrom + length, offset - i * postOffset,
							mPaintLine);
				} else if (i == latitudeTitles.size() - 1) {
					canvas.drawLine(startFrom, getDataQuadrantStartY(),
							startFrom + length, getDataQuadrantStartY(),
							mPaintLine);
				} else {
					canvas.drawLine(startFrom, offset - (i - 1) * postOffset
							- postOffset / 4f, startFrom + length, offset
							- (i - 1) * postOffset - postOffset / 4f,
							mPaintLine);
				}
			}
		}
	}

	/**
	 * <p>
	 * draw latitude lines
	 * </p>
	 * <p>
	 * 绶窔銈掓浉銇� *
	 * </p>
	 * <p>
	 * 缁樺埗绾嚎
	 * </p>
	 * 
	 * @param canvas
	 */
	protected void drawLatitudeTitle(Canvas canvas) {
		if (null == latitudeTitles) {
			return;
		}
		if (!displayLatitudeTitle) {
			return;
		}
		if (latitudeTitles.size() <= 1) {
			return;
		}
		Paint mPaintFont = new Paint();
		mPaintFont.setColor(latitudeFontColor);
		mPaintFont.setTextSize(latitudeFontSize);
		mPaintFont.setAntiAlias(true);

		float postOffset = this.getDataQuadrantHeight()
				/ (latitudeTitles.size() - 3 + 0.5f);
		float offset = getDataQuadrantHeight();
		float startFrom = 0f;
		if (axisYPosition == AXIS_Y_POSITION_LEFT) {
			startFrom = getDataQuadrantStartX();
			for (int i = 0; i < latitudeTitles.size(); i++) {
				if (0 == i) {
					canvas.drawText(latitudeTitles.get(i), startFrom
							- mPaintFont.measureText(latitudeTitles.get(i)),
							super.getHeight() - this.axisXTitleQuadrantHeight
									- borderWidth - 2f, mPaintFont);
				} else if (i == latitudeTitles.size() - 1) {
					canvas.drawText(latitudeTitles.get(i), startFrom
							- mPaintFont.measureText(latitudeTitles.get(i)),
							offset - i * postOffset + latitudeFontSize,
							mPaintFont);
				} else {
					canvas.drawText(latitudeTitles.get(i), startFrom
							- mPaintFont.measureText(latitudeTitles.get(i)),
							offset - i * postOffset + latitudeFontSize / 2f,
							mPaintFont);
				}
			}

		} else {
			startFrom = super.getWidth() - axisYTitleQuadrantWidth
					+ borderWidth;
			for (int i = 0; i < latitudeTitles.size(); i++) {

				if (0 == i) {
					// canvas.drawText(latitudeTitles.get(i), startFrom,
					// super.getHeight() - this.axisXTitleQuadrantHeight
					// - borderWidth - 2f, mPaintFont);
				} else if (i == latitudeTitles.size() - 1) {
					// canvas.drawText(latitudeTitles.get(i), startFrom, offset
					// - i * postOffset + latitudeFontSize, mPaintFont);
				} else {
					canvas.drawText(latitudeTitles.get(i), startFrom, offset
							- (i - 1) * postOffset - postOffset / 4
							+ latitudeFontSize / 2f, mPaintFont);
				}
			}
		}

	}

	/**
	 * <p>
	 * Zoom in the graph
	 * </p>
	 * <p>
	 * 鎷″ぇ琛ㄧず銇欍倠銆� *
	 * </p>
	 * <p>
	 * 鏀惧ぇ琛ㄧず
	 * </p>
	 */
	protected boolean zoomIn() {
		// DO NOTHING
		return false;
	}

	/**
	 * <p>
	 * Zoom out the grid
	 * </p>
	 * <p>
	 * 绺皬琛ㄧず銇欍倠銆� *
	 * </p>
	 * <p>
	 * 缂╁皬
	 * </p>
	 */
	protected boolean zoomOut() {
		// DO NOTHING
		return false;
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
	 * @return the axisXColor
	 */
	public int getAxisXColor() {
		return axisXColor;
	}

	/**
	 * @param axisXColor
	 *            the axisXColor to set
	 */
	public void setAxisXColor(int axisXColor) {
		this.axisXColor = axisXColor;
	}

	/**
	 * @return the axisYColor
	 */
	public int getAxisYColor() {
		return axisYColor;
	}

	/**
	 * @param axisYColor
	 *            the axisYColor to set
	 */
	public void setAxisYColor(int axisYColor) {
		this.axisYColor = axisYColor;
	}

	/**
	 * @return the axisWidth
	 */
	// public float getAxisWidth() {
	// return axisWidth;
	// }

	/**
	 * @param axisWidth
	 *            the axisWidth to set
	 */
	// public void setAxisWidth(float axisWidth) {
	// this.axisWidth = axisWidth;
	// }

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
	 * @return the dataQuadrantPaddingTop
	 */
	@Deprecated
	public float getAxisMarginTop() {
		return dataQuadrantPaddingTop;
	}

	/**
	 * @param axisMarginTop
	 *            the axisMarginTop to set
	 */
	@Deprecated
	public void setAxisMarginTop(float axisMarginTop) {
		this.dataQuadrantPaddingTop = axisMarginTop;
		this.dataQuadrantPaddingBottom = axisMarginTop;
	}

	/**
	 * @return the dataQuadrantPaddingRight
	 */
	@Deprecated
	public float getAxisMarginRight() {
		return dataQuadrantPaddingRight;
	}

	/**
	 * @param axisMarginRight
	 *            the axisMarginRight to set
	 */
	@Deprecated
	public void setAxisMarginRight(float axisMarginRight) {
		this.dataQuadrantPaddingRight = axisMarginRight;
		this.dataQuadrantPaddingLeft = axisMarginRight;
	}

	/**
	 * @return the dataQuadrantPaddingTop
	 */
	public float getDataQuadrantPaddingTop() {
		return dataQuadrantPaddingTop;
	}

	/**
	 * @param dataQuadrantPaddingTop
	 *            the dataQuadrantPaddingTop to set
	 */
	public void setDataQuadrantPaddingTop(float dataQuadrantPaddingTop) {
		this.dataQuadrantPaddingTop = dataQuadrantPaddingTop;
	}

	/**
	 * @return the dataQuadrantPaddingLeft
	 */
	public float getDataQuadrantPaddingLeft() {
		return dataQuadrantPaddingLeft;
	}

	/**
	 * @param dataQuadrantPaddingLeft
	 *            the dataQuadrantPaddingLeft to set
	 */
	public void setDataQuadrantPaddingLeft(float dataQuadrantPaddingLeft) {
		this.dataQuadrantPaddingLeft = dataQuadrantPaddingLeft;
	}

	/**
	 * @return the dataQuadrantPaddingBottom
	 */
	public float getDataQuadrantPaddingBottom() {
		return dataQuadrantPaddingBottom;
	}

	/**
	 * @param dataQuadrantPaddingBottom
	 *            the dataQuadrantPaddingBottom to set
	 */
	public void setDataQuadrantPaddingBottom(float dataQuadrantPaddingBottom) {
		this.dataQuadrantPaddingBottom = dataQuadrantPaddingBottom;
	}

	/**
	 * @return the dataQuadrantPaddingRight
	 */
	public float getDataQuadrantPaddingRight() {
		return dataQuadrantPaddingRight;
	}

	/**
	 * @param dataQuadrantPaddingRight
	 *            the dataQuadrantPaddingRight to set
	 */
	public void setDataQuadrantPaddingRight(float dataQuadrantPaddingRight) {
		this.dataQuadrantPaddingRight = dataQuadrantPaddingRight;
	}

	/**
	 * @param padding
	 *            the dataQuadrantPaddingTop dataQuadrantPaddingBottom
	 *            dataQuadrantPaddingLeft dataQuadrantPaddingRight to set
	 * 
	 */
	public void setDataQuadrantPadding(float padding) {
		this.dataQuadrantPaddingTop = padding;
		this.dataQuadrantPaddingLeft = padding;
		this.dataQuadrantPaddingBottom = padding;
		this.dataQuadrantPaddingRight = padding;
	}

	/**
	 * @param topnbottom
	 *            the dataQuadrantPaddingTop dataQuadrantPaddingBottom to set
	 * @param leftnright
	 *            the dataQuadrantPaddingLeft dataQuadrantPaddingRight to set
	 * 
	 */
	public void setDataQuadrantPadding(float topnbottom, float leftnright) {
		this.dataQuadrantPaddingTop = topnbottom;
		this.dataQuadrantPaddingLeft = leftnright;
		this.dataQuadrantPaddingBottom = topnbottom;
		this.dataQuadrantPaddingRight = leftnright;
	}

	/**
	 * @param top
	 *            the dataQuadrantPaddingTop to set
	 * @param right
	 *            the dataQuadrantPaddingLeft to set
	 * @param bottom
	 *            the dataQuadrantPaddingBottom to set
	 * @param left
	 *            the dataQuadrantPaddingRight to set
	 * 
	 */
	public void setDataQuadrantPadding(float top, float right, float bottom,
			float left) {
		this.dataQuadrantPaddingTop = top;
		this.dataQuadrantPaddingLeft = right;
		this.dataQuadrantPaddingBottom = bottom;
		this.dataQuadrantPaddingRight = left;
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
	 * @return the latitudeTitles
	 */
	public List<String> getLatitudeTitles() {
		return latitudeTitles;
	}

	/**
	 * @param latitudeTitles
	 *            the latitudeTitles to set
	 */
	public void setLatitudeTitles(List<String> latitudeTitles) {
		this.latitudeTitles = latitudeTitles;
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

	/**
	 * @return the axisXPosition
	 */
	public int getAxisXPosition() {
		return axisXPosition;
	}

	/**
	 * @param axisXPosition
	 *            the axisXPosition to set
	 */
	public void setAxisXPosition(int axisXPosition) {
		this.axisXPosition = axisXPosition;
	}

	/**
	 * @return the axisYPosition
	 */
	public int getAxisYPosition() {
		return axisYPosition;
	}

	/**
	 * @param axisYPosition
	 *            the axisYPosition to set
	 */
	public void setAxisYPosition(int axisYPosition) {
		this.axisYPosition = axisYPosition;
	}

}
