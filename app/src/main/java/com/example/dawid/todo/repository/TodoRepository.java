package com.example.dawid.todo.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dawid.todo.model.Priority;
import com.example.dawid.todo.model.Status;
import com.example.dawid.todo.model.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@SuppressLint("NewApi")
public class TodoRepository extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "TodoDataBase7.db";
    public static final String TABLE_NAME = "todo";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_PRIORITY = "PRIORITY";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_CREATED_DATE = "CREATED";
    public static final String COLUMN_FINISHED_DATE = "FINISHED";
    public static final String COLUMN_MODIFIED_DATE = "MODIFIED";

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
                "FINISHED TEXT, " +
                "MODIFIED TEXT" +
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

        if(todo.getFinished() != null)
            contentValues.put(COLUMN_FINISHED_DATE, todo.getFinished().toString());

        if(todo.getModified() != null)
            contentValues.put(COLUMN_MODIFIED_DATE, todo.getModified().toString());

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Collection<Todo> findAll() {

        Collection<Todo> todos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor resource = db.rawQuery("select * from "+TABLE_NAME,null);

        while(resource.moveToNext()){

            String string = resource.getString(3);

            Priority priority = Priority.NONE;

            if(string.equals(Priority.NORMAL.toString()))
                priority = Priority.NORMAL;
            else if(string.equals(Priority.HIGH.toString()))
                priority = Priority.HIGH;

            Status status = Status.TODO;

            if(resource.getString(4).equals(Status.DONE.toString()))
                status = Status.DONE;

            LocalDateTime finished = null;
            LocalDateTime modified = null;

            if(resource.getString(6) != null)
                 finished = LocalDateTime.parse(resource.getString(6));

            if(resource.getString(7) != null)
                 modified = LocalDateTime.parse(resource.getString(7));

            Todo todo = new Todo(
                    Integer.parseInt(resource.getString(0)),
                    resource.getString(1),
                    resource.getString(2),
                    priority,
                    status,
                    LocalDateTime.parse(resource.getString(5)),
                    finished,
                    modified
            );

            Log.e("todorepo", todo.toString());

            todos.add(todo);
        }

        return todos;
    }

    public boolean update(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,todo.getId().toString());
        contentValues.put(COLUMN_TITLE,todo.getTitle());
        contentValues.put(COLUMN_DESCRIPTION,todo.getDescription());
        contentValues.put(COLUMN_PRIORITY,todo.getPriority().toString());
        contentValues.put(COLUMN_STATUS,todo.getStatus().toString());

        if(todo.getFinished() != null)
            contentValues.put(COLUMN_FINISHED_DATE, todo.getFinished().toString());

        if(todo.getModified() != null)
            contentValues.put(COLUMN_MODIFIED_DATE, todo.getModified().toString());

        Log.i("todo_update", todo.toString());

        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { todo.getId().toString() });
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