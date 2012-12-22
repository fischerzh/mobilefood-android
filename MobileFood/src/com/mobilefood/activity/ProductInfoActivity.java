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
	
        TextView prod_titel = (TextView) findViewById(R.id.prod_info_titel);
        prod_titel.setText(ProductsHelper.getCurrentItem().getName() + " (EAN: " + currentProd.getEan() +" )");
        
        TextView prod_sub_titel = (TextView) findViewById(R.id.prod_info_subtitel);
        prod_sub_titel.setText("Hersteller:\t\t" +currentProd.getProducer());
        
        TextView prod_text = (TextView) findViewById(R.id.prod_info_text);
        prod_text.setText("Kategorie:\t\t" + currentProd.getCategory());
        
        TextView prod_content = (TextView) findViewById(R.id.prod_info_contents);
        prod_content.setText("Inhalte:\t\t\t\t" + currentProd.getContents().toString());
	}
	
	
	
    public void onBackClick(View view)
    {
//    	System.out.println("Back clicked: " + getCallingActivity().getShortClassName());
    	Intent intent = null;
    	if(getCallingActivity() == null || getCallingActivity().equals(null))
    	{
        	MainActivity.callMe(view.getContext(), false);
    	}
    	else if(getCallingActivity().getShortClassName().contains("ProductActivity") )
    	{
    		intent = new Intent(view.getContext(), ProductActivity.class);
    		startActivity(intent);
    	}
    	else if(getCallingActivity().getShortClassName().contains("FavoritesActivity") )
    	{
    		intent = new Intent(view.getContext(), FavoritesActivity.class);
    		startActivity(intent);
    	}
    	else
    	{
        	MainActivity.callMe(view.getContext(), false);
    	}
    }
    
    @Override
    public void onBackPressed() {
//    	editTxt.setText("");
    	System.out.println("Back pressed");
    	return;
    }
}
