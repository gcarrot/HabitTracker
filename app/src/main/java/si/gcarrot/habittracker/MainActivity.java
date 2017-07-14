package si.gcarrot.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import si.gcarrot.habittracker.data.ProjectDbHelper;
import si.gcarrot.habittracker.data.ProjectContract.ProjectEntry;

public class MainActivity extends AppCompatActivity {

    private ProjectDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new ProjectDbHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        String[] projection = {
                ProjectEntry._ID,
                ProjectEntry.COLUMN_PROJECT_TITLE,
                ProjectEntry.COLUMN_PROJECT_DESCRIPTION,
                ProjectEntry.COLUMN_PROJECT_COMPANY,
                ProjectEntry.COLUMN_PROJECT_STATUS
        };

        Cursor cursor = db.query(ProjectEntry.TABLE_NAME, projection,
                null, null,
                null, null, null);
        try {
            TextView displayView = (TextView) findViewById(R.id.text_view);
            displayView.setText("There are " + cursor.getCount() + " projects in the " + ProjectEntry.TABLE_NAME + " table.\n\n");

            displayView.append(ProjectEntry._ID + " - " +
                    ProjectEntry.COLUMN_PROJECT_TITLE + " - " +
                    ProjectEntry.COLUMN_PROJECT_COMPANY + " - " +
                    ProjectEntry.COLUMN_PROJECT_DESCRIPTION + " - " +
                    ProjectEntry.COLUMN_PROJECT_STATUS + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ProjectEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(ProjectEntry.COLUMN_PROJECT_TITLE);
            int descriptionColumnIndex = cursor.getColumnIndex(ProjectEntry.COLUMN_PROJECT_DESCRIPTION);
            int companyColumnIndex = cursor.getColumnIndex(ProjectEntry.COLUMN_PROJECT_COMPANY);
            int statusColumnIndex = cursor.getColumnIndex(ProjectEntry.COLUMN_PROJECT_STATUS);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentCompany = cursor.getString(companyColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                int currentStatus = cursor.getInt(statusColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentTitle + " - " +
                        currentCompany + " - " +
                        currentDescription + " - " +
                        currentStatus + "\n"));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            deleteAllData();
            return true;
        }

        if (id == R.id.action_inser_test_data) {
            insertTestData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertTestData(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProjectEntry.COLUMN_PROJECT_TITLE, "Test title");
        values.put(ProjectEntry.COLUMN_PROJECT_COMPANY, "Udacity");
        values.put(ProjectEntry.COLUMN_PROJECT_DESCRIPTION, "Go and make 1 more app to finish course!");
        values.put(ProjectEntry.COLUMN_PROJECT_HOURS, 7.5);
        values.put(ProjectEntry.COLUMN_PROJECT_DUEDATE, "25.7.2017");
        values.put(ProjectEntry.COLUMN_PROJECT_STATUS, ProjectEntry.STATUS_INPROGRESS);

        long newRowId = db.insert(ProjectEntry.TABLE_NAME, null, values);

        Log.i("Test", "New row ID: " + newRowId);
        displayDatabaseInfo();
    }

    private void deleteAllData(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(ProjectEntry.TABLE_NAME, null, null);

        displayDatabaseInfo();
    }
}
