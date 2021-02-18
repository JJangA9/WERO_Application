package com.example.wero_app

import com.kakao.auth.*

class KakaoSDKAdapter : KakaoAdapter() {
    override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_ACCOUNT)
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
                // SDK 로그인시 사용되는 WebView 에서
                // pause 와 resume 시에 Timer 를 설정하여 CPU소모를 절약한다.
                // true 를 리턴할경우 webview 로그인을 사용하는 화면에서
                // 모든 webview 에 onPause 와 onResume 시에 Timer 를 설정해 주어야 한다.
                // 지정하지 않을 시 false 로 설정된다.
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isSaveFormData(): Boolean {
                return true
                // Kakao SDK 에서 사용되는 WebView에서
                // email 입력폼의 데이터를 저장할지 여부를 결정한다.
                // true일 경우, 다음번에 다시 로그인 시 email 폼을 누르면
                // 이전에 입력했던 이메일이 나타난다.
            }

            override fun isSecureMode(): Boolean {
                return true
                // 로그인시 access token과 refresh token을 저장할 때의 암호화 여부를 결정한다.
            }
        }
    }

    override fun getApplicationConfig(): IApplicationConfig {
        return IApplicationConfig {
            GlobalApplication.instance?.getGlobalApplicationContext()
        }
    }
}