package com.example.cafemanagement;

import android.widget.EditText;

import java.io.Serializable;

public class ManagerDTO implements Serializable {

    private String editId;
    private String editPw;

    public ManagerDTO(String editId,String editPw) {

        this.editId = editId;
        this.editPw = editPw;
    }

    public ManagerDTO(EditText editId, Integer editPw) {
    }

    public String getEditPw() {
        return editPw;
    }

    public void setEditPw(String editPw) {
        this.editPw = editPw;
    }

    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }


    @Override
    public String toString() {
        return "ManagerDTO{" +
                "editPw=" + editPw +
                ", editId='" + editId + '\'' +
                '}';
    }
}