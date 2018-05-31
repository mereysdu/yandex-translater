//создаем ДБ и таблицу
package com.example.windows.translate.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wordBase.db";
    private static final int VERSION = 1;
    public WordBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + WordDbSchema.WordTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                WordDbSchema.WordTable.Cols.WORD + ", " +
                WordDbSchema.WordTable.Cols.FAVORITES +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
