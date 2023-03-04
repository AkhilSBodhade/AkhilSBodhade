package com.mutefrog.myfolio.view.adapter

import com.mutefrog.myfolio.model.IOU

interface PIOUListItemMenuClickListener {

    fun onEditClick(iou: IOU)

    fun onDeleteClick(iou: IOU)

}