package Commands.Utils.Creaters;

import BasicClasses.*;
import Interfaces.*;
import com.google.inject.Inject;

import java.util.ArrayList;


/**
 * Классб содержащий методы для создания группы и человека.
 */
public class ElementCreatorImp implements ElementCreator {
    private final StringReader stringReader;
    private final RefIntReader refIntReader;
    private final PrimitiveIntReader primitiveIntReader;
    private final PrimitiveFloatReader primitiveFloatReader;
    private final FormOfEducationReader formOfEducationReader;
    private final ColorReader colorReader;
    private final CountryReader countryReader;
    private final SemesterReader semesterReader;

    @Inject
    public ElementCreatorImp(StringReader stringReader, RefIntReader refIntReader, PrimitiveIntReader primitiveIntReader
            , PrimitiveFloatReader primitiveFloatReader, FormOfEducationReader formOfEducationReader, ColorReader colorReader,
                             CountryReader countryReader, SemesterReader semesterReader) {
        this.stringReader = stringReader;
        this.refIntReader = refIntReader;
        this.primitiveIntReader = primitiveIntReader;
        this.primitiveFloatReader = primitiveFloatReader;
        this.formOfEducationReader = formOfEducationReader;
        this.colorReader = colorReader;
        this.countryReader = countryReader;
        this.semesterReader = semesterReader;
    }

    @Override
    public StudyGroup createStudyGroup() {
        String name = stringReader.read("Введите имя группы: ", false);
        Integer x = refIntReader.read("Введите X: ", false, 531, "MAX");
        float y = primitiveFloatReader.read("Введите Y: ", -653f, "MIN");
        Integer studentsCount = refIntReader.read("Введите количество студентов: ", false, 0, "MIN");
        FormOfEducation formOfEducation = formOfEducationReader.read(true);
        Semester semester = semesterReader.read(false);

        return new StudyGroup(name, new Coordinates(x, y), studentsCount, formOfEducation, semester, createPerson());
    }

    @Override
    public Person createPerson() {
        String groupAdminName = stringReader.read("Введите имя админа группы: ", false);
        int height = primitiveIntReader.read("Введите рост админа группы: ", 0, "MIN");
        Color eyeColor = colorReader.read("Введите цвет глаз Админа группы.", false);
        Color hairColor = colorReader.read("Введите цвет волос Админа группы", false);
        Country nationality = countryReader.read("Введите национальность Админа группы", false);

        return new Person(groupAdminName, height, eyeColor, hairColor, nationality);
    }

    @Override
    public StudyGroup createScriptStudyGroup(ArrayList<String> parameters) {
        if (validateArrayStudyGroup(parameters)) {
            FormOfEducation formOfEducation = null;
            if (!parameters.get(4).isEmpty()) { formOfEducation = FormOfEducation.valueOf(parameters.get(4).toUpperCase()); }
            return new StudyGroup(parameters.get(0),
                    new Coordinates(Integer.parseInt(parameters.get(1)), Float.parseFloat(parameters.get(2))),
                    Integer.parseInt(parameters.get(3)),
                    formOfEducation,
                    Semester.valueOf(parameters.get(5).toUpperCase()),
                    new Person(parameters.get(6), Integer.parseInt(parameters.get(7)), Color.valueOf(parameters.get(8).toUpperCase()),
                            Color.valueOf(parameters.get(9).toUpperCase()), Country.valueOf(parameters.get(10).toUpperCase())));
        } else { System.out.println("Один из параметров не соответствует требованиям."); }

        return null;
    }

    @Override
    public Person createScriptPerson(ArrayList<String> parameters) {
        if (validateArrayPerson(parameters)) {
            return new Person(parameters.get(0), Integer.parseInt(parameters.get(1)), Color.valueOf(parameters.get(2).toUpperCase()),
                    Color.valueOf(parameters.get(3).toUpperCase()), Country.valueOf(parameters.get(4).toUpperCase()));
        } else { System.out.println("Один из параметров не соответствует требованиям."); }

        return null;
    }

    private boolean validateArrayStudyGroup(ArrayList<String> parameters) {
        try {
            return !parameters.get(0).isEmpty()
                    && Integer.parseInt(parameters.get(1)) <= 531
                    && Float.parseFloat(parameters.get(2)) > -653f
                    && Integer.parseInt(parameters.get(3)) > 0
                    && (formOfEducationReader.checkExist(parameters.get(4)) || parameters.get(4).isEmpty())
                    && !parameters.get(5).isEmpty()
                    && !parameters.get(6).isEmpty()
                    && Integer.parseInt(parameters.get(7)) > 0
                    && colorReader.checkExist(parameters.get(8))
                    && colorReader.checkExist(parameters.get(9))
                    && countryReader.checkExist(parameters.get(10));

        } catch (NumberFormatException ex) { return false; }
    }

    private boolean validateArrayPerson(ArrayList<String> parameters) {
        try {
            return (!parameters.get(0).isEmpty() && parameters.get(0) != null) &&
                    Integer.parseInt(parameters.get(1)) > 0 &&
                    colorReader.checkExist(parameters.get(2)) &&
                    colorReader.checkExist(parameters.get(3)) &&
                    countryReader.checkExist(parameters.get(4));

        } catch (NumberFormatException ex) { return false; }
    }
}
