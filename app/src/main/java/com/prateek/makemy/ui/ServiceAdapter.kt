package com.prateek.makemy.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.prateek.makemy.R
import com.prateek.makemy.interfaces.ServiceItemClickListener
import com.prateek.makemy.models.ServiceItem

class ServiceAdapter(private var dataList: List<ServiceItem>, private val context : Context, private val listener : ServiceItemClickListener) :
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.textView.text = data.serviceName

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()

        Glide.with(context)
            .load(data.image)
            .apply(requestOptions)
            .into(holder.imageView)

        holder.imageView.setOnClickListener{
            listener.onServiceItemClicked(data.serviceName)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newDataList: List<ServiceItem>) {
        Log.d("Adapter", "Updating")
        dataList = newDataList
        notifyDataSetChanged()
        val diffCallback = YourDataModelDiffCallback(dataList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        Log.d("Adapter", "Updated")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_service_name)
        val imageView: ImageView = itemView.findViewById(R.id.image_service)
    }

    private class YourDataModelDiffCallback(
        private val oldList: List<ServiceItem>,
        private val newList: List<ServiceItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].serviceName == newList[newItemPosition].serviceName
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
