package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mutefrog.myfolio.base.BaseAdapter
import com.mutefrog.myfolio.base.BaseHolder
import com.mutefrog.myfolio.databinding.LayoutListItemBinding


class MyFolioListAdapter(private val onClickAction: ((Any) -> Unit)) :
    BaseAdapter<Any, LayoutListItemBinding, ListHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHolder {
        return ListHolder(
            LayoutListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickAction
        )
    }
}

class ListHolder(
    viewBinding: LayoutListItemBinding,
    private val onClickAction: ((Any) -> Unit)
) : BaseHolder<Any, LayoutListItemBinding>(viewBinding) {
    override fun bind(binding: LayoutListItemBinding, item: Any?) {
        item?.let { data ->
            binding.apply {
                var label = ""
                when (data) {
                    is String -> {
                        label = data
                    }
                }

                txtCenter.text = label

                root.setOnClickListener {
                    onClickAction.invoke(data)
                }
            }
        } ?: return
    }
}