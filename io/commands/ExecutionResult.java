package apollon.io.commands;

import java.util.Objects;

/**
 * Modelliert ein Objekt, das aussagt, wie das Programm weiterarbeiten soll.
 * @author ufufe
 * @version 1.0
 */
public class ExecutionResult {
    private final String message;
    private final ResultState state;

    /**
     * Gibt das Objekt zurück.
     * @param state Programmstatus nach Ausführung.
     */
    public ExecutionResult(ResultState state) {
        this.message = null;
        this.state = state;
    }

    /**
     * Gibt das Objekt zurück.
     * @param state Programmstatus nach Ausführung.
     * @param message Ausgabe nach Ausführung.
     */
    public ExecutionResult(String message, ResultState state) {
        this.message = message;
        this.state = state;
    }

    /**
     * Gibt die Ausgabe zurück.
     * @return Aussage.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gibt den Status des Programms zurück.
     * @return Status.
     */
    public ResultState getState() {
        return state;
    }

    /**
     * Gibt zurück, ob das Objekt eine Ausgabe hat.
     * @return wahr, falls es eine Aussage gibt.
     */
    public boolean hasMessage() {
        return Objects.nonNull(message);
    }
}
