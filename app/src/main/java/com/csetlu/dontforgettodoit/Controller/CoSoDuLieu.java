package com.csetlu.dontforgettodoit.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.csetlu.dontforgettodoit.Model.ViecCanLamM;

import java.util.ArrayList;
import java.util.List;

public class CoSoDuLieu extends SQLiteOpenHelper {

    private static final int PHIENBAN = 3;
    private static final String CSDL = "btlandroid";
    private static final String BANG = "nhacviec";
    private static final String MACV = "ma_cv";
    private static final String CONGVIEC = "congViec";
    private static final String TRANGTHAI = "trangThai";
    private static final String NGAY = "ngay";
    private static final String THOIGIAN = "thoiGian";
    private static final String TAOBANG = "CREATE TABLE "+BANG+" ("+MACV+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CONGVIEC+" TEXT, "+TRANGTHAI+" INTEGER, "+NGAY+" TEXT, "+THOIGIAN+" TEXT)";
    private static final String XOABANG = "DROP TABLE IF EXISTS "+BANG;
    private SQLiteDatabase db;

    public CoSoDuLieu(Context context) {
        super(context, CSDL, null, PHIENBAN);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(TAOBANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if (oldVersion < PHIENBAN)
        {
            db.execSQL(XOABANG);
            db.execSQL(TAOBANG);
        }
    }

    public void moCSDL(){
        db = this.getWritableDatabase();
    }

    public long themCongViec(ViecCanLamM cv){
        ContentValues banGhi = new ContentValues();
        banGhi.put(CONGVIEC, cv.layCV());
        banGhi.put(NGAY, cv.layNgay());
        banGhi.put(THOIGIAN, cv.layThoiGian());
        banGhi.put(TRANGTHAI, 0);
        return db.insert(BANG, null, banGhi);
    }

    public List<ViecCanLamM> layDsCongViec(){
        List<ViecCanLamM> dsCongViec = new ArrayList<>();
        Cursor conTro = null;
        db.beginTransaction();
            conTro = db.query(BANG, null, null, null, null, null, null);
            if (conTro != null)
                if (conTro.moveToFirst())
                    do {
                    ViecCanLamM cv = new ViecCanLamM();
                    cv.ganMaCV(conTro.getInt(conTro.getColumnIndex(MACV)));
                    cv.ganCV(conTro.getString(conTro.getColumnIndex(CONGVIEC)));
                    cv.ganNgay(conTro.getString(conTro.getColumnIndex(NGAY)));
                    cv.ganThoiGian(conTro.getString(conTro.getColumnIndex(THOIGIAN)));
                    cv.ganTT(conTro.getInt(conTro.getColumnIndex(TRANGTHAI)));
                    dsCongViec.add(cv);
                    } while (conTro.moveToNext());
        db.endTransaction();
        conTro.close();
        return dsCongViec;
    }

    public void suaCongViec(ViecCanLamM congViec){
        ContentValues banGhi = new ContentValues();
        banGhi.put(CONGVIEC, congViec.layCV());
        banGhi.put(NGAY, congViec.layNgay());
        banGhi.put(THOIGIAN, congViec.layThoiGian());
        db.update(BANG, banGhi, MACV + "=?", new String[] {String.valueOf(congViec.layMaCV())});
    }

//    public void suaNgay(int maCV, String ngay){
//        ContentValues banGhi = new ContentValues();
//        banGhi.put(NGAY, ngay);
//        db.update(BANG, banGhi, MACV + "=?", new String[] {String.valueOf(maCV)});
//    }
//
//    public void suaThoiGian(int maCV, String thoiGian){
//        ContentValues banGhi = new ContentValues();
//        banGhi.put(THOIGIAN, thoiGian);
//        db.update(BANG, banGhi, MACV + "=?", new String[] {String.valueOf(maCV)});
//    }

    public void chuyenTrangThai(int maCV, int trangThai){
        ContentValues banGhi = new ContentValues();
        banGhi.put(TRANGTHAI, trangThai);
        db.update(BANG, banGhi, MACV + "=?", new String[] {String.valueOf(maCV)});
    }

    public void xoaBanGhi(int maCV){
        db.delete(BANG, MACV +"=?", new String[] {String.valueOf(maCV)});
    }
}
