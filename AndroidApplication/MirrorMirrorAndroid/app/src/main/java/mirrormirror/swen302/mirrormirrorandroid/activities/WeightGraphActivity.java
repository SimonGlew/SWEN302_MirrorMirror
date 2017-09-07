package mirrormirror.swen302.mirrormirrorandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

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
        //Go get data

        //ServerController.getWeights(numDays, this);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0,1),
                new DataPoint(1,5),
                new DataPoint(2,3),
                new DataPoint(3,2),
                new DataPoint(4,6)
        });

        graph.addSeries(series);
    }


}
