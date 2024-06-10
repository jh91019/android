package com.example.cafemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    Context context;
    SQLiteDatabase db;

    public InventoryDAO(Context context) {
        this.context = context;
    }

    public SQLiteDatabase dbConn() {
        db = context.openOrCreateDatabase("management.db", Context.MODE_PRIVATE, null);
        String sql = "CREATE TABLE IF NOT EXISTS inventory( product_id text, product_category text not null, product_name TEXT NOT NULL, price INTEGER NOT NULL, amount INTEGER NOT NULL, purchase_date TEXT, PRIMARY KEY(product_id))";
        //sql 실행
        db.execSQL(sql);
        return db;
    }

    //재고목록 조회
    public List<InventoryDTO> list() {
        List<InventoryDTO> items = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbConn();
            String sql = "SELECT * from inventory order by product_id";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) { //커서가 다음행에 자료가 있는 지 확인
                String product_id = cursor.getString(0);
                String product_category = cursor.getString(1);
                String product_name = cursor.getString(2);
                int price = cursor.getInt(3);
                int amount = cursor.getInt(4);
                String purchase_date = cursor.getString(5);
                //ArrayList에 추가
                items.add(new InventoryDTO(product_id, product_category, product_name, price, amount, purchase_date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return items;
    }

    //키워드 검색
    public List<InventoryDTO> search(String keyword) {
        List<InventoryDTO> items = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbConn();
            String sql = "SELECT * from inventory where product_name like '%" + keyword +  "%'" + " order by product_id";
            Log.i("test", "Search SQL : " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) { //커서가 다음행에 자료가 있는 지 확인
                String product_id = cursor.getString(0);
                String product_category = cursor.getString(1);
                String product_name = cursor.getString(2);
                int price = cursor.getInt(3);
                int amount = cursor.getInt(4);
                String purchase_date = cursor.getString(5);
                //ArrayList에 추가
                items.add(new InventoryDTO(product_id, product_category, product_name, price, amount, purchase_date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return items;
    }

    //재고등록
    public void insert(InventoryDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("INSERT INTO inventory VALUES('%s', '%s', '%s', %d, %d, '%s')", dto.getProduct_id(), dto.getProduct_category(), dto.getProduct_name(), dto.getPrice(), dto.getAmount(), dto.getPurchase_date());
            //sql 실행
            Log.i("test", "Insert SQL : " + sql);
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    //재고수정
    public void update(InventoryDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("UPDATE inventory SET product_category='%s', product_name='%s', price=%d, amount=%d, purchase_date='%s' where product_id = '%s'", dto.getProduct_category(), dto.getProduct_name(), dto.getPrice(), dto.getAmount(), dto.getPurchase_date(), dto.getProduct_id());
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    //재고삭제
    public void delete(String id) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = "DELETE FROM inventory WHERE product_id = '" + id + "'";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
}
