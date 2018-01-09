package com.example.dawid.todo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dawid.todo.R;
import com.example.dawid.todo.model.Priority;
import com.example.dawid.todo.model.Todo;
import com.example.dawid.todo.repository.TodoRepository;

public class EditTodo extends AppCompatActivity{

    private TodoRepository todoRepository;
    private EditText title;
    private EditText description;
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        todoRepository = new TodoRepository(this);
        todo = (Todo) getIntent().getSerializableExtra("todo");

        title = findViewById(R.id.title_input_update);
        description = findViewById(R.id.description_input_update);

        RadioGroup radioGroup = findViewById(R.id.priority_choose_update);

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {

            View radio = radioGroup.findViewById(i);

            int index = radioGroup.indexOfChild(radio);

            switch (index){
                case 0: this.todo.updatePriority(Priority.NONE); break;
                case 1: this.todo.updatePriority(Priority.NORMAL); break;
                case 2: this.todo.updatePriority(Priority.HIGH); break;
            }

        });

        init();

        findViewById(R.id.updateBtn)
                .setOnClickListener(view -> {
                    todoRepository.update(todo);
                    startActivity(new Intent(this, MainActivity.class));
                });
    }

    private void init(){
        setActualValues();
        initTextChangedListeners();
    }

    private void setActualValues(){
        title.setText(todo.getTitle());
        description.setText(todo.getDescription());
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
                todo.updateDescription(description.getText().toString());
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
                todo.updateTitle(title.getText().toString());
            }
        });
    }

}
