package fr.alainmuller.databasesample.app.ui.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import fr.alainmuller.databasesample.app.R;
import fr.alainmuller.databasesample.app.data.dao.CommentsDAO;
import fr.alainmuller.databasesample.app.data.model.Comment;

public class MainActivity extends ListActivity {

    private CommentsDAO dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new CommentsDAO(this);
        openCnxRW();

        List<Comment> values = dataSource.getAllComments();

        // use the ArrayAdapter to show the elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    private void openCnxRW() {
        try {
            dataSource.openRW();
        } catch (SQLException e) {
            Log.e(MainActivity.class.getSimpleName(), "Erreur SQL à l'ouverture de la connexion : " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        openCnxRW();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    public void addNew(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        String[] comments = new String[]{"Cool", "Very nice", "Hate it"};
        int nextInt = new Random().nextInt(3);
        // save the new comment to the database
        comment = dataSource.createComment(comments[nextInt]);
        adapter.add(comment);
        // Notify the adapter to refresh the layout
        adapter.notifyDataSetChanged();
    }

    public void deleteFirst(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        if (getListAdapter().getCount() > 0) {
            comment = (Comment) getListAdapter().getItem(0);
            dataSource.deleteComment(comment);
            adapter.remove(comment);
        }
        // Notify the adapter to refresh the layout
        adapter.notifyDataSetChanged();
    }

    public void deleteLast(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        if (getListAdapter().getCount() > 0) {
            comment = (Comment) getListAdapter().getItem(getListAdapter().getCount() - 1);
            dataSource.deleteComment(comment);
            adapter.remove(comment);
        }
        // Notify the adapter to refresh the layout
        adapter.notifyDataSetChanged();
    }
}
