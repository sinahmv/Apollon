package apollon.persistence;

import apollon.data.Assignment;
import apollon.data.Instructor;
import apollon.data.PlagiarsmQueue;
import apollon.data.Review;
import apollon.data.Solution;
import apollon.data.Student;
import apollon.data.Tutor;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Modelliert die Administration des Systems und speichert Daten.
 * @author ufufe
 * @version 1.0
 */
public class Administration {

    private static final String ERROR_STUDENT_EXISTS = "Error, this student already exists.";
    private static final String ERROR_INSTRUCTOR_EXISTS = "Error, this instructor already exists.";
    private static final String ERROR_NAME_FOUND_TUTOR = "Error, this person is a tutor.";
    private static final String ERROR_NAME_FOUND_INSTRUCTOR = "Error, this person is an instructor.";
    private static final String ERROR_TUTOR_EXISTS = "Error, this tutor already exists.";
    private static final String ERROR_ASSIGNMENT_EXISTS = "Error, this assignment already exists.";
    private static final String ERROR_WRONG_TUTOR = "Error, this assignment can only be reviewed by another tutor.";
    private static final String ERROR_NO_SOLUTIONS = "Error, there are no solutions.";
    private static final String ERROR_NO_REVIEWS = "Error, there are no reviews.";
    private static final String ERROR_NO_ASSIGNMENT = "Error, there is no assignment with this id.";
    private static final String ERROR_NO_TEACHER = "Error, there is no teacher saved in this system.";
    private static final String ERROR_REVIEW = "Error, theres already a review for this assignment.";
    private static final String ERROR_PLAGIARISM = "Error, this review is marked as an plagiarism.";
    private static final String ERROR_CANT_TOUCH = "Error, this review cannot be changed by this instructor.";
    private static final String ERROR_TUTOR_DOES_NOT_EXIST = "Error, this is not a tutor.";
    private static final String ERROR_ASSIGNMENT_DOES_NOT_EXIST = "Error, this is not an existing assignment.";
    private static final String ERROR_STUDENT_DOES_NOT_EXIST = "Error, this is not an existing student.";
    private static final String ERROR_NOT_ALL_REVIEWS = "Error, not all reviews are submitted.";
    private static final String ERROR_NO_SOLUTION = "Error, theres is no fitting solution submitted.";
    private static final String PATTERN = "%.2f";
    private static final String BRACKETS_OPEN = "(";
    private static final String SPACE = " ";
    private static final String BRACKETS_CLOSE = ")";
    private static final String COLON = ":";
    private static final String SQUARE_BRACKETS_OPEN = "[";
    private static final String SQUARE_BRACKETS_CLOSE = "]";
    private static final String COMMA = ",";
    private static final String MINUS = "-";
    private static final String SLASH = "/";
    private static final String EMPTY_STRING = "";
    private static final String FOR = "for";
    private static final String MARKED_AS_PLAGIARISM = "marked as plagiarism";
    private static final String MARK_FOR = "mark for";
    private static final String REMOVED_AND_GRADE = "removed and grade";
    private static final String RESTORED = "restored";
    private static final String REGEX = "[.]";
    private static final int MAX_LENGTH = 3;
    private static final double ONEHUNDRET = 100.0;
    private static final float ONEHUNDRET_FLOAT = 100;
    private static final int GRADE_FIVE = 5;

    private final HashMap<Integer, Student> students;
    private final HashMap<String, Instructor> instructors;
    private final HashMap<String, Tutor> tutors;
    private final HashMap<Integer, Assignment> assignments;
    private final HashMap<Solution, Integer> solutions;
    private final HashMap<Review, Integer> reviews;

    /**
     * Erstellt ein Administrations-Objekt.
     */
    public Administration() {
        this.students = new HashMap<>();
        this.instructors = new HashMap<>();
        this.tutors = new HashMap<>();
        this.assignments = new HashMap<>();
        this.solutions = new HashMap<>();
        this.reviews = new HashMap<>();
    }

    /**
     * Erstellt einen neuen Studenten und fügt ihn zum System hinzu.
     * @param student Student.
     */
    public void addStudent(Student student) throws AdministrationException {
        if (students.containsKey(student.getMatriculationNumb())) {
            throw new AdministrationException(ERROR_STUDENT_EXISTS);
        }
        students.put(student.getMatriculationNumb(), student);
    }

