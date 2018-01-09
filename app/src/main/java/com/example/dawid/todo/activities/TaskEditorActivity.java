package com.example.dawid.todo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dawid.task.R;
import com.example.dawid.todo.model.Priority;
import com.example.dawid.todo.model.Task;
import com.example.dawid.todo.repository.TaskRepository;

public class TaskEditorActivity extends AppCompatActivity{

    private TaskRepository taskRepository;
    private EditText title;
    private EditText description;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        taskRepository = new TaskRepository(this);
        task = (Task) getIntent().getSerializableExtra("task");

        title = findViewById(R.id.title_input_update);
        description = findViewById(R.id.description_input_update);

        RadioGroup radioGroup = findViewById(R.id.priority_choose_update);

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {

            View radio = radioGroup.findViewById(i);

            int index = radioGroup.indexOfChild(radio);

            switch (index){
                case 0: this.task.updatePriority(Priority.NONE); break;
                case 1: this.task.updatePriority(Priority.NORMAL); break;
                case 2: this.task.updatePriority(Priority.HIGH); break;
            }

        });

        init();

        findViewById(R.id.updateBtn)
                .setOnClickListener(view -> {
                    taskRepository.update(task);
                    startActivity(new Intent(this, TasksListActivity.class));
                });
    }

    private void init(){
        setActualValues();
        initTextChangedListeners();
    }

    private void setActualValues(){
        title.setText(task.getTitle());
        description.setText(task.getDescription());
    }

    private void initTextChangedListeners(){

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                task.updateDescription(description.getText().toString());
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                task.updateTitle(title.getText().toString());
            }
        });
    }

}
