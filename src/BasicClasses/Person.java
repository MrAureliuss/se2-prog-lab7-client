package BasicClasses;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private int height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;

    public Person(String name, int height, Color eyeColor, Color hairColor, Country nationality) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public int compareValue() {
        return this.name.length() + height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Person)) {
            return false;
        }

        Person person = (Person) obj;
        return name.equals(person.getName()) &&
                height == person.getHeight() &&
                eyeColor.equals(person.getEyeColor()) &&
                hairColor.equals(person.getHairColor()) &&
                nationality.equals(person.getNationality());
    }
}
