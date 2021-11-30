package com.neppplus.retrofitlibrarytest_20211122.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.retrofitlibrarytest_20211122.R
import com.neppplus.retrofitlibrarytest_20211122.datas.ReviewData

class MainRecyclerAdapter(val mContext: Context, val mList : List<ReviewData>) : RecyclerView.Adapter< RecyclerView.ViewHolder >() {

//    2가지 viewholder 필요함. => 0번칸 : 상단부(Header) xml / 나머지(item)칸 : 리뷰모양 xml

    inner class HeaderViewHolder(row : View) : RecyclerView.ViewHolder(row){


    }

    inner class ItemViewHolder(row: View) : RecyclerView.ViewHolder(row){


    }

    val HEADER_VIEW_TYPE = 1000
    val REVIEW_ITEM_TYPE = 1001

    override fun getItemViewType(position: Int): Int {

//        position 0 : 맨 윗칸 => 상단 뷰
//        position 그 외 : 목록 표시. => 리뷰 아이템

        return when(position){

            0 -> HEADER_VIEW_TYPE
            else -> REVIEW_ITEM_TYPE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType){
            HEADER_VIEW_TYPE -> {
                val row = LayoutInflater.from(mContext).inflate(R.layout.main_recycler_item_top_view, parent,false)
                HeaderViewHolder(row)
            }
            else -> {
//                리뷰 아이템
                val row = LayoutInflater.from(mContext).inflate(R.layout.main_recycler_item_review_item,parent,false)
                ItemViewHolder(row)

            }

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size + 1

}