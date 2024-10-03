package com.application.Pojo;

public class Product {

	private int id;
	
	private String product_name;
	
	private String brand;
	
	private Number inventory;
	
	private int category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Number getInventory() {
		return inventory;
	}

	public void setInventory(Number inventory) {
		this.inventory = inventory;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int categpory) {
		this.category = categpory;
	}
}
