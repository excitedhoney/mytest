package com.xiyou.apps.lookpan.model;

public class MingJiaInfoBean {

	private String article_id;
	private String author;
	private String article_title;
	private String image_url;
	private String article_date;
	private String content;

	public String getArticle_date() {
		return article_date;
	}

	public void setArticle_date(String article_date) {
		this.article_date = article_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getArticle_title() {
		return article_title;
	}

	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	@Override
	public String toString() {
		return "MingJiaInfoBean [article_id=" + article_id + ", author="
				+ author + ", article_title=" + article_title + ", image_url="
				+ image_url + ", article_date=" + article_date + ", content="
				+ content + "]";
	}

}
