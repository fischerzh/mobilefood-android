package com.mobilefood.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mobilefood.activity.R;
import com.mobilefood.barcode.*;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.BarcodeAlertDialog;
import com.mobilefood.classes.util.SharedPrefEditor;
import com.mobilefood.json.LoadJSON;

import android.R.style;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView textView;
	private Button scanButton, productsButton, favButton, producerButton, categoryButton;
	private EditText eanNrTxt;
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
        setTitle("");
        this.act = MainActivity.this;
//        this.textView = (TextView) findViewById(R.id.TextView01);
        
        
        this.applicationContext = getApplicationContext();
        //Set Buttons
        scanButton = (Button) findViewById(R.id.scanButton);
        productsButton = (Button) findViewById(R.id.button_products);
        favButton = (Button) findViewById(R.id.button_favorites);
        producerButton = (Button) findViewById(R.id.button_producer);
        categoryButton = (Button) findViewById(R.id.button_category);
        eanNrTxt = (EditText) findViewById(R.id.eanNr);
        
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
        		
        		if(eanNrTxt.getText().length()>0)
        		{
					Intent intent = new Intent(getApplicationContext(), IntentIntegrator.class);
		    		intent.putExtra("SCAN_RESULT", eanNrTxt.getText().toString());
		    		onActivityResult(0, android.app.Activity.RESULT_OK, intent);
        		}
        		else
        		{
            		IntentIntegrator integrator = new IntentIntegrator(act);
            		integrator.initiateScan();
            	}

        	}
    	});
        
        //Favorites
        favButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("Favorites clicked");
        		
        		startFavoritesActivity();
        	}
    	});
        
        //Producers
        producerButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("Producers clicked");
        		
        		startProducersActivity();
        	}
    	});
        
        //Category
        categoryButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		System.out.println("Category clicked");
        		
        		startCategoryActivity();
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
                Product currentProd = null;
//                Toast.makeText(MainActivity.this.applicationContext,"Product scanned: " + contents, Toast.LENGTH_LONG).show();		
                List<Product> resultList = new ArrayList<Product>();
                for (Product prod : ProductsHelper.getProductList())
                {
                	if (prod.getEan().compareTo(contents) == 0 || prod.getEan().contains(contents)) 
                	{
                		currentProd = prod;
                		resultList.add(currentProd);
                		ProductsHelper.setCurrentItem(prod);
                        productFound = true;
                	}
                }
                String[] producerList = new String[resultList.size()];

//                Toast.makeText(MainActivity.this.applicationContext,"Product found? : " + contents , Toast.LENGTH_LONG).show();		

                if (productFound)
                {
//                    Toast.makeText(MainActivity.this.applicationContext,"PRODUCT FOUND!" + currentProd.getName(), Toast.LENGTH_LONG).show();	

                	BarcodeAlertDialog alertDialog;
                	
					if (currentProd.getCategory().contentEquals("Brot"))
					{
						if(resultList.size()>1)
						{
							for(int i = 0; i < resultList.size(); i++)
							{
								producerList[i] = resultList.get(i).getProducer();
							}
							alertDialog = new BarcodeAlertDialog(act, "Brot gefunden: " + currentProd.getName() +" \n Mehrere Hersteller vorhanden!", R.style.style_product_bread, productFound, producerList);
						}
						else
						{
							alertDialog = new BarcodeAlertDialog(act, "Brot gefunden: " + currentProd.getName(), R.style.style_product_bread, productFound);
						}
					}
					else
					{
						alertDialog  = new BarcodeAlertDialog(act, "Produkt gefunden: " + currentProd.getName(), R.style.style_product_found, productFound);
					}
					
            		alertDialog.show();
//            		Toast.makeText(MainActivity.this.applicationContext,"Produkt gefunden: !", Toast.LENGTH_LONG).show();	
                }
                else
                {
            		BarcodeAlertDialog alertDialog= new BarcodeAlertDialog(act, "Produkt nicht gefunden", R.style.style_product_not_found, productFound);
            		alertDialog.show();
//                    Toast.makeText(MainActivity.this.applicationContext,"Kein Produkt gefunden!", Toast.LENGTH_LONG).show();	
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
    
    /*
     * Check to enable or disable button after async JSON read
     * 
     */
    public void checkButtonStatus()
    {
        //setDisabled if not Loaded
        if(ProductsHelper.getProductList()==null || !jsonLoader.getIsLoaded())
        {
        	categoryButton.setEnabled(false);
        	producerButton.setEnabled(false);
        	favButton.setEnabled(false);
        	scanButton.setEnabled(false);
        	productsButton.setEnabled(false);
        }
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
    
    
	protected void startProducersActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, ProducerActivity.class);
		startActivity(intent);
	}
	
	protected void startCategoryActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
		startActivity(intent);
	}
    
//    @Override
//    public void onBackPressed() {
////    	finish();
////    	android.os.Process.killProcess(android.os.Process.myPid());
////    	super.onDestroy();
////    	System.exit(0);
//    }
	
	@Override
	public void onBackPressed()
	{
		Intent homeIntent = new Intent(Intent.ACTION_MAIN);
		homeIntent.addCategory(Intent.CATEGORY_HOME);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(homeIntent);
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
