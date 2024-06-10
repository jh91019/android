package com.example.cafemanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText editId;
    EditText editPw;
    Button buttonLogin;
    ManagerDAO dao;
    private List<ManagerDTO> dto;
    SQLiteDatabase sqliteDB;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    CheckBox autoLogin;
    boolean saveLogin;
    SharedPreferences auto;
    String id, pw;
    SharedPreferences.Editor autoLoginEdit;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.buttonLogin);
        editId = findViewById(R.id.editId);
        editPw = findViewById(R.id.editpw);
        autoLogin = findViewById(R.id.autoLogin);
        context = this;
        auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        autoLoginEdit = auto.edit();
        dao = new ManagerDAO(this);
        load();
        if(saveLogin) {
            editId.setText(id);
            editPw.setText(pw);
            autoLogin.setChecked(saveLogin);
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editId.getText().toString();
                String pw = editPw.getText().toString();
                int result = dao.insert(id, pw);
                Log.i("test", "result:" + result);
               if (result == 1) {
                       save();
                   custom_dialog();
                } else if (editId.length() == 0 || editPw.length() == 0) {
//                    new AlertDialog.Builder(MainActivity.this)
//                            .setTitle("")
//                            .setMessage("아이디와 비밀번호를 입력하지 않았습니다.")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(MainActivity.this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//
//                            .setNegativeButton("", null).show();
                   custom_dialogA("아이디와 비밀번호를 입력하지 않았습니다", "아이디와 비밀번호를 입력해주세요.");
                } else {
//                    new AlertDialog.Builder(MainActivity.this)
//                            .setTitle("")
//                            .setMessage("로그인 정보가 맞지 않습니다.")
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Toast.makeText(MainActivity.this, "아이디와 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
//                                    editId.setText(null);
//                                    editPw.setText(null);
//                                }
//                            })
//                            .setNegativeButton("", null).show();
                   custom_dialogA("로그인 정보가 맞지 않습니다.", "아이디와 비밀번호를 다시 확인해주세요.");
                }
            }

        });
    }


    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.btn_as) {
                    intent = new Intent(this, PhoneActivity.class);
                    startActivity(intent);
        }
    }

    public void custom_dialog() {
        View dialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        androidx.appcompat.app.AlertDialog.Builder ab = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        ab.setView(dialog);

        TextView textContent = dialog.findViewById(R.id.textContent);
        textContent.setText("관리자 계정에 로그인 하시겠습니까?");

        final androidx.appcompat.app.AlertDialog alertDialog = ab.create();
        alertDialog.show();

        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "관리자 계정에 로그인 되었습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
                startActivity(intent);
            }
        });

        Button btnNo = dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editId.setText(null);
                editPw.setText(null);
                alertDialog.dismiss();
            }
        });
    }

    public void custom_dialogA(String a, String b) {
        View dialog = getLayoutInflater().inflate(R.layout.custom_dialoga, null);
        androidx.appcompat.app.AlertDialog.Builder ab = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
        ab.setView(dialog);

        TextView textContent = dialog.findViewById(R.id.textContent);
        textContent.setText(a);

        final androidx.appcompat.app.AlertDialog alertDialog = ab.create();
        alertDialog.show();

        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, b, Toast.LENGTH_SHORT).show();
                editId.setText(null);
                editPw.setText(null);
                alertDialog.dismiss();
            }
        });
    }

    private void save() {
        autoLoginEdit.putBoolean("save_auto", autoLogin.isChecked());
        autoLoginEdit.putString("userId", editId.getText().toString());
        autoLoginEdit.putString("userPw", editPw.getText().toString());
        autoLoginEdit.commit();
    }

    public void load() {
        saveLogin = auto.getBoolean("save_auto", false);
        id = auto.getString("userId", "");
        pw = auto.getString("userPw", "");
    }

    public void delete() {
        autoLoginEdit.clear();
        autoLoginEdit.commit();
    }

    public String send(String id){
        id = auto.getString("userId", "");
        return id;
    }

    public String confirm(String pw){
        pw = auto.getString("userPw", "");
        return pw;
    }
}