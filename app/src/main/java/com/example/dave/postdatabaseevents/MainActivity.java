package com.example.dave.postdatabaseevents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {

    private String URL_NEW_PREDICTION = "http://10.0.2.2/tech/new_predict.php";
    private Button btnAddPrediction;

    String numOfGoal = "1";
    String numOfCard = "1";
    String diffOfPos = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup goal = (RadioGroup) findViewById(R.id.answer1);

        goal.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                switch (checkedId) {
                    case R.id.answer1A:
                        numOfGoal = "1";
                        break;
                    case R.id.answer1B:
                        numOfGoal = "2";
                        break;
                    case R.id.answer1C:
                        numOfGoal = "3";
                        break;

                }

            }
        });

        RadioGroup card = (RadioGroup) findViewById(R.id.answer2);

        card.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                switch (checkedId) {
                    case R.id.answer2A:
                        numOfCard = "1";
                        break;
                    case R.id.answer2B:
                        numOfCard = "2";
                        break;
                    case R.id.answer2C:
                        numOfCard = "3";
                        break;

                }
            }
        });

        RadioGroup pos = (RadioGroup) findViewById(R.id.answer3);

        pos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.answer3A:
                        diffOfPos = "1";
                        break;
                    case R.id.answer3B:
                        diffOfPos = "2";
                        break;
                    case R.id.answer3C:
                        diffOfPos = "3";
                        break;

                }

            }
        });

        btnAddPrediction = (Button) findViewById(R.id.submit);

        btnAddPrediction.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                new AddNewPrediction().execute(numOfGoal, numOfCard, diffOfPos);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private class AddNewPrediction extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... arg) {
            // TODO Auto-generated method stub
            String goalNo = arg[0];
            String cardNo = arg[1];
            String posDiff = arg[2];

            // Preparing post params

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("input11", goalNo);
            params.put("input22", cardNo);
            params.put("input33", posDiff);

            ServiceHandler serviceClient = new ServiceHandler();

            String json = null;
            // Android app running on emulator must use 10.0.2.2 (localhost doesn't work
            // json = serviceClient.performPostCall("http://10.0.2.2/football.php", params);
            json = serviceClient.performPostCall("http://10.0.0.16/football.php", params);

            Log.d("Prediction Request: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {
                        // new category created successfully
                        Log.e("Prediction added successfully ",
                                "> " + jsonObj.getString("message"));
                    } else {
                        Log.e("Add Prediction Error: ",
                                "> " + jsonObj.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "JSON data error!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}