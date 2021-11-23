package com.neppplus.retrofitlibrarytest_20211122.api

import com.neppplus.retrofitlibrarytest_20211122.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIService {

//    로그인 기능 명세

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email:String,
        @Field("password") pw: String,
    ) : Call<BasicResponse>

//    회원가입 기능

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nick: String,
    ) : Call<BasicResponse>

//    중복 확인 기능  - GET
    @GET("/user/check")
    fun getRequestDuplicatedCheck(
        @Query("type") type: String,
        @Query("value")  value: String,
    ) : Call<BasicResponse>


}