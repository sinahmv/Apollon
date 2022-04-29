package apollon.data;

import java.util.Objects;

/**
 * Modelliert eine Lösung, die zu Apollon von einem Studierenden hinzugefügt werden kann.
 * Besteht aus ihrem Lösungstext, der Aufgabennummer und der studentischen Matrikelnummer.
 * @author ufufe
 * @version 1.0
 */
public class Solution implements Comparable<Solution> {

    private String solutionText;
    private final int assignment;
    private final int matriculationNumber;

    /**
     * Erstellt eine Solution von einem bestimmten Studenten für eine bestimmte Aufgabe mit einem Antworttext des
     * Studenten.
     * @param assignment Die Aufgabe.
     * @param matriculationNumber Die Matrikelnummer des Studenten.
     * @param solutionText Der Antworttext des Studierenden.
     */
    public Solution(int assignment, int matriculationNumber, String solutionText) {
        this.solutionText = solutionText;
        this.assignment = assignment;
        this.matriculationNumber = matriculationNumber;
    }

    /**
     * Gibt den Antworttext zurück.
     * @return Antworttext.
     */
    public String getSolutionText() {
        return solutionText;
    }

    /**
     * Gibt die zugehörige Aufgabe zurück.
     * @return Die Aufgabe.
     */
    public int getAssignment() {
        return assignment;
    }

    /**
     * Gibt die Matrikelnummer zurück.
     * @return Die Matrikelnummer.
     */
    public int getMatriculationNumber() {
        return matriculationNumber;
    }

    /**
     * Legt den Antworttext fest.
     * @param newSolution neuer Antworttext.
     */
    public void setSolutionText(String newSolution) {
        this.solutionText = newSolution;
    }

    /**
     * Vergleicht eine Lösung hinsichtlich der Matrikelnummer in Bezug auf eine andere Lösung.
     * @param solution Die Lösung, mit der verglichen wird.
     * @return Aussage, ob die Matrikelnummer größer, kleiner oder gleich ist.
     */
    @Override
    public int compareTo(Solution solution) {
        if (this.matriculationNumber < solution.getMatriculationNumber()) {
            return -1;
        }
        else if (this.matriculationNumber > solution.getMatriculationNumber()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Vergleicht eine Lösung hinsichtlich der Aufgabennummer in Bezug auf eine andere Lösung.
     * @param solution Die Lösung, mit der verglichen wird.
     * @return Aussage, ob die Aufgabennummer größer, kleiner oder gleich ist.
     */
    public int compareToAssignmentNumber(Solution solution) {
        if (this.assignment < solution.getAssignment()) {
            return -1;
        }
        else if (this.assignment > solution.getAssignment()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Vergleicht eine Lösung hinsichtlich der Aufgabennummer in Bezug auf eine andere Lösung.
     * @param solution Die Lösung, mit der verglichen wird.
     * @return Aussage, ob die Aufgabennummer gleich ist.
     */
    public boolean compareToSolutions(Solution solution) {
        return (this.assignment == solution.getAssignment() && this.matriculationNumber
                == solution.getMatriculationNumber());
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof Solution)) {
            return false;
        }
        Solution solution = (Solution) object;
        return (this.matriculationNumber == solution.getMatriculationNumber());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
