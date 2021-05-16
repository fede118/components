package com.section11.components.recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.section11.components.databinding.RecyclerComponentLayoutBinding
import com.section11.components.recycler.adapter.RecyclerAdapter
import com.section11.components.recycler.factory.RecyclerComponentAttr
import com.section11.components.recycler.factory.RecyclerComponentAttrParser
import com.section11.components.recycler.factory.RecyclerComponentConfiguration
import com.section11.components.recycler.factory.RecyclerComponentConfigurationFactory
import com.section11.components.recycler.listener.RecyclerItemClickListener
import com.section11.components.recycler.model.ViewHolderModel

class RecyclerComponent : ConstraintLayout {

    private lateinit var binding: RecyclerComponentLayoutBinding
    private lateinit var attrs: RecyclerComponentAttr
    private var dataList: List<ViewHolderModel>? = null
    private var listener: RecyclerItemClickListener? = null

    constructor(
        context: Context,
        recyclerTitle: String,
        modelList: List<ViewHolderModel>,
        listener: RecyclerItemClickListener
    ) : super(context) {
        initAttrs(recyclerTitle)
        this.dataList = modelList
        this.listener = listener
    }

    constructor(
        context: Context,
        attributeSet: AttributeSet
    ) : super(context, attributeSet) {
        attrs = RecyclerComponentAttrParser.parse(context, attributeSet)
        initAttrs(attrs.title)
    }


    var title: String?
        get() = attrs.title
        set(value) {
            attrs = attrs.copy(title = value)
            setRecyclerTitle(createConfiguration())
        }

    private fun initAttrs(title: String?) {
        attrs = RecyclerComponentAttr(title = title)
        setupComponents(createConfiguration())
    }

    private fun createConfiguration() = RecyclerComponentConfigurationFactory.create(attrs)

    private fun setupComponents(config: RecyclerComponentConfiguration) {
        Fresco.initialize(context)

        binding = RecyclerComponentLayoutBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)

        setRecyclerTitle(config)
    }

    private fun setRecyclerTitle(config: RecyclerComponentConfiguration) {
        if (config.title == null) {
            binding.recyclerTitle.visibility = View.GONE
            return
        }
        binding.recyclerTitle.visibility = View.VISIBLE
        binding.recyclerTitle.text = config.title
    }

    fun setOnRecyclerItemClickedListener(listener: RecyclerItemClickListener) {
        this.listener = listener
    }

    fun setRecyclerComponentData(data: List<ViewHolderModel>) {
        this.dataList = data
    } 

    fun bind(data: List<ViewHolderModel>, listener: RecyclerItemClickListener) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = RecyclerAdapter(data, listener)
    }
}
