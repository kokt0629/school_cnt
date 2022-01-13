package kr.peopleware.lucene.search.test;

import java.util.Date;

import kr.peopleware.lucene.search.Searcher;
import kr.peopleware.lucene.util.QueryBuilder;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.util.Version;

public class SearchTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		
		//설정 파일을 세팅하여 Searcher 생성
		Searcher search = new Searcher("search.properties");
		
//		search.setSort("tweetId",SortField.LONG,true);
		
		//색인된 데이터의 contents에 "감기"라는 검색어를 넣어서 검색
		Document[] docs = search.getDocuments(QueryBuilder.makeQuery("contents", "감기",new CJKAnalyzer(Version.LUCENE_36)));

		int count = 0;
		for (Document document : docs) {
			if(count == 10)break;
			System.out.println("["+count+"]");			
			System.out.println(document.get("urls"));
			System.out.println(document.get("tweetId"));
			Date date = new Date(Long.parseLong(document.get("createdAt")));
			System.out.println(date);
			System.out.println(document.get("contents"));
			System.out.println();
			count++;
		}
		System.out.println(docs.length);
		search.close();
	}

}
