package com.mobilefood.activity;

import com.mobilefood.activity.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ProductActivity extends Activity{
	
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

}
