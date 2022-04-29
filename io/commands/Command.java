package apollon.io.commands;

import apollon.persistence.Administration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Modelliert einen Befehl für das Programm.
 * @author ufufe
 * @version 1.0
 */
public abstract class Command {

    /**
     * Gibt an, an welchem Zeichen Strings des Inputs aufgeteilt werden sollen.
     */
    protected static final String DELIMITER_COMMAND_DEFAULT = "\\s";
    /**
     * Ausgabe bei fehlerhaftem Input.
     */
    protected static final String ERROR_WRONG_PARAMETER_COUNT = "Error, the parameter count was unexpected.";
    /**
     * Ausgabe bei falscher Anzahl von Parametern im Input.
     */
    protected static final String ERROR_PARAMETERS_DO_NOT_MATCH = "Error, in parameters.";
    /**
     * Ausgabe bei fehlerhafter Matrikelnummer.
     */
    protected static final String ERROR_NUMB_DOES_NOT_MATCH = "Error, matriculation numbers must be positive "
            + "5-digit natural numbers!";
    /**
     * Ausgabe bei Eingabe eines ungültigen Parameters für eine Aufgabe.
     */
    protected static final String ERROR_NUMBER_DOES_NOT_MATCH = "Error, in assignment number";
    private final String regex;
    private final Administration administration;

    /**
     * Gibt den Befehl mit seinem Input-Regex und der zugehörigen Administration zurück.
     * @param regex Vorgabe, wie der Input semantisch aussehen sollte.
     * @param administration Administration.
     */
    protected Command(String regex, Administration administration) {
        this.regex = regex;
        this.administration = administration;
    }

    /**
     * Schaut, ob der Input richtig ist.
     * @param input Der Input aus den Kommandozeilenargumenten.
     * @return wahr, falls der Input richtig ist.
     */
    public boolean matchesInput(String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.find();
    }

    /**
     * Gibt die Administration zurück.
     * @return Administration.
     */
    public Administration getAdministration() {
        return administration;
    }

    /**
     * Abstrakte Methode zum Ausführen.
     * @param input Der Input aus den Kommandozeilenargumenten.
     * @return den Status des Programms.
     */
    public abstract ExecutionResult execute(String input);

}
