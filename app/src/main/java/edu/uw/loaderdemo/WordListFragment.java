package edu.uw.loaderdemo;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.annotation.FractionRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple Fragment to display a list of words.
 */
public class WordListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "WordList";

    private SimpleCursorAdapter adapter;

    public WordListFragment() {
        // Required empty public constructor
    }

    //A factory method to create a new fragment with some arguments
    public static WordListFragment newInstance() {
        WordListFragment fragment = new WordListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    // Custom dictionary
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_word_list, container, false);

        // Uniform Resource identifier
        //Log.v(TAG, UserDictionary.Words.CONTENT_URI);
        // Projection is a string array of all the names of columns I want to select
        // _ID is primary key of table
        // Basically the query method on the resolver has analogs to a SELECT *
        // FROM * WHERE * ORDER BY
        // Returned is a cursor, which is like an iterator
        //String[] projection = {UserDictionary.Words.WORD, UserDictionary.Words._ID};
        //Cursor cursor = getActivity().getContentResolver().query(
        //        UserDictionary.Words.CONTENT_URI, projection, null, null, null);
        //cursor.moveToFirst();
        //cursor.getString(0); // zero-th column
        //Log.v(TAG, cursor.getString(0));
        // Get me the column index in word - will return 0 because that is where I set it.
        //cursor.getColumnIndexOrThrow(UserDictionary.Words.WORD); // For use without a projection?


        //model
        //String[] data = {"Dog","Cat","Android","Inconceivable"};

        //controller
        AdapterView listView = (AdapterView)rootView.findViewById(R.id.wordListView);
        int[] views = {}; // Which text views do you want to take each column from my cursor, and what
        // mapping do we want to perform there.
        adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_layout,
                null, // null or cursor
                new String[] {UserDictionary.Words.WORD},
                new int[] {R.id.txtListItem},
                0);

        // Which loader we care about, bundle of arguments, and someone who listenes to loader callbacks (this guy)
        getLoaderManager().initLoader(0, null, this);
        listView.setAdapter(adapter);

        // Loader is wrapper for async task (DB read in this case) but can do it over and over
        // like when the DB changes.

        //handle button input
        final TextView inputText = (TextView)rootView.findViewById(R.id.txtAddWord);
        Button addButton = (Button)rootView.findViewById(R.id.btnAddWord);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputWord = inputText.getText().toString();
                Log.v(TAG, "To add: "+inputWord);

                ContentValues values = new ContentValues(); //only for databases // Alot like a bundle, key value pairs
                values.put(UserDictionary.Words.WORD, inputWord);
                values.put(UserDictionary.Words.APP_ID, "edu.uw.loaderdemo");
                values.put(UserDictionary.Words.LOCALE, "en_US");
                values.put(UserDictionary.Words.FREQUENCY, 100); // Default frequency

                // Content reoslver interacts with db
                getActivity().getContentResolver().insert(UserDictionary.Words.CONTENT_URI, values);
            }
        });

        return rootView;
    }

    // Whenever loader that is watching DB is created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {UserDictionary.Words.WORD, UserDictionary.Words._ID};
        CursorLoader cl = new CursorLoader(
                getActivity(),
                UserDictionary.Words.CONTENT_URI, // Where does DB exist
                projection, // Same project we used above
                null, null, null // Nulls
        );

        return cl;
    }

    // onPostExecute, what happens when we finish querying the database
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data); // Swap out one set of data for another
    }

    // Restarts adapter with no data.
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
