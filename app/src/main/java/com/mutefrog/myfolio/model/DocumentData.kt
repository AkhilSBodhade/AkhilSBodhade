package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class DocumentData(

    @SerializedName("document_holder_name") var documentHolderName: String? = null,
    @SerializedName("document_number") var documentNumber: String? = null,
    @SerializedName("medical_type") var medicalType: String? = null,
    @SerializedName("medical_date") var medicalDate: String? = null,
    @SerializedName("prescription_type") var prescriptionType: String? = null,
    @SerializedName("prescription_date") var prescriptionDate: String? = null

){
    fun getDocumentHolderNameValue() = documentHolderName?.decrypt()
    fun getDocumentNumberValue() = documentNumber?.decrypt()
    fun getMedicalTypeValue() = medicalType?.decrypt()
    fun getMedicalDateValue() = medicalDate?.decrypt()
    fun getPrescriptionTypeValue() = prescriptionType?.decrypt()
    fun getPrescriptionDateValue() = prescriptionDate?.decrypt()
}