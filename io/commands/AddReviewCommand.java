package apollon.io.commands;

import apollon.data.Review;
import apollon.persistence.Administration;
import apollon.persistence.AdministrationException;

/**
 * Modelliert den Befehl zum Hinzufügen einer Korrektur.
 * @author ufufe
 * @version 1.0
 */
public class AddReviewCommand extends Command {

    private static final String REGEX = "^review";
    private static final int EXPECTED_INPUT_LENGTH = 6;
    private static final int INDEX_TUTOR = 1;
    private static final int INDEX_ASSIGNMENT = 2;
    private static final int INDEX_STUDENT = 3;
    private static final int INDEX_GRADE = 4;
    private static final int INDEX_NOTE = 5;
    private static final String REGEX_ASSIGNMENT = "^[0-9]*$";
    private static final String REGEX_GRADE = "^[1-5]{1}$";
    private static final String REGEX_MATRICULATION_NUMBER = "^[0-9]{5}$";
    private static final String REGEX_SOLUTION = "^[^ \\t\\n\\x0B\\f\\r]+$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param administration Die Administration in der dieser Befehl ausgeführt wird.
     */
    public AddReviewCommand(Administration administration) {
        super(REGEX, administration);
    }

    @Override
    public ExecutionResult execute(String input) {

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT, EXPECTED_INPUT_LENGTH);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            return new ExecutionResult(ERROR_WRONG_PARAMETER_COUNT, ResultState.FAILURE_CONTINUE);
        }

        String tutorName = splitInput[INDEX_TUTOR];
        String assignmentNumber = splitInput[INDEX_ASSIGNMENT];
        String studentNumber = splitInput[INDEX_STUDENT];
        String gradeNumber = splitInput[INDEX_GRADE];
        String note = splitInput[INDEX_NOTE];

        if (!tutorName.matches(REGEX_SOLUTION)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }
        if (!assignmentNumber.matches(REGEX_ASSIGNMENT)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }
        if (!studentNumber.matches(REGEX_MATRICULATION_NUMBER)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }
        if (!gradeNumber.matches(REGEX_GRADE)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }
        if (!note.matches(REGEX_SOLUTION)) {
            return new ExecutionResult(ERROR_PARAMETERS_DO_NOT_MATCH, ResultState.FAILURE_CONTINUE);
        }

        Review review = new Review(tutorName, Integer.parseInt(assignmentNumber), Integer.parseInt(studentNumber),
                Integer.parseInt(gradeNumber), note);
        try {
            getAdministration().addReview(review);
        } catch (AdministrationException exception) {
            return new ExecutionResult(exception.getMessage(), ResultState.FAILURE_CONTINUE);
        }
        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }
}
