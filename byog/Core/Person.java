package byog.Core;

import byog.TileEngine.TETile;

import java.io.*;

public class Person implements Serializable {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public static void save(Person s) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("peppe.ser"));
        objectOutputStream.writeObject(s);

    }

    public static Person load() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("peppe.ser"));
        Person s = (Person) objectInputStream.readObject();
        return s;
    }

    public static void main(String[] args) {
        Person mike = new Person("Peter Jeon");
/*
        try {
            save(mike);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

*/


        try {
            Person s = load();
            System.out.println(s.name);
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

