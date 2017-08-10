package mirrormirror.swen302.mirrormirrorandroid.utilities;

import android.util.Base64;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by bondkyal on 10/08/17.
 */

public class ServerController {

    private static final String SERVER_ADDRESS = "http://130.195.6.76:3000";

    public static void sendImageAsBytes( byte[] imageBytes, String datetime){
        int x = 0;

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
            messageObject.put("uid", "3");
            messageObject.put("image", byteString);
            messageObject.put("datetime", datetime);

        }catch(Exception e){
            e.printStackTrace();
        }

        socket.emit("image event", messageObject);
    }
}
