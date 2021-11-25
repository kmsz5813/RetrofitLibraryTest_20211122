package com.neppplus.retrofitlibrarytest_20211122.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.retrofitlibrarytest_20211122.R
import com.neppplus.retrofitlibrarytest_20211122.datas.ReviewData
import java.text.SimpleDateFormat

class ReviewRecyclerAdapter(
    val mContext: Context,
    val mList: List<ReviewData>) : RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtReviewTitle = itemView.findViewById<TextView>(R.id.txtReviewTitle)
        val txtReviewContent = itemView.findViewById<TextView>(R.id.txtReviewContent)
        val txtScore = itemView.findViewById<TextView>(R.id.txtScore)
        val txtUserNickname = itemView.findViewById<TextView>(R.id.txtUserNickname)
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        val txtCreateAt = itemView.findViewById<TextView>(R.id.txtCreateAt)

        fun bind( data: ReviewData ){

            txtReviewTitle.text = data.title
            txtReviewContent.text = data.content

            txtScore.text = data.score.toString()
            txtUserNickname.text = data.user.nickname
            txtProductName.text = data.product.name

            val sdf = SimpleDateFormat("yyyy.M.d a h:mm")

            txtCreateAt.text = sdf.format( data.createdAt )


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.review_list_item, parent, false)
        return ReviewViewHolder(row)

    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

        holder.bind( mList[position] )

    }

    override fun getItemCount() = mList.size

}