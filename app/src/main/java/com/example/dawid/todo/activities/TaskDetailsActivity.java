package com.example.dawid.todo.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dawid.task.R;
import com.example.dawid.todo.model.Task;
import com.example.dawid.todo.repository.TaskRepository;

import java.time.format.DateTimeFormatter;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private TaskRepository taskRepository;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);
        taskRepository = new TaskRepository(this);
        TextView title = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        TextView priority_value = findViewById(R.id.priority_value);
        TextView created_date = findViewById(R.id.created_date_value);
        TextView modified_date = findViewById(R.id.modified_value);
        TextView status = findViewById(R.id.status_value);
        TextView finished = findViewById(R.id.finished_value);

        Task task = (Task) getIntent().getSerializableExtra("task");

        title.setText(task.getTitle());
        description.setText(task.getDescription());
        priority_value.setText(task.getPriority().toString());
        created_date.setText(task.getCreate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));

        if(task.getModified() == null)
            findViewById(R.id.modified).setVisibility(View.INVISIBLE);
        else
            modified_date.setText(task.getModified().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));

        if(task.getFinished() == null)
            findViewById(R.id.finished).setVisibility(View.INVISIBLE);
        else {
            finished.setText(task.getFinished().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));
            findViewById(R.id.markAsDoneBtn).setVisibility(View.INVISIBLE);
        }

        status.setText(task.getStatus().toString());

        findViewById(R.id.editBtn)
                .setOnClickListener(view -> {

                    Intent intent = new Intent(this, TaskEditorActivity.class);
                    intent.putExtra("task", task);
                    startActivity(intent);

                });

        findViewById(R.id.markAsDoneBtn)
                .setOnClickListener(view -> {
                    task.finishTodo();

                    Log.i("siema", task.toString());

                    taskRepository.update(task);
                    startActivity(new Intent(this, TasksListActivity.class));
                });

        findViewById(R.id.removeBtn)
                .setOnClickListener(view -> {
                    taskRepository.delete(task.getId().toString());
                    startActivity(new Intent(this, TasksListActivity.class));
                });
    }
}
