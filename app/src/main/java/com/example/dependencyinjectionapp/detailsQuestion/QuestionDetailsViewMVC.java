package com.example.dependencyinjectionapp.detailsQuestion;


import com.example.dependencyinjectionapp.Model.QuestionWithBody;
import com.example.dependencyinjectionapp.questionslist.ObservableViewMvc;

public interface QuestionDetailsViewMVC extends ObservableViewMvc<QuestionDetailsViewMVC.Listener> {
    interface Listener{

    }
    void bindQuestion(QuestionWithBody question);
}
