package io.github.normansyarif.mysaving;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_saving";
    private static final String TABLE_NAME = "tb_saving";
    private static final String COL_ID = "col_id";
    private static final String COL_PIGGY = "col_piggy";
    private static final String COL_BANK = "col_bank";
    private static final String COL_GOAL_DESC = "col_goal_desc";
    private static final String COL_GOAL_TARGET = "col_goal_target";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (" + COL_ID + " INTEGER PRIMARY KEY,"+ COL_PIGGY +" INTEGER," + COL_BANK + " INTEGER," + COL_GOAL_DESC + " TEXT, " + COL_GOAL_TARGET + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    boolean insertData(int piggy, int bank, String goalDesc, int goalTarget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, 1);
        contentValues.put(COL_PIGGY, piggy);
        contentValues.put(COL_BANK, bank);
        contentValues.put(COL_GOAL_DESC, goalDesc);
        contentValues.put(COL_GOAL_TARGET, goalTarget);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        return result != -1;
    }


    Cursor getFromDb(String columns) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select " + columns + " from "+TABLE_NAME + " where col_id = 1",null);
    }

    boolean updateGoalData(String _goalDesc, int _goalTarget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("col_goal_desc", _goalDesc);
        contentValues.put("col_goal_target", _goalTarget);
        db.update(TABLE_NAME, contentValues, "col_id = ?",new String[] { "1" });
        return true;
    }

    boolean updateData(String colName, int _newValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colName, _newValue);
        db.update(TABLE_NAME, contentValues, "col_id = ?",new String[] { "1" });
        return true;
    }


//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
//    }
}