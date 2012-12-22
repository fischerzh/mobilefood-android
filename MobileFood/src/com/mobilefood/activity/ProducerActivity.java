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

public class ProducerActivity extends Activity{
	
	private ListView listView;
	private ArrayAdapter adapter;
	private EditText editTxt;
    private ArrayList<Product> originalProducts;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.producers_main);
        setTitle("");

        listView = (ListView) findViewById(R.id.producer_list_view);
        editTxt = (EditText) findViewById(R.id.producer_search_box);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ProductsHelper.getProducerList());
        listView.setAdapter(adapter);
        
        listView.setTextFilterEnabled(true);
        listView.setClickable(true);
//        listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//				editTxt.setText("");
				String currentProducer;
				
				System.out.println("Position clicked: " + position + " " + listView.getItemAtPosition(position));
				
				currentProducer = (String) listView.getItemAtPosition(position);

			    ProductsHelper.setCurrentProducer(currentProducer);
//				ProductInfoActivity.callMe(adapter.getContext());
				Intent intent = new Intent(ProducerActivity.this, ProductActivity.class);
				intent.putExtra("Producer", ProductsHelper.getCurrentProducer());
				startActivityForResult(intent, 4);
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
        	   ProducerActivity.this.adapter.getFilter().filter(arg0);
           }
           
       });
        
        
	}
	
    public void onHomeClick(View view)
    {
    	onBackPressed();
    }
    
    @Override
    public void onBackPressed() {
    	System.out.println("Back pressed");
    	ProductsHelper.setCurrentProducer("");
    	MainActivity.callMe(ProducerActivity.this, false);

    }

}
