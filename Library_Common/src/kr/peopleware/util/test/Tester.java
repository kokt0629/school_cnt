package kr.peopleware.util.test;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import kr.peopleware.util.collection.MapUtil;
import kr.peopleware.util.collection.data.Pair;
import kr.peopleware.util.date.DateUtil;
import kr.peopleware.util.file.FileUtil;
import kr.peopleware.util.properties.SettingProperties;
import kr.peopleware.util.string.StringUtil;
import kr.peopleware.util.web.ApiCaller;
import kr.peopleware.util.web.WebCaller;
import kr.peopleware.util.web.XMLCaller;
import kr.peopleware.util.web.data.RequestResult;

import org.codehaus.jettison.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		List<String> strs = StringUtil.split("진짜 자꾸 이러지좀 말자", " ");
		System.out.println(strs);
//				StringUtilTester();
		//		DateUtilTester();
		//		MapUtilTester();
		//		APICallerTester();
//		BDBTester();
//				FileUtilTester();
		//		WebCallerTester();
		//		SettingPropertiesTester();
		//		XMLCallerTester();
		//		int sum = RecursiveTester(10);
		//		System.out.println(sum);
	}

	private static int RecursiveTester(int a) {
		int result = 0;
		return result;
	}

	private static void XMLCallerTester() {
		XMLCaller caller = new XMLCaller();
		Document document = caller.requestGET("http://openapi.naver.com/search?key=c9413c87172e87dbdc7d05a4346e8eca&query=nexearch&target=rank");
		NodeList nodeList = document.getElementsByTagName("K");
		for(int i=0;i<nodeList.getLength();i++){
			System.out.println(nodeList.item(i).getChildNodes().item(0).getNodeValue());
		}
	}

	private static void SettingPropertiesTester() {
		SettingProperties properties = SettingProperties.getInstance("setting.properties");
		String[] keys = properties.getKeys();
		for (String string : keys) {
			System.out.println(string);
		}
		Set<Entry<Object, Object>> set = properties.getEntrySet();
		for (Entry<Object, Object> entry : set) {
			String cat = (String) entry.getKey();
			int id = Integer.parseInt((String) entry.getValue());
			System.out.println(cat+":"+id);
		}
	}

	private static void WebCallerTester() {
		WebCaller caller = new WebCaller();		
		System.out.println(caller.request("http://www.quickple.com"));
	}

	private static void StringUtilTester() {
//		//		System.out.println(StringUtil.hasKorean("가을a"));		
//		//		System.out.println(StringUtil.isOnlyKorean("가을"));
//		//		System.out.println(StringUtil.getOnlyKorean("가을 a양과 b양은 good입니다.", false));
//		//		System.out.println(StringUtil.getOnlyNotKorean("가을 a양과 b양은 good입니다.", true));
//		//		System.out.println(StringUtil.getOnlyNotKorean("가을 a양과 b양은 good입니다12345.", false));		
//		System.out.println(StringUtil.getOnlyKoreanJamo("가을ㅋㅋㅋㅋㅃㆉ", false));
//		System.out.println(StringUtil.hasKoreanJamo("ㄱ"));
//		//		System.out.println(StringUtil.getOnlyKoreanCompatibilityJamo("가을ㅋㅋㅋㅋ짱웃ㄱㅋㅋㅋㅋaaa", false));
//		//		System.out.println(StringUtil.hasNumeric("1223ㅋㅋ"));
//		//		System.out.println(StringUtil.isOnlyNumeric("1223 "));
//		//		System.out.println(StringUtil.getOnlyNumeric("1223  aa11",true));
//		//		System.out.println(StringUtil.reductionWhitespace("ㄱㄴㅁㅁ     ㅋㅋ  ㅇㅇ\t\ta"));
//		//		System.out.println(StringUtil.removeWhitespace("ㄱㄴㅁㅁ  ㅋㅋ  ㅇㅇ"));				
//		System.out.println(StringUtil.getOnlyKoreanJamo("요런부류 아직 쫌 있죠. ㅜㅜ 확~마😡RT“@korea486: 17:41 이회창 ㅋㅋㅋ ㄱㄵ 대통령 만들기 보좌역을 맡았고 411민주당 공심위를 맡아 총선 말아잡수신 일곱번째 후보 연설 중인 조정식 후보! http://t.co/luIXuv9W", false));
//		System.out.println(StringUtil.trim("        또한 투바디로 많이들 이용하시는데, 생각보다 무게감이 있어 비추이다.                                          "));
//		System.out.println(StringUtil.trim("감기는 자주걸린다"));
//		System.out.println(StringUtil.trim("감기는 자주걸린다 "));
//		System.out.println(StringUtil.trim(" 감기는 자주걸린다"));
//		List<String> list = StringUtil.ngram("감기는 자주 걸리는 병이다.",5, true);
//		for (String string : list) {
//			System.out.println(string);
//		}
		System.out.println(StringUtil.getJasoLength('가'));
		System.out.println(StringUtil.getJasoLength("감각"));
		String str = "영어로 말해요(abc) 에이비씨 abc굿 ㅋ 짱 a!a";
//		String str = "( *｀ω´) ！(◎_◎;)( ´ ▽ ` )ﾉ♪( ´θ｀)ノ((((；ﾟДﾟ)))))))( ；´Д｀)";
		for(int i=0;i<str.length();i++){
			char ch = str.charAt(i);
			System.out.println(ch +"("+(int)ch+") | "+StringUtil.getCharacterType(ch));
		}
		
		
		List<String> aa = StringUtil.split(str, " ");
		for (String string : aa) {
			List<Pair<String,Integer>> listResult = StringUtil.getEojeolType(string);
			for (Pair<String, Integer> pair : listResult) {
				System.out.println(pair.getSecond() + " | "+pair.getFirst());
			}
			System.out.println();
		}
		
		
	}

	private static void DateUtilTester() {
		System.out.println(DateUtil.getDate(1338476331000L));
	}

	private static void FileUtilTester() {
		//		System.out.println(FileUtil.getFiles("C:/Users/shin/Desktop/ApiCaller/"));
//		System.out.println("시작");		
//		//		System.out.println(FileUtil.getFiles("C:/Users/shin/Desktop/ApiCaller"));
//		List<File> fileNames = FileUtil.getFiles("D:/workspace/QuickpleKeywordExtractor/backup");
//		fileNames = FileUtil.getFiles("D:/workspace/QuickpleKeywordExtractor/backup");
//		int i=0;
//		for (File string : fileNames) {
//			System.out.println(i+++" : "+string);
//		}
//		System.out.println("끝");	
	}

	private static void BDBTester() {
		String filepath = "/Users/shin285/Documents/workspace/Web_SentenceSpliterTool/WebContent/";
//		
//		BDBManager<String,Integer> cursorDB = new BDBManager<String, Integer>();
//		cursorDB.setDBConfig(filepath+"sentenceDB","cursor", false,true);	
//		cursorDB.open();
//		System.out.println(cursorDB.get("shin285last"));
//		cursorDB.close();
	}

	private static void APICallerTester() {
		//		String url = "http://121.162.245.162:48085/subsearch/search?q=%E3%85%8B%E3%85%8B&count=1000";
		String url = "http://api.quickple.com/3/link/recently_news?count=100";
		String url2 = "http://api.quickple.com/3/link/news?count=100";
		ApiCaller caller = new ApiCaller();
		RequestResult result = caller.requestGET(url);
		RequestResult result2 = caller.requestGET(url2);
		try {
			JSONArray recentlyJSON = result.toJSONObject().getJSONArray("news");
			JSONArray originalJSON = result2.toJSONObject().getJSONArray("news");
			Map<String,Integer> recentlyMap = new HashMap<String,Integer>();
			for(int i=0;i<recentlyJSON.length();i++){
				recentlyMap.put(recentlyJSON.getJSONObject(i).toString(), i);
			}

			MapUtil.printMap(recentlyMap);

			int match = 0;

			for(int i=0;i<originalJSON.length();i++){				
				if(recentlyMap.get((String) originalJSON.getJSONObject(i).get("title")) != null){
					match++;
				}else{

				}
			}

			System.out.println(match);
			//			System.out.println(result.toJSONObject().getJSONArray("news").getJSONObject(0).get("title"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void MapUtilTester() {
		//		Map<String,String> strMap = new HashMap<String,String>();
		//		strMap.put("첫번째", "감");
		//		strMap.put("두번째", "기");
		//		strMap.put("세번째", "는");
		//		strMap.put("네번째", "자");
		//		MapUtil.printMap(strMap);
		//		
		//		System.out.println(MapUtil.getKeys(strMap));
		//		System.out.println();
		//		
		//		List<Pair<String, String>> pairList = MapUtil.getListPair(strMap);
		//		for (Pair<?, ?> pair : pairList) {
		//			System.out.println(pair.getFirst());
		//			System.out.println(pair.getSecond());
		//		}
		//		
		//		List<Pair<String, String>> pairList2 = MapUtil.getListPair(MapUtil.sortByValue(strMap, MapUtil.ASCENDING_ORDER));
		//		for (Pair<?, ?> pair : pairList2) {
		//			System.out.println(pair.getFirst());
		//			System.out.println(pair.getSecond());
		//		}		
		Map<String,Double> strMap = new HashMap<String,Double>();
		strMap.put("하나", -0.1);
		strMap.put("둘", 0.4);
		strMap.put("셋", 0.2);
		strMap.put("넷1", 0.3);
		strMap.put("넷2", 0.3);
		strMap.put("넷3", 0.3);
		strMap.put("다섯", 0.5);
		//		MapUtil.printMap(MapUtil.scalingDoubleMap(strMap));
		MapUtil.printMap(MapUtil.getRankByDoubleValue(strMap));
	}

}
