package com.mobilefood.activity.old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.mobilefood.activity.R;
import com.mobilefood.activity.R.id;
import com.mobilefood.activity.R.layout;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.Products;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.classes.override.old.CustomAdapter;
import com.mobilefood.classes.override.old.ProductListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CopyOfProductActivity extends Activity{
	
	private List<Product> productList;
	private TextView productContent;
	private ListView lv;
	private EditText editTxt;
	
	CustomAdapter myAdapter;
	
	ArrayAdapter<String> arrad;
	ArrayAdapter<String> productNames;
	
//	HashMap<String, Integer> myMap = new HashMap<String, Integer>();
	
	public static void callMe(Context context)
	{
		Intent intent = new Intent(context, CopyOfProductActivity.class);
		context.startActivity(intent);
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.products_main);
        
		lv = (ListView) findViewById(R.id.product_list_view);
//		editTxt = (EditText) findViewById(R.id.product_search_box);
        
        this.productList = ProductsHelper.getProductList();
        
        List<String> data = new ArrayList<String>();

        for (Product prod : this.productList)
        {
        	data.add(prod.getName());
        	        	//productNames.add(prod.getName());
        }

        //data = Arrays.sort(data.toArray());
        
//        Collections.sort(data, new Comparator<String>() {
//            @Override
//            public int compare(String s1, String s2) {
//                return s1.compareToIgnoreCase(s2);
//            }
//        });
        
        // TODO: Sorting also ProductsHelper.setFilteredList, not only data with string only!!!!!!!
        myAdapter = new CustomAdapter(this, productList);
        lv.setAdapter(myAdapter);
        
        //this.productNames = new ArrayAdapter<String>(this, R.layout.products_item, R.id.product_content, data);
        //this.productNames = new ProductListAdapter(this, R.id.product_content, data);
        //lv.setAdapter(productNames);
                
        /** NORMAL ADAPTER **/
//		arrad = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_multiple_choice, data); // simple_list_item_1
//		lv.setAdapter(arrad);
        
		lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				System.out.println("LV clicked..");
//				Product prod = (Product) adapter.getItemAtPosition(position);
				System.out.println("Item at position: " + position);
				System.out.println("Item from adapter: " + adapter.getItemAtPosition(position));
				System.out.println("Item from productsList: " + CopyOfProductActivity.this.productList.get(position));
				System.out.println("Item from filteredList (ProdHelper): " + ProductsHelper.getFilteredList().get(position));
//				Intent intent = new Intent(v.getContext(), ProductInfoActivity.class);
//				intent.putExtra("com.mobilefood.classes.Product", ProductActivity.this.productList.get(position));
//				startActivity(intent);
				CheckedTextView check = (CheckedTextView)v;
				check.setChecked(!check.isChecked());
				boolean click = !check.isChecked();
				check.setChecked(click);
				
				ProductsHelper.setCurrentItem(CopyOfProductActivity.this.getCurrentProductFromList(adapter.getItemAtPosition(position).toString()));
//				ProductInfoActivity.callMe(adapter.getContext());
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
               //ProductActivity.this.productNames.getFilter().filter(arg0); /* USED WITH NORMAL ADAPTER */
//        	   myAdapter.getFilter().filter(arg0.toString().toLowerCase());
               //arrad.getFilter().filter(arg0.toString().toLowerCase());
           }
       });
	}

	/**
	 * @return the productsList
	 */
	public List<Product> getProductList() {
		return productList;
	}

	/**
	 * @param productsList the productsList to set
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
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

	public Product getCurrentProductFromList(String prodName)
	{
		Product currProd = null;
		for (Product prod : this.productList)
		{
			if (prod.getName().equalsIgnoreCase(prodName))
				currProd = prod;
		}
		return currProd;
	}
}
