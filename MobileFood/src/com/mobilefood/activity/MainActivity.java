package com.mobilefood.activity;

import java.util.HashMap;

import com.mobilefood.activity.R;
import com.mobilefood.barcode.*;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.BarcodeAlertDialog;
import com.mobilefood.classes.util.SharedPrefEditor;
import com.mobilefood.json.LoadJSON;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView textView;
	private Button scanButton, productsButton, favButton;
	private String jsonUrl;
	public static final String PREFS_NAME = "DateOfFile";
	
	private HashMap<String, String> selectionToUrl;

	private Activity act;
	private ProductActivity prodAct;
	private Context applicationContext;
    private SharedPrefEditor editor;
	private LoadJSON jsonLoader;
    
	private static boolean refreshHome = true;
	
	
	public static void callMe(Context context, boolean refresh)
	{
		refreshHome = refresh;
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        this.act = MainActivity.this;
//        this.textView = (TextView) findViewById(R.id.TextView01);
        
        
        this.applicationContext = getApplicationContext();
        //Set Buttons
        scanButton = (Button) findViewById(R.id.scanButton);
        productsButton = (Button) findViewById(R.id.button_products);
        favButton = (Button) findViewById(R.id.button_favorites);
        
        
        //Start AsyncTask for JSON only if app was started
        if(refreshHome)
        	startJSONSync();
        
        
        //Procucts
        productsButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("Products clicked");
        		startProductAcitivty();
        	}
        });
        
        //Scanning
        scanButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("Scan Product clicked");

        		IntentIntegrator integrator = new IntentIntegrator(act);
        		integrator.initiateScan();

        	}
    	});
        
        //Favorites
        favButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("Favorites clicked");
        		
        		startFavoritesActivity();
        	}
    	});
        
    }
    
	/*
     * Handle Scanner Result
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	//Toast.makeText(MainActivity.this.applicationContext,"Product scanned: " + intent.getStringExtra("SCAN_RESULT"), Toast.LENGTH_LONG).show();	
//        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
            	boolean productFound = false;
                String contents = intent.getStringExtra("SCAN_RESULT");
                System.out.println("Scan Result: "  + contents);
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                Toast.makeText(MainActivity.this.applicationContext,"Product scanned: " + contents, Toast.LENGTH_LONG).show();		

                for (Product prod : ProductsHelper.getProductList())
                {
                	if (prod.getEan().compareTo(contents) == 0 || prod.getEan().contains(contents)) 
                	{
                        productFound = true;
                	}
                }
                if (productFound)
                {
            		BarcodeAlertDialog alertDialog= new BarcodeAlertDialog(act, "Produkt gefunden", R.style.style_product_found);
            		alertDialog.show();
//                  Toast.makeText(MainActivity.this.applicationContext,"PRODUCT IS KOSHER!", Toast.LENGTH_LONG).show();	
                }
                else
                {
            		BarcodeAlertDialog alertDialog= new BarcodeAlertDialog(act, "Produkt nicht gefunden", R.style.style_product_not_found);
            		alertDialog.show();
//                    Toast.makeText(MainActivity.this.applicationContext,"NO PRODUCT FOUND: NOT KOSHER!", Toast.LENGTH_LONG).show();	
                }

            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            	System.out.println("Scan cancelled");
            }
//        }
    }

    @Override
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /*
     * Start JSON Synchronization
     */
    public void startJSONSync()
    {
        editor = new SharedPrefEditor(this);
    	// Get SharedPref HashMap for Link from Selection
    	System.out.println(editor.getSelectionToUrlMap().get(editor.getSelection()));
    	// Start Sync
    	setJsonUrl(editor.getSelectionToUrlMap().get(editor.getSelection()));
    	System.out.println("Start JSON Sync");
    	jsonLoader = new LoadJSON(this.act, getJsonUrl());
    	jsonLoader.execute();
    	
    }
    
    public void startProductAcitivty()
    {
//    	ProductActivity.callMe(this);
		Intent intent = new Intent(MainActivity.this, ProductActivity.class);
		startActivity(intent);
    }
    
    public void startFavoritesActivity()
    {
//    	FavoritesActivity.callMe(this);
		Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
		startActivity(intent);
    }
    
    @Override
    public void onBackPressed() {
    	System.out.println("Back pressed");
    	return;
    }
    
	/**
	 * @return the jsonUrl
	 */
	public String getJsonUrl() {
		return jsonUrl;
	}

	/**
	 * @param jsonUrl the jsonUrl to set
	 */
	public void setJsonUrl(String jsonUrl) {
		this.jsonUrl = jsonUrl;
	}

}
