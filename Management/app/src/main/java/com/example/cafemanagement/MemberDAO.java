package com.example.cafemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    Context context;
    SQLiteDatabase db;

    public MemberDAO(Context context) {
        this.context = context;
    }

    public SQLiteDatabase dbConn() {
        db = context.openOrCreateDatabase("management.db",Context.MODE_PRIVATE, null);
        String sql = "CREATE TABLE if not exists member (id TEXT(4) PRIMARY KEY, phone TEXT(13) NOT NULL UNIQUE, sdate date NOT NULL, orderc INTEGER DEFAULT 1, coupon INTEGER DEFAULT 0, stamp INTEGER DEFAULT 1, odate DATE NOT NULL, rowid INT PRIMARY KEY NOT NULL UNIQUE, CONSTRAINT phone_un UNIQUE(phone))";
        db.execSQL(sql);
        return db;
    }

    public List<MemberDTO> list(String id1) {
        ArrayList<MemberDTO> items = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbConn();
            String csql = String.format("select count(*) from member where id='%s'", id1);
            cursor = db.rawQuery(csql, null);
            if (cursor.getCount()==1) {
                String sql = String.format("select * from member where id ='%s'", id1);
                // Log.i("test","sql:"+sql);
                cursor = db.rawQuery(sql, null);
                // Log.i("test","test:"+cursor.moveToNext());
                while(cursor.moveToNext()) {
                    // Log.i("text","while");
                    String id = cursor.getString(0);
                    String phone = cursor.getString(1);
                    String sdate = cursor.getString(2);
                    int orderc = cursor.getInt(3);
                    int coupon = cursor.getInt(4);
                    int stamp = cursor.getInt(5);
                    String odate = cursor.getString(6);
                    int rowid = cursor.getInt(7);
                    items.add(new MemberDTO(id, phone, sdate, orderc, coupon, stamp, odate, rowid));
                    // Log.d("test", "add:"+String.valueOf(items));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null) cursor.close();
            if(db!=null) db.close();
        }
        // Log.i("test","items:"+items);
        return items;
    }

    public List<MemberDTO> sort(int sort) {
        ArrayList<MemberDTO> items1 = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = null;
        try {
            db = dbConn();
            switch(sort) {
                case 1: sql = "select * from member order by sdate";
                break;
                case 2: sql = "select * from member order by sdate desc";
                break;
                case 3: sql = "select * from member order by orderc";
                break;
                case 4: sql = "select * from member order by orderc desc" ;
                break;
                case 5: sql = "select * from member order by id";
                break;
            }
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String phone = cursor.getString(1);
                String sdate = cursor.getString(2);
                int orderc = cursor.getInt(3);
                int coupon = cursor.getInt(4);
                int stamp = cursor.getInt(5);
                String odate = cursor.getString(6);
                int rowid = cursor.getInt(7);
                items1.add(new MemberDTO(id, phone, sdate, orderc, coupon, stamp, odate, rowid));
                // Log.d("test", "add:"+String.valueOf(items1));
            }
        }   catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(db!=null) db.close();
            if(cursor!=null) cursor.close();
        }
        return items1;
    }

    public void delete(String id) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("delete from member where id='%s'", id);
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public int row(String id) {
        int rowc = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db  = dbConn();
            String sql = String.format("select rowid from member where id='%s'", id);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                rowc = cursor.getInt(0);
                // Log.i("test1", String.valueOf(rowc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor!=null) cursor.close();
            if(db!=null) db.close();
        }
        return rowc;
    }

    public String update(String idc, String phonec, int rowc) {
        SQLiteDatabase db = null;
        ArrayList<MemberDTO> items = new ArrayList<>();
        String result="";
        try {
            db = dbConn();
            String sql = String.format("update member set id = '%s', phone = '%s' where rowid = %d", idc, phonec, rowc);
            Log.i("test","sql:"+sql);
            db.execSQL(sql);
            result="success";
        } catch (Exception e) {
            e.printStackTrace();
            result="fail";
        } finally {
            if(db!=null) db.close();
        }
        return result;
    }
}

