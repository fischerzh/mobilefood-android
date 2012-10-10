package com.mobilefood.classes.override;

import com.mobilefood.activity.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class BarcodeAlertDialog extends AlertDialog 
{

	public BarcodeAlertDialog (Context context, String text, int theme) {
		super(context, theme); 
		setMessage(text); 
		setCancelable(true); 
		setButton("Ok", new DialogInterface.OnClickListener() 
		{
 
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
 	}

}