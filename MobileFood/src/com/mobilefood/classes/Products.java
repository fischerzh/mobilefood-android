package com.mobilefood.classes;

import com.j256.ormlite.field.DatabaseField;


public class Products {
	
	//public List<>
	
	@DatabaseField(generatedId = true)
	public int id;
	
	@DatabaseField(index=true)
	public String name;
	
	@DatabaseField
	public String producer;
	
	@DatabaseField
	public String kontrolleur;

	Products() {
		
	}
	
}
