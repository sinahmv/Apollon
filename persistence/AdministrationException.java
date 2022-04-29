package apollon.persistence;

/**
 * Modelliert eine eigene Exception für das Programm.
 * @author ufufe
 * @version 1.0
 */
public class AdministrationException extends Throwable {

    /**
     * Gibt das Exception-Objekt zurück.
     * @param message Ausgabe.
     */
    public AdministrationException(String message) {
        super(message);
    }

}