package com.example.cafemanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class EditInventory extends AppCompatActivity implements View.OnClickListener {
    EditText editProduct_id, editProduct_name, editPrice, editAmount, editPurchase_date;
    Button btnUpdate, btnBack, btnDelete;
    Spinner editCategorySpinner;
    String[] category = {"카테고리를 선택해주세요", "음료", "디저트", "비품"};
    InventoryDAO dao;
    InventoryDTO dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);
        String selectedCt = getIntent().getStringExtra("selectedCategory");
        editProduct_id = findViewById(R.id.editId);
        editCategorySpinner = (Spinner) findViewById(R.id.spinnerCategory);
        editProduct_name = findViewById(R.id.editName);
        editPrice = findViewById(R.id.editPrice);
        editAmount = findViewById(R.id.editAmount);
        editPurchase_date = findViewById(R.id.editPurchaseDate);
        editProduct_name.setFilters(new InputFilter[]{filterKor});
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        /* 스피너 어댑터 */
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, category);
        sAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        editCategorySpinner.setAdapter(sAdapter);

        dao = new InventoryDAO(this);

        Intent intent = getIntent();
        dto = (InventoryDTO) intent.getSerializableExtra("dto");
        editProduct_id.setText(dto.getProduct_id());
        editCategorySpinner.setSelection(sAdapter.getPosition(selectedCt));
        editProduct_name.setText(dto.getProduct_name());
        editPrice.setText(Integer.toString(dto.getPrice()));
        editAmount.setText(dto.getAmount() + "");
        editPurchase_date.setText(dto.getPurchase_date());
        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        editCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editCategorySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(EditInventory.this, "상품분류를 선택해주세요", Toast.LENGTH_SHORT).show();
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

    //커스텀 다이얼로그
    public void custom_dialog() {
        View dialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(EditInventory.this);
        ab.setView(dialog);

        TextView textContent = dialog.findViewById(R.id.textContent);
        textContent.setText("선택하신 재고를 삭제하시겠습니까?");

        final AlertDialog alertDialog = ab.create();
        alertDialog.show();

        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.delete(dto.getProduct_id());
                Toast.makeText(EditInventory.this, dto.getProduct_name() + "이(가) 삭제되었습니다.", Toast.LENGTH_LONG).show();
                finish();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
            String product_id = editProduct_id.getText().toString();
            String product_category = editCategorySpinner.getSelectedItem().toString();
            String product_name = editProduct_name.getText().toString();
            String priceS = editPrice.getText().toString();
            String amountS = editAmount.getText().toString();
            String purchase_date = editPurchase_date.getText().toString();
            int price;
            int amount;

            if (TextUtils.isEmpty(product_id)) {
                Toast.makeText(EditInventory.this, "상품코드를 입력해주세요.", Toast.LENGTH_LONG).show();
                return;
            } else if (product_category.equals("카테고리를 선택해주세요")) {
                Toast.makeText(EditInventory.this, "상품분류를 선택해주세요.", Toast.LENGTH_LONG).show();
                return;
            } else if (TextUtils.isEmpty(product_name)) {
                Toast.makeText(EditInventory.this, "상품명을 입력해주세요.", Toast.LENGTH_LONG).show();
                return;
            } else if (TextUtils.isEmpty(priceS)) {
                Toast.makeText(EditInventory.this, "가격을 입력해주세요.", Toast.LENGTH_LONG).show();
                return;
            } else if (TextUtils.isEmpty(amountS)) {
                Toast.makeText(EditInventory.this, "수량을 입력해주세요.", Toast.LENGTH_LONG).show();
                return;
            } else {
                price = Integer.parseInt(priceS);
                amount = Integer.parseInt(amountS);

                dto.setProduct_id(product_id);
                dto.setProduct_category(product_category);
                dto.setProduct_name(product_name);
                dto.setPrice(price);
                dto.setAmount(amount);
                dto.setPurchase_date(purchase_date);

                dao.update(dto);
            }

            Toast.makeText(this, dto.getProduct_name() + "이(가) 수정되었습니다.", Toast.LENGTH_LONG).show();
            //현재 화면 종료
            finish();
        } else if (v.getId() == R.id.btnDelete) {
            custom_dialog();
        } else if (v.getId() == R.id.btnBack) {
            Intent intent = new Intent(EditInventory.this, InventoryManagement.class);
            startActivity(intent);
        }
    }

    public String numberFormat(String str) {
        if (str.length() == 0) {
            return "";
        }
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }
}