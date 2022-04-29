package apollon.io.commands;

import apollon.data.Assignment;
import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zur Ausführung des Hinzufügens einer Aufgabe mit Aufgabenstellung.
 * @author ufufe
 * @version 1.0
 */
public class AddAssignmentCommand extends Command {

    private static final String REGEX = "^assignment";
    private static final int EXPECTED_INPUT_LENGTH = 2;
    private static final int INDEX_NAME = 1;
    private static final String REGEX_OR_PATTERN = "%s%s%s";
    private static final String REGEX_MESSAGE = "^[^ \\t\\n\\x0B\\f\\r]+$";
    private static final String OUTPUT_ASSIGNMENT = "assignment id(";
    private static final String OUTPUT_ASSIGNMENT_END = ")";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public AddAssignmentCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        String message = splitInput[INDEX_NAME];

        if (!message.matches(REGEX_MESSAGE)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }

        Assignment assignment = new Assignment(message);
        try {
            getAdministration().addAssignment(assignment);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }
        return new ExecutionResult(String.format(REGEX_OR_PATTERN, OUTPUT_ASSIGNMENT, assignment.getNumber(),
                OUTPUT_ASSIGNMENT_END), ResultState.SUCCESS_CONTINUE);
    }
}
