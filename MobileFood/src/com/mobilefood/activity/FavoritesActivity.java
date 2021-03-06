package com.mobilefood.activity;

import java.util.ArrayList;

import com.mobilefood.activity.R;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.ProductBaseAdapter;
import com.mobilefood.classes.override.old.CustomAdapter;
import com.mobilefood.classes.override.old.ProductListAdapter;
import com.mobilefood.classes.util.SharedPrefEditor;

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
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FavoritesActivity extends Activity{
	
	private ListView listView;
	private ProductBaseAdapter adapter;
//	private ArrayAdapter adapter;
	private EditText editTxt;
//	private static Context ctx;
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.favorites_main);
        setTitle("");
        
        listView = (ListView) findViewById(R.id.product_list_view);
        editTxt = (EditText) findViewById(R.id.product_search_box);
        
//        adapter = new ProductListAdapter(this, ProductsHelper.getProductList());
        
        ArrayList<Product> favoritesList = new ArrayList<Product>();
        if(ProductsHelper.getProductList()!=null)
		{
            for (Product prod: ProductsHelper.getProductList())
            {
            	if(prod.isFavorite())
            		favoritesList.add(prod);
            }
		}

        
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, favoritesList);
        adapter = new ProductBaseAdapter(this, favoritesList);
        listView.setAdapter(adapter);
        
        listView.setTextFilterEnabled(true);
        listView.setClickable(true);
//        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//				editTxt.setText("");
//				ProductsHelper.setProductList(originalProducts);
				Product currentItem;
			    currentItem = (Product)listView.getItemAtPosition(position);
				System.out.println("Position clicked: " + position + " " + listView.getItemAtPosition(position));

			    ProductsHelper.setCurrentItem(currentItem);
//				ProductInfoActivity.callMe(adapter.getContext());
				Intent intent = new Intent(FavoritesActivity.this, ProductInfoActivity.class);
				startActivityForResult(intent, 2);
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
        	   FavoritesActivity.this.adapter.getFilter().filter(arg0);
           }
       });
        
        
	}
	
    public void onHomeClick(View view)
    {
    	System.out.println("Home clicked");
    	onBackPressed();
    }
    
    @Override
    public void onBackPressed() {
    	System.out.println("Back pressed");
    	MainActivity.callMe(FavoritesActivity.this, false);

    }
    
    

}
