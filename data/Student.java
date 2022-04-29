package apollon.data;

import java.util.Objects;

/**
 * Modelliert einen Studenten der zu Apollon hinzugefügt werden kann.
 * Besteht aus seinem Namen und seiner Matrikelnummer.
 * @author ufufe
 * @version 1.0
 */
public class Student implements Comparable<Student> {

    private final String name;
    private final int matriculationNumb;

    /**
     * Erstellt einen Studenten, mit Namen und eindeutiger Matrikelnummer.
     * @param matriculationNumb Die Matrikelnummer.
     * @param name Der Name des Studierenden.
     */
    public Student(String name, int matriculationNumb) {
        this.name = name;
        this.matriculationNumb = matriculationNumb;
    }

    /**
     * Gibt die Matrikelnummer zurück.
     * @return Die Matrikelnummer.
     */
    public int getMatriculationNumb() {
        return matriculationNumb;
    }

    /**
     * Gibt den Namen zurück.
     * @return Der Name.
     */
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Student student) {
        if (this.matriculationNumb < student.getMatriculationNumb()) {
            return -1;
        }
        else if (this.matriculationNumb > student.getMatriculationNumb()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof Student)) {
            return false;
        }
        Student student = (Student) object;
        return (this.matriculationNumb == student.getMatriculationNumb());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
