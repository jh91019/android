package com.example.cafemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

public class ManagerDAO {

    EditText editId;
    Integer editPw;
    Context context;
    SQLiteDatabase db;
    public ManagerDAO(Context context) {
        this.context = context;
    }

    public SQLiteDatabase dbConn() {
        db = context.openOrCreateDatabase("management.db", Context.MODE_PRIVATE, null);
        String sql = "create table if not exists manager (userId TEXT NOT NULL UNIQUE, userPw TEXT NOT NULL UNIQUE, PRIMARY KEY(userId,userPw))";
        Log.d("test", "sql : " + sql);
        db.execSQL(sql);
//        String sql1 = "delete from manager";
//        db.execSQL(sql1);
//        String sql2 = "insert into manager values('manager', '1234')";
//        db.execSQL(sql2);
        return db;
    }

    public int insert(String id, String pw) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int result = 0;
        try {
            db = dbConn();
            String sql = String.format("select userId from manager where userId='%s' and userPw='%s'" , id, pw);
            Log.i("test", "sql : " + sql);
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()) {
                result = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return result;
    }

    public int update(String idE, String pwE, String id) {
        SQLiteDatabase db = null;
        int result = 0;
        try {
            db = dbConn();
            String sql = String.format("update manager set userId = '%s', userPw = '%s' where userId='%s'", idE, pwE, id);
            db.execSQL(sql);
            result = 1;
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
        return result;
    }
}




