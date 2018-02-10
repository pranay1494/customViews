package com.example.pranay.circularimage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CustomProgressBar customProgress;
    private CustomSeekbar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customProgress = findViewById(R.id.customProgress);
        seekbar = findViewById(R.id.seekbar);

        List<String> header = new ArrayList<>();
        header.add("hello");
        header.add("hi");
        header.add("bye");
        header.add("ssup");


        List<Double> percent = new ArrayList<>();
        percent.add(20.0);
        percent.add(20.0);
        percent.add(50.0);
        percent.add(10.0);

        List<Integer> color = new ArrayList<>();
        color.add(getResources().getColor(R.color.colorAccent));
        color.add(Color.BLACK);
        color.add(getResources().getColor(R.color.colorPrimary));
        color.add(Color.GREEN);

        customProgress.setUp(percent,header,color,100);


        ArrayList<Integer> ranges = new ArrayList<>();
        ranges.add(0);
        ranges.add(10);
        ranges.add(25);
        ranges.add(35);
        ranges.add(40);

        ArrayList<String> rangeTexts = new ArrayList<>();
        rangeTexts.add("0");
        rangeTexts.add("10");
        rangeTexts.add("25");
        rangeTexts.add("35");
        rangeTexts.add("40");
        seekbar.setMinMax(0,40,5);
        seekbar.setRangeList(ranges,rangeTexts);


    }
}
