package fr.alainmuller.databasesample.app.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.alainmuller.databasesample.app.config.Config;
import fr.alainmuller.databasesample.app.data.helpers.MyCustomSQLiteHelper;
import fr.alainmuller.databasesample.app.data.model.Comment;

/**
 * The Comments DAO : it maintains the database connection and supports adding new comments
 * and fetching all comments
 * Created by Alain MULLER on 02/06/2014.
 */
public class CommentsDAO {

    /// database fields
    private SQLiteDatabase database;
    private MyCustomSQLiteHelper dbHelper;
    private String[] allColumns = {Config.COLUMN_COMMENTS_ID, Config.COLUMN_COMMENTS_COMMENT};

    public CommentsDAO(Context context) {
        dbHelper = new MyCustomSQLiteHelper(context);
    }

    public void openRW() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void openR() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        Comment result = null;
        // use ContentValues to insert data
        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_COMMENTS_COMMENT, comment);

        // insert the values in the table
        long commentId = database.insert(Config.TABLE_COMMENTS, null, contentValues);

        // retrieve the comment newly inserted
        Cursor cursor = database.query(Config.TABLE_COMMENTS, allColumns, Config.COLUMN_COMMENTS_ID + " = " + commentId, null, null, null, null);
        // just one row, we use the primary key
        cursor.moveToFirst();

        result = cursorToComment(cursor);
        // Always close the cursor
        cursor.close();

        return result;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        database.delete(Config.TABLE_COMMENTS, Config.COLUMN_COMMENTS_ID + " = " + id, null);
        System.out.println("Comment [id:" + id + "] deleted.");
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(Config.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    /**
     * Private method to return a Comment from its database value via a cursor
     *
     * @param cursor the value read from the database
     * @return Comment the data object
     */
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setText(cursor.getString(1));
        return comment;
    }
}
