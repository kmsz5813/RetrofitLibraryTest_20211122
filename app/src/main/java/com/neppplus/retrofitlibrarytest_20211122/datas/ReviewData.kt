package com.neppplus.retrofitlibrarytest_20211122.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ReviewData(

    var id : Int,
    var title: String,
    var content : String,
    var score: Double,
    @SerializedName("created_at")
    var createdAt : Date,
    var user: UserData,
    var product : ProductData


) : Serializable {



}