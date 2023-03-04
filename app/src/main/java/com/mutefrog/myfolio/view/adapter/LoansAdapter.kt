package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.model.Loan

class LoansAdapter(private val dataList: ArrayList<Loan>) :
    RecyclerView.Adapter<LoansAdapter.ViewHolder>() {

    var loanItemMenuClickListener: LoanItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val loanNameTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_loan_item_name)
        val statusTagTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_loan_item_status_tag)
        val durationTagTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_loan_item_duration_tag)
        val shareTagTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_loan_item_share_tag)
        val loanAmountTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_loan_item_loan_amount)
        val startDateTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_loan_item_start_date)
        val endDateTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_loan_item_end_date)
        val menuImageView: AppCompatImageView =
            itemView.findViewById(R.id.iv_loan_item_menu)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_loan_list_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.loanNameTextView.text = dataList[position].getPurposeOfLoanValue()

        if (dataList[position].status.equals("1")) {
            viewHolder.statusTagTextView.text = "  Active  "
        } else {
            viewHolder.statusTagTextView.text = "  Complete  "
        }

        viewHolder.durationTagTextView.text = dataList[position].duration

        if (dataList[position].share!!.equals("1")) {
            viewHolder.shareTagTextView.text = "  Share Now  "
        } else {
            viewHolder.shareTagTextView.text = "  Share Later  "
        }

        viewHolder.loanAmountTextView.text = dataList[position].getLoanAmountValue()

        viewHolder.startDateTextView.text = dataList[position].startDate
        viewHolder.endDateTextView.text = dataList[position].endDate

        viewHolder.menuImageView.tag = position
        viewHolder.menuImageView.setOnClickListener {
            val pop = PopupMenu(viewHolder.menuImageView.context, it)
            pop.inflate(R.menu.bank_accounts_item_menu)
            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.bank_account_edit -> {
                        loanItemMenuClickListener?.onEditClick(dataList[it.tag as Int])
                    }
                    R.id.bank_account_delete -> {
                        loanItemMenuClickListener?.onDeleteClick(dataList[it.tag as Int])
                    }
                }
                true
            }
            pop.show()
        }

    }

    override fun getItemCount() = dataList.size

    interface LoanItemMenuClickListener {

        fun onEditClick(loan: Loan)

        fun onDeleteClick(loan: Loan)
    }

    fun setItemMenuClickListener(itemMenuClickL: LoanItemMenuClickListener) {
        loanItemMenuClickListener = itemMenuClickL
    }

}
