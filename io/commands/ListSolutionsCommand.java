package apollon.io.commands;

import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zum Auflisten aller studentischen Lösungen.
 * @author ufufe
 * @version 1.0
 */
public class ListSolutionsCommand extends Command {

    private static final String REGEX = "^list-solutions";
    private static final int EXPECTED_INPUT_LENGTH = 2;
    private static final int INDEX_ASSIGNMENT = 1;
    private static final String REGEX_NUMB = "^[0-9]+$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public ListSolutionsCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        String assignment = splitInput[INDEX_ASSIGNMENT];

        // Semantic check
        if (!assignment.matches(REGEX_NUMB)) {
            return new ExecutionResult(ERROR_NUMBER_DOES_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }

        try {
            int assignmentNumb = Integer.parseInt(assignment);
            getAdministration().getSolutions(assignmentNumb);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }

        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

}
