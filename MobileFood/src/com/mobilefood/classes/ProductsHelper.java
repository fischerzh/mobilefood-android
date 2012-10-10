package com.mobilefood.classes;

import java.util.ArrayList;
import java.util.List;

public class ProductsHelper {
	
	private static List<Product> productList;
	private static List<Product> filteredList;
	private static List<Product> productWatchList;
	
	private static Product currentItem;
	
	/**
	 * @return the productsList
	 */
	public static List<Product> getProductList() {
		return productList;
	}

	/**
	 * @param productsList the productsList to set
	 */
	public static void setProductList(List<Product> productList) {
		ProductsHelper.productList = productList;
	}

	/**
	 * @return the productWatchList
	 */
	public static List<Product> getProductWatchList() {
		return productWatchList;
	}

	/**
	 * @param productWatchList the productWatchList to set
	 */
	public static void setProductWatchList(List<Product> productWatchList) {
		ProductsHelper.productWatchList = productWatchList;
	}
	
	public static void addProductToWatchList(Product prod)
	{
		System.out.println("Add product to watch list: " + prod.getName());
		if (ProductsHelper.productWatchList != null)
		{
			ProductsHelper.productWatchList.add(prod);
		}
		else
		{
			ProductsHelper.initProductWatchList();
		}
	}
	
	public static void removeProductFromWatchList(Product prod)
	{
		ProductsHelper.productWatchList.remove(prod);
	}

	public static void initProductWatchList()
	{
		ProductsHelper.productWatchList = new ArrayList<Product>();
	}

	/**
	 * @return the filteredList
	 */
	public static List<Product> getFilteredList() {
		return filteredList;
	}

	/**
	 * @param filteredList the filteredList to set
	 */
	public static void setFilteredList(List<Product> filteredList) {
		ProductsHelper.filteredList = filteredList;
	}

	/**
	 * @return the currentItem
	 */
	public static Product getCurrentItem() {
		return currentItem;
	}

	/**
	 * @param currentItem the currentItem to set
	 */
	public static void setCurrentItem(Product currentItem) {
		ProductsHelper.currentItem = currentItem;
	}
}