    /**
     * Erstellt einen neuen Lehrer und fügt ihn zum System hinzu.
     * @param instructor Lehrer.
     */
    public void addInstructor(Instructor instructor) throws AdministrationException {
        if (instructors.containsKey(instructor.getName())) {
            throw new AdministrationException(ERROR_INSTRUCTOR_EXISTS);
        }
        if (tutors.containsKey(instructor.getName())) {
            throw new AdministrationException(ERROR_NAME_FOUND_TUTOR);
        }
        instructors.put(instructor.getName(), instructor);
    }

    /**
     * Erstellt einen neuen Tutor und fügt ihn zum System hinzu.
     * @param tutor Tutor.
     */
    public void addTutor(Tutor tutor) throws AdministrationException {
        if (tutors.containsKey(tutor.getName())) {
            throw new AdministrationException(ERROR_TUTOR_EXISTS);
        }
        if (instructors.containsKey(tutor.getName())) {
            throw new AdministrationException(ERROR_NAME_FOUND_INSTRUCTOR);
        }
        tutors.put(tutor.getName(), tutor);
    }

    /**
     * Erstellt eine neue Aufgabe und fügt sie zum System hinzu.
     * @param assignment Aufgabe.
     */
    public void addAssignment(Assignment assignment) throws AdministrationException {
        if (tutors.isEmpty() && instructors.isEmpty()) {
            throw new AdministrationException(ERROR_NO_TEACHER);
        }
        if (assignments.containsKey(assignment.getNumber())) {
            throw new AdministrationException(ERROR_ASSIGNMENT_EXISTS);
        }
        int id = assignments.size() + 1;
        assignments.put(assignment.setNumber(id), assignment);
    }

    /**
     * Erstellt eine neue studentische Lösung und fügt sie zum System hinzu.
     * @param solution Lösung.
     */
    public void addSolution(Solution solution) throws AdministrationException {
        if (!assignments.containsKey(solution.getAssignment())) {
            throw new AdministrationException(ERROR_ASSIGNMENT_DOES_NOT_EXIST);
        }
        if (!students.containsKey(solution.getMatriculationNumber())) {
            throw new AdministrationException(ERROR_STUDENT_DOES_NOT_EXIST);
        }
        if (reviews.containsValue(solution.getAssignment())) {
            throw new AdministrationException(ERROR_REVIEW);
        }

        if (solutions.isEmpty()) {
            solutions.put(solution, solution.getAssignment());
        }
        else {
            for (Solution searchedSolution : solutions.keySet()) {
                if (solution.compareToSolutions(searchedSolution)) {
                    searchedSolution.setSolutionText(solution.getSolutionText());
                    return;
                }

            }
        }
        solutions.put(solution, solution.getAssignment());
    }

    /**
     * Erstellt eine neue Korrektur und fügt sie zum System hinzu.
     * @param review Korrektur.
     */
    public void addReview(Review review) throws AdministrationException {
        if (!tutors.containsKey(review.getTutor())) {
            throw new AdministrationException(ERROR_TUTOR_DOES_NOT_EXIST);
        }
        else if (!assignments.containsKey(review.getAssignment())) {
            throw new AdministrationException(ERROR_ASSIGNMENT_DOES_NOT_EXIST);
        }
        else if (!students.containsKey(review.getStudent())) {
            throw new AdministrationException(ERROR_STUDENT_DOES_NOT_EXIST);
        }

        for (Review searchedReview : reviews.keySet()) {
            if (searchedReview.compareToReview(review) && !(searchedReview.getTutor().equals(review.getTutor()))) {
                throw new AdministrationException(ERROR_WRONG_TUTOR);
            }
            else if (searchedReview.compareToReview(review) && !searchedReview.isMarkedAsPlagiarism()
                    && searchedReview.getTutor().equals(review.getTutor())) {
                searchedReview.setGrade(review.getGrade());
                searchedReview.setNote(review.getNote());
                return;
            }
            else if (searchedReview.compareToReview(review) && searchedReview.getTutor().equals(review.getTutor())
                    && searchedReview.isMarkedAsPlagiarism()) {
                throw new AdministrationException(ERROR_PLAGIARISM);
            }
        }
        reviews.put(review, review.getAssignment());
    }

