package apollon.io.commands;

import apollon.data.Solution;
import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zum Hinzufügen einer studentischen Lösung.
 * @author ufufe
 * @version 1.0
 */
public class AddSolutionCommand extends Command {

    private static final String REGEX = "^submit";
    private static final int EXPECTED_INPUT_LENGTH = 4;
    private static final int INDEX_ASSIGNMENT = 1;
    private static final int INDEX_MATRICULATION_NUMBER = 2;
    private static final int INDEX_SOLUTION = 3;
    private static final String REGEX_ASSIGNMENT = "^[0-9]*$";
    private static final String REGEX_MATRICULATION_NUMBER = "^[0-9]{5}$";
    private static final String REGEX_SOLUTION = "^[^ \\t\\n\\x0B\\f\\r]+$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public AddSolutionCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        String assignment = splitInput[INDEX_ASSIGNMENT];
        String matriculationNumber = splitInput[INDEX_MATRICULATION_NUMBER];
        String solutionText = splitInput[INDEX_SOLUTION];

        if (!assignment.matches(REGEX_ASSIGNMENT)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }
        if (!solutionText.matches(REGEX_SOLUTION)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }
        if (!matriculationNumber.matches(REGEX_MATRICULATION_NUMBER)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }

        Solution solution = new Solution(Integer.parseInt(assignment), Integer.parseInt(matriculationNumber),
                solutionText);
        try {
            getAdministration().addSolution(solution);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }
        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

}
