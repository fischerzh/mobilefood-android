package com.mobilefood.classes;

import java.util.HashMap;

import com.mobilefood.activity.BeforeMainActivity;

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
		"http://www.uitiwg.ch/products_bigdata.json", 
		"http://www.uitiwg.ch/products_bigdata.json"};
	
	private HashMap<String, String> selectionToUrlMap;
	private String activeURL;
	private String activeSelection;
	
	public static final String PREFS_NAME_LIST_SELECTION = "ListSelection";
	private Context cont;
	
	public SharedPrefEditor(Context context)
	{
		this.cont = context;
		this.selectionToUrlMap = new HashMap<String, String>();
	}
	
	public void setSelectionInSharedPref(String selection)
	{	    
	    SharedPreferences settings2 = cont.getSharedPreferences(PREFS_NAME_LIST_SELECTION, 0);
	    SharedPreferences.Editor editor = settings2.edit();
	    editor.putString("silentMode", selection);
	    System.out.println("setSharedPref #SELECTION: " + selection);
	    // Commit the edits!
	    editor.commit();
	    
	}
	
	public String getSelectionFromSharedPref()
	{
	    String selection;
	    // Restore preferences
	    SharedPreferences settings = cont.getSharedPreferences(PREFS_NAME_LIST_SELECTION, 0);
	    selection = settings.getString("silentMode", "");
	    System.out.println("getSharedPref #SELECTION: " + selection);
		
		return selection;
	}
	
	public HashMap<String, String> getSelectionToUrlMap()
	{
	    	
    	for(int i = 0; i< getSelectionList().length; i++)
    	{
    		System.out.println("Add Selection: " + getSelectionList()[i]);
    		System.out.println("Add URL: " + getUrlList()[i]);
    		this.selectionToUrlMap.put(getSelectionList()[i], getUrlList()[i]);
    	}
    	return selectionToUrlMap;
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
