package com.example.cafemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhoneActivity extends AppCompatActivity {
    TextView txtResult;
    Uri number;
    Button btn_back;
    ImageView img1, img2;
    LinearLayout layout2, layout3;
    TextView text2, text4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        txtResult = findViewById(R.id.txtResult);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        text2 = findViewById(R.id.text2);
        text4 = findViewById(R.id.text4);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = Uri.parse("tel:"+text2.getText().toString());
                check();
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = Uri.parse("tel:"+text4.getText().toString());
                check();
            }
        });
    }

     void check() {
        int check = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);

        if (check != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE},
                    1);
        }


         else {
            new AlertDialog.Builder(PhoneActivity.this)

                    .setTitle("")
                    .setMessage("전화 연결을 하시겠습니까?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(PhoneActivity.this, "전화 연결하는 중 입니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_CALL, number);
                            startActivity(intent);
                         }
                      })

                    .setNegativeButton("No", null).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(

        int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    check();
                    Log.d("test", "ok");
                } else {
                    Log.d("test", "Permission deny");
            }

                break;
         }
    }
}

