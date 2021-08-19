package com.section11.components.recycler.listener

import com.section11.components.recycler.model.ViewHolderModel

interface RecyclerItemClickListener {

    fun onRecyclerItemClicked(model: ViewHolderModel)
}