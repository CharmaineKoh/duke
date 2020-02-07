package tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Task that need to be done before a specific date/time.
 */
public class Deadline extends Task {

    protected LocalDate dateBy;

    /**
     * Creates a task with a deadline.
     *
     * @param description task with a deadline to be completed.
     * @param dateBy      date the task needs to be completed by.
     */
    public Deadline(String description, LocalDate dateBy) {
        super(description);
        this.dateBy = dateBy;
        System.out.println("\t\t" + this);
    }

    /**
     * Returns the string of the task with a deadline indicating [D] for deadline followed by the description
     * then do by date in MMM d yyyy.
     *
     * @return the string of the deadline task indicating [D] for deadline followed by the description
     *     then do by date in MMM d yyyy.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateBy.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}