package com.mobilefood.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProductsHelper {
	
	private static List<Product> productList;
	private static List<Product> filteredList;
	private static List<Product> productWatchList;
	private static ArrayList<String> productListString;
	private static String[]	productName, productEAN;
	private static HashMap<String, String> productNameToEan;
	private static HashMap<Integer, Product> productFavorites;
	
	private static Product currentItem;
	private static String currentProducer = "";
	private static String currentCategory = "";
	private static String[] producerListFromSearch;
	
	/**
	 * @return the productsList
	 */
	public static List<Product> getProductList() {
		Collections.sort(productList);
		return productList;
	}

	/**
	 * @param productsList the productsList to set
	 */
	public static void setProductList(List<Product> productList) {
		ProductsHelper.productList = productList;
	}

	public static ArrayList<String> getProductListString()
	{
		productListString = new ArrayList<String>();
		productNameToEan = new HashMap<String, String>();
        for (Product prod : getProductList())
        {
        	productListString.add(prod.getName());
        	productNameToEan.put(prod.getName(), prod.getEan());
        }
//        Collections.sort(productListString);
//        System.out.println(productListString.toArray().toString());
        return productListString;
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
//		if (ProductsHelper.productWatchList != null)
//		{
//			ProductsHelper.productWatchList.add(prod);
//		}
//		else
//		{
//			ProductsHelper.initProductWatchList();
//		}
		if(ProductsHelper.getProductFavorites() == null)
			ProductsHelper.productFavorites = new HashMap<Integer, Product>();
		ProductsHelper.productFavorites.put(prod.getId(), prod);
	}
	
	public static void removeProductFromWatchList(Product prod)
	{
		System.out.println("Remove product from watch list: " + prod.getName());
//		ProductsHelper.productWatchList.remove(prod);
		ProductsHelper.productFavorites.remove(prod.getId());
	}

	public static void initProductWatchList()
	{
		ProductsHelper.productWatchList = new ArrayList<Product>();
	}
	
	public static ArrayList<String> getProductWatchListString()
	{
		productListString = new ArrayList<String>();
        for (Product prod : getProductWatchList())
        {
        	productListString.add(prod.getName());
        }
        return productListString;
	}

	/**
	 * @return the productFavorites
	 */
	public static HashMap<Integer, Product> getProductFavorites() {
		return productFavorites;
	}

	/**
	 * @param productFavorites the productFavorites to set
	 */
	public static void setProductFavorites(HashMap<Integer, Product> productFavorites) {
		ProductsHelper.productFavorites = productFavorites;
	}

	/**
	 * @return the filteredList
	 */
	public static List<Product> getFilteredList() 
	{
		return filteredList;
	}

	/**
	 * @param filteredList the filteredList to set
	 */
	public static void setFilteredList(List<Product> filteredList) 
	{
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
	public static void setCurrentItem(Product currentItem) 
	{
		ProductsHelper.currentItem = currentItem;
	}
	
	public static Product findItemName(String name)
	{
		Product prodFound = null;
		for (Product prod : getProductList())
		{
			if (prod.getName().contentEquals(name))
			{
				prodFound = prod;
				break;
			}
		}
		return prodFound;
	}
	
	public static Product findItemEan(String ean)
	{
		Product prodFound = null;
		for (Product prod : getProductList())
		{
			if (prod.getEan().contentEquals(ean))
			{
				prodFound = prod;
				break;
			}
		}
		return prodFound;
	}
	
	/**
	 * @return the productNameToEan
	 */
	public static HashMap<String, String> getProductNameToEan() 
	{
		return productNameToEan;
	}

	/**
	 * @param productNameToEan the productNameToEan to set
	 */
	public static void setProductNameToEan(HashMap<String, String> productNameToEan) 
	{
		ProductsHelper.productNameToEan = productNameToEan;
	}

	public static void setCurrentProducer(String currentProducer) 
	{
		// TODO Auto-generated method stub
		ProductsHelper.currentProducer = currentProducer;
	}
	
	public static String getCurrentProducer()
	{
		return ProductsHelper.currentProducer;
	}

	public static List<String> getProducerList() 
	{
		// TODO Auto-generated method stub
		List<String> producerList = new ArrayList<String>();
		for(Product prod: getProductList())
		{
			if(!producerList.contains(prod.getProducer()))
				producerList.add(prod.getProducer());
		}
		return producerList;
	}
	
	public static void setCurrentCategory(String currentCategory) 
	{
		// TODO Auto-generated method stub
		ProductsHelper.currentCategory = currentCategory;
	}
	
	public static String getCurrentCategory() {
		// TODO Auto-generated method stub
		return ProductsHelper.currentCategory;
	}

	public static List<String> getCategoryList() 
	{
		// TODO Auto-generated method stub
		List<String> categoryList = new ArrayList<String>();
		for(Product prod: getProductList())
		{
			if(!categoryList.contains(prod.getCategory()))
				categoryList.add(prod.getCategory());
		}
		return categoryList;	
	}

	public static void setProducerListFromSearch(String[] producerList) {
		// TODO Auto-generated method stub
		ProductsHelper.producerListFromSearch = producerList;
	}

	public static String[] getProducerListFromSearch() {
		// TODO Auto-generated method stub
		return ProductsHelper.producerListFromSearch;
	}

}
