package com.example.progexam

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Rickandmortyapi {
    @GET("character")
    fun getAllCharacters(): Observable<Response>

    @GET("character/{id}")
    fun getSingleChar(@Path("id") id: Long? ): Single<Character>
}