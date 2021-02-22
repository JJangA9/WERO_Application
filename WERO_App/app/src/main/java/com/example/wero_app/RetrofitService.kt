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

    @POST("/diary/save")
    fun saveDiary(@Body data: DiaryData) : Call<DiaryResponse>
}

data class LoginData(var userEmail: String, var userPwd: String)
data class LoginResponse(var code: Int, var message: String, var userId: Int)
data class JoinData(var kakaoId: String)
data class JoinResponse(var code: Int, var message: String)
data class DiaryData(var kakaoId: String?, var date: String, var content: String?, var isShared: Int)
data class DiaryResponse(var code: Int, var message: String)