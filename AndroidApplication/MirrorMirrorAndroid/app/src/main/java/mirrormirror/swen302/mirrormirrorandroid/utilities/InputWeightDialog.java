package mirrormirror.swen302.mirrormirrorandroid.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.text.DecimalFormat;


import mirrormirror.swen302.mirrormirrorandroid.R;


/**
 * Created by hayandr1 on 29/08/17.
 */

public class InputWeightDialog extends AlertDialog implements View.OnClickListener, Spinner.OnItemSelectedListener {
    private static final int maxWeight = 200;
    private static final double subKGStepping = 0.1;
    private static final DecimalFormat dF = new DecimalFormat(".##");

    private static float leftSelected;
    private static float rightSelected;

    public InputWeightDialog(Context context){
        super(context);
    }

    void populateSpinnerAdapters(){
//        String[] leftAdapterVals = new String[maxWeight]; //KG spinner
//        String[] rightAdapterVals = new String[(int)(1/subKGStepping)]; //Sub-KG spinner
//        for(int n = 0; n < maxWeight; n++){
//            leftAdapterVals[n] = Integer.toString(n);
//        }
//        for(int n = 0; n < (int)(1/subKGStepping); n++){
//            rightAdapterVals[n] = dF.format(subKGStepping*n);
//        }
//        ArrayAdapter<String> leftAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_value, leftAdapterVals);
//        ArrayAdapter<String> rightAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_value, rightAdapterVals);
//        Spinner leftSpinner = (Spinner) findViewById(R.id.weight_spinner_left);
//        Spinner rightSpinner = (Spinner) findViewById(R.id.weight_spinner_right);
//        leftSpinner.setAdapter(leftAdapter);
//        rightSpinner.setAdapter(rightAdapter);
//        leftSpinner.setOnItemSelectedListener(this);
//        rightSpinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_weight_dialog);
        populateSpinnerAdapters();
        Button cancel = (Button) findViewById(R.id.cancel_button);
        Button submit = (Button) findViewById(R.id.submit_button);
        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cancel_button){
            cancel();
        } else if(v.getId() == R.id.submit_button){
            ServerController.sendWeight(leftSelected+rightSelected, DateTimeManager.getDatetimeAsString(), getContext());
            cancel();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String) parent.getItemAtPosition(position);
        if(parent.getId() == R.id.weight_spinner_left){
            leftSelected = Float.valueOf(selected);
        } else if(parent.getId() == R.id.weight_spinner_right){
            rightSelected = Float.valueOf(selected);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
