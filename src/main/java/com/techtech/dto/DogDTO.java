package com.techtech.dto;

public class DogDTO {
	Integer did;
	String name;
	String color;
	
	public DogDTO() {} //default constructor
	
	public DogDTO(int did, String name, String color) { //when making parameterized constructor, good practice to create a default constructor 
		super();
		this.did = did;
		this.name = name;
		this.color = color;
	}
	
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return "DogDTO [did=" + did + ", name=" + name + ", color=" + color + "]";
	}
}
