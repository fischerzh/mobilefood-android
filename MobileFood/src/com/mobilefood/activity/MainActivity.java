package com.mobilefood.activity;

import com.mobilefood.activity.R;
import com.mobilefood.httphandler.DownloadFoodList;
import com.mobilefood.httphandler.HttpJsonHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Handler;

public class MainActivity extends Activity {
	
	private Handler handler;
	private TextView textView;
	private Button scanButton, productsButton;
	private String url = "http://www.uitiwg.ch/products.json";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.TextView01);
        scanButton = (Button) findViewById(R.id.scanButton);
        productsButton = (Button) findViewById(R.id.button_products);
        readWebpage(scanButton);
        
        productsButton.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		//return
        		System.out.println("Button clicked");
        		startProductAcitivty();
        	}
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    public void readWebpage(View view) {
        DownloadFoodList task = new DownloadFoodList();
        HttpJsonHandler handler = new HttpJsonHandler();
        //System.out.println("Products: " + HttpJsonHandler.getJSONfromFile("file:///android_asset/products.json") );
        //System.out.println("Products: " + HttpJsonHandler.getJSONfromURL("http://www.uitiwg.ch/products.json"));
        System.out.println("Products: " + HttpJsonHandler.getJSON(url));
        handler.parseJSONtoObj(url);
        //task.execute(new String[] { "http://www.vogella.com" });
        
      }
    
    public void startProductAcitivty()
    {
    	ProductActivity.callMe(this);
    }
}
