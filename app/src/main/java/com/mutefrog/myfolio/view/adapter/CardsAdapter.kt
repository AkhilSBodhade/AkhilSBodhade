package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.model.Card

class CardsAdapter(private val dataList: ArrayList<Card>) :
    RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    var cardItemMenuClickListener: CardItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val bankNameInitialsTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_card_item_initials)
        val bankNameTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_card_item_bank_name)
        val cardNumberTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_card_item_card_number)
        val expiryDateTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_card_item_expiry_date)
        val cardLimitTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_card_item_card_limit)
        val menuImageView: AppCompatImageView =
            itemView.findViewById(R.id.iv_card_item_menu)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_cards_list_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.bankNameTextView.text = dataList[position].getCardBankNameValue()

        val initials =
            dataList[position].getCardBankNameValue()?.split(' ')
                ?.mapNotNull { it.firstOrNull()?.toString() }
                ?.reduce { acc, s -> acc + s }
        viewHolder.bankNameInitialsTextView.text = initials?.take(2)

        viewHolder.cardNumberTextView.text = "XXXX  XXXX  XXXX " + dataList[position].getCardNoValue()+"   "
        viewHolder.expiryDateTextView.text = dataList[position].expiryDate
        viewHolder.cardLimitTextView.text = dataList[position].getCardLimitValue()

        viewHolder.menuImageView.tag = position
        viewHolder.menuImageView.setOnClickListener {
            val pop = PopupMenu(viewHolder.menuImageView.context, it)
            pop.inflate(R.menu.bank_accounts_item_menu)
            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.bank_account_edit -> {
                        cardItemMenuClickListener?.onEditClick(dataList[it.tag as Int])
                    }
                    R.id.bank_account_delete -> {
                        cardItemMenuClickListener?.onDeleteClick(dataList[it.tag as Int])
                    }
                }
                true
            }
            pop.show()
        }

    }

    override fun getItemCount() = dataList.size

    interface CardItemMenuClickListener {

        fun onEditClick(bankAccount: Card)

        fun onDeleteClick(bankAccount: Card)
    }

    fun setItemMenuClickListener(itemMenuClickL: CardItemMenuClickListener) {
        cardItemMenuClickListener = itemMenuClickL
    }

}
