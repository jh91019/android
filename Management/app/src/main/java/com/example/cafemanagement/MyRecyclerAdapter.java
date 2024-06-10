package com.example.cafemanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    Context context;
    List<MenuDTO> items;
    static String option = "";

    public MyRecyclerAdapter(Context context, List<MenuDTO> menus, String option) {
        this.context = context;
        this.items = menus;
        this.option = option;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_row, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuDTO menuDto = items.get(position);
        holder.tvCategory.setText(menuDto.getCategory());
        holder.tvMenuName.setText(menuDto.getMenuName());
        holder.tvPrice.setText(menuDto.getPrice() + "");
        if (menuDto.getRun() == 1) {
            holder.tvRun.setText("○");
        } else {
            holder.tvRun.setText("Ⅹ");
        }
    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory, tvMenuName, tvPrice, tvRun;

        public void custom_dialog() {
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialog = inflater.inflate(R.layout.custom_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(dialog);

            TextView textContent = dialog.findViewById(R.id.textContent);
            Button btnYes = dialog.findViewById(R.id.btnYes);
            Button btnNo = dialog.findViewById(R.id.btnNo);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            switch (MyRecyclerAdapter.option) {
                case "edit":
                    textContent.setText("선택하신 메뉴를 수정하시겠습니까?");

                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MenuEditActivity.class);
                            //전달할 데이터, 리스트.get(인덱스)
                            MenuDTO menuDto = items.get(getLayoutPosition());
                            Log.i("test", "가져오는 값 확인 :" + menuDto);

                            intent.putExtra("dto", menuDto);
                            context.startActivity(intent);
                            alertDialog.dismiss();
                        }
                    });

                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    break;
                case "delete":
                    textContent.setText("선택하신 메뉴를 삭제하시겠습니까?");

                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            items.remove(getLayoutPosition());
                            notifyItemRemoved(getLayoutPosition());
                            notifyItemRangeChanged(getLayoutPosition(), items.size());
                            alertDialog.dismiss();
                        }
                    });

                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    break;
            }
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvMenuName = itemView.findViewById(R.id.tvMenuName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRun = itemView.findViewById(R.id.tvRun);

            tvMenuName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    custom_dialog();
                }
            });
        }
    }
}