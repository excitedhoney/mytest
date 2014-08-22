package cn.precious.metal.config;

public class AndroidAPIConfig {
	// 获取华尔街自选的列表
	// public static final String HUAERJIE_HANGQING_OPTIONAL =
	// "http://api.markets.wallstreetcn.com/v1/quotes.json" ;

	// 获取天津所的所有行情
	public static final String TJS_OPTIONAL = "http://www.06866.com/app/get_tg_data";
	// 获取上交所的所有行情
	public static final String SJS_OPTIONAL = "http://www.06866.com/app/get_sg_data";
	//获取国际商品
	public static final String COMMODITY_OPTIONAL = "http://www.06866.com/app/get_often_data/commodity";
	//获取行情的外汇
	public static final String FOREX_OPTIONAL = "http://www.06866.com/app/get_often_data/forex";
	//获取指数行情
	public static final String INDICE_OPTIONAL = "http://www.06866.com/app/get_often_data/indice";

	public static final String HUAERJIE_LIVE = "http://api.wallstreetcn.com/apiv1/livenews-list-v2.json";

	// 获取华尔街最新数据的
	public static final String HUAERJIE_HANGQING_OPTIONAL_LATEST_PRICE = "http://api.markets.wallstreetcn.com/v1/price.json";

	// 获取华尔街财经日历
	public static final String HUAERJIE_ECONOMIC_CANLENDA = "http://api.markets.wallstreetcn.com/v1/calendar.json";

	// 获取华头条新闻
	public static final String HUAERJIE_NEWS = "http://api.wallstreetcn.com/apiv1/news-list.json";
	public static final String TOP_HUAERJIE_NEWS = "http://api.wallstreetcn.com/apiv1/topnews-list.json";

	// 获取华 宏观热点
	public static final String HUAERJIE_MACRO_HOT = "http://api.wallstreetcn.com/apiv1/news-list.json?spid=11142";
	// 获取华 美国新闻
	public static final String HUAERJIE_American = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=7350";

	// 获取黄金ETF报告
	public static final String ETF_REPORT_GOLD = "http://unews.fx678.com/union/jzr/ishare_gold.html";
	// 获取白银ETF报告
	public static final String ETF_REPORT_SILVER = "http://unews.fx678.com/union/jzr/ishare_silver.html";

	// 获取K线的数据api
	public static final String KLINE_API = "http://www.06866.com/app/get_interval_data";

	// 根据treaty 获取最新的价格
	public static final String GET_TREATY_DATE = "http://www.06866.com/app/get_treaty_data";
	// 获取验证码
	public static final String GET_CODE = "http://www.06866.com/app/send_mobile_code?mobile=";
	// 注册
	public static final String REGISTER_API = "http://www.06866.com/app/register";
	// 登录
	public static final String LOGIN_API = "http://www.06866.com/app/login";
	// 策略list
	public static final String CELUO_LIST = "http://www.06866.com/app/article_list";
	// 策略详细页
	public static final String DETAIL_CELUO = "http://www.06866.com/app/get_article";

}
