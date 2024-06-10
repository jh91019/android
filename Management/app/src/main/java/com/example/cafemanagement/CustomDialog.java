//package com.example.cafemanagement;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//public class CustomDialog extends Dialog {
//
//    Button btnDelete, btnCancel;
//
//    public CustomDialog(@NonNull Context context) {
//        super(context);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_custom_dialog);
//
//        btnDelete = findViewById(R.id.btnDelete);
//        btnCancel = findViewById(R.id.btnCancel);
//
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                return 1;
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//    }
//}