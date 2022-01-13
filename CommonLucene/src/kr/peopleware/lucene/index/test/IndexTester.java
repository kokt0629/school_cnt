package kr.peopleware.lucene.index.test;

import java.util.ArrayList;
import java.util.List;

import kr.peopleware.lucene.index.Indexer;
import kr.peopleware.lucene.index.properties.PropertiesManager;
import kr.peopleware.lucene.model.Tweet;
import kr.peopleware.util.file.FileUtil;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.util.Version;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class IndexTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		//설정 파일 경로
		PropertiesManager pm = new PropertiesManager("index.properties");
		
		//Analyzer 및 설정 파일 경로를 초기화하는 Indexer 생성
		Indexer indexer = new Indexer(new CJKAnalyzer(Version.LUCENE_36),"index.properties");
		
		//설정 파일의 TARGET_PATH로부터 데이터를 가져옴
		List<Tweet> tweetList = getTweets(pm.getProperty("TARGET_PATH"));		
		
		//색인
		for (Tweet tweet : tweetList ) {
			indexer.index(tweet);
		}
		
		//커밋
		indexer.commit();
		indexer.close();
	}

	private static List<Tweet> getTweets(String path) {
		List<Tweet> tweetList = new ArrayList<Tweet>();
		List<String> filenames = FileUtil.getFileNames(path);

		for (String filename : filenames) {
			List<String> lines = null;
			try {
				lines = FileUtil.load2List(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(lines == null){
				continue;
			}
			System.out.println("line : " + lines);
			for (String line : lines) {
				
				Tweet t = convertTweet((DBObject) JSON.parse(line));				

				tweetList.add(t);
			}
			//			System.out.println(filename);
		}
		return tweetList;
	}
	private static Tweet convertTweet(DBObject obj) {		
		Tweet tweet = new Tweet();
		tweet.setTweetId((Long) obj.get("tweetId"));
		tweet.setContents((String) obj.get("contents"));		
		tweet.setCreatedAt((Long) obj.get("createdAt"));
		tweet.setCrawledAt((Long) obj.get("crawledAt"));
		tweet.setLinked((Boolean)obj.get("linked"));
		@SuppressWarnings("unchecked")
		List<String> urls = (List<String>) obj.get("urls");
		if(urls != null){
			tweet.setUrls(urls);
		}				

		return tweet;
	}

}
