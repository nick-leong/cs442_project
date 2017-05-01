package teamm.cs442_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView goldTeamNum;
    TextView blueTeamNum;
    TextView greenTeamNum;
    TextView orangeTeamNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        goldTeamNum = (TextView) findViewById(R.id.goldTeamNum);
        blueTeamNum = (TextView) findViewById(R.id.blueTeamNum);
        greenTeamNum = (TextView) findViewById(R.id.greenTeamNum);
        orangeTeamNum = (TextView) findViewById(R.id.orangeTeamNum);

        goldTeamNum.setText(Integer.toString((int) Math.floor(Math.random()*10)));
        blueTeamNum.setText(Integer.toString((int) Math.floor(Math.random()*10)));
        greenTeamNum.setText(Integer.toString((int) Math.floor(Math.random()*10)));
        orangeTeamNum.setText(Integer.toString((int) Math.floor(Math.random()*10)));
    }
}
