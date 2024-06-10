package com.example.cafemanagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class InventoryCustomDialog extends Dialog {

    private EditText et_text;
    private Context mContext;
    private List<InventoryDTO> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        Button YesButton = findViewById(R.id.btnYes);
        Button NoButton = findViewById(R.id.btnNo);

        // 버튼 리스너 설정
        YesButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시
                // ...코드..
                Toast.makeText(mContext, et_text.getText().toString(), Toast.LENGTH_SHORT).show();
                // Custom Dialog 종료
                dismiss();
            }
        });
        NoButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '취소' 버튼 클릭시
                // Custom Dialog 종료
                dismiss();
            }
        });

    }

    public InventoryCustomDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }


}