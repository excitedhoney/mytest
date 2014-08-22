package cn.precious.metal.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceActivity;

public class AppSetting {

	private static AppSetting instance;

	private Context context;

	private AppSetting(Context context) {
		this.context = context;
	}

	public static AppSetting getInstance(Context context) {
		if (instance == null) {
			instance = new AppSetting(context);
		} else {
			instance.context = context;
		}
		return instance;
	}
	/**
	 * 
	 */
	public boolean  isLoginOn () {
		if(getPhoneNumber() != null && !"".equals(getPhoneNumber()))
			return true ;
		return false ;
	}
	
	public void setRegisterCode (String code) {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("RegisterCode", code);
		editor.commit();
	}
	
	public String getRegisterCode () {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getString("RegisterCode", "");
	}
	
	
	public void setRefreshTime (int time) {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("RefreshTime", time);
		editor.commit();
	}
	
	public int getRefreshTime () {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("RefreshTime", 5000);
	}
	
	
	public void setHighlightFlag (boolean isHight) {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("HighlightFlag", isHight);
		editor.commit();
	}
	
	public boolean isHighlightFlag () {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("HighlightFlag", false);
	}
	
	public void setTuisongFlag (boolean isHight) {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("TuisongFlag", isHight);
		editor.commit();
	}
	
