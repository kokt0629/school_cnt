package kr.peopleware.lucene.index.test;

import java.util.ArrayList;
import java.util.List;

import kr.peopleware.lucene.index.Indexer;
import kr.peopleware.lucene.index.properties.PropertiesManager;
import kr.peopleware.lucene.model.School;
import kr.peopleware.util.file.FileUtil;

import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.util.Version;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class IndexTester2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		//설정 파일 경로
		PropertiesManager pm = new PropertiesManager("index.properties");
		
		//Analyzer 및 설정 파일 경로를 초기화하는 Indexer 생성
		Indexer indexer = new Indexer(new CJKAnalyzer(Version.LUCENE_36),"index.properties");
		
		//설정 파일의 TARGET_PATH로부터 데이터를 가져옴
		List<School> schoolList = getSchools(pm.getProperty("TARGET_PATH"));		
		
		//색인
		for (School school : schoolList ) {
			indexer.index(school);
		}
		
		//커밋
		indexer.commit();
		indexer.close();
	}

	private static List<School> getSchools(String path) {
		List<School> schoolList = new ArrayList<School>();
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
			for (String line : lines) {
				//School t = convertSchool((DBObject) JSON.parse(line));	
				line = line.replaceAll(" ", "");
				School school = new School();		
				school.setContents(line);
				School t = school;  
				schoolList.add(t);
				/*
				int target_num1 = line.indexOf("중학교");	
				int target_num2 = line.indexOf("고등학교");	
				int target_num3 = line.indexOf("초등학교");
				int target_num4 = line.indexOf("대학교");
				String school_txt = "NONE";
				String[] arr = {};
				line = line.replaceAll(" ", "").replaceAll("\t", "").replaceAll("\"", "");
				if(target_num1 != -1) {
					arr = line.substring(0, target_num1+3).split(",");
					school_txt = arr[arr.length - 1];
				} else if(target_num2 != -1) {
					arr = line.substring(0, target_num2+4).split(",");
					school_txt = arr[arr.length - 1];
				} else if(target_num3 != -1) {
					arr = line.substring(0, target_num3+4).split(",");
					school_txt = arr[arr.length - 1];
				} else if(target_num4 != -1) {
					arr = line.substring(0, target_num4+3).split(",");
					school_txt = arr[arr.length - 1];
				}
				System.out.println("school_txt : " + school_txt);
				School t = convertSchool(line, school_txt);
				*/
			}
			//			System.out.println(filename);
		}
		return schoolList;
	}
	private static School convertSchool(String obj, String school_txt) {		
		School school = new School();
		school.setSchool(school_txt);
		school.setContents(obj);

		return school;
	}

}
