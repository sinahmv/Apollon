package apollon.io.commands;

import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zum Markieren eines Plagiats.
 * @author ufufe
 * @version 1.0
 */
public class MarkPlagiarismCommand extends Command {

    private static final String REGEX = "^mark-plagiarism";
    private static final int EXPECTED_INPUT_LENGTH = 4;
    private static final int INDEX_TUTOR = 1;
    private static final int INDEX_STUDENT = 2;
    private static final int INDEX_ASSIGNMENT = 3;

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public MarkPlagiarismCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        String tutor = splitInput[INDEX_TUTOR];
        int student = Integer.parseInt(splitInput[INDEX_STUDENT]);
        int assignment = Integer.parseInt(splitInput[INDEX_ASSIGNMENT]);

        try {
            getAdministration().markPlagiarism(tutor, student, assignment);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }

        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

}
