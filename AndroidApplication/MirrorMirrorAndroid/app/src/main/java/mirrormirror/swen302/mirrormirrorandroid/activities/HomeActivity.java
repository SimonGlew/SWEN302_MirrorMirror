package mirrormirror.swen302.mirrormirrorandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mirrormirror.swen302.mirrormirrorandroid.R;
import mirrormirror.swen302.mirrormirrorandroid.activities.CameraPreviewActivity;
import mirrormirror.swen302.mirrormirrorandroid.adapters.HorizontalAdapter;
import mirrormirror.swen302.mirrormirrorandroid.utilities.DateTimeManager;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ImageStorageManager;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ServerController;

/**
 * Created by bondkyal on 10/08/17.
 */

public class HomeActivity extends AppCompatActivity {


    public static final int CAMERA_ACTIVITY_REQUEST_CODE = 10;

    RecyclerView recyclerView;
    HorizontalAdapter horizontalAdapter;

    List<String> filePaths;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
//        setImageClickListeners();
//        setButtonClickListeners();
        initialLoadImages();
        setRecyclerView();


    }


    private void setRecyclerView(){

        recyclerView = (RecyclerView)findViewById(R.id.horizontal_recycler_view);
        horizontalAdapter = new HorizontalAdapter(filePaths,this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(horizontalAdapter);
    }

    private void setButtonClickListeners(){
//        Button weightSubmit = (Button) findViewById(R.id.submit_weight);
        //final EditText weightField = (EditText)findViewById(R.id.weight_text_field);

//        weightSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Float weight = Float.valueOf(weightField.getText().toString());
//                ServerController.sendWeight(weight, DateTimeManager.getDatetimeAsString(), getApplicationContext());
//                weightField.clearFocus();
//                hideKeyboard();
//            }
//        });
//        weightField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_DONE){
//                    Float weight = Float.valueOf(weightField.getText().toString());
//                    ServerController.sendWeight(weight, DateTimeManager.getDatetimeAsString(), getApplicationContext());
//                    weightField.clearFocus();
//                    hideKeyboard();
//                }
//                return false;
//            }
//        });
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.take_image){
            Intent intent = new Intent(this, CameraPreviewActivity.class);
            startActivityForResult(intent, CAMERA_ACTIVITY_REQUEST_CODE);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadImages();
    }

    private void initialLoadImages(){
        File[] files = getFilesDir().listFiles();
        filePaths = new ArrayList<>(files.length);
        for(int i = files.length-1; i >= 0; i --){
            filePaths.add(files[i].getName());
        }
    }

    private void loadImages(){
        File[] files = getFilesDir().listFiles();
        for(int i = filePaths.size(); i < files.length; i ++){
            filePaths.add(0,files[i].getName());
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
