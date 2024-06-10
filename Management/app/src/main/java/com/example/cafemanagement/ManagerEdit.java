package com.example.cafemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManagerEdit extends AppCompatActivity {


    SharedPreferences auto;
    String id, pw, pw1, id1;
    SharedPreferences.Editor autoLoginEdit;
    EditText editId, editPw;
    Button btnedit;
    ManagerDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_edit);
        editId = findViewById(R.id.editId);
        editPw = findViewById(R.id.editpw);
        btnedit = findViewById(R.id.btnEdit);
        dao = new ManagerDAO(this);
        auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        autoLoginEdit = auto.edit();
        id = ((MainActivity) MainActivity.context).send(id);
        Log.d("text", id);
        editId.setText(id);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id1 = editId.getText().toString();
                pw1 = editPw.getText().toString();
                int result = dao.update(id1, pw1, id);
                if (result==1) {
                    Toast.makeText(ManagerEdit.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                    ((MainActivity)MainActivity.context).delete();
                    Intent intent = new Intent(ManagerEdit.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}