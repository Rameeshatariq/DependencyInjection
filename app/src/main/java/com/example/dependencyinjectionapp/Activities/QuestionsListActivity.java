package com.example.dependencyinjectionapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dependencyinjectionapp.Adapter.QuestionsAdapter;
import com.example.dependencyinjectionapp.Constants;
import com.example.dependencyinjectionapp.Interface.OnQuestionClickListener;
import com.example.dependencyinjectionapp.Model.Question;
import com.example.dependencyinjectionapp.Model.QuestionsListResponseSchema;
import com.example.dependencyinjectionapp.Network.StackoverflowApi;
import com.example.dependencyinjectionapp.R;
import com.example.dependencyinjectionapp.Fragment.ServerErrorDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends AppCompatActivity implements
        Callback<QuestionsListResponseSchema>

{

    private RecyclerView mRecyclerView;
    private QuestionsAdapter mQuestionAdapter;

    private StackoverflowApi mStackoverflowApi;

    private Call<QuestionsListResponseSchema> mCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_questions_list);

        // Initializing RecyclerView
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mQuestionAdapter = new QuestionsAdapter(new OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                // 1- we will  create a details Activity
                // 2- getId() will be solved in Question Class
                //    QuestionDetialsActivity.start(QuestionsListActivity.this, question.getId());
            }
        });

        mRecyclerView.setAdapter(mQuestionAdapter);






        /* ***********   Retrofit Configuration  *************************/

        // 1- Dependency of retrofit        X
        // 2- StackOverFlow API   X
        // 3- Question Class  X
        // 4- Configuration of other classes and Interfaces   XX
        // 5- Running the App

        // I will separate every thing
        // UI & Networking...

        // Initiate Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackoverflowApi = retrofit.create(StackoverflowApi.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mCall = mStackoverflowApi.lastActiveQuestions(20);
        mCall.enqueue(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mCall !=null){
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;
        if(response.isSuccessful() && (responseSchema = response.body()) != null){
            mQuestionAdapter.bindData(responseSchema.getQuestions());
        }else{
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }


}