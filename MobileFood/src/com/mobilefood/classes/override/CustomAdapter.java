package com.mobilefood.classes.override;

import java.util.List;

import com.mobilefood.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter
{
    private final Context context;
    private final List<String> items;
     
    public CustomAdapter( Context context, List<String> items )
    {
        this.context = context;
        this.items = items;
    }
     
    @Override
    public int getCount()
    {
        return items.size();
    }
 
    @Override
    public Object getItem( int position )
    {
        return items.get( position );
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
        TextView tv = (TextView) v.findViewById( R.id.product_content );
        tv.setText( item );

        return v;
    }
}