package si.gcarrot.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import si.gcarrot.habittracker.data.ProjectContract.ProjectEntry;

/**
 * Created by Urban on 7/14/17.
 */

public class ProjectDbHelper extends SQLiteOpenHelper  {

    public static final String LOG_TAG = ProjectDbHelper.class.getSimpleName();

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "habit_tracker.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProjectEntry.TABLE_NAME + " (" +
                    ProjectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ProjectEntry.COLUMN_PROJECT_TITLE + " TEXT NOT NULL," +
                    ProjectEntry.COLUMN_PROJECT_COMPANY + " TEXT," +
                    ProjectEntry.COLUMN_PROJECT_DESCRIPTION + " TEXT NOT NULL," +
                    ProjectEntry.COLUMN_PROJECT_DUEDATE + " TEXT," +
                    ProjectEntry.COLUMN_PROJECT_HOURS + " REAL," +
                    ProjectEntry.COLUMN_PROJECT_STATUS + " INTEGER NOT NULL DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProjectEntry.TABLE_NAME;

    private static final String SQL_ALTER_ADD_NEW_COLUMN =
            "ALTER TABLE " + ProjectEntry.TABLE_NAME + " ADD COLUMN new_column INTEGER DEFAULT 0";


    public ProjectDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }



}
