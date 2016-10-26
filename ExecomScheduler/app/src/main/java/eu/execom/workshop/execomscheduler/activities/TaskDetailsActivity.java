package eu.execom.workshop.execomscheduler.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import eu.execom.workshop.execomscheduler.R;
import eu.execom.workshop.execomscheduler.models.Task;
import eu.execom.workshop.execomscheduler.util.ExtrasConsts;

@EActivity(R.layout.activity_task_details)
public class TaskDetailsActivity extends AppCompatActivity {

    private static final String TAG = TaskDetailsActivity.class.getSimpleName();

    @Extra(ExtrasConsts.EXTRA_TASK)
    Task task;

    @ViewById(R.id.details_task_title)
    TextView taskTitleTextView;

    @ViewById(R.id.details_task_details)
    TextView taskDetailsTextView;

    @ViewById(R.id.details_task_finished)
    CheckBox taskFinishedCheckBox;

    @ViewById(R.id.details_save_button)
    Button saveButton;

    @ViewById(R.id.details_remove_button)
    Button removeButton;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
            default:
                return true;
        }

    }

    @AfterViews
    void initComponents() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        taskTitleTextView.setText(task.getTitle());
        taskDetailsTextView.setText(task.getDescription());
        taskFinishedCheckBox.setChecked(task.isFinished());
        taskFinishedCheckBox.setClickable(!task.isFinished());

    }

    @Click(R.id.details_save_button)
    void onSaveButtonClick() {

        task.setFinished(taskFinishedCheckBox.isChecked());
        Intent intent = getIntent();
        intent.putExtra(getString(R.string.task_label), task);
        intent.putExtra(getString(R.string.operation_label),
                getString(R.string.save_label));
        setResult(RESULT_OK, intent);
        finish();

    }

    @Click(R.id.details_remove_button)
    void onRemoveButtonClick() {

        Intent intent = getIntent();
        intent.putExtra(getString(R.string.task_label), task);
        intent.putExtra(getString(R.string.operation_label),
                getString(R.string.remove_label));
        setResult(RESULT_OK, intent);
        finish();

    }

}
