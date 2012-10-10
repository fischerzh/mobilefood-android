package com.mobilefood.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable{
	
	@SerializedName("ean")
	private String ean;
	
	@SerializedName("name")
	private String name;

	@SerializedName("producer")
	private String producer;
	
	@SerializedName("kontrolleur")
	private String kontrolleur;
	
	private boolean isFavorite;
	
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString()
	{
		return name;
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