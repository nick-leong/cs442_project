package teamm.cs442_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    TextView factionLabel;
    TextView nameLabel;
    ImageView profile_img;
    Button factionTestBtn;
    char factionChoice;

    String userFaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_img = (ImageView) findViewById(R.id.profile_img);

        factionLabel = (TextView) findViewById(R.id.factionLabel);
        nameLabel = (TextView) findViewById(R.id.nameLabel);

        factionChoice = 'Z';

        Intent testIntent = getIntent();
        boolean fromMain = false;
        if(testIntent != null){
            try {
                factionChoice = testIntent.getCharExtra("result", 'Z');
                userFaction = testIntent.getStringExtra("faction");
                fromMain = true;
                if(userFaction == null){
                    userFaction = "None";
                }
            }catch(Exception e){
                userFaction = testIntent.getStringExtra("faction");
                fromMain = true;
                if(userFaction == null){
                    userFaction = "None";
                }
            }
        }

        if(fromMain){
            factionLabel.setText(userFaction);
        }else{

            if(factionChoice != 'Z'){
                if(factionChoice == '0'){
                    factionLabel.setText("Gold");
                    //profile_img.setBackgroundColor(Color.parseColor("#FFD700"));
                }else if(factionChoice == '1'){
                    factionLabel.setText("Green");
                    //profile_img.setBackgroundColor(Color.GREEN);
                }else if(factionChoice == '2'){
                    factionLabel.setText("Blue");
                    //profile_img.setBackgroundColor(Color.BLUE);
                }else if(factionChoice == '3'){
                    factionLabel.setText("Orange");
                    //profile_img.setBackgroundColor(Color.parseColor("#FFA500"));
                }
                userFaction = factionLabel.getText().toString();
            }
        }

        factionTestBtn = (Button) findViewById(R.id.factionTestBtn);
        factionTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ProfileActivity.this, TestActivity.class);
                ProfileActivity.this.startActivityForResult(myIntent, 2510);
            }
        });

        String userid = Profile.getCurrentProfile().getId();
        Picasso.with(this).load("https://graph.facebook.com/" + userid + "/picture?type=large").into(profile_img);

        nameLabel.setText(Profile.getCurrentProfile().getFirstName() + " " +Profile.getCurrentProfile().getLastName());
    }

    @Override
    public void onBackPressed(){

        Intent myIntent = new Intent();
        myIntent.putExtra("faction_name", userFaction);
        setResult(RESULT_OK, myIntent);
        finishActivity(209);
        ProfileActivity.this.finish();

        super.onBackPressed();
    }

    public static URL getFacebookProfilePictureURL(String userID){
        try {
            URL imageURL = new URL("http://graph.facebook.com/" + userID + "/picture?type=large");
            return imageURL;
        }catch(Exception e){
            Log.d("ERROR", e.toString());
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2510) {
            if (resultCode == RESULT_OK) {
                if(data != null){
                    factionChoice = data.getCharExtra("result", 'Z');
                }

                if(factionChoice != 'Z'){
                    if(factionChoice == '0'){
                        factionLabel.setText("Gold");
                        //profile_img.setBackgroundColor(Color.parseColor("#FFD700"));
                    }else if(factionChoice == '1'){
                        factionLabel.setText("Green");
                        //profile_img.setBackgroundColor(Color.GREEN);
                    }else if(factionChoice == '2'){
                        factionLabel.setText("Blue");
                        //profile_img.setBackgroundColor(Color.BLUE);
                    }else if(factionChoice == '3'){
                        factionLabel.setText("Orange");
                        //profile_img.setBackgroundColor(Color.parseColor("#FFA500"));
                    }
                    userFaction = factionLabel.getText().toString();
                }
            }
        }
    }
}
