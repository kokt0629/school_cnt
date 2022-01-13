package kr.peopleware.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.peopleware.util.collection.MapUtil;
import kr.peopleware.util.collection.data.Pair;

/**
 * 루트 파일 하위에 있는 모든 파일 리스트를 가져옴( 디렉토리 제외 )
 * @author shin
 *
 */
public class FileUtil {
		
	private FileUtil(){
		;
	}
	/**
	 * rootPath 밑의 하위 파일(디렉토리 제외)을 List<File> 형태로 반환
	 * @param rootPath
	 * @return
	 */
	public static List<File> getFiles(String rootPath){
		List<File> fileNames = new ArrayList<File>();

		File f = new File(rootPath);
		if(f.isDirectory()){
			File[] files = f.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].isFile()){
					fileNames.add(files[i]);
				}else{
					fileNames.addAll(getFiles(files[i].getAbsolutePath()));
				}
			}
		}else{
			fileNames.add(f);
		}
		return fileNames;
	}

	/**
	 * rootPath 밑의 하위 파일(디렉토리 제외)을 List<String> 형태로 반환
	 * @param rootPath
	 * @return
	 */
	public static List<String> getFileNames(String rootPath) {
		List<String> fileNames = new ArrayList<String>();

		File f = new File(rootPath);
		if(f.isDirectory()){
			File[] files = f.listFiles();
			for(int i=0;i<files.length;i++){				
				if(files[i].isFile()){
					fileNames.add(files[i].getAbsolutePath());
				}else{
					fileNames.addAll(getFileNames(files[i].getAbsolutePath()));
				}
			}			
		}else{
			fileNames.add(f.getAbsolutePath());
		}
		return fileNames;
	}	
	public static String load2Memory(String filename) throws Exception{
		StringBuffer sb = new StringBuffer();
		File file = new File(filename);
		if(!file.exists() || !file.isFile()){
			return null;
		}
		BufferedReader br =new BufferedReader(new FileReader(file));
		char[] doc = new char[(int) file.length()];
		br.read(doc);
		sb.append(doc);		
		br.close();
		return sb.toString();
	}
	public static List<String> load2List(String filename) throws Exception{
		List<String> lines = new ArrayList<String>();
		File file = new File(filename);		
		if(!file.exists() || !file.isFile()){
			return null;
		}
		BufferedReader br =new BufferedReader(new FileReader(file));
		String line = null;
		while( (line = br.readLine()) != null){
			lines.add(line);
		}
		br.close();
		return lines;
	}
	/**
	 * 입력된 파일의 내용을 라인별 List 형태로 반환
	 * @param filename 읽어들일 파일 이름
	 * @param encoding 읽어들일 파일의 인코딩
	 * @return 파일의 한라인씩 순차적으로 저장된 리스트
	 */
	public static List<String> load2List(String filename,String encoding){		  
		BufferedReader br;
		List<String> resultList = new ArrayList<String>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), encoding));
			String line;
			while ((line = br.readLine()) != null) {
				resultList.add(line);
			}
			br.close();		
		} catch (Exception e) {
			e.printStackTrace();
		} 
		 
		return resultList;
	}

	public static <K, V> void writeMap2File(String filename,Map<K,V> map){

		writeMap2File(filename, map, map.size());
	}
	public static <K, V> void writeMap2File(String filename,Map<K,V> map,int writeCount){
		BufferedWriter bw = null;		
		try {
			bw = new BufferedWriter(new FileWriter(filename));
			List<Pair<K,V>> listPair = MapUtil.getListPair(map);
			int i=0;
			for (Pair<K, V> pair : listPair) {
				bw.write(pair.getFirst().toString()+"\t"+pair.getSecond().toString());
				bw.newLine();
				i++;
				if(i >= writeCount){
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bw != null){
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
