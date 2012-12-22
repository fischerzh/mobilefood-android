package com.mobilefood.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mobilefood.activity.R;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.Products;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.BarcodeAlertDialog;
import com.mobilefood.classes.util.SharedPrefEditor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

public class LoadJSON extends AsyncTask<Context, Integer, String> {
    
	/* UI Elements */
	ProgressDialog dialog ;
    private Context cont;
    
    
	/* Constants */
    private String jsonURL;
	private String FILENAME = "products.json";
	public static final String PREFS_NAME = "DateOfFile";

	
    /* JSON and Java Objects */
    private List<Products> productsList;
    private ArrayList<Product> productList;
	private String dateOfFile;
    
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

	/**
	 * @return the productList
	 */
	public ArrayList<Product> getProductList() {
		ArrayList<Product> returnList = (ArrayList<Product>)getProductsList().get(0).getProducts();
		return returnList;
	}


	/**
	 * @return the dateOfFile
	 */
	public String getDateOfFile() {
		return dateOfFile;
	}


	/**
	 * @param dateOfFile the dateOfFile to set
	 */
	public void setDateOfFile(String dateOfFile) {
		this.dateOfFile = dateOfFile;
	}


	/*
	 * Start JSON download
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Context... params) {
		System.out.println("Start JSON Loader");
		publishProgress(0);
		
//		if(isNetworkAvailable())
//		{
			try {
				
				setProductsList(readJsonStreamProducts());
				System.out.println(productsList.get(0).getProducts().get(0).getName());
				System.out.println(productsList.get(0).getProducts().get(0).getEan());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
//		}
		publishProgress(100);
		
		/** Get Favorites from SharedPref if available **/
		SharedPrefEditor editor = new SharedPrefEditor(getContext());
		ArrayList<Product> prodListFromPrefs = new ArrayList<Product>();
		prodListFromPrefs = editor.getProductList();
		if (prodListFromPrefs != null)
		{
			System.out.println("Prod List from Pref in LoadJSON: " + prodListFromPrefs.toString());
			System.out.println("Size: " + prodListFromPrefs.size());
			for(int i = 0; i < getProductList().size(); i++)
			{
				for(int j = 0; j < prodListFromPrefs.size(); j++)
				{
//					if(getProductList().get(i).getName().contains(prodListFromPrefs.get(j).getName()))

					if(getProductList().get(i).getId() == prodListFromPrefs.get(j).getId())
					{
						System.out.println("Favorite found: " + getProductList().get(i).getName());
						getProductList().get(i).setFavorite(true);
					}
				}
			}
		}
		else
		{
//    		BarcodeAlertDialog alertDialog = new BarcodeAlertDialog(getContext(), "Internet wird benštigt!", R.style.style_product_found, false);
//    		alertDialog.show();
//           Toast.makeText(getContext(),"Internet wird benštigt!", Toast.LENGTH_LONG).show();	
			return "NOT_READY";
    		
		}
		return "READY";

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
		if(isNetworkAvailable())
//		JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(getJSONDataFromURL("http://www.uitiwg.ch/products.json"), "UTF-8")));
		{
			if(hasChanged(jsonURL) || !fileExists())
			{
	//			System.out.println("has file changed? " + hasChanged(jsonURL));
				BufferedReader r = new BufferedReader(new InputStreamReader(getJSONDataFromURL(this.getUrl()), "UTF-8"));
				StringBuilder total = new StringBuilder();
				String line;
				while( (line = r.readLine()) != null) {
					total.append(line);
				}
				r.close();
				storeJSONLocal(total.toString());
			}
		}
		JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(getJSONDataFromFile(), "UTF-8")));
		
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
	
	private boolean hasChanged(String url){
		boolean hasChanged;
		long date;
		String dateFromSharedPrefStr, dateFromWebStr;
	    try {
	      String dateFromPref;
	      HttpURLConnection.setFollowRedirects(false);
	      HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
	      con.setRequestMethod("HEAD");
//	      hasChanged = (con.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED);
	      date = con.getLastModified();
	      dateFromWebStr = new Date(date).toGMTString();
	      dateFromPref = getDateFromSharedPref();
	      if (dateFromPref == null || dateFromPref.contentEquals(""))
	      {
	    	  // No date yet stored, first time getting file
	    	  System.out.println("NO DATE STORED: WRITE TO PREF");
	    	  storeDateInSharedPref(dateFromWebStr);
	    	  return true;
	      }
	      dateFromSharedPrefStr = new Date(getDateFromSharedPref()).toGMTString();
	      if (dateFromSharedPrefStr.contentEquals(dateFromWebStr))
	      {
	    	  System.out.println("EQUAL DATE: NO CHANGE");
	    	  hasChanged = false;
	      }
	      else
	      {
	    	  System.out.println("NOT EQUAL DATE: FILE CHANGE");
		      storeDateInSharedPref(dateFromWebStr);
		      hasChanged = true;
	      }
	      return hasChanged;
	    }
	    catch (Exception e) {
	       e.printStackTrace();
	       return false;
	    }
	}
	
	private boolean fileExists()
	{
		File file = getContext().getFileStreamPath(this.FILENAME);
		System.out.println("File exists: " + file.exists());
		return file.exists();
	}
	
	private void storeJSONLocal(String file)
	{
		System.out.println("Store JSON locally");

		FileOutputStream fos;
		try {
			fos = cont.openFileOutput(this.FILENAME, Context.MODE_PRIVATE);
			fos.write(file.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void storeDateInSharedPref(String date)
	{	    
	    SharedPreferences settings2 = cont.getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings2.edit();
	    editor.putString(PREFS_NAME, date);
	    System.out.println("setSharedPref #DATE: " + date);
	    // Commit the edits!
	    editor.commit();
	    
	}
	
	private String getDateFromSharedPref()
	{
	    String date;
	    // Restore preferences
	    SharedPreferences settings = cont.getSharedPreferences(PREFS_NAME, 0);
	    date = settings.getString(PREFS_NAME, "");
	    System.out.println("getSharedPref #DATE: " + date);
	    setDateOfFile(date);
		
		return date;
	}

	
	private InputStream getJSONDataFromFile()
	{
		System.out.println("Reading JSON from File..");
//		InputStream is = cont.getResources().openRawResource(R.raw.products);
//		InputStream is = cont.getAssets().open("products.json");
		FileInputStream fis = null;
		try {
			fis = this.cont.openFileInput(this.FILENAME);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("InputStream: " + fis);
//		return is;
		return fis;
	}
	
	private boolean isNetworkAvailable() 
	{
	    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
    
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (result.contentEquals("READY"))
			ProductsHelper.setProductList(getProductsList().get(0).getProducts());
		else if(result.contentEquals("NOT_READY"))
		{
			BarcodeAlertDialog alertDialog = new BarcodeAlertDialog(getContext(), "Internet wird benštigt!", R.style.style_no_internet, false);
			alertDialog.show();
			
//          Toast.makeText(getContext(),"Internet wird benštigt!", Toast.LENGTH_LONG).show();	
		}
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
