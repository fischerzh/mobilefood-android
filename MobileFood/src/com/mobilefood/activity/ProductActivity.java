package com.mobilefood.activity;

import com.mobilefood.activity.R;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.CustomAdapter;
import com.mobilefood.classes.override.ProductListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class ProductActivity extends Activity{
	
	private ListView listView;
	private ProductListAdapter adapter;
	private EditText editTxt;
		
	public static void callMe(Context context)
	{
		Intent intent = new Intent(context, ProductActivity.class);
		context.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.products_main);
        
        editTxt = (EditText) findViewById(R.id.product_search_box);
        
        adapter = new ProductListAdapter(this, ProductsHelper.getProductList());

        listView = (ListView) findViewById(R.id.product_list_view);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println("Item clicked: " + arg2);
			}
		});
        
        listView.setAdapter(adapter);

        
        editTxt.addTextChangedListener(new TextWatcher()
        {
           @Override
           public void onTextChanged( CharSequence arg0, int arg1, int arg2, int arg3)
           {
               // TODO Auto-generated method stub
           }

           @Override
           public void beforeTextChanged( CharSequence arg0, int arg1, int arg2, int arg3)
           {
               // TODO Auto-generated method stub
           }

           @Override
           public void afterTextChanged( Editable arg0)
           {
               // TODO Auto-generated method stub
        	   adapter.getFilter().filter(arg0.toString().toLowerCase());
           }
       });
	}
}
