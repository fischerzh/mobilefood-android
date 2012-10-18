package com.mobilefood.classes.override;

import java.util.ArrayList;
import java.util.List;

import com.mobilefood.activity.ProductActivity;
import com.mobilefood.activity.ProductInfoActivity;
import com.mobilefood.activity.R;
import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter
{
	
    private final Context context;
//    private List<String> filteredItems, items;
    private List<Product> productItems, filteredItems;
    private int pos;
         
    public CustomAdapter( Context context, List<Product> items )
    {
        this.context = context;
        //this.items = items;
        this.productItems = items;
        this.filteredItems = items;
    }
         
 
    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
    	ViewHolder holder; // to reference the child views for later actions
        View v = convertView;
        this.pos = position;
        if ( v == null )
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate( R.layout.products_item, parent, false );
            
            //cache view fields into holder
            holder = new ViewHolder();
            holder.tv = (TextView) v.findViewById( R.id.product_content );
            holder.chkbx = (CheckBox) v.findViewById(R.id.product_chk_box);
            
            // associate holder with view for later lookup
            v.setTag(holder);
        }
        else
        {
        	holder = (ViewHolder) v.getTag();
        }

//        tv = (TextView) v.findViewById( R.id.product_content );
        
        
        holder.tv.setText(filteredItems.get(position).getName());
        
        
//        CheckBox chkbx = (CheckBox) v.findViewById(R.id.product_chk_box);
        holder.chkbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					ProductsHelper.addProductToWatchList(ProductsHelper.getProductList().get(CustomAdapter.this.pos));
				}
				else
				{
					ProductsHelper.removeProductFromWatchList(ProductsHelper.getProductList().get(CustomAdapter.this.pos));
				}
			}
		});
        
        return v;
    }
    
    public void startProductInfoAcitivty()
    {
    	ProductInfoActivity.callMe(CustomAdapter.this.context);
    }
    
 // somewhere else in your class definition
    static class ViewHolder {
        TextView tv;
        CheckBox chkbx;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}