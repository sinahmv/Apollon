package apollon.data;

/**
 * Modelliert einen Tutor, der zu Apollon hinzugefügt werden kann.
 * Besitzt seinen eindeutigen Namen.
 * @author ufufe
 * @version 1.0
 */
public class Tutor {

    private final String name;

    /**
     * Erstellt einen Tutor mit eindeutigem Namen.
     * @param name Der Name des Tutors.
     */
    public Tutor(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen des Tutors zurück.
     * @return Der Name des Tutors.
     */
    public String getName() {
        return name;
    }

}
