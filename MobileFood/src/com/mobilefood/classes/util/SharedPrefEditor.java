package com.mobilefood.classes.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.Assert;

import com.mobilefood.activity.BeforeMainActivity;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefEditor {
	
	private String[] selectionList = {
			"Israelitische Kultusgemeinde Baden", 
			"Israelitische Gemeinde Basel", 
			"Israelitische Religionsgesellschaft Basel (IRGB)", 
			"Jüdische Gemeinde Bern", 
			"Israelitische Kultusgemeinde Endingen", 
			"Communauté Israélite de Genève",
			"Communauté Israélite de Lausanne et du ct de Vaud", 
			"Israelitische Gemeinde Winterthur", 
			"Israelitische Cultusgemeinde Zürich (ICZ)",
			"Israelitische Religionsgesellschaft Zürich (IRGZ)"};
	
	private String[] urlList = {
			"http://46.163.77.113:8080/SKoscher/JSON/IKBaden.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/IGBasel.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/IRGBasel.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/JGBern.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/IKEndingen.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/CIGeneve.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/CILausanneVaud.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/IGWinterthur.json", 
			"http://46.163.77.113:8080/SKoscher/JSON/ICZuerich.json",
			"http://46.163.77.113:8080/SKoscher/JSON/IRGZuerich.json"};
	
	private HashMap<String, String> selectionToUrlMap;
	private String activeURL;
	private String activeSelection;
	
	private static final String PREFS_NAME_LIST_SELECTION = "ListSelection";
	private static final String PREFS_PROD_LIST = "ProductList";
	private Context cont;
	
	public SharedPrefEditor(Context context)
	{
		this.cont = context;
		this.selectionToUrlMap = new HashMap<String, String>();
	}
	
	public void setSelection(String selection)
	{	    
	    SharedPreferences settings = cont.getSharedPreferences(PREFS_NAME_LIST_SELECTION, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString("userSelection", selection);
	    System.out.println("setSharedPref #SELECTION: " + selection);
	    // Commit the edits!
	    editor.commit();
	    
	}
	
	public String getSelection()
	{
	    String selection;
	    // Restore preferences
	    SharedPreferences settings = cont.getSharedPreferences(PREFS_NAME_LIST_SELECTION, 0);
	    selection = settings.getString("userSelection", "");
	    System.out.println("getSharedPref #SELECTION: " + selection);
		
		return selection;
	}
	
	public HashMap<String, String> getSelectionToUrlMap()
	{
	    Assert.assertEquals(getSelectionList().length == getUrlList().length, true);
    	for(int i = 0; i< getSelectionList().length; i++)
    	{
//    		System.out.println("Add Selection: " + getSelectionList()[i]);
//    		System.out.println("Add URL: " + getUrlList()[i]);
    		this.selectionToUrlMap.put(getSelectionList()[i], getUrlList()[i]);
    	}
    	return selectionToUrlMap;
	}
	
	public void setProductList(ArrayList<Product> list) {
        //save the Product list to preference
        SharedPreferences settings = cont.getSharedPreferences(PREFS_PROD_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(PREFS_PROD_LIST);
        editor.commit();
        try {
            editor.putString(PREFS_PROD_LIST, ObjectSerializer.serialize(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }
	
	public ArrayList<Product> getProductList() {
        //      load tasks from preference
		ArrayList<Product> prodList = null;
        SharedPreferences settings = cont.getSharedPreferences(PREFS_PROD_LIST, Context.MODE_PRIVATE);

        try {
        	prodList = (ArrayList<Product>) ObjectSerializer.deserialize(settings.getString(PREFS_PROD_LIST, ObjectSerializer.serialize(new ArrayList<Product>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prodList;
    }
	
	public void saveCurrentFavList()
	{
//		setProductList((ArrayList<Product>)ProductsHelper.getProductWatchList());
		ArrayList<Product> favList = new ArrayList<Product>();
		favList.addAll(ProductsHelper.getProductFavorites().values());
		
		setProductList(favList);
		
		System.out.println("Current Watch-List saved to SharedPref: " + ProductsHelper.getProductFavorites().values().toString());
	}

	/**
	 * @return the selectionList
	 */
	public String[] getSelectionList() {
		return selectionList;
	}
	
	/**
	 * @return the urlList
	 */
	public String[] getUrlList() {
		return urlList;
	}

}
