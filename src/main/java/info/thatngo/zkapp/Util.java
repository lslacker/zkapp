package info.thatngo.zkapp;

import java.text.DateFormatSymbols;
import java.util.Random;

public class Util {
    public static Random random = new Random();
    public static String[] MONTHS = new DateFormatSymbols().getMonths();

    public static int nextInt(int low, int high){
        return random.nextInt(high-low) + low;
    }
}
