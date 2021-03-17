package com.example.myunittestpracticeapp.data.remote


import com.example.myunittestpracticeapp.BuildConfig
import com.example.myunittestpracticeapp.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("/api/")
    suspend fun searchForImage(

        @Query("q") searchByQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY

    ) : Response<ImageResponse>
}