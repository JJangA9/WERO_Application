package com.example.wero_app

import android.app.Application
import com.kakao.auth.KakaoSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        //kakao SDK 초기화
        KakaoSDK.init(KakaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) {"this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }

    companion object {
        var instance: GlobalApplication? = null
    }
}