package com.elsawy.ahmed.sqlaskproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.elsawy.ahmed.sqlaskproject.R;
import com.elsawy.ahmed.sqlaskproject.RequestHandler;
import com.elsawy.ahmed.sqlaskproject.Utils.Constants;
import com.elsawy.ahmed.sqlaskproject.models.Answer;
import com.elsawy.ahmed.sqlaskproject.models.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AnswerTheQuestionsActivity extends AppCompatActivity {

    private TextView questionTV;
    private EditText answer_edit;
    private ImageButton sendAnswerImage;
    private ImageView cancelAnswerBtn;

    private Answer currentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_the_questions);

        Question currentQuestion = (Question) getIntent().getParcelableExtra("questionInfo");
        this.currentAnswer = new Answer(currentQuestion);

        questionTV = (TextView) findViewById(R.id.question_txt_answer_activity);
        answer_edit = (EditText) findViewById(R.id.answer_editText);
        sendAnswerImage = (ImageButton) findViewById(R.id.answer_layout_send_action);
        cancelAnswerBtn = (ImageView) findViewById(R.id.answer_layout_cancel_action);

        cancelAnswerBtn.setOnClickListener(e -> finish());
        questionTV.setText(currentAnswer.getQuestion().getQuestionText());
        sendAnswerImage.setOnClickListener(new SendAnswerListener());

        handleAnswerEditText();
    }

    private class SendAnswerListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String answerText = answer_edit.getText().toString();

            currentAnswer.setAnswerText(answerText);
            sendAnswer(currentAnswer);
            finish();
        }
    }

    private void sendAnswer(Answer currentAnswer){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_HANDLE_ANSWER,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Log.i("answerQuestion",response);

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
                params.put("answer_text", currentAnswer.getAnswerText());
                params.put("question_text", currentAnswer.getQuestion().getQuestionText());
                params.put("anonymous", String.valueOf(currentAnswer.getQuestion().getAnonymous()));
                params.put("asker_id", currentAnswer.getQuestion().getAskerID());
                params.put("receiver_id", currentAnswer.getQuestion().getReceiverID());
                params.put("question_id", currentAnswer.getQuestion().getQuestionId());
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void handleAnswerEditText() {
        answer_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if(count == 0)
                {
                    sendAnswerImage.setClickable(false);
                    sendAnswerImage.setImageResource(R.drawable.ic_send_black_24dp);
                }
                else {
                    sendAnswerImage.setClickable(true);
                    sendAnswerImage.setImageResource(R.drawable.ic_send_white_24dp);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}