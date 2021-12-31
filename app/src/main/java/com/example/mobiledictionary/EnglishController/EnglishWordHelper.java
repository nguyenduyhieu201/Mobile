package com.example.mobiledictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class EnglishWordHelper extends SQLiteOpenHelper {
    public EnglishWordHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor GetData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery(sql, null);
    }

    public Cursor SearchWord (String word, String tableName) {
        return GetData("Select * from " + tableName + " where EngWord = '" + word + "'");
    }

    public void HighlightWord (int id, String tableName) {
        QueryData("Update '" + tableName + "' SET 'Highlight' = 1 where Id = '"
                + id + "'");
    }

    public void UnHighlightWord (int id, String tableName) {
        QueryData("Update '" + tableName + "' SET 'Highlight' = 0 where Id = '"
                + id + "'");
    }
    public int getHighlightWord (int id, String tableName) {
        Cursor HighlightWord = GetData("Select * from " + tableName + " where Id = " + id);
        HighlightWord.moveToNext();
        int highlightValue = HighlightWord.getInt(3);
        return highlightValue;
    }

    public void NoteWord (String note, int id, String tableName) {
        QueryData("Update '" + tableName + "' SET Note = '" + note + "' where Id = '"
                + id + "'");
    }

    public String getNoteWord (int id, String tableName) {
        Cursor Note = GetData("Select * from " + tableName + " where Id = " + id);
        Note.moveToNext();
        String noteValue = Note.getString(4);
        if (noteValue == null) {
            return "";
        }
        return noteValue;
    }

    public void CreateData (String tableName) {
        QueryData("CREATE TABLE IF NOT " +
                "EXISTS " + tableName + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, EngWord VARCHAR(200)," +
                "Meaning VARCHAR(200), Highlight INTEGER, Note VARCHAR(200) DEFAULT null)");
    }

    public void InsertData (String tableName, String engWord, String meaning) {
        QueryData("INSERT INTO " + tableName + " Values (null, '" + engWord + "', '" + meaning
        + "',0,null)");
    }

    /*public String[] getAllWord() {
        String  wordsList[] ;
        String query = "SELECT * FROM" + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
