package com.example.dawid.todo.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dawid.task.R;
import com.example.dawid.todo.model.Priority;
import com.example.dawid.todo.model.Status;
import com.example.dawid.todo.model.Task;
import com.example.dawid.todo.repository.TaskRepository;

import java.time.LocalDateTime;

public class TaskCreatorActivity extends AppCompatActivity {

    private Priority priority = Priority.NORMAL;
    TaskRepository taskRepository = new TaskRepository(this);

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        Button createBtn = findViewById(R.id.createBtn);
        EditText titleInput = findViewById(R.id.title_input);
        EditText descriptionInput = findViewById(R.id.description_input);
        RadioGroup radioGroup = findViewById(R.id.priority_choose);

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {

            View radio = radioGroup.findViewById(i);

            int index = radioGroup.indexOfChild(radio);

            switch (index){
                case 0: this.priority = Priority.NONE; break;
                case 1: this.priority = Priority.NORMAL; break;
                case 2: this.priority = Priority.HIGH; break;
            }

        });

        createBtn.setOnClickListener(view -> {

            Intent intent = new Intent(this, TasksListActivity.class);
            taskRepository.persist(new Task(titleInput.getText().toString(),
                    descriptionInput.getText().toString(),
                    this.priority, Status.TODO, LocalDateTime.now())
            );
            startActivity(intent);
        });

    }
}
