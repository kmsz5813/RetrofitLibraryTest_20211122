package com.neppplus.retrofitlibrarytest_20211122

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.retrofitlibrarytest_20211122.databinding.ActivityEditReviewBinding
import com.neppplus.retrofitlibrarytest_20211122.datas.BasicResponse
import com.neppplus.retrofitlibrarytest_20211122.datas.ProductData
import com.neppplus.retrofitlibrarytest_20211122.utils.GlobalData
import com.neppplus.retrofitlibrarytest_20211122.utils.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EditReviewActivity : BaseActivity() {

    lateinit var binding : ActivityEditReviewBinding

    lateinit var mProductData : ProductData

    val mInputTagList = ArrayList<String>()

//    대표 사진 가지러 간다고 메모
    val REQ_FOR_THUMBNAIL = 1004

//    선택한 이미지의 uri를 담아줄 변수
    var mSelectedThumbnailUri : Uri? = null // 선택한 이미지는 처음에는 없다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_review)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        val ocl = View.OnClickListener{

//            권한 체크. (갤러리 조회 가능?)

            val pl = object : PermissionListener {
                override fun onPermissionGranted() {

//                    권한 있을때? 리뷰 대표 사진을 가지러 이동.
                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_PICK
                    myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    startActivityForResult( myIntent, REQ_FOR_THUMBNAIL )

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "갤러리 조회 권한 없음", Toast.LENGTH_SHORT).show()

                }

            }

            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()

        }

        binding.txtEmptyImg.setOnClickListener(ocl)

//        한글자 입력할때마다 -> 스페이스를 넣었는지 검사.

        binding.edtTag.addTextChangedListener {

            val nowText = it.toString()

            if (nowText == "") {
//                빈칸일때는 밑의 코드 실행X
                return@addTextChangedListener
            }

            Log.d("입력값", nowText)

//            지금 입력된 내용의 마지막 글자(char)가 ' ' 글자인가?

           if (nowText.last() == ' ') {
               Log.d("입력값", "스페이스가 들어옴")

//               입력된값을 태그로 등록

//               태그로 등록될문구 => " " 공백 제거
               val tag = nowText.replace(" ", "")

//               태그목록으로 추가해보자.
               mInputTagList.add(tag)

//               태그 목록 보여줄 레이아웃에 xml을 끌어오기 -> 그 내부의 텍스트뷰에 문구변경

               val tagBox = LayoutInflater.from(mContext).inflate(R.layout.tag_list_item, null)
               val txtTag = tagBox.findViewById<TextView>(R.id.txtTag)
               txtTag.text = "#${tag}"

               binding.tagListLayout.addView(tagBox)

//               입력값 초기화
               binding.edtTag.setText("")

           }


        }

        binding.btbWrite.setOnClickListener {



            val inputTitle = binding.edtReviewTile.text.toString()
            val inputContent = binding.edtContent.text.toString()

//            몇점 입력?
            val rating = binding.ratingBar.rating.toInt()
            Log.d("평점 점수", rating.toString())

//            태그 임시 : ""
            val tagStr = ""

//            선택한 사진 첨부

//            선택한 사진? 추출 => mSelectedThumbnailUri 에 담겨있다.
//            mSelectedThumbnailUri 가 null? 아직 첨부 X.

            if (mSelectedThumbnailUri == null){
                Toast.makeText(mContext, "대표 이미지를 첨부해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            리뷰 작성 : 2가지 데이터 (Multopart) 보내자.

//            1. 일반 파라미터들 (id / 제목 /내용/점수 등등)



//            2. 이미지 등 파일 파라미터

            val productIdBody = RequestBody.create(MediaType.parse("text/plain"), mProductData.id.toString() )
            val titleBody = RequestBody.create(MediaType.parse("text/plain"), inputTitle )
            val contentBody = RequestBody.create(MediaType.parse("text/plain"), inputContent )
            val scoreBody = RequestBody.create(MediaType.parse("text/plain"), rating.toString() )
            val tagListBody = RequestBody.create(MediaType.parse("text/plain"), tagStr )

            val params = HashMap<String, RequestBody>()
            params.put("product_id", productIdBody)
            params.put("title", titleBody)
            params.put("content", contentBody)
            params.put("scor", scoreBody)
            params.put("tag_list", tagListBody)

//            썸네일그림 데이터 첨부

//            보내줄 그림파일 꺼내오기
            val file = File( URIPathHelper().getPath(mContext, mSelectedThumbnailUri!!) )
//            그림파일 -> 첨부할 수 있는 형태로 가공
            val fileReqBody = RequestBody.create( MediaType.parse("image/*"),file )

//            어느 이름표로 보낼지
            val thumbnailImageBody = MultipartBody.Part.createFormData("thumbnail_img", "thumbnail.jpg", fileReqBody)

            apiService.postRequestReview(
                params,
                thumbnailImageBody
//                2. 이미지 등 파일 : Multopart.part
            ).enqueue(object : Callback<BasicResponse>{
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_FOR_THUMBNAIL){

            if (resultCode == RESULT_OK){

//                첨부한 이미지 (data.data) Uri 를 저장 -> 업로드 시에 첨부.
                mSelectedThumbnailUri = data!!.data

//                선택한 이미지를 이미지뷰에 표시
                Glide.with(mContext).load(mSelectedThumbnailUri).into(binding.imgSeleceted)

//                선택 유도 문구 숨김 / 선택한 이미지뷰 표시
                binding.txtEmptyImg.visibility = View.GONE
                binding.imgSeleceted.visibility = View.VISIBLE

            }
        }

    }

}