package apollon.data;

import java.util.Objects;

/**
 * Modelliert ein Objekt zum Vergleich von Lösungen, um Plagiate zu finden.
 * Enthält die Matrikelnummer des gewählten Objekts und die Matrikelnummer des Objekts mit dem verglichen wird.
 * Ebenfalls wird die Länge der gemeinsamen gleichen Teilzeichenkette gespeichert sowie die Zeichenfolge dieser.
 * Der prozentuale Anteil dieser Zeichenkette am Gesamttext wird ebenso gespeichert.
 * @author ufufe
 * @version 1.0
 */
public class PlagiarsmQueue implements Comparable<PlagiarsmQueue> {

    private final int mainMatriculationnumber;
    private final int associatedMatriculationnumber;
    private final int length;
    private final String plagiarismString;
    private final String percentage;

    /**
     * Erstellt ein Plagiats-Vergleichs-Objekt mit eindeutiger Hauptmatrikelnummer und Vergleichsmatrikelnummer,
     * Länge des Plagiats, Zeichenkette des Plagiats und dem prozentualen Anteil an der Gesamtlösung.
     * @param mainMatriculationnumber Die Hauptmatrikelnummer.
     * @param associatedMatriculationnumber Die zweite Matrikelnummer.
     * @param length Die Länge des Plagiats.
     * @param plagiarismString Die Zeichenkette des Plagiats.
     * @param percentage Der prozentuale Anteil an der Gesamtlösung.
     */
    public PlagiarsmQueue(int mainMatriculationnumber, int associatedMatriculationnumber, int length,
                          String plagiarismString, String percentage) {
        this.mainMatriculationnumber = mainMatriculationnumber;
        this.associatedMatriculationnumber = associatedMatriculationnumber;
        this.length = length;
        this.plagiarismString = plagiarismString;
        this.percentage = percentage;
    }

    /**
     * Gibt die Hauptmatrikelnummer zurück.
     * @return Die Hauptmatrikelnummer.
     */
    public int getMainMatriculationnumber() {
        return mainMatriculationnumber;
    }

    /**
     * Gibt die zweite Matrikelnummer zurück.
     * @return Die zweite Matrikelnummer.
     */
    public int getAssociatedMatriculationnumber() {
        return associatedMatriculationnumber;
    }

    /**
     * Gibt die Länge des Plagiats zurück.
     * @return Die Länge.
     */
    public int getLength() {
        return length;
    }

    /**
     * Gibt die Teilzeichenkette zurück.
     * @return Die Teilzeichenkette.
     */
    public String getPlagiarismString() {
        return plagiarismString;
    }

    /**
     * Gibt den prozentualen Anteil zurück.
     * @return Den Anteil.
     */
    public String getPercentage() {
        return percentage;
    }

    @Override
    public int compareTo(PlagiarsmQueue plagiarsmQueue) {
        if (Double.parseDouble(this.percentage) < Double.parseDouble(plagiarsmQueue.getPercentage())) {
            return 1;
        } else if (Double.parseDouble(this.percentage) > Double.parseDouble(plagiarsmQueue.getPercentage())) {
            return -1;
        } else {
            if (this.mainMatriculationnumber < plagiarsmQueue.getMainMatriculationnumber()) {
                return -1;
            } else if (this.mainMatriculationnumber > plagiarsmQueue.getMainMatriculationnumber()) {
                return 1;
            } else {
                if (this.getAssociatedMatriculationnumber() < plagiarsmQueue.getAssociatedMatriculationnumber()) {
                    return -1;
                } else if (this.getAssociatedMatriculationnumber()
                        > plagiarsmQueue.getAssociatedMatriculationnumber()) {
                    return 1;
                } else {
                    return this.getPlagiarismString().compareTo(plagiarsmQueue.getPlagiarismString());
                }
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof PlagiarsmQueue)) {
            return false;
        }
        PlagiarsmQueue plagiarsmQueue = (PlagiarsmQueue) object;
        return (Double.parseDouble(this.percentage) == Double.parseDouble(plagiarsmQueue.getPercentage()));
    }

}
