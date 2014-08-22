package cn.precious.metal.entity;

import java.util.List;

public class NewsInfo {

	private String node_title;
	private int nid;
	private long node_created;
	private String file_managed_file_usage_uri;
	
	
	private List<TaxonomyVocabulary> taxonomy_vocabulary_6;
	public String getNode_title() {
		return node_title;
	}
	public void setNode_title(String node_title) {
		this.node_title = node_title;
	}
	public int getNid() {
		return nid;
	}
	public void setNid(int nid) {
		this.nid = nid;
	}
	public long getNode_created() {
		return node_created;
	}
	public void setNode_created(long node_created) {
		this.node_created = node_created;
	}
	public String getFile_managed_file_usage_uri() {
		return file_managed_file_usage_uri;
	}
	public void setFile_managed_file_usage_uri(String file_managed_file_usage_uri) {
		this.file_managed_file_usage_uri = file_managed_file_usage_uri;
	}
	public List<TaxonomyVocabulary> getTaxonomy_vocabulary_6() {
		return taxonomy_vocabulary_6;
	}
	public void setTaxonomy_vocabulary_6(
			List<TaxonomyVocabulary> taxonomy_vocabulary_6) {
		this.taxonomy_vocabulary_6 = taxonomy_vocabulary_6;
	}
	

}
