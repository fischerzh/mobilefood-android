package com.mobilefood.activity;

import java.util.List;

import com.mobilefood.activity.R;
import com.mobilefood.classes.Products;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ProductActivity extends Activity{
	
	private static List<Products> productsList;
	
	public static void callMe(Context context)
	{
		Intent intent = new Intent(context, ProductActivity.class);
		context.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.products_main);
        setContentView(R.layout.products_item);

	}

	/**
	 * @return the productsList
	 */
	public static void getProductsListFromSerializable() {
		//Use serializable to get Object from MainActivity
		System.out.println("Get Object with Products");
	}

	/**
	 * @return the productsList
	 */
	public static List<Products> getProductsList() {
		return productsList;
	}

	/**
	 * @param productsList the productsList to set
	 */
	public static void setProductsList(List<Products> productsList) {
		ProductActivity.productsList = productsList;
	}


}
