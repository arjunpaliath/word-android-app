package koundinya.com.wordquiz.view.activity;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import koundinya.com.wordquiz.R;
import koundinya.com.wordquiz.Util.UtilMethods;
import koundinya.com.wordquiz.model.CurrentUser;
import koundinya.com.wordquiz.model.Question;
import koundinya.com.wordquiz.model.User;
import koundinya.com.wordquiz.parser.QuestionParser;
import koundinya.com.wordquiz.parser.ScoreParser;
import koundinya.com.wordquiz.view.adapters.ScoreListAdapter;

public class GameActivity extends Activity implements View.OnClickListener{

    Firebase myFirebaseRef;
    Firebase scoreRef;
    ArrayList<Question> questionValues;
    TextView questionText;

    TextView option_1_text;
    TextView option_2_text;
    TextView option_3_text;
    TextView option_4_text;
    TextView score_card;

    RelativeLayout option_1;
    RelativeLayout option_2;
    RelativeLayout option_3;
    RelativeLayout option_4;

    RelativeLayout result_view;
    TextView result_text;
    Question currentQ;
    DrawerLayout mDrawerLayout;

    LinearLayout question_layer;
    RelativeLayout options_view;
    int currentQuestion;
    int []colorArray;


    private TextView user_name;
    private ListView list_scores;
    private ScoreListAdapter listAdapter;
    private ArrayList<User> scoreArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_game_layout);

        Firebase.setAndroidContext(this);

        initViews();

        setupFireBase();
    }


    public void initViews(){

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        question_layer = (LinearLayout) findViewById(R.id.game_layer);
        questionText = (TextView) findViewById(R.id.questionText);
        option_1_text = (TextView) findViewById(R.id.option_1_text);
        option_2_text = (TextView) findViewById(R.id.option_2_text);
        option_3_text = (TextView) findViewById(R.id.option_3_text);
        option_4_text = (TextView) findViewById(R.id.option_4_text);

        option_1 = (RelativeLayout) findViewById(R.id.option_1);
        option_1.setTag(1);
        option_2 = (RelativeLayout) findViewById(R.id.option_2);
        option_2.setTag(2);
        option_3 = (RelativeLayout) findViewById(R.id.option_3);
        option_3.setTag(3);
        option_4 = (RelativeLayout) findViewById(R.id.option_4);
        option_4.setTag(4);
        result_view = (RelativeLayout) findViewById(R.id.result_view);
        result_text = (TextView) findViewById(R.id.answer_status);
        score_card = (TextView) findViewById(R.id.score_card);

        colorArray = new int[] {getResources().getColor(R.color.srihari_blue),
                getResources().getColor(R.color.srihari_green),
                getResources().getColor(R.color.srihari_red),
                getResources().getColor(R.color.srihari_orange)};

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        handleDrawerViews();

    }



    public void handleDrawerViews() {

        user_name = (TextView) findViewById(R.id.user_name);

        user_name.setText(CurrentUser.getInstance(this).getName());

        list_scores = (ListView) findViewById(R.id.score_list);

        scoreArray = new ArrayList<User>();

        listAdapter = new ScoreListAdapter(this,R.layout.score_list_item,scoreArray);

        list_scores.setAdapter(listAdapter);


    }
    public void openDrawer(){
        mDrawerLayout.openDrawer(Gravity.RIGHT);
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

        scoreRef = new Firebase("https://wordapp1.firebaseio.com/scores");


        Query queryRef = scoreRef.orderByValue().limitToLast(10);

        queryRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                scoreArray.clear();
                scoreArray.addAll(ScoreParser.parseResponseValues(dataSnapshot));


                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


    public void updateViews(ArrayList<Question> questions){

        int initialOption = UtilMethods.generateRandomInt(0,questionValues.size()-1);
        currentQuestion = initialOption;
        generateQuestion(initialOption);

    }


    public void generateQuestion(int index){

        Question viewQuestion = questionValues.get(index);
        currentQ = viewQuestion;
        questionText.setText(viewQuestion.qWord);
        score_card.setText(CurrentUser.getInstance(this).getScore());

        Set<Integer> numbers = UtilMethods.generateRandomSet(0, 3);

        int count = 0;
        for (Integer s : numbers) {

            switch(count){

                case 0:
                    option_1_text.setText(viewQuestion.choices.get(s));
                    option_1.setBackgroundColor(colorArray[s]);
                    option_1.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
                case 1:
                    option_2_text.setText(viewQuestion.choices.get(s));
                    option_2.setBackgroundColor(colorArray[s]);
                    option_2.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
                case 2:
                    option_3_text.setText(viewQuestion.choices.get(s));
                    option_3.setBackgroundColor(colorArray[s]);
                    option_3.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
                case 3:
                    option_4_text.setText(viewQuestion.choices.get(s));
                    option_4.setBackgroundColor(colorArray[s]);
                    option_4.setOnClickListener((android.view.View.OnClickListener) this);
                    break;
            }
            count ++;
        }
        question_layer.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {


      switch (v.getId()) {
          case R.id.leader_board:
              openDrawer();
              break;
          default:
              boolean correctAnswer = false;
              option_1.setVisibility(View.GONE);
              option_2.setVisibility(View.GONE);
              option_3.setVisibility(View.GONE);
              option_4.setVisibility(View.GONE);

              result_view.setVisibility(View.VISIBLE);

              TextView selected_resp = null;

              switch ((int) v.getTag()) {
                  case 1:
                      selected_resp = (TextView) v.findViewById(R.id.option_1_text);
                      break;
                  case 2:
                      selected_resp = (TextView) v.findViewById(R.id.option_2_text);
                      break;
                  case 3:
                      selected_resp = (TextView) v.findViewById(R.id.option_3_text);
                      break;
                  case 4:
                      selected_resp = (TextView) v.findViewById(R.id.option_4_text);
                      break;

              }

              correctAnswer = selected_resp.getText().equals(currentQ.answer) ? true : false;

              TextView status;
              status = (TextView) result_view.findViewById(R.id.answer_status);

              if (correctAnswer) {

                  CurrentUser.getInstance(this).correctAnswer(true);
                  status.setText("Correct.");
              } else {

                  CurrentUser.getInstance(this).correctAnswer(false);
                  status.setText("Wrong.");
              }

              ColorDrawable viewColor = (ColorDrawable) v.getBackground();

              result_view.setBackgroundColor(viewColor.getColor());


              new Timer().schedule(new TimerTask() {
                  public void run() {
                      GameActivity.this.runOnUiThread(new Runnable() {
                          public void run() {
                              resetViews();
                              generateQuestion(UtilMethods.generateRandomInt(0, questionValues.size() - 1));
                          }
                      });
                  }
              }, 500);
            break;
      }

    }




    public void resetViews() {

        result_view.setVisibility(View.GONE);

        option_1.setVisibility(View.VISIBLE);
        option_2.setVisibility(View.VISIBLE);
        option_3.setVisibility(View.VISIBLE);
        option_4.setVisibility(View.VISIBLE);

    }

}

