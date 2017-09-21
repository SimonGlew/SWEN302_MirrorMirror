package mirrormirror.swen302.mirrormirrorandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mirrormirror.swen302.mirrormirrorandroid.R;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ServerController;
import mirrormirror.swen302.mirrormirrorandroid.utilities.Weight;

/**
 * Created by glewsimo on 7/09/17.
 */

public class WeightGraphActivity extends AppCompatActivity {

    private int numDays = 0;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_graph);

        Intent intent = getIntent();
        numDays = intent.getIntExtra("numDays", 0);
        ServerController.setSocketWeightListener(this);
        ServerController.sendWeightsRequest(this, numDays);

    }

    public void makeWeightGraph(List<Weight> weights){
        System.out.println(numDays);
        //Go get data

        //ServerController.getWeights(numDays, this);

        DataPoint[] datapoints = generateData(weights);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(datapoints);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(numDays);

        graph.getViewport().setScrollable(true);

        series.setColor(getResources().getColor(R.color.colorAccent));
        series.setThickness(7);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(13);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, (int)((dataPoint.getX() + 1) - numDays));
                Toast.makeText(WeightGraphActivity.this, "On " + format.format(cal.getTime()) + ", you weighed: " +
                        dataPoint.getY() + "kgs", Toast.LENGTH_SHORT).show();
            }
        });

        graph.addSeries(series);



    }

    public DataPoint[] generateData(List<Weight> weights) {
        DecimalFormat df = new DecimalFormat("#.#");

        DataPoint[] dataPoints = new DataPoint[weights.size()];
        for(int i = 0; i < weights.size(); i ++) {
            double weight = weights.get(i).getWeight();

            long timeDiff = new Date().getTime() - weights.get(i).getDate().getTime();
            double xValue = weights.size() - 1 - ((((timeDiff / 1000) / 60) / 60) / 24);
            if(xValue < 0){
                int z = 1;
            }


            DataPoint d = new DataPoint(xValue, Double.parseDouble(df.format(weight)));
            dataPoints[weights.size() - 1 - i] = d;
        }
        return dataPoints;
    }

}
