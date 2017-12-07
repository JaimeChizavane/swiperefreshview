package info.androidhive.swiperefresh.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.androidhive.swiperefresh.R;
import info.androidhive.swiperefresh.app.MyApplication;
import info.androidhive.swiperefresh.helper.Grade;
import info.androidhive.swiperefresh.helper.Movie;
import info.androidhive.swiperefresh.helper.SwipeListAdapter;


public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
/*pos*/    private String TAG = MainActivity.class.getSimpleName();

    private String URL_TOP_250 = "http://api.androidhive.info/json/imdb_top_250.php?offset=";

    private String URL_CADEIRAS = "http://sigeup-api.ga/api/pauta/";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    private List<Movie> movieList;

    //Creating the Grade list
    private List<Grade> gradeList;

    //RequestQueue queue = Volley.newRequestQueue(this);



    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;

    //Here we are parsing the Student ID that will be retrived in the future by getText() method
    private String studentID = "X01763";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        movieList = new ArrayList<>();
        gradeList = new ArrayList<>();

        //adapter = new SwipeListAdapter(this, movieList);
        adapter = new SwipeListAdapter(this, gradeList);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchGrades();
                                        fetchGrades2();

                                    }
                                }
        );

    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        fetchGrades();
        fetchGrades2();

    }



    /**
     * Fetching grades json by making http call to the Central HTTP
     */
    private void fetchGrades() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        String url_sigeapi = URL_CADEIRAS + studentID;

        /*// Volley's json array request object
        JsonArrayRequest req = new JsonArrayRequest(url_sigeapi,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {

                            // looping through json and adding to grades list
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject gradeObj = response.getJSONObject(i);

                                    String teste1 = gradeObj.getString("teste1");
                                    String teste2 = gradeObj.getString("teste2");

                                    Grade grade = new Grade(teste1, teste2);

                                    //gradeList.add(0, grade);
                                    gradeList.add(grade);

                                } catch (JSONException e) {
                                    //Log.d(TAG, gradeList.toString());
                                    //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Server Error: " + error.getMessage()+" "+error.getStackTrace());
                *//*Log.e(TAG, "Server Error: " +error.getStackTrace());
                Log.e(TAG, "Server Error: " +error.getLocalizedMessage());*//*
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/


        // Volley's json array request object
        StringRequest req = new StringRequest(Request.Method.GET, url_sigeapi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {

                            // looping through json and adding to grades list
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject gradeObj = new JSONObject(response);

                                    //JSONObject gradeObj = response.getJSONObject(i);

                                    JSONArray castArray = gradeObj.getJSONArray("pauta");

                                    gradeObj = castArray.getJSONObject(i);

                                    String teste1 = gradeObj.getString("teste1");
                                    String teste2 = gradeObj.getString("teste2");

                                    String cadeira1 = "Programacao 1";
                                    //Grade grade = new Grade(teste1, teste2);
                                    Grade grade = new Grade(teste1, cadeira1);

                                    //gradeList.add(0, grade);
                                    gradeList.add(grade);

                                } catch (JSONException e) {

                                    Log.e(TAG, "JSON Parsing error is: " + e.getMessage());
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }






            public void fetchGrades2(){

                String url_sigeapi = URL_CADEIRAS + studentID;

                StringRequest strReq = new StringRequest
                        (Request.Method.GET, url_sigeapi, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {


                                try {

                                    JSONObject gradeObj = new JSONObject(response);

                                    JSONArray castArray = gradeObj.getJSONArray("pauta");

                                    gradeObj = castArray.getJSONObject(0);

                                    String teste1 = gradeObj.getString("teste1");
                                    String teste2 = gradeObj.getString("teste2");

                                    Log.d("Response: ", gradeObj.toString()); //response.toString());
                                    //Log.d("Teste 1: ", teste1);
                                    //Log.d("Teste 2: ", teste2);

                                    System.out.println("Teste 1: "+teste1);
                                    System.out.println("Teste 2: "+teste2);
                                    System.out.println("JSON Object retrived:"+gradeObj.toString());

                                    Grade grade = new Grade(teste1, teste2);

                                    gradeList.add(grade);
                                }
                                catch(JSONException e){
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }


                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.e(TAG, "Server Error: " + error.getMessage());
                            }
                        });

                // Access the RequestQueue through your singleton class.
                MyApplication.getInstance().addToRequestQueue(strReq);


            }


}


