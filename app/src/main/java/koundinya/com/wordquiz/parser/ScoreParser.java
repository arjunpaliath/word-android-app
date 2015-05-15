package koundinya.com.wordquiz.parser;

import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;

import koundinya.com.wordquiz.model.User;

/**
 * Created by girishk on 12/05/15.
 */
public class ScoreParser {


    public static ArrayList<User> parseResponseValues(DataSnapshot parent) {

        ArrayList<User> result = new ArrayList<User>();
        User newUser;


        for (DataSnapshot child : parent.getChildren()) {

            newUser = new User();

            Long longScore = child.getValue(Long.class);

            System.out.println(longScore);

            Integer intScore = (int) (long) longScore;

            newUser.name   = child.getKey();
            newUser.score  = intScore;

            result.add(newUser);
        }


        Collections.reverse(result);

        return result;
    }
}
