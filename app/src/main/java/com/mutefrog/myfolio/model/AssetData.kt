package com.mutefrog.myfolio.model

import com.google.gson.annotations.SerializedName
import com.mutefrog.myfolio.utils.decrypt


data class AssetData (

  @SerializedName("owner_name"         ) var ownerName         : String? = null,
  @SerializedName("property_type"      ) var propertyType      : String? = null,
  @SerializedName("property_name"      ) var propertyName      : String? = null,
  @SerializedName("property_address"   ) var propertyAddress   : String? = null,
  @SerializedName("value_of_property"  ) var valueOfProperty   : String? = null,
  @SerializedName("status"             ) var status            : String? = null,
  @SerializedName("vehicle_type"       ) var vehicleType       : String? = null,
  @SerializedName("vehicle_brand"      ) var vehicleBrand      : String? = null,
  @SerializedName("registration_no"    ) var registrationNo    : String? = null,
  @SerializedName("chassis_no"         ) var chassisNo         : String? = null,
  @SerializedName("insurance_validity" ) var insuranceValidity : String? = null,
  @SerializedName("asset_type"         ) var assetType         : String? = null,
  @SerializedName("asset_name"         ) var assetName         : String? = null,
  @SerializedName("asset_weight"       ) var assetWeight       : String? = null,
  @SerializedName("asset_quantity"     ) var assetQuantity     : String? = null,
  @SerializedName("comment"            ) var comment           : String? = null


){
  fun getPropertyTypeValue() = propertyType?.decrypt()
  fun getOwnerNameValue() = ownerName?.decrypt()
  fun getPropertyNameValue() = propertyName?.decrypt()
  fun getPropertyAddressValue() = propertyAddress?.decrypt()
  fun getValueOfPropertyValue() = valueOfProperty?.decrypt()
  fun getVehicleTypeValue() = vehicleType?.decrypt()
  fun getVehicleBrandValue() = vehicleBrand?.decrypt()
  fun getRegistrationNoValue() = registrationNo?.decrypt()
  fun getChassisNoValue() = chassisNo?.decrypt()
  fun getAssetTypeValue() = assetType?.decrypt()
  fun getAssetNameValue() = assetName?.decrypt()
  fun getAssetWeightValue() = assetWeight?.decrypt()
  fun getAssetQuantityValue() = assetQuantity?.decrypt()
}