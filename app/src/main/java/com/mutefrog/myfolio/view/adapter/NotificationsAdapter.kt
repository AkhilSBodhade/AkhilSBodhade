package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.*
import com.mutefrog.myfolio.model.*

class NotificationsAdapter(private val dataList: ArrayList<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

//    var listItemMenuClickListener: ListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ViewNotificationsListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewNotificationsListRowBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.txtNotificationItemTitle.text =
            dataList[position].title

        viewHolder.binding.txtNotificationItemDatetime.text =
            dataList[position].createdAt

//        viewHolder.binding.btnEditContactItem.tag = position
//        viewHolder.binding.btnEditContactItem.setOnClickListener {
//            listItemMenuClickListener?.onEditClick(dataList[it.tag as Int])
//        }

    }

    override fun getItemCount() = dataList.size

//    interface ListItemMenuClickListener {
//
//        fun onEditClick(item: Notification)
//
//        fun onDeleteClick(item: Notification)
//    }
//
//    fun setItemMenuClickListener(itemMenuClickL: ListItemMenuClickListener) {
//        listItemMenuClickListener = itemMenuClickL
//    }

}
