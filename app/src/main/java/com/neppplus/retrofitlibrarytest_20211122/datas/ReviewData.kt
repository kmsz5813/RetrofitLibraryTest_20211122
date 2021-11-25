package com.neppplus.retrofitlibrarytest_20211122.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReviewData(

    var title: String,
    var content : String,
    var score: Int,
    @SerializedName("user_id")
    var userId : Int,
    @SerializedName("product_id")
    var productId : Int

) : Serializable {



}