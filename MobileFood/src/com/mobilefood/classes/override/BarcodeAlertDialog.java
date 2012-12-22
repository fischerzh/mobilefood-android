package com.mobilefood.classes.override;

import com.mobilefood.activity.BeforeMainActivity;
import com.mobilefood.activity.ProductInfoActivity;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class BarcodeAlertDialog extends AlertDialog 
{

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