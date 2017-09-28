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

import com.shawnlin.numberpicker.NumberPicker;

import java.text.DecimalFormat;


import mirrormirror.swen302.mirrormirrorandroid.R;


/**
 * Created by hayandr1 on 29/08/17.
 */

public class InputWeightDialog extends AlertDialog implements View.OnClickListener{
    private static final int maxWeight = 200;
    private static final double subKGStepping = 0.1;
    private static final DecimalFormat dF = new DecimalFormat(".##");

    private static NumberPicker leftPicker;
    private static NumberPicker rightPicker;

    public InputWeightDialog(Context context){
        super(context);
    }

//    void populateSpinnerAdapters(){
//        String[] rightAdapterVals = new String[(int)(1/subKGStepping)]; //Sub-KG spinner
//        for(int n = 0; n < (int)(1/subKGStepping); n++){
//            rightAdapterVals[n] = dF.format(subKGStepping*n);
//        }
//        leftPicker = (NumberPicker) findViewById(R.id.weight_picker_left);
//        rightPicker = (NumberPicker) findViewById(R.id.weight_picker_right);
//        leftPicker.setMaxValue(maxWeight);
//        leftPicker.setMinValue(1);
//        rightPicker.setMinValue(0);
//        rightPicker.setMaxValue(9);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.input_weight_dialog);
//
//        populateSpinnerAdapters();
//        Button cancel = (Button) findViewById(R.id.cancel_button);
//        Button submit = (Button) findViewById(R.id.submit_button);
//        cancel.setOnClickListener(this);
//        submit.setOnClickListener(this);
//    }
//
    @Override
    public void onClick(View v) {

    }
//        } else if(v.getId() == R.id.submit_button){
//            float weightVal = leftPicker.getValue();
//            weightVal += (rightPicker.getValue())/((int)(1/subKGStepping));
//            ServerController.sendWeight(weightVal, DateTimeManager.getDatetimeAsString(), getContext());
//            cancel();
//        }
//    }
//
//
////    @Override
////    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////        String selected = (String) parent.getItemAtPosition(position);
//////        if(parent.getId() == R.id.weight_spinner_left){
//////            leftSelected = Float.valueOf(selected);
//////        } else if(parent.getId() == R.id.weight_spinner_right){
//////            rightSelected = Float.valueOf(selected);
//////        }
////    }
////
////    @Override
////    public void onNothingSelected(AdapterView<?> parent) {
////
////    }
}
