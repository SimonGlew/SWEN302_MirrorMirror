package mirrormirror.swen302.mirrormirrorandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import mirrormirror.swen302.mirrormirrorandroid.R;
import mirrormirror.swen302.mirrormirrorandroid.activities.CameraPreviewActivity;
import mirrormirror.swen302.mirrormirrorandroid.utilities.ImageStorageManager;

/**
 * Created by bondkyal on 10/08/17.
 */

public class HomeActivity extends AppCompatActivity {

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView mainImage;

    public static final int CAMERA_ACTIVITY_REQUEST_CODE = 10;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setReferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    private void setReferences() {
        image1 = (ImageView) findViewById(R.id.image_1);
        image2 = (ImageView) findViewById(R.id.image_2);
        image3 = (ImageView) findViewById(R.id.image_3);
        image4 = (ImageView) findViewById(R.id.image_4);
        mainImage = (ImageView) findViewById(R.id.main_image);
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

    private void loadImages(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.images_shared_preferences), Context.MODE_PRIVATE);
        String image1 = sharedPreferences.getString("image1", null);
        String image2 = sharedPreferences.getString("image2", null);
        String image3 = sharedPreferences.getString("image3", null);
        String image4 = sharedPreferences.getString("image4", null);

        this.image1.setImageBitmap(ImageStorageManager.loadImageByName(image1, this));
        this.image2.setImageBitmap(ImageStorageManager.loadImageByName(image2, this));
        this.image3.setImageBitmap(ImageStorageManager.loadImageByName(image3, this));
        this.image4.setImageBitmap(ImageStorageManager.loadImageByName(image4, this));
        this.mainImage.setImageBitmap(ImageStorageManager.loadImageByName(image1, this));
//        Map<String, ?> keyVals =  sharedPreferences.getAll();
//        for(String s : keyVals.keySet()){
//            switch(s){
//                case "image1":
//                    break;
//                case "image2":
//                    break;
//            }
//        }
    }
}
