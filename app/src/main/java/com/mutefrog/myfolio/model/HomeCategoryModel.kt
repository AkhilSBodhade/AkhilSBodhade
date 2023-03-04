package com.mutefrog.myfolio.model

class HomeCategoryModel(private var categoryName: String, private var categoryImageId: Int, private var categoryNameId: String) {

    fun getCategoryName(): String {
        return categoryName
    }

    fun getCategoryImageId(): Int {
        return categoryImageId
    }

    fun getCategoryNameId(): String {
        return categoryNameId
    }
}
