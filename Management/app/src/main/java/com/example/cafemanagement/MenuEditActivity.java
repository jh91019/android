package com.example.cafemanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import java.util.regex.Pattern;


public class MenuEditActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout layout;
    ImageView iv;
    EditText editCategory, editMenuId, editMenuName, editPrice;
    Chip switchRun;
    Button btnUpdate, btnDelete;
    MenuDAO menuDao;
    MenuDTO menuDto;
    int run;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);

        layout = findViewById(R.id.layout);
        iv = findViewById(R.id.iv);
        editCategory = findViewById(R.id.editCategory);
        editMenuId = findViewById(R.id.editMenuId);
        editMenuName = findViewById(R.id.editMenuName);
        editPrice = findViewById(R.id.editPrice);
        switchRun = findViewById(R.id.switchRun);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        editMenuName.setFilters(new InputFilter[] {filterAlpha});
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hidekeyboard();
                return false;
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuEditActivity.this, "이전 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        switchRun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchRun.isChecked()) {
                    run = 1;
                    switchRun.setText("판매중");
                } else {
                    run = 0;
                    switchRun.setText("임시중단");
                }
            }
        });

        menuDao = new MenuDAO(this);
        Intent intent = getIntent();
        menuDto = (MenuDTO) intent.getSerializableExtra("dto");
        //상품분류, 상품ID 변경 못하게
        assert menuDto != null;
        editCategory.setText(menuDao.findCategory(menuDto));
        editMenuId.setText(menuDto.getMenuId());
        editMenuName.setText(menuDto.getMenuName());
        editPrice.setText(Integer.toString(menuDto.getPrice()));
        if (menuDto.getRun() == 1) {
            switchRun.setChecked(true);
        } else {
            switchRun.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUpdate) {
            try {
                String category = editCategory.getText().toString();
                String menuId = editMenuId.getText().toString();
                String menuName = editMenuName.getText().toString();


                if (TextUtils.isEmpty(editMenuName.getText())) {
                    Toast.makeText(MenuEditActivity.this, "상품정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    editMenuName.requestFocus();
                    return;
                }
                int price = Integer.parseInt(editPrice.getText().toString());
                int run = (switchRun.getText() == "판매중" ? 1 : 0);

                menuDto.setCategory(category);
                menuDto.setMenuId(menuId);
                menuDto.setMenuName(menuName);
                menuDto.setPrice(price);
                menuDto.setRun(run);

                menuDao.update(menuDto);
                Toast.makeText(MenuEditActivity.this, "상품정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MenuEditActivity.this, "상품정보를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnDelete) {
//            new AlertDialog.Builder(this)
//                    .setTitle("삭제 확인")
//                    .setMessage("선택하신 메뉴를 삭제하시겠습니까?")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            menuDao.delete(menuDto);
//                            Toast.makeText(MenuEditActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
            custom_dialog();
        }
    }

    public void hidekeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void custom_dialog() {
        View dialog = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        androidx.appcompat.app.AlertDialog.Builder ab = new androidx.appcompat.app.AlertDialog.Builder(MenuEditActivity.this);
        ab.setView(dialog);

        TextView textContent = dialog.findViewById(R.id.textContent);
        textContent.setText("선택하신 메뉴를 삭제하시겠습니까?");

        final androidx.appcompat.app.AlertDialog alertDialog = ab.create();
        alertDialog.show();

        Button btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDao.delete(menuDto);
                Toast.makeText(MenuEditActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                Intent intent = new Intent(MenuEditActivity.this, MenuBoardActivity.class);
                startActivity(intent);
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
    protected InputFilter filterAlpha = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Zㄱ-ㅎ가-힣]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
}