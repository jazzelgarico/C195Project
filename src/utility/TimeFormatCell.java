package utility;

import javafx.scene.control.ListCell;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A custom format for ListCell<LocalDateTime>
 */
public class TimeFormatCell extends ListCell<LocalDateTime>{

    /**
     * No-arg constructor for TimeFormatCell
     */
    public TimeFormatCell() {}

    /**
     * Override of updateItem: displays LocalDateTime in "h:mma" format if not empty or null.
     *
     * @param time the LocalDateTime to update
     * @param empty true if empty, false if not empty
     */
    @Override
    protected void updateItem(LocalDateTime time, boolean empty) {
        super.updateItem(time, empty);
        if (time == null || empty) {
            setText(null);
        } else {
            setText(time.format(DateTimeFormatter.ofPattern("h:mma")));
        }
    }

}
