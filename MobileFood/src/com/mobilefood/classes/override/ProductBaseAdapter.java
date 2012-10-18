package com.mobilefood.classes.override;

import com.mobilefood.classes.Product;
import com.mobilefood.classes.ProductsHelper;
import com.mobilefood.activity.R;
import android.content.Context;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProductBaseAdapter extends BaseAdapter{
	
	private static ArrayList<Product> resultList;
	
	private LayoutInflater mInflater;
	private Context cont;
	
	private int checkBoxCounter, checkBoxInitialized;
	
	public ProductBaseAdapter(Context context, ArrayList<Product> items)
	{
		resultList = items;
		setCont(context);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return resultList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		
//		final int pos = position;
		
		checkBoxCounter = 0;
		checkBoxInitialized = 0;
		
		//Initialize convertView
		if(convertView==null)
		{
			convertView = mInflater.inflate(R.layout.product_item2, null);
			holder = new ViewHolder();
			holder.setTxtName((TextView)convertView.findViewById(R.id.prod_item_name));
			holder.setTxtProducer((TextView)convertView.findViewById(R.id.prod_item_producer));
			holder.setTxtEan((TextView)convertView.findViewById(R.id.prod_item_ean));
			holder.setChkBox((CheckBox)convertView.findViewById(R.id.prod_item_checkbox));
//			convertView.setTag(holder);
			holder.getChkBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int getPosition = (Integer) buttonView.getTag();
					System.out.println("Checked item at Position: " + getPosition + " " + resultList.get(getPosition).getName() + " set to Favorite " + isChecked);
//					Product currentProd = (Product) holder.chkBox.getTag();
//					currentProd.setFavorite(buttonView.isChecked());
					resultList.get(getPosition).setFavorite(buttonView.isChecked());
					if(isChecked)
					{
//						Toast.makeText(getCont(), "You selected" + resultList.get(getPosition).getName(), Toast.LENGTH_SHORT).show();
						resultList.get(getPosition).setFavorite(true);
					    ProductsHelper.addProductToWatchList(resultList.get(getPosition));
					}
					else					
					{
//						Toast.makeText(getCont(), "Not selected" + resultList.get(getPosition).getName(), Toast.LENGTH_SHORT).show();
						resultList.get(getPosition).setFavorite(false);
					    ProductsHelper.removeProductFromWatchList(resultList.get(getPosition));
					}
					
				}
			});
			
			convertView.setTag(holder);
			convertView.setTag(R.id.prod_item_name, holder.txtName);
			convertView.setTag(R.id.prod_item_checkbox, holder.chkBox);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();

		}
		holder.getChkBox().setTag(position);
		//setValues
		holder.getTxtName().setText(resultList.get(position).getName());
		holder.getTxtProducer().setText(resultList.get(position).getProducer());
		holder.getTxtEan().setText(resultList.get(position).getEan());
		holder.getChkBox().setChecked(resultList.get(position).isFavorite());
		
		ProductsHelper.setProductList(resultList);
			
		return convertView;
	
	}
	
	
	
	/**
	 * @return the cont
	 */
	public Context getCont() {
		return cont;
	}

	/**
	 * @param cont the cont to set
	 */
	public void setCont(Context cont) {
		this.cont = cont;
	}



	static class ViewHolder 
	{
		private TextView txtName;
		private TextView txtProducer;
		private TextView txtEan;
		private CheckBox chkBox;
		private RelativeLayout item;
		

		/**
		 * @return the txtCategory
		 */
		public TextView getTxtName() {
			return txtName;
		}
		/**
		 * @param txtName the txtName to set
		 */
		public void setTxtName(TextView txtName) {
			this.txtName = txtName;
		}
		/**
		 * @return the txtDueDate
		 */
		public TextView getTxtEan() {
			return txtEan;
		}
		/**
		 * @param txtDueDate the txtDueDate to set
		 */
		public void setTxtEan(TextView txtEan) {
			this.txtEan = txtEan;
		}
		/**
		 * @return the txtDescription
		 */
		public TextView getTxtProducer() {
			return txtProducer;
		}
		/**
		 * @param txtDescription the txtDescription to set
		 */
		public void setTxtProducer(TextView txtProducer) {
			this.txtProducer = txtProducer;
		}
		/**
		 * @return the chkBox
		 */
		public CheckBox getChkBox() {
			return chkBox;
		}
		/**
		 * @param chkBox the chkBox to set
		 */
		public void setChkBox(CheckBox chkBox) {
			this.chkBox = chkBox;
		}
		
		
		
	}
	

}
