package com.section11.components.recycler.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.section11.components.R
import com.section11.components.databinding.ViewHolderLayoutBinding
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel

class RecyclerViewHolder internal constructor(private val viewBinding: ViewHolderLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root), RecyclerViewHolderInterface {

    private val presenter = ViewHolderPresenter(this)

    fun bind(viewHolderModel: ViewHolderModel, listener: RecyclerItemClickListener) {
        viewBinding.root.setOnClickListener {
            listener.onRecyclerItemClicked(viewHolderModel)
        }

        val simpleDraweeView = viewBinding.viewHolderImage

        val hierarchy: GenericDraweeHierarchy =
            GenericDraweeHierarchyBuilder.newInstance(viewBinding.root.resources)
                .setProgressBarImage(ProgressBarDrawable())
                .setPlaceholderImage(R.drawable.ic_launcher_foreground)
                .build()
        simpleDraweeView.setHierarchy(hierarchy)

        presenter.onBind(viewHolderModel)
    }

    override fun setTitle(title: String) {
        viewBinding.viewHolderTitle.text = title
    }

    override fun setImage(imageUri: String) {
        viewBinding.viewHolderTitle.visibility = View.VISIBLE
        viewBinding.viewHolderImage.setImageURI(imageUri)
    }
}