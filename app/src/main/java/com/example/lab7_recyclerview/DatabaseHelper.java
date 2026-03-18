package com.example.lab7_recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentManager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENT = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STUDENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_EMAIL, student.getEmail());
        long success = db.insert(TABLE_STUDENT, null, values);
        db.close();
        return success;
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))
                );
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_EMAIL, student.getEmail());
        int success = db.update(TABLE_STUDENT, values, KEY_ID + "=?", new String[]{String.valueOf(student.getId())});
        db.close();
        return success;
    }

    public int deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int success = db.delete(TABLE_STUDENT, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return success;
    }
}
