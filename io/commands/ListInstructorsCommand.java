package apollon.io.commands;

import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zum Auflisten aller Lehrpersonen.
 * @author ufufe
 * @version 1.0
 */
public class ListInstructorsCommand extends Command {

    private static final String REGEX = "^list-instructors$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public ListInstructorsCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        try {
            getAdministration().getInstructors();
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }

        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);

    }
}
