package com.example.cafemanagement;

import java.io.Serializable;

public class SaleDTO implements Serializable {
    private String order_date;
    private String menu_name;
    private int day_amount;
    private int day_sales;
    private int month_amount;
    private int month_sales;

    public SaleDTO(String order_date, String menu_name, int day_amount, int day_sales, int month_amount, int month_sales) {
        this.order_date = order_date;
        this.menu_name = menu_name;
        this.day_amount = day_amount;
        this.day_sales = day_sales;
        this.month_amount = month_amount;
        this.month_sales = month_sales;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public int getDay_amount() {
        return day_amount;
    }

    public void setDay_amount(int day_mount) {
        this.day_amount = day_mount;
    }

    public int getDay_sales() {
        return day_sales;
    }

    public void setDay_sales(int day_sales) {
        this.day_sales = day_sales;
    }
    public int getMonth_amount() {
        return month_amount;
    }

    public void setMonth_amount(int month_amount) {
        this.month_amount = month_amount;
    }

    public int getMonth_sales() {
        return month_sales;
    }

    public void setMonth_sales(int month_sales) {
        this.month_sales = month_sales;
    }
}
