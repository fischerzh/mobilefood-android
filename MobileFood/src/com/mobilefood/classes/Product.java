package com.mobilefood.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Serializable, Comparable{
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("id")
	private int id;
	
	@SerializedName("ean")
	private String ean = " ";
	
	@SerializedName("name")
	private String name = " ";

	@SerializedName("producer")
	private String producer = " ";
	
	@SerializedName("controller")
	private String controller = " ";

	@SerializedName("category")
	private String category = "";
	
	@SerializedName("contents")
	private List<String> contents;
	
	@SerializedName("packaging")
	private String packaging = "";
	
	@SerializedName("chalavakum")
	private boolean chalavakum=false;
	
	@SerializedName("parve")
	private boolean parve=false;
	
	@SerializedName("comment")
	private String comment="";
	
	@SerializedName("prodfamcomment")
	private String prodfamcomment="";
	
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
		return (ean!=null?ean:"");
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
		return (name!=null?name:"");
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name.trim();
	}

	/**
	 * @return the producer
	 */
	public String getProducer() {
		return (producer!=null?producer:"");
	}

	/**
	 * @param producer the producer to set
	 */
	public void setProducer(String producer) {
		this.producer = producer;
	}

	/**
	 * @return the controller
	 */
	public String getController() {
		return (controller!=null?controller:"");
	}

	/**
	 * @param kontrolleur the controller to set
	 */
	public void setController(String controller) {
		this.controller = controller;
	}

	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return (category!=null?category:"");
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
	public List<String> getContents() {

		if(contents!=null)
		{
			return contents;
		}
		else
		{
			List<String> emptyContent = new ArrayList<String>();
			emptyContent.add("");
			return emptyContent;
		}
	}
	
	public String getContentList() {
		String delim = "";
		StringBuilder sb = new StringBuilder();
		if(getContents()!=null)
		{
			for (String c : getContents())
			{
				sb.append(delim).append(c);
				delim = ",";
			}
			return sb.toString();
		}
		else
		{
			return "";
		}

	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(List<String> contents) {
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

	/**
	 * @return the comment
	 */
	public String getComment() {
		return (comment!=null?comment:"");
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the packaging
	 */
	public String getPackaging() {
		return packaging;
	}

	/**
	 * @param packaging the packaging to set
	 */
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	/**
	 * @return the chalavakum
	 */
	public boolean isChalavakum() {
		return chalavakum;
	}

	/**
	 * @param chalavakum the chalavakum to set
	 */
	public void setChalavakum(boolean chalavakum) {
		this.chalavakum = chalavakum;
	}

	/**
	 * @return the parve
	 */
	public boolean isParve() {
		return parve;
	}

	/**
	 * @param parve the parve to set
	 */
	public void setParve(boolean parve) {
		this.parve = parve;
	}
	
	
	/**
	 * @return the prodfamcomment
	 */
	public String getProdFamComment() {
		return (prodfamcomment!=null?prodfamcomment:"");
	}

	/**
	 * @param prodfamcomment the prodfamcomment to set
	 */
	public void setProdFamComment(String prodfamcomment) {
		this.prodfamcomment = prodfamcomment;
	}

	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		Product prod = (Product)obj;
		return(this.name.compareToIgnoreCase(prod.getName()));

	}
	
	
}