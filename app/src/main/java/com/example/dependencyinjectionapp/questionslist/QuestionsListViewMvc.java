package com.example.dependencyinjectionapp.questionslist;

import com.example.dependencyinjectionapp.questions.Question;

import java.util.List;


public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {
    interface Listener {
        void onQuestionClicked(Question question);
    }
    void bindQuestions(List<Question> questions);
}
