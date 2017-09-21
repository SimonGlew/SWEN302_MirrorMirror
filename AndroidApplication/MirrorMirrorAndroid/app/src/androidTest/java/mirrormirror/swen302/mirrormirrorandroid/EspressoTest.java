package mirrormirror.swen302.mirrormirrorandroid;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;

import mirrormirror.swen302.mirrormirrorandroid.activities.HomeActivity;

/**
 * Created by slaterjack on 10/08/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest

public class EspressoTest {


    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(
            HomeActivity.class);



    @Test
    public void changeText_sameActivity() {
        HomeActivity activity = mActivityRule.getActivity();
        ImageView i = (ImageView)activity.findViewById(R.id.main_image);

    }
}


