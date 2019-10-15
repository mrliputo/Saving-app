package io.github.normansyarif.mysaving;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPiggyActivity extends AppCompatActivity {
    EditText etMoney;
    TextView tvMoney;
    DatabaseHelper myDb;
    int curSaving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_piggy);

        etMoney = findViewById(R.id.editText);
        tvMoney = findViewById(R.id.text2);
        myDb = new DatabaseHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Cursor res = myDb.getFromDb("col_piggy");
        if(res.getCount() != 0) {
            while (res.moveToNext()) {
                curSaving = res.getInt(0);
                tvMoney.setText(Utils.numberToMoney(curSaving));
            }
        }
    }

    private void dataChanged() {
        int opr = 0;

        if(!etMoney.getText().toString().equals("")) {
            opr = Integer.parseInt(etMoney.getText().toString());
        }

        int newValue = curSaving + opr;
        tvMoney.setText(Utils.numberToMoney(newValue));
    }

    public void saveData(View v) {
        boolean isUpdate = myDb.updateData("col_piggy", Utils.moneyToNumber(tvMoney.getText().toString()));
        if(isUpdate)
            finish();
        else
            Toast.makeText(AddPiggyActivity.this,"Nothing changed.",Toast.LENGTH_LONG).show();
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
