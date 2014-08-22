package cn.precious.metal.config;

public class API {

	public static final String base = "http://api.wallstreetcn.com/";
	
	public static final String javascript = "<script type='text/javascript'>" 
												+ "function intentTo(imageUrl) {"
												+ "window.jsObj.fullImage(imageUrl) ;"
												+ "}"
											+ "</script>";
	
	
	public static final String img = "<style type='text/css'> img{ "
			+ "border:0;"
			+ "margin:0;"
			// + "padding:20;"
			+ "max-width:100%;width:100%;text-align:center;    vertical-align:middle"
			+ "overflow:hidden;}" + "body{" + "padding:5;" + "font-size:16px;"
			+ " font-family:'微软雅黑';font-style: normal;" + "color:white;}"
			+ "a:link {text-decoration:none;color:black;}\n"
			+ "a:hover {text-decoration:none;color:red;}\n"
			+ "a:active {text-decoration:none;color:red;}\n"
			+ "a:visited {text-decoration:none;color:gray;}" + "</style>";
	
	
	public static final String imgBig = "<style type='text/css'> img{ "
			+ "border:0;"
			+ "margin:0;"
			// + "padding:20;"
			+ "max-width:100%;width:100%;text-align:center;    vertical-align:middle"
			+ "overflow:hidden;}" + "body{" + "padding:5;" + "font-size:18px;"
			+ " font-family:'微软雅黑';font-style: normal;" + " color:#ffffff;}"
			+ "a:link {text-decoration:none;color:black;}\n"
			+ "a:hover {text-decoration:none;color:red;}\n"
			+ "a:active {text-decoration:none;color:red;}\n"
			+ "a:visited {text-decoration:none;color:gray;}" + "</style>";
	public static final String url_news_link = "http://wallstreetcn.com/node/";
	public static final String url_live_link = "http://live.wallstreetcn.com/node/";
	public static final String base_img = "http://thumbnail.wallstreetcn.com/thumb/";
	public static final String news_head = "http://api.wallstreetcn.com/apiv1/news-list.json";
	public static final String news_recommand = "http://api.wallstreetcn.com/apiv1/news-list.json?spid=3119";
	public static final String news_info = "http://api.wallstreetcn.com/apiv1/node/";
	public static final String news_live = "http://api.wallstreetcn.com/apiv1/livenews-list.json";
	public static final String news_live_refresh = "http://api.wallstreetcn.com/apiv1/livenews.json";
	public static final String news_live_refresh_count = "http://api.wallstreetcn.com/apiv1/livenews-count.json";
	public static final String news_heartest = "http://api.wallstreetcn.com/apiv1/rank-twodays-list.json";
	public static final String news_more_breakfest = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=3562";
	public static final String news_more_china = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=7351";
	public static final String news_more_europe = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=7349";
	public static final String news_more_USA = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=7350";
	public static final String news_more_finance = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=4";
	public static final String news_more_market = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=48";
	public static final String news_more_company = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=7354";
	public static final String news_more_CentralBank = "http://api.wallstreetcn.com/apiv1/news-list.json?tid=7353";
	public static final String news_more_hot = "http://api.wallstreetcn.com/apiv1/rank-twodays-list.json";
	public static final String news_more_recommend = "http://api.wallstreetcn.com/apiv1/news-list.json?spid=3119";
	public static final String news_boradcast = "http://api.wallstreetcn.com/apiv1/topnews-list.json";

	public static final String MARKET = "http://wallstreetcn.sojex.cn/Wallstreetcn/query.action?rtp=GetTypeQuotes&type=";
	public static final String MARKET_CHOOSE = "http://wallstreetcn.sojex.cn/Wallstreetcn/query.action?rtp=GetBatchQuotes&ids=";
	public static final String MARKET_CHART = "http://wallstreetcn.sojex.cn/Wallstreetcn/query.action?rtp=TimeChart&qid=";
	public static final String MARKET_CHOOSE_LIST = "http://wallstreetcn.sojex.cn/Wallstreetcn/query.action?rtp=GetQuoteTypes";

	public static final String IMG_PEN = "<img src=\"http://bcs.duapp.com/wallstreetcn/pen.png\" style=\"height:15px; width:15px\" />";
	public static final String IMG_TAG = "<img src=\"http://bcs.duapp.com/wallstreetcn/tag.png\" style=\"height:15px; width:15px\"/>";

	public static final String IMG_PEN_BIG = "<img src=\"http://bcs.duapp.com/wallstreetcn/pen.png\" style=\"height:15px; width:20px\" />";
	public static final String IMG_TAG_BIG = "<img src=\"http://bcs.duapp.com/wallstreetcn/tag.png\" style=\"height:15px; width:20px\"/>";

}
