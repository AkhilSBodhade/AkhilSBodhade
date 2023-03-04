package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentAddAssetBinding
import com.mutefrog.myfolio.model.Asset
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddAssetViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddAssetFragment : Fragment() {

    private var _binding: FragmentAddAssetBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddAssetViewModel by viewModels()

    var selectedItem: Asset? = null

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAssetBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            var dataJson =
                AddAssetFragmentArgs.fromBundle(requireArguments()).dataJson
            selectedItem = Gson().fromJson(dataJson, Asset::class.java)

            var categoryJson =
                AddAssetFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            "Add " + selectedCategoryItem?.assetType.toString()
        )
        hideMainActivityBottomBar(activity)

        setHTMLText(
            binding.txtAddAssetPropertyAddress,
            getString(R.string.add_asset_property_address)
        )
        setHTMLText(binding.txtAddAssetPropertyName, getString(R.string.add_asset_property_name))
        setHTMLText(binding.txtAddAssetPropertyOwner, getString(R.string.add_asset_property_owner))
        setHTMLText(binding.txtAddAssetPropertyType, getString(R.string.add_asset_property_type))
        setHTMLText(binding.txtAddAssetPropertyValue, getString(R.string.add_asset_property_value))
        setHTMLText(
            binding.txtAddAssetPropertyStatus,
            getString(R.string.add_asset_property_status)
        )

        setHTMLText(binding.txtAddAssetVehicleBrand, getString(R.string.add_asset_vehicle_brand))
        setHTMLText(
            binding.txtAddAssetVehicleChassisNumber,
            getString(R.string.add_asset_vehicle_ch_num)
        )
        setHTMLText(binding.txtAddAssetVehicleOwner, getString(R.string.add_asset_vehicle_owner))
        setHTMLText(binding.txtAddAssetVehicleType, getString(R.string.add_asset_vehicle_type))
        setHTMLText(
            binding.txtAddAssetVehicleRegNumber,
            getString(R.string.add_asset_vehicle_reg_num)
        )
        setHTMLText(
            binding.txtAddAssetVehicleInsuranceValidity,
            getString(R.string.add_asset_vehicle_insurance)
        )

        setHTMLText(binding.txtAddAssetAstOwner, getString(R.string.add_asset_ast_owner))
        setHTMLText(binding.txtAddAssetAstQty, getString(R.string.add_asset_ast_qty))
        setHTMLText(binding.txtAddAssetAstName, getString(R.string.add_asset_ast_name))
        setHTMLText(binding.txtAddAssetAstType, getString(R.string.add_asset_ast_type))
        setHTMLText(binding.txtAddAssetAstWeight, getString(R.string.add_asset_ast_weight))

        setHTMLText(binding.txtAddAssetCltOwner, getString(R.string.add_asset_clt_owner))
        setHTMLText(binding.txtAddAssetCltQty, getString(R.string.add_asset_clt_qty))
        setHTMLText(binding.txtAddAssetCltName, getString(R.string.add_asset_clt_name))
        setHTMLText(binding.txtAddAssetCltType, getString(R.string.add_asset_clt_type))

        binding.imgShareNowInfo.setOnClickListener {
            showShareNowAlert(requireContext())
        }

        manageFormContentVisibility()

        if (selectedItem != null) {

            binding.editAddAssetPropertyAddress.setText(selectedItem!!.assetData?.getPropertyAddressValue())
            binding.editAddAssetPropertyName.setText(selectedItem!!.assetData?.getPropertyNameValue())
            binding.editAddAssetPropertyOwner.setText(selectedItem!!.assetData?.getOwnerNameValue())
            binding.editAddAssetPropertyType.setText(selectedItem!!.assetData?.getPropertyTypeValue())
            binding.editAddAssetPropertyValue.setText(selectedItem!!.assetData?.getValueOfPropertyValue())
            binding.editAddAssetPropertyStatus.setText(selectedItem!!.assetData?.status)

            binding.editAddAssetVehicleBrand.setText(selectedItem!!.assetData?.getVehicleBrandValue())
            binding.editAddAssetVehicleChassisNumber.setText(selectedItem!!.assetData?.getChassisNoValue())
            binding.editAddAssetVehicleOwner.setText(selectedItem!!.assetData?.getOwnerNameValue())
            binding.editAddAssetVehicleType.setText(selectedItem!!.assetData?.getVehicleTypeValue())
            binding.editAddAssetVehicleRegNumber.setText(selectedItem!!.assetData?.getRegistrationNoValue())
            binding.editAddAssetVehicleInsuranceValidity.setText(selectedItem!!.assetData?.insuranceValidity)

            binding.editAddAssetAstOwner.setText(selectedItem!!.assetData?.getOwnerNameValue())
            binding.editAddAssetAstQty.setText(selectedItem!!.assetData?.getAssetQuantityValue())
            binding.editAddAssetAstName.setText(selectedItem!!.assetData?.getAssetNameValue())
            binding.editAddAssetAstType.setText(selectedItem!!.assetData?.getAssetTypeValue())
            binding.editAddAssetAstWeight.setText(selectedItem!!.assetData?.getAssetWeightValue())

            binding.editAddAssetCltOwner.setText(selectedItem!!.assetData?.getOwnerNameValue())
            binding.editAddAssetCltQty.setText(selectedItem!!.assetData?.getAssetQuantityValue())
            binding.editAddAssetCltName.setText(selectedItem!!.assetData?.getAssetNameValue())
            binding.editAddAssetCltType.setText(selectedItem!!.assetData?.getAssetTypeValue())

            binding.editAddAssetComment.setText(selectedItem!!.assetData?.comment)
            setShareNowStatus(selectedItem!!.share)

        }

        viewModel.addData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                    if (dataHandler.data?.success == true) {
                        activity?.onBackPressed()
                    }
                }
                is DataHandler.ERROR -> {
                    progressAlert?.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
                    progressAlert?.show()
                }
            }
        }

        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).text =
            getString(R.string.save)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            if (isValidForm()) {
                var id: Int? = null
                if (selectedItem != null)
                    id = selectedItem?.id

                viewModel.addUpdateAsset(
                    id,
                    getAccessToken(requireActivity()),
                    selectedCategoryItem?.id,
                    getOwner().encrypt(),
                    getPropertyType().encrypt(),
                    getPropertyName().encrypt(),
                    getPropertyAddress().encrypt(),
                    getPropertyValue().encrypt(),
                    getPropertyStatus(),
                    getVehicleType().encrypt(),
                    getVehicleBrand().encrypt(),
                    getVehicleRegNumber().encrypt(),
                    getVehicleChassisNumber().encrypt(),
                    getVehicleInsuranceValidity(),
                    getAstCltType().encrypt(),
                    getAstCltName().encrypt(),
                    getAstWeight().encrypt(),
                    getAstCltQty().encrypt(),
                    getComment(),
                    getShareNowStatus()
                )
            } else
                displaySnackbarAlert(
                    requireActivity(),
                    root,
                    getString(R.string.msg_enter_valid_details)
                )
        }
    }

    private fun manageFormContentVisibility() {
        if (selectedCategoryItem?.assetType == "Property") {
            binding.llAddAssetItemProperty.visibility = View.VISIBLE
            binding.llAddAssetItemVehicle.visibility = View.GONE
            binding.llAddAssetItemLiquidity.visibility = View.GONE
            binding.llAddAssetItemCollectable.visibility = View.GONE
        } else if (selectedCategoryItem?.assetType == "Vehicle") {
            binding.llAddAssetItemProperty.visibility = View.GONE
            binding.llAddAssetItemVehicle.visibility = View.VISIBLE
            binding.llAddAssetItemLiquidity.visibility = View.GONE
            binding.llAddAssetItemCollectable.visibility = View.GONE
        } else if (selectedCategoryItem?.assetType == "Liquidity") {
            binding.llAddAssetItemProperty.visibility = View.GONE
            binding.llAddAssetItemVehicle.visibility = View.GONE
            binding.llAddAssetItemLiquidity.visibility = View.VISIBLE
            binding.llAddAssetItemCollectable.visibility = View.GONE
        } else {
            binding.llAddAssetItemProperty.visibility = View.GONE
            binding.llAddAssetItemVehicle.visibility = View.GONE
            binding.llAddAssetItemLiquidity.visibility = View.GONE
            binding.llAddAssetItemCollectable.visibility = View.VISIBLE
        }
    }

    private fun isValidForm(): Boolean {
        if (selectedCategoryItem?.assetType == "Property")
            return !(getPropertyType().isEmpty() ||
                    getPropertyName().isEmpty() ||
                    getPropertyOwner().isEmpty() ||
                    getPropertyAddress().isEmpty())
        else if (selectedCategoryItem?.assetType == "Vehicle")
            return !(getVehicleType().isEmpty() ||
                    getVehicleBrand().isEmpty() ||
                    getVehicleOwner().isEmpty() ||
                    getVehicleRegNumber().isEmpty())
        else if (selectedCategoryItem?.assetType == "Liquidity")
            return !(getAstType().isEmpty() ||
                    getAstName().isEmpty() ||
                    getAstOwner().isEmpty())
        else
            return !(getCltType().isEmpty() ||
                    getCltName().isEmpty() ||
                    getCltOwner().isEmpty())
    }

    private fun getOwner(): String {
        if (selectedCategoryItem?.assetType == "Property")
            return getPropertyOwner()
        else if (selectedCategoryItem?.assetType == "Vehicle")
            return getVehicleOwner()
        else if (selectedCategoryItem?.assetType == "Liquidity")
            return getAstOwner()
        else
            return getCltOwner()
    }

    private fun getPropertyName(): String {
        return binding.editAddAssetPropertyName.editableText.toString()
    }

    private fun getPropertyOwner(): String {
        return binding.editAddAssetPropertyOwner.editableText.toString()
    }

    private fun getPropertyAddress(): String {
        return binding.editAddAssetPropertyAddress.editableText.toString()
    }

    private fun getPropertyType(): String {
        return binding.editAddAssetPropertyType.editableText.toString()
    }

    private fun getPropertyValue(): String {
        return binding.editAddAssetPropertyValue.editableText.toString()
    }

    private fun getPropertyStatus(): String {
        return binding.editAddAssetPropertyStatus.editableText.toString()
    }

    private fun getVehicleBrand(): String {
        return binding.editAddAssetVehicleBrand.editableText.toString()
    }

    private fun getVehicleChassisNumber(): String {
        return binding.editAddAssetVehicleChassisNumber.editableText.toString()
    }

    private fun getVehicleOwner(): String {
        return binding.editAddAssetVehicleOwner.editableText.toString()
    }

    private fun getVehicleType(): String {
        return binding.editAddAssetVehicleType.editableText.toString()
    }

    private fun getVehicleRegNumber(): String {
        return binding.editAddAssetVehicleRegNumber.editableText.toString()
    }

    private fun getVehicleInsuranceValidity(): String {
        return binding.editAddAssetVehicleInsuranceValidity.editableText.toString()
    }

    private fun getAstOwner(): String {
        return binding.editAddAssetAstOwner.editableText.toString()
    }

    private fun getAstQty(): String {
        return binding.editAddAssetAstQty.editableText.toString()
    }

    private fun getAstName(): String {
        return binding.editAddAssetAstName.editableText.toString()
    }

    private fun getAstCltType(): String {
        return if (selectedCategoryItem?.assetType == "Liquidity")
            getAstType()
        else
            getCltType()
    }

    private fun getAstCltName(): String {
        return if (selectedCategoryItem?.assetType == "Liquidity")
            getAstName()
        else
            getCltName()
    }

    private fun getAstCltQty(): String {
        return if (selectedCategoryItem?.assetType == "Liquidity")
            getAstQty()
        else
            getCltQty()
    }

    private fun getAstType(): String {
        return binding.editAddAssetAstType.editableText.toString()
    }

    private fun getAstWeight(): String {
        return binding.editAddAssetAstWeight.editableText.toString()
    }

    private fun getCltOwner(): String {
        return binding.editAddAssetCltOwner.editableText.toString()
    }

    private fun getCltQty(): String {
        return binding.editAddAssetCltQty.editableText.toString()
    }

    private fun getCltName(): String {
        return binding.editAddAssetCltName.editableText.toString()
    }

    private fun getCltType(): String {
        return binding.editAddAssetCltType.editableText.toString()
    }

    private fun getComment(): String {
        return binding.editAddAssetComment.editableText.toString()
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddAssetShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddAssetShareNow.isChecked = isSet == 1
    }


}