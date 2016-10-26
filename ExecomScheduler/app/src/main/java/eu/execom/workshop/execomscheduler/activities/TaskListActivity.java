package eu.execom.workshop.execomscheduler.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import eu.execom.workshop.execomscheduler.util.DatabaseHelper;
import eu.execom.workshop.execomscheduler.R;
import eu.execom.workshop.execomscheduler.models.Task;
import eu.execom.workshop.execomscheduler.views.adapters.TasksAdapter;
import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.List;

@EActivity(R.layout.activity_task_list)
public class TaskListActivity extends AppCompatActivity {

    private static final String TAG = TaskListActivity.class.getSimpleName();
    private static final int ADD_TASK = 1;
    private static final int TASK_DETAILS = 2;

    @ViewById(R.id.task_list)
    ListView taskList;

    @ViewById(R.id.add_task)
    FloatingActionButton addTaskButton;

    @Bean
    TasksAdapter tasksAdapter;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Task, Integer> taskDao;

    private Intent mAddTaskIntent;
    private Intent mTaskDetailsIntent;

    @AfterViews
    void setAllElements() {
        setElements();
        setListAdapter();
        setScrollListener();
    }

    @OnActivityResult(ADD_TASK)
    void onAddTaskResult(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            String newTaskTitle =
                    data.getExtras().get(getString(R.string.new_title_label)).toString();
            String newTaskDescription =
                    data.getExtras().get(getString(R.string.new_description_label)).toString();
            Task newTask = new Task(newTaskTitle, newTaskDescription, false);
            addTask(newTask);
        } else {
            super.onActivityResult(ADD_TASK, resultCode, data);
        }

    }

    @OnActivityResult(TASK_DETAILS)
    void onDetailsResult(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            String operation =
                    data.getExtras().get(getString(R.string.operation_label)).toString();

            if (operation.equals(getString(R.string.save_label))) {
                Task task =
                        (Task) data.getExtras().get(getString(R.string.task_label));
                saveTask(task);
            } else if (operation.equals(getString(R.string.remove_label))) {
                Task task =
                        (Task) data.getExtras().get(getString(R.string.task_label));
                deleteTask(task);
            }

        } else {
            super.onActivityResult(TASK_DETAILS, resultCode, data);
        }

    }

    @ItemClick(R.id.task_list)
    void taskListItemClick(Task task) {

        mTaskDetailsIntent.putExtra(getString(R.string.task_label), task);
        startActivityForResult(mTaskDetailsIntent, TASK_DETAILS);

    }

    @Click(R.id.add_task)
    void startAddTaskActivity() {
        startActivityForResult(mAddTaskIntent, ADD_TASK);
    }

    private void addTask(Task newTask) {

        try {

            taskDao.create(newTask);
            tasksAdapter.add(newTask);

        }
        catch (SQLException e) {
            Log.e(TAG, "Can't get task dao object.");
        }

    }

    private void setElements() {

        mAddTaskIntent = new Intent(this, AddTaskActivity_.class);
        mTaskDetailsIntent = new Intent(this, TaskDetailsActivity_.class);

    }

    private void setListAdapter() {

        try {
            final List<Task> tasks = taskDao.queryForAll();
            tasksAdapter.setTasks(tasks);
            taskList.setAdapter(tasksAdapter);
        }
        catch (SQLException e) {
            Log.e(TAG, "Can't get task dao object.");
        }

    }

    private void setScrollListener() {

        taskList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    addTaskButton.show();
                }
                else {
                    if (addTaskButton.isShown()) {
                        addTaskButton.hide();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

        });

    }

    private void saveTask(Task task) {

        try {

            taskDao.update(task);
            tasksAdapter.update(task);

        }
        catch (SQLException e) {
            Log.e(TAG, "Can't save task.");
        }

    }

    private void deleteTask(Task task) {

        try {

            taskDao.delete(task);
            tasksAdapter.remove(task);

        }
        catch (SQLException e) {
            Log.e(TAG, "Can't remove task.");
        }

    }

}
