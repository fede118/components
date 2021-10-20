package com.section11.components.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.facebook.drawee.backends.pipeline.Fresco
import com.section11.components.databinding.RecyclerComponentLayoutBinding
import com.section11.components.recycler.adapter.RecyclerAdapter
import com.section11.components.recycler.factory.RecyclerComponentAttr
import com.section11.components.recycler.factory.RecyclerComponentAttrParser
import com.section11.components.recycler.factory.RecyclerComponentConfiguration
import com.section11.components.recycler.factory.RecyclerComponentConfigurationFactory
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel
import com.section11.components.smoothSnapToPosition

class RecyclerComponent : ConstraintLayout {

    private lateinit var binding: RecyclerComponentLayoutBinding
    private lateinit var attrs: RecyclerComponentAttr
    private lateinit var adapter: RecyclerAdapter
    private var dataList: List<ViewHolderModel>? = null
    private var listener: RecyclerItemClickListener? = null

    constructor(
        context: Context,
        recyclerTitle: String,
        placeHolderItems: Int,
        modelList: List<ViewHolderModel>,
        listener: RecyclerItemClickListener
    ) : super(context) {
        initAttrs(recyclerTitle, placeHolderItems)
        this.dataList = modelList
        this.listener = listener
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet
    ) : super(context, attributeSet) {
        attrs = RecyclerComponentAttrParser.parse(context, attributeSet)
        initAttrs(attrs.title, attrs.placeHolderItems)
    }

    var title: String?
        get() = attrs.title
        set(value) {
            attrs = attrs.copy(title = value)
            setRecyclerTitle(createConfiguration())
        }

    var placeHolderItems: Int
        get() = attrs.placeHolderItems
        set(value) {
            attrs = attrs.copy(placeHolderItems = value)
            setPlaceHolderItems(createConfiguration())
        }

    private fun initAttrs(title: String?, placeHolderItems: Int) {
        attrs = RecyclerComponentAttr(title = title, placeHolderItems = placeHolderItems)
        setupComponents(createConfiguration())
    }

    private fun createConfiguration() = RecyclerComponentConfigurationFactory.create(attrs)

    private fun setupComponents(config: RecyclerComponentConfiguration) {
        Fresco.initialize(context)

        binding = RecyclerComponentLayoutBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        initializeRecycler()

        setRecyclerTitle(config)
        setPlaceHolderItems(config)
    }

    private fun setRecyclerTitle(config: RecyclerComponentConfiguration) {
        binding.recyclerTitle.apply {
            if (config.title == null) {
                visibility = View.GONE
                return
            }
            visibility = View.VISIBLE
            text = config.title
        }
    }

    private fun setPlaceHolderItems(config: RecyclerComponentConfiguration) {
        if (config.placeHolderItems > ZERO) {
            val placeHolderItems = List(config.placeHolderItems) {
                object : ViewHolderModel {
                    override fun getId(): String? = null
                    override fun getTitle(): String? = null
                    override fun getImageUrl(): String? = null
                }
            }

            adapter.updateItemsList(placeHolderItems)
            binding.recyclerView.smoothSnapToPosition(ZERO)
        } else {
            adapter.updateItemsList(emptyList())
        }
    }

    fun setOnRecyclerItemClickedListener(listener: RecyclerItemClickListener) {
        adapter.listener = listener
    }

    fun bind(data: List<ViewHolderModel>, listener: RecyclerItemClickListener? = null) {
        adapter.updateItemsList(data)
        listener?.let {
           adapter.listener = it
        }
    }

    private fun initializeRecycler() {
        adapter = dataList?.let { RecyclerAdapter(it) } ?: RecyclerAdapter(emptyList())
        binding.recyclerView.adapter = adapter

        listener?.let {
            this.listener = it
        }
    }

    companion object {
        private const val ZERO = 0
    }
}
