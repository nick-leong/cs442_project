package teamm.cs442_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TestActivity extends AppCompatActivity {

    RadioGroup radGroupQ1;
    RadioButton radChoiceA;
    RadioButton radChoiceB;
    RadioButton radChoiceC;
    RadioButton radChoiceD;
    Button fact_test_submitBtn;

    char finalChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        radGroupQ1 = (RadioGroup) findViewById(R.id.radGroupQ1);
        radChoiceA = (RadioButton) findViewById(R.id.radChoiceA);
        radChoiceB = (RadioButton) findViewById(R.id.radChoiceB);
        radChoiceC = (RadioButton) findViewById(R.id.radChoiceC);
        radChoiceD = (RadioButton) findViewById(R.id.radChoiceD);

        fact_test_submitBtn = (Button) findViewById(R.id.fact_test_submitBtn);
        fact_test_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radGroupQ1.getCheckedRadioButtonId() == radChoiceA.getId()){
                    //choice A
                    finalChoice = 'A';
                }else if(radGroupQ1.getCheckedRadioButtonId() == radChoiceB.getId()){
                    //choice B
                    finalChoice = 'B';
                }else if(radGroupQ1.getCheckedRadioButtonId() == radChoiceC.getId()){
                    //choice C
                    finalChoice = 'C';
                }else if(radGroupQ1.getCheckedRadioButtonId() == radChoiceD.getId()){
                    //choice D
                    finalChoice = 'D';
                }
                Intent myIntent = new Intent();
                myIntent.putExtra("result", finalChoice);
                setResult(RESULT_OK, myIntent);
                finishActivity(2510);
                TestActivity.this.finish();
            }
        });


    }
}
