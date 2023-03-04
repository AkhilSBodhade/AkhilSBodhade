package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ViewAssetsListRowBinding
import com.mutefrog.myfolio.model.Asset
import com.mutefrog.myfolio.model.Card

class AssetsAdapter(private val dataList: ArrayList<Asset>) :
    RecyclerView.Adapter<AssetsAdapter.ViewHolder>() {

    var listItemMenuClickListener: ListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ViewAssetsListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewAssetsListRowBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (dataList[position].assetTypeId == 1) {

            viewHolder.binding.txtAssetItemPropertyAddress.text =
                dataList[position].assetData?.getPropertyAddressValue()
            viewHolder.binding.txtAssetItemPropertyOwner.text =
                dataList[position].assetData?.getOwnerNameValue()
            viewHolder.binding.txtAssetItemPropertyValue.text =
                dataList[position].assetData?.getValueOfPropertyValue()
            viewHolder.binding.txtAssetItemPropertyType.text =
                dataList[position].assetData?.getPropertyTypeValue()

            viewHolder.binding.llAssetItemProperty.visibility = View.VISIBLE
            viewHolder.binding.llAssetItemVehicle.visibility = View.GONE
            viewHolder.binding.llAssetItemLiquidity.visibility = View.GONE
            viewHolder.binding.llAssetItemCollectable.visibility = View.GONE
        }

        if (dataList[position].assetTypeId == 2) {
            viewHolder.binding.txtAssetItemVehicleModelNumber.text =
                dataList[position].assetData?.getPropertyAddressValue()
            viewHolder.binding.txtAssetItemVehicleRegNumber.text =
                dataList[position].assetData?.getOwnerNameValue()
            viewHolder.binding.txtAssetItemVehicleInsuranceValidity.text =
                dataList[position].assetData?.getValueOfPropertyValue()

            viewHolder.binding.llAssetItemProperty.visibility = View.GONE
            viewHolder.binding.llAssetItemVehicle.visibility = View.VISIBLE
            viewHolder.binding.llAssetItemLiquidity.visibility = View.GONE
            viewHolder.binding.llAssetItemCollectable.visibility = View.GONE
        }

        if (dataList[position].assetTypeId == 3) {
            viewHolder.binding.txtAssetItemLiqItemType.text =
                dataList[position].assetData?.getAssetTypeValue()
            viewHolder.binding.txtAssetItemLiqItemQty.text =
                dataList[position].assetData?.getAssetQuantityValue()
            viewHolder.binding.txtAssetItemLiqItemWeight.text =
                dataList[position].assetData?.getAssetWeightValue()

            viewHolder.binding.llAssetItemProperty.visibility = View.GONE
            viewHolder.binding.llAssetItemVehicle.visibility = View.GONE
            viewHolder.binding.llAssetItemLiquidity.visibility = View.VISIBLE
            viewHolder.binding.llAssetItemCollectable.visibility = View.GONE
        }

        if (dataList[position].assetTypeId == 4) {
            viewHolder.binding.txtAssetItemCollectableQty.text =
                dataList[position].assetData?.getAssetQuantityValue()
            viewHolder.binding.txtAssetItemCollectableNominee.text =
                dataList[position].assetData?.getOwnerNameValue()

            viewHolder.binding.llAssetItemProperty.visibility = View.GONE
            viewHolder.binding.llAssetItemVehicle.visibility = View.GONE
            viewHolder.binding.llAssetItemLiquidity.visibility = View.GONE
            viewHolder.binding.llAssetItemCollectable.visibility = View.VISIBLE
        }


        viewHolder.binding.ivAssetItemMenu.tag = position
        viewHolder.binding.ivAssetItemMenu.setOnClickListener {
            val pop = PopupMenu(viewHolder.binding.ivAssetItemMenu.context, it)
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

        fun onEditClick(asset: Asset)

        fun onDeleteClick(asset: Asset)
    }

    fun setItemMenuClickListener(itemMenuClickL: ListItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
