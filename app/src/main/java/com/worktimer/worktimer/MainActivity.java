package com.worktimer.worktimer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private AlertDialog mDialog = null;
    public AppDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new AppDatabase(getBaseContext());

/*
        String[] projection = {WorkTimer.Columns._ID, WorkTimer.Columns.TASK_NAME, WorkTimer.Columns.TASK_DESCRIPTION, WorkTimer.Columns.TASKS_SORTORDER};
        String sortOrder = WorkTimer.Columns.TASKS_SORTORDER + "," + WorkTimer.Columns.TASK_NAME + "COLLATE NOCASE";

        new CursorLoader(getActivity(),
                WorkTimer.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder);
*/


        final Button button = findViewById(R.id.button_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext(), Main2Activity.class);
                i.putExtra("PersonID", 12);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nex_window:
                startActivity(new Intent(this, Main2Activity.class));
                break;
            case R.id.about:
                showAboutDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAboutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);


        builder.setTitle("Work Timer");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setView(messageView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Log.d(TAG, "onClick: Entering messageView.onClick, showing = " + mDialog.isShowing());
                if(mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });


        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);

        mDialog.show();


    }

}
