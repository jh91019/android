package com.example.cafemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.text.DecimalFormat;

public class DaySalemana extends AppCompatActivity {

    Button btnSale;
    EditText txtDateSearch;
    TextView txtSale;
    RecyclerView rv1;
    RecyclerView.Adapter DayAdapter;
    List<SaleDTO> items4;
    OrdersDAO dao;
    int dayresult;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String dresult="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_salemana);

        btnSale = findViewById(R.id.btnSale);
        txtDateSearch = findViewById(R.id.txtDateSearch);
        txtSale = findViewById(R.id.txtSale);
        rv1 = findViewById(R.id.rv1);
        rv1.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        dao = new OrdersDAO(this);
        txtSale.addTextChangedListener(watcher); //콤마설정
        //경계선 설정
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv1.getContext(), new LinearLayoutManager(this).getOrientation());
        rv1.addItemDecoration(dividerItemDecoration);

        btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order_date = txtDateSearch.getText().toString();
                SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
                dateForm.setLenient(false); //엄격한 모드로 설정

                try {
                    Date parseDate = dateForm.parse(order_date);
                    dayresult = dao.day_sale(order_date);
                    if (dayresult==0) {
                        Toast.makeText(DaySalemana.this,"해당날짜는 없습니다.",Toast.LENGTH_SHORT).show();
                    } else {
                        items4 = dao.list4(order_date);
                        txtSale.setText(String.valueOf(dayresult));
                        DayAdapter = new DayAdapter();
                        rv1.setAdapter(DayAdapter);
                    }

                } catch (ParseException e) {
                    Toast.makeText(DaySalemana.this, "형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                hidekeyboard();
            }
        });
    }
    class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {


        @NonNull
        @Override
        public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.daysale_row,parent,false);
            return new DayViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
            SaleDTO items = items4.get(position);
            holder.txtViewDate.setText(items.getOrder_date());
            holder.txtViewMenu.setText(items.getMenu_name());
            holder.txtViewAmount.setText(String.valueOf(items.getDay_amount())+"개");
            holder.txtViewSale.setText(String.valueOf(items.getDay_sales())+"원");
            holder.txtViewSale.addTextChangedListener(watcher);
        }

        @Override
        public int getItemCount() {
            Log.i("test","자료개수:" + items4.size() + "");
            return items4.size();
        }
        public class DayViewHolder extends RecyclerView.ViewHolder {
            private TextView txtViewDate,txtViewMenu,txtViewAmount,txtViewSale;
            public DayViewHolder(View view4) {
                super(view4);
                txtViewDate = view4.findViewById(R.id.txtViewDate);
                txtViewMenu = view4.findViewById(R.id.txtViewMenu);
                txtViewAmount = view4.findViewById(R.id.txtViewAmount);
                txtViewSale = view4.findViewById(R.id.txtViewSale);
            }
        }
    }
    void hidekeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(rv1.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(dresult)){
                dresult = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                txtSale.setText(dresult);

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}