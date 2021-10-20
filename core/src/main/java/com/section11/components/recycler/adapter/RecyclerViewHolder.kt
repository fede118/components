package com.section11.components.recycler.adapter

import android.graphics.drawable.Animatable
import android.net.Uri
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import com.section11.components.R
import com.section11.components.animateIn
import com.section11.components.databinding.ViewHolderLayoutBinding
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel


class RecyclerViewHolder internal constructor(private val viewBinding: ViewHolderLayoutBinding)
    : RecyclerView.ViewHolder(viewBinding.root), RecyclerViewHolderInterface {

    private val presenter = ViewHolderPresenter(this)

    fun bind(viewHolderModel: ViewHolderModel, listener: RecyclerItemClickListener?) {
        viewBinding.root.setOnClickListener {
            listener?.onRecyclerItemClicked(viewHolderModel)
        }

        presenter.onBind(viewHolderModel)
    }

    override fun setTitle(title: String) {
        viewBinding.titleShimmerContainer.apply {
            hideShimmer()
            stopShimmer()
            background = null
        }

        viewBinding.itemTitle.apply {
            text = title
            animateIn()
        }

    }

    override fun setImage(imageUrl: String) {
        Fresco.newDraweeControllerBuilder().apply {
            setUri(imageUrl)
            oldController = viewBinding.itemImage.controller
            controllerListener = object : BaseControllerListener<ImageInfo>() {
                override fun onFinalImageSet(
                    id: String?,
                    imageInfo: ImageInfo?,
                    animatable: Animatable?
                ) {
                    super.onFinalImageSet(id, imageInfo, animatable)
                    viewBinding.imageShimmerContainer.let {
                        if (it.isShimmerVisible) {
                            it.hideShimmer()
                            it.stopShimmer()

                            viewBinding.itemImage.animateIn()
                        }
                    }
                }
            }
            viewBinding.itemImage.controller = this.build()
        }
    }

    override fun showTitleLoading() {
        viewBinding.titleShimmerContainer.background ?: run {
            viewBinding.titleShimmerContainer.background =
                ContextCompat.getDrawable(viewBinding.root.context, R.drawable.shimmer_image_place_holder)
        }

        viewBinding.itemTitle.text = null
        viewBinding.titleShimmerContainer.apply {
            showShimmer(true)
            startShimmer()
        }
    }

    override fun showImageLoading() {
        removeCurrentImage()
        viewBinding.imageShimmerContainer.apply {
            showShimmer(true)
            startShimmer()
        }
    }

    private fun removeCurrentImage() {
        val uri = Uri.Builder()
            .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
            .path(java.lang.String.valueOf(R.drawable.blank_placeholder))
            .build()

        viewBinding.itemImage.setImageURI(uri.toString())
    }
}