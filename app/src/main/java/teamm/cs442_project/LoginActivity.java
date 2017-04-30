package teamm.cs442_project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    AccessTokenTracker att;

    ImageView fe_logo;
    Button loginBtn;
    Button toMapButton;
    LoginButton facebookLogin;
    EditText usernameText;
    EditText passwordText;

    TextView signUpPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());

        fe_logo = (ImageView) findViewById(R.id.fe_logo);
        fe_logo.setImageResource(R.mipmap.fe_icon);

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

        callbackManager = CallbackManager.Factory.create();

        facebookLogin = (LoginButton) findViewById(R.id.facebookLogin);
        facebookLogin.setReadPermissions("email");
        //facebookLogin.setFragment((Fragment) findViewById(R.id.login_container));

        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //usernameText.setText("Successful facebook login");
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }

            @Override
            public void onCancel() {
                usernameText.setText("Canceled facebook login");
            }

            @Override
            public void onError(FacebookException error) {
                usernameText.setText("Login error!");
            }
        });

        toMapButton = (Button) findViewById(R.id.toMapButton);
        toMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });

        att = new AccessTokenTracker(){
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken){
                if(currentAccessToken == null){
                    toMapButton.setVisibility(View.INVISIBLE);
                }else{
                    toMapButton.setVisibility(View.VISIBLE);
                }
            }
        };

        if(isLoggedIn()){
            toMapButton.setVisibility(View.VISIBLE);
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }else{
            toMapButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2610) {
            if (resultCode == RESULT_OK) {
                String usernameresult = data.getStringExtra("username");
                usernameText.setText(usernameresult);

                passwordText.requestFocus();
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
