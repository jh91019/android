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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MonthSalemana extends AppCompatActivity {

    Button btnSale;
    EditText txtDateSearch;
    TextView txtSale;
    RecyclerView rv1;
    RecyclerView.Adapter MonAdapter;
    List<SaleDTO> items6;
    OrdersDAO dao;
    int monresult;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String mresult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_salemana);

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
                SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM");
                dateForm.setLenient(false); //엄격한 모드로 설정
                try {
                    Date parseDate = dateForm.parse(order_date);
                    monresult = dao.mon_sale(order_date);
                    if (monresult==0) {
                        Toast.makeText(MonthSalemana.this,"해당날짜는 없습니다.",Toast.LENGTH_SHORT).show();
                    } else {
                        items6 = dao.list6(order_date);
                        txtSale.setText(String.valueOf(monresult));
                        MonAdapter = new MonAdapter();
                        rv1.setAdapter(MonAdapter);
                    }
                } catch (ParseException e) {
                    Toast.makeText(MonthSalemana.this, "형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                hidekeyboard();
//                monresult = dao.mon_sale(order_date);
//                Log.i("test","mon result:"+monresult);
//                if (monresult==0) {
//                    Toast.makeText(MonthSalemana.this,"해당날짜는 없습니다.",Toast.LENGTH_SHORT).show();
//                } else {
//                    items6 = dao.list6(order_date);
//                    Log.i("test","items"+items6);
//                    txtSale.setText(String.valueOf(monresult));
//                    MonAdapter = new MonthSalemana.MonAdapter();
//                    rv1.setAdapter(MonAdapter);
//                }
//                hidekeyboard();
            }
        });
    }

    class MonAdapter extends RecyclerView.Adapter<MonthSalemana.MonAdapter.MonViewHolder> {

        @NonNull
        @Override
        public MonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.monsale_row,parent,false);
            return new MonViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull MonViewHolder holder, int position) {
            SaleDTO items = items6.get(position);
            holder.txtViewDatem.setText(items.getOrder_date());
            holder.txtViewMenum.setText(items.getMenu_name());
            holder.txtViewAmountm.setText(String.valueOf(items.getMonth_amount())+"개");
            holder.txtViewSalem.setText(String.valueOf(items.getMonth_sales())+"원");

        }

        @Override
        public int getItemCount() {
            Log.i("test","자료개수:" + items6.size() + "");
            return items6.size();
        }
        public class MonViewHolder extends RecyclerView.ViewHolder {
            private TextView txtViewDatem,txtViewMenum,txtViewAmountm,txtViewSalem;
            public MonViewHolder(View view5) {
                super(view5);
                txtViewDatem = view5.findViewById(R.id.txtViewDatem);
                txtViewMenum = view5.findViewById(R.id.txtViewMenum);
                txtViewAmountm = view5.findViewById(R.id.txtViewAmountm);
                txtViewSalem = view5.findViewById(R.id.txtViewSalem);
            }
        }
    }
    void hidekeyboard() { // 검색후 키보드 자동닫기
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(rv1.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    TextWatcher watcher = new TextWatcher() { //금액단위 콤마설정
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(mresult)){
                mresult = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                txtSale.setText(mresult);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}