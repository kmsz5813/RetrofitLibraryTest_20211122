package com.neppplus.retrofitlibrarytest_20211122.fragments

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.retrofitlibrarytest_20211122.R
import com.neppplus.retrofitlibrarytest_20211122.databinding.FragmentRecyclerviewPracticeBinding
import com.neppplus.retrofitlibrarytest_20211122.datas.BasicResponse
import com.neppplus.retrofitlibrarytest_20211122.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewParacticeFargment : BaseFragment() {

    lateinit var binding: FragmentRecyclerviewPracticeBinding

    val mReviewList = ArrayList<ReviewData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_recyclerview_practice,container,false )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        getReviewListFromSever()
    }

    fun getReviewListFromSever(){

        apiService.getRequestReview().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful){

                    val br = response.body()!!

                    mReviewList.clear()
                    mReviewList.addAll(br.data.reviews)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }
}