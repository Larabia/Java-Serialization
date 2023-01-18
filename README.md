# Java-Serialization 🚀
You can use this project as a cheatsheet or a fast course on Java Serialization!

## Before we start ✋
> In this ReadMe you'll find information about **Serialization of Objects in Java.**  
> Also, this project includes java files with the **actual implementation**.

We will be reviewing the following topics:
- `Serializable` Java's interface.
- The use of `FileInputStream` and `ObjectInputStream` to **serialize objects to a file**.
- The use of `FileOutputStream` and `ObjectOutputStream` to **deserialize objects from a file**.
- `static` and `transient` fields in serialization.
- Custom Serialization.

**You can use this ReadMe as a cheatsheet and clone this project to run it in your IDE for further knowledge about how Output and Input work in Java.**

🛠️ This project was created with **Java8** and **Eclipse**(IDE).

So you'll need a **basic knowledge of Java**, an **IDE** and **Java 8 installed** and you are ready to go!

[To download and install Eclipse on Windows 10](https://www.youtube.com/watch?v=N-wXTRpR03U)

[To download and install Java8](https://www.youtube.com/watch?v=ClcHrcNXP9g)

## Serializable Java's interface 🤖

**Serialization** describes the process of **taking an object’s state and transforming it into a stream of bytes**:
- A stream is an abstract definition of a sequence of data that is made available over time.
- A byte is an 8 bit (0s or 1s) group of data.
- A stream of bytes is a sequence of bytes that is made available over time.

**To serialize an object, its fields and their types are stored in the form of bytes**. These bytes are then able to be written to memory, a file, a database, or sent over a network with all the information necessary to recreate the object.

**Deserialization** does the opposite of serialization and **converts a stream of bytes back into an object**. 

There are a couple of important details to note about serialization and deserialization:
- The stream of bytes created by serialization **only includes the member variables of an object and not its methods**.
- **Deserialization creates a copy of the original object**. This copy shares the same state but is an entirely new object in memory.

Java provides the `Serializable` interface to do just that. Remember that an interface **describes the behavior a class should have** and by having our class implement `Serializable` it can be serialized by the Java Virtual Machine (JVM).  
 `Serializable` is a **marker** interface. **A marker interface provides run time information to the JVM about the class and does not have any methods associated with it**.   
Although there is no need to implement any methods for serialization it is important for the implementing class to provide a `serialVersionUID`.

```java
public class Person implements Serializable {
  private String name;
  private int age;
  private static final long serialVersionUID = 1L;
}
``` 

Our `serializable` class can get a `serialVersionUID` in one of the following ways:
- You can have **the JVM define one for you**. This is not ideal because, depending on the JVM you have, you’ll get a different value and this may cause deserialization to fail.
- You can have **your IDE generate one for you**. This is better than the first option but you’ll need to ensure that your IDE has this feature.
- You can **define the serialVersionUID explicitly in the class**. This is the preferred option because you don’t need to rely on the JVM or your IDE to ensure proper deserialization.  

Although the JVM uses the `serialVersionUID` to locate the proper class, **it does not store the class file as part of the serialization**. We need to ensure we load the class file into our program.

## Serialize objects to a file 📠

We’ll need to use the helper classes, `FileOutputStream`, which will help us write to a file, and `ObjectOutputStream`, which will help us write a serializable object to an output stream.

1. Initialize a `FileOutputStream` object **which will create and write a stream of bytes to a file** and pass the name of the file as an argument (ex: person-file.txt).
2. Initialize an `ObjectOutputStream` object **which will help serialize an object to a specified output stream** and pass the `FileOutputStream` object as an argument.
3. Use **objectOutputStream.writeObject()** to serialize the michael and peter objects to a file.

```java
public class Person implements Serializable {
  private String name;
  private int age;
  private static final long serialVersionUID = 1L;
 
  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }  
 
  public static void main(String[] args) throws FileNotFoundException, IOException{
    Person michael = new Person("Michael", 26);
    Person peter = new Person("Peter", 37);
 
    FileOutputStream fileOutputStream = new FileOutputStream("persons.txt");
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
 
    objectOutputStream.writeObject(michael);
    objectOutputStream.writeObject(peter);    
  }
}

``` 

After the execution of the above program, the persons.txt will contain a stream of bytes that has the type and value information of the fields for the michael and peter objects respectively.  


## Deserializing an Object to a File 💾

We’ll make use of helper classes `FileInputStream`, which helps us read a file and `ObjectInputStream` which helps us read a serialized stream of bytes.

1. Initialize `FileInputStream` and `ObjectInputStream` in main() which will read a file and transform a stream of bytes into a Java object.
3. Create a Person object named **michaelCopy** by using **objectInputStream.readObject()** to read a serialized object.
4. Created a Person object named **peterCopy** by using **objectInputStream.readObject()** to read a serialized object.

```java
public class Person implements Serializable {
  public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
 
    FileInputStream fileInputStream = new FileInputStream("persons.txt");
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
 
    Person michaelCopy = (Person) objectInputStream.readObject();
    Person peterCopy = (Person) objectInputStream.readObject();
  }
}

``` 
The deserialized object shares the same field values as the original object but **is located in a different place in memory**.  
The deserialized objects **will be in the order that they were serialized in** and we need to **explicitly typecast the **readObject() return value** back into the type of object we serialized. 
A constructor is not called during deserialization for the deserialized type object. **Object fields are set using reflection**.  
A constructor is only called for the first non-serializable class in the parent hierarchy of the deserialized object.


### Exceptions 💥:
- The JVM ensures it deserializes the object using the correct class file by comparing the `serialVersionUID` in the class file with the one in the serialized object. If a match is not found an **InvalidClassException** is thrown. 
- **readObject()** throws a **ClassNotFoundException** when the class of the serialized object cannot be found.


## Static and transient fields in serialization 🕹️

- Recall that the JVM defines a default way of serializing objects; this default includes **ignoring `static` class fields**, which belong to a class and not an object.  
- The JVM also serializes all fields in an object, even those marked `private` and `final`.
- We can also instruct the JVM to ignore them using the `transient` keyword. **A field marked as `transient` will have its value ignored during serialization and instead receive the default value for that type of field**.

**You may find the need for transient fields if a field has its value generated based on other fields or, most importantly, if you have a reference field that is not serializable.**

```java
public class Car implements Serializable {
  private String make;
  private int year;
  private static final long serialVersionUID = 1L;
  private transient String model;  
  }

``` 

If we compare the original object with the deserialize one:

```
Toyota (Copy) - Car make is: Toyota, Car year is: 2021, Car model is: null, serialVersionUID: 1
Toyota (Original) - Car make is: Toyota, Car year is: 2021, Car model is: Corolla, serialVersionUID: 1
Is same object: false
```
**Recall that the == operator in Java compares the references (address locations) of two types.** Thats why `Is same object: false`.  
The deserialize (copy) object **will not get the default value for a static field (in our example the value 0), it will instead receive the current value of the static field** since program execution since static fields belong to the class and not the object.

## Custom Serialization 📑

**In order for reference types to be serializable, they must also implement the Serializable interface.** When the JVM encounters a reference type, it will try to serialize the reference type first before trying to serialize the **encapsulating object**. The implementation of **readObject()** and **writeObject()** is especially useful when:
- you have a reference field that **does not or cannot implement Serializable**. 
- You could also potentially handle `static` field values if you needed to persist them but this is **not a good practice** as a `static` field should belong to a class and not an object.

```java
public class DateOfBirth {
  private int month;
  private int day;
  private int year;
 
  // constructors and getters
}
 
public class Person implements Serializable {
  private String name;
  private DateOfBirth dateOfBirth;
 
  private void writeObject(java.io.ObjectOutputStream stream) throws IOException{
    stream.writeObject(this.name);
    stream.writeInt(this.dateOfBirth.getMonth());
    stream.writeInt(this.dateOfBirth.getDay());
    stream.writeInt(this.dateOfBirth.getYear());
  }
 
  private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
    this.name = (String) stream.readObject();
    int month = (int) stream.readInt();
    int day = (int) stream.readInt();
    int year = (int) stream.readInt();
    this.dateOfBirth = new DateOfBirth(month, day, year);
  }    
}

``` 

In the example above:
1. We’ve got two classes **Person** which implements `Serializable` and **DateOfBirth** which does not.
2. **Person** has a *reference field* of type **DateOfBirth**.
3. If we were to use the default serialization process we would get a `NotSeriliazableException` because **DateOfBirth** does not implement `Serializable`.
4. We implement **writeObject()**, which must throw `IOException`, to serialize a **DataOfBirth** object **by manually serializing all of its fields separately**. We also serialize the serializable String field.
5. We implement **readObject()**, which must throw `IOException` and `ClassNotFoundException`, **to deserialize a Person object by reading all the int fields that are a part of DataOfBirth and creating a new DateOfBirth object with the provided constructor**. This new object is used to set the dateOfBirth field in Person.













