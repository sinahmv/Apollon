package apollon.io.commands;

import apollon.data.Instructor;
import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zum Hinzufügen einer Lehrperson.
 * @author ufufe
 * @version 1.0
 */
public class AddInstructorCommand extends Command {

    private static final String REGEX = "^instructor";
    private static final int EXPECTED_INPUT_LENGTH = 2;
    private static final int INDEX_NAME = 1;
    private static final String REGEX_NAME = "^[^ \\t\\n\\x0B\\f\\r]+$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public AddInstructorCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        String name = splitInput[INDEX_NAME];

        if (!name.matches(REGEX_NAME)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }

        Instructor instructor = new Instructor(name);
        try {
            getAdministration().addInstructor(instructor);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }
        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

}
