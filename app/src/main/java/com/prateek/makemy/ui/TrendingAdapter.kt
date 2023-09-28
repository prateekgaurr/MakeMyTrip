package com.prateek.makemy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.prateek.makemy.R
import com.prateek.makemy.interfaces.TrendingItemClickListener
import com.prateek.makemy.models.ServiceItem
import com.prateek.makemy.models.TrendingItem

class TrendingAdapter(private var dataList: List<TrendingItem>, private val context : Context, private val listener : TrendingItemClickListener) :
    RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_trending, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.textView.text = data.title



        Glide.with(context)
            .load(data.image)
            .transform(CenterCrop())
            .apply(RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms(CenterCrop()))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)

        holder.imageView.setOnClickListener{
            listener.onTrendingItemClicked(data.title)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newDataList: List<TrendingItem>) {
        val diffCallback = YourDataModelDiffCallback(dataList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList = newDataList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.trending_text)
        val imageView: ImageView = itemView.findViewById(R.id.trending_image)
    }

    private class YourDataModelDiffCallback(
        private val oldList: List<TrendingItem>,
        private val newList: List<TrendingItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
