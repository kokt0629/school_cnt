package kr.peopleware.lucene.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryTermVector;
import org.apache.lucene.search.TermQuery;

public class QueryBuilder {
 	public static Query makeQuery(String field,String q,Analyzer analyzer)
	{
		BooleanQuery bq = new BooleanQuery();
		QueryTermVector qtv = new QueryTermVector(q, analyzer);
		for(int i=0;i<qtv.size();i++)
		{
			bq.add(new TermQuery(new Term(field,qtv.getTerms()[i])), BooleanClause.Occur.MUST);
		}
		return bq;
	}
 	public static Query makeQuery2(String field,String q,Analyzer analyzer)
	{
		BooleanQuery bq = new BooleanQuery();
		QueryTermVector qtv = new QueryTermVector(q, analyzer);
		for(int i=0;i<qtv.size();i++)
		{
			bq.add(new TermQuery(new Term(field,qtv.getTerms()[i])), BooleanClause.Occur.MUST);
		}
		return bq;
	}
 	public static Query makeQuery(String field, Object a,Object b){
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
		return nq;
	}
}
