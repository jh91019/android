package com.example.cafemanagement;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuAddActivity extends AppCompatActivity {
    ImageView iv;
    Spinner spinner;
    EditText addMenuName, addPrice;
    Button btnSave, btnComplete;
    Chip switchRun;
    MenuDAO menuDao;
    RecyclerView rv;
    MyRecyclerAdapter adapterR;
    List<MenuDTO> items;
    private boolean checkFirst = true;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);

        iv = findViewById(R.id.iv);
        spinner = findViewById(R.id.spinner);
        addMenuName = findViewById(R.id.addMenuName);
        addPrice = findViewById(R.id.addPrice);
        switchRun = findViewById(R.id.switchRun);
        layout = findViewById(R.id.layout);

        btnSave = findViewById(R.id.btnSave);
        btnComplete = findViewById(R.id.btnComplete);
        rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        List<String> group = Arrays.asList(getResources().getStringArray(R.array.group));
        ArrayAdapter adapterS = new ArrayAdapter(this, android.R.layout.simple_spinner_item, group) {
            @Override
            public int getCount() {
                return group.size() - 1;
            }
        };
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterS);
        spinner.setSelection(group.size() - 1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (checkFirst) {
                    spinner.setSelection(group.size() - 1);
                    checkFirst = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MenuAddActivity.this, "분류를 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        adapterR = new MyRecyclerAdapter(MenuAddActivity.this, items, "delete");
        rv.setAdapter(adapterR);
        items = new ArrayList<>();
        menuDao = new MenuDAO(this);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuAddActivity.this, "이전 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hidekeyboard();
                return false;
            }
        });

        switchRun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchRun.isChecked()) {
                    switchRun.setText("판매중");
                } else {
                    switchRun.setText("임시중단");
                }
            }
        });
        switchRun.setChecked(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = (spinner.getSelectedItem().equals("분류") ? "" : spinner.getSelectedItem().toString());
                String menuName = addMenuName.getText().toString();
                int price = Integer.parseInt(addPrice.getText().toString().equals("") ? "0" : addPrice.getText().toString());
                int run = (switchRun.getText() == "판매중" ? 1 : 0);

                try {
                    if (TextUtils.isEmpty(addMenuName.getText()) || category.equals("")) {
                        Log.i("test", "save 클릭" + category + " & " + menuName + " & " + price + " & " + run);
                        Toast.makeText(MenuAddActivity.this, "상품정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        addMenuName.requestFocus();
                        return;
                    }

                    adapterR.items.add(new MenuDTO(category, menuName, price, run));
                    adapterR.notifyDataSetChanged();
                    Toast.makeText(MenuAddActivity.this, "임시저장 되었습니다.", Toast.LENGTH_SHORT).show();

                    spinner.setSelection(group.size() - 1);
                    addMenuName.setText("");
                    addPrice.setText("");
                } catch (
                        Exception e) {
                    e.printStackTrace();

                    if (TextUtils.isEmpty(addMenuName.getText())) {
                        addMenuName.requestFocus();
                    } else {
                        addPrice.requestFocus();
                    }
                    Toast.makeText(MenuAddActivity.this, "상품정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (items.size() == 0) {
                        Toast.makeText(MenuAddActivity.this, "새로 추가한 메뉴가 없습니다. 임시저장 목록을 확인해주세요.", Toast.LENGTH_SHORT).show();
                        if (TextUtils.isEmpty(addMenuName.getText())) {
                            addMenuName.requestFocus();
                        } else {
                            addPrice.requestFocus();
                        }
                    } else {
                        for (int i = 0; i < items.size(); i++) {
                            menuDao.insertDB(items.get(i));
                        }
                        Toast.makeText(MenuAddActivity.this, items.size() + "개 품목이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapterR = new MyRecyclerAdapter(MenuAddActivity.this, items, "delete");
        rv.setAdapter(adapterR);
    }

    public void hidekeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}