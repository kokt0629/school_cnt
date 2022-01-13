package kr.peopleware.util.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class SettingProperties {
	static SettingProperties settingProperties = null;
	static Properties properties = null;	
	
	private SettingProperties(String filename) throws FileNotFoundException, IOException {
		properties = new Properties();										     
		properties.load(new FileInputStream(filename));		
	}
	
    public static SettingProperties getInstance(String filename) {
    	try {
			if (settingProperties == null) {
				settingProperties = new SettingProperties(filename);
			}
			return settingProperties;
    	}
    	catch(Exception e) {
    		
    		System.out.println(e.toString());
    		return null;
    	}
	}
    public String[] getKeys(){
    	String[] keys = new String[properties.keySet().size()];    	
		return properties.keySet().toArray(keys);
    }
    
    public Set<Entry<Object, Object>> getEntrySet(){
    	return properties.entrySet();    	
    }
    
    public int getIntProperty(String propertyName) {
    	return (Integer.parseInt(properties.getProperty(propertyName)));
    }

    public String getProperty(String propertyName) {
    	return (properties.getProperty(propertyName));
    }
}