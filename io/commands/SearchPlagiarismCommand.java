package apollon.io.commands;

import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zur Suche nach Plagiaten in studentischen Lösungen.
 * @author ufufe
 * @version 1.0
 */
public class SearchPlagiarismCommand extends Command {

    private static final String ERROR_WRONG_PARAMETER_COUNT = "The parameter count was unexpected.";
    private static final String REGEX = "^search-plagiarism";
    private static final int EXPECTED_INPUT_LENGTH = 2;
    private static final int INDEX_ASSIGNMENT = 1;

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public SearchPlagiarismCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);
        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        int assignment = Integer.parseInt(splitInput[INDEX_ASSIGNMENT]);

        try {
            getAdministration().searchPlagiarism(assignment);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }
        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

}
