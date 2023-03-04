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
import com.mutefrog.myfolio.model.Nominators
import com.mutefrog.myfolio.model.Nominees

class NomineeAdapter(
    private val nomineesList: ArrayList<Nominees>,
    private val nominatorsList: ArrayList<Nominators>,
    private val currentTab: Int?
) :
    RecyclerView.Adapter<NomineeAdapter.ViewHolder>() {

    var listItemMenuClickListener: NomineeItemMenuClickListener? = null

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_name)
        val tagTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_tag)
        val dobTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_dob)
        val genderTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_gender)
        val mobileNumberTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_mobile_number)
        val emailTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_email)
        val aadharNumberTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_aadhar_number)
        val relationshipTextView: AppCompatTextView =
            itemView.findViewById(R.id.txt_nominee_item_relationship)
        val menuImageView: AppCompatImageView =
            itemView.findViewById(R.id.iv_nominee_item_menu)
        val itemButton: AppCompatButton =
            itemView.findViewById(R.id.btn_nominee_item)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_nominees_list_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (currentTab == 1) {
            viewHolder.nameTextView.text = nomineesList[position].nomineeName
//        viewHolder.tagTextView.text = dataList[position].
            viewHolder.nameTextView.text = nomineesList[position].nomineeName
            viewHolder.dobTextView.text = nomineesList[position].nomineeDob
            viewHolder.genderTextView.text = nomineesList[position].nomineeGender
            viewHolder.mobileNumberTextView.text = nomineesList[position].nomineeMobile
            viewHolder.emailTextView.text = nomineesList[position].nomineeEmail
            viewHolder.aadharNumberTextView.text = nomineesList[position].nomineeAadhaarCard
            viewHolder.relationshipTextView.text = nomineesList[position].relationshipWithNominee

            if (nomineesList[position].informationRequested == 1) {
                viewHolder.itemButton.text = "Information Requested"
            } else {
                viewHolder.itemButton.text = "Request All Information"
                viewHolder.itemButton.tag = position
                viewHolder.itemButton.setOnClickListener {
                    listItemMenuClickListener?.onReqInfoClick(
                        nomineesList[it.tag as Int],
                        Nominators()
                    )
                }
            }
            viewHolder.menuImageView.tag = position
            viewHolder.menuImageView.setOnClickListener {
                val pop = PopupMenu(viewHolder.menuImageView.context, it)
                pop.inflate(R.menu.nominee_item_menu)
                pop.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
//                        R.id.nominee_edit -> {
//                            listItemMenuClickListener?.onEditClick(
//                                nomineesList[it.tag as Int],
//                                Nominators()
//                            )
//                        }
                        R.id.nominee_delete -> {
                            listItemMenuClickListener?.onDeleteClick(
                                nomineesList[it.tag as Int],
                                Nominators()
                            )
                        }
                    }
                    true
                }
                pop.show()
            }
        }
        if (currentTab == 2) {
            viewHolder.nameTextView.text = nominatorsList[position].nomineeName
//        viewHolder.tagTextView.text = dataList[position].
            viewHolder.nameTextView.text = nominatorsList[position].nomineeName
            viewHolder.dobTextView.text = nominatorsList[position].nomineeDob
            viewHolder.genderTextView.text = nominatorsList[position].nomineeGender
            viewHolder.mobileNumberTextView.text = nominatorsList[position].nomineeMobile
            viewHolder.emailTextView.text = nominatorsList[position].nomineeEmail
            viewHolder.aadharNumberTextView.text = nominatorsList[position].nomineeAadhaarCard
            viewHolder.relationshipTextView.text = nominatorsList[position].relationshipWithNominee

            if (nominatorsList[position].informationRequested == 1) {
                viewHolder.itemButton.text = "Information Requested"
            } else {
                viewHolder.itemButton.text = "Request All Information"
                viewHolder.itemButton.tag = position
                viewHolder.itemButton.setOnClickListener {
                    listItemMenuClickListener?.onReqInfoClick(
                        Nominees(),
                        nominatorsList[it.tag as Int]
                    )
                }
            }
//            viewHolder.menuImageView.tag = position
//            viewHolder.menuImageView.setOnClickListener {
//                val pop = PopupMenu(viewHolder.menuImageView.context, it)
//                pop.inflate(R.menu.bank_accounts_item_menu)
//                pop.setOnMenuItemClickListener { item ->
//                    when (item.itemId) {
//                        R.id.bank_account_edit -> {
//                            listItemMenuClickListener?.onEditClick(
//                                Nominees(),
//                                nominatorsList[it.tag as Int]
//                            )
//                        }
//                        R.id.bank_account_delete -> {
//                            listItemMenuClickListener?.onDeleteClick(
//                                Nominees(),
//                                nominatorsList[it.tag as Int]
//                            )
//                        }
//                    }
//                    true
//                }
//                pop.show()
//            }
        }
    }

    override fun getItemCount() =
        if (currentTab == 1)
            nomineesList.size
        else
            nominatorsList.size


    interface NomineeItemMenuClickListener {

        fun onEditClick(itemNominee: Nominees, itemNominator: Nominators)

        fun onDeleteClick(itemNominee: Nominees, itemNominator: Nominators)

        fun onReqInfoClick(itemNominee: Nominees, itemNominator: Nominators)
    }

    fun setItemMenuClickListener(itemMenuClickL: NomineeItemMenuClickListener) {
        listItemMenuClickListener = itemMenuClickL
    }

}