	public boolean isTuisongFlagFlag () {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("TuisongFlag", false);
	}
	
	
	
	
	public void clearUserInfo () {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear() ;
		editor.commit() ;
	}
	
	
	public void setPhoneNumber(String phoneNumber) {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("PhoneNumber", phoneNumber);
		editor.commit();
	}
	
	
	public String getPhoneNumber() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);

		return sp.getString("PhoneNumber", "");
	}
	
	
	public void setVirtualBalance(float balance)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putFloat("VirtualBalance", balance);
		editor.commit();
	}
	
	
	public float getVirtualBalance() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getFloat("VirtualBalance", 10000);
	}
	
	
	/***
	 * Sma
	 * @param param
	 */
	public void setSmaParam1(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("SmaParam1", param);
		editor.commit();
	}
	
	
	public int getSmaParam1() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("SmaParam1", 5);
	}
	
	public void setSmaParam2(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("SmaParam2", param);
		editor.commit();
	}
	
	
	public int getSmaParam2() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("SmaParam2", 10);
	}
	
	public void setSmaParam3(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("SmaParam3", param);
		editor.commit();
	}
	
	
	public int getSmaParam3() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("SmaParam3", 20);
	}
	
	
	public void setEmaParam(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("EmaParam", param);
		editor.commit();
	}
	
	
	public int getEmaParam() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("EmaParam", 20);
	}
	
	
	public void setBoolTParam(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("BoolTParam", param);
		editor.commit();
	}
	
	
	public int getBoolTParam() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("BoolTParam", 20);
	}
	
	public void setBoolKParam(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("BoolKParam", param);
		editor.commit();
	}
	
	
	public int getBoolKParam() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("BoolKParam", 2);
	}
	
	/***
	 * macd
	 */
	public void setMacdTParam1(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("MacdTParam1", param);
		editor.commit();
	}
	
	
	public int getMacdTParam1() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("MacdTParam1", 12);
	}
	public void setMacdTParam2(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("MacdTParam2", param);
		editor.commit();
	}
	
	
	public int getMacdTParam2() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("MacdTParam2", 26);
	}
	
	public void setMacdKParam(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("MacdKParam", param);
		editor.commit();
	}
	
	
	public int getMacdKParam() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("MacdKParam", 9);
	}
	
	
	public void setKdjParam(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("KdjParam", param);
		editor.commit();
	}
	
	
	public int getKdjKParam() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("KdjParam", 9);
	}
	
	
	public void setRsiParam1(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("RsiParam1", param);
		editor.commit();
	}
	
	
	public int getRsiParam1() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("RsiParam1", 6);
	}
	
	public void setRsiParam2(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("RsiParam2", param);
		editor.commit();
	}
	
	
	public int getRsiParam2() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("RsiParam2", 12);
	}
	public void setRsiParam3(int param)  {
		SharedPreferences sp = context.getSharedPreferences("user_info",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("RsiParam3", param);
		editor.commit();
	}
	
	
	public int getRsiParam3() {
		SharedPreferences sp = context.getSharedPreferences(
				"user_info", PreferenceActivity.MODE_PRIVATE);
		return sp.getInt("RsiParam3", 24);
	}

	/***
	 * ========================================================================
	 * ========= 所有配置项的基本信息
	 * 
	 * @return
	 */
	public boolean isNeedGuide() {
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);

		return sp.getBoolean("guide", true);
	}

	public void setNeedGuide(boolean needGuide) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("guide", needGuide);
		editor.commit();
	}

	//天津所
	public boolean isHaveInitTJSOptionalData() {
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("HaveInitTJSOptionalData", false);
	}

	public void setHaveInitTJSOptionalData(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("HaveInitTJSOptionalData", save);
		editor.commit();
	}
	
	//上交所
	public boolean isHaveInitSJSOptionalData() {
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("HaveInitSJSOptionalData", false);
	}
	
	public void setHaveInitSJSOptionalData(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("HaveInitSJSOptionalData", save);
		editor.commit();
	}
	//外汇
	public boolean isHaveInitForexOptionalData() {
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("HaveInitForexOptionalData", false);
	}
	
	public void setHaveInitForexOptionalData(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("HaveInitForexOptionalData", save);
		editor.commit();
	}
	//国际商品
	public boolean isHaveInitCommodityOptionalData() {
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("HaveInitCommodityOptionalData", false);
	}
	
	public void setHaveInitCommodityOptionalData(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("HaveInitCommodityOptionalData", save);
		editor.commit();
	}
	//指数
	public boolean isHaveInitIndiceOptionalData() {
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("HaveInitIndiceOptionalData", false);
	}
	
	public void setHaveInitIndiceOptionalData(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("HaveInitIndiceOptionalData", save);
		editor.commit();
	}

	/***
	 * =========  下拉刷新的时间 
	 */
	public void  setOptionalRefreshTime(long time){
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("OptionalRefreshTime", time);
		editor.commit();
	}
	
	public long getOptionalRefreshTime(){
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);

		return sp.getLong("OptionalRefreshTime", System.currentTimeMillis());
	}
	
	public void  setGloalNewsRefreshTime(long time){
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("GloalNewsRefreshTime", time);
		editor.commit();
	}
	
	public long getGloalNewsRefreshTime(){
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);

		return sp.getLong("GloalNewsRefreshTime", System.currentTimeMillis());
	}
	
	public void  setMacroHotRefreshTime(long time){
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("MacroHotRefreshTime", time);
		editor.commit();
	}
	
	public long getMacroHotRefreshTime(){
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		
		return sp.getLong("MacroHotRefreshTime", System.currentTimeMillis());
	}
	
	public boolean isFirstInitOptional() {
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);

		return sp.getBoolean("FirstInitOptional", false);
	}
	public void setFirstInitOptional(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("FirstInitOptional", save);
		editor.commit();
	}

	
	public boolean isShowNewsPic() {
		// TODO Auto-generated method stub
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("ShowNewsPic", true);
	}

	public void setShowNewsPic(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("ShowNewsPic", save);
		editor.commit();
	}
	
	public boolean isBigTextFont() {
		// TODO Auto-generated method stub
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);
		return sp.getBoolean("BigTextFont", false);
	}
	
	public void setBigTextFont(boolean save) {
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("BigTextFont", save);
		editor.commit();
	}
	
	
	//
	public long getStrategyRefreshTime(){
		SharedPreferences sp = context.getSharedPreferences(
				ParamConfig.SETTING_FILE_NAME, PreferenceActivity.MODE_PRIVATE);

		return sp.getLong("StrategyRefreshTime", System.currentTimeMillis());
	}
	
	public void  setStrategyRefreshTime(long time){
		SharedPreferences sp = context.getSharedPreferences("setting",
				PreferenceActivity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putLong("StrategyRefreshTime", time);
		editor.commit();
	}

}
