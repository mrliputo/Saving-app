package io.github.normansyarif.mysaving;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MovePiggyActivity extends AppCompatActivity {

    EditText etMoney;
    TextView tvMoney, tvMoney2;
    DatabaseHelper myDb;
    int curSaving, curSaving2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_piggy);

        etMoney = findViewById(R.id.editText);
        tvMoney = findViewById(R.id.text2);
        tvMoney2 = findViewById(R.id.text4);
        myDb = new DatabaseHelper(this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0099cc")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0083ae"));

        setData();

        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataChanged();
            }
        });
    }

    private void setData() {
        Cursor res = myDb.getFromDb("col_piggy, col_bank");
        if(res.getCount() != 0) {
            while (res.moveToNext()) {
                curSaving = res.getInt(0);
                curSaving2 = res.getInt(1);
                tvMoney.setText(Utils.numberToMoney(curSaving));
                tvMoney2.setText(Utils.numberToMoney(curSaving2));
            }
        }
    }

    private void dataChanged() {
        int opr = 0;

        if(!etMoney.getText().toString().equals("")) {
            opr = Integer.parseInt(etMoney.getText().toString());
        }

        int newValue = curSaving - opr;
        int newValue2 = curSaving2 + opr;

        if(opr <= curSaving) {
            etMoney.setTextColor(Color.parseColor("#ff0099cc"));
            etMoney.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_blue));
            tvMoney.setTextColor(Color.parseColor("#808080"));
        }else{
            etMoney.setTextColor(Color.parseColor("#D81B60"));
            etMoney.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_red));
            tvMoney.setTextColor(Color.parseColor("#D81B60"));
        }

        tvMoney.setText(Utils.numberToMoney(newValue));
        tvMoney2.setText(Utils.numberToMoney(newValue2));
    }

    public void saveData(View v) {
        boolean isUpdate = myDb.updateData("col_piggy", Utils.moneyToNumber(tvMoney.getText().toString()));
        boolean isUpdate2 = myDb.updateData("col_bank", Utils.moneyToNumber(tvMoney2.getText().toString()));
        if(isUpdate && isUpdate2)
            finish();
        else
            Toast.makeText(MovePiggyActivity.this,"Nothing changed.",Toast.LENGTH_LONG).show();
    }

    public void clearFields(View v) {
        etMoney.setText("");
    }

    public void quickBtn(View v) {
        Button b = (Button)v;
        int addValue = Integer.parseInt(b.getText().toString().replace("+ ", ""));
        int curValue = 0;

        if(!etMoney.getText().toString().equals("")) {
            curValue = Integer.parseInt(etMoney.getText().toString());
        }

        etMoney.setText(String.valueOf(addValue + curValue));
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
