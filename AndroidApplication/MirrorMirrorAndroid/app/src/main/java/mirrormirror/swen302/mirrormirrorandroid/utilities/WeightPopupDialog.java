package mirrormirror.swen302.mirrormirrorandroid.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import mirrormirror.swen302.mirrormirrorandroid.R;

/**
 * Created by bondkyal on 28/09/17.
 */

public class WeightPopupDialog extends AlertDialog implements View.OnClickListener{

    private TextView weightTextView;
    private Double weight;

    public WeightPopupDialog(@NonNull Context context, Double w) {
        super(context);
        this.weight = w;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_weight_popup);

        Button cancel = (Button) findViewById(R.id.cancel_button);
        weightTextView = (TextView) findViewById(R.id.weight_label);
        weightTextView.setText(this.weight + " kg");

        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cancel_button){
            cancel();
        }
    }

}
