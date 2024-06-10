package com.example.cafemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    Context context;
    SQLiteDatabase db;

    public MenuDAO(Context context) {
        this.context = context;
    }

    public SQLiteDatabase dbConn() {
        db = context.openOrCreateDatabase("management.db", Context.MODE_PRIVATE, null);
        String PRAGMA = "PRAGMA foreign_keys = ON";
        String createCT = "CREATE TABLE if not exists categoryTab(category_id TEXT(2) PRIMARY KEY, category text UNIQUE)";
        String createML = "CREATE TABLE if not exists menuList(" +
                "category_id TEXT, menu_no integer primary key AUTOINCREMENT, " +
                "menu_name text UNIQUE, menu_id TEXT AS (printf('%s%03d', category_id, menu_no)) stored, " +
                "price integer default 0 not null, " +
                "run integer not null check (run in (0,1)) default 0, " +
                "foreign key (category_id) references categoryTab (category_id))";
        String createView = "create view if not exists menuView as select category, menu_id, menu_name, price, run " +
                "from categoryTab c, menuList m where c.category_id=m.category_id";
        db.execSQL(PRAGMA);
        db.execSQL(createML);
        db.execSQL(createCT);
        db.execSQL(createView);
        Log.i("test", "**여기는 MenuDAO : dbConn ");

//        Cursor cursor = null;
//        try {
//            String sqlCheck = "select exists (select * from categoryTab)";
////        카테고리 초기설정
//            cursor = db.rawQuery(sqlCheck, null);
//            if (cursor.getCount() == 0) {
//                Log.i("test", "**여기는 MenuDAO sqlCheck, cursor.getCount :" + cursor.getCount());
//            } else {
//                String sql1 = "insert into categoryTab values ('cf', 'coffee')";
//                String sql2 = "insert into categoryTab values ('bv', 'beverage')";
//                String sql3 = "insert into categoryTab values ('te', 'tea')";
//                String sql4 = "insert into categoryTab values ('fd', 'food')";
//                db.execSQL(sql1);
//                db.execSQL(sql2);
//                db.execSQL(sql3);
//                db.execSQL(sql4);
//                Log.i("test", "**여기는 MenuDAO sqlCheck, cursor.getCount :" + cursor.getCount());
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        } finally {
//            if (cursor != null) cursor.close();
//        }
        return db;
    }

    public void insertDB(MenuDTO dto) {
        Log.i("test", "dto:" + dto);
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("Insert into menuList (category_id, menu_name, price, run) values((select category_id from categoryTab where category='%s'), '%s', %d, %d)", dto.getCategory(), dto.getMenuName(), dto.getPrice(), dto.getRun());
            db.execSQL(sql);
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public void update(MenuDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = String.format("update menuList set " +
                    "menu_name='%s', price=%d, run=%d " +
                    "where menu_id='%s'", dto.getMenuName(), dto.getPrice(), dto.getRun(), dto.getMenuId());
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public void delete(MenuDTO dto) {
        SQLiteDatabase db = null;
        try {
            db = dbConn();
            String sql = "delete from menuList where menu_id='" + dto.getMenuId() + "'";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) db.close();
        }
    }

    public String findCategory(MenuDTO dto) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String category = null;

        try {
            db = dbConn();
            Log.i("test", "**여기는 MenuDAO findCategory : dbConn ok");
            String sql = "select category from menuView where menu_id='" + dto.getMenuId() + "'";
            cursor = db.rawQuery(sql, null);

            try {
                if (cursor.moveToNext()) {
                    category = cursor.getString(0);
                    Log.i("test", "**여기는 MenuDAO findCategory : " + sql + ", " + category);
                }
            } catch (CursorIndexOutOfBoundsException exception) {
                exception.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        Log.i("test", "**여기는 MenuDAO 카테고리명 가져오기 성공");
        return category;
    }

    public List<MenuDTO> list() {
        List<MenuDTO> items = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbConn();
            String sql = "select * from menuView";
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String category = cursor.getString(0);
                String menuId = cursor.getString(1);
                String menuName = cursor.getString(2);
                int price = cursor.getInt(3);
                int run = cursor.getInt(4);
                items.add(new MenuDTO(category, menuId, menuName, price, run));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return items;
    }

    public List<MenuDTO> list(String keyword) {
        List<MenuDTO> items = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "";

        try {
            db = dbConn();
            if (keyword.equals("coffee")) {
                sql = "Select * from menuView where category='coffee' order by menu_id";
            } else if (keyword.equals("beverage")) {
                sql = "Select * from menuView where category='beverage' order by menu_id";
            } else if (keyword.equals("tea")) {
                sql = "Select * from menuView where category='tea' order by menu_id";
            } else if (keyword.equals("food")) {
                sql = "Select * from menuView where category='food' order by menu_id";
            } else if (keyword.equals("all") || keyword.equals("")) {
                sql = "Select * from menuView order by category, menu_id";
            } else {
                sql = "Select * from menuView where menu_name like '%" + keyword + "%' order by category, menu_id";
            }
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String category = cursor.getString(0);
                String menuId = cursor.getString(1);
                String menuName = cursor.getString(2);
                int price = cursor.getInt(3);
                int run = cursor.getInt(4);
                items.add(new MenuDTO(category, menuId, menuName, price, run));
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return items;
    }
}