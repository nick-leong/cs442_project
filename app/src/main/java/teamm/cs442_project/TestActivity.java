package teamm.cs442_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    boolean test_finished;
    ArrayList<QuestionNode> questions;
    int currentQuestion;
    Button fact_test_submitBtn;

    EditText group1_num;
    TextView group1_word1;
    TextView group1_word2;
    TextView group1_word3;

    EditText group2_num;
    TextView group2_word1;
    TextView group2_word2;
    TextView group2_word3;

    EditText group3_num;
    TextView group3_word1;
    TextView group3_word2;
    TextView group3_word3;

    EditText group4_num;
    TextView group4_word1;
    TextView group4_word2;
    TextView group4_word3;

    public char addScores(){
        int[] groups = new int[4];

        for(int i = 0; i < 5; i++){
            QuestionNode currQ = questions.get(i);
            WordClusterList.WordClusterNode currWordNode = currQ.words.wordnodes;
            for(int j = 0; j < 4; j++){
                Log.d("Words", currWordNode.words.toString());
                Log.d("Score", currWordNode.score+"");
                if(j == 0){
                    groups[0] += currWordNode.score;
                }else if(j == 1){
                    groups[1] += currWordNode.score;
                }else if(j == 2){
                    groups[2] += currWordNode.score;
                }else{
                    groups[3] += currWordNode.score;
                }
                currWordNode = currWordNode.next;
            }
        }

        int highest_num = groups[0];
        char highest_group = '0';
        for(int i = 1; i < 4; i++){
            if(groups[i] > highest_num){
                highest_num = groups[i];
                highest_group = Integer.toString(i).charAt(0);
            }
        }

        Log.d("HIGHEST_NUM", highest_num+"");
        return highest_group;
    }

    public void loadQuestion(){
        if(currentQuestion >= 5){
            return;
        }
        if(currentQuestion == 4){
            test_finished = true;
        }
        QuestionNode qNode = questions.get(currentQuestion);
        WordClusterList.WordClusterNode head = qNode.words.wordnodes;
        WordClusterList.WordClusterNode curr = head;

        for(int i = 1; i <= 4; i++){
            if(i == 1){
                group1_word1.setText(curr.words.get(0));
                group1_word2.setText(curr.words.get(1));
                group1_word3.setText(curr.words.get(2));
            }else if(i == 2){
                group2_word1.setText(curr.words.get(0));
                group2_word2.setText(curr.words.get(1));
                group2_word3.setText(curr.words.get(2));
            }else if(i == 3){
                group3_word1.setText(curr.words.get(0));
                group3_word2.setText(curr.words.get(1));
                group3_word3.setText(curr.words.get(2));
            }else{
                group4_word1.setText(curr.words.get(0));
                group4_word2.setText(curr.words.get(1));
                group4_word3.setText(curr.words.get(2));
            }
            curr = curr.next;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        currentQuestion = 0;

        group1_num = (EditText) findViewById(R.id.group1_num);
        group2_num = (EditText) findViewById(R.id.group2_num);
        group3_num = (EditText) findViewById(R.id.group3_num);
        group4_num = (EditText) findViewById(R.id.group4_num);

        group1_word1 = (TextView) findViewById(R.id.group1_word1);
        group1_word2 = (TextView) findViewById(R.id.group1_word2);
        group1_word3 = (TextView) findViewById(R.id.group1_word3);

        group2_word1 = (TextView) findViewById(R.id.group2_word1);
        group2_word2 = (TextView) findViewById(R.id.group2_word2);
        group2_word3 = (TextView) findViewById(R.id.group2_word3);

        group3_word1 = (TextView) findViewById(R.id.group3_word1);
        group3_word2 = (TextView) findViewById(R.id.group3_word2);
        group3_word3 = (TextView) findViewById(R.id.group3_word3);

        group4_word1 = (TextView) findViewById(R.id.group4_word1);
        group4_word2 = (TextView) findViewById(R.id.group4_word2);
        group4_word3 = (TextView) findViewById(R.id.group4_word3);

        questions = new ArrayList<QuestionNode>();
        questions.add(new QuestionNode(
                "1 Active Opportunistic Spontaneous",
                "2 Parental Traditional Responsible",
                "3 Authentic Harmonious Compassionate",
                "4 Versatile Inventive Competent"));

        questions.add(new QuestionNode(
                "1 Competitive Impetuous Impactful",
                "2 Practical Sensible Dependable",
                "3 Unique Empathetic Communicative",
                "4 Curious Conceptual Knowledgable"));

        questions.add(new QuestionNode(
                "1 Realistic Open-Minded Adventuresome",
                "2 Loyal Conservative Organized",
                "3 Devoted Warm Poetic",
                "4 Theoretical Seeking Ingenious"));

        questions.add(new QuestionNode(
                "1 Daring Impulsive Fun",
                "2 Concerned Prodedural Cooperative",
                "3 Tender Inspirational Dramatic",
                "4 Determined Complex Composed"));

        questions.add(new QuestionNode(
                "1 Exciting Courageous Skillful",
                "2 Orderly Conventional Caring",
                "3 Vivacious Affectionate Sympathetic",
                "4 Philosophical Principled Rational"));

        fact_test_submitBtn = (Button) findViewById(R.id.fact_test_submitBtn);
        fact_test_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(test_finished){
                    WordClusterList.WordClusterNode currentNode = questions.get(currentQuestion).words.wordnodes;
                    for(int i = 1; i <= 4; i++){
                        if(i == 1){
                            currentNode.score = Integer.parseInt(group1_num.getText().toString());
                        }else if(i == 2){
                            currentNode.score = Integer.parseInt(group2_num.getText().toString());
                        }else if(i == 3){
                            currentNode.score = Integer.parseInt(group3_num.getText().toString());
                        }else{
                            currentNode.score = Integer.parseInt(group4_num.getText().toString());
                        }
                        currentNode = currentNode.next;
                    }

                    char team = addScores();
                    Log.d("TEAM", team+"");

                    Intent myIntent = new Intent();
                    myIntent.putExtra("result", team);
                    setResult(RESULT_OK, myIntent);
                    finishActivity(2510);
                    TestActivity.this.finish();

                }else{
                    //add numbers to QuestionNode
                    WordClusterList.WordClusterNode currentNode = questions.get(currentQuestion).words.wordnodes;
                    for(int i = 1; i <= 4; i++){
                        if(i == 1){
                            currentNode.score = Integer.parseInt(group1_num.getText().toString());
                        }else if(i == 2){
                            currentNode.score = Integer.parseInt(group2_num.getText().toString());
                        }else if(i == 3){
                            currentNode.score = Integer.parseInt(group3_num.getText().toString());
                        }else{
                            currentNode.score = Integer.parseInt(group4_num.getText().toString());
                        }
                        currentNode = currentNode.next;
                    }

                    currentQuestion++;
                    loadQuestion();
                    group1_num.setText("");
                    group2_num.setText("");
                    group3_num.setText("");
                    group4_num.setText("");

                    group1_num.requestFocus();
                }

            }
        });

        loadQuestion();

    }
}
