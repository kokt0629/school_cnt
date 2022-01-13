package kr.peopleware.util.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import kr.peopleware.util.web.data.RequestResult;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApiCaller {
	public static final String APPLICATION_JSON = "application/json";	
	
	public RequestResult requestGET(String url) {
		HttpGet get = new HttpGet(url);		
		return request(get);
	}
	
	public RequestResult requestGET(String url, Map<String, String> header) {
		HttpGet get = new HttpGet(url);
		makeHeader(get, header);
		
		return request(get);
	}

	public RequestResult requestGET(URI uri) {
		HttpGet get = new HttpGet(uri);
		
		return request(get);
	}
	
	public RequestResult requestGET(URI uri, Map<String, String> header) {
		HttpGet get = new HttpGet(uri);
		makeHeader(get, header);
		
		return request(get);
	}
	
	public RequestResult requestPOST(String url, String content, String contentType) {
		RequestResult result = null;
		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", contentType);
			post.setEntity(new StringEntity(content, "UTF-8"));
			result = request(post);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public RequestResult requestPOST(String url, String content, String contentType, Map<String, String> header) {
		RequestResult result = null;
		try {
			HttpPost post = new HttpPost(url);
			makeHeader(post, header);
			post.setHeader("Content-Type", contentType);
			post.setEntity(new StringEntity(content, "UTF-8"));
			
//			System.out.println(post.getEntity());
			result = request(post);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	private RequestResult request(HttpUriRequest request) {		
		RequestResult result = null;
		
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
				result = new RequestResult(data.toString());
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
//				System.err.println("셧다운");
//				client.getConnectionManager().shutdown();				
			}
		}
		client.getConnectionManager().shutdown();		
		return result;
	}
	
	private void makeHeader(HttpUriRequest request, Map<String, String> header) {
		for(Map.Entry<String, String> entry : header.entrySet()) { 
			request.addHeader(entry.getKey(), entry.getValue());
		}
	}
}
