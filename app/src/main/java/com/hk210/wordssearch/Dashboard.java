package com.hk210.wordssearch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.hk210.wordssearch.Adapter.DataAdapter;
import com.hk210.wordssearch.Adapter.HistoryAdaptor;
import com.hk210.wordssearch.Database.DBHelper;
import com.hk210.wordssearch.Model.DataModel;
import com.hk210.wordssearch.Model.ModelHistory;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    //Strings
    public String BASE_URL = "https://api.datamuse.com/words?";
    public String search_query;
    public String[] array;


    //Lists
    private List<ModelHistory> data;
    private List<DataModel> allword;

    //Database Instance
    private DBHelper dbHelper;

    //adapters
    private HistoryAdaptor adapter;
    private DataAdapter dataAdapter;

    //views
    private TextInputLayout searchQuery;
    private RecyclerView recyclerView, fetchRecyclerView;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        allword = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        dataAdapter = new DataAdapter(this, allword);
        fetchRecyclerView = findViewById(R.id.main_recycler_view);
        fetchRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        fetchRecyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progress = findViewById(R.id.progressbar);
        progress.setVisibility(View.INVISIBLE);

        dbHelper = new DBHelper(this);


        searchQuery = findViewById(R.id.query_search);
        Button meanButton = findViewById(R.id.button_means_search);
        Button soundButton = findViewById(R.id.button_sounds_search);
        Button spellButton = findViewById(R.id.button_spell_search);




        meanButton.setOnClickListener(v -> {
            search_query = searchQuery.getEditText().getText().toString();
            if (!search_query.isEmpty()) {
                progress.setVisibility(View.VISIBLE);
                meanFetch();
                dataAdapter.notifyDataSetChanged();
                historyData();
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(Dashboard.this, "Cannot be empty", Toast.LENGTH_SHORT).show();
            }

        });
        soundButton.setOnClickListener(v -> {
            search_query = searchQuery.getEditText().getText().toString();
            if (!search_query.isEmpty()) {
                progress.setVisibility(View.VISIBLE);
                soundFetch();
                dataAdapter.notifyDataSetChanged();
                historyData();
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(Dashboard.this, "Cannot be empty", Toast.LENGTH_SHORT).show();
            }

        });
        spellButton.setOnClickListener(v -> {
            search_query = searchQuery.getEditText().getText().toString();
            if (!search_query.isEmpty()) {
                progress.setVisibility(View.VISIBLE);
                spellFetch();
                dataAdapter.notifyDataSetChanged();
                historyData();
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(Dashboard.this, "Cannot be empty", Toast.LENGTH_SHORT).show();
            }

        });
        fetchdatabase();
        adapter.notifyDataSetChanged();



    }


    //Volley Request queue to get data and update the list. Afterwords updating the adapter.
    public void meanData(String URL) {
        allword.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject dataObject = response.getJSONObject(i);
                    DataModel dataModel = new DataModel();
                    dataModel.setWord(dataObject.getString("word"));
                    dataModel.setScore(dataObject.getString("score"));
                    allword.add(dataModel);
                    Collections.sort(allword, (o1, o2) -> o1.word.compareTo(o2.word));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            dataAdapter = new DataAdapter(Dashboard.this, allword);
            fetchRecyclerView.setAdapter(dataAdapter);
            dataAdapter.notifyDataSetChanged();
            fetchRecyclerView.smoothScrollToPosition(0);
            fetchdatabase();
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(data.size() - 1);
            progress.setVisibility(View.INVISIBLE);

        }, error -> Log.d("tag", "onErrorResponse: " + error.getMessage()));

        queue.add(jsonArrayRequest);
    }

    public void soundData(String URL) {
        allword.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject dataObject = response.getJSONObject(i);
                    DataModel dataModel = new DataModel();
                    dataModel.setWord(dataObject.getString("word"));
                    dataModel.setScore(dataObject.getString("score"));
                    allword.add(dataModel);
                    Collections.sort(allword, (o1, o2) -> o1.word.compareTo(o2.word));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            dataAdapter = new DataAdapter(Dashboard.this, allword);
            fetchRecyclerView.setAdapter(dataAdapter);
            dataAdapter.notifyDataSetChanged();
            fetchRecyclerView.smoothScrollToPosition(0);
            fetchdatabase();
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(data.size() - 1);
            progress.setVisibility(View.INVISIBLE);
        }, error -> Log.d("tag", "onErrorResponse: " + error.getMessage()));
        queue.add(jsonArrayRequest);
    }

    public void spellData(String URL) {
        allword.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject dataObject = response.getJSONObject(i);

                    DataModel dataModel = new DataModel();
                    dataModel.setWord(dataObject.getString("word"));
                    dataModel.setScore(dataObject.getString("score"));
                    allword.add(dataModel);
                    Collections.sort(allword, (o1, o2) -> o1.word.compareTo(o2.word));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            dataAdapter = new DataAdapter(Dashboard.this, allword);
            fetchRecyclerView.setAdapter(dataAdapter);
            dataAdapter.notifyDataSetChanged();
            fetchRecyclerView.smoothScrollToPosition(0);
            fetchdatabase();
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(data.size() - 1);
            progress.setVisibility(View.INVISIBLE);

        }, error -> Log.d("tag", "onErrorResponse: " + error.getMessage()));

        queue.add(jsonArrayRequest);
    }

    //fetching realtime words data from api
    private void meanFetch() {
        String search = search_query.replace(" ", "+");
        String final_url = BASE_URL + "ml=" + search;
        meanData(final_url);
    }

    private void spellFetch() {
        String search = search_query.replace(" ", "+");
        String final_spell_url = BASE_URL + "sp=" + search;
        spellData(final_spell_url);
    }

    private void soundFetch() {
        String search = search_query.replace(" ", "+");
        String final_sound_url = BASE_URL + "sl=" + search;
        soundData(final_sound_url);
    }

    //Database Working done here.
    private void historyData() {
        dbHelper.insertData(search_query); //inserting data into database
    }

    private void fetchdatabase() {
        data = dbHelper.getAllData(); //fetching data from database to display search history
        String all = data.toString();
        if (all != null) {
            adapter = new HistoryAdaptor(getApplicationContext(), data);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            data.clear();
        }
    }
}