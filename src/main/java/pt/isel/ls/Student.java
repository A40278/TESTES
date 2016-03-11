package pt.isel.ls;

/**
 * Created by EduW on 09/03/2016.
 */
public class Student {
    private String name;
    private int age, number;
    private String gender;

    public Student(String name, int age, int number, String gender) {
        this.name = name;
        this.age = age;
        this.number = number;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getNumber() {
        return number;
    }

    public String getGender() {
        return gender;
    }
}
