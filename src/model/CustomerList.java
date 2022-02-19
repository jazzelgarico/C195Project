package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;

/**
 * Models a customer list. CustomerList should match the rows of customers in the database.
 */
public class CustomerList {
    private static ObservableList<Customer> list = FXCollections.observableArrayList();

    /**
     * Sets the list by removing all Customers in list and adding all Customers in the provided customerList.
     *
     * @param customerList the customerList to set
     */
    public static void set(ObservableList<Customer> customerList) {
        list.removeAll();
        list.addAll(customerList);
    }

    /**
     * Gets the list of Customers.
     *
     * @return the list of Customers
     */
    public static ObservableList<Customer> get(){
        return list;
    }

    /**
     * Adds the customer to the list.
     *
     * @param customer the customer to add
     */
    public static void add(Customer customer){
        list.add(customer);
    }

    /**
     * Updates the customer in the list.
     * <p>
     * Finds index of the original customer in the list and sets the new customer to that index.
     *
     * @param customer
     */
    public static void replace(Customer customer){
        Optional<Customer> oldCustomer = list.stream()
                .filter(c -> c.getCustomerId() == customer.getCustomerId())
                        .findAny();
        if (oldCustomer.isPresent()) {
            list.set(list.indexOf(oldCustomer.get()),customer);
        }
    }

    /**
     * Removes the customer from the list.
     *
     * @param customer the customer to remove
     */
    public static void remove(Customer customer){
        list.remove(customer);
    }

}
