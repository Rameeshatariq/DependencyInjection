package com.example.dependencyinjectionapp.detailsQuestion;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dependencyinjectionapp.Model.QuestionWithBody;
import com.example.dependencyinjectionapp.R;
import com.example.dependencyinjectionapp.questionslist.BaseViewMVC;


public class QuestionDetailsViewMvcImpl extends BaseViewMVC<QuestionDetailsViewMVC.Listener>
        implements QuestionDetailsViewMVC {

    private final TextView mTxtQuestionBody;

    public QuestionDetailsViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.activity_question_detials, container, false));
        mTxtQuestionBody = findViewById(R.id.txt_question_body);
    }

    @Override
    public void bindQuestion(QuestionWithBody question) {
        String questionBody = question.getmBody();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
    }
}