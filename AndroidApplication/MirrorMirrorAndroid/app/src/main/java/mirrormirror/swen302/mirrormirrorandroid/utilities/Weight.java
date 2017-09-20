package mirrormirror.swen302.mirrormirrorandroid.utilities;

import java.util.Date;

/**
 * Created by glewsimo on 21/09/17.
 */

public class Weight {
    private double weight;
    private Date date;

    public Weight(double weight, Date date){
        this.weight = weight;
        this.date = date;
    }

    public double getWeight(){
        return this.weight;
    }

    public Date getDate(){
        return this.date;
    }
}
