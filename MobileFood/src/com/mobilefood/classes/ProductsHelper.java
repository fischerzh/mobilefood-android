package com.mobilefood.classes;

import java.util.List;

public class ProductsHelper {
	
	private static List<Products> productsList;

	/**
	 * @return the productsList
	 */
	public static List<Products> getProductsList() {
		return productsList;
	}

	/**
	 * @param productsList the productsList to set
	 */
	public static void setProductsList(List<Products> productsList) {
		ProductsHelper.productsList = productsList;
	}
	
	

}
