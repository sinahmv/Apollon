package apollon.io.commands;

import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zum Auflisten aller Studenten.
 * @author ufufe
 * @version 1.0
 */
public class ListStudentsCommand extends Command {

    private static final String REGEX = "^list-students$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public ListStudentsCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        try {
            getAdministration().getStudents();
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }

        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

}
