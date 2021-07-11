package com.hk210.wordssearch.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.hk210.wordssearch.Model.ModelHistory;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table history( search TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists history");
    }

    public Boolean insertData(String search_made) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("search", search_made);
        long result = db.insert("history", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<ModelHistory> getAllData() {
        ArrayList<ModelHistory> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM history", null);
        if (cursor.moveToNext()) {
            do {
                ModelHistory data = new ModelHistory("" + cursor.getString(cursor.getColumnIndex("search")));
                arrayList.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}
