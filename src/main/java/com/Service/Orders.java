package com.Service;

import java.util.HashMap;
import java.util.Map;

public class Orders {


    private String customerName;
    private Map<String, Integer> items;
    private Orders_Status status;

    public Orders(String customerName){
        this.customerName = customerName;
        this.items = new HashMap<>();
        this.status =Orders_Status.WAITING;
    }

    public void addItems(String item, int quantity){
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public Orders_Status getStatus() {
        return status;
    }

    public void setStatus(Orders_Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "CustomerName='" + customerName + '\'' +
                ", items=" + items +
                ", status=" + status +
                '}';
    }

}
