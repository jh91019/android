package com.example.cafemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class OrdersDAO {

    Context context;
    SQLiteDatabase db;

    public OrdersDAO(Context context) {
        this.context = context;
    }

    public SQLiteDatabase dbConn() {
        db = context.openOrCreateDatabase("management.db", Context.MODE_PRIVATE,null);
        String sql = "create table if not exists orders (order_num text , order_date date not null, menu_id integer not null, amount integer not null, total_price)";
        db.execSQL(sql);
        return db;
    }

    public List<OrdersDTO>list() {
        List<OrdersDTO>items = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try { //주문내역 전체리스트
            db = dbConn();

            String sql="select order_num, order_date, o.menu_id,menu_name,amount, m.price*o.amount \n" +
                    "from orders o, menuList m\n" +
                    "where  o.menu_id=m.menu_id\n" +
                    "order by order_num desc";

            cursor = db.rawQuery(sql,null);
            while (cursor.moveToNext()) {
                String order_num = cursor.getString(0);
                String order_date = cursor.getString(1);
                String menu_name=cursor.getString(3);
                int amount = cursor.getInt(4);
                int total_price = cursor.getInt(5);
                items.add(new OrdersDTO(order_num,order_date,menu_name,amount,total_price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }
        return items;
    }
    public List<OrdersDTO>list1(String ordernum) {
        List<OrdersDTO>items1 = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor1 = null;
        try {
            db = dbConn();
            String osql = String.format("select count(*) from orders where order_num='%s'",ordernum);
            cursor1 = db.rawQuery(osql,null);
            if (cursor1.getCount()==1) {
                //주문번호로 검색
                String sql=String.format("select order_num, order_date, o.menu_id,menu_name,amount, m.price*o.amount\n" +
                        "from orders o, menuList m\n" +
                        "where  m.menu_id=o.menu_id and order_num='%s'\n" +
                        "order by order_num desc",ordernum);
                cursor1 = db.rawQuery(sql,null);
                while (cursor1.moveToNext()) {
                    String order_num = cursor1.getString(0);
                    String order_date = cursor1.getString(1);
                    String menu_name=cursor1.getString(3);
                    int amount = cursor1.getInt(4);
                    int total_price = cursor1.getInt(5);
                    items1.add(new OrdersDTO(order_num,order_date,menu_name,amount,total_price));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor1 != null)
                cursor1.close();
            if(db != null)
                db.close();
        }
        return items1;
    }

    public List<OrdersDTO>list2(String orderdate) {
        List<OrdersDTO>items2 = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor2 = null;
        try {
            db = dbConn();
            //날짜로 검색
            String orsql=String.format("select order_num, order_date, o.menu_id,menu_name,amount, m.price*o.amount\n" +
                    "from orders o, menuList m\n" +
                    "where  m.menu_id=o.menu_id and order_date='%s'\n" +
                    "order by order_num desc",orderdate);
            cursor2 = db.rawQuery(orsql,null);
                while (cursor2.moveToNext()) {
                    String order_num = cursor2.getString(0);
                    String order_date = cursor2.getString(1);
                    String menu_name=cursor2.getString(3);
                    int amount = cursor2.getInt(4);
                    int total_price = cursor2.getInt(5);
                    items2.add(new OrdersDTO(order_num,order_date,menu_name,amount,total_price));
                }
           //
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor2 != null)
                cursor2.close();
            if(db != null)
                db.close();
        }
        Log.d("test", "items2:"+ items2);
        return items2;
    }
    public int day_sale(String daydate) {
        int dayresult = 0;
        SQLiteDatabase db = null;
        Cursor cursor3 = null;
        try {
            db = dbConn();
            //일별 총매출
            String daysql = String.format("select sum(m.price*o.amount)\n" +
                    "from orders o,menuList m\n" +
                    "where m.menu_id=o.menu_id and order_date='%s'",daydate);
            cursor3 = db.rawQuery(daysql,null);
            while (cursor3.moveToNext()) {
                dayresult = cursor3.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor3 != null)
                cursor3.close();
            if(db != null)
                db.close();
        }
        Log.d("test", "dayresult:"+ dayresult);
        return dayresult;
    }
    public List list4(String orderdate2) {
        ArrayList<SaleDTO> items4 = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor4 = null;
        try {
            db = dbConn();
            //상품별 일별매출
            String daysalesql=String.format("select order_date, m.menu_name,sum(amount),sum(amount)*price\n" +
                    "from orders o,menuList m\n" +
                    "where m.menu_id=o.menu_id and order_date='%s'\n" +
                    "group by m.menu_name",orderdate2);
            cursor4 = db.rawQuery(daysalesql,null);
            Log.i("test","sql:"+daysalesql);
            while (cursor4.moveToNext()) {
                String order_date = cursor4.getString(0);
                String menu_name = cursor4.getString(1);
                int day_amount = cursor4.getInt(2);
                int day_sales = cursor4.getInt(3);
                int month_amount = 0;
                int month_sales = 0;
                items4.add(new SaleDTO(order_date,menu_name, day_amount, day_sales, month_amount, month_sales));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor4 != null)
                cursor4.close();
            if(db != null)
                db.close();
        }
        Log.d("test", "items4:"+ items4);
        return items4;
    }
    public int mon_sale(String mondate) {
        int monresult = 0;
        SQLiteDatabase db = null;
        Cursor cursor5 = null;
        try { //월별 총매출
            db = dbConn();
            String monsql="select sum(m.price*o.amount)" +
                    " from orders o,menuList m" +
                    " where m.menu_id=o.menu_id and strftime('%Y-%m', order_date)='"+mondate+"'";
            cursor5 = db.rawQuery(monsql,null);
            while (cursor5.moveToNext()) {
                monresult = cursor5.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor5 != null)
                cursor5.close();
            if(db != null)
                db.close();
        }
        Log.d("test", "monresult:"+ monresult);
        return monresult;
    }
    public List list6(String orderdate3) {
        ArrayList<SaleDTO> items6 = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor6 = null;
        Log.i("test", "2222222");
        try { // 상품별 월별매출
            db = dbConn();
            Log.i("test", "2222222");
            String monsalesql="select (select substr(order_date,1,7)), m.menu_name,sum(amount),sum(amount)*price AS 매출" +
                    " from orders o,menuList m" +
                    " where m.menu_id=o.menu_id and strftime('%Y-%m', order_date)='" + orderdate3 + "'" +
                    " group by m.menu_name" +
                    " order by 매출 desc";
            cursor6 = db.rawQuery(monsalesql,null);
            Log.i("test","sql:"+monsalesql);
            while (cursor6.moveToNext()) {
                String order_date = cursor6.getString(0);
                String menu_name = cursor6.getString(1);
                int month_amount = cursor6.getInt(2);
                int month_sales = cursor6.getInt(3);
                int day_amount = 0;
                int day_sales = 0;
                items6.add(new SaleDTO(order_date,menu_name, day_amount, day_sales, month_amount, month_sales));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor6 != null)
                cursor6.close();
            if(db != null)
                db.close();
        }
        Log.i("test", "items6:"+ items6);
        return items6;
    }
}