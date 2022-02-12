package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class CustomerList {
    private static ObservableList<Customer> list = FXCollections.observableArrayList();

    public static void set(ObservableList<Customer> cList) {
        list.removeAll();
        list.addAll(cList);
    }

    public static ObservableList<Customer> get(){
        return list;
    }

    public static void add(Customer customer){
        list.add(customer);
    }

    public static void replace(Customer customer){
        System.out.println("Customer ID to edit: " + customer.getCustomerId());
        Optional<Customer> oldCustomer = list.stream()
                .filter(c -> c.getCustomerId() == customer.getCustomerId())
                        .findAny();
        if (oldCustomer.isEmpty()) {
        } else {
            System.out.println(oldCustomer.get());
            System.out.println(list.indexOf(oldCustomer.get()));
            list.set(list.indexOf(oldCustomer.get()),customer);
        }
    }

    public static void remove(Customer customer){
        list.remove(customer);
    }

}