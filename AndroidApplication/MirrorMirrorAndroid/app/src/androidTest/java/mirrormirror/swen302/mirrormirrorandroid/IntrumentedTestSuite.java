package mirrormirror.swen302.mirrormirrorandroid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.github.nkzawa.socketio.client.Socket;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

import mirrormirror.swen302.mirrormirrorandroid.utilities.SocketSingleton;

import static org.junit.Assert.*;

/**
 * Created by slaterjack on 10/08/17.
 */
public class IntrumentedTestSuite {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void TextAppContext(){
        assertEquals("mirrormirror.swen302.mirrormirrorandroid", appContext.getPackageName());
    }

    @Test
    public void TestSocketConnection(){
        final Socket socket = SocketSingleton.getInstance(appContext).getSocket();
        socket.connect();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                assertTrue(socket.connected());

            }
        }, 1000);
    }
}