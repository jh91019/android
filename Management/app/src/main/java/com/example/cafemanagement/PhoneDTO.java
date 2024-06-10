package com.example.cafemanagement;

import java.security.PrivateKey;
public class PhoneDTO {
    private String name;
    private String tel;

    public PhoneDTO(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
