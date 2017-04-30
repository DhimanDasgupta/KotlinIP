package com.dhimandasgupta.kotlinip

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by dhimandasgupta on 30/04/17.
 */
interface ApiService {
    @GET("/ip") fun ip() : Observable<Ip>
}