package com.example.cafemanagement;

import java.io.Serializable;

public class MemberDTO implements Serializable {
    private String id;
    private String phone;
    private String sdate;
    private int orderc;
    private int coupon;
    private int stamp;
    private String odate;
    private int rowid;



    public MemberDTO(String id, String phone, String sdate, int orderc, int coupon, int stamp, String odate, int rowid) {
        this.id = id;
        this.phone = phone;
        this.sdate = sdate;
        this.orderc = orderc;
        this.coupon = coupon;
        this.stamp = stamp;
        this.odate = odate;
        this.rowid = rowid;
    }


    @Override
    public String toString() {
        return "MemberDTO{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", sdate='" + sdate + '\'' +
                ", orderc=" + orderc +
                ", coupon=" + coupon +
                ", stamp=" + stamp +
                ", odate='" + odate + '\'' +
                ", rowid=" + rowid +
                '}';
    }

    public int getRowid() {
        return rowid;
    }

    public void setRowid(int rowid) {
        this.rowid = rowid;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public int getOrderc() {
        return orderc;
    }

    public void setOrderc(int orderc) {
        this.orderc = orderc;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public int getStamp() {
        return stamp;
    }

    public void setStamp(int stamp) {
        this.stamp = stamp;
    }

    public String getDate() {
        return odate;
    }

    public void setDate(String odate) {
        this.odate = odate;
    }
}
