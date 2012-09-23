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
import java.util.Iterator;
import java.util.List;

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


import com.mobilefood.classes.Products;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HttpJsonHandler {
	
	private static String filePath = "file:///android_asset/products.json";
	
	public static JSONObject getJSON(String url)
	{
		if ( Changed(url) ) 
		{
			System.out.println("File has changed");
			return getJSONfromURL(url);
		} 
		else
		{
			System.out.println("File not changed");
			return getJSONfromFile(filePath);
		}
	}
	
    public void parseJSONtoObj(String url) {
                
        InputStream source = retrieveStream(url);
        
        Gson gson = new Gson();
        
        Reader reader = new InputStreamReader(source);
        System.out.println("reader: " + reader);
        Products product = gson.fromJson(reader, Products.class);
        
        //Toast.makeText(Context, (CharSequence)product.name, Toast.LENGTH_SHORT).show();
        System.out.println("Product: " + product.name);
        
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

		try {
			JsonReader reader = new JsonReader(new FileReader(fileName));
		 
			reader.beginObject();
		 
			while (reader.hasNext()) {
		 
			  String name = reader.nextName();
		 
			  if (name.equals("name")) {
		 
				System.out.println(reader.nextString());
		 
			  } else if (name.equals("age")) {
		 
				System.out.println(reader.nextInt());
		 
			  } else if (name.equals("message")) {
		 
				// read array
				reader.beginArray();
		 
				while (reader.hasNext()) {
					System.out.println(reader.nextString());
				}
		 
				reader.endArray();
		 
			  } else {
				reader.skipValue(); //avoid some unhandle events
			  }
		        }
		 
			reader.endObject();
			reader.close();
		 
		     } catch (FileNotFoundException e) {
			e.printStackTrace();
		     } catch (IOException e) {
			e.printStackTrace();
		     }
		
		
		//read from file
//		try{
//			Gson gson = new Gson();
////			BufferedReader reader = new BufferedReader(new FileReader(fileName));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//				System.out.println("line: " + line);
//			}
//			is.close();
//			result=sb.toString();
//			Products obj = gson.fromJson(reader, Products.class);
//			 
//			System.out.println(obj);
//		}catch(Exception e){
//			Log.e("log_tag", "Error converting result "+e.toString());
//		}

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

}
