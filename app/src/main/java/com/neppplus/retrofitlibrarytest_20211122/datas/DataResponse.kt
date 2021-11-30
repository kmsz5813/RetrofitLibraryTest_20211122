package com.neppplus.retrofitlibrarytest_20211122.datas

class DataResponse(
    var user: UserData,
    var token: String,

//    이 변수는 상품 목록에서만 사용.
    var products: List<ProductData>,

    var categories: List< SmallCategoryData >,

    var reviews : List<ReviewData>,

    var banners : List<BannerData>

) {
}