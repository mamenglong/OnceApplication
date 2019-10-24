package com.mml.onceapplication.http.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mml.onceapplication.R
import com.mml.onceapplication.http.data.Data

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-10-24 上午11:58
 * Description: This is Adapter
 * Package: com.mml.onceapplication.http.adapter
 * Project: OnceApplication
 */
class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

class CAdapter: RecyclerView.Adapter<ViewHolder>() {
    var listData= mutableListOf<Data>()
    var onConvert:((view:View,pos:Int)->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          val view=LayoutInflater.from(parent.context).inflate(R.layout.item_activity_coroutines,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onConvert?.invoke(holder.itemView,position)
    }

}