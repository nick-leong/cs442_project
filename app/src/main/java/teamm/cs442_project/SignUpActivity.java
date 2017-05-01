package teamm.cs442_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText emailEditText;

    Button userSignUpButton;
    ImageButton viewPasswordButton;

    boolean passIsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        passIsVisible = false;

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        userSignUpButton = (Button) findViewById(R.id.userSignUpButton);

        userSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameEditText.getText().length() == 0 || passwordEditText.getText().length() == 0 || passwordEditText.getText().length() < 3){
                    return;
                }else{
                    user createdUser = new user("", usernameEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString(), "?");
                    DatabaseHelper dbhelper = new DatabaseHelper(SignUpActivity.this);
                    dbhelper.addUser(createdUser);

                    Intent myIntent = new Intent();
                    myIntent.putExtra("username", createdUser.getUserName());
                    setResult(RESULT_OK, myIntent);
                    finishActivity(2610);
                    SignUpActivity.this.finish();
                }
            }
        });

        viewPasswordButton = (ImageButton) findViewById(R.id.viewPasswordButton);

        viewPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("isvisible", ""+passIsVisible);
                if(!passIsVisible){
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passIsVisible = true;
                }else{
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passIsVisible = false;
                }
            }
        });
    }
}
