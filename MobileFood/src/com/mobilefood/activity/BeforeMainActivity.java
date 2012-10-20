package com.mobilefood.activity;

import com.mobilefood.barcode.IntentIntegrator;
import com.mobilefood.classes.SharedPrefEditor;
import com.mobilefood.classes.override.BarcodeAlertDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BeforeMainActivity extends Activity
{
	private ArrayAdapter<String> adapter;
	
	private TextView textView;
	private ListView listView;
	private Button button;
	
	private SharedPrefEditor editor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        editor = new SharedPrefEditor(this);
        if(editor.getSelectionFromSharedPref() == null || editor.getSelectionFromSharedPref().equals(""))
        {
        	loadSelectionList();
        }
        else
        {
        	startMainActivity();
        }

    }
	
	public void loadSelectionList()
	{
        setContentView(R.layout.activity_before_main);
        
        this.textView = (TextView) findViewById(R.id.before_main_text);
        listView = (ListView) findViewById(R.id.before_main_listView);
        button = (Button) findViewById(R.id.before_main_button);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, editor.getSelectionList());
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
				// TODO Auto-generated method stub
				System.out.println("Selected: " + adapter.getItemAtPosition(pos).toString());
		        editor.setSelectionInSharedPref(adapter.getItemAtPosition(pos).toString());
				startMainActivity();
			}
        });
        
        button.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
        		BarcodeAlertDialog alertDialog= new BarcodeAlertDialog(view.getContext(), "Die Inhalte dieser App wurden mit gr��ter Sorgfalt erstellt. F�r die Richtigkeit, " +
        				"Vollst�ndigkeit und Aktualit�t der Inhalte k�nnen wir jedoch keine Gew�hr �bernehmen."
        				, R.style.style_disclaimer);
        		alertDialog.show();
			}
		});
	}

	
	public void startMainActivity()
	{
		Intent intent = new Intent(BeforeMainActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	

}
