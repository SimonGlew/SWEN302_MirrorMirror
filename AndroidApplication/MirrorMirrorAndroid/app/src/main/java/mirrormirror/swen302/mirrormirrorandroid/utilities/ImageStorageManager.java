package mirrormirror.swen302.mirrormirrorandroid.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import mirrormirror.swen302.mirrormirrorandroid.R;

/**
 * Created by bondkyal on 10/08/17.
 */

public class ImageStorageManager {
    public static void storeImage(String dateTime, byte[] data, Context context) {
        try {
            String image = determineImageName(context);
            FileOutputStream fos = context.openFileOutput(dateTime, Context.MODE_PRIVATE);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImageByName(String fileName, Context context){
        try{
            FileInputStream fis = context.openFileInput(fileName);
            Bitmap image = BitmapFactory.decodeStream(fis);
            fis.close();
            return  image;

        }catch(Exception e){

        }
        return null;
    }

    public static String determineImageName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.images_shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        return null;
    }
}
