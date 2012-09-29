package com.mobilefood.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mobilefood.classes.TwitterTrend;
import com.mobilefood.classes.TwitterTrends;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LoadJSON extends AsyncTask<Context, Integer, String> {
    
	ProgressDialog dialog ;
    
    private Context cont;
    private String jsonURL;
    
    private JSONObject jsonObj;
    
    /*
     * Public Constructor
     */
    public LoadJSON(Activity act, String url)
    {
		setUrl(url);
		setContext(act);
		dialog = new ProgressDialog(getContext());
    }
    
	private void setContext(Activity act) {
		this.cont = act;
	}
	
	private Context getContext()
	{
		return this.cont;
	}

	private void setUrl(String url) {
		this.jsonURL = url;
	}
	
	private String getUrl()
	{
		return this.jsonURL;
	}

	
	/*
	 * Start JSON download
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Context... params) {
		// Check if file has Changed
		System.out.println("Start JSON Loader");
		publishProgress(0);
		
		//Only for temporary use!
		//setJsonObj(getJSONObjectFromURL(jsonURL));
		runJSONParser();
		publishProgress(100);
		return "JSON Object ready";
		
		// TODO USE THIS FOR LATER!!
		
//		if ( Changed(jsonURL) ) 
//		{
//			System.out.println("File has changed");
//			
//			//Load the newest JSON file
//			setJsonObj(getJSONObjectFromURL(jsonURL));
//			return "JSON Object ready..";
//		} 
//		else
//		{
//			System.out.println("File not changed");
//			return "JSON Object not changed";
//		}
	}

    
	private JSONObject getJSONObjectFromURL(String url)
	{	
		//JSON Object to be returned;
		JSONObject json = null;
		
		System.out.println("get Json from URL");
		HttpClient httpclient = new DefaultHttpClient();
		 
        // Prepare a request object
        HttpGet httpget = new HttpGet(url); 
 
        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("Response Status",response.getStatusLine().toString());
 
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
 
            if (entity != null) {
 
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                Log.i("Result",result);
 
                // A Simple JSONObject Creation
                json=new JSONObject(result);
                Log.i("JSON Object","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

 
                // Closing the input stream will trigger connection release
                instream.close();
            }

 
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return json;
	}
	
	public void runJSONParser()
	{
		try{
	        System.out.println("Json Parser started..");
	        Gson gson = new Gson();
	        Reader r = new InputStreamReader(getJSONData("https://api.twitter.com/1/trends/1.json"));
	        TwitterTrends objs = gson.fromJson(r, TwitterTrends.class);
	        Log.i("MY INFO", ""+objs.getTrends().size());
	        for(TwitterTrend tr : objs.getTrends()){
	            Log.i("TRENDS", tr.getName() + " - " + tr.getUrl());
	        }
	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	}
	
	public InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return data;
    }
	
	
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
	
	private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
	
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

	/**
	 * @return the jsonObj
	 */
	public JSONObject getJsonObj() {
		return jsonObj;
	}

	/**
	 * @param jsonObj the jsonObj to set
	 */
	private void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

 }
