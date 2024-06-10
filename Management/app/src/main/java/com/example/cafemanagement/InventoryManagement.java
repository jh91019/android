package com.example.cafemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class InventoryManagement extends AppCompatActivity {
    private EditText editSearch;
    private Button btnInsert;
    private RecyclerView rv;
    InventoryDAO dao;
    InventoryDTO dto;
    private List<InventoryDTO> items, filteredList;
    private Context context;
    RecyclerView.Adapter myAdapter;

    //검색 결과 목록
    private List<InventoryDTO> seacchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//화면 생성 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_management);

        //재고 검색
        editSearch = findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = editSearch.getText().toString().trim();
                items = dao.search(keyword);
                Log.i("test", "검색결과 : " + items);
                myAdapter = new MyAdapter();

                //rv에 연결
                rv.setAdapter(myAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //재고 등록 버튼
        btnInsert = findViewById(R.id.btnInsert);

        // rv 재고목록
        rv = findViewById(R.id.rv);
        // rv 화면 레이아웃
        rv.setLayoutManager(new LinearLayoutManager(this));
        // 구분선
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        //InventoryDAO가 호출됨
        dao = new InventoryDAO(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryManagement.this, InsertInventory.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {//화면 복귀
        super.onResume();
        //목록이 전달됨
        items = dao.list();

        editSearch.setText(null);
        //Adapter 생성
        myAdapter = new MyAdapter();

        //rv에 연결
        rv.setAdapter(myAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item, parent, false);
            return new ViewHolder(rowItem);
        }

        //child view 데이터를 채워서 출력하기
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            InventoryDTO dto = items.get(position);

            holder.product_id.setText(dto.getProduct_id());
            holder.product_category.setText(dto.getProduct_category());
            holder.product_name.setText(dto.getProduct_name());
            holder.price.setText(numberFormat(String.valueOf(dto.getPrice())));
            holder.amount.setText(numberFormat(String.valueOf(dto.getAmount())));
            holder.purchase_date.setText(dto.getPurchase_date());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView product_id, product_category, product_name, price, amount, purchase_date;

            public ViewHolder(@NonNull View view) {
                super(view);
                view.setOnClickListener(this);
                //child view를 클릭하면 현재클래스에서 처리
                this.product_id = view.findViewById(R.id.txtProduct_id);
                this.product_category = view.findViewById(R.id.txtProduct_category);
                this.product_name = view.findViewById(R.id.txtProduct_name);
                this.price = view.findViewById(R.id.txtPrice);
                this.amount = view.findViewById(R.id.txtAmount);
                this.purchase_date = view.findViewById(R.id.txtPurchase_date);
            }

            public void custom_dialog() {
                View dialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                AlertDialog.Builder ab = new AlertDialog.Builder(InventoryManagement.this);
                ab.setView(dialog);

                TextView textContent = dialog.findViewById(R.id.textContent);
                textContent.setText("재고 수정화면으로 이동하시겠습니까?");

                final AlertDialog alertDialog = ab.create();
                alertDialog.show();

                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(InventoryManagement.this, EditInventory.class);
                        //전달할 데이터, 리스트.get(인덱스)
                        InventoryDTO dto = items.get(getLayoutPosition());
                        Log.i("test", "가져오는 값 확인 :" + dto);

                        intent.putExtra("dto", dto);
                        intent.putExtra("selectedCategory", dto.getProduct_category());
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

            @Override
            public void onClick(View v) {
                custom_dialog();
            }

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

