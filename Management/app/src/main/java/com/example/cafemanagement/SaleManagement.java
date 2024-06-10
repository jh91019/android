package com.example.cafemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SaleManagement extends AppCompatActivity {

    Button btnOrders;
    Button btnDay;
    Button btnMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_management);

        btnOrders = findViewById(R.id.btnOrders);
        btnDay = findViewById(R.id.btnDay);
        btnMonth = findViewById(R.id.btnMonth);

        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleManagement.this, OrdersActivity.class);
                startActivity(intent);
            }
        });
        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleManagement.this, DaySalemana.class);
                startActivity(intent);
            }
        });
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleManagement.this, MonthSalemana.class);
                startActivity(intent);
            }
        });
    }
}