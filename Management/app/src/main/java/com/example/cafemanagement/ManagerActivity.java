package com.example.cafemanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerActivity extends AppCompatActivity {
    Button btn_s;
    Button btn_i;
    Button btn_m;
    Button btn_mb;
    ImageButton btn_logout;
    ImageButton btnEdit;


    String pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        btn_s = (Button) findViewById(R.id.btn_s);
        btn_i = (Button) findViewById(R.id.btn_i);
        btn_m = (Button) findViewById(R.id.btn_m);
        btn_mb = (Button) findViewById(R.id.btn_mb);
        btn_logout =  findViewById(R.id.btn_logout);
        btnEdit =  findViewById(R.id.btnEdit);

        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, SaleManagement.class);
                startActivity(intent);
            }
        });

        btn_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, InventoryManagement.class);
                startActivity(intent);
            }
        });
        btn_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, MenuBoardActivity.class);
                startActivity(intent);
            }
        });
        btn_mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ManagerActivity.this, MainMemberActivity.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom_dialog();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom_dialoga();
            }
        });
    }


    public void custom_dialog() {
        View dialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        androidx.appcompat.app.AlertDialog.Builder ab = new androidx.appcompat.app.AlertDialog.Builder(ManagerActivity.this);
        ab.setView(dialog);

        TextView textContent = dialog.findViewById(R.id.textContent);
        textContent.setText("로그아웃 하시겠습니까?");

        final androidx.appcompat.app.AlertDialog alertDialog = ab.create();
        alertDialog.show();

        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.context).delete();
                Toast.makeText(ManagerActivity.this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        Button btnNo = dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void custom_dialoga() {
        View dialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        androidx.appcompat.app.AlertDialog.Builder ab = new androidx.appcompat.app.AlertDialog.Builder(ManagerActivity.this);
        ab.setView(dialog);

        TextView textContent = dialog.findViewById(R.id.textContent);
        textContent.setText("정보를 수정하시겠습니까?");

        final androidx.appcompat.app.AlertDialog alertDialog = ab.create();
        alertDialog.show();

        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_dialog();
                alertDialog.dismiss();
            }
        });
        Button btnNo = dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void password_dialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View dialog = inflater.inflate(R.layout.password_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setView(dialog);
        final androidx.appcompat.app.AlertDialog alertDialog = ab.create();
        alertDialog.show();
        EditText editpw = (EditText)dialog.findViewById(R.id.editpw);
        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editpw.getText().toString();
                pw = ((MainActivity) MainActivity.context).confirm(pw);
                Log.d("tect", password);
                Log.d("tect", pw);
                Log.d("text", String.valueOf(password.equals(pw)));
                if (password.equals(pw)) {
                    alertDialog.dismiss();
                    Intent intent = new Intent(ManagerActivity.this, ManagerEdit.class);
                    startActivity(intent);
                } else {
                    alertDialog.dismiss();
                    Toast.makeText(ManagerActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnNo = dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}

