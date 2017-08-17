package mirrormirror.swen302.mirrormirrorandroid.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bondkyal on 10/08/17.
 */

public class DateTimeManager {
    public static String getDatetimeAsString(){
        Date dateNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTime = ft.format(dateNow);
        return dateTime;
    }

    public static Date getDateFromString(String stringDate){
        return null;
    }
}
