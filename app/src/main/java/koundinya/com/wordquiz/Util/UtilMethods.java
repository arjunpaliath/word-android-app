package koundinya.com.wordquiz.Util;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by girishk on 10/05/15.
 */
public class UtilMethods {

    public static Set<Integer> generateRandomSet(int min, int max){

        Random rand = new Random();
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < 4)
        {
            int randomNum = rand.nextInt((max - min) + 1) + min;
            generated.add(randomNum);
        }
        return generated;

    }


    public static int generateRandomInt(int min, int max) {
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;

    }
}
