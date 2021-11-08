package com.example.dependencyinjectionapp.questions;

import androidx.annotation.Nullable;

import com.example.dependencyinjectionapp.Model.SingleQuestionResponseSchema;
import com.example.dependencyinjectionapp.Network.StackoverflowApi;
import com.example.dependencyinjectionapp.questionslist.BaseObservable;

import java.util.Stack;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    private final StackoverflowApi mStackoverflowApi;

    public interface Listener {
        void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question);
        void onFetchOfQuestionDetailsFailed();
    }



    @Nullable
    Call<SingleQuestionResponseSchema> call;

    public FetchQuestionDetailsUseCase(StackoverflowApi stackoverflowApi){


        mStackoverflowApi = stackoverflowApi;

    }

    public void fetchQuestionDetailsAndNotify(String questionId){
        cancelCurrentFetchIfActive();

        call = mStackoverflowApi.questionDetails(questionId);
        call.enqueue(new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {

                if (response.isSuccessful()){
                    notifySucceeded(response.body().getQuestions());
                }else{
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
                if (call != null && !call.isCanceled() && !call.isExecuted()){
                    call.cancel();
                }
            }


        });
    }
    private void cancelCurrentFetchIfActive(){
        if(call != null && !call.isCanceled() && !call.isExecuted()){
            call.cancel();
        }
    }

    private void notifySucceeded(QuestionWithBody question){
        for (Listener listener : getListeners()){
            listener.onFetchOfQuestionDetailsSucceeded(question);
        }
    }

    private void notifyFailed(){
        for (Listener listener : getListeners()){
            listener.onFetchOfQuestionDetailsFailed();
        }
    }

}
