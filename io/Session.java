package apollon.io;

import apollon.io.commands.Command;
import apollon.io.commands.ExecutionResult;
import apollon.io.commands.ResultState;
import apollon.io.commands.AddAssignmentCommand;
import apollon.io.commands.AddInstructorCommand;
import apollon.io.commands.AddReviewCommand;
import apollon.io.commands.AddSolutionCommand;
import apollon.io.commands.AddTutorCommand;
import apollon.io.commands.AddStudentCommand;
import apollon.io.commands.ListInstructorsCommand;
import apollon.io.commands.ListReviewsCommand;
import apollon.io.commands.ListSolutionsCommand;
import apollon.io.commands.ListStudentsCommand;
import apollon.io.commands.ListTutorsCommand;
import apollon.io.commands.MarkPlagiarismCommand;
import apollon.io.commands.QuitCommand;
import apollon.io.commands.ResetCommand;
import apollon.io.commands.SearchPlagiarismCommand;
import apollon.io.commands.SummaryTasksCommand;
import apollon.persistence.Administration;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Modelliert die Session.
 * @author ufufe
 * @version 1.0
 */
public class Session {

    private static final String ERROR_NO_COMMAND_GIVEN = "Error, no command was given.";
    private Administration administration;
    private LinkedList<Command> commands;

    /**
     * Gibt das Session-Objekt zurück.
     */
    public Session() {
        this.initializeAdministration();
    }

    /**
     * Startet das Programm und lässt es laufen.
     */
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            Command command = findMatchingCommand(input);
            if (Objects.isNull(command)) {
                System.out.println(ERROR_NO_COMMAND_GIVEN);
                continue;
            }

            ExecutionResult executionResult = command.execute(input);

            if (executionResult.getState() == ResultState.SUCCESS_CONTINUE && executionResult.hasMessage()) {
                System.out.println(executionResult.getMessage());
            } else if (executionResult.getState() == ResultState.FAILURE_CONTINUE && executionResult.hasMessage()) {
                System.out.println(executionResult.getMessage());
            } else if (executionResult.getState() == ResultState.SUCCESS_RESET) {
                this.initializeAdministration();
            }

            if (executionResult.getState() == ResultState.FAILURE_QUIT
                    || executionResult.getState() == ResultState.SUCCESS_QUIT) {
                break;
            }
        }
    }

    /**
     * Initialisiert eine neue Administration.
     */
    private void initializeAdministration() {
        Administration newAdministration = new Administration();
        LinkedList<Command> newCommands = new LinkedList<>();
        newCommands.add(new QuitCommand(newAdministration));
        newCommands.add(new AddStudentCommand(newAdministration));
        newCommands.add(new ListStudentsCommand(newAdministration));
        newCommands.add(new AddInstructorCommand(newAdministration));
        newCommands.add(new ListInstructorsCommand(newAdministration));
        newCommands.add(new AddTutorCommand(newAdministration));
        newCommands.add(new ListTutorsCommand(newAdministration));
        newCommands.add(new AddAssignmentCommand(newAdministration));
        newCommands.add(new AddSolutionCommand(newAdministration));
        newCommands.add(new AddReviewCommand(newAdministration));
        newCommands.add(new ListSolutionsCommand(newAdministration));
        newCommands.add(new ListReviewsCommand(newAdministration));
        newCommands.add(new ResetCommand(newAdministration));
        newCommands.add(new SummaryTasksCommand(newAdministration));
        newCommands.add(new MarkPlagiarismCommand(newAdministration));
        newCommands.add(new SearchPlagiarismCommand(newAdministration));
        this.administration = newAdministration;
        this.commands = newCommands;
    }

    /**
     * Gibt den gesuchten Befehl zurück.
     * @return Den Befehl.
     */
    private Command findMatchingCommand(String input) {
        for (Command command : commands) {
            if (command.matchesInput(input)) {
                return command;
            }
        }
        return null;
    }
}