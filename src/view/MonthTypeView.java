package view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MonthTypeCount;
import model.MonthTypeReport;
import java.time.Month;

/**
 * A TableView which displays the MonthTypeView.
 */
public class MonthTypeView extends TableView<MonthTypeCount> {
    private TableColumn<MonthTypeCount,String> colType;
    private TableColumn<MonthTypeCount,Month> colMonth;
    private TableColumn<MonthTypeCount,Integer> colCount;

    /**
     * No-arg Constructor which builds a MonthTypeView.
     */
    public MonthTypeView() {
        colType = new TableColumn();
        colMonth = new TableColumn();
        colCount = new TableColumn();
        this.getColumns().add(colType);
        this.getColumns().add(colMonth);
        this.getColumns().add(colCount);
        colType.setText("Type");
        colMonth.setText("Month");
        colCount.setText("Count");
        this.setMaxWidth(225);
        this.setItems(MonthTypeReport.get());
        this.setMaxHeight((this.getItems().size() * 25) +25);
        colType.setCellValueFactory(new PropertyValueFactory("Type"));
        colMonth.setCellValueFactory(new PropertyValueFactory("Month"));
        colCount.setCellValueFactory(new PropertyValueFactory("Count"));
    }

}
