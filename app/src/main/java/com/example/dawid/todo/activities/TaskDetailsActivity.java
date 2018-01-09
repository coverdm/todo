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

@SuppressLint("NewApi")
public class TaskDetailsActivity extends AppCompatActivity {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private TaskRepository taskRepository;
    private Task task;
    private TextView title;
    private TextView description;
    private TextView priority_value;
    private TextView created_date;
    private TextView modified_date;
    private TextView status;
    private TextView finished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        init();

        findViewById(R.id.editBtn)
                .setOnClickListener(view -> {
                    Intent intent = new Intent(this, TaskEditorActivity.class);
                    intent.putExtra("task", task);
                    startActivity(intent);
                });

        findViewById(R.id.markAsDoneBtn)
                .setOnClickListener(view -> {
                    task.finishTodo();
                    taskRepository.update(task);
                    startActivity(new Intent(this, TasksListActivity.class));
                });

        findViewById(R.id.removeBtn)
                .setOnClickListener(view -> {
                    taskRepository.delete(task);
                    startActivity(new Intent(this, TasksListActivity.class));
                });
    }

    private void initValues() {
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
    }

    private void init() {
        this.taskRepository = new TaskRepository(this);
        this.title = findViewById(R.id.name);
        this.description = findViewById(R.id.description);
        this.priority_value = findViewById(R.id.priority_value);
        this.created_date = findViewById(R.id.created_date_value);
        this.modified_date = findViewById(R.id.modified_value);
        this.status = findViewById(R.id.status_value);
        this.finished = findViewById(R.id.finished_value);
        this.task = (Task) getIntent().getSerializableExtra("task");
        initValues();
    }
}
