package com.example.test.task;

import android.content.Intent;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.test.task.Adapters.Divider;
import com.example.test.task.Adapters.TaskAdapter;
import com.example.test.task.Model.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    static final int ADD_TASK_REQUEST=1;
    private TaskAdapter taskAdapter;
    TaskStorage taskStorage;
    private int layoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.layout_landscape)!=null){
            layoutId=1;
        }

        BottomAppBar bottomAppBar=(BottomAppBar)findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Divider(this,LinearLayoutManager.VERTICAL));
        loadTasks();

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTasks(){
        taskStorage=TaskStorage.getInstance();
        if(taskStorage.listSize()!=0) {
            taskAdapter = new TaskAdapter(this, taskStorage,layoutId);
            recyclerView.setAdapter(taskAdapter);
        }
    }

    private void addNewTask(){
        Intent addNewTask=new Intent(this,AddNewTask.class);
        startActivityForResult(addNewTask,ADD_TASK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent getNewTask){
        super.onActivityResult(requestCode, resultCode, getNewTask);
        if(requestCode==ADD_TASK_REQUEST) {
            if (resultCode == RESULT_OK) {
                /*String[] newTask=getNewTask.getStringArrayExtra("newTask");
                Task task=new Task(newTask[0],newTask[1],newTask[2]);
                taskStorage.addToList(task);*/
                //taskStorage=getNewTask.getParcelableExtra("newNewTask");
                //taskStorage=TaskStorage.getInstance();
            }
        }
        if(taskAdapter!=null){
            taskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
}
