/*
 * Created on Sat Oct 10 2020
 *
 * Copyright (c) 2020 - RapidMart
 */
package com.app.rapidshopper;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText confirmEdit;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        confirmEdit = findViewById(R.id.confirmEdit);
        final Button continueButton = findViewById(R.id.ContinueButton);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        continueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                return false;
            }
        });
       /* Amplify.Auth.fetchAuthSession(
                result ->
                {
                    if( result.isSignedIn() ) {
                        startMainActivity();
                    }
                },
                error -> Log.e("LoginActivity", error.toString())
        );*/
    }
    public void startMainActivity() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("LoginActivity","SignIncomplete");
                Intent i = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(i);
            }
        });
    }
    public void signInUser()
    {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        Amplify.Auth.signIn(
                username,
                password,
                result -> {
                    if( result.isSignInComplete() )
                    {
                        startMainActivity();
                    }

                },
                error -> Log.e("LoginActivity", error.toString())
        );
    }
    public void signUpUser()
    {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = usernameEditText.getText().toString();
        Amplify.Auth.signUp(
                username,
                password,
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                resultsignup -> {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });

                },
                error -> Log.e("LoginActivity", "Sign up failed", error)
        );
    }
    public void confirmSignUp()
    {
        String strUsername = usernameEditText.getText().toString();
        String strConfirm = confirmEdit.getText().toString();

        Amplify.Auth.confirmSignUp(
                strUsername ,
                strConfirm,
                resultconfirm -> {
                    if( resultconfirm.isSignUpComplete() )
                    {
                        startMainActivity();
                    }
                    Log.i("LoginActivity", resultconfirm.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");

                },
                error -> Log.e("LoginActivity", error.toString())
        );
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}