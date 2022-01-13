package kr.peopleware.util.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class WebCaller {
	public String request(String url) {		
		HttpGet request = new HttpGet(url);
		String result = null;
		
		HttpClient client = new DefaultHttpClient();		
		HttpEntity resEntity = null;
		HttpResponse response = null;
		try {			
			response = client.execute(request);
			resEntity = response.getEntity();
			
			if(resEntity != null) {
				Header encodingHeader = response.getFirstHeader("Content-Encoding");
				
				int len;
				byte[] tmp = new byte[2048];
				StringBuilder data = new StringBuilder();
				if(encodingHeader != null && encodingHeader.getValue().equals("gzip")) {
//					System.out.println("use gzip");
					GZIPInputStream gzipIs = new GZIPInputStream(resEntity.getContent());
					while((len = gzipIs.read(tmp)) != -1) {
						data.append(new String(tmp, 0, len, "UTF-8"));
					}
					gzipIs.close();
				} else {
//					System.out.println("normal");
					InputStream is = resEntity.getContent();
					while((len = is.read(tmp)) != -1) {
						data.append(new String(tmp, 0, len, "UTF-8"));
					}
					is.close();
				}
				result = data.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			client.getConnectionManager().shutdown();
			return null;			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			client.getConnectionManager().shutdown();
			return null;
		} finally {
			if(client != null)
			{				
				client.getConnectionManager().shutdown();				
			}
		}
		client.getConnectionManager().shutdown();		
		return result;
	}
}
