package com.mobilefood.classes.override;

import java.util.ArrayList;
import java.util.List;

import com.mobilefood.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter implements Filterable
{
    private final Context context;
    private List<String> filteredItems, items;
     
    private TextView tv;
    
    public CustomAdapter( Context context, List<String> items )
    {
        this.context = context;
        this.items = items;
        this.filteredItems = items;
    }
     
    @Override
    public int getCount()
    {
        return filteredItems.size();
    }
 
    @Override
    public Object getItem( int position )
    {
        return filteredItems.get( position );
    }
 
    @Override
    public long getItemId( int position )
    {
        return getItem( position ).hashCode();
    }
 
    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        View v = convertView;
        if ( v == null )
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate( R.layout.products_item, parent, false );
        }
        final String item = (String) getItem( position );
        tv = (TextView) v.findViewById( R.id.product_content );
        Button btn = (Button) v.findViewById(R.id.btn_info);
        
        tv.setText( item );

        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context,"Clicked on Item: " + CustomAdapter.this.tv.getText(), Toast.LENGTH_LONG).show();		
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
                CustomAdapter.this.filteredItems = (List<String>) results.values;
                CustomAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                System.out.println("**** PERFORM FILTERING for: " + constraint);
                List<String> filteredResults = getFilteredResults((String)constraint);
                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }
    
    private List<String> getFilteredResults(String constraint)
    {
    	System.out.println("getFilteredResults...");
    	List<String> filtered = new ArrayList<String>();
    	for(int i = 0; i < this.items.size(); i++)
        {
            String name = this.items.get(i).toString();
            if (name.toLowerCase().contains(constraint))
            {
                filtered.add(name);
            }
        }
    	return filtered;
    }
}