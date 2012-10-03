package com.mobilefood.classes;

import com.google.gson.annotations.SerializedName;

public class Product {
	
	@SerializedName("ean")
	private String ean;
	
	@SerializedName("name")
	private String name;

	@SerializedName("producer")
	private String producer;
	
	@SerializedName("kontrolleur")
	private String kontrolleur;
	
	
	/**
	 * @return the ean
	 */
	public String getEan() {
		return ean;
	}
	
	/**
	 * @param ean the ean to set
	 */
	public void setEan(String ean) {
		this.ean = ean;
	}
	
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