package com.example.lab3_3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "qlsv.db";
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DB_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không cần thực hiện gì trong phương thức này vì chúng ta đã có cơ sở dữ liệu sẵn có.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý các cập nhật cơ sở dữ liệu khi cần thiết
    }

    public void copyDatabaseFromAssets() {
        if (!checkDatabaseExists()) {
            try {
                InputStream inputStream = context.getAssets().open(DATABASE_NAME);
                OutputStream outputStream = new FileOutputStream(DB_PATH);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Error copying database from assets: " + e.getMessage());
            }
        }
    }

    private boolean checkDatabaseExists() {
        File dbFile = new File(DB_PATH);
        return dbFile.exists();
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM qlsv", null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(
                        cursor.getString(0), // MSSV
                        cursor.getString(1), // HoTen
                        cursor.getString(2)  // Lop
                );
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO qlsv (MSSV, HoTen, Lop) VALUES (?, ?, ?)";
        db.execSQL(sql, new String[]{student.getMssv(), student.getName(), student.getClassName()});
        db.close();
    }

    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "UPDATE qlsv SET HoTen = ?, Lop = ? WHERE MSSV = ?";
            db.execSQL(sql, new String[]{student.getName(), student.getClassName(), student.getMssv()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error updating student: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "DELETE FROM qlsv WHERE MSSV = ?";
            db.execSQL(sql, new String[]{student.getMssv()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error deleting student: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
    }}
