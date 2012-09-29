package com.mobilefood.classes;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;


public class Products {
		
	public int id;
	
	@SerializedName("producer")
	public String producer;
	
	@SerializedName("kontrolleur")
	public String kontrolleur;
	
	@SerializedName("name")
	public String name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the producer
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * @param producer the producer to set
	 */
	public void setProducer(String producer) {
		this.producer = producer;
	}

	/**
	 * @return the kontrolleur
	 */
	public String getKontrolleur() {
		return kontrolleur;
	}

	/**
	 * @param kontrolleur the kontrolleur to set
	 */
	public void setKontrolleur(String kontrolleur) {
		this.kontrolleur = kontrolleur;
	}

	
}
