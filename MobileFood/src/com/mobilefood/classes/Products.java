package com.mobilefood.classes;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;


public class Products {
	
	//public List<>
	
	//@DatabaseField(generatedId = true)
	public int id;
	
	//@DatabaseField(index=true)
	@SerializedName("name")
	public String name;
	
	//@DatabaseField
	@SerializedName("producer")
	public String producer;
	
	//@DatabaseField
	@SerializedName("kontrolleur")
	public String kontrolleur;

	Products() {
		
	}
	
}
