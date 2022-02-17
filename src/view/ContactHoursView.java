package view;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.*;

import java.time.Duration;
import java.time.Month;
import java.util.Formatter;

public class ContactHoursView {
    private TableView<ContactHours> contactHoursView;
    private TableColumn<ContactHours,Contact> colContact;
    private TableColumn<ContactHours,Month> colMonth;
    private TableColumn<ContactHours,Duration> colHours;

    public ContactHoursView() {
        contactHoursView = new TableView();
        colContact = new TableColumn();
        colMonth = new TableColumn();
        colHours = new TableColumn();
        contactHoursView.getColumns().add(colContact);
        contactHoursView.getColumns().add(colMonth);
        contactHoursView.getColumns().add(colHours);
        colContact.setText("Contact");
        colMonth.setText("Month");
        colHours.setText("Total Hours");

        contactHoursView.setItems(ContactHoursReport.get());
        contactHoursView.setMaxHeight((contactHoursView.getItems().size() * 25) +25);

        Callback<TableColumn<ContactHours, Duration>, TableCell<ContactHours, Duration>> durationFactory =
                localDateTimeTableColumn -> new TableCell<ContactHours, Duration>() {
                    @Override
                    protected void updateItem(Duration item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            Formatter formatter = new Formatter();
                            formatter.format("%d:%02d",item.toHours(),item.toMinutes()%60);
                            setText(formatter.toString());
                        }
                    }
                };

        colContact.setCellValueFactory(new PropertyValueFactory("Contact"));
        colMonth.setCellValueFactory(new PropertyValueFactory("Month"));
        colHours.setCellValueFactory(new PropertyValueFactory("TotalHours"));
        colHours.setCellFactory(durationFactory);

    }

    public TableView<ContactHours> get() {
        return contactHoursView;
    }


}
