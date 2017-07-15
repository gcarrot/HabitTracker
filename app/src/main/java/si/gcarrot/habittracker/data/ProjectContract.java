package si.gcarrot.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Urban on 7/14/17.
 */
public final class ProjectContract {

    public static abstract class ProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "projects";

        public static final String COLUMN_PROJECT_TITLE = "title";
        public static final String COLUMN_PROJECT_COMPANY = "company";
        public static final String COLUMN_PROJECT_DESCRIPTION = "description";
        public static final String COLUMN_PROJECT_DUEDATE = "duedate";
        public static final String COLUMN_PROJECT_HOURS = "hours";
        public static final String COLUMN_PROJECT_STATUS = "status";


        public static final int STATUS_DELETED = 2;
        public static final int STATUS_DONE = 1;
        public static final int STATUS_INPROGRESS = 0;
    }
}