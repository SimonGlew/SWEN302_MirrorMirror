package mirrormirror.swen302.mirrormirrorandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;

public class CameraPreviewActivity extends AppCompatActivity {

    private static final String SERVER_ADDRESS = "ws://192.168.1.13:3000";
    private Camera frontCamera;
    private CameraPreview frontCameraPreview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        beginCameraSession();
    }

    private void beginCameraSession(){
        requestCameraPermission();
        frontCamera = CameraDispatcher.getCameraInstance();
        frontCameraPreview = new CameraPreview(this, frontCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(frontCameraPreview,0);

        Button captureButton = (Button)findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontCamera.takePicture(null, null, picture);
            }
        });

    }

    private Camera.PictureCallback picture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

//            String dataString = Base64.encodeToString(data, Base64.DEFAULT);
//            resultData.setData(Uri.parse(dataString));
//
//            setResult(RESULT_OK, resultData);
//            finish();
            result(data);
            //sendImageAsBytes(data);
            //request permissions to write to external storage

            //Save image in external storage maybe interanl?



            //display image on in view
        }
    };

    private void result(byte[] data){

        Intent resultData = new Intent();
        resultData.putExtra("imageBytes", data);
        setResult(RESULT_OK, resultData);
        finish();
    }

    private void sendImageAsBytes( byte[] imageBytes){
        int x = 0;
        requestInternetPermission();
        Socket socket = null;
        try {
            socket = IO.socket(SERVER_ADDRESS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();

        String byteString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        JSONObject messageObject = new JSONObject();
        try {
            messageObject.put("username", "hi");
            messageObject.put("image", byteString);


        }catch(Exception e){
            e.printStackTrace();
        }

        socket.emit("image event", messageObject);
    }

    public void requestCameraPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void requestInternetPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

}
