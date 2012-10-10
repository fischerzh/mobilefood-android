package com.mobilefood.activity;

import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProductInfoActivity extends Activity
{
	private int prodID;
	
	public static void callMe(Context context)
	{
		Intent intent = new Intent(context, ProductInfoActivity.class);
		context.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
	
        setContentView(R.layout.products_info_main);
        
        System.out.println("Got Product: " + ProductsHelper.getCurrentItem().getName());
	
        TextView prod_titel = (TextView) findViewById(R.id.prod_info_titel);
        prod_titel.setText(ProductsHelper.getCurrentItem().getName() + " (EAN: " + ProductsHelper.getCurrentItem().getEan() +" )");
        
        TextView prod_sub_titel = (TextView) findViewById(R.id.prod_info_subtitel);
        prod_sub_titel.setText(ProductsHelper.getCurrentItem().getProducer());
        
        TextView prod_text = (TextView) findViewById(R.id.prod_info_text);
        prod_text.setText(ProductsHelper.getCurrentItem().getKontrolleur());
	}
}
