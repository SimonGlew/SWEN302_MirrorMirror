package mirrormirror.swen302.mirrormirrorandroid.utilities;

import android.content.Context;
import android.util.Base64;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import mirrormirror.swen302.mirrormirrorandroid.activities.HomeActivity;
import mirrormirror.swen302.mirrormirrorandroid.activities.WeightGraphActivity;

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

        }catch(Exception e){
            e.printStackTrace();
        }

        socket.emit("weight event", messageObject);
    }

    public static void sendWeightsRequest(Context context, int numberOfDays){
        Socket socket = SocketSingleton.getInstance(context).getSocket();
        if(!socket.connected()){
            socket.connect();
        }

        JSONObject messageObject = new JSONObject();
        try{
            messageObject.put("uid", 3);
            messageObject.put("numDays", numberOfDays);
        }catch(Exception e){
            e.printStackTrace();
        }
        socket.emit("request weights event", messageObject);
    }

    public static void sendImagesRequest(Context context, int numberOfImages){
        Socket socket = SocketSingleton.getInstance(context).getSocket();
        if(!socket.connected()) {
            socket.connect();
        }

        JSONObject messageObject = new JSONObject();
        try {
            messageObject.put("uid", 3);
            messageObject.put("numImages", numberOfImages);

        }catch(Exception e){
            e.printStackTrace();
        }

        socket.emit("request last images event", messageObject);
    }

    public static void sendImageAdditionsRequest(Context context, int numberOfImages, int offset){
        Socket socket = SocketSingleton.getInstance(context).getSocket();
        if(!socket.connected()) {
            socket.connect();
        }

        JSONObject messageObject = new JSONObject();
        try {
            messageObject.put("uid", 3);
            messageObject.put("numImages", numberOfImages);
            messageObject.put("offset", offset);

        }catch(Exception e){
            e.printStackTrace();
        }

        socket.emit("request images event", messageObject);
    }

    public static void sendAndroidIdEvent(Context context){
        Socket socket = SocketSingleton.getInstance(context).getSocket();
        if(!socket.connected())
            socket.connect();

        socket.emit("android connection event");
    }

    public static void setSocketListeners(HomeActivity context){
        Socket socket = SocketSingleton.getInstance(context).getSocket();

        Emitter.Listener onInitialImagesMessage = createNewImageListener(context);
        socket.on("request last images success event", onInitialImagesMessage);

        Emitter.Listener onAdditionImagesMessage = createNewAdditionImageListener(context);
        socket.on("request images success event", onAdditionImagesMessage);

        Emitter.Listener onConnection = createConnectionListener(context);
        socket.on(Socket.EVENT_CONNECT, onConnection);

//        Emitter.Listener onWeightMessage = createWeightGraphListener(context);
//        socket.on("request weights success event", onWeightMessage);

        Emitter.Listener onNewWeightMessage = createNewWeightListener(context);
        socket.on("weight saved event", onNewWeightMessage);
    }

    public static void setSocketWeightListener(WeightGraphActivity context){
        Socket socket = SocketSingleton.getInstance(context).getSocket();

        Emitter.Listener onWeightMessage = createWeightGraphListener(context);
        socket.on("request weights success event", onWeightMessage);


    }

    public static Emitter.Listener createConnectionListener(final HomeActivity homeActivity){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                homeActivity.onConnection();
            }
        };
    }

    public static Emitter.Listener createWeightGraphListener(final WeightGraphActivity activity){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray jsonArray = (JSONArray)args[0];
                List<Weight> weights = Weight.parseWeights(jsonArray);
                activity.makeWeightGraph(weights);
            }
        };
    }

    public static Emitter.Listener createNewImageListener(final HomeActivity activity){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                    JSONArray jsonArray = (JSONArray)args[0];
                activity.loadInitialImagesFromServer(jsonArray);
            }
        };
    }

    public static Emitter.Listener createNewAdditionImageListener(final HomeActivity activity){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray jsonArray = (JSONArray)args[0];
                activity.loadMoreImages(jsonArray);
            }
        };
    }

    public static Emitter.Listener createNewWeightListener(final HomeActivity homeActivity){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Yeee");
                JSONObject jsonObject = (JSONObject) args[0];
                homeActivity.loadWeightPopup(jsonObject);
            }
        };
    }

}
