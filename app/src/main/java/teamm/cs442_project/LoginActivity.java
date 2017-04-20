package teamm.cs442_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText usernameText;
    EditText passwordText;

    TextView signUpPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameText.getText().toString().equals("username") && passwordText.getText().toString().equals("password")){
                    passwordText.setText("");
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(myIntent);
                }
            }
        });

        signUpPrompt = (TextView) findViewById(R.id.signUpPrompt);

        signUpPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivityForResult(myIntent, 2610);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2610) {
            if (resultCode == RESULT_OK) {
                String usernameresult = data.getStringExtra("username");
                usernameText.setText(usernameresult);

                passwordText.requestFocus();
            }
        }
    }
}
