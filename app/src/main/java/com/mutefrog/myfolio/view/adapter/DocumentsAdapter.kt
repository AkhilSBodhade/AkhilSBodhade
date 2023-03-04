package com.mutefrog.myfolio.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.ViewDocumentsListRowBinding
import com.mutefrog.myfolio.model.Document

class DocumentsAdapter(private val dataList: ArrayList<Document>) :
    RecyclerView.Adapter<DocumentsAdapter.ViewHolder>() {

    var listItemMenuClickListener: ListItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val binding: ViewDocumentsListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewDocumentsListRowBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.binding.txtDocItemName.text =
            dataList[position].documentData?.getDocumentHolderNameValue()

        viewHolder.binding.txtDocItemDocNumber.text =
            dataList[position].documentData?.getDocumentNumberValue()

//        viewHolder.binding.txtDocItemDocType.text =
//            dataList[position].documentTypeId.toString()

        if(dataList[position].documentTypeId == 8) {

            viewHolder.binding.llItemDocNumber.visibility = View.GONE
            viewHolder.binding.llItemDocMedpresdate.visibility = View.VISIBLE
            viewHolder.binding.llItemDocMedprestype.visibility = View.VISIBLE

            viewHolder.binding.txtDocItemMedpresDate.text = "Date Of Prescription"
            viewHolder.binding.editDocItemMedpresDate.text =
                dataList[position].documentData?.getPrescriptionDateValue()

            viewHolder.binding.txtDocItemMedpresType.text = "Type Of Prescription"
            viewHolder.binding.editDocItemMedpresType.text =
                dataList[position].documentData?.getPrescriptionTypeValue()

        }
        else if(dataList[position].documentTypeId == 7) {

            viewHolder.binding.llItemDocNumber.visibility = View.GONE
            viewHolder.binding.llItemDocMedpresdate.visibility = View.VISIBLE
            viewHolder.binding.llItemDocMedprestype.visibility = View.VISIBLE

            viewHolder.binding.txtDocItemMedpresDate.text = "Date Of Medical Report"
            viewHolder.binding.editDocItemMedpresDate.text =
                dataList[position].documentData?.getMedicalDateValue()

            viewHolder.binding.txtDocItemMedpresType.text = "Type Of Medical Report"
            viewHolder.binding.editDocItemMedpresType.text =
                dataList[position].documentData?.getMedicalTypeValue()
        }else{
            viewHolder.binding.llItemDocNumber.visibility = View.VISIBLE
            viewHolder.binding.llItemDocMedpresdate.visibility = View.GONE
            viewHolder.binding.llItemDocMedprestype.visibility = View.GONE
        }



        viewHolder.binding.ivDocItemMenu.tag = position
        viewHolder.binding.ivDocItemMenu.setOnClickListener {
            val pop = PopupMenu(viewHolder.binding.ivDocItemMenu.context, it)
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

        fun onEditClick(asset: Document)

        fun onDeleteClick(asset: Document)
    }

    fun setItemMenuClickListener(itemMenuClickL: ListItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
