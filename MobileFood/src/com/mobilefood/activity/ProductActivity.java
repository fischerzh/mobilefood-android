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
import android.widget.EditText;
import android.widget.ListView;

public class ProductActivity extends Activity{
	
	private ListView listView;
	private ProductBaseAdapter adapter;
//	private ArrayAdapter adapter;
	private EditText editTxt;
    private ArrayList<Product> originalProducts;
	
    private boolean hasProducer, hasCategory;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.products_main);
        setTitle("");

        listView = (ListView) findViewById(R.id.product_list_view);
        editTxt = (EditText) findViewById(R.id.product_search_box);
        
//        adapter = new ProductListAdapter(this, ProductsHelper.getProductList());
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ProductsHelper.getProductListString());
        
        /** INSTEAD OF LIST FROM ProductsHelper GET THE INTENT DETAILS PROVIDED !!! **/
//        if(!ProductsHelper.getCurrentProducer().contentEquals(""))
        hasProducer = getIntent().hasExtra("Producer");
        hasCategory = getIntent().hasExtra("Category");
        System.out.println("Has Producer: " + hasProducer + ", Has Category: " + hasCategory);
        if(getCallingActivity() == null || getCallingActivity().equals(null) || (!hasCategory && !hasProducer))
        {
            adapter = new ProductBaseAdapter(this, (ArrayList<Product>) ProductsHelper.getProductList());
        }
        else if(getCallingActivity().getShortClassName().contains("ProducerActivity") || hasProducer)
		{
        	System.out.println("getProducerList");
        	ArrayList<Product> prodFilteredProducer = new ArrayList<Product>();
        	for(Product prod: ProductsHelper.getProductList())
        	{
        		if(prod.getProducer() == ProductsHelper.getCurrentProducer())
        		{
        			prodFilteredProducer.add(prod);
        		}
        	}
            adapter = new ProductBaseAdapter(this, prodFilteredProducer);
//            ProductsHelper.setCurrentProducer("");
		}
        else if(getCallingActivity().getShortClassName().contains("CategoryActivity") || hasCategory)
        {
        	System.out.println("getCategoryList");
        	ArrayList<Product> prodFilteredProducer = new ArrayList<Product>();
        	for(Product prod: ProductsHelper.getProductList())
        	{
        		if(prod.getCategory() == ProductsHelper.getCurrentCategory())
        		{
        			prodFilteredProducer.add(prod);
        		}
        	}
            adapter = new ProductBaseAdapter(this, prodFilteredProducer);
//            ProductsHelper.setCurrentCategory("");
        }

        
        listView.setAdapter(adapter);
        
        listView.setTextFilterEnabled(true);
        listView.setClickable(true);
        
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//				editTxt.setText("");
				Product currentItem;
				
				System.out.println("Position clicked: " + position + " " + listView.getItemAtPosition(position));
				
			    currentItem = (Product)listView.getItemAtPosition(position);

			    ProductsHelper.setCurrentItem(currentItem);

			    Intent intent = new Intent(ProductActivity.this, ProductInfoActivity.class);
		        if(getCallingActivity() != null )
		        {
			        if(getCallingActivity().getShortClassName().contains("ProducerActivity") || getIntent().hasExtra("Producer"))
					{
						intent.putExtra("Producer", ProductsHelper.getCurrentProducer());
					}
					else if (getCallingActivity().getShortClassName().contains("CategoryActivity") || getIntent().hasExtra("Category"))
					{
						intent.putExtra("Category", ProductsHelper.getCurrentCategory());
					}
		        }
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
        	   if(arg0.equals(""))
        	   {
        		   ProductsHelper.setProdSearchText(null);
        	   }
        	   else
        	   {
        		   ProductsHelper.setProdSearchText(arg0.toString());
        	   }
        	   System.out.println("Call filter: " + arg0);
        	   ProductActivity.this.adapter.getFilter().filter(arg0);
           }
           
       });
        
        if(ProductsHelper.getProdSearchText()!=null)
        {
        	this.editTxt.setText(ProductsHelper.getProdSearchText());
        	ProductActivity.this.adapter.getFilter().filter(ProductsHelper.getProdSearchText());
        }
	}
	
    public void onHomeClick(View view)
    {
    	onBackPressed();
    }
    
    @Override
    public void onBackPressed() {
    	System.out.println("Back pressed");
    	startCallingActivity();
    }

    public void startCallingActivity()
    {
    	Intent intent = null;
    	if(getCallingActivity() == null || getCallingActivity().equals(null))
    	{
    		ProductsHelper.setCurrentItem(null);
    		ProductsHelper.setCurrentProducer(null);
    		ProductsHelper.setCurrentCategory(null);
    		ProductsHelper.setProdSearchText(null);        	MainActivity.callMe(ProductActivity.this, false);
    	}
    	else if(getCallingActivity().getShortClassName().contains("ProductActivity") )
    	{
    		intent = new Intent(ProductActivity.this, ProductActivity.class);
    		startActivity(intent);
    	}
    	else if(getCallingActivity().getShortClassName().contains("FavoritesActivity") )
    	{
    		intent = new Intent(ProductActivity.this, FavoritesActivity.class);
    		startActivity(intent);
    	}
    	else if(getCallingActivity().getShortClassName().contains("ProducerActivity") || hasProducer)
    	{
    		ProductsHelper.setCurrentProducer(null);
    		ProductsHelper.setCurrentItem(null);
    		ProductsHelper.setProdSearchText(null);
    		intent = new Intent(ProductActivity.this, ProducerActivity.class);
    		startActivity(intent);
    	}
    	else if(getCallingActivity().getShortClassName().contains("CategoryActivity") || hasCategory)
    	{
    		ProductsHelper.setCurrentCategory(null);
    		ProductsHelper.setCurrentItem(null);
    		ProductsHelper.setProdSearchText(null);
    		intent = new Intent(ProductActivity.this, CategoryActivity.class);
    		startActivity(intent);
    	}    	
    	else
    	{
    		ProductsHelper.setCurrentItem(null);
    		ProductsHelper.setCurrentProducer(null);
    		ProductsHelper.setCurrentCategory(null);
    		ProductsHelper.setProdSearchText(null);
        	MainActivity.callMe(ProductActivity.this, false);
    	}
    }
}
