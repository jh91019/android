package com.example.cafemanagement;

import java.io.Serializable;

public class InventoryDTO implements Serializable {
    private String product_id;
    private String product_category;
    private String product_name;
    private int price;
    private int amount;
    private String purchase_date;

    public InventoryDTO(String product_id, String product_category, String product_name, int price, int amount, String purchase_date) {
        this.product_id = product_id;
        this.product_category = product_category;
        this.product_name = product_name;
        this.price = price;
        this.amount = amount;
        this.purchase_date = purchase_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "product_id='" + product_id + '\'' +
                ", product_category='" + product_category + '\'' +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", purchase_date='" + purchase_date + '\'' +
                '}';
    }
}
