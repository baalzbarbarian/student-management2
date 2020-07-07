package com.example.ass_quanlysinhvien.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ass_quanlysinhvien.Model.mStudents;
import com.example.ass_quanlysinhvien.Model.mClass;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private static final String DATABASE_NAME = "fpolyManager";
    private static final String CLASSTABLE = "classfpoly";
    private static final String STUDENTSTABLE = "students";
    private static final String ID = "id";
    private static final String CODE = "code";
    private static final String CLASSNAME = "clname";
    private static final String STNAME = "stname";
    private static final String BORN = "born";
    private static final String tag = "Database";

    private static final String classTable = "CREATE TABLE " +CLASSTABLE+ "(" +
            ID+ " integer PRIMARY KEY AUTOINCREMENT, " +
            CODE+ " TEXT, " +
            CLASSNAME+ " TEXT);";

    private static final String stTable = "CREATE TABLE if not exists " +STUDENTSTABLE+ " (" +
            ID+ " integer PRIMARY KEY AUTOINCREMENT, " +
            CODE+ " TEXT not null, " +
            STNAME+ " TEXT, " +
            BORN+ " TEXT, " +
            "FOREIGN KEY ( "+CODE+" ) REFERENCES " +CLASSTABLE + " ( "+CODE+" ));";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(classTable);
        db.execSQL(stTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +CLASSTABLE);
        db.execSQL("DROP TABLE IF EXISTS " +STUDENTSTABLE);
        onCreate(db);
    }


    public boolean addFpolyClass (String code, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, code);
        values.put(CLASSNAME, name);

        long result = db.insert(CLASSTABLE, null, values);
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public int addStudent (mStudents mStudents) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CODE, mStudents.getClassCode());
        values.put(STNAME, mStudents.getStudentName());
        values.put(BORN, mStudents.getStudentBirthday());

        try {
            if(db.insert(STUDENTSTABLE, null, values) < 0) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(tag, ex.toString());
        }
        return 1;

    }
    public List<mClass> getAllClass() {
        db = this.getWritableDatabase();
        List<mClass> listClass = new ArrayList<>();
        String sql = "SELECT * FROM " +CLASSTABLE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mClass m = new mClass();
            m.setField1(Integer.parseInt(cursor.getString(0)));
            m.setField2(cursor.getString(1));
            m.setField3(cursor.getString(2));
            listClass.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return listClass;
    }


    public List<mStudents> getAllStudents() {
        db = this.getWritableDatabase();
        List<mStudents> st = new ArrayList<>();
        String sqlAllSt = "SELECT * FROM " +STUDENTSTABLE;

        Cursor cursor = db.rawQuery(sqlAllSt, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mStudents mStudents = new mStudents();
            mStudents.setId(Integer.parseInt(cursor.getString(0)));
            mStudents.setStudentName(cursor.getString(2));
            mStudents.setStudentBirthday(cursor.getString(3));
            st.add(mStudents);
            cursor.moveToNext();
        }
        cursor.close();
        return st;
    }


    public List<String> getClassByCode() {
        List<String> lst = new ArrayList<>();
        String sqla= "SELECT " +CODE+ " FROM "+CLASSTABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqla, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                lst.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lst;
    }

    public List<mStudents> getStudentsByCode2(String code) {
        db = this.getWritableDatabase();
        List<mStudents> st = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                +STUDENTSTABLE+ " WHERE "+CODE+ " LIKE "+"'" +code+ "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mStudents mStudents = new mStudents();
            mStudents.setId(Integer.parseInt(cursor.getString(0)));
            mStudents.setStudentName(cursor.getString(2));
            mStudents.setStudentBirthday(cursor.getString(3));
            st.add(mStudents);
            cursor.moveToNext();
        }
        cursor.close();
        return st;
    }

    public int delClass(int c) {
        SQLiteDatabase db = this.getWritableDatabase();
        int sql = db.delete(CLASSTABLE,ID+ " = ?",
                new String[]{String.valueOf(c)});
        if(sql == 0){
            return -1;
        }
        return 1;
    }

    public int delStudent(mStudents s) {
        SQLiteDatabase db = this.getWritableDatabase();
        int sql = db.delete(STUDENTSTABLE,ID+ " =?",
                new String[]{String.valueOf(s.getId())});
        if(sql == 0){
            return -1;
        }
        return 1;
    }

    public int updateClass(mClass mClass){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CODE, mClass.getField2());
        values.put(CLASSNAME, mClass.getField3());
//        db.close();
        return db.update(CLASSTABLE, values,
                ID+ " =?",
                new String[]{String.valueOf(mClass.getField1())});
    }



}