package teamm.cs442_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    Socket client_sock;
    final String server_ip = "52.27.236.253";
    final int data_port = 65121;

    String server_resp;

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

//        SocketAsync dataTask = new SocketAsync("a", goldTeamNum);
//        dataTask.execute();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        ArrayList<TextView> socketInput = new ArrayList<TextView>();
        socketInput.add(goldTeamNum);
        socketInput.add(blueTeamNum);
        socketInput.add(greenTeamNum);
        socketInput.add(orangeTeamNum);
        SocketAsync dataTask = new SocketAsync("a", socketInput);
        dataTask.execute();
    }

    private String getTeam(){
        return "Gold";
    }

}
