import java.io.*;

public class Person implements Serializable{
	
	//class variables
	private static boolean isHuman = true;
	private static final long serialVersionUID = 1L;
	//instance variables
	private String name;
	private int age;
	private Address address;
	private DateOfBirth dateOfBirth;
	
	//constructor
	public Person(String name, int age, Address address, DateOfBirth dateOfBirth) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
	
	}
	
	//custom serialize methods
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(this.name);//we use Object because String is a wrapped object class, not a primitive
		stream.writeInt(this.age);
		stream.writeObject(this.address);
		
		//we write the instance variables of the dateOfBirth object which don't implements Serializable
		stream.writeInt(this.dateOfBirth.getDay());
		stream.writeInt(this.dateOfBirth.getMonth());
		stream.writeInt(this.dateOfBirth.getYear());
	}
	
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException{
		this.name = (String) stream.readObject();
		this.age = (int) stream.readInt();
		this.address = (Address) stream.readObject();
		
		//we read the instance variables of the dateOfBirth and create a new DateOfBirth object
		
		int day = (int) stream.readInt();
		int month = (int) stream.readInt();
		int year = (int) stream.readInt();
		
		this.dateOfBirth = new DateOfBirth(day, month, year);
		
		
	}
	
	public String toString() {
		return "Name:" + this.name + " Age: " + this.age + " Address : " + this.address.getStreet() + " " + this.address.getNumber() + " Date of birth: " + this.dateOfBirth.getDay() + "/"
				+ this.dateOfBirth.getMonth() + "/"+ this.dateOfBirth.getYear() + " Is human?: " + this.isHuman + " serialVersionUID: "
				+ this.serialVersionUID;
		
	}

}
