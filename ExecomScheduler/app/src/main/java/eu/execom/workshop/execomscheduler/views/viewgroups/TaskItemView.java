package eu.execom.workshop.execomscheduler.views.viewgroups;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.workshop.execomscheduler.R;
import eu.execom.workshop.execomscheduler.models.Task;

@EViewGroup(R.layout.task_item_layout)
public class TaskItemView extends LinearLayout {

    @ViewById(R.id.task_title)
    TextView taskTitleView;

    @ViewById(R.id.task_item_checkbox)
    ImageView taskFinishedView;

    public TaskItemView(Context context) {
        super(context);
    }

    public void bind(Task task) {
        taskTitleView.setText(task.getTitle());
        if (task.isFinished())
            taskFinishedView.setVisibility(View.VISIBLE);
        else
            taskFinishedView.setVisibility(View.GONE);
    }

}
