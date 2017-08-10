package mirrormirror.swen302.mirrormirrorandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slaterjack on 10/08/17.
 */
public class CameraPreviewActivityTest {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void TextAppContext(){
        assertEquals("mirrormirror.swen302.mirrormirrorandroid", appContext.getPackageName());
    }

    public void TestCameraPermission(){

    }
}