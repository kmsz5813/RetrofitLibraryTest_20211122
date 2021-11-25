package com.neppplus.retrofitlibrarytest_20211122

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.neppplus.retrofitlibrarytest_20211122.databinding.ActivityEditReviewBinding
import com.neppplus.retrofitlibrarytest_20211122.datas.BasicResponse
import com.neppplus.retrofitlibrarytest_20211122.datas.ProductData
import com.neppplus.retrofitlibrarytest_20211122.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditReviewActivity : BaseActivity() {

    lateinit var binding : ActivityEditReviewBinding

    lateinit var mProductData : ProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_review)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btbWrite.setOnClickListener {

            val inputTitle = binding.edtReviewTile.text.toString()
            val inputContent = binding.edtContent.text.toString()

//            몇점 입력?
            val rating = binding.ratingBar.rating.toInt()
            Log.d("평점 점수", rating.toString())

            apiService.postRequestReview(mProductData.id, inputTitle, inputContent, rating).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

    override fun setValues() {

        mProductData = intent.getSerializableExtra("product") as ProductData

        binding.txtProductName.text = mProductData.name
        binding.txtUserNickname.text = GlobalData.loginUser!!.nickname

//        오늘 날짜 -> 2021.11.25 형태로 가공 -> 텍스트뷰에 반영

//        1. 오늘 날짜?
        val now = Calendar.getInstance() // 현재 일시 자동 기록

//        원하는 형태로 가공 (String 생성)
        val sdf = SimpleDateFormat("yyyy.M.d")
        val nowString = sdf.format( now.time)

        binding.txtToday.text = nowString

    }
}