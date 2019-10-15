package io.github.normansyarif.mysaving;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView curSaving, curPiggy, curBank, goalDesc, goalLeft, noGoal;
    ProgressBar goalProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curSaving = findViewById(R.id.cur_saving);
        curPiggy = findViewById(R.id.cur_piggy);
        curBank = findViewById(R.id.cur_bank);
        goalDesc = findViewById(R.id.goal_desc);
        goalLeft = findViewById(R.id.goal_left);
        noGoal = findViewById(R.id.no_goal);
        goalProgress = findViewById(R.id.goal_progress);

        myDb = new DatabaseHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(MainActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void addPiggy(View v) {
        openActivity(AddPiggyActivity.class);
    }

    public void movePiggy(View v) {
        openActivity(MovePiggyActivity.class);
    }

    public void minPiggy(View v) {
        openActivity(MinPiggyActivity.class);
    }

    public void addBank(View v) {
        openActivity(AddBankActivity.class);
    }

    public void minBank(View v) {
        openActivity(MinBankActivity.class);
    }

    public void setGoal(View v) {
        openActivity(GoalActivity.class);
    }


    private void setData() {

        Cursor res = myDb.getFromDb("col_piggy, col_bank, col_goal_desc, col_goal_target");
        if(res.getCount() == 0) {
            myDb.insertData(0, 0, "", 0);
        }

        while (res.moveToNext()) {
            int _curSaving = (res.getInt(0) + res.getInt(1));
            Log.d("miku", "HERE => " + _curSaving);

            curSaving.setText(Utils.numberToMoney(_curSaving));
            curPiggy.setText(Utils.numberToMoney(res.getInt(0)));
            curBank.setText(Utils.numberToMoney(res.getInt(1)));

            if(!res.getString(2).equals("")) {
                goalDesc.setText(res.getString(2));
                if(_curSaving >= res.getInt(3)) {
                    goalLeft.setText("Hooray! you've reached your goal!");
                    goalProgress.setProgress(100);
                }else{
                    int progress = _curSaving * 100 / res.getInt(3);
                    goalLeft.setText(Utils.numberToMoney((res.getInt(3) - _curSaving)) + " left to reach " + Utils.numberToMoney(res.getInt(3)) + " (" + progress + "%)");
                    goalProgress.setProgress(progress);
                }

                noGoal.setVisibility(View.GONE);
                goalDesc.setVisibility(View.VISIBLE);
                goalLeft.setVisibility(View.VISIBLE);
                goalProgress.setVisibility(View.VISIBLE);
            }else{
                noGoal.setVisibility(View.VISIBLE);
                goalDesc.setVisibility(View.GONE);
                goalLeft.setVisibility(View.GONE);
                goalProgress.setVisibility(View.GONE);
            }
        }

    }

}
