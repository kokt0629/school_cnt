package kr.peopleware.util.object;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtil {
	
	public static Object readObject(String filename) {
		ObjectInputStream is = null;
		Object obj = null;
		try {
			is = new ObjectInputStream(new FileInputStream(filename));
			obj = is.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}
	public static void writeObject(String filename,Object obj){
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(filename));
			os.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();		
		} finally {
			try {
				if(os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
