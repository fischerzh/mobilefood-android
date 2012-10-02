package com.mobilefood.activity;

import com.mobilefood.activity.R;
import com.mobilefood.barcode.*;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.json.LoadJSON;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView textView;
	private Button scanButton, productsButton;
	private String jsonUrl = "http://www.uitiwg.ch/products.json";
	private Activity act;
	private ProductActivity prodAct;
	LoadJSON jsonLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.act = MainActivity.this;
        this.textView = (TextView) findViewById(R.id.TextView01);
        
        //Set Buttons
        scanButton = (Button) findViewById(R.id.scanButton);
        productsButton = (Button) findViewById(R.id.button_products);
        
        //Start AsyncTask for JSON
        startJSONSync();
        
        
        //Procucts
        productsButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("Product clicked");
            	//ProductsHelper.setProductsList(jsonLoader.getProductsList());
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
                
    }
    
	/*
     * Handle Scanner Result
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                System.out.println("Scan Result: "  + contents);
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
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
    	// Start Sync
    	System.out.println("Start JSON Sync");
    	jsonLoader = new LoadJSON(this.act, jsonUrl);
    	jsonLoader.execute();
    	
    }
    
    public void startProductAcitivty()
    {
    	ProductActivity.callMe(this);
    }
}
