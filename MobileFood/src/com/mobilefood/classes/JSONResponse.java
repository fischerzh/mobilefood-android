package com.mobilefood.classes;

import java.util.List;

public class JSONResponse {
	
	public List<Products> products;

	/**
	 * @return the products
	 */
	public List<Products> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Products> products) {
		this.products = products;
	}

}
