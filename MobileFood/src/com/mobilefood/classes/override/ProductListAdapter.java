package com.mobilefood.classes.override;

import java.util.ArrayList;
import java.util.List;

import com.mobilefood.activity.R;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.override.CustomAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

public class ProductListAdapter extends ArrayAdapter<Product> implements Filterable
{
    private Activity ctx;

    private LayoutInflater inflator;
    
	private List<Product> originalValues;
	private List<Product> filteredValues;
	private Filter mFilter;
	
	ViewHolder viewHolder;
	private int pos;

	public ProductListAdapter(Activity context, List<Product> items) {
		super(context, R.layout.products_item, items);
		// TODO Auto-generated constructor stub
		System.out.println("ProductListAdapter: got Products " + items.size());
		this.ctx = context;
		
		originalValues = new ArrayList<Product>();
		originalValues.addAll(items);
		
		filteredValues = new ArrayList<Product>();
		filteredValues.addAll(items);
		
		inflator = ctx.getLayoutInflater();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = null;
		this.pos = position;
		Product prod = filteredValues.get(position);
		System.out.println("ProductListAdapter: getView " + prod.toString() + " " + filteredValues.get(position) );
		viewHolder = null;
		
		if(convertView == null)
		{
			view = inflator.inflate(R.layout.products_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tv = (TextView) view.findViewById(R.id.product_content);
//			viewHolder.btn = (Button) view.findViewById(R.id.product_btn_info);
			viewHolder.chkbx = (CheckBox) view.findViewById(R.id.product_chk_box);
			
//			viewHolder.btn.setTag(position);
			view.setTag(viewHolder);
			
		}
		else
		{
			view = convertView;
            viewHolder = ((ViewHolder) view.getTag());
		}
		
		viewHolder.tv.setText(prod.getName());
        viewHolder.chkbx.setChecked(prod.isFavorite());
        
        return view;
	}

    static class ViewHolder {
        TextView tv;
//        Button btn;
        CheckBox chkbx;
    }
}
