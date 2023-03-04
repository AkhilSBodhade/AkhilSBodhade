package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ViewAssetsListRowBinding
import com.mutefrog.myfolio.databinding.ViewCardsListRowBinding
import com.mutefrog.myfolio.databinding.ViewContactsListRowBinding
import com.mutefrog.myfolio.databinding.ViewInsuranceListRowBinding
import com.mutefrog.myfolio.model.Asset
import com.mutefrog.myfolio.model.Card
import com.mutefrog.myfolio.model.Contact
import com.mutefrog.myfolio.model.Insurance

class ContactsAdapter(private val dataList: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    var listItemMenuClickListener: ListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ViewContactsListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewContactsListRowBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.txtContactItemName.text =
            dataList[position].getNameValue()

        val initials =
            dataList[position].getNameValue()?.split(' ')
                ?.mapNotNull { it.firstOrNull()?.toString() }
                ?.reduce { acc, s -> acc + s }
        viewHolder.binding.txtContactItemInitials.text = initials?.take(2)

        viewHolder.binding.txtContactItemContactNumber.text =
            dataList[position].getContactNumberValue()

        viewHolder.binding.txtContactItemEmailAddress.text =
            dataList[position].email

//        viewHolder.binding.txtContactItemTypeTag.text =
//            dataList[position].type

        viewHolder.binding.btnEditContactItem.tag = position
        viewHolder.binding.btnEditContactItem.setOnClickListener {
            listItemMenuClickListener?.onEditClick(dataList[it.tag as Int])
        }

        viewHolder.binding.ivContactItemArrow.setOnClickListener {
            if (viewHolder.binding.llContactItemDetail.visibility == View.VISIBLE) {
                viewHolder.binding.ivContactItemArrow.setImageResource(R.drawable.ic_contact_row_down_arrow)
                viewHolder.binding.llContactItemDetail.visibility = View.GONE
            } else {
                viewHolder.binding.ivContactItemArrow.setImageResource(R.drawable.ic_contact_row_up_arrow)
                viewHolder.binding.llContactItemDetail.visibility = View.VISIBLE
            }
        }

//        viewHolder.binding.ivInsuranceItemMenu.tag = position
//        viewHolder.binding.ivInsuranceItemMenu.setOnClickListener {
//            val pop = PopupMenu(viewHolder.binding.ivInsuranceItemMenu.context, it)
//            pop.inflate(R.menu.bank_accounts_item_menu)
//            pop.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.bank_account_edit -> {
//                        listItemMenuClickListener?.onEditClick(dataList[it.tag as Int])
//                    }
//                    R.id.bank_account_delete -> {
//                        listItemMenuClickListener?.onDeleteClick(dataList[it.tag as Int])
//                    }
//                }
//                true
//            }
//            pop.show()
//        }

    }

    override fun getItemCount() = dataList.size

    interface ListItemMenuClickListener {

        fun onEditClick(item: Contact)

        fun onDeleteClick(item: Contact)
    }

    fun setItemMenuClickListener(itemMenuClickL: ListItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
