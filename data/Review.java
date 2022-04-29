package apollon.data;

import java.util.Objects;

/**
 * Modelliert eine Korrektur, die von einem Tutor zu Apollon hinzugefügt werden kann.
 * Besteht aus ihrem Tutor, der Aufgabennummer, dem Studierenden den sie betrifft, einem Kommentar vom Tutor und einer
 * Note.
 * Ebenfalls wird gespeichert, ob die Korrektur schon als Plagiat markiert ist oder war und was im Falle einer
 * Markierung was die vorherige Note war.
 * @author ufufe
 * @version 1.0
 */
public class Review implements Comparable<Review> {

    private final String tutor;
    private final int assignment;
    private final int student;
    private String note;
    private int grade;
    private boolean markedAsPlagiarism;
    private int oldGrade;
    private String instructor;

    /**
     * Erstellt eine Korrektur im Name eines Tutors, für die Aufgabe eines Studenten, mit einer Note und Anmerkung.
     * Die Korrektur ist anfangs nie als Plagiat markiert worden und ist auch zu diesem Zeitpunkt kein Plagiat.
     * @param tutor Der Tutor.
     * @param assignment Die Aufgabe.
     * @param student Der Student.
     * @param grade Die Note.
     * @param note Die Anmerkung.
     */
    public Review(String tutor, int assignment, int student, int grade, String note) {
        this.tutor = tutor;
        this.assignment = assignment;
        this.student = student;
        this.grade = grade;
        this.note = note;
        this.markedAsPlagiarism = false;
    }

    /**
     * Gibt den Namen des zugehörigen Lehrers zurück.
     * @return Name des Lehrers.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Stellt den Namen des zugehörigen Lehrers ein.
     * @param instructor Name des Lehrers.
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * Gibt den Namen des zugehörigen Tutors zurück.
     * @return Name des Tutors.
     */
    public String getTutor() {
        return tutor;
    }

    /**
     * Gibt die zugehörige Aufgabe zurück.
     * @return Die Aufgabe.
     */
    public int getAssignment() {
        return assignment;
    }

    /**
     * Gibt die zugehörige Matrikelnummer zurück.
     * @return Die Matrikelnummer.
     */
    public int getStudent() {
        return student;
    }

    /**
     * Gibt die zugehörige Rückmeldung zurück.
     * @return Die Rückmeldung.
     */
    public String getNote() {
        return note;
    }

    /**
     * Legt die zugehörige Rückmeldung fest.
     * @param note Die neue Rückmeldung.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gibt die zugehörige Note zurück.
     * @return Die Note.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Legt die zugehörige Note fest.
     * @param grade Die neue Note.
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }

    /**
     * Gibt zurück, ob die Korrektur als Plagiat markiert ist.
     * @return Plagiatsmarkierung.
     */
    public boolean isMarkedAsPlagiarism() {
        return markedAsPlagiarism;
    }

    /**
     * Markiert die Korrektur als Plagiat.
     */
    public void setMarkedAsPlagiarism() {
        this.markedAsPlagiarism = true;
    }

    /**
     * Entfernt eine Markierung als Plagiat.
     */
    public void unmarkPlagiarism() {
        this.markedAsPlagiarism = false;
    }

    /**
     * Gibt die alte Note zurück.
     * @return Vorherige Note.
     */
    public int getOldGrade() {
        return oldGrade;
    }

    /**
     * Legt die alte Note fest, bevor ein Plagiat markiert wird.
     * @param oldGrade alte Note.
     */
    public void setOldGrade(int oldGrade) {
        this.oldGrade = oldGrade;
    }

    /**
     * Vergleicht eine Korrektur in Bezug auf eine andere Korrektur.
     * @param review Die Korrektur mit der verglichen wird.
     * @return Aussage, ob Aufgabe und Student gleich sind.
     */
    public boolean compareToReview(Review review) {
        return (this.assignment == review.getAssignment() && this.getStudent()
                == review.getStudent());
    }


    @Override
    public int compareTo(Review review) {
        if (!(this.tutor.equals(review.getTutor()))) {
            return this.tutor.compareTo(review.getTutor());
        }
        else {
            return Integer.compare(this.student, review.getStudent());
        }
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof Review)) {
            return false;
        }
        Review review = (Review) object;
        return (Objects.equals(this.tutor, review.getTutor()));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
