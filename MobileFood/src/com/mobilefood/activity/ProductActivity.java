package com.mobilefood.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mobilefood.activity.R;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.Products;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity{
	
	private List<Products> productsList;
	private TextView productContent;
	private ListView lv;
	private EditText editTxt;
	
	ArrayAdapter<String> arrad;
	ArrayAdapter<String> productNames;
	
	public static void callMe(Context context)
	{
		Intent intent = new Intent(context, ProductActivity.class);
		context.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.products_main);
        
		lv = (ListView) findViewById(R.id.product_list_view);
		editTxt = (EditText) findViewById(R.id.product_search_box);
        
        this.productsList = ProductsHelper.getProductsList();
        
        List<String> data = new ArrayList<String>();

        for (Product prod : this.productsList.get(0).getProducts())
        {
        	data.add(prod.getName());
        	//productNames.add(prod.getName());
        }
        //data = Arrays.sort(data.toArray());
        
        Collections.sort(data, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        
        
        this.productNames = new ArrayAdapter<String>(this, R.layout.products_item, R.id.product_content, data);

        //lv.setAdapter(new CustomAdapter(this, data));
		//arrad = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, data);
		lv.setAdapter(productNames);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
	       	    Toast.makeText(getApplicationContext(),"Click ListItem Number " + pos, Toast.LENGTH_LONG).show();				
			}
        	}); 
        
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
               ProductActivity.this.productNames.getFilter().filter(arg0);
           }
       });
	}

	/**
	 * @return the productsList
	 */
	public List<Products> getProductsList() {
		return productsList;
	}

	/**
	 * @param productsList the productsList to set
	 */
	public void setProductsList(List<Products> productsList) {
		this.productsList = productsList;
	}

	/**
	 * @return the productContent
	 */
	public TextView getProductContent() {
		return productContent;
	}

	/**
	 * @param productContent the productContent to set
	 */
	public void setProductContent(TextView productContent) {
		this.productContent = productContent;
	}

	
}
