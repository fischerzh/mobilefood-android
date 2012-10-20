package com.mobilefood.activity.old;

import java.util.ArrayList;

import com.mobilefood.activity.R;
import com.mobilefood.activity.R.id;
import com.mobilefood.activity.R.layout;
import com.mobilefood.classes.ProductsHelper;

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

public class ProductListMain extends Activity {
	private ListView lv;
	private EditText et;
	private ArrayAdapter<String> arrad;
	private String listview_array[] = { "ONE", "TWO", "THREE", "FOUR", "FIVE",
			"SIX", "SEVEN", "EIGHT", "NINE", "TEN" };
	private ArrayList<String> array_sort = new ArrayList<String>();
	int textlength = 0;
		
	public static void callMe(Context context)
	{
		Intent intent = new Intent(context, ProductListMain.class);
		context.startActivity(intent);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout._old_products_list_main);

		lv = (ListView) findViewById(R.id.ListView01);
		et = (EditText) findViewById(R.id.EditText01);
		
		this.arrad = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_multiple_choice, listview_array);
				android.R.layout.simple_list_item_multiple_choice, ProductsHelper.getProductListString());
		
		lv.setAdapter(arrad);
		
		lv.setTextFilterEnabled(true);
		lv.setClickable(true);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			  @Override
			  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			    System.out.println("Position clicked: " + position + " " + lv.getItemAtPosition(position));

			  }
			});
		
		et.addTextChangedListener(new TextWatcher()
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
	            ProductListMain.this.arrad.getFilter().filter(arg0);

	        }
	    });
	}
}
