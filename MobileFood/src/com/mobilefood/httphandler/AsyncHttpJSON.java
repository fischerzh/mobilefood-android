package com.mobilefood.httphandler;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpJSON extends AsyncTask<Void, Integer, JSONObject>{

	
	private JSONObject result = null;
	private String url;
	private JSONObject json;

	private Context context;
	
	ProgressDialog dialog;
	
	private final String TAG = "AsyncHttpJSON";
	
	public AsyncHttpJSON(Activity act, String url, JSONObject json)
	{
		setUrl(url);
		setJson(json);
		setContext(act);
		dialog = new ProgressDialog(getContext());
	}
	

	@Override
	protected JSONObject doInBackground(Void... params) {
		
		publishProgress(0);
		//prepare post parameters
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put("JSON", getJson().toString());
		
		Log.d(TAG, "JSON: " + getJson().toString());
		
		//query
		setResult(HttpRequestHandler.getPostResponse(getUrl(), postParams));
		publishProgress(100);
		return getResult();
	}
	
	@Override
	protected void onProgressUpdate(Integer... value) {

		super.onProgressUpdate();
		
		
		if(value[0]<100)
		{
			dialog = ProgressDialog.show(getContext(),"Please wait...", "Request in progress", true);
		}
		else
		{
			dialog.dismiss();
		}
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
	}

	
	
	private JSONObject getResult() {
		return result;
	}

	private void setResult(JSONObject result) {
		this.result = result;
	}

	private String getUrl() {
		return url;
	}

	private JSONObject getJson() {
		return json;
	}

	private void setUrl(String url)
	{
		this.url = url;
	}
	
	private void setJson(JSONObject json)
	{
		this.json = json;
	}

	private Context getContext() {
		return context;
	}

	private void setContext(Context context) {
		this.context = context;
	}


}
