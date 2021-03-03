package com.example.wero_app

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("/user/login")
    fun userLogin(@Body data: LoginData) : Call<LoginResponse>

    @POST("/user/join")
    fun userJoin(@Body data: JoinData) : Call<JoinResponse>

    @POST("/diary/save")
    fun saveDiary(@Body data: DiaryData) : Call<DiaryResponse>

    @POST("/post")
    fun sendPost(@Body data: DiaryData) : Call<DiaryResponse>

    @POST("/post/reply")
    fun sendReply(@Body data: ReplyData) : Call<ReplyResponse>

    @GET("/diary/list")
    fun getDiaryList(@Query("userId") userId: String, @Query("date") date: String) : Call<DiaryListResponse>

    @GET("/post/list")
    fun getPostList(@Query("userId") userId: String) : Call<PostListResponse>

    @DELETE("/post")
    fun deletePost(@Query("diaryId") diaryId: Int, @Query("userToId") userToId: String) : Call<ServerResponse>
}

data class LoginData(var userEmail: String, var userPwd: String)
data class LoginResponse(var code: Int, var message: String, var userId: Int)

data class JoinData(var kakaoId: String)
data class JoinResponse(var code: Int, var message: String)

data class DiaryData(var userId: String?, var date: String, var content: String?, var isShared: Int)
data class DiaryResponse(var code: Int, var message: String)

data class ReplyData(var userId: String?, var diaryId: Int, var replyDate: String, var content: String?)
data class ReplyResponse(var code: Int, var message: String)

data class DiaryListResponse(var result: JsonArray)
data class PostListResponse(var result: JsonArray)

data class ServerResponse(var code: Int, var message: String)