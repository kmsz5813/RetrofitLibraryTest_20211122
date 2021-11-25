package com.neppplus.retrofitlibrarytest_20211122

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neppplus.retrofitlibrarytest_20211122.api.ServerAPI
import com.neppplus.retrofitlibrarytest_20211122.api.ServerAPIService

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var apiService : ServerAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        val retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIService::class.java)

    }

    abstract fun setupEvents()
    abstract fun setValues()

}