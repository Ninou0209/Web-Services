/* IST & CC CLOSSET Marine LENOIR Guillaume */ 
package com.example.carservice;

import java.io.Serializable;

public class Car implements Serializable{
	
	/* ----------------------------------Declarations-------------------------------*/
	
	private String plateNumber;
	private String brand;
	private int price;
	
	/*-----------------------------------Constructeur------------------------------*/ 
	
	public Car() {
		super();
	}
	
	public Car(String plateNumber, String brand, int price) {
		super();
		this.plateNumber = plateNumber;
		this.brand = brand;
		this.price = price;
	}

	/* ------------------------------Acces en lecture et ecriture-------------------*/
	
	public String getPlateNumber() {
		return plateNumber;
	}
	
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	/* ------------------------------ Affichage -----------------------------------*/ 
	
	@Override
	public String toString() {
		return "Car [plateNumber=" + plateNumber + ", brand=" + brand + ", price=" + price + "]";
	}

}
