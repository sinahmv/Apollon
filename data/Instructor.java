package apollon.data;


/**
 * Modelliert eine Lehrperson, die zu Apollon hinzugefügt werden kann.
 * Hat einen eindeutigen Namen.
 * @author ufufe
 * @version 1.0
 */
public class Instructor {

    private final String name;

    /**
     * Erstellt eine Lehrperson mit eindeutigem Namen.
     * @param name Der Name der Lehrperson.
     */
    public Instructor(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen der Lehrperson zurück.
     * @return Name der Lehrperson.
     */
    public String getName() {
        return name;
    }

}
