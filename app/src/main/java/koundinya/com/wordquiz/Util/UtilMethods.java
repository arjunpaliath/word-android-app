package koundinya.com.wordquiz.Util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    private static List<String> nouns = new ArrayList<String>();
    private static List<String> adjectives = new ArrayList<String>();

    private static int prime;


    public static String randomUserName(Context context, int rand){

        try {
            load(context,"a.txt", adjectives);
            load(context,"n.txt", nouns);
        } catch (IOException e) {
            throw new Error(e);
        }

        int combo = size();

        int primeCombo = 2;
        while (primeCombo<=combo) {
            int nextPrime = primeCombo+1;
            primeCombo *= nextPrime;
        }
        prime = primeCombo+1;

         int a = rand%adjectives.size();
         int n = rand/adjectives.size();

        String adjective = adjectives.get(a);
        adjective =  adjective.substring(0,1).toUpperCase() + adjective.substring(1).toLowerCase();

        String noun = nouns.get(n);
        noun =  noun.substring(0,1).toUpperCase() + noun.substring(1).toLowerCase();

        return adjective+noun;

    }
    private  static int size() {
        return nouns.size()*adjectives.size();
    }

    private static int getPrime() {
        return prime;
    }

    private static void load(Context mContext,String name, List<String> col) throws IOException {

        InputStream inputStream = mContext.getAssets().open(name);

        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line=r.readLine())!=null)
                col.add(line);
        } finally {
            r.close();
        }
    }



}


