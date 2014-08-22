package cn.precious.metal.ui.fragment.news.live;

public class Util {

	public static String filterHtml_new(String str) {
		String strr = str;
		char[] cah = strr.toCharArray();
		for (int i = 0; i < cah.length; i++) {
		}
		if (strr.contains("\n")) {
			strr = strr.replace("\n", "");
		}
		if (strr.contains("<p>")) {
			strr = strr.replace("<p>", "");
		}
		if (strr.contains("</p>")) {
			strr = strr.replace("</p>", "");
		}
		if (strr.contains("<br/>")) {
			strr = strr.replace("<br/>", "");
		}
		if (strr.contains("<div>")) {
			strr = strr.replace("<div>", "");
		}
		if (strr.contains("</div>")) {
			strr = strr.replace("</div>", "");
		}
		if (strr.contains("&nbsp;")) {
			strr = strr.replace("&nbsp;", "");
		}
		if (strr.contains(" ")) {
			strr = strr.replace(" ", "");
		}
		if (strr.contains("&mdash;")) {
			strr = strr.replace("&mdash;", "--");
		}
		if (strr.contains("&ldquo;")) {
			strr = strr.replace("&ldquo;", "“");
		}
		if (strr.contains("&rdquo;")) {
			strr = strr.replace("&ldquo;", "”");
		}
		if (strr.contains("<br/>")) {
			strr = strr.replace("<br/>", "");
		}
		if (strr.contains("<ahref=") || strr.contains("<a href=")) {

			strr = strr.split("<u>")[0];

		}
		// strr.indexOf("<strong>")

		return getHTML(strr);

	}

	public static String getHTML(String s) {
		StringBuffer b = new StringBuffer(s);
		int i = s.indexOf("<strong>");
		int j = s.indexOf("</strong>");
		if (i > 0) {
			b.replace(i, j + 9, "");
		}

		return b.toString();

	}
}
