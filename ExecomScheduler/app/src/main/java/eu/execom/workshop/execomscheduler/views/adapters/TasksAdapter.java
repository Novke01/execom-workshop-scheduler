package eu.execom.workshop.execomscheduler.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import eu.execom.workshop.execomscheduler.models.Task;
import eu.execom.workshop.execomscheduler.views.viewgroups.TaskItemView;
import eu.execom.workshop.execomscheduler.views.viewgroups.TaskItemView_;

import java.util.ArrayList;
import java.util.List;


@EBean
public class TasksAdapter extends BaseAdapter {

    private static final String TAG = BaseAdapter.class.getSimpleName();

    List<Task> tasks;

    @RootContext
    Context context;

    @Override
    public long getItemId(int position) {
        return tasks.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TaskItemView taskItemView;
        if (convertView == null) {
            taskItemView = TaskItemView_.build(context);
        }
        else {
            taskItemView = (TaskItemView) convertView;
        }
        taskItemView.bind(getItem(position));
        return taskItemView;

    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @AfterInject
    void initAdapter() {

        tasks = new ArrayList<>();

    }

    public void add(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }

    public void remove(Task task) {
        tasks.remove(task);
        notifyDataSetChanged();
    }

    public void update(Task task) {
        int taskIndex = tasks.indexOf(task);
        tasks.get(taskIndex).setFinished(task.isFinished());
        notifyDataSetChanged();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
