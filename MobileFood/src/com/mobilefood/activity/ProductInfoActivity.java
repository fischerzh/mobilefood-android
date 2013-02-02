package com.mobilefood.activity;

import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProductInfoActivity extends Activity
{
	private int prodID;
	private Product currentProd;
	
//	public static void callMe(Context context)
//	{
//		Intent intent = new Intent(context, ProductInfoActivity.class);
//		context.startActivity(intent);
//	}
	
	public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
	
        setContentView(R.layout.products_info_main);
        setTitle("");

        currentProd = ProductsHelper.getCurrentItem();
        
        System.out.println("Got Product: " + currentProd.getName());
        System.out.println("Got Content: " + currentProd.getContents().toString());
	
        TextView prod_titel = (TextView) findViewById(R.id.prod_info_prod_txt);
        prod_titel.setText(currentProd.getName());
        
        TextView prod_ean = (TextView) findViewById(R.id.prod_info_ean_txt);
        prod_ean.setText(currentProd.getEan());
        
        TextView prod_producer = (TextView) findViewById(R.id.prod_info_producer_txt);
        prod_producer.setText(currentProd.getProducer());
        
        TextView prod_cat = (TextView) findViewById(R.id.prod_info_cat_txt);
        prod_cat.setText(currentProd.getCategory());
        
        TextView prod_package = (TextView) findViewById(R.id.prod_info_package_txt);
        prod_package.setText(currentProd.getPackaging());
       
        TextView prod_koscher = (TextView) findViewById(R.id.prod_info_koscher_att_txt);
        String parve = "Nein", chalavakum = "Nein";
        if(currentProd.isParve())
        	parve = "Ja";
        if(currentProd.isChalavakum())
        	chalavakum = "Ja";
        prod_koscher.setText("Parve: " + parve + " , Chalavakum: " + chalavakum);    
        
        TextView prod_controller = (TextView) findViewById(R.id.prod_info_controller_txt);
        prod_controller.setText(currentProd.getController());          
        
        TextView prod_notes = (TextView) findViewById(R.id.prod_info_note_txt);
        prod_notes.setText(currentProd.getComment());
        
        TextView prod_content = (TextView) findViewById(R.id.prod_info_content_txt);
        prod_content.setText(currentProd.getContentList());
        
	}
	
	
	
    public void onBackClick(View view)
    {
//    	System.out.println("Back clicked: " + getCallingActivity().getShortClassName());
    	startCallingActivity();
    }
    
    @Override
    public void onBackPressed() {
//    	editTxt.setText("");
    	System.out.println("Back pressed");
    	startCallingActivity();
    }
    
    public void startCallingActivity()
    {
    	Intent intent = null;
    	if(getCallingActivity() != null )
    	{
	    	if(getCallingActivity().getShortClassName().contains("ProductActivity") )
	    	{
	    		intent = new Intent(ProductInfoActivity.this, ProductActivity.class);
	    		if(getIntent().hasExtra("Producer"))
					intent.putExtra("Producer", ProductsHelper.getCurrentProducer());
	    		if(getIntent().hasExtra("Category"))
					intent.putExtra("Category", ProductsHelper.getCurrentCategory());
	    		startActivityForResult(intent, 1);
	    	}
	    	else if(getCallingActivity().getShortClassName().contains("FavoritesActivity") )
	    	{
	    		intent = new Intent(ProductInfoActivity.this, FavoritesActivity.class);
	    		startActivityForResult(intent, 2);
	    	}
	    	else
	    	{
	    		MainActivity.callMe(ProductInfoActivity.this, false);
	    	}
    	}
    	else
    	{
        	MainActivity.callMe(ProductInfoActivity.this, false);
    	}
    }
}
