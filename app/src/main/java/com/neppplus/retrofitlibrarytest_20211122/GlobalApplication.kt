package com.neppplus.retrofitlibrarytest_20211122

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "60444385e0dd4b77d65180784d20063f")

    }

}