import java.io.*;

public class Address implements Serializable{
	//instance variables
	private String street;
	private int number;
	
	//constructor
	public Address(String street, int number) {
		this.street = street;
		this.number = number;
	}
	
	
	//getters and setters
	public String getStreet() {
		return this.street;
		
	}
	
	public void setStreet(String street) {
		this.street = street;
		
	}
	
	public int getNumber() {
		return this.number;
		
	}
	
	public void setNumber(int number) {
		this.number = number;
		
	}

}
