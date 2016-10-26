package eu.execom.workshop.execomscheduler.models;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Sasa on 10/17/2016.
 */

@DatabaseTable(tableName = "tasks")
public class Task implements Serializable {

    @DatabaseField(columnName = "id", generatedId = true)
    private Integer mId;

    @DatabaseField(columnName = "title", canBeNull = false)
    private String mTitle;

    @DatabaseField(columnName = "description", canBeNull = false)
    private String mDescription;

    @DatabaseField(columnName = "finished", canBeNull = false)
    private boolean mFinished;

    public Task() {
        mId = null;
        mTitle = "";
        mDescription = "";
        mFinished = false;
    }

    public Task(String title, String description, boolean finished) {
        mId = null;
        mTitle = title;
        mDescription = description;
        mFinished = finished;
    }

    public Integer getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String taskTitle) {
        mTitle = taskTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public void setFinished(boolean finished) {
        mFinished = finished;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Task)) return false;
        Task otherTask = (Task) other;
        Log.i("Task equals", otherTask.mId + "");
        return mId.equals(otherTask.mId);
    }
}
