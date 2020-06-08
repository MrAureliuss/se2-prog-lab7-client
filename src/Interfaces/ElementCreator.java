package Interfaces;

import BasicClasses.Person;
import BasicClasses.StudyGroup;

import java.util.ArrayList;

public interface ElementCreator {
    StudyGroup createStudyGroup();

    Person createPerson();

    StudyGroup createScriptStudyGroup(ArrayList<String> parameters);

    Person createScriptPerson(ArrayList<String> parameters);
}
