package com.example.wero_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException


class Login : AppCompatActivity() {
    private var callback: SessionCallback = SessionCallback()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val btn_login = findViewById<Button>(R.id.btn_login)
        btn_login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btn_register = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener {
            val registerIntent = Intent(this, Register::class.java)
            startActivity(registerIntent)
        }

        Session.getCurrentSession().addCallback(callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private inner class SessionCallback: ISessionCallback {
        override fun onSessionOpened() {
            //로그인 세션 열렸을 때
            UserManagement.getInstance().me( object : MeV2ResponseCallback() {
                override fun onSuccess(result: MeV2Response?) {
                    //로그인 성공했을 때
                    var intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    //로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    Toast.makeText(this@Login, "로그인 도중 오류가 발생했습니다", Toast.LENGTH_SHORT).show()
                }
            })
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            //로그인 세션이 정상적으로 열리지 않았을 때
            if(exception != null) {
                com.kakao.util.helper.log.Logger.e(exception)
                Toast.makeText(this@Login, "로그인 도중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
