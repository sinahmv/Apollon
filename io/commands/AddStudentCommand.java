package apollon.io.commands;

import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;
import apollon.data.Student;

/**
 * Modelliert den Befehl zum Hinzufügen eines Studenten.
 * @author ufufe
 * @version 1.0
 */
public class AddStudentCommand extends Command {

    private static final String REGEX = "^student";
    private static final int EXPECTED_INPUT_LENGTH = 3;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_MATRICULATION_NUMB = 2;
    private static final String REGEX_NAME = "^[^ \\t\\n\\x0B\\f\\r]+$";
    private static final String REGEX_NUMB = "^[1-9]{1}[0-9]{4}$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public AddStudentCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        String name = splitInput[INDEX_NAME];
        String numb = splitInput[INDEX_MATRICULATION_NUMB];

        if (!name.matches(REGEX_NAME)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }
        if (!numb.matches(REGEX_NUMB)) {
            return new ExecutionResult(ERROR_NUMB_DOES_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }

        try {
            int matriculationNumb = Integer.parseInt(numb);
            Student student = new Student(name, matriculationNumb);
            getAdministration().addStudent(student);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }
        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

}
