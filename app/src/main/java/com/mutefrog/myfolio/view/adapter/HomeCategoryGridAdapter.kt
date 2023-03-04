package com.mutefrog.myfolio.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.model.HomeCategoryModel

class HomeCategoryGridAdapter(
    context: Context,
    categoriesArrayList: ArrayList<HomeCategoryModel>
) :
    ArrayAdapter<HomeCategoryModel?>(context, 0, categoriesArrayList as List<HomeCategoryModel?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var gridItemView = convertView
        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(context)
                .inflate(R.layout.view_home_grid_category, parent, false)
        }

        val categoryModel: HomeCategoryModel? = getItem(position)
        val categoryNameTextView = gridItemView!!.findViewById<TextView>(R.id.idTVCourse)
        val categoryIconImageView = gridItemView.findViewById<ImageView>(R.id.idIVcourse)

        categoryNameTextView.text = categoryModel?.getCategoryName()
        if (categoryModel != null) {
            categoryIconImageView.setImageResource(categoryModel.getCategoryImageId())
        }
        return gridItemView
    }
}
