package io.github.normansyarif.mysaving;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GoalActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText goalDesc, goalTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        goalDesc = findViewById(R.id.goal_desc);
        goalTarget = findViewById(R.id.goal_target);

        myDb = new DatabaseHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setData();
    }

    private void setData() {
        Cursor res = myDb.getFromDb("col_goal_desc, col_goal_target");
        if(res.getCount() != 0) {
            while (res.moveToNext()) {
                goalDesc.setText(res.getString(0));
                if(res.getInt(1) == 0) {
                    goalTarget.setText("");
                }else{
                    goalTarget.setText(String.valueOf(res.getInt(1)));
                }
            }
        }
    }

    public void saveData(View v) {
        String _desc = goalDesc.getText().toString();
        int _target = 0;

        if(!goalTarget.getText().toString().equals("")) {
            _target = Integer.parseInt(goalTarget.getText().toString());
        }

        boolean isUpdate = myDb.updateGoalData(_desc, _target);
        if(isUpdate)
            finish();
        else
            Toast.makeText(GoalActivity.this,"Nothing changed.",Toast.LENGTH_LONG).show();
    }

    public void clearFields(View v) {
        goalDesc.setText("");
        goalTarget.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
