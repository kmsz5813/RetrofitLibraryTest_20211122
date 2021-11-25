package com.neppplus.retrofitlibrarytest_20211122.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.neppplus.retrofitlibrarytest_20211122.R
import com.neppplus.retrofitlibrarytest_20211122.databinding.FragmentMyProfileBinding
import com.neppplus.retrofitlibrarytest_20211122.datas.BasicResponse
import com.neppplus.retrofitlibrarytest_20211122.utils.ContextUtil
import com.neppplus.retrofitlibrarytest_20211122.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileFragment : BaseFragment() {

    lateinit var binding: FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_my_profile, container, false)
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

        getMyInfoFromServer()

        binding.txtNickname.text = GlobalData.loginUser!!.nickname
        Glide.with(mContext).load(GlobalData.loginUser!!.profileImageURL).into(binding.imgProfile)

    }

    fun getMyInfoFromServer() {

        apiService.getRequestMyInfo().enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    binding.txtNickname.text = br.data.user.nickname
                    Glide.with(mContext).load(br.data.user.profileImageURL).into(binding.imgProfile)

                    when(GlobalData.loginUser!!.provider){
                        "facebook" -> {
                            binding.imgProvider.setImageResource(R.drawable.facebook_logo)
                            binding.imgProvider.visibility
                        }
                        "kakao" -> {
                            binding.imgProvider.setImageResource(R.drawable.kakao_logo)
                            binding.imgProvider.visibility
                        }
                        else -> {
                            binding.imgProvider.visibility = View.GONE
                        }

                    }


                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}