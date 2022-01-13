package kr.peopleware.util.web.data;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class RequestResult {
	private String data;

	public RequestResult() {
		this.data = "";
	}
	
	public RequestResult(String data) {
		super();
		this.data = data;
	}
	
	public JSONObject toJSONObject() {
		JSONObject result = null;
		
		try {
			result = new JSONObject(data);
		} catch (JSONException e) { 
			e.printStackTrace();
		}		
		return result;
	}
	
	public JSONArray toJSONArray() {
		JSONArray result = null;
		
		try {
			result = new JSONArray(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String toString() {
		return data;
	}
	
	
}
