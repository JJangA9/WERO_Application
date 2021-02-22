package com.example.wero_app

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.sql.Date

interface RetrofitService {

    @POST("/user/login")
    fun userLogin(@Body data: LoginData) : Call<LoginResponse>

    @POST("/user/join")
    fun userJoin(@Body data: JoinData) : Call<JoinResponse>
}

data class LoginData(var userEmail: String, var userPwd: String)
data class LoginResponse(var code: Int, var message: String, var userId: Int)
data class JoinData(var kakaoId: String)
data class JoinResponse(var code: Int, var message: String)
data class Diary(var userId: Int, var date: Date, var content: String, var isShared: Int)