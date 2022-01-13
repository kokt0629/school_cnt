package kr.peopleware.lucene.model;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class School extends BaseModel{
	private String school;
	private String contents;
	
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	@Override
	public Document convetDocument() {
		Document doc = new Document();

		//doc.add(new Field("school", this.getSchool(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("contents", this.getContents(), Field.Store.YES, Field.Index.ANALYZED));
		return doc;
	}	
}
