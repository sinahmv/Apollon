package apollon.io.commands;

import apollon.persistence.Administration;

/**
 * Modelliert den Befehl zum Neustart und Zurücksetzen des Programms.
 * @author ufufe
 * @version 1.0
 */
public class ResetCommand extends Command {

    private static final String REGEX = "^reset$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public ResetCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        return new ExecutionResult(ResultState.SUCCESS_RESET);

    }

}
