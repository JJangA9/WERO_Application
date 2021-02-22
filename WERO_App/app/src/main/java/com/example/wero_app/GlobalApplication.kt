package com.example.wero_app

import android.app.Application
import com.kakao.auth.KakaoSDK
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        //kakao SDK 초기화
        KakaoSdk.init(this, "ade08056f1c3a9fd0f215520fd377866")
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