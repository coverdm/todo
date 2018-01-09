package com.example.dawid.todo.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dawid.todo.R;
import com.example.dawid.todo.model.Priority;
import com.example.dawid.todo.model.Todo;
import com.example.dawid.todo.repository.TodoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {

    private List<Todo> todos = new ArrayList<>();
    TodoRepository todoRepository = new TodoRepository(this);
    CustomAdapter customAdapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);
        populateTodoList();

        Log.i("todos", this.todos.toString());

        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            Todo todo = todos.get(i);
            Intent intent = new Intent(this, TodoDetails.class);
            intent.putExtra("todo", todo);
            startActivity(intent);
        });
    }

    private void populateTodoList() {
        todos.addAll(todoRepository.findAll());
    }

    private void orderByPriorityDesc() {
        Collections.sort(todos, (a, b) -> a.getPriority().compareTo(b.getPriority()));
    }

    private void orderByCreatedTimeDesc() {
        Collections.sort(todos, (a, b) -> a.getCreate().compareTo(b.getCreate()));
        Collections.reverse(todos);
    }

    private void orderByNameDesc() {
        Collections.sort(todos, (a, b) -> a.getTitle().compareTo(b.getTitle()));
    }

    private void orderByStatusDesc() {
        Collections.sort(todos, (a, b) -> a.getStatus().compareTo(b.getStatus()));
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return todos.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.list_item, null);

            ImageView priorityView = view.findViewById(R.id.priority);
            TextView title = view.findViewById(R.id.name);

            if (todos.get(i).getPriority() == Priority.NONE)
                priorityView.setImageResource(android.R.drawable.star_off);
            else if (todos.get(i).getPriority() == Priority.NORMAL)
                priorityView.setImageResource(android.R.drawable.star_big_off);
            else if (todos.get(i).getPriority() == Priority.HIGH)
                priorityView.setImageResource(android.R.drawable.star_big_on);

            title.setText(todos.get(i).getTitle());

            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.createNewItemBtn:
                createNewItem();
                return true;
            case R.id.SortByDate:
                orderByCreatedTimeDesc();
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.SortByPriority:
                orderByPriorityDesc();
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.SortByStatus:
                orderByStatusDesc();
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.SortByName:
                orderByNameDesc();
                customAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewItem() {
        Intent intent = new Intent(this, NewItemActivity.class);
        startActivity(intent);
    }


}
