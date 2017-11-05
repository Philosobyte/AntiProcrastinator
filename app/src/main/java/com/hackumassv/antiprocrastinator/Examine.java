package com.hackumassv.antiprocrastinator;

import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

public class Examine extends AppCompatActivity {

    //The manager of stats
    public UsageStatsManager statsManager;
    public HorizontalBarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Binary Switch!
        boolean binarySwitch = false;
        //Binary Switch!

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_procrastinator);

        if(!checkForPermission(this)) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }

        //Start test code!
        ProfileList testList = new ProfileList(this);
        testList.processApps();
        testList.sort();

        System.out.println(testList.toString());

        //End test code!

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chart = findViewById(R.id.chart);
        List<BarEntry> entries = new ArrayList<>();
        BarDataSet dataSet;

        if(binarySwitch) {
            for(int i=0; i<testList.size(); i++) {
                if(testList.getProfile(i).timeInMinutes()>10) {
                    entries.add(
                            new BarEntry(
                                    i+1,
                                    (float)testList.getProfile(i).timeInHoursDouble(),
                                    testList.getProfile(i).returnAppName()));
                }
            }
            dataSet = new BarDataSet(entries, "Hours Spent");
        } else {
            for(int i=0; i<testList.size(); i++) {
                if(testList.getProfile(i).timeInMinutes()>10) {
                    entries.add(
                            new BarEntry(
                                    i+1,
                                    (float)testList.getProfile(i).timeInHoursDouble()/testList.getProfile(i).timeEntrySize(),
                                    testList.getProfile(i).returnAppName()));
                }
            }
            dataSet = new BarDataSet(entries, "Average Hours per Session");
        }

        BarData barData = new BarData(dataSet);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return (String)entry.getData();
            }
        });
        chart.setData(barData);

        XAxis xaxis = chart.getXAxis();
        xaxis.setDrawGridLines(false);
        xaxis.setDrawLabels(false);
        xaxis.setDrawAxisLine(false);

        YAxis left = chart.getAxisLeft();
        left.setDrawGridLines(false);
        left.setDrawLabels(false);
        left.setDrawAxisLine(false);

        YAxis right = chart.getAxisRight();
        right.setDrawGridLines(false);
        right.setTextSize(12);

        barData.setValueTextSize(12);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setTextSize(12);
        chart.invalidate();
        chart.setDrawValueAboveBar(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anti_procrastinator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkForPermission(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());
        return mode == MODE_ALLOWED;
    }

}