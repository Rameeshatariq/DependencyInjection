package com.example.dependencyinjectionapp.dependencyInjection;

import androidx.annotation.UiThread;


import com.example.dependencyinjectionapp.Constants;
import com.example.dependencyinjectionapp.Network.StackoverflowApi;
import com.example.dependencyinjectionapp.questions.FetchQuestionDetailsUseCase;
import com.example.dependencyinjectionapp.questions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@UiThread
public class CompositionRoot {

    private Retrofit retrofit;
    private StackoverflowApi stackoverflowApi;

    @UiThread
    private Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @UiThread
    private StackoverflowApi getStackoverflowApi(){
        if (stackoverflowApi == null){
            stackoverflowApi = getRetrofit().create(StackoverflowApi.class);
        }
        return stackoverflowApi;
    }

    @UiThread
    public FetchQuestionDetailsUseCase getfetchQuestionDetailsUseCase(){
        return new FetchQuestionDetailsUseCase(getStackoverflowApi());
    }

    @UiThread
    public FetchQuestionsListUseCase getfetchQuestionsListUseCase(){
        return new FetchQuestionsListUseCase(getStackoverflowApi());
    }
}