    /**
     * Erstellt eine Ausgabe von allen im System gespeicherten Studenten.
     */
    public void getStudents() throws AdministrationException {
        LinkedList<Integer> outputList = new LinkedList<>(students.keySet());
        Comparator<Integer> order = Integer::compare;
        outputList.sort(order);
        for (int j = 0; j < outputList.size(); j++) {
            System.out.println(students.get(outputList.get(j)).getName() + SPACE + BRACKETS_OPEN
                    + students.get(outputList.get(j)).getMatriculationNumb() + BRACKETS_CLOSE);
        }
    }

    /**
     * Erstellt eine Ausgabe von allen im System gespeicherten Tutoren.
     */
    public void getTutors() throws AdministrationException {
        Tutor[] tutorList = tutors.values().toArray(new Tutor[0]);
        LinkedList<String> outputList = new LinkedList<>();
        for (int i = 0; i < tutorList.length; i++) {
            String outputToBeSorted = tutorList[i].getName();
            outputList.add(outputToBeSorted);
            Collections.sort(outputList);
        }
        for (int j = 0; j < outputList.size(); j++) {
            System.out.println(outputList.get(j));
        }
    }

    /**
     * Erstellt eine Ausgabe von allen im System gespeicherten Lehrern.
     */
    public void getInstructors() throws AdministrationException {
        Instructor[] instructorList = instructors.values().toArray(new Instructor[0]);
        LinkedList<String> outputList = new LinkedList<>();
        for (int i = 0; i < instructorList.length; i++) {
            String outputToBeSorted = instructorList[i].getName();
            outputList.add(outputToBeSorted);
            Collections.sort(outputList);
        }
        for (int j = 0; j < outputList.size(); j++) {
            System.out.println(outputList.get(j));
        }
    }

    /**
     * Erstellt eine Ausgabe von allen im System gespeicherten Lösungen zur gegebenen Aufgabe.
     * @param assignmentNumb Aufgaben-ID.
     */
    public void getSolutions(int assignmentNumb) throws AdministrationException {
        if (solutions.isEmpty()) {
            throw new AdministrationException(ERROR_NO_SOLUTIONS);
        }
        if (solutions.containsValue(assignmentNumb)) {

            LinkedList<Solution> solutionsList = new LinkedList<Solution>(solutions.keySet());
            Collections.sort(solutionsList);

            for (Solution solution:solutionsList) {
                if (solution.getAssignment() == assignmentNumb) {
                    System.out.println(students.get(solution.getMatriculationNumber()).getName() + SPACE + BRACKETS_OPEN
                            + solution.getMatriculationNumber() + BRACKETS_CLOSE + COLON + SPACE
                            + solution.getSolutionText());
                }
            }
        }
    }

    /**
     * Erstellt eine Ausgabe von allen im System gespeicherten Korrekturen zur gegebenen Aufgabe.
     * @param assignmentNumb Aufgaben-ID.
     */
    public void getReviews(int assignmentNumb) throws AdministrationException {
        if (reviews.isEmpty()) {
            throw new AdministrationException(ERROR_NO_REVIEWS);
        }
        if (reviews.containsValue(assignmentNumb)) {

            LinkedList<Review> listOfReviews = new LinkedList<Review>(reviews.keySet());
            Collections.sort(listOfReviews);

            for (Review review:listOfReviews) {
                if (review.getAssignment() == assignmentNumb) {
                    System.out.println(review.getTutor() + COLON + SPACE + review.getNote() + SPACE
                            + SQUARE_BRACKETS_OPEN + review.getStudent() + COMMA + SPACE + review.getGrade()
                            + SQUARE_BRACKETS_CLOSE);
                }
            }
        }
    }

