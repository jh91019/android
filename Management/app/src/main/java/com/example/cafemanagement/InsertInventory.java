package com.example.cafemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class InsertInventory extends AppCompatActivity {
    EditText editId, editName, editPrice, editAmount, editPurchaseDate;
    Button btnInsert, btnBack;
    private Spinner spinnerCategory;
    String[] category = {"카테고리를 선택해주세요", "음료", "디저트", "비품"};
    InventoryDAO dao;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String today = df.format(date);
    ConstraintLayout testLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_inventory);

        //위젯 연결작업
        btnInsert = findViewById(R.id.btnInsert);
        btnBack = findViewById(R.id.btnBack);
        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        editName.setFilters(new InputFilter[]{filterKor});
        editPrice = findViewById(R.id.editPrice);
        editAmount = findViewById(R.id.editAmount);
        editPurchaseDate = findViewById(R.id.editPurchaseDate);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        testLayout = findViewById(R.id.testLayout);

        /* 스피너 어댑터 */
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, category);
        sAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(sAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        testLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

        //등록 구입일자 오늘 날짜로 기본 셋팅
        editPurchaseDate.setText(today);

        dao = new InventoryDAO(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String product_id = editId.getText().toString();
                String product_category = spinnerCategory.getSelectedItem().toString();
                String product_name = editName.getText().toString();
                String priceS = editPrice.getText().toString();
                String amountS = editAmount.getText().toString();
                String purchase_date = editPurchaseDate.getText().toString();
                int price;
                int amount;

                if (TextUtils.isEmpty(product_id)) {
                    Toast.makeText(InsertInventory.this, "상품코드를 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (product_category.equals("카테고리를 선택해주세요")) {
                    Toast.makeText(InsertInventory.this, "상품분류를 선택해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(product_name)) {
                    Toast.makeText(InsertInventory.this, "상품명을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(priceS)) {
                    Toast.makeText(InsertInventory.this, "가격을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(amountS)) {
                    Toast.makeText(InsertInventory.this, "수량을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    price = Integer.parseInt(priceS);
                    amount = Integer.parseInt(amountS);

                    InventoryDTO dto = new InventoryDTO(product_id, product_category, product_name, price, amount, purchase_date);

                    dao.insert(dto);
                }
                Toast.makeText(InsertInventory.this, "등록되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertInventory.this, InventoryManagement.class);
                startActivity(intent);
            }
        });
    }

    //한글만 입력가능하도록 제한
    protected InputFilter filterKor = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[ㄱ-ㅣ가-힣]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    //숫자 천단위 구분 표시ㅡ
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(testLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
