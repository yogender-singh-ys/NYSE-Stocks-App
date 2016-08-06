package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.FetchHistoricData;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    public static LineChart lineChart;
    public static ArrayList<Entry> entries = new ArrayList<>();
    public static ArrayList<String> labels = new ArrayList<String>();
    public static ProgressBar progressBar;
    public static Boolean makeCall= true;
    public static Integer days = 7;
    public static String symbol;
    public static TextView dataTV;

    private TextView headingTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle extras = getIntent().getExtras();
        if(extras.getString("symbol") != null )
        {
            symbol = extras.getString("symbol");
        }

        headingTV = (TextView) findViewById(R.id.textView);

        lineChart = (LineChart) findViewById(R.id.chart);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar) ;
        dataTV =(TextView) findViewById(R.id.data);

        LineDataSet dataset = new LineDataSet(entries, getString(R.string.stocks));

        LineData data = new LineData(labels, dataset);
        // set the data and list of lables into chart

        if((savedInstanceState == null || !savedInstanceState.containsKey("entries"))) {
            makeDataCall(0);
        }

        lineChart.setData(data);
        lineChart.setBackgroundColor(getResources().getColor(R.color.white_color));
        lineChart.setDescription(getString(R.string.stocks));

        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.days_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.days_7:
                checkDaysSelected(7);
                return true;
            case R.id.days_15:
                checkDaysSelected(15);
                return true;
            case R.id.days_30:
                checkDaysSelected(30);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }

    }

    private void checkDaysSelected(Integer local_days) {
        if(makeCall) {
            if (days == local_days) {
                Toast.makeText(this, R.string.chart_displayed, Toast.LENGTH_SHORT).show();
            } else {
                days = local_days;
                makeDataCall(days);
            }
        }else{
            Toast.makeText(this, String.format(getString(R.string.loading_chart),days), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if( labels.size() != 0 ) {
            outState.putStringArrayList("entries",labels);
            outState.putInt("days",days);
        }

    }

    protected void makeDataCall(Integer index) {
        if(makeCall) {
            headingTV.setText(String.format(getString(R.string.historic_heading),symbol.toUpperCase(),days));
            new FetchHistoricData(this).execute();
        }
    }
}

