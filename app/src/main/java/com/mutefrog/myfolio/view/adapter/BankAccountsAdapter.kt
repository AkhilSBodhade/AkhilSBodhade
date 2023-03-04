package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.model.BankAccount

class BankAccountsAdapter(private val bankAccountsList: ArrayList<BankAccount>) :
    RecyclerView.Adapter<BankAccountsAdapter.ViewHolder>() {

    var bankItemMenuClickListener: BankItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val bankNameTextView: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.txt_bank_acc_item_bank_name)
        val shareNowTagTextView: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.txt_bank_acc_item_share_now_tag)
        val accountNumberTextView: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.txt_bank_acc_item_acc_number)
        val ifscCodeTextView: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.txt_bank_acc_item_ifsc_code)
        val customerIdTextView: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.txt_bank_acc_item_cust_id)
        val relationshipMgrTextView: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.txt_bank_acc_item_relationship_mgr)
        val commentTextView: AppCompatTextView =
            itemView.findViewById<AppCompatTextView>(R.id.txt_bank_acc_item_comment)
        val menuImageView: AppCompatImageView =
            itemView.findViewById<AppCompatImageView>(R.id.iv_bank_acc_item_menu)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_bank_accounts_list_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bankNameTextView.text = bankAccountsList[position].getBankNameValue()

        if (bankAccountsList[position].share == 1) {
            viewHolder.shareNowTagTextView.visibility = View.VISIBLE
        } else {
            viewHolder.shareNowTagTextView.visibility = View.GONE
        }

        viewHolder.accountNumberTextView.text = bankAccountsList[position].getBankAccountNumberValue()

        if(bankAccountsList[position].ifscCode != null && bankAccountsList[position].ifscCode!!.isNotEmpty())
        viewHolder.ifscCodeTextView.text = bankAccountsList[position].getIFSCCodeValue()

        if(bankAccountsList[position].customerId != null && bankAccountsList[position].customerId!!.isNotEmpty())
        viewHolder.customerIdTextView.text = bankAccountsList[position].getCustomerIdValue()

        if(bankAccountsList[position].relationshipManager != null && bankAccountsList[position].relationshipManager!!.isNotEmpty())
        viewHolder.relationshipMgrTextView.text = bankAccountsList[position].relationshipManager

        if(bankAccountsList[position].comment != null && bankAccountsList[position].comment!!.isNotEmpty())
        viewHolder.commentTextView.text = bankAccountsList[position].comment

        viewHolder.menuImageView.tag = position
        viewHolder.menuImageView.setOnClickListener {
            val pop = PopupMenu(viewHolder.menuImageView.context, it)
            pop.inflate(R.menu.bank_accounts_item_menu)
            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.bank_account_edit -> {
                        bankItemMenuClickListener?.onEditClick(bankAccountsList[it.tag as Int])
                    }
                    R.id.bank_account_delete -> {
                        bankItemMenuClickListener?.onDeleteClick(bankAccountsList[it.tag as Int])
                    }
                }
                true
            }
            pop.show()
        }

    }

    override fun getItemCount() = bankAccountsList.size

    interface BankItemMenuClickListener {

        fun onEditClick(bankAccount: BankAccount)

        fun onDeleteClick(bankAccount: BankAccount)
    }

    fun setItemMenuClickListener(itemMenuClickL: BankItemMenuClickListener) {
        bankItemMenuClickListener = itemMenuClickL
    }

}
