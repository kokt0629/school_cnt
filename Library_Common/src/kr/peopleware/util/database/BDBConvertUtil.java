package kr.peopleware.util.database;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BDBConvertUtil {
	public static Object BytestoObject(byte[] bytes){

		ByteArrayInputStream resultStream = new ByteArrayInputStream(bytes);
		ObjectInput in;
		Object readObj = null;
		try {
			in = new ObjectInputStream(resultStream);
			readObj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}				
		return readObj;
	}
	public static byte[] ObjecttoBytes(Object obj){
		ObjectOutputStream os = null;
		byte[] resultBytes = null;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
		try {
			os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
			os.flush();
			os.writeObject(obj);
			os.flush();
			resultBytes = byteStream.toByteArray();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return resultBytes;
	}
}
