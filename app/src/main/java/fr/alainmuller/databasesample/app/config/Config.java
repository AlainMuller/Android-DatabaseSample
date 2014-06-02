package fr.alainmuller.databasesample.app.config;

/**
 * Configuration class with all databases constants
 * Created by Alain MULLER on 02/06/2014.
 */
public class Config {

    // The database itself (name and version)
    public static final String DATABASE = "comments.db";
    public static final int DATABASE_VERSION = 1;

    // The comments table (id, comment)
    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_COMMENTS_ID = "id";
    public static final String COLUMN_COMMENTS_COMMENT = "comment";
}

