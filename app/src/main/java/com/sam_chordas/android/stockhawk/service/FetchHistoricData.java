package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.GraphActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;


public class FetchHistoricData extends AsyncTask <String, String, String> {

    private Context context;

    public FetchHistoricData(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        GraphActivity.progressBar.setVisibility(View.VISIBLE);
        GraphActivity.makeCall = false;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=";
            String endURL = "%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
            String startDate = getDateDiff(-1 * (GraphActivity.days));
            String endDate = getDateDiff(0);

            String queryURL = "select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%20in%20%28%22"+ GraphActivity.symbol+"%22%29%20and%20startDate%20%3D%20%22"+ startDate +"%22%20and%20endDate%20%3D%20%22" + endDate;
            URL url = new URL(baseUrl + queryURL +  endURL);

            URLConnection urlConnection = url.openConnection();
            HttpURLConnection  connection = (HttpURLConnection) urlConnection;;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        }
        catch(Exception e) {
            return null;
        }
    }

    protected void onPostExecute(String response) {

        try
        {
            JSONObject resultData = new JSONObject(response);
            JSONArray rawData = resultData.getJSONObject("query").getJSONObject("results").getJSONArray("quote");

            GraphActivity.entries.clear();
            GraphActivity.labels.clear();

            for (int i = 0; i < rawData.length(); i++) {

                JSONObject c = rawData.getJSONObject(i);

                String date  = c.getString("Date");
                String close = c.getString("Close");

                GraphActivity.entries.add(new Entry( Float.parseFloat(close) , i));
                GraphActivity.labels.add(date);

            }

            LineDataSet dataset = new LineDataSet(GraphActivity.entries, context.getString(R.string.stocks));
            LineData data = new LineData(GraphActivity.labels, dataset);

            GraphActivity.lineChart.setData(data);
            GraphActivity.lineChart.invalidate();
            GraphActivity.lineChart.animate();

            GraphActivity.progressBar.setVisibility(View.INVISIBLE);
            GraphActivity.makeCall = true;

        }catch (JSONException e){
            Log.e("Error", e.getMessage(), e);
        }

    }


    public String getDateDiff(Integer Days)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Days);
        String monthNumber;
        String dayNumber;

        if( (cal.get(Calendar.MONTH) + 1) < 10 )
        {
            monthNumber = "0" + (cal.get(Calendar.MONTH) + 1);
        }
        else{
            monthNumber = "" + (cal.get(Calendar.MONTH) + 1);
        }
        if( (cal.get(Calendar.DAY_OF_MONTH)) < 10 )
        {
            dayNumber = "0" + (cal.get(Calendar.DAY_OF_MONTH));
        }
        else{
            dayNumber = "" + (cal.get(Calendar.DAY_OF_MONTH));
        }

        return cal.get(Calendar.YEAR) + "-" + monthNumber + "-" + dayNumber;
    }
}
