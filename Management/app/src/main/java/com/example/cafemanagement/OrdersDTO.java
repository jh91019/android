package com.example.cafemanagement;

import java.io.Serializable;

public class OrdersDTO implements Serializable {
    private String order_num;
    private String order_date;
    private  int menu_id;
    private String menu_name;
    private int amount;
    private int total_price;

    public OrdersDTO(String order_num, String order_date, String menu_name, int amount, int total_price) {
        this.order_num = order_num;
        this.order_date = order_date;
        this.menu_name = menu_name;
        this.amount = amount;
        this.total_price = total_price;

    }

    public String toString() {
        return "OrdersDTO{" +
                "order_num=" + order_num +
                ", order_date=" + order_date +
                ", menu_name=" + menu_name +
                ", amount=" + amount +
                ", total_price=" + total_price +
                '}';
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
}
