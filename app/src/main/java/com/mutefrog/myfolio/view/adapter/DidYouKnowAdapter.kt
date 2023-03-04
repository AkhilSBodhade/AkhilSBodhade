package com.mutefrog.myfolio.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ViewDidYouKnowListRowBinding
import com.mutefrog.myfolio.model.Contact
import com.mutefrog.myfolio.model.DidYouKnow


class DidYouKnowAdapter(
    private val context: Context?,
    private val dataList: ArrayList<DidYouKnow>
) :
    RecyclerView.Adapter<DidYouKnowAdapter.ViewHolder>() {

    var listItemMenuClickListener: ListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ViewDidYouKnowListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewDidYouKnowListRowBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvDyn.text = dataList[position].title

        if (dataList[position].type == "Video")
            viewHolder.binding.ivIcn.setImageResource(R.drawable.icn_dyn_video)
        if (dataList[position].type == "Url")
            viewHolder.binding.ivIcn.setImageResource(R.drawable.icn_dyn_url)

        val options: RequestOptions = RequestOptions()
            .centerCrop()
//            .placeholder(R.drawable.ic_media_play)
//            .error(R.drawable.ic_media_pause)
        Glide.with(context!!).load(dataList[position].thumbnailImage).apply(options)
            .into(viewHolder.binding.ivDyn)

        viewHolder.binding.root.tag = position
        viewHolder.binding.root.setOnClickListener {
            listItemMenuClickListener?.onItemClick(dataList[it.tag as Int])
        }

    }

    override fun getItemCount() = dataList.size


    interface ListItemMenuClickListener {

        fun onItemClick(item: DidYouKnow)

    }

    fun setItemMenuClickListener(itemMenuClickL: ListItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
