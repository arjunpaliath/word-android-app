package koundinya.com.wordquiz.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import koundinya.com.wordquiz.R;
import koundinya.com.wordquiz.model.Question;
import koundinya.com.wordquiz.parser.QuestionParser;

public class GameActivity extends Activity implements View.OnClickListener{

    Firebase myFirebaseRef;
    ArrayList<Question> questionValues;
    TextView questionText;

    TextView option_1_text;
    TextView option_2_text;
    TextView option_3_text;
    TextView option_4_text;

    RelativeLayout option_1;
    RelativeLayout option_2;
    RelativeLayout option_3;
    RelativeLayout option_4;

    RelativeLayout result_view;
    TextView result_text;
    Question currentQ;

    LinearLayout question_layer;
    RelativeLayout options_view;
    int currentQuestion;
    int []colorArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Firebase.setAndroidContext(this);

        initViews();

        setupFireBase();
    }


    public void initViews(){
        question_layer = (LinearLayout) findViewById(R.id.game_layer);
        questionText = (TextView) findViewById(R.id.questionText);
        option_1_text = (TextView) findViewById(R.id.option_1_text);
        option_2_text = (TextView) findViewById(R.id.option_2_text);
        option_3_text = (TextView) findViewById(R.id.option_3_text);
        option_4_text = (TextView) findViewById(R.id.option_4_text);

        option_1 = (RelativeLayout) findViewById(R.id.option_1);
        option_2 = (RelativeLayout) findViewById(R.id.option_2);
        option_3 = (RelativeLayout) findViewById(R.id.option_3);
        option_4 = (RelativeLayout) findViewById(R.id.option_4);
        result_view = (RelativeLayout) findViewById(R.id.result_view);
        result_text = (TextView) findViewById(R.id.answer_status);

        colorArray = new int[] {getResources().getColor(R.color.srihari_blue),
                getResources().getColor(R.color.srihari_green),
                getResources().getColor(R.color.srihari_red),
                getResources().getColor(R.color.srihari_orange)};


    }

    public void setupFireBase() {

        myFirebaseRef = new Firebase("https://wordapp1.firebaseio.com/questions/");

        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Map<String, Object> resultObject = (Map<String, Object>) snapshot.getValue();

                questionValues = QuestionParser.parseResponseValues(resultObject);

                updateViews(questionValues);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }


    public void updateViews(ArrayList<Question> questions){

        int initialOption = randInt(0,questionValues.size());
        currentQuestion = initialOption;
        generateQuestion(initialOption);

    }


    public void generateQuestion(int index){

        Question viewQuestion = questionValues.get(index);
        currentQ = viewQuestion;
        questionText.setText(viewQuestion.qWord);


        Set<Integer> numbers = randIntSet(0, 3);

        int count = 0;
        for (Integer s : numbers) {

            switch(count){

                case 0:
                    option_1_text.setText(viewQuestion.choices.get(s));
                    option_1.setTag(s);
                    option_1.setBackgroundColor(colorArray[s]);
                    option_1.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
                case 1:
                    option_2_text.setText(viewQuestion.choices.get(s));
                    option_2.setTag(s);
                    option_2.setBackgroundColor(colorArray[s]);
                    option_2.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
                case 2:
                    option_3_text.setText(viewQuestion.choices.get(s));
                    option_3.setBackgroundColor(colorArray[s]);
                    option_3.setTag(s);
                    option_3.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
                case 3:
                    option_4_text.setText(viewQuestion.choices.get(s));
                    option_4.setBackgroundColor(colorArray[s]);
                    option_4.setTag(s);
                    option_4.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
            }
            count ++;
        }
        question_layer.setVisibility(View.VISIBLE);

    }


    public int randInt(int min, int max) {

        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;

    }
    public  Set<Integer> randIntSet(int min, int max) {


        Random rand = new Random();
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < 4)
        {
            int randomNum = rand.nextInt((max - min) + 1) + min;
            generated.add(randomNum);
        }
        return generated;
    }


    @Override
    public void onClick(View v) {

        boolean correctAnswer = false;
        option_1.setVisibility(View.GONE);
        option_2.setVisibility(View.GONE);
        option_3.setVisibility(View.GONE);
        option_4.setVisibility(View.GONE);

        result_view.setVisibility(View.VISIBLE);

        TextView selected_resp = null;

        switch((int)v.getTag()){
            case 0:
                selected_resp = (TextView)v.findViewById(R.id.option_1_text);
            case 1:
                selected_resp = (TextView)v.findViewById(R.id.option_1_text);
            case 2:
                selected_resp = (TextView)v.findViewById(R.id.option_1_text);
            case 3:
                selected_resp = (TextView)v.findViewById(R.id.option_1_text);

        }
        correctAnswer = selected_resp.getText() == currentQ.qWord ? true : false;

        TextView status;
        status = (TextView) result_view.findViewById(R.id.answer_status);

        if(correctAnswer) {

            status.setText("Correct.");
        }else {
            status.setText("Wrong.");
        }
        result_view.setBackgroundColor(colorArray[(int)v.getTag()]);


        new Timer().schedule(new TimerTask(){
            public void run() {
                GameActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        resetViews();
                        generateQuestion(randInt(0,questionValues.size()));
                    }
                });
            }
        }, 500);


    }




    public void resetViews() {

        result_view.setVisibility(View.GONE);

        option_1.setVisibility(View.VISIBLE);
        option_2.setVisibility(View.VISIBLE);
        option_3.setVisibility(View.VISIBLE);
        option_4.setVisibility(View.VISIBLE);

    }
}
