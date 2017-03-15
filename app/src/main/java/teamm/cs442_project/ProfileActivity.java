package teamm.cs442_project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView factionLabel;
    ImageView profile_img;
    Button factionTestBtn;
    char factionChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_img = (ImageView) findViewById(R.id.profile_img);
        factionLabel = (TextView) findViewById(R.id.factionLabel);

        factionChoice = 'Z';

        Intent testIntent = getIntent();
        if(testIntent != null){
            factionChoice = testIntent.getCharExtra("result", 'Z');
        }

        if(factionChoice != 'Z'){
            if(factionChoice == 'A'){
                factionLabel.setText("Red");
                profile_img.setBackgroundColor(Color.RED);
            }else if(factionChoice == 'B'){
                factionLabel.setText("Blue");
                profile_img.setBackgroundColor(Color.BLUE);
            }else if(factionChoice == 'C'){
                factionLabel.setText("Green");
                profile_img.setBackgroundColor(Color.GREEN);
            }else if(factionChoice == 'D'){
                factionLabel.setText("Yellow");
                profile_img.setBackgroundColor(Color.YELLOW);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2510) {
            if (resultCode == RESULT_OK) {
                if(data != null){
                    factionChoice = data.getCharExtra("result", 'Z');
                }

                if(factionChoice != 'Z'){
                    if(factionChoice == 'A'){
                        factionLabel.setText("Red");
                        profile_img.setBackgroundColor(Color.RED);
                    }else if(factionChoice == 'B'){
                        factionLabel.setText("Blue");
                        profile_img.setBackgroundColor(Color.BLUE);
                    }else if(factionChoice == 'C'){
                        factionLabel.setText("Green");
                        profile_img.setBackgroundColor(Color.GREEN);
                    }else if(factionChoice == 'D'){
                        factionLabel.setText("Yellow");
                        profile_img.setBackgroundColor(Color.YELLOW);
                    }
                }
            }
        }
    }
}
