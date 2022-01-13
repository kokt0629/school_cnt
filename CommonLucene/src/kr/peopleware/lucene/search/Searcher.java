
package kr.peopleware.lucene.search;

import java.io.File;
import java.io.IOException;

import kr.peopleware.lucene.index.properties.PropertiesManager;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryTermVector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher
{

	private String indexPath;
	private Sort sort;	
	private PropertiesManager propertiesManager;
	private Directory dir;
	private IndexReader reader;
	private IndexSearcher searcher;
	
	public Searcher()
	{
		indexPath = null;
		sort = null;	
	}
	public Searcher(String propertiesFileName){
		setPropertiesFile(propertiesFileName);
		init();
	}
	public void setSort(String sortFieldName,int fieldType,boolean reverse){
		sort = new Sort(new SortField(sortFieldName, fieldType,reverse));
	}
	private void init() {		
		indexPath = propertiesManager.getProperty("INDEX_PATH");
	}
	public void setPropertiesFile(String filename){
		propertiesManager = new PropertiesManager(filename);
	}
	public Document[] getDocuments(Query q){		
		Document[] resultDocument = null;
		try {
			dir = FSDirectory.open(new File(indexPath));			
			reader = IndexReader.open(dir);
//			CustomAddScoreQuery c = new CustomAddScoreQuery(reader);			
			searcher = new IndexSearcher(reader);			
			resultDocument = this.searchDocument(searcher,-1,q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultDocument;
	}
	public void close(){
		try {
			dir.close();
			reader.close();
			searcher.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
 	public Document[] getDocument(Query q)
	{
		if(indexPath == null)
			return null;
		Document[] resultDocument = null;
		Directory dir = null;
		try
		{
			dir = FSDirectory.open(new File(indexPath));
			IndexReader reader = null;
			reader = IndexReader.open(dir,true);
			IndexSearcher searcher = new IndexSearcher(reader);
			resultDocument = this.searchDocument(searcher,-1,q);			
			searcher.close();
			reader.close();
			dir.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultDocument;
	}

	public Document[] getDocument(Query q,int maxDocumentCount)
	{
		if(indexPath == null)
			return null;
		Document[] resultDocument = null;
		Directory dir = null;
		try
		{
			dir = FSDirectory.open(new File(indexPath));
			IndexReader reader = null;
			reader = IndexReader.open(dir,true);
			IndexSearcher searcher = new IndexSearcher(reader);
			resultDocument = this.searchDocument(searcher,maxDocumentCount,q);
			searcher.close();
			reader.close();
			dir.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultDocument;
	}
		
	private Document[] searchDocument(IndexSearcher searcher, int maxDocumentCount,Query q) throws Exception{
		Document[] resultDocument = null;
		TopDocs docs = null;
		TopFieldDocs sortDocs = null;
		docs = searcher.search(q, 1);		
		if(docs.totalHits > 0)
		{
			if(maxDocumentCount < 0 || maxDocumentCount > docs.totalHits){
				maxDocumentCount = docs.totalHits;
			}
			
			ScoreDoc[] result = null;
			if(sort != null){
//				System.out.println("소팅");
				sortDocs = searcher.search(q, maxDocumentCount, sort);
				result = sortDocs.scoreDocs;
			}else{				
//				System.out.println("소팅 안됨");
				docs = searcher.search(q, maxDocumentCount);
				result = docs.scoreDocs;
			}						
			resultDocument = new Document[maxDocumentCount];			
			for(int i = 0; i < maxDocumentCount; i++)
				resultDocument[i] = searcher.doc(result[i].doc);

		}else{
			System.out.println("검색 결과 없음");
		}
//		System.out.println(maxDocumentCount);		
		return resultDocument;
	}
	
	public TopDocs getTopDocument(String field,String q)
	{
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_35);
		BooleanQuery bq = new BooleanQuery();
		QueryTermVector qtv = new QueryTermVector(q, analyzer);
		for(int i=0;i<qtv.size();i++)
		{				
			bq.add(new TermQuery(new Term(field,qtv.getTerms()[i])), BooleanClause.Occur.MUST);
		}
		return getTopDocument(bq);
	}
	public TopDocs getTopDocument(String field,String q,int maxDocumentCount)
	{
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_35);
		BooleanQuery bq = new BooleanQuery();
		QueryTermVector qtv = new QueryTermVector(q, analyzer);
		for(int i=0;i<qtv.size();i++)
		{				
			bq.add(new TermQuery(new Term(field,qtv.getTerms()[i])), BooleanClause.Occur.MUST);
		}
		return getTopDocument(bq,maxDocumentCount);
	}	
	
	public TopDocs getTopDocument(String field, Object a,Object b){
		Query nq = null;
		if(a.getClass() == Integer.class){
			nq = NumericRangeQuery.newIntRange(field, (Integer)a, (Integer)b, true, true);
			
		}else if(a.getClass() == Double.class){
			nq = NumericRangeQuery.newDoubleRange(field, (Double)a, (Double)b, true, true);
			
		}else if(a.getClass() == Float.class){
			nq = NumericRangeQuery.newFloatRange(field, (Float)a, (Float)b, true, true);
			
		}else if(a.getClass() == Long.class){
			nq = NumericRangeQuery.newLongRange(field, (Long)a, (Long)b, true, true);
			
		}
		return getTopDocument(nq);
	}
	public TopDocs getTopDocument(String field, Object a,Object b,int maxDocumentCount){
		Query nq = null;
		if(a.getClass() == Integer.class){
			nq = NumericRangeQuery.newIntRange(field, (Integer)a, (Integer)b, true, true);
			
		}else if(a.getClass() == Double.class){
			nq = NumericRangeQuery.newDoubleRange(field, (Double)a, (Double)b, true, true);
			
		}else if(a.getClass() == Float.class){
			nq = NumericRangeQuery.newFloatRange(field, (Float)a, (Float)b, true, true);
			
		}else if(a.getClass() == Long.class){
			nq = NumericRangeQuery.newLongRange(field, (Long)a, (Long)b, true, true);
			
		}
		return getTopDocument(nq,maxDocumentCount);
	}

 	public TopDocs getTopDocument(Query q)
	{
		if(indexPath == null)
			return null;
		TopDocs resultDocument = null;
		Directory dir = null;
		try
		{
			dir = FSDirectory.open(new File(indexPath));
			IndexReader reader = null;
			reader = IndexReader.open(dir,true);
			IndexSearcher searcher = new IndexSearcher(reader);
			resultDocument = this.searchTopDocument(searcher,-1,q);			
			searcher.close();
			reader.close();
			dir.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultDocument;
	}

	public TopDocs getTopDocument(Query q,int maxDocumentCount)
	{
		if(indexPath == null)
			return null;
		TopDocs resultDocument = null;
		Directory dir = null;
		try
		{
			dir = FSDirectory.open(new File(indexPath));
			IndexReader reader = null;
			reader = IndexReader.open(dir,true);
			IndexSearcher searcher = new IndexSearcher(reader);
			resultDocument = this.searchTopDocument(searcher,maxDocumentCount,q);
			searcher.close();
			reader.close();
			dir.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return resultDocument;
	}
	
	private TopDocs searchTopDocument(IndexSearcher searcher, int maxDocumentCount,Query q) throws Exception{		
		TopDocs docs = null;
		docs = searcher.search(q, 1);
		if(docs.totalHits > 0)
		{
			if(maxDocumentCount < 0){
				maxDocumentCount = docs.totalHits;
			}
			if(sort != null){
				System.out.println("소팅");
				docs = searcher.search(q, maxDocumentCount,sort);
			}else{				
				System.out.println("소팅 안됨");
				docs = searcher.search(q, maxDocumentCount);
			}			
		}else{
			System.out.println("검색 결과 없음");
		}
		return docs;
	}
	
}
