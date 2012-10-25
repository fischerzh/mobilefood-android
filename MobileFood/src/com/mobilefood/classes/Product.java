package com.mobilefood.classes;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("id")
	private int id;
	
	@SerializedName("ean")
	private String ean;
	
	@SerializedName("name")
	private String name;

	@SerializedName("producer")
	private String producer;
	
	@SerializedName("kontrolleur")
	private String kontrolleur;

	@SerializedName("category")
	private String category;
	
	@SerializedName("contents")
	private String contents;
	
	private boolean isFavorite;

	
	public String toString()
	{
		return name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

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

	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}


	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isFavorite() {
		return isFavorite;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	
	
}