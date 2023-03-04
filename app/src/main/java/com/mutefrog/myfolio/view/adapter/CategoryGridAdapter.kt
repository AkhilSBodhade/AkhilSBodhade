package com.mutefrog.myfolio.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.model.Category

class CategoryGridAdapter(
    context: Context,
    categoriesArrayList: ArrayList<Category>?
) :
    ArrayAdapter<Category?>(context, 0, categoriesArrayList as List<Category>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var gridItemView = convertView
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(context)
                .inflate(R.layout.view_grid_category, parent, false)
        }

        val categoryModel: Category? = getItem(position)
        val categoryNameTextView = gridItemView!!.findViewById<TextView>(R.id.txt_category_name)
        val categoryIconImageView = gridItemView.findViewById<ImageView>(R.id.img_category_icon)
        setItemIcon(categoryModel?.docCount, categoryIconImageView, categoryModel?.image)

        setItemText(categoryModel?.loanType, categoryModel?.docCount, categoryNameTextView)
        setItemText(categoryModel?.financeType, categoryModel?.docCount, categoryNameTextView)
        setItemText(categoryModel?.assetType, categoryModel?.docCount, categoryNameTextView)
        setItemText(categoryModel?.insuranceType, categoryModel?.docCount, categoryNameTextView)
        setItemText(categoryModel?.contactType, categoryModel?.docCount, categoryNameTextView)
        setItemText(categoryModel?.documentType, categoryModel?.docCount, categoryNameTextView)

        return gridItemView
    }

    private fun setItemText(type: String?, docCount: Int?, categoryNameTextView: TextView) {
        if (type != null) {
            if (docCount != null && docCount != 0) {
                categoryNameTextView.text =
                    "${type} (${docCount})"
                categoryNameTextView.setTextColor(context.getColor(R.color.primary_blue))
            } else {
                categoryNameTextView.text = type
                categoryNameTextView.setTextColor(context.getColor(R.color.tutorial_dash_gray))
            }
        }
    }

    private fun setItemIcon(
        docCount: Int?,
        categoryIconImageView: ImageView,
        imageName: String?
    ) {
        if (docCount != null && docCount != 0) {
            categoryIconImageView.setImageResource(
                context.resources.getIdentifier(
                    "ic_cat_" + imageName + "_select",
                    "drawable",
                    context.packageName
                )
            )
        } else {
            categoryIconImageView.setImageResource(
                context.resources.getIdentifier(
                    "ic_cat_$imageName"
                    ,
                    "drawable",
                    context.packageName
                )
            )
        }
    }

    override fun getItem(position: Int): Category? {
        return super.getItem(position)
    }

}
