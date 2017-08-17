package mirrormirror.swen302.mirrormirrorandroid.utilities;

import android.content.Context;
import android.util.Base64;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by bondkyal on 10/08/17.
 */

public class ServerController {


    public static void sendImageAsBytes( byte[] imageBytes, String datetime, Context context){
        int x = 0;

        Socket socket = SocketSingleton.getInstance(context).getSocket();
        if(!socket.connected()) {
            socket.connect();
        }
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

    public static void sendWeight(float weight, String datetime, Context context){
        Socket socket = SocketSingleton.getInstance(context).getSocket();
        if(!socket.connected()) {
            socket.connect();
        }

        JSONObject messageObject = new JSONObject();
        try {
            messageObject.put("uid", "3");
            messageObject.put("weight", weight);
            messageObject.put("datetime", datetime);

        }catch(Exception e){
            e.printStackTrace();
        }

        socket.emit("weight event", messageObject);
    }
}
