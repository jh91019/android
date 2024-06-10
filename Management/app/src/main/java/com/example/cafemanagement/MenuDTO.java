package com.example.cafemanagement;

import java.io.Serializable;

public class MenuDTO implements Serializable {
    private String categoryId;
    private String category;
    private int menuNo;
    private String menuName;
    private String menuId;
    private int price;
    private int run;

    public MenuDTO(String category, String menuId, String menuName, int price, int run) {
        this.category = category;
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.run = run;
    }

    public MenuDTO(String category, String menuName, int price, int run) {
        this.category = category;
        this.menuName = menuName;
        this.price = price;
        this.run = run;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "categoryId='" + categoryId + '\'' +
                ", category='" + category + '\'' +
                ", menuNo=" + menuNo +
                ", menuName='" + menuName + '\'' +
                ", menuId='" + menuId + '\'' +
                ", price=" + price +
                ", run=" + run +
                '}';
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }
}