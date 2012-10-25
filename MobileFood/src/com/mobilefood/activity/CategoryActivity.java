package com.mobilefood.activity;

import java.util.ArrayList;

import com.mobilefood.activity.R;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.ProductBaseAdapter;
import com.mobilefood.classes.util.SharedPrefEditor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CategoryActivity extends Activity{
	
	private ListView listView;
	private ArrayAdapter adapter;
	private EditText editTxt;
    private String[] defaultCategoryList = {"Milch", "Brot"};
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category_main);
        setTitle("");

        listView = (ListView) findViewById(R.id.category_list_view);
        editTxt = (EditText) findViewById(R.id.category_search_box);
        
        if(ProductsHelper.getCategoryList() != null || !ProductsHelper.getCategoryList().isEmpty())
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ProductsHelper.getCategoryList());
        else
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, defaultCategoryList);

        listView.setAdapter(adapter);
        
        listView.setTextFilterEnabled(true);
        listView.setClickable(true);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				String currentCategory;
				
				System.out.println("Position clicked: " + position + " " + listView.getItemAtPosition(position));
				
				currentCategory = (String) listView.getItemAtPosition(position);

			    ProductsHelper.setCurrentCategory(currentCategory);
				Intent intent = new Intent(CategoryActivity.this, ProductActivity.class);
				startActivityForResult(intent, 1);
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
        	   CategoryActivity.this.adapter.getFilter().filter(arg0);
           }
           
       });
        
        
	}
	
    public void onHomeClick(View view)
    {
    	System.out.println("Home clicked");
    	MainActivity.callMe(view.getContext(), false);
    }
    
    @Override
    public void onBackPressed() {
    	System.out.println("Back pressed");
    	return;
    }


}
