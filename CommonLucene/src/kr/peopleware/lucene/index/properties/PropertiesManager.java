package kr.peopleware.lucene.index.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
	private Properties properties;
	private String propertiesName;
	public PropertiesManager(){
		properties = new Properties();
		this.refresh();
	}
	public PropertiesManager(String filename){
		properties = new Properties();
		propertiesName = filename;		
		this.refresh();
	}
	
	public void refresh(){
		try {
			properties.load(new FileInputStream(propertiesName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getProperty(String propertyName){
		return (properties.getProperty(propertyName));
	}
    public Integer getIntProperty(String propertyName) {
    	return (Integer.parseInt(properties.getProperty(propertyName)));
    }
    public Boolean getBooleanProperty(String propertyName){
    	return (Boolean.parseBoolean(properties.getProperty(propertyName)));
    }
    public String getPropertiesName(){
    	return propertiesName;
    }
}
