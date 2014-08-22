package cn.precious.metal.entity;

public class Strategy {
	private String article_id;
	private String author;
	private String author_pic;
	private String article_title;
	private String image_url;
	private String summary;

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

	public String getAuthor_pic() {
		return author_pic;
	}

	public void setAuthor_pic(String author_pic) {
		this.author_pic = author_pic;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "Strategy [article_id=" + article_id + ", author=" + author
				+ ", author_pic=" + author_pic + ", article_title="
				+ article_title + ", image_url=" + image_url + ", summary="
				+ summary + "]";
	}

}
