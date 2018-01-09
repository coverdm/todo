package com.example.dawid.todo.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dawid.todo.R;
import com.example.dawid.todo.model.Status;
import com.example.dawid.todo.model.Todo;
import com.example.dawid.todo.repository.TodoRepository;

import java.time.format.DateTimeFormatter;

public class TodoDetails extends AppCompatActivity {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private TodoRepository todoRepository;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);
        todoRepository = new TodoRepository(this);
        TextView title = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        TextView priority_value = findViewById(R.id.priority_value);
        TextView created_date = findViewById(R.id.created_date_value);
        TextView modified_date = findViewById(R.id.modified_value);
        TextView status = findViewById(R.id.status_value);
        TextView finished = findViewById(R.id.finished_value);

        Todo todo = (Todo) getIntent().getSerializableExtra("todo");

        title.setText(todo.getTitle());
        description.setText(todo.getDescription());
        priority_value.setText(todo.getPriority().toString());
        created_date.setText(todo.getCreate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));

        if(todo.getModified() == null)
            findViewById(R.id.modified).setVisibility(View.INVISIBLE);
        else
            modified_date.setText(todo.getModified().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));

        if(todo.getFinished() == null)
            findViewById(R.id.finished).setVisibility(View.INVISIBLE);
        else {
            finished.setText(todo.getFinished().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));
            findViewById(R.id.markAsDoneBtn).setVisibility(View.INVISIBLE);
        }

        status.setText(todo.getStatus().toString());

        findViewById(R.id.editBtn)
                .setOnClickListener(view -> {

                    Intent intent = new Intent(this, EditTodo.class);
                    intent.putExtra("todo", todo);
                    startActivity(intent);

                });

        findViewById(R.id.markAsDoneBtn)
                .setOnClickListener(view -> {
                    todo.finishTodo();

                    Log.i("siema", todo.toString());

                    todoRepository.update(todo);
                    startActivity(new Intent(this, MainActivity.class));
                });

        findViewById(R.id.removeBtn)
                .setOnClickListener(view -> {
                    todoRepository.delete(todo.getId().toString());
                    startActivity(new Intent(this, MainActivity.class));
                });
    }
}
