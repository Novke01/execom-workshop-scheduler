package eu.execom.workshop.execomscheduler.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import eu.execom.workshop.execomscheduler.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_task)
public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = AddTaskActivity.class.getSimpleName();

    @ViewById(R.id.add_task_button)
    Button addTaskButton;

    @ViewById(R.id.add_task_title)
    EditText editTextTitle;

    @ViewById(R.id.add_task_description)
    EditText editTextDescription;

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

    }

    @Click(R.id.add_task_button)
    void onAddTaskClick() {

        if (editTextTitle.getError() == null) {

            Intent intent = getIntent();
            intent.putExtra(getString(R.string.new_title_label), editTextTitle.getText());
            intent.putExtra(getString(R.string.new_description_label),
                    editTextDescription.getText());

            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @FocusChange(R.id.add_task_title)
    void onTaskTitleFocusChange(boolean hasFocus) {

        if (!hasFocus) {
            validateInput(editTextTitle, 40, getString(R.string.title_error_msg));
        }

    }

    @FocusChange(R.id.add_task_description)
    void onTaskDescriptionFocusChange(boolean hasFocus) {

        if (!hasFocus) {
            validateInput(editTextDescription, 400,
                    getString(R.string.description_error_msg));
        }

    }

    private void validateInput(EditText inputEditText, int maxLen, String errorMsg) {

        if (inputEditText.getText() == null ||
                inputEditText.getText().length() == 0 ||
                inputEditText.getText().length() > maxLen) {
            inputEditText.setError(errorMsg);
        }
        else {
            inputEditText.setError(null);
        }

    }

}
