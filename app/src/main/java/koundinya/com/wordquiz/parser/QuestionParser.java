package koundinya.com.wordquiz.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import koundinya.com.wordquiz.model.Question;

/**
 * Created by girishk on 10/05/15.
 */
public class QuestionParser {

    public static ArrayList<Question> parseResponseValues(Map<String, Object> resultHash){

        ArrayList<Question> result = new ArrayList<Question>();

        for (Map.Entry<String, Object> entry : resultHash.entrySet()) {


            Question newQuestion = new Question();

            try {
                JSONObject jObject =  new JSONObject(entry.getValue().toString());

                newQuestion.qWord = (String)jObject.getString("qWord");
                newQuestion.answer = (String)jObject.getString("answer").replace("_"," ");
                JSONArray jArray = (JSONArray)jObject.get("choices");

                newQuestion.choices = new ArrayList<String>();

                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        newQuestion.choices.add(jArray.get(i).toString().replace("_"," "));
                    }
                }

                result.add(newQuestion);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }






        return result;

    }
}
