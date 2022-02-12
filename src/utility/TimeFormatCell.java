package utility;

import javafx.scene.control.ListCell;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatCell extends ListCell<LocalDateTime>{

    public TimeFormatCell() {}
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
