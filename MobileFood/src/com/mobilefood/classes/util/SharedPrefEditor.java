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
		"IK Baden", 
		"IRG Basel", 
		"IG Basel", 
		"JG Bern", 
		"CIL Genève", 
		"IG Winterthur",
		"ICZ", 
		"IRG Zürich", 
		"JLG Or Chadasch"};
	
	private String[] urlList = {
		"http://www.uitiwg.ch/products.json", 
		"http://www.uitiwg.ch/products_bigdata.json", 
		"http://www.uitiwg.ch/products_bigdata.json", 
		"http://www.uitiwg.ch/products.json", 
		"http://www.uitiwg.ch/products.json", 
		"http://www.uitiwg.ch/products_bigdata.json",
		"http://www.uitiwg.ch/products_bigdata.json", 
		"http://www.uitiwg.ch/products_contents.json", 
		"http://www.uitiwg.ch/products_id.json"};
	
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
		setProductList((ArrayList<Product>)ProductsHelper.getProductWatchList());
		System.out.println("Current Watch-List saved to SharedPref: " + ProductsHelper.getProductWatchList().toString());
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
