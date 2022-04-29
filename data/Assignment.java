package apollon.data;

import java.util.Objects;

/**
 * Modelliert eine Aufgabe, die zu Apollon hinzugefügt werden kann.
 * Besteht aus ihrer ID und ihrem Aufgabentext.
 * @author ufufe
 * @version 1.0
 */
public class Assignment implements Comparable<Assignment> {

    private final String message;
    private int number;

    /**
     * Gibt die Aufgabe mit ihrem Aufgabentext zurück.
     * @param message Der Aufgabentext.
     */
    public Assignment(String message) {
        this.message = message;
    }

    /**
     * Gibt den Aufgabentext zurück.
     * @return Der Aufgabentext.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gibt die Aufgabenkennung zurück.
     * @return Die Aufgabenkennung.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setzt die Aufgabenstellung fest.
     * @param id Die neue Aufgabenkennung, die festgelegt wird.
     * @return Die Aufgabenkennung.
     */
    public int setNumber(int id) {
        this.number = id;
        return id;
    }

    @Override
    public int compareTo(Assignment assignment) {
        if (this.number < assignment.getNumber()) {
            return -1;
        }
        else if (this.number > assignment.getNumber()) {
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
        if (!(object instanceof Assignment)) {
            return false;
        }
        Assignment assignment = (Assignment) object;
        return (this.number == assignment.getNumber());
    }

}
