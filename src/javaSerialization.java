import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class javaSerialization {
	
	 public static void main(String[] args) {
		 
		 //creating an instance of Person
		 Person personjhon = new Person("Jhon", 23, new Address("Lincon st.", 345), new DateOfBirth(17, 06, 1999));
		 
		 //serializing personjhon into a file
		 try {
			 
			FileOutputStream fileOutput = new FileOutputStream("createdFiles/person.txt");
			ObjectOutputStream streamOutput = new ObjectOutputStream(fileOutput);
			streamOutput.writeObject(personjhon);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		//deserializing person.txt into an object
		 Person personJhonCopy = null;
		 
		 try {
			 
			FileInputStream fileInput = new FileInputStream("createdFiles/person.txt");
			ObjectInputStream streamInput = new ObjectInputStream(fileInput);
			personJhonCopy = (Person) streamInput.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
		 System.out.println("personjhon");
		 System.out.println(personjhon.toString());
		 
		 System.out.println("personJhonCopy");
		 System.out.println(personJhonCopy.toString());
		 
		 
	}

}
