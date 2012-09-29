package com.mobilefood.httphandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import com.mobilefood.classes.JSONResponse;
import com.mobilefood.classes.Products;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HttpJsonHandler extends AsyncTask<String, Void, String>{
	
	private static final String TAG = "MobileFood";
	private static String filePath = "file:///android_asset/products.json";
	private String webURL = "http://www.uitiwg.ch/products.json";
	private Activity act;
	private JSONObject result = null;
	private String url;
	private JSONObject jsonObj;
	private List<Products> product_list;
	
	public HttpJsonHandler() {
		
	}
	
	public HttpJsonHandler(Activity act, String url, JSONObject json) 
	{
			this.act = act;
			this.url = url;
			this.jsonObj = json;
			
			// TODO Auto-generated constructor stub
	}
	
	public static JSONObject getJSON(String url)
	{
		return getJSONfromURL(url);
//		if ( Changed(url) ) 
//		{
//			System.out.println("File has changed");
//			return getJSONfromURL(url);
//		} 
//		else
//		{
//			System.out.println("File not changed");
//			return getJSONfromFile(filePath);
//		}
	}
	
    public void parseJSONtoObj(String url) {
                        
        Gson gson = new Gson();

        JSONResponse response = null;
        Products prod = null;

        prod = gson.fromJson(getJSON(url).toString(), Products.class);
        System.out.println("Products scanned: " + prod);
        System.out.println("Name: " + prod.getName());
//        if (prod != null)
//        {
//        	for (int i = 0; i < prod.size(); i++) {
//        		Products prods;
//        		prods = response.products.get(i);
//        		product_list.add(prods); //Adding each post to the list
//        		System.out.println("Product added: " + prods.getName());
//                //Toast.makeText(Context, (CharSequence)prod.getName(), Toast.LENGTH_SHORT).show();
//
//        	}
//        }

        
    }
    
    
	public static JSONObject getJSONfromURL(String url){

		//initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		
		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		//try parse the string to a JSON object
		try{
	        	jArray = new JSONObject(result);
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}

		return jArray;
	}
	
	public static JSONObject getJSONfromFile(String fileName){

		//initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;
		//read from file
		try{
			Gson gson = new Gson();
//			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				System.out.println("line: " + line);
			}
			is.close();
			result=sb.toString();
			Products obj = gson.fromJson(reader, Products.class);
			 
			System.out.println(obj);
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		return jArray;
	}
	
	// Using HTTP_NOT_MODIFIED
	private static boolean Changed(String url){
	    try {
	      HttpURLConnection.setFollowRedirects(false);
	      HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
	      con.setRequestMethod("HEAD");
	      return (con.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED);
	    }
	    catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
	}
	
	private InputStream retrieveStream(String url) {
        
        DefaultHttpClient client = new DefaultHttpClient(); 
        
        HttpGet getRequest = new HttpGet(url);
          
        try {
           
           HttpResponse getResponse = client.execute(getRequest);
           final int statusCode = getResponse.getStatusLine().getStatusCode();
           
           if (statusCode != HttpStatus.SC_OK) { 
              Log.w(getClass().getSimpleName(), 
                  "Error " + statusCode + " for URL " + url); 
              return null;
           }

           HttpEntity getResponseEntity = getResponse.getEntity();
           return getResponseEntity.getContent();
           
        } 
        catch (IOException e) {
           getRequest.abort();
           Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }
        
        return null;
        
     }

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		parseJSONtoObj(this.webURL);
		return "Parse done";
	}
	
    @Override
    protected void onPostExecute(String result) {
    	System.out.println(result);
    }
    
	protected void onProgressUpdate(Integer... value) {

		super.onProgressUpdate();
		
	}

}
