package mirrormirror.swen302.mirrormirrorandroid;

import android.content.Context;
import android.content.Intent;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.SocketIOException;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Jack on 7/08/2017.
 */

public class SocketSingleton {

    private static SocketSingleton instance;
    private static final String SERVER_ADDRESS = "http://192.168.1.13:3000";
    private Socket socket;
    private Context context;

    public static SocketSingleton get(Context context){
        if(instance == null){
            instance = getSync(context);
        }
        instance.context = context;
        return instance;
    }

    public static synchronized SocketSingleton getSync(Context context){
        if (instance == null) {
            instance = new SocketSingleton(context);
        }
        return instance;
    }

    public Socket getSocket(){
        return this.socket;
    }

    private SocketSingleton(Context context){
        this.context = context;
        try {
            this.socket = IO.socket(SERVER_ADDRESS);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }



}