package com.example.cafemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainMemberActivity extends AppCompatActivity {

    EditText editSearch;
    Button btnSearch;
    Spinner sp;
    RecyclerView rv;
    MemberDAO dao;
    List<MemberDTO> items;
    List<String> orderItems;
    RecyclerView.Adapter myAdapter;
    ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_member);
        layout = (ConstraintLayout) findViewById(R.id.layout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hidekeyboard();
                return false;
            }
        });
        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hidekeyboard();
                return false;
            }
        });
        sp = findViewById(R.id.sp);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        dao = new MemberDAO(this);
        orderItems = Arrays.asList(getResources().getStringArray(R.array.order_array));
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, orderItems) {
            @Override
            public int getCount() {
                return orderItems.size() - 1;
            }
        };
        sp.setAdapter(adapter);
        sp.setSelection(orderItems.size() - 1);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editSearch.setText("");
                if (position == 0) {
                    int sort = 1;
                    items = dao.sort(sort);
                    myAdapter = new MyAdapter();
                    rv.setAdapter(myAdapter);
                } else if (position == 1) {
                    int sort = 2;
                    items = dao.sort(sort);
                    myAdapter = new MyAdapter();
                    rv.setAdapter(myAdapter);
                } else if (position == 2) {
                    int sort = 3;
                    items = dao.sort(sort);
                     myAdapter = new MyAdapter();
                    rv.setAdapter(myAdapter);
                } else if (position == 3) {
                    int sort = 4;
                    items = dao.sort(sort);
                    myAdapter = new MyAdapter();
                    rv.setAdapter(myAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editSearch.getText().toString();
                if (id.length() != 4) {
                    Toast.makeText(MainMemberActivity.this, "적립번호는 4자리입니다.", Toast.LENGTH_SHORT).show();
                    onResume();
                } else {
                    items = dao.list(id);
                    if (items.size() == 0) {
                        Toast.makeText(MainMemberActivity.this, "없는 적립번호입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Log.d("test", "items:"+ items);
                        myAdapter = new MyAdapter();
                        rv.setAdapter(myAdapter);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        items = dao.sort(5);
        myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
        super.onResume();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        Context context;

        public MyAdapter() {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_member_list, parent, false);
            return new ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MemberDTO dto = items.get(position);
            // Log.i("test", "dto:" + dto);
            holder.text1.setText(dto.getId());
            holder.text2.setText(dto.getPhone());
            holder.text3.setText(dto.getSdate());
            holder.text4.setText(String.valueOf(dto.getOrderc()));
            holder.text5.setText(String.valueOf(dto.getCoupon()));
            holder.text6.setText(String.valueOf(dto.getStamp()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainMemberActivity.this, "수정을 원하시면 적립번호를 길게 누르세요", Toast.LENGTH_SHORT).show();
                }
            });

            holder.text1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String txtId = ((TextView) v).getText().toString();
                    //items1 = dao.info(txtId);
                    // MemberDTO dto = items.get(position);
                    // Log.i("test", "dto:" + dto);
                    Intent intent = new Intent(MainMemberActivity.this, MemberInfo.class);
                    intent.putExtra("dto", dto);
                    startActivity(intent);
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            // Log.i("test", "자료개수:" + items.size() + "");
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView text1, text2, text3, text4, text5, text6;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(R.id.text1);
                text2 = itemView.findViewById(R.id.text2);
                text3 = itemView.findViewById(R.id.text3);
                text4 = itemView.findViewById(R.id.text4);
                text5 = itemView.findViewById(R.id.text5);
                text6 = itemView.findViewById(R.id.text6);
            }
        }
    }

    void hidekeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}