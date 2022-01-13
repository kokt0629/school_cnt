package kr.peopleware.util.string;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.peopleware.util.collection.data.Pair;
import kr.peopleware.util.model.Jaso;
import kr.peopleware.util.model.Syllable;

public class StringUtil {

	public static char[] ChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
		0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
		0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
	public static char[] JungSung = { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153,
		0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b,
		0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163 };
	public static char[] JongSung = { 0x0000, 0x3131, 0x3132, 0x3133, 0x3134,
		0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d,
		0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146,
		0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

	public static int JAMO = -1,KOREAN = 0,ENGLISH = 1,NUMBER = 2, SPECIAL = 3, SL=4; 

	/**
	 * 해당 문자열 내에 한글이 포함되어 있는지 여부
	 * @param str
	 * @return
	 */
	public static boolean hasKorean(String str){
		int i=0,length = str.length();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if(UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock))
				return true;					
		}
		return false;
	}	

	/**
	 * 해당 문자열 내에 완성형 한글이 포함되어 있는지 여부
	 * 완성형 한글 : 자음과 모음이 결합된 형태 (ex : 강, 가, 나 not ㄱ, ㅏ,ㄷ)
	 * @param str
	 * @return
	 */
	public static boolean hasKoreanSyllable(String str){
		int i=0,length = str.length();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if(UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 해당 문자열 내에 한글 자소가 포함되어 있는지 여부
	 * 한글 자소 : ㅋ,ㄱ,ㅇ,ㅏ,ㅑ,ㅠ,ㅜ..
	 * @param str
	 * @return
	 */
	public static boolean hasKoreanCompatibilityJamo(String str){
		int i=0,length = str.length();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if(UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 해당 문자열 내에 특수 문자형 한글 자소가 포함되어 있는지 여부
	 * 특수 문자형 한글 자소 : ㄱ,ㄴ (ㅋ+한자 키 조합으로 생성되는 문자들)
	 * @param str
	 * @return
	 */
	@Deprecated
	public static boolean hasKoreanJamo(String str){
		int i=0,length = str.length();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);			
			if(UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 해당 문자열 내에 숫자가 포함되어 있는지 여부
	 * @param str
	 * @return
	 */
	public static boolean hasNumeric(String str){
		int i=0,length = str.length();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);						
			if(Character.isDigit(ch)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 해당 문자열이 한글로만 구성되어 있는지 여부
	 * 한글은 완성형, 자소, 자소 특수형 모두 해당
	 * @param str
	 * @return
	 */
	public static boolean isOnlyKorean(String str){
		int i=0,length = str.length();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);			
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if(!(UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock))){
				return false;
			}
		}
		return true;
	}

	/**
	 * 해당 문자열이 숫자로만 구성되어 있는지 여부
	 * @param str
	 * @return
	 */
	public static boolean isOnlyNumeric(String str){
		int i=0,length = str.length();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);						
			if(!(Character.isDigit(ch))){
				return false;
			}
		}
		return true;
	}

	/**
	 * 해당 문자가 한글인지 여부
	 * 완성형, 자소, 자소 특수형 모두 해당
	 * @param ch
	 * @return
	 */
	public static boolean isKorean(Character ch){
		Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
		if(UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
				UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
				UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)){
			return true;
		}
		return false;
	}

	/**
	 * 해당 문자가 한글 완성형인지 여부
	 * 완성형 한글 : 자음과 모음이 결합된 형태 (ex : 강, 가, 나 not ㄱ, ㅏ,ㄷ)
	 * @param ch
	 * @return
	 */
	public static boolean isKoreanSyllable(Character ch){
		Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
		if(UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock)){
			return true;
		}
		return false;
	}

	/**
	 * 해당 문자가 한글 자소형인지 여부
	 * 한글 자소 : ㅋ,ㄱ,ㅇ,ㅏ,ㅑ,ㅠ,ㅜ..
	 * @param ch
	 * @return
	 */
	public static boolean isKoreanCompatibilityJamo(Character ch){
		Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
		if(UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock)){
			return true;
		}
		return false;
	}

	/**
	 * 해당 문자가 한글 자소 특수형인지 여부
	 * 특수 문자형 한글 자소 : ㄱ,ㄴ (ㅋ+한자 키 조합으로 생성되는 문자들)
	 * @param ch
	 * @return
	 */
	@Deprecated
	public static boolean isKoreanJamo(Character ch){
		Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
		if(UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)){
			return true;
		}
		return false;
	}


	/**
	 * 해당 문자열 내 한글만 추출
	 * 한글은 완성형, 자소형, 자소 특수형 모두 포함
	 * @param str
	 * @param includeWhitespace 추출 시 공백 포함 여부
	 * @return
	 */
	public static String getOnlyKorean(String str,boolean includeWhitespace){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);			
			if(isKorean(ch) || (includeWhitespace && Character.isWhitespace(ch))){
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 해당 문자열 내 한글을 제외한 기타 문자 추출
	 * @param str
	 * @param includeWhitespace 추출 시 공백 포함 여부
	 * @return
	 */
	public static String getOnlyNotKorean(String str,boolean includeWhitespace){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();
		for(i=0;i<length;i++){
			char ch = str.charAt(i);
			if(!includeWhitespace && Character.isWhitespace(ch)){
				continue;
			}
			if(!isKorean(ch)){
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	/**
	 * 해당 문자열 내 완성형 한글만 추출
	 * @param str
	 * @param includeWhitespace 추출시 공백 포함 여부
	 * @return
	 */
	public static String getOnlyKoreanSyllable(String str,boolean includeWhitespace){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();

		for(i=0;i<length;i++){
			char ch = str.charAt(i);			
			if(isKoreanSyllable(ch) || (includeWhitespace && Character.isWhitespace(ch))){
				sb.append(ch);
			}
		}
		if(sb.length() == 0){
			return null;
		}
		return sb.toString();
	}

	/**
	 * 해당 문자열 내 자소형 한글만 추출 
	 * @param str
	 * @param includeWhitespace 추출시 공백 포함 여부
	 * @return
	 */
	public static String getOnlyKoreanCompatibilityJamo(String str,boolean includeWhitespace){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();

		for(i=0;i<length;i++){
			char ch = str.charAt(i);			
			if(isKoreanCompatibilityJamo(ch) || (includeWhitespace && Character.isWhitespace(ch))){
				sb.append(ch);
			}
		}
		if(sb.length() == 0){
			return null;
		}
		return sb.toString();
	}

	/**
	 * 해당 문자열 내 자소 특수형 한글만 추출
	 * @param str
	 * @param includeWhitespace 추출시 공백 포함 여부
	 * @return
	 */
	public static String getOnlyKoreanJamo(String str,boolean includeWhitespace){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();

		for(i=0;i<length;i++){
			char ch = str.charAt(i);			
			if(isKoreanJamo(ch) || (includeWhitespace && Character.isWhitespace(ch))){
				sb.append(ch);
			}
		}
		if(sb.length() == 0){
			return null;
		}
		return sb.toString();
	}

	/**
	 * 해당 문자열 내 숫자만 추출
	 * @param str
	 * @param includeWhitespace 추출시 공백 포함 여부
	 * @return
	 */
	public static String getOnlyNumeric(String str,boolean includeWhitespace){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();

		for(i=0;i<length;i++){
			char ch = str.charAt(i);
			if(Character.isDigit(ch) || (includeWhitespace && Character.isWhitespace(ch))){
				sb.append(ch);
			}
		}

		if(sb.length() == 0){
			return null;
		}
		return sb.toString();
	}

	@Deprecated
	/**
	 * 해당 문자열 내 중복된 공백문자는 하나의 공백문자로 치환
	 * 감기\t\t\n자주    걸린다. -> 감기 자주 걸린다.
	 * @param str
	 * @return
	 */	
	public static String reductionWhitespace(String str){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();		
		for(i=0;i<length;i++){
			char ch = str.charAt(i);
			if(sb.length() == 0){
				sb.append(ch);
			}else{
				char lastCh = sb.charAt(sb.length()-1);
				if(Character.isWhitespace(ch) && !Character.isWhitespace(lastCh)){
					sb.append(ch);
				}else if(!Character.isWhitespace(ch)){
					sb.append(ch);
				}
			}
		}		
		if(sb.length() == 0){
			return null;
		}
		return sb.toString().trim();
	}

	/**
	 * 해당 문자열 내 공백문자 모두 제거
	 * 감기\t\t\n자주    걸린다. -> 감기자주걸린다.
	 * @param str
	 * @return
	 */
	public static String removeWhitespace(String str){
		int i=0,length = str.length();
		StringBuffer sb = new StringBuffer();		
		for(i=0;i<length;i++){

			char ch = str.charAt(i);
			if(!Character.isWhitespace(ch) && ch != (char)0x00A0 && ch != (char)0x2007 && ch != (char)0x202F){
				//			if(!Character.isWhitespace(ch)){
				sb.append(ch);
			}
		}		
		if(sb.length() == 0){
			return null;
		}		
		return sb.toString().trim();
	}

	/**
	 * 해당 문자열 내 html 관련 태그를 모두 삭제(refine)
	 * @param str
	 * @return refined str
	 */
	public static String removaTag(String str){
		Matcher mat;		
		// script 처리 
		Pattern script = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);  
		mat = script.matcher(str);		
		str = mat.replaceAll("");  
		// style 처리
		Pattern style = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);  
		mat = style.matcher(str);  
		str = mat.replaceAll("");
		// tag 처리 
		Pattern tag = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");  
		mat = tag.matcher(str);  
		str = mat.replaceAll("");  
		// ntag 처리 
		Pattern ntag = Pattern.compile("<\\w+\\s+[^<]*\\s*>");  
		mat = ntag.matcher(str);  
		str = mat.replaceAll("");  
		// entity ref 처리
		Pattern Eentity = Pattern.compile("&[^;]+;");  
		mat = Eentity.matcher(str);  
		str = mat.replaceAll("");
		// whitespace 처리 
		Pattern wspace = Pattern.compile("\\s\\s+");  
		mat = wspace.matcher(str); 
		str = mat.replaceAll(""); 	          

		return str ;		
	}

	/**
	 * 문자열 양쪽 끝에 위치한 공백을 제거
	 * 자바 String에서 제공하는 trim에 추가적으로  non-breaking space도 제거
	 * @param str
	 * @return
	 */
	public static String trim(String str){

		int i=0,length = str.length();		
		int startIndex = 0,endIndex = 0;
		for(i=0;i<length;i++){			
			char ch = str.charAt(i);			
			if(!Character.isWhitespace(ch) && ch != (char)0x00A0 && ch != (char)0x2007 && ch != (char)0x202F){
				break;
				//				sb.append(ch);
			}
		}
		String result = null;
		startIndex = i;

		for(i=length-1;i>=startIndex;i--){
			char ch = str.charAt(i);
			if(!Character.isWhitespace(ch) && ch != (char)0x00A0 && ch != (char)0x2007 && ch != (char)0x202F){
				break;
			}
		}
		endIndex = i+1;		
		result = str.substring(startIndex, endIndex);		
		return result;
	}
	
	public static List<String> ngram(String str,int n){

		List<String> ngramList = new ArrayList<String>();
		for(int i=0;i<n-1;i++){
			str = "$"+str+"X";
		}
		for(int i=0;i<str.length();i++){
			if(i+n > str.length()){
				break;
			}
			StringBuilder sb = new StringBuilder();
			for(int j=i;j<i+n;j++){
				sb.append(str.charAt(j));
			}
			ngramList.add(sb.toString());
		}
		return ngramList;
	}

	public static int getJasoLength(char ch){

		int jong,tmp;
		if(ch >= 0xAC00 && ch <=0xD7A3){
			tmp = ch - 0xAC00;			
			tmp = tmp % (21*28);			
			jong = tmp % 28;			
			if(jong != 0){
				return 3;
			}else{
				return 2;
			}
		}
		return 0;
	}
	public static int getJasoLength(String ch){
		int length = 0;
		for(int i=0;i<ch.length();i++){
			length += StringUtil.getJasoLength(ch.charAt(i));
		}
		return length;
	}
	public static Jaso hangul2Jaso(char ch){
		int cho,jung,jong,tmp;
		if(ch >= 0xAC00 && ch <=0xD7A3){
			tmp = ch - 0xAC00;
			cho = tmp / (21*28);
			tmp = tmp % (21*28);			
			jung = tmp / 28;
			jong = tmp % 28;			
			

			//			System.out.println("초중 합친거 : "+(0xAC00+cho*588+jung*28));
			//			System.out.println("초 : "+ChoSung[cho]);
			//			System.out.println("중 : "+JungSung[jung]);
			if(jong != 0){
				//				System.out.println("종 : "+JongSung[jong]);
				return new Jaso(ChoSung[cho], cho,JungSung[jung], jung,JongSung[jong],jong);
			}else{
				return new Jaso(ChoSung[cho], cho,JungSung[jung],jung, 'x',jong);
			}			
		}
		return null;
	}
	public static Integer getJasoIndex(char ch,int type){
		int cho,jung,jong,tmp;
		if(ch >= 0xAC00 && ch <=0xD7A3){
			tmp = ch - 0xAC00;
			cho = tmp / (21*28);
			tmp = tmp % (21*28);			
			jung = tmp / 28;
			jong = tmp % 28;
			if(type == Syllable.CHOSUNG){
				return cho;
			}
			else if(type == Syllable.JUNGSUNG){
				return jung;
			}
			else if(type == Syllable.JONGSUNG){
				return jong;
			}
		}
		return null;
	}
	public static List<Syllable> getSyllableList(String str) {

		List<Syllable> tokenList = new ArrayList<Syllable>();
		int i=0,length = str.length();

		for(i=0;i<length;i++){			
			Jaso jaso = StringUtil.hangul2Jaso(str.charAt(i));
			if(jaso == null){
				tokenList.add(new Syllable(new String()+str.charAt(i), Syllable.ETC, -1));
				continue;
			}

			tokenList.add(new Syllable(jaso.getCho(),Syllable.CHOSUNG,jaso.getChoIndex()));
			tokenList.add(new Syllable(jaso.getJung(),Syllable.JUNGSUNG,jaso.getJungIndex()));
			if(!jaso.getJong().equals("x"))
				tokenList.add(new Syllable(jaso.getJong(), Syllable.JONGSUNG, jaso.getJongIndex()));			
		}
		return tokenList;
	}
	public static List<String> split(String str,String spliter){

		List<String> resultList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append(str);

		int prevIndex = 0;
		int nextIndex = 0;

		while(true){
			nextIndex = sb.indexOf(spliter, prevIndex);
			if(nextIndex == -1)
			{
				resultList.add(sb.substring(prevIndex));
				break;
			}			
			resultList.add(sb.substring(prevIndex, nextIndex));

			prevIndex = nextIndex+1;
		}
		return resultList;
	}
	public static String getCharacterType(char ch){
		Character.UnicodeBlock unicodeblock = Character.UnicodeBlock.of(ch);
		return unicodeblock.toString();
	}
	public static Character.UnicodeBlock getUnicodeType(char ch){
		Character.UnicodeBlock unicodeblock = Character.UnicodeBlock.of(ch);
		return unicodeblock;
	}
	
	public static List<Pair<String, Integer>> getEojeolType(String str) {
		List<Pair<Character,Integer>> typeList = new ArrayList<Pair<Character,Integer>>();
		for(int i=0;i<str.length();i++){
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeblock = Character.UnicodeBlock.of(ch);
			if(unicodeblock == Character.UnicodeBlock.BASIC_LATIN){
				if (((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z'))) {
					typeList.add(new Pair<Character, Integer>(ch, ENGLISH));
			    }else if ((ch >= '0') && (ch <= '9')) {
			    	typeList.add(new Pair<Character, Integer>(ch, NUMBER));
			    }else{
			    	typeList.add(new Pair<Character, Integer>(ch,SPECIAL));
			    }
			}else if(unicodeblock == Character.UnicodeBlock.HANGUL_SYLLABLES){
				typeList.add(new Pair<Character, Integer>(ch, KOREAN));			
			}else if(unicodeblock == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO){
				typeList.add(new Pair<Character, Integer>(ch, JAMO));
			}else if(unicodeblock == Character.UnicodeBlock.HIRAGANA ||
					unicodeblock == Character.UnicodeBlock.KATAKANA ||
					unicodeblock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
					unicodeblock == Character.UnicodeBlock.HIRAGANA){
				typeList.add(new Pair<Character, Integer>(ch, SL));
			}else{
				typeList.add(new Pair<Character, Integer>(ch, SPECIAL));
			}
		}
		int prevType = -2;
		String continousStr = "";
		
		List<Pair<String,Integer>> resultTypeList = new ArrayList<Pair<String,Integer>>();
		for (Pair<Character, Integer> pair : typeList) {
			
			int curType = pair.getSecond();
			
			if(prevType == -2 || curType == prevType){
				continousStr += pair.getFirst();
			}else{
				resultTypeList.add(new Pair<String,Integer>(continousStr,prevType));
				continousStr = ""+pair.getFirst();
			}
			
			prevType = curType;			
		}
		resultTypeList.add(new Pair<String,Integer>(continousStr,prevType));
		return resultTypeList;
	}
}
