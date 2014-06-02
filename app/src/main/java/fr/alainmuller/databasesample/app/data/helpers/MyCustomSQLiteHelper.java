package fr.alainmuller.databasesample.app.data.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fr.alainmuller.databasesample.app.config.Config;

/**
 * My own SQLite Helper to acces the comments database
 * Created by Alain MULLER on 02/06/2014.
 */
public class MyCustomSQLiteHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_COMMENTS = "CREATE TABLE " + Config.TABLE_COMMENTS + " (" +
            Config.COLUMN_COMMENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Config.COLUMN_COMMENTS_COMMENT + " TEXT NOT NULL);";

    // Override the constructor to send the database name and version.
    public MyCustomSQLiteHelper(Context context) {
        super(context, Config.DATABASE, null, Config.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // We Create the table at first run
        db.execSQL(CREATE_TABLE_COMMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // When DATABASE_VERSION changes, android call this method.

        Log.w(MyCustomSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );

        // Here we just destroy the table and recreate it, but it could (should!) be more complexe
        // to avoid losing data
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_COMMENTS);
        onCreate(db);
    }
}
