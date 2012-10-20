package com.mobilefood.activity;

import java.util.ArrayList;

import com.mobilefood.activity.R;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.ProductBaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class ProductActivity extends Activity{
	
	private ListView listView;
	private ProductBaseAdapter adapter;
//	private ArrayAdapter adapter;
	private EditText editTxt;
    private ArrayList<Product> originalProducts;
		
//	public static void callMe(Context context)
//	{
//		Intent intent = new Intent(context, ProductActivity.class);
//		context.startActivity(intent);
//	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.products_main);
//        setContentView(R.layout.products_list_main);
        
        listView = (ListView) findViewById(R.id.product_list_view);
        editTxt = (EditText) findViewById(R.id.product_search_box);
        
//        adapter = new ProductListAdapter(this, ProductsHelper.getProductList());
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ProductsHelper.getProductListString());
        
        adapter = new ProductBaseAdapter(this, (ArrayList<Product>) ProductsHelper.getProductList());
        listView.setAdapter(adapter);
        
        listView.setTextFilterEnabled(true);
        listView.setClickable(true);
//        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//				editTxt.setText("");
				Product currentItem;
				
				System.out.println("Position clicked: " + position + " " + listView.getItemAtPosition(position));
				
			    currentItem = (Product)listView.getItemAtPosition(position);

			    ProductsHelper.setCurrentItem(currentItem);
//				ProductInfoActivity.callMe(adapter.getContext());
				Intent intent = new Intent(ProductActivity.this, ProductInfoActivity.class);
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
        	   System.out.println("Call filter: " + arg0);
        	   ProductActivity.this.adapter.getFilter().filter(arg0);
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
