package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ViewInvestmentListRowBinding
import com.mutefrog.myfolio.model.FinInvestment

class InvestmentsAdapter(private val dataList: ArrayList<FinInvestment>) :
    RecyclerView.Adapter<InvestmentsAdapter.ViewHolder>() {

    var listItemMenuClickListener: ListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ViewInvestmentListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewInvestmentListRowBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        manageTypeWiseView(viewHolder, dataList[position].financeTypeId)

        if (dataList[position].financeTypeId == 1) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getEquityNameValue()
            viewHolder.binding.txtInvestmentItemEquityNoShare.text =
                dataList[position].financeData?.getNoOfSharesValue()
            viewHolder.binding.txtInvestmentItemEquityAmountInvested.text =
                dataList[position].financeData?.getAmountInvestedValue()
            viewHolder.binding.txtInvestmentItemEquityBrokerName.text =
                dataList[position].financeData?.getBrokerNameValue()

        } else if (dataList[position].financeTypeId == 2) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getFundNameValue()

            viewHolder.binding.txtInvestmentItemMfAmountInvested.text =
                dataList[position].financeData?.getAmountInvestedValue()
            viewHolder.binding.txtInvestmentItemMfFundType.text =
                dataList[position].financeData?.fundType
            viewHolder.binding.txtInvestmentItemMfBrokerName.text =
                dataList[position].financeData?.getBrokerNameValue()

        } else if (dataList[position].financeTypeId == 3) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.sipName

            viewHolder.binding.txtInvestmentItemSipAmountInvested.text =
                dataList[position].financeData?.getAmountInvestedValue()
            viewHolder.binding.txtInvestmentItemSipBrokerName.text =
                dataList[position].financeData?.getBrokerNameValue()

        } else if (dataList[position].financeTypeId == 4) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getAccountHolderNameValue()

            viewHolder.binding.txtInvestmentItemFdAmountInvested.text =
                dataList[position].financeData?.getAmountInvestedValue()
            viewHolder.binding.txtInvestmentItemFdMaturityDate.text =
                dataList[position].financeData?.maturityDate
            viewHolder.binding.txtInvestmentItemFdNumber.text =
                dataList[position].financeData?.getFixedDepositNumberValue()

        } else if (dataList[position].financeTypeId == 5) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getAccountHolderNameValue()

            viewHolder.binding.txtInvestmentItemFdAmountInvested.text =
                dataList[position].financeData?.getAmountInvestedValue()
            viewHolder.binding.txtInvestmentItemFdMaturityDate.text =
                dataList[position].financeData?.maturityDate
            viewHolder.binding.txtInvestmentItemFdNumber.text =
                dataList[position].financeData?.getFixedDepositNumberValue()
        }

        else if (dataList[position].financeTypeId == 6) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getCryptoNameValue()

            viewHolder.binding.txtInvestmentItemCryptoAmtInvested.text =
                dataList[position].financeData?.getAmountInvestedValue()
            viewHolder.binding.txtInvestmentItemCryptoBrokerName.text =
                dataList[position].financeData?.getBrokerNameValue()
            viewHolder.binding.txtInvestmentItemCryptoQty.text =
                dataList[position].financeData?.getQuantityValue()

        } else if (dataList[position].financeTypeId == 7) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getNPSTypeValue()

            viewHolder.binding.txtInvestmentItemNpsPraNum.text =
                dataList[position].financeData?.getPermanentRetirementAccNoValue()

        } else if (dataList[position].financeTypeId == 8) {
            viewHolder.binding.txtInvestmentItemName.text =
                ""
            viewHolder.binding.txtInvestmentItemPpfAccNum.text =
                dataList[position].financeData?.getPfAmountValue()

            viewHolder.binding.txtInvestmentItemPpfMaturityDate.text =
                dataList[position].financeData?.maturityDate

        } else if (dataList[position].financeTypeId == 9) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getBondNameValue()

            viewHolder.binding.txtInvestmentItemBondMaturityAmount.text =
                dataList[position].financeData?.getMaturityAmountValue()
            viewHolder.binding.txtInvestmentItemBondNum.text =
                dataList[position].financeData?.getBondNumberValue()


        } else if (dataList[position].financeTypeId == 10) {

            viewHolder.binding.txtInvestmentItemName.text =
                ""
            viewHolder.binding.txtInvestmentItemPfUanNum.text =
                dataList[position].financeData?.getUANValue()

        } else if (dataList[position].financeTypeId == 11) {

            viewHolder.binding.txtInvestmentItemName.text =
                dataList[position].financeData?.getCertificateNameValue()

            viewHolder.binding.txtInvestmentItemCertNum.text =
                dataList[position].financeData?.getCertificateNumberValue()
        }

        viewHolder.binding.ivInvestmentItemMenu.tag = position
        viewHolder.binding.ivInvestmentItemMenu.setOnClickListener {
            val pop = PopupMenu(viewHolder.binding.ivInvestmentItemMenu.context, it)
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

    fun manageTypeWiseView(viewHolder: ViewHolder, financeTypeId: Int?) {

        viewHolder.binding.llInvestmentItemEquity.visibility = View.GONE
        viewHolder.binding.llInvestmentItemMf.visibility = View.GONE
        viewHolder.binding.llInvestmentItemFd.visibility = View.GONE
        viewHolder.binding.llInvestmentItemCrypto.visibility = View.GONE
        viewHolder.binding.llInvestmentItemNps.visibility = View.GONE
        viewHolder.binding.llInvestmentItemPpf.visibility = View.GONE
        viewHolder.binding.llInvestmentItemBond.visibility = View.GONE
        viewHolder.binding.llInvestmentItemPf.visibility = View.GONE
        viewHolder.binding.llInvestmentItemCert.visibility = View.GONE

        when (financeTypeId) {

            1 -> viewHolder.binding.llInvestmentItemEquity.visibility = View.VISIBLE

            2 -> viewHolder.binding.llInvestmentItemMf.visibility = View.VISIBLE

            3 -> viewHolder.binding.llInvestmentItemSip.visibility = View.VISIBLE

            4 -> viewHolder.binding.llInvestmentItemFd.visibility = View.VISIBLE

            6 -> viewHolder.binding.llInvestmentItemCrypto.visibility = View.VISIBLE

            7 -> viewHolder.binding.llInvestmentItemNps.visibility = View.VISIBLE

            8 -> viewHolder.binding.llInvestmentItemPpf.visibility = View.VISIBLE

            9 -> viewHolder.binding.llInvestmentItemBond.visibility = View.VISIBLE

            10 -> viewHolder.binding.llInvestmentItemPf.visibility = View.VISIBLE

            11 -> viewHolder.binding.llInvestmentItemCert.visibility = View.VISIBLE
        }

    }

    override fun getItemCount() = dataList.size

    interface ListItemMenuClickListener {

        fun onEditClick(asset: FinInvestment)

        fun onDeleteClick(asset: FinInvestment)
    }

    fun setItemMenuClickListener(itemMenuClickL: ListItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
