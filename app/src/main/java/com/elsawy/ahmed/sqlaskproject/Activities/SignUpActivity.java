package com.elsawy.ahmed.sqlaskproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.MainActivity;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.SharedPrefManager;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private EditText firstNameField;
    private EditText lastNameField;
    private Button signUpButton;
    private TextView genderTextView;
    private TextView birthdayTextView;
    private ProgressDialog progressDialog;


    private String gender = null;
    private Date birthday = null;
    private int day, month, year;
    private String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailField = findViewById(R.id.email_sign_up);
        passwordField = findViewById(R.id.password_sign_up);
        firstNameField = findViewById(R.id.first_name_sign_up);
        lastNameField = findViewById(R.id.last_name_sign_up);
        signUpButton = findViewById(R.id.email_sign_up_button);
        genderTextView = findViewById(R.id.gender_id);
        birthdayTextView = findViewById(R.id.birthday_id);

        signUpButton.setOnClickListener(clickSignUpButton);
        genderTextView.setOnClickListener(clickGenderTextView);
        birthdayTextView.setOnClickListener(clickBirthdayTextView);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn())
        {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Log.d(TAG, "You are already have account");
        }
    }

    private void createAccount() {

        String email = SignUpActivity.this.emailField.getText().toString().trim();
        String password = SignUpActivity.this.passwordField.getText().toString().trim();
        String firstName = SignUpActivity.this.firstNameField.getText().toString().trim();
        String lastName = SignUpActivity.this.lastNameField.getText().toString().trim();
        String gender = SignUpActivity.this.genderTextView.getText().toString().trim();
        String date = SignUpActivity.this.birthdayTextView.getText().toString().trim();
        String username = firstName + " " + lastName;

        if (!validateForm(email,password,firstName,lastName,gender,date)) {
            return;
        }

        showProgressDialog();
        saveProfileData(email,password,username,gender,date);
    }

    private void saveProfileData(String email,String password,String username,String gender,String birthday) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                response -> {
                        hideProgressDialog();
                    try {
                        Log.i("MainActivity Response",response);

                        JSONObject jsonObject = new JSONObject(response);

                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.hide();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", gender);
                params.put("birthday", birthday);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private boolean validateForm(String email,String password,String firstName,String lastName,String gender,String birthday) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else if (!Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", Pattern.UNIX_LINES).matcher(email).matches()) {
            this.emailField.setError("Should be a valid email");
            valid = false;
        } else {
            emailField.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        if (TextUtils.isEmpty(firstName)) {
            firstNameField.setError("Required.");
            valid = false;
        } else {
            firstNameField.setError(null);
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameField.setError("Required.");
            valid = false;
        } else {
            lastNameField.setError(null);
        }

        if (gender.equals(getResources().getString(R.string.gender))) {
            genderTextView.setError("Required.");

            genderTextView.setText(R.string.indicateGedner);
            genderTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            valid = false;
        } else {
            genderTextView.setError(null);
        }

        if (birthday.equals(getResources().getString(R.string.birthday))) {
            birthdayTextView.setError("Required.");

            birthdayTextView.setText(R.string.determineBornday);
            birthdayTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            valid = false;
        } else {
            birthdayTextView.setError(null);
        }

        return valid;
    }

    private View.OnClickListener clickSignUpButton = v -> {
            createAccount();
    };

    private View.OnClickListener clickGenderTextView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

            builder.setTitle(R.string.gender)
                    .setItems(R.array.genderArray, (dialog, which) -> {
                        gender = (which == 0) ? "male" : "female";
                        genderTextView.setText(gender);
                        genderTextView.setTextColor(getResources().getColor(R.color.defaultTextColor));

                    });
            builder.create();
            builder.show();

        }
    };

    private View.OnClickListener clickBirthdayTextView = v -> showDialog(0);

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            String birthDayTxt = selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear;
            birthdayTextView.setText(birthDayTxt);
            birthdayTextView.setTextColor(getResources().getColor(R.color.defaultTextColor));

        }
    };


}
