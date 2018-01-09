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

import com.example.dawid.task.R;
import com.example.dawid.todo.model.Priority;
import com.example.dawid.todo.model.Task;
import com.example.dawid.todo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressLint("NewApi")
public class TasksListActivity extends AppCompatActivity {

    private List<Task> tasks = new ArrayList<>();
    TaskRepository taskRepository = new TaskRepository(this);
    CustomAdapter customAdapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);
        populateTodoList();

        Log.i("tasks", this.tasks.toString());

        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            Task task = tasks.get(i);
            Intent intent = new Intent(this, TaskDetailsActivity.class);
            intent.putExtra("task", task);
            startActivity(intent);

        });
    }

    private void populateTodoList() {
        tasks.addAll(taskRepository.findAll());
    }

    private void orderByPriorityDesc() {
        Collections.sort(tasks, (a, b) -> a.getPriority().compareTo(b.getPriority()));
    }

    private void orderByCreatedTimeDesc() {
        Collections.sort(tasks, (a, b) -> a.getCreate().compareTo(b.getCreate()));
        Collections.reverse(tasks);
    }

    private void orderByNameDesc() {
        Collections.sort(tasks, (a, b) -> a.getTitle().compareTo(b.getTitle()));
    }

    private void orderByStatusDesc() {
        Collections.sort(tasks, (a, b) -> a.getStatus().compareTo(b.getStatus()));
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
            return tasks.size();
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

            if (tasks.get(i).getPriority() == Priority.NONE)
                priorityView.setImageResource(android.R.drawable.star_off);
            else if (tasks.get(i).getPriority() == Priority.NORMAL)
                priorityView.setImageResource(android.R.drawable.star_big_off);
            else if (tasks.get(i).getPriority() == Priority.HIGH)
                priorityView.setImageResource(android.R.drawable.star_big_on);

            title.setText(tasks.get(i).getTitle());

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
        Intent intent = new Intent(this, TaskCreatorActivity.class);
        startActivity(intent);
    }


}
