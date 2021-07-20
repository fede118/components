package com.section11.components.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.section11.components.databinding.ViewHolderLayoutBinding
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel

class RecyclerAdapter(private var modelList: List<ViewHolderModel>)
    : RecyclerView.Adapter<RecyclerViewHolder>() {

    var listener: RecyclerItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ViewHolderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val viewHolderModel = modelList[position]
        holder.bind(viewHolderModel, listener)
    }

    fun updateItemsList(newModelList: List<ViewHolderModel>) {
        modelList = newModelList
        notifyDataSetChanged()
    }
}
