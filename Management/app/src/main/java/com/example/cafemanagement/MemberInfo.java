package com.example.cafemanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemberInfo extends AppCompatActivity {
    Button btnDelete, btnUpdate;
    MemberDAO dao;
    MemberDTO dto;
    EditText txtId, txtPhone;
    TextView txtSdate, txtOrderc, txtCoupon, txtStamp, txtOdate;
    int rowc;
    ConstraintLayout layout;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        txtSdate = findViewById(R.id.txtSdate);
        txtOrderc = findViewById(R.id.txtOrderc);
        txtCoupon = findViewById(R.id.txtCoupon);
        txtStamp = findViewById(R.id.txtStamp);
        txtOdate = findViewById(R.id.txtOdate);
        txtId = findViewById(R.id.txtId);
        txtPhone = findViewById(R.id.txtPhone);
        txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        dao = new MemberDAO(this);
        Intent intent = getIntent();
        dto = (MemberDTO) intent.getSerializableExtra("dto");
        Log.i("test", String.valueOf(dto));
        layout = (ConstraintLayout) findViewById(R.id.layout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //if(context.getResources().getConfiguration().hardKeyboardHidden== Configuration.HARDKEYBOARDHIDDEN_YES) {
                    hidekeyboard();
               // }
                return false;
            }
        });
        txtId.setText(dto.getId());
        txtPhone.setText(dto.getPhone());
        txtSdate.setText(dto.getSdate());
        txtOrderc.setText(String.valueOf(dto.getOrderc()));
        txtCoupon.setText(String.valueOf(dto.getCoupon()));
        txtStamp.setText(String.valueOf(dto.getStamp()));
        txtOdate.setText(dto.getOdate());

        txtSdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemberInfo.this, "가입일자는 수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        txtOrderc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemberInfo.this, "주문횟수는 수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        txtCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemberInfo.this, "쿠폰은 수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        txtStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemberInfo.this, "스탬프는 수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        txtOdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MemberInfo.this, "마지막 주문일자는 수정할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = dto.getId();
                rowc = dao.row(id);
                String idc = txtId.getText().toString();
                String phonec = txtPhone.getText().toString();
                if(idc=="" || phonec=="") {
                    Toast.makeText(MemberInfo.this, "값을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else if (idc.length()!=4) {
                    Toast.makeText(MemberInfo.this, "적립번호는 4자리입니다.", Toast.LENGTH_SHORT).show();
                } else if ((phonec.length()!=12)||getCharNumber(phonec, '-')!=2) {
                    Toast.makeText(MemberInfo.this, "전화번호가 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    String result=dao.update(idc, phonec, rowc);
                    Log.i("test","result:"+result);
                    if(result.equals("success")) {
                        Toast.makeText(MemberInfo.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                        hidekeyboard();
                    } else {
                        Toast.makeText(MemberInfo.this, "적립번호와 전화번호는 중복될 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            int getCharNumber(String str, char c) {
                int count = 0;
                for(int i=0;i<str.length();i++) {
                    if (str.charAt(i)== c)
                        count++;
                } return count;
            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom_dialog();
            }
        });

    }

//    void showDialog() {
//        AlertDialog.Builder ab = new AlertDialog.Builder(MemberInfo.this)
//                .setTitle("삭제")
//                .setMessage("정말 삭제하시겠습니까?")
//                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String id = txtId.getText().toString();
//                        dao.delete(id);
//                        Intent intent = new Intent(MemberInfo.this, MainActivity.class);
//                        startActivity(intent);
//                        Toast.makeText(MemberInfo.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                })
//                .setNegativeButton("취소", null);
//        AlertDialog abDlg = ab.create();
//        abDlg.show();
//    }

    public void hidekeyboard() {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void custom_dialog() {
        View dialog = getLayoutInflater().inflate(R.layout.activity_custom_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(MemberInfo.this);
        ab.setView(dialog);

        final AlertDialog alertDialog = ab.create();
        alertDialog.show();

        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtId.getText().toString();
                        dao.delete(id);
                        Intent intent = new Intent(MemberInfo.this, MainMemberActivity.class);
                        startActivity(intent);
                        Toast.makeText(MemberInfo.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}