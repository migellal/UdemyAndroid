package com.udemy.michalgellert.udemyandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.udemy.michalgellert.udemyandroid.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApiActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.htmlDataButton)
    Button htmlDataButton;
    @BindView(R.id.resultTextView)
    TextView resultTextView;
    @BindView(R.id.jsonDataButton)
    Button jsonDataButton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);
        requestQueue = Volley.newRequestQueue(this);
        htmlDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.google.pl/",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                resultTextView.setText(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                resultTextView.setText("!!!");
                            }
                        });
                requestQueue.add(stringRequest);
            }
        });
        final String jsonUrl = "https://jsonplaceholder.typicode.com/users/";
        final List<User> users = new ArrayList<>();
        jsonDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonArrayRequest jar = new JsonArrayRequest(jsonUrl,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject user = (JSONObject) response.get(i);
                                        Gson gson = new Gson();
                                        User u = gson.fromJson(user.toString(), User.class);
                                        users.add(u);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                resultTextView.setText(users.get(0).getName());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                resultTextView.setText("!!!");
                            }
                        });
                requestQueue.add(jar);
            }
        });
    }

    private class ThreadClass extends AsyncTask<String, Integer, Float> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);
            // textView.setText("" + System.currentTimeMillis());
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected Float doInBackground(String... params) {
            String url = params[0];
            String a = url;
            for (int i = 0; i < 100000; i++) {
                a = a + url;
                if (i > 0 && i % 1000 == 0)
                    publishProgress(i / 1000);
            }
            return 0.0F;
        }

        @Override
        protected void onPostExecute(Float f) {
            progressBar.setVisibility(View.INVISIBLE);
            // textView.setText(textView.getText() + "   " + "" + System.currentTimeMillis());
            super.onPostExecute(f);
        }

        @Override
        protected void onCancelled(Float f) {
            super.onCancelled(f);
        }
    }
}
