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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mirrormirror.swen302.mirrormirrorandroid.R;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ServerController;

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
        makeWeightGraph();
    }

    private void makeWeightGraph(){
        System.out.println(numDays);
        //Go get data

        //ServerController.getWeights(numDays, this);
        GraphView graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(numDays + 1);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(generateData());

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Number of Days away from " + format.format(new Date()));
        gridLabel.setVerticalAxisTitle("Weight (kg)");
        gridLabel.setVerticalAxisTitleTextSize(20);
        gridLabel.setHorizontalAxisTitleTextSize(20);
        graph.setTitle("Previous " + numDays + " days");
        graph.setTitleTextSize(50);

        series.setColor(getResources().getColor(R.color.colorAccent));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE, (int)(- dataPoint.getX()));
                Toast.makeText(WeightGraphActivity.this, "On " + format.format(cal.getTime()) + ", you weighed: " +
                        dataPoint.getY() + "kgs", Toast.LENGTH_SHORT).show();
            }
        });

        graph.addSeries(series);



    }

    public DataPoint[] generateData() {
        double min = 72.0;
        double max = 80.0;

        Random r = new Random();
        DecimalFormat df = new DecimalFormat("#.#");

        DataPoint[] dataPoints = new DataPoint[numDays];
        for(int i = 1; i <= numDays; i ++) {

            double weight = (min + (max - min) * r.nextDouble());

            DataPoint d = new DataPoint(i, Double.parseDouble(df.format(weight)));
            dataPoints[i -1] = d;
        }
        return dataPoints;
    }

}
