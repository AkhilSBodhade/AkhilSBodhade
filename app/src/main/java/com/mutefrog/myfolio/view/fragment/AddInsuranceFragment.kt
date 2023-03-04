package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentAddInsuranceBinding
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Insurance
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddInsuranceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddInsuranceFragment : Fragment() {

    private var _binding: FragmentAddInsuranceBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddInsuranceViewModel by viewModels()

    var selectedItem: Insurance? = null

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddInsuranceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            var dataJson =
                AddInsuranceFragmentArgs.fromBundle(requireArguments()).dataJson
            selectedItem = Gson().fromJson(dataJson, Insurance::class.java)

            var categoryJson =
                AddInsuranceFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            selectedCategoryItem?.insuranceType.toString()
        )
        hideMainActivityBottomBar(activity)

        setHTMLText(binding.txtAddInsuranceCategory, getString(R.string.add_insurance_category))
        setHTMLText(binding.txtAddInsuranceCompany, getString(R.string.add_insurance_company))
        setHTMLText(binding.txtAddInsuranceType, getString(R.string.add_insurance_type))
        setHTMLText(binding.txtAddInsuranceNumber, getString(R.string.add_insurance_number))
        setHTMLText(binding.txtAddInsuranceHolder, getString(R.string.add_insurance_holder_name))
        setHTMLText(binding.txtAddInsuranceCoverAmt, getString(R.string.add_insurance_cover_amt))
        setHTMLText(
            binding.txtAddInsurancePremiumAmt,
            getString(R.string.add_insurance_premium_amt)
        )

        binding.imgShareNowInfo.setOnClickListener { }

        if (selectedItem != null) {
            binding.editAddInsuranceCategory.setText(selectedItem!!.getInsuranceCategoryValue())
            binding.editAddInsuranceCompany.setText(selectedItem!!.getInsuranceCompanyNameValue())
            binding.editAddInsuranceType.setText(selectedItem!!.getInsuranceTypeValue())
            binding.editAddInsuranceNumber.setText(selectedItem!!.getInsuranceNumberValue())
            binding.editAddInsuranceHolder.setText(selectedItem!!.getInsuranceHolderNameValue())
            binding.editAddInsuranceCoverAmt.setText(selectedItem!!.getTotalCoverAmountValue())
            binding.editAddInsurancePremiumAmt.setText(selectedItem!!.getPremiumAmountValue())
            binding.editAddInsuranceComment.setText(selectedItem!!.note)
            setShareNowStatus(selectedItem?.share)
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

                viewModel.addUpdateInsurance(
                    id,
                    getAccessToken(requireActivity()),
                    selectedCategoryItem?.id,
                    getType().encrypt(),
                    getHolderName().encrypt(),
                    getCompany().encrypt(),
                    getNumber().encrypt(),
                    getCoverAmt().encrypt(),
                    getPremiumAmt().encrypt(),
                    getComment(),
                    getShareNowStatus(),
                    getInsuranceCategory().encrypt()
                )
            } else
                displaySnackbarAlert(
                    requireActivity(),
                    root,
                    getString(R.string.msg_enter_valid_details)
                )
        }
    }

    private fun isValidForm(): Boolean {
        return !(getCompany().isEmpty() ||
                getHolderName().isEmpty() ||
                getNumber().isEmpty())
    }

    private fun getCompany(): String {
        return binding.editAddInsuranceCompany.editableText.toString()
    }

    private fun getType(): String {
        return binding.editAddInsuranceType.editableText.toString()
    }

    private fun getNumber(): String {
        return binding.editAddInsuranceNumber.editableText.toString()
    }

    private fun getHolderName(): String {
        return binding.editAddInsuranceHolder.editableText.toString()
    }

    private fun getCoverAmt(): String {
        return binding.editAddInsuranceCoverAmt.editableText.toString()
    }

    private fun getPremiumAmt(): String {
        return binding.editAddInsurancePremiumAmt.editableText.toString()
    }

    private fun getComment(): String {
        return binding.editAddInsuranceComment.editableText.toString()
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddInsuranceShareNow.isChecked)
            1
        else
            0
    }

    private fun getInsuranceCategory(): String {
        return binding.editAddInsuranceCategory.editableText.toString()
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddInsuranceShareNow.isChecked = isSet == 1
    }


}