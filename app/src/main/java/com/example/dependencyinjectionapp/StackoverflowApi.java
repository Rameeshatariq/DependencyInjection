package com.example.dependencyinjectionapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackoverflowApi {

    @GET("/questions?order=desc&sort=activity&site=stackoverflow")
    Call<QuestionsListResponseSchema> lastActiveQuestions(
            @Query("pagesize") Integer pageSize
    );


}
