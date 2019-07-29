package com.elsawy.ahmed.sqlaskproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.MainActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    String TAG = "LoginActivity";

    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private View progressView;
    private View loginFormView;
    private TextView signUpOpenView;
    private Button emailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn())
            openMainActivity();

        emailView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordView = (EditText) findViewById(R.id.password);
        signUpOpenView = (TextView) findViewById(R.id.sign_up_open);
        emailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);

        signUpOpenView.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        passwordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        emailSignInButton.setOnClickListener(view -> attemptLogin());

    }

    private void openMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void attemptLogin() {

        emailView.setError(null);
        passwordView.setError(null);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){// || !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            userLogin(email,password);
        }
    }

    private boolean isEmailValid(String email) {
        return (Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", Pattern.UNIX_LINES).matcher(email).matches());
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    private void userLogin(final String email,final String password){


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                response -> {
                    showProgress(false);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(!obj.getBoolean("error")){
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(
                                            obj.getInt("id"),
                                            obj.getString("username"),
                                            obj.getString("email"),
                                            obj.getString("gender"),
                                            obj.getString("birthday"));

                            openMainActivity();

                        }else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    obj.getString("message"),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    showProgress(false);

                    Toast.makeText(
                            getApplicationContext(),
                            error.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



}
