package eu.execom.workshop.execomscheduler.configs;

import eu.execom.workshop.execomscheduler.models.Task;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Sasa on 10/19/2016.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {
            Task.class,
    };

    public static void main(String[] args) throws SQLException, IOException {

        writeConfigFile("ormlite_config.txt", classes);

    }

}
