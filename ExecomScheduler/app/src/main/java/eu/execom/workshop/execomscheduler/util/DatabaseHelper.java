package eu.execom.workshop.execomscheduler.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import eu.execom.workshop.execomscheduler.R;
import eu.execom.workshop.execomscheduler.models.Task;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.androidannotations.annotations.EBean;

import java.sql.SQLException;

@EBean
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private Dao<Task, Integer> taskDao = null;

    public DatabaseHelper(Context context) {
        super(context, context.getResources().getString(R.string.database_name),
                null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            Log.i(TAG, "onCreate");
            TableUtils.dropTable(connectionSource, Task.class, true);
            TableUtils.createTable(connectionSource, Task.class);
        }
        catch (SQLException e) {
            Log.e(TAG, "Can't create database.");
            throw new RuntimeException();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            Log.i(TAG, "onUpdate");
            TableUtils.dropTable(connectionSource, Task.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop database.");
            throw new RuntimeException();
        }

    }

    public Dao<Task, Integer> getDao() throws SQLException {
        if (taskDao == null) {
            taskDao = getDao(Task.class);
        }
        return taskDao;
    }

    @Override
    public void close() {
        super.close();
        taskDao = null;
    }
}
