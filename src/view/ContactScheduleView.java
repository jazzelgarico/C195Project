package view;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Appointment;
import model.AppointmentList;
import model.Contact;
import model.ContactList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ContactScheduleView {

    private VBox vbox;

    public ContactScheduleView() {
        vbox = new VBox();
        for (Contact c : ContactList.get()) {
            Label label = new Label(c.getContactName());

            Callback<TableColumn<Appointment, LocalDate>, TableCell<Appointment, LocalDate>> dateFactory =
                    localDateTimeTableColumn -> new TableCell<Appointment, LocalDate>() {
                        @Override
                        protected void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.format(DateTimeFormatter.ofPattern("M/d/yyyy")));
                            }
                        }
                    };

            Callback<TableColumn<Appointment, LocalDateTime>, TableCell<Appointment, LocalDateTime>> timeFactory =
                    localDateTimeTableColumn -> new TableCell<Appointment, LocalDateTime>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.format(DateTimeFormatter.ofPattern("h:mma")));
                            }
                        }
                    };

            TableView<Appointment> scheduleView = new TableView<>();
            TableColumn<Appointment, Integer> colAppointmentId = new TableColumn<>("Appointment ID");
            TableColumn<Appointment, String> colTitle = new TableColumn<>("Title");
            TableColumn<Appointment, String> colType = new TableColumn<>("Type");
            TableColumn<Appointment, String> colDesc = new TableColumn<>("Description");
            TableColumn<Appointment, LocalDate> colDate = new TableColumn<>("Date");
            TableColumn<Appointment, LocalDateTime> colStart = new TableColumn<>("Start");
            TableColumn<Appointment, LocalDateTime> colEnd = new TableColumn<>("End");
            TableColumn<Appointment, Integer> colCustomerId = new TableColumn<>("Customer ID");
            scheduleView.getColumns().add(colAppointmentId);
            scheduleView.getColumns().add(colTitle);
            scheduleView.getColumns().add(colType);
            scheduleView.getColumns().add(colDesc);
            scheduleView.getColumns().add(colDate);
            scheduleView.getColumns().add(colStart);
            scheduleView.getColumns().add(colEnd);
            scheduleView.getColumns().add(colCustomerId);

            scheduleView.setItems(AppointmentList.get().stream().
                    filter(a -> a.getContactId() == c.getContactId()).
                    collect(Collectors.toCollection(FXCollections::observableArrayList)));

            colAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
            colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            // Formats columns
            colDate.setCellFactory(dateFactory);
            colEnd.setCellFactory(timeFactory);
            colStart.setCellFactory(timeFactory);
            scheduleView.setMaxHeight((scheduleView.getItems().size() * 25) + 25);
            scheduleView.setPlaceholder(new Label("No appointments found."));
            vbox.getChildren().add(label);
            vbox.getChildren().add(scheduleView);
            vbox.setSpacing(10);
        }
    }
    public VBox get() {
            return vbox;
        }


}
