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

public class CustomAdapter extends BaseAdapter implements Filterable
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
    public int getCount()
    {
//        return productItems.size();
        return filteredItems.size();
    }
 
    @Override
    public Object getItem( int position )
    {
//        return productItems.get( position );
    	return filteredItems.get(position);
    }
 
    @Override
    public long getItemId( int position )
    {
        return filteredItems.get( position ).hashCode();
    }
    
    public String getItemName(int position)
    {
    	return filteredItems.get(position).getName();
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
//            holder.btn = (Button) v.findViewById(R.id.product_btn_info);
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
        
//        button btn = (button) v.findviewbyid(r.id.product_btn_info);
        holder.btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				toast.maketext(context,"clicked on item: " + customadapter.this.tv.gettext(), toast.length_long).show();		
				System.out.println("view tag: " + v.getTag((int)CustomAdapter.this.getItemId(pos)));
				System.out.println("pos: " + pos);
				System.out.println("customadapter itemid: " + CustomAdapter.this.getItemId(pos));
				System.out.println("customadapter itemobj: " + CustomAdapter.this.getItem(pos).toString());
				System.out.println("customadapter filteredItem: " + ProductsHelper.getFilteredList().get(pos));
				ProductsHelper.setCurrentItem((Product)CustomAdapter.this.getItem(pos));
				Intent intent = new Intent(v.getContext(), ProductInfoActivity.class);
//				intent.putextra("com.mobilefood.classes.product", prod);
				//startproductinfoacitivty();
				v.getContext().startActivity(intent);
			}

		});
        
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

    @Override
    public Filter getFilter() {
    	    	    	
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                CustomAdapter.this.filteredItems = (List<Product>) results.values;
//            	CustomAdapter.this.productItems = (List<Product>) results.values;
                ProductsHelper.setFilteredList(CustomAdapter.this.filteredItems);
                CustomAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
            	
                System.out.println("**** PERFORM FILTERING for: " + constraint);
                List<Product> filteredResults = getFilteredResults((String)constraint);
                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }
    
    private List<Product> getFilteredResults(String constraint)
    {
    	CustomAdapter.this.filteredItems = CustomAdapter.this.productItems;
    	System.out.println("getFilteredResults...");
    	List<Product> filtered = new ArrayList<Product>();
    	for(int i = 0; i < this.getCount(); i++)
        {
            String name = getItemName(i);
            if (name.toLowerCase().contains(constraint))
            {
                filtered.add((Product) getItem(i));
            }
        }
    	return filtered;
    }
    
    public void startProductInfoAcitivty()
    {
    	ProductInfoActivity.callMe(CustomAdapter.this.context);
    }
    
 // somewhere else in your class definition
    static class ViewHolder {
        TextView tv;
        Button btn;
        CheckBox chkbx;
    }
}