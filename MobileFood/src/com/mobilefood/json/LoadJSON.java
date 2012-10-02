package com.mobilefood.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mobilefood.classes.Products;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.old.TwitterTrend;
import com.mobilefood.classes.old.TwitterTrends;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LoadJSON extends AsyncTask<Context, Integer, String> {
    
	/* UI Elements */
	ProgressDialog dialog ;
    private Context cont;
    
	/* Constants */
    private String jsonURL;
	private String FILENAME = "products.json";

    /* JSON and Java Objects */
    private JSONObject jsonObj;
    private List<Products> productsList;
	private List<TwitterTrends> messageList;
	
    /* Public Constructor */
    public LoadJSON(Activity act, String url)
    {
		setUrl(url);
		setContext(act);
		dialog = new ProgressDialog(getContext());
    }
    
    /* Getters and Setters */
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
	
	public List<Products> getProductsList() {
		return productsList;
	}


	public void setProductsList(List<Products> productsList) {
		this.productsList = productsList;
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
		
		try {
			
			setProductsList(readJsonStreamProducts());
			System.out.println(productsList.get(0).getProducts().get(0).getName());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		publishProgress(100);
		return "JSON Object ready";
		
		// TODO USE THIS FOR LATER!!
		
//		if ( Changed(jsonURL) ) 
//		{
//			System.out.println("File has changed");
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


	public InputStream getJSONDataFromURL(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
    		System.out.println("Start HTTP Download Request..");
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
		System.out.println("Finished HTTP Download Request..");

        return data;
    }
	
	public List<Products> readJsonStreamProducts() throws IOException 
	{	
		System.out.println("Json Stream reading..");
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(
				getJSONDataFromURL("http://www.uitiwg.ch/products.json"), "UTF-8")));
			// TODO getJSONDataFromFile or getJSONDataFromSharedPreferences!
        List<Products> products = new ArrayList<Products>();
        reader.beginArray();
        while (reader.hasNext()) {
        	Products product = gson.fromJson(reader, Products.class);
            products.add(product);
        }
        reader.endArray();
        reader.close();
        System.out.println("Reading finished");
        return products;
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
	
	private void storeJSONLocal(String jsonFile)
	{
		System.out.println("Store JSON locally");
		String FILENAME = "products.json";
	
		FileOutputStream fos = null;
		try {
			fos = this.cont.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.close();
			fos.write(jsonFile.getBytes());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getJSONLocal()
	{
		System.out.println("Get JSON locally");
		String jsonFile = null;
		File file = this.cont.getFileStreamPath(FILENAME);
		FileInputStream fis;
		
		if (file.exists())
		{
			try {
				fis = this.cont.openFileInput(FILENAME);
				fis.read(jsonFile.getBytes());
				System.out.println("Reading File..." + String.valueOf(jsonFile));
				fis.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return jsonFile;
	}
    
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		ProductsHelper.setProductsList(getProductsList());
	}
	
	protected void onProgressUpdate(Integer... value) {
		super.onProgressUpdate();
		
		if(value[0]<100)
		{
			dialog = ProgressDialog.show(getContext(),"Please wait...", "Downloading Product Information", true);
		}
		else
		{
			dialog.dismiss();
		}
	}


 }
