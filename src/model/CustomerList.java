package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerList {
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     *
     * @param newCustomer
     */
    public static void addCustomer(Customer newCustomer) {
        customerList.add(newCustomer);
    }

    /**
     *
     * @return
     */
    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    public static int getListSize() {
        return customerList.size();
    }

}
