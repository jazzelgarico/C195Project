package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MonthTypeCount;
import model.MonthTypeReport;
import java.time.Month;

public class MonthTypeView {

    private TableView<MonthTypeCount> tblViewMonthType;
    private TableColumn<MonthTypeCount,String> colType;
    private TableColumn<MonthTypeCount,Month> colMonth;
    private TableColumn<MonthTypeCount,Integer> colCount;

    public MonthTypeView() {
        tblViewMonthType = new TableView();
        colType = new TableColumn();
        colMonth = new TableColumn();
        colCount = new TableColumn();
        tblViewMonthType.getColumns().add(colType);
        tblViewMonthType.getColumns().add(colMonth);
        tblViewMonthType.getColumns().add(colCount);
        colType.setText("Type");
        colMonth.setText("Month");
        colCount.setText("Count");
        tblViewMonthType.setMaxWidth(225);
        tblViewMonthType.setItems(MonthTypeReport.get());
        tblViewMonthType.setMaxHeight((tblViewMonthType.getItems().size() * 25) +25);
        colType.setCellValueFactory(new PropertyValueFactory("Type"));
        colMonth.setCellValueFactory(new PropertyValueFactory("Month"));
        colCount.setCellValueFactory(new PropertyValueFactory("Count"));
    }

    public TableView<MonthTypeCount> get() {
        return tblViewMonthType;
    }
}
