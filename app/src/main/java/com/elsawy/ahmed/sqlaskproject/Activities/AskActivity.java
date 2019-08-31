package com.elsawy.ahmed.sqlaskproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AskActivity extends AppCompatActivity implements View.OnClickListener {

    CircleImageView anonymousBtn;
    RelativeLayout anonymousLayout;
    TextView anonymousTV;
    TextView letterCountTV;
    EditText questionEdit;
    ImageButton sendQuestionBtn;
    ImageButton cancelQuestionBtn;

    Animation rightAnimation;
    Animation leftAnimation;
    boolean hideUser = false;
    Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        this.currentQuestion = (Question) getIntent().getParcelableExtra("questionInfo");

        anonymousBtn = (CircleImageView) findViewById(R.id.anonymous_user_btn);
        anonymousLayout = (RelativeLayout) findViewById(R.id.anonymous_user_layout);
        anonymousTV = (TextView) findViewById(R.id.anonymous_user_TV);
        questionEdit = (EditText) findViewById(R.id.question_editText);
        letterCountTV = (TextView) findViewById(R.id.letter_count);
        sendQuestionBtn = (ImageButton) findViewById(R.id.send_question_action);
        cancelQuestionBtn = (ImageButton) findViewById(R.id.cancel_question_action);

        rightAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation_right);
        leftAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation_left);

        anonymousBtn.setOnClickListener(this);
        anonymousLayout.setOnClickListener(this);

        handleQuestionEditText();
        sendQuestionBtn.setOnClickListener(new SendBtnListener());
        cancelQuestionBtn.setOnClickListener(e -> finish());

    }

    private class SendBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String questionText = questionEdit.getText().toString();

            currentQuestion.setQuestionText(questionText);
            currentQuestion.setAnonymous(hideUser);
            currentQuestion.setQuestionTimestamp(System.currentTimeMillis()/1000);

            sendQuestion(currentQuestion);
            Log.i("AskedQuestion",currentQuestion.getAskerID() + "  "+currentQuestion.getReceiverID() + "  "+currentQuestion.getQuestionText() + "  " + currentQuestion.getAnonymous());

            finish();
        }
    }

    private void handleQuestionEditText() {
        questionEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int maxLetterCount = 300;
                int remainLetterCount = maxLetterCount - count;
                letterCountTV.setText(Integer.toString(remainLetterCount));

                if(remainLetterCount == maxLetterCount)
                {
                    sendQuestionBtn.setClickable(false);
                    sendQuestionBtn.setImageResource(R.drawable.ic_send_black_24dp);
                }
                else {
                    sendQuestionBtn.setClickable(true);
                    sendQuestionBtn.setImageResource(R.drawable.ic_send_white_24dp);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (hideUser) {
            anonymousBtn.startAnimation(leftAnimation);
            anonymousTV.setText(getResources().getString(R.string.ask_by_user));
            anonymousBtn.setImageDrawable(getResources().getDrawable(R.drawable.profile_image));
            hideUser = false;
        } else {
            anonymousBtn.startAnimation(rightAnimation);
            anonymousBtn.setImageDrawable(getResources().getDrawable(R.drawable.anonymous));
            anonymousTV.setText(getResources().getString(R.string.ask_anonymously));
            hideUser = true;
        }
    }

    private void sendQuestion(Question currentQuestion){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_INSERT_QUESTION,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(!obj.getBoolean("error")){

//                            openMainActivity();

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
                params.put("asker_id", currentQuestion.getAskerID());
                params.put("receiver_id", currentQuestion.getReceiverID());
                params.put("question_text", currentQuestion.getQuestionText());
                params.put("anonymous", currentQuestion.getAnonymous()+"");
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

}