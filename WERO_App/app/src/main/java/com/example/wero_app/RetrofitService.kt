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
    fun saveDiary(@Body data: DiaryData) : Call<ServerResponse>

    @POST("/post")
    fun sendPost(@Body data: DiaryData) : Call<ServerResponse>

    @POST("/post/reply")
    fun sendReply(@Body data: ReplyData) : Call<ReplyResponse>

    @GET("/diary")
    fun getDiary(@Query("diaryId") diaryId: Int) : Call<DiaryResponse>

    @GET("/reply")
    fun getReply(@Query("diaryId") diaryId: Int) : Call<DiaryResponse>

    @GET("/diary/list")
    fun getDiaryList(@Query("userId") userId: String, @Query("date") date: String) : Call<DiaryListResponse>

    @GET("/post/list")
    fun getPostList(@Query("userId") userId: String) : Call<PostListResponse>

    @GET("/reply/list")
    fun getReplyList(@Query("userId") userId: String) : Call<ReplyListResponse>

    @DELETE("/post")
    fun deletePost(@Query("diaryId") diaryId: Int, @Query("userToId") userToId: String) : Call<ServerResponse>

}

data class LoginData(var userEmail: String, var userPwd: String)
data class LoginResponse(var code: Int, var message: String, var userId: Int)

data class JoinData(var kakaoId: String)
data class JoinResponse(var code: Int, var message: String)

data class DiaryData(var userId: String?, var date: String, var content: String?, var isShared: Int)

data class ReplyData(var diaryId: Int, var userFromId: String?, val userToId: String, var replyDate: String, var content: String?)
data class ReplyResponse(var code: Int, var message: String)

data class DiaryListResponse(var result: JsonArray)
data class PostListResponse(var result: JsonArray)
data class ReplyListResponse(var result: JsonArray)
data class DiaryResponse(var result: JsonArray)

data class ServerResponse(var code: Int, var message: String)

data class DiaryItem(var diaryId: Int, var userId: String, var diaryDate: String, var content: String, var isShared: Int)
data class ReplyItem(val replyId: Int, val diaryId: Int, val userFromId: String,
                     val userToId: String?, val replyDate: String, val content: String?, val reply: String)
data class PostItem(val diaryId: Int, val userFromId: String, val userToId: String, val diaryDate: String?, val content: String?, val isShared: Int?)

