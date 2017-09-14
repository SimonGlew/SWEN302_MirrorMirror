package mirrormirror.swen302.mirrormirrorandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(generateData());

        series.setTitle("Previous " + numDays + " days");
        series.setColor(getResources().getColor(R.color.colorAccent));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);

        graph.addSeries(series);

        graph.getViewport().setMinX(numDays);
        graph.getViewport().setMaxX(numDays);

    }

    public DataPoint[] generateData() {
        double min = 72.0;
        double max = 80.0;

        Random r = new Random();
        DecimalFormat df = new DecimalFormat("#.#");

        DataPoint[] dataPoints = new DataPoint[numDays];
        for(int i = 0; i < numDays; i ++) {

            double weight = (min + (max - min) * r.nextDouble());

            DataPoint d = new DataPoint(i, Double.parseDouble(df.format(weight)));
            dataPoints[i] = d;
        }
        return dataPoints;
    }

}
