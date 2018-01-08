package com.example.dawid.todo.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dawid.todo.model.Priority;
import com.example.dawid.todo.model.Status;
import com.example.dawid.todo.model.Todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@SuppressLint("NewApi")
public class TodoRepository extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "TodoDataBase4.db";
    public static final String TABLE_NAME = "todo";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_PRIORITY = "PRIORITY";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_CREATED_DATE = "CREATED";
    public static final String COLUMN_FINISHED_DATE = "FINISHED";

    public TodoRepository(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TITLE TEXT," +
                "DESCRIPTION TEXT," +
                "PRIORITY TEXT, " +
                "STATUS TEXT, " +
                "CREATED TEXT, " +
                "FINISHED TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean persist(final Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,todo.getTitle());
        contentValues.put(COLUMN_DESCRIPTION,todo.getDescription());
        contentValues.put(COLUMN_PRIORITY,todo.getPriority().toString());
        contentValues.put(COLUMN_STATUS, todo.getStatus().toString());
        contentValues.put(COLUMN_CREATED_DATE, todo.getCreate().toString());
        contentValues.put(COLUMN_FINISHED_DATE, todo.getEnd().toString());
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Collection<Todo> findAll() {

        Collection<Todo> todos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);

        while(res.moveToNext()){

            String string = res.getString(3);

            Priority priority = Priority.NONE;

            if(string.equals(Priority.NORMAL.toString()))
                priority = Priority.NORMAL;
            else if(string.equals(Priority.HIGH.toString()))
                priority = Priority.HIGH;

            todos.add(new Todo(res.getString(1),
                    res.getString(2),
                    priority,
                    Status.DONE,
                    LocalDate.parse(res.getString(5)),
                    LocalDate.parse(res.getString(6)))
            );
        }

        return todos;
    }

    public boolean update(String id, final Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,id);
        contentValues.put(COLUMN_TITLE,todo.getTitle());
        contentValues.put(COLUMN_DESCRIPTION,todo.getDescription());
        contentValues.put(COLUMN_PRIORITY,todo.getPriority().toString());
        contentValues.put(COLUMN_CREATED_DATE,todo.getCreate().toString());
        contentValues.put(COLUMN_FINISHED_DATE,todo.getEnd().toString());
        db.update(TABLE_NAME, contentValues, "COLUMN_ID = ?",new String[] { id });
        return true;
    }

    public Integer delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public void deleteAll(){
        int size = findAll().size();
        while(size --> 0){
            delete(size + "");
        }
    }
}