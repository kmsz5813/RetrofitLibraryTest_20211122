package com.neppplus.retrofitlibrarytest_20211122.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.NumberFormat
import java.util.*

class ProductData(

    var id: Int,
    var name: String,
    var price: Int,
    @SerializedName("image_url")
    var imageURL : String,


    var store: StoreData,
    @SerializedName("small_category")
    var smallCategory : SmallCategoryData


) : Serializable {

    fun getFormattedPrice() : String {

        return  "${ NumberFormat.getInstance(Locale.KOREA).format(this.price) } 원"

    }

}