    /**
     * Erstellt eine Ausgabe von allen im System gespeicherten Aufgaben zur Übersicht.
     */
    public void getTasks() throws AdministrationException {

        LinkedList<Assignment> printAssignment = new LinkedList<Assignment>(assignments.values());
        Collections.sort(printAssignment);
        LinkedList<Review> printReview = new LinkedList<Review>(reviews.keySet());
        Collections.sort(printReview);
        LinkedList<Solution> printSolution = new LinkedList<Solution>(solutions.keySet());
        Collections.sort(printSolution);

        for (Assignment assignment:printAssignment) {
            int amountOfReviews = 0;
            int amountOfSolutions = 0;
            float mid = 0;
            int sum = 0;
            int counter = 0;
            for (int j = 0; j < printReview.size(); j++) {
                if (printReview.get(j).getAssignment() == assignment.getNumber()) {
                    amountOfReviews = amountOfReviews + 1;
                    sum = sum + printReview.get(j).getGrade();
                    counter = counter + 1;
                }
            }
            for (int k = 0; k < printSolution.size(); k++) {
                if (printSolution.get(k).getAssignment() == assignment.getNumber()) {
                    amountOfSolutions = amountOfSolutions + 1;
                }
            }
            if (amountOfReviews == 0) {
                System.out.println(assignment.getNumber() + COLON + SPACE + assignment.getMessage() + SPACE
                        + SQUARE_BRACKETS_OPEN + MINUS + COMMA + SPACE
                        + amountOfReviews + SPACE + SLASH + SPACE + amountOfSolutions + SQUARE_BRACKETS_CLOSE);
            }
            else {
                mid = (float) sum / counter;
                double midOutput = Math.round(mid * ONEHUNDRET) / ONEHUNDRET;
                String out = Double.toString(midOutput);
                if (out.length() == MAX_LENGTH) {
                    String result = String.format(PATTERN, midOutput);
                    System.out.println(assignment.getNumber() + COLON + SPACE + assignment.getMessage() + SPACE
                            + SQUARE_BRACKETS_OPEN + result + COMMA + SPACE + amountOfReviews + SPACE + SLASH + SPACE
                            + amountOfSolutions + SQUARE_BRACKETS_CLOSE);
                }
                else {
                    System.out.println(assignment.getNumber() + COLON + SPACE + assignment.getMessage() + SPACE
                            + SQUARE_BRACKETS_OPEN + midOutput + COMMA + SPACE + amountOfReviews + SPACE + SLASH
                            + SPACE + amountOfSolutions + SQUARE_BRACKETS_CLOSE);
                }
            }
        }
    }

    /**
     * Erstellt eine Markierung eines Plagiats von einer Lehrperson für die Aufgabe eines Studenten.
     * @param teacher Lehrer.
     * @param student Student.
     * @param assignment Aufgaben-ID.
     */
    public void markPlagiarism(String teacher, int student, int assignment) throws AdministrationException {
        if (!instructors.containsKey(teacher)) {
            throw new AdministrationException(ERROR_NO_TEACHER);
        }
        if (!students.containsKey(student)) {
            throw new AdministrationException(ERROR_STUDENT_DOES_NOT_EXIST);
        }
        if (!assignments.containsKey(assignment)) {
            throw new AdministrationException(ERROR_NO_ASSIGNMENT);
        }
        if (!solutions.containsValue(assignment)) {
            throw new AdministrationException(ERROR_NO_SOLUTIONS);
        }

        boolean hasSolution = false;
        for (Solution solution : solutions.keySet()) {
            if (solution.getMatriculationNumber() == student && solution.getAssignment() == assignment) {
                hasSolution = true;
            }
        }
        if (!hasSolution) {
            throw new AdministrationException(ERROR_NO_SOLUTION);
        }
        boolean reviewBoolean = true;
        for (Solution solution : solutions.keySet()) {
            if (solution.getAssignment() == assignment) {
                boolean hasReview = false;
                for (Review review : reviews.keySet()) {
                    if (review.getAssignment() == assignment
                            && solution.getMatriculationNumber() == review.getStudent()) {
                        hasReview = true;
                    }
                }
                if (!hasReview) {
                    reviewBoolean = false;
                    break;
                }
            }
        }
        if (!reviewBoolean) {
            throw new AdministrationException(ERROR_NOT_ALL_REVIEWS);
        }
        for (Review review : reviews.keySet()) {
            if (review.getStudent() == student && review.getAssignment() == assignment
                    && !review.isMarkedAsPlagiarism()) {
                review.setOldGrade(review.getGrade());
                review.setGrade(GRADE_FIVE);
                review.setMarkedAsPlagiarism();
                review.setInstructor(teacher);
                System.out.println(assignment + SPACE + FOR + SPACE + student + SPACE + MARKED_AS_PLAGIARISM);
            } else if (review.getStudent() == student && review.getAssignment() == assignment
                    && review.isMarkedAsPlagiarism()  && review.getInstructor().equals(teacher)) {
                review.setGrade(review.getOldGrade());
                review.unmarkPlagiarism();
                System.out.println(student + SPACE + MARK_FOR + SPACE + assignment + SPACE + REMOVED_AND_GRADE + SPACE
                        + review.getGrade() + SPACE + RESTORED);
            } else if (review.getStudent() == student && review.getAssignment() == assignment
                    && review.isMarkedAsPlagiarism()  && !(review.getInstructor().equals(teacher))) {
                throw new AdministrationException(ERROR_CANT_TOUCH);
            }
        }
    }

