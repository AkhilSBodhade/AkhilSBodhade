package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ViewAssetsListRowBinding
import com.mutefrog.myfolio.databinding.ViewInsuranceListRowBinding
import com.mutefrog.myfolio.model.Asset
import com.mutefrog.myfolio.model.Card
import com.mutefrog.myfolio.model.Insurance

class InsuranceAdapter(private val dataList: ArrayList<Insurance>) :
    RecyclerView.Adapter<InsuranceAdapter.ViewHolder>() {

    var listItemMenuClickListener: ListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ViewInsuranceListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewInsuranceListRowBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.txtInsuranceItemName.text =
            dataList[position].getInsuranceTypeValue()
        viewHolder.binding.txtInsuranceItemCompanyName.text =
            dataList[position].getInsuranceCompanyNameValue()
        viewHolder.binding.txtInsuranceItemCoverAmount.text =
            dataList[position].getTotalCoverAmountValue()
        viewHolder.binding.txtInsuranceItemInsuranceNumber.text =
            dataList[position].getInsuranceNumberValue()
        viewHolder.binding.txtInsuranceItemPremiumAmount.text =
            dataList[position].getPremiumAmountValue()

        viewHolder.binding.ivInsuranceItemMenu.tag = position
        viewHolder.binding.ivInsuranceItemMenu.setOnClickListener {
            val pop = PopupMenu(viewHolder.binding.ivInsuranceItemMenu.context, it)
            pop.inflate(R.menu.bank_accounts_item_menu)
            pop.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.bank_account_edit -> {
                        listItemMenuClickListener?.onEditClick(dataList[it.tag as Int])
                    }
                    R.id.bank_account_delete -> {
                        listItemMenuClickListener?.onDeleteClick(dataList[it.tag as Int])
                    }
                }
                true
            }
            pop.show()
        }

    }

    override fun getItemCount() = dataList.size

    interface ListItemMenuClickListener {

        fun onEditClick(item: Insurance)

        fun onDeleteClick(item: Insurance)
    }

    fun setItemMenuClickListener(itemMenuClickL: ListItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
