package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MonthTypeCount;
import model.MonthTypeReport;
import java.time.Month;

public class MonthTypeView {
    private static TableView<MonthTypeCount> tblViewMonthType = new TableView<>();
    private static TableColumn<MonthTypeCount,String> colType = new TableColumn<>();
    private static TableColumn<MonthTypeCount,Month> colMonth = new TableColumn<>();
    private static TableColumn<MonthTypeCount,Integer> colCount = new TableColumn<>();

    public static TableView<MonthTypeCount> get() {
        tblViewMonthType.getColumns().add(colType);
        tblViewMonthType.getColumns().add(colMonth);
        tblViewMonthType.getColumns().add(colCount);
        colType.setText("Type");
        colMonth.setText("Month");
        colCount.setText("Count");
        tblViewMonthType.setItems(MonthTypeReport.get());
        colType.setCellValueFactory(new PropertyValueFactory("Type"));
        colMonth.setCellValueFactory(new PropertyValueFactory("Month"));
        colCount.setCellValueFactory(new PropertyValueFactory("Count"));
        return tblViewMonthType;
    }
}
