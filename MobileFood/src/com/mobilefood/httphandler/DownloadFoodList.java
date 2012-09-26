package com.mobilefood.httphandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.widget.TextView;

public class DownloadFoodList extends AsyncTask<String, Void, String> {
	
	private TextView textView;
	private String webUrl = "http://www.uitiwg.ch/products.json";
	
    @Override
    protected String doInBackground(String... urls) {
      System.out.println("doInBackground: " + urls);
      String response = "";
      for (String url : urls) {
    	  
    	  response += HttpJsonHandler.getJSON(url);
    	  response += HttpJsonHandler.getJSON(webUrl);
    	  
      }
      return response;
//      for (String url : urls) {
//        DefaultHttpClient client = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(webUrl);
//        try {
//          HttpResponse execute = client.execute(httpGet);
//          InputStream content = execute.getEntity().getContent();
//
//          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
//          String s = "";
//          while ((s = buffer.readLine()) != null) {
//            response += s;
//          }
//
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//      }
//      return response;
    }

    @Override
    protected void onPostExecute(String result) {
    	System.out.println("onPostExecute: " + result);
      textView.setText(result);
    }
  }