package com.example.lab7_recyclerview

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentManager"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENT = "students"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE " + TABLE_STUDENT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        onCreate(db)
    }

    fun addStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, student.name)
        values.put(KEY_EMAIL, student.email)
        val success = db.insert(TABLE_STUDENT, null, values)
        db.close()
        return success
    }

    fun getAllStudents(): List<Student> {
        val studentList = ArrayList<Student>()
        val selectQuery = "SELECT * FROM $TABLE_STUDENT"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val student = Student(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL))
                )
                studentList.add(student)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList
    }

    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, student.name)
        values.put(KEY_EMAIL, student.email)
        val success = db.update(TABLE_STUDENT, values, "$KEY_ID=?", arrayOf(student.id.toString()))
        db.close()
        return success
    }

    fun deleteStudent(id: Int): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_STUDENT, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
        return success
    }
}
