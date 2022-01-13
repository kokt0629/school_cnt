package kr.peopleware.lucene.index;

import java.io.File;
import java.io.IOException;

import kr.peopleware.lucene.index.properties.PropertiesManager;
import kr.peopleware.lucene.model.BaseModel;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
	private static PropertiesManager propertiesManager;
	private static IndexWriter writer = null;	
	public Indexer(){ 
		;
	}
	public Indexer(Analyzer analyzer,String propertiesFileName){
		setPropertiesFile(propertiesFileName);
		init(analyzer);
	}
	public void init(Analyzer analyzer){
		if(propertiesManager == null){
			System.err.println("Properties are null. Please check properties filename.");			
		}else{
			Directory dir = null;
			try {
				dir = FSDirectory.open(new File(propertiesManager.getProperty("INDEX_PATH")));			
				IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_36, analyzer);			
				iwc.setOpenMode(getOpenMode());

				//Set merge policy
				if(propertiesManager.getBooleanProperty("MERGE_POLICY")){
					TieredMergePolicy tmp = new TieredMergePolicy();
					Integer maxMergeAtOnce = propertiesManager.getIntProperty("MAX_MERGE_AT_ONCE");
					Integer segmentsPerTier = propertiesManager.getIntProperty("SEGMENTS_PER_TIER");
					if(maxMergeAtOnce != null){
						tmp.setMaxMergeAtOnce(maxMergeAtOnce);
					}
					if(segmentsPerTier != null){
						tmp.setSegmentsPerTier(segmentsPerTier);
					}				
					iwc.setMergePolicy(tmp);
				}

				writer = new IndexWriter(dir,iwc);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	private OpenMode getOpenMode() {
		String openMode = propertiesManager.getProperty("OPEN_MODE");
		if(openMode.toUpperCase().equals("create".toUpperCase())){
			return OpenMode.CREATE;
		}
		else if(openMode.toUpperCase().equals("append".toUpperCase())){
			return OpenMode.APPEND;
		}
		else if(openMode.toUpperCase().equals("create_or_append".toUpperCase())){
			return OpenMode.CREATE_OR_APPEND;
		}else{
			System.err.println("Please check OPEN_MODE in propertyfile["+propertiesManager.getPropertiesName()+"]");
			return null;
		}
	}
	public void setPropertiesFile(String filename){
		propertiesManager = new PropertiesManager(filename);
	}
	public void index(BaseModel model){
		try {			
			writer.addDocument(model.convetDocument());
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			writer.close(true);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void commit(){
		try {
			writer.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void remove(Query q){		
		try {
			writer.deleteDocuments(q);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
}
