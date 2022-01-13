package kr.peopleware.util.database;

import java.io.File;

import kr.peopleware.util.database.interfaces.DatabaseManager;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Durability;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class BDBManager<K,V> implements DatabaseManager<K, V>{

	private Environment env;
	protected Database database;
	private EnvironmentConfig myEnvConfig = null;
	private DatabaseConfig myDbConfig=null;
	private String dbPath,dbName;
	private Cursor cursor = null;
	private boolean readOnly;

	public void setDBConfig(String dbPath, String dbName, boolean readOnly){		
		this.dbPath = dbPath;
		this.dbName = dbName;
		this.readOnly = readOnly;
	}
	public void setDBConfig(String dbPath, String dbName, boolean readOnly, boolean createDB){
		if(createDB){
			File f = new File(dbPath);
			if(!f.exists()){
				f.mkdirs();
			}
		}
		this.dbPath = dbPath;
		this.dbName = dbName;
		this.readOnly = readOnly;
	}
	public void openCursor(){
		if(cursor != null){
			cursor.close();
		}
		cursor = database.openCursor(null, null);
	}
	@SuppressWarnings("unchecked")
	public K getNextKey(){
		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundData = new DatabaseEntry();		
		while(cursor.getNext(foundKey, foundData, LockMode.DEFAULT) ==  OperationStatus.SUCCESS){
			return (K)BDBConvertUtil.BytestoObject(foundKey.getData());
		}		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public K getKey(int index){		
		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundData = new DatabaseEntry();
		cursor.getNext(foundKey, foundData, LockMode.READ_UNCOMMITTED);
		if(index == 0){
			return (K)BDBConvertUtil.BytestoObject(foundKey.getData());
		}
		cursor.getPrev(foundKey, foundData, LockMode.READ_UNCOMMITTED);
		if(index == cursor.skipNext(index, foundKey, foundData, LockMode.READ_UNCOMMITTED)){
			return (K)BDBConvertUtil.BytestoObject(foundKey.getData());
		}else{
			return null;
		}
	}

	@Override
	public void insert(K key, V value) {		
		DatabaseEntry keyEntry = new DatabaseEntry(BDBConvertUtil.ObjecttoBytes(key));
		DatabaseEntry valueEntry = new DatabaseEntry(BDBConvertUtil.ObjecttoBytes(value));
		database.put(null, keyEntry, valueEntry);
	}

	@Override
	public void delete(K key) {
		DatabaseEntry keyEntry = new DatabaseEntry(BDBConvertUtil.ObjecttoBytes(key));
		database.delete(null, keyEntry);
	}

	@Override
	public void update(K key, V value) {
		Object foundData = this.get(key);
		if(foundData == null){
			try{
				Exception e = new Exception("업데이트 할 key가 없습니다.");
				throw e;
			}catch(Exception e){				
				e.printStackTrace();
			}
		}
		else{
			this.insert(key, value);
		}
	}

	public void upsert(K key, V value) {
		this.insert(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {					
		DatabaseEntry keyEntry = new DatabaseEntry(BDBConvertUtil.ObjecttoBytes(key));
		DatabaseEntry dataEntry = new DatabaseEntry();
		// Perform the get.
		if (this.database.get(null, keyEntry, dataEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
			// Recreate the data String.
			return (V) BDBConvertUtil.BytestoObject(dataEntry.getData());
		} else {
			;
		}		
		return null;

	}

	@Override
	public void open() {
		myEnvConfig = new EnvironmentConfig();
		File inDir = new File (dbPath);

		myEnvConfig.setTransactional(true);
		myEnvConfig.setDurability(Durability.COMMIT_NO_SYNC);

		myEnvConfig.setReadOnly(readOnly);
		myEnvConfig.setAllowCreate(!readOnly);

		myDbConfig = new DatabaseConfig();

		myDbConfig.setReadOnly(readOnly);
		myDbConfig.setAllowCreate(!readOnly);
		myDbConfig.setTransactional(true);		

		this.env = new Environment(inDir, myEnvConfig);	
		this.database = this.env.openDatabase(null, dbName, myDbConfig);		
	}
	
	public void changeDB(String dbName){
		this.dbName = dbName;
		this.database = this.env.openDatabase(null, this.dbName, myDbConfig);
	}

	@Override
	public void close() {
		try {			
			if (cursor != null){
				this.cursor.close();
			}
			if (this.database != null) {
				this.database.close();
			}
			if (this.env != null) {
				this.env.sync();
				this.env.close();
			}			
		} catch (DatabaseException dbe) {
			//			logger.debug(dbe.getMessage());
			dbe.printStackTrace();
		}		
	}	
}
