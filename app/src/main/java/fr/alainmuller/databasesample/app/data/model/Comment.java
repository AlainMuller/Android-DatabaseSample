package fr.alainmuller.databasesample.app.data.model;

/**
 * The class representing a comment
 * Created by Alain MULLER on 02/06/2014.
 */
public class Comment {
    private long id;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // We use toString to display the text in the ListView (SimpleAdapter)
    @Override
    public String toString() {
        return id + " - " + text;
    }
}
