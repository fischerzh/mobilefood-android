package com.mobilefood.classes.override;

import com.mobilefood.activity.MainActivity;
import com.mobilefood.activity.ProducerActivity;
import com.mobilefood.activity.ProductInfoActivity;
import com.mobilefood.classes.ProductsHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class BarcodeAlertDialog extends AlertDialog 
{
	
	public BarcodeAlertDialog (Context context, String text, int theme, boolean found, String[] producerList) {
		super(context, theme); 
		setMessage(text); 
		setCancelable(true); 
		final String[] prodList = producerList;
		if(found)
		{
			setButton(BUTTON_POSITIVE, "Zur Hersteller Liste", new DialogInterface.OnClickListener() 
			{
	 
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					Intent intent = new Intent(getContext(), ProducerActivity.class);
					ProductsHelper.setProducerListFromSearch(prodList);
//					ProductsHelper.setProdSearchText(ProductsHelper.getCurrentItem().getName());
//		    		intent.putExtra("ProducerList", prodList);
		    		getContext().startActivity(intent);
				}
			});	
			setButton(BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() 
			{
	 
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
		}
	
	}

	public BarcodeAlertDialog (Context context, String text, int theme, boolean found) {
		super(context, theme); 
		
		setMessage(text); 
		setCancelable(true); 
		if(found)
		{
			setButton(BUTTON_POSITIVE, "Zum Produkt", new DialogInterface.OnClickListener() 
			{
	 
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					Intent intent = new Intent(getContext(), ProductInfoActivity.class);
					getContext().startActivity(intent);
				}
			});	
			setButton(BUTTON_NEGATIVE, "Abbrechen", new DialogInterface.OnClickListener() 
			{
	 
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
		}
		else
		{
			setButton(BUTTON_NEGATIVE, "OK", new DialogInterface.OnClickListener() 
			{
	 
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
//					dialog.dismiss();
				}
			});
		}


 	}

}