    /**
     * Sucht für die gegebene Aufgabe nach Plagiaten.
     * @param assignment Aufgaben-ID.
     */
    public void searchPlagiarism(int assignment) throws AdministrationException {
        if (instructors.isEmpty()) {
            throw new AdministrationException(ERROR_NO_TEACHER);
        }

        ArrayList<Solution> solutionList = new ArrayList<>(solutions.keySet());
        ArrayList<Solution> relevantSolutions = new ArrayList<>();
        ArrayList<Review> relevantReviews = new ArrayList<>();
        for (Solution solution : solutionList) {
            if (solution.getAssignment() == assignment) {
                relevantSolutions.add(solution);
            }
        }
        for (Review review : reviews.keySet()) {
            if (review.getAssignment() == assignment) {
                relevantReviews.add(review);
            }
        }
        ArrayList<PlagiarsmQueue> plagiarismQueues = new ArrayList<>();
        if (!(relevantSolutions.size() == relevantReviews.size())) {
            throw new AdministrationException(ERROR_NOT_ALL_REVIEWS);
        }
        for (int i = 0; i < relevantSolutions.size(); i++) {
            for (int j = 0; j < relevantSolutions.size(); j++) {
                if (!(relevantSolutions.get(i).getMatriculationNumber()
                        == relevantSolutions.get(j).getMatriculationNumber())) {
                    String longestCommon = searchLongestCommonSubstring(relevantSolutions.get(i).getSolutionText(),
                            relevantSolutions.get(j).getSolutionText());
                    int length = longestCommon.length();
                    float percent = ((float) length / relevantSolutions.get(i).getSolutionText().length())
                            * ONEHUNDRET_FLOAT;
                    double percentOutput = Math.round(percent * ONEHUNDRET) / ONEHUNDRET;
                    String percentageOutput = Double.toString(percentOutput);
                    if (percentageOutput.split(REGEX)[1].length() == 1) {
                        percentageOutput = String.format(PATTERN, percentOutput);
                        PlagiarsmQueue plagiarsmQueue
                                = new PlagiarsmQueue(relevantSolutions.get(i).getMatriculationNumber(),
                                        relevantSolutions.get(j).getMatriculationNumber(), length, longestCommon,
                                percentageOutput);
                        plagiarismQueues.add(plagiarsmQueue);
                    }
                    else {
                        PlagiarsmQueue plagiarsmQueue
                                = new PlagiarsmQueue(relevantSolutions.get(i).getMatriculationNumber(),
                                        relevantSolutions.get(j).getMatriculationNumber(), length, longestCommon,
                                percentageOutput);
                        plagiarismQueues.add(plagiarsmQueue);
                    }
                }
            }
        }
        Collections.sort(plagiarismQueues);
        for (int i = 0; i < plagiarismQueues.size(); i++) {
            if (Double.parseDouble(plagiarismQueues.get(i).getPercentage()) > 0) {
                System.out.println(plagiarismQueues.get(i).getMainMatriculationnumber() + SPACE
                        + plagiarismQueues.get(i).getAssociatedMatriculationnumber() + SPACE
                        + plagiarismQueues.get(i).getPlagiarismString() + SPACE + plagiarismQueues.get(i).getLength()
                        + SPACE + plagiarismQueues.get(i).getPercentage());
            }
        }
    }

    /**
     * Sucht den längsten gemeinsamen Substring zweier gegebener Zeichenketten.
     * @param mainString erste Zeichenkette.
     * @param compareString zweite Zeichenkette.
     */
    private String searchLongestCommonSubstring(String mainString, String compareString) {
        String longestCommonSubstring = EMPTY_STRING;
        for (int i = 0; i < mainString.length(); i++) {
            for (int j = 0; j < mainString.length() - i; j++) {
                String temporaryString = mainString.substring(j, j + i + 1);
                if (compareString.contains(temporaryString)) {
                    longestCommonSubstring = temporaryString;
                }
            }
        }
        return longestCommonSubstring;
    }

}