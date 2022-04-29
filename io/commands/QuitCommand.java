package apollon.io.commands;

import apollon.persistence.Administration;

/**
 * Modelliert den Befehl zum Beenden des Programms.
 * @author ufufe
 * @version 1.0
 */
public class QuitCommand extends Command {

    private static final String REGEX = "^quit$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public QuitCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        return new ExecutionResult(ResultState.SUCCESS_QUIT);
    }

}
