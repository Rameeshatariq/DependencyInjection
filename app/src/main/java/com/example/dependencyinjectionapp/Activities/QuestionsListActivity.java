package com.example.dependencyinjectionapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.dependencyinjectionapp.Fragment.ServerErrorDialogFragment;
import com.example.dependencyinjectionapp.Interface.DialogsManager;
import com.example.dependencyinjectionapp.MyApplication;
import com.example.dependencyinjectionapp.questions.FetchQuestionsListUseCase;
import com.example.dependencyinjectionapp.questions.Question;
import com.example.dependencyinjectionapp.questionslist.QuestionsListViewMVCImpl;
import com.example.dependencyinjectionapp.questionslist.QuestionsListViewMvc;

import java.util.List;
import java.util.Stack;


import retrofit2.Retrofit;

public class QuestionsListActivity extends AppCompatActivity implements
        QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener
{
    private static final int NUM_OF_QUESTIONS_TO_FETCH  = 20;
    private FetchQuestionsListUseCase fetchQuestionsListUseCase;
    private QuestionsListViewMvc mViewMVC;

    // Dialog Fragments
    private DialogsManager mDialogsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this), null);

        setContentView(mViewMVC.getRootView());

        // Networking

        fetchQuestionsListUseCase = ((MyApplication) getApplication()).getCompositionRoot().getfetchQuestionsListUseCase();



        // dialog mangaer
        mDialogsManager = new DialogsManager(getSupportFragmentManager());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        fetchQuestionsListUseCase.registerListener(this);

        fetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);


    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);

        fetchQuestionsListUseCase.unregisterListener(this);
    }


    @Override
    public void onFetchOfQuestionsSucceeded(List<Question> questions) {
        mViewMVC.bindQuestions(questions);


    }

    @Override
    public void onFetchOfQuestionsFailed() {

        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");

    }


    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetialsActivity.start(QuestionsListActivity.this, question.getId());

    }
}