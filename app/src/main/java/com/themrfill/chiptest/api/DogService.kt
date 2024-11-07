package com.themrfill.chiptest.api

import com.themrfill.chiptest.model.Breeds
import com.themrfill.chiptest.model.Images
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogService {
    @GET("breeds/list/all")
    fun getBreeds(): Call<Breeds>

    @GET("breed/{breed}/images")
    fun getBreedImages(@Path("breed") breed: String): Call<Images>

    @GET("breed/{breed}/{sub}/images")
    fun getSubBreedImages(@Path("breed") breed: String, @Path("sub") sub: String): Call<Images>
}