package kr.peopleware.lucene.model;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

public class Tweet extends BaseModel{
	private long tweetId;
	private String contents;
	private long createdAt;
	private long crawledAt;	
	private boolean linked;
	private List<String> urls;	
	public long getTweetId() {
		return tweetId;
	}
	public void setTweetId(long tweetId) {		
		this.tweetId = tweetId;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	public long getCrawledAt() {
		return crawledAt;
	}
	public void setCrawledAt(long crawledAt) {
		this.crawledAt = crawledAt;
	}	
	public boolean isLinked() {
		return linked;
	}
	public void setLinked(boolean linked) {
		this.linked = linked;
	}
	public List<String> getUrls() {
		return urls;
	}
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	@Override
	public Document convetDocument() {
		Document doc = new Document();
		NumericField createdAt = new NumericField("createdAt",Field.Store.YES,true);
		createdAt.setLongValue(this.getCreatedAt());		
		doc.add(createdAt);		
	
		
		NumericField tweetId = new NumericField("tweetId",Field.Store.YES,false);		
		tweetId.setLongValue(this.getTweetId());
		doc.add(tweetId);
		
		String type = "all";
		if(this.getUrls() == null || this.getUrls().size() == 0){
			type="link";
		}
		
		doc.add(new Field("type",type,Field.Store.NO,Field.Index.NOT_ANALYZED));
		if(this.getUrls() != null){
			doc.add(new Field("urls",this.getUrls().toString(),Field.Store.YES,Field.Index.NOT_ANALYZED));
		}		
		doc.add(new Field("contents", this.getContents(), Field.Store.YES, Field.Index.ANALYZED));
		return doc;
	}	
}
