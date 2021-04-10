package com.example.wero_app

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("/user/join")
    fun userJoin(@Body data: JoinData) : Call<ServerResponse>

    @POST("/diary/save")
    fun saveDiary(@Body data: DiaryData) : Call<ServerResponse>

    @POST("/post")
    fun sendPost(@Body data: DiaryData) : Call<ServerResponse>

    @POST("/post/reply")
    fun sendReply(@Body data: ReplyData) : Call<ServerResponse>


    @POST("/reply")
    fun shareReply(@Body data: ReplyIdData) : Call<ServerResponse>

    @PUT("/diary")
    fun updateDiary(@Body data: DiaryItem) : Call<ServerResponse>

    @PUT("/reply/check")
    fun checkReply(@Body data: DiaryIdData) : Call<ServerResponse>


    @GET("/diary")
    fun getDiary(@Query("diaryId") diaryId: Int) : Call<JsonArrayResponse>

    @GET("/reply")
    fun getReply(@Query("diaryId") diaryId: Int) : Call<JsonArrayResponse>

    @GET("/diary/list")
    fun getDiaryList(@Query("userId") userId: String, @Query("date") date: String) : Call<JsonArrayResponse>

    @GET("/post/list")
    fun getPostList(@Query("userId") userId: String) : Call<JsonArrayResponse>

    @GET("/reply/list")
    fun getReplyList(@Query("userId") userId: String) : Call<JsonArrayResponse>

    @GET("/wero/list")
    fun getWeroList() : Call<JsonArrayResponse>

    @GET("/name")
    fun getName(@Query("userId") userId: String) : Call<JsonArrayResponse>


    @DELETE("/post")
    fun deletePost(@Query("diaryId") diaryId: Int, @Query("userToId") userToId: String) : Call<ServerResponse>

    @DELETE("/diary")
    fun deleteDiary(@Query("diaryId") diaryId: Int) : Call<ServerResponse>

    @DELETE("/reply")
    fun deleteReply(@Query("replyId") replyId: Int) : Call<ServerResponse>


}

data class JoinData(var kakaoId: String)
data class DiaryData(var userId: String?, var date: String, var content: String?, var isShared: Int?)
data class ReplyData(var diaryId: Int, var userFromId: String?, val userToId: String, var replyDate: String, var content: String?)
data class ReplyIdData(var replyId: Int)
data class DiaryIdData(var diaryId: Int)

data class JsonArrayResponse(var result: JsonArray)
data class ServerResponse(var code: Int, var message: String)

data class DiaryItem(var diaryId: Int, var userId: String?, var diaryDate: String, var content: String, var isShared: Int?)
data class ReplyItem(val replyId: Int, val diaryId: Int, val userFromId: String,
                     val userToId: String?, val replyDate: String, val content: String?, val reply: String)
data class PostItem(val diaryId: Int, val userFromId: String, val userToId: String, val userName: String, val diaryDate: String?, val content: String?, val isShared: Int?)
data class WeroItem(val replyId: Int, val userId: String, val userName: String?, val content: String, val heart: Int)

