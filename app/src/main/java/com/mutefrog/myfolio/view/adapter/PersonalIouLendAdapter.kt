package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.model.IOU

class PersonalIouLendAdapter(private val arrayList: ArrayList<IOU>) :
    RecyclerView.Adapter<PersonalIouLendAdapter.ViewHolder>() {

    var listItemMenuClickListener: PIOUListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_personal_iou_lend_item_iou_name)
        val shareNowTagTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_personal_iou_lend_item_share_now_tag)
        val borrowerNameTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_personal_iou_lend_item_borrower_name)
        val amountLendTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_personal_iou_lend_item_amount_lend)
        val deadlineTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_personal_iou_lend_item_deadline)
        val commentTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_comment)
        val menuImageView: AppCompatImageView =
            itemView.findViewById(R.id.iv_personal_iou_lend_item_menu)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_personal_iou_lend_list_row, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.nameTextView.text = arrayList[position].getReasonValue()

        if (arrayList[position].share == 1) {
            viewHolder.shareNowTagTextView.visibility = View.VISIBLE
        } else {
            viewHolder.shareNowTagTextView.visibility = View.GONE
        }

        viewHolder.borrowerNameTextView.text = arrayList[position].getBorrowerNameValue()
        viewHolder.amountLendTextView.text = arrayList[position].getTotalAmountValue()
        viewHolder.deadlineTextView.text = arrayList[position].deadline
        viewHolder.commentTextView.text = arrayList[position].comment

        viewHolder.menuImageView.tag = position
        viewHolder.menuImageView.setOnClickListener {
            val pop = PopupMenu(viewHolder.menuImageView.context, it)
            pop.inflate(R.menu.bank_accounts_item_menu)
            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.bank_account_edit -> {
                        listItemMenuClickListener?.onEditClick(arrayList[it.tag as Int])
                    }
                    R.id.bank_account_delete -> {
                        listItemMenuClickListener?.onDeleteClick(arrayList[it.tag as Int])
                    }
                }
                true
            }
            pop.show()
        }

    }

    override fun getItemCount() = arrayList.size

    fun setItemMenuClickListener(itemMenuClickL: PIOUListItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
