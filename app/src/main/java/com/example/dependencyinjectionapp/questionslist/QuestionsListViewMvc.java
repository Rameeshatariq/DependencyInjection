package com.example.dependencyinjectionapp.questionslist;

import com.example.dependencyinjectionapp.Model.Question;

import java.util.List;
import java.util.Observable;



public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {
    interface Listener {
        void onQuestionClicked(Question question);
    }
    void bindQuestions(List<Question> questions);
}
