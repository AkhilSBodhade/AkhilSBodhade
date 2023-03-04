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
import com.mutefrog.myfolio.databinding.FragmentAddDocumentBinding
import com.mutefrog.myfolio.model.Asset
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Document
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddAssetViewModel
import com.mutefrog.myfolio.viewmodel.AddDocumentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDocumentFragment : Fragment() {

    private var _binding: FragmentAddDocumentBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddDocumentViewModel by viewModels()

    var selectedItem: Document? = null

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDocumentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            var dataJson =
                AddDocumentFragmentArgs.fromBundle(requireArguments()).dataJson
            selectedItem = Gson().fromJson(dataJson, Document::class.java)

            var categoryJson =
                AddDocumentFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            "Add " + selectedCategoryItem?.documentType.toString()
        )
        hideMainActivityBottomBar(activity)

        setHTMLText(binding.txtAddDocumentType, getString(R.string.add_document_type))
        setHTMLText(binding.txtAddDocumentName, getString(R.string.add_document_name_of_holder))
        setHTMLText(binding.txtAddDocumentNumber, getString(R.string.add_document_number))
        setHTMLText(
            binding.txtAddDocumentMedType,
            getString(R.string.add_document_med_report_type)
        )
        setHTMLText(
            binding.txtAddDocumentMedDate,
            getString(R.string.add_document_med_report_date)
        )
        setHTMLText(binding.txtAddDocumentPresType, getString(R.string.add_document_presc_type))
        setHTMLText(binding.txtAddDocumentPresDate, getString(R.string.add_document_presc_date))

        binding.imgShareNowInfo.setOnClickListener {
            showShareNowAlert(requireContext())
        }

        manageFormContentVisibility()

        if (selectedItem != null) {
            setShareNowStatus(selectedItem!!.share)
            binding.editAddDocumentName.setText(selectedItem?.documentData?.getDocumentHolderNameValue())
            binding.editAddDocumentNumber.setText(selectedItem?.documentData?.getDocumentNumberValue())
            binding.editAddDocumentMedDate.setText(selectedItem?.documentData?.getMedicalDateValue())
            binding.editAddDocumentPresDate.setText(selectedItem?.documentData?.getPrescriptionDateValue())
            binding.editAddDocumentMedType.setText(selectedItem?.documentData?.getMedicalTypeValue())
            binding.editAddDocumentPresType.setText(selectedItem?.documentData?.getPrescriptionTypeValue())
            binding.editAddDocumentComment.setText(selectedItem?.comment)
        }

        binding.editAddDocumentMedDate.setOnClickListener {
            setDate(requireActivity(), binding.editAddDocumentMedDate)
        }

        binding.editAddDocumentPresDate.setOnClickListener {
            setDate(requireActivity(), binding.editAddDocumentPresDate)
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

                viewModel.addUpdateDocument(
                    id,
                    getAccessToken(requireActivity()),
                    selectedCategoryItem?.id,
                    getName().encrypt(),
                    getNumber()?.encrypt(),
                    getMedType().encrypt(),
                    getMedDate().encrypt(),
                    getPresType().encrypt(),
                    getPresDate().encrypt(),
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
        if (selectedCategoryItem?.documentType == "Medical Report") {
            binding.llAddDocumentNumber.visibility = View.GONE
            binding.llAddDocumentMed.visibility = View.VISIBLE
            binding.llAddDocumentPres.visibility = View.GONE
        } else if (selectedCategoryItem?.documentType == "Medical Prescription") {
            binding.llAddDocumentNumber.visibility = View.GONE
            binding.llAddDocumentMed.visibility = View.GONE
            binding.llAddDocumentPres.visibility = View.VISIBLE
        } else {
            binding.llAddDocumentNumber.visibility = View.VISIBLE
            binding.llAddDocumentMed.visibility = View.GONE
            binding.llAddDocumentPres.visibility = View.GONE
        }
    }

    private fun isValidForm(): Boolean {
        if (selectedCategoryItem?.documentType == "Medical Report" ||
            selectedCategoryItem?.documentType == "Medical Prescription"
        )
            return getName().isNotEmpty()
        else
            return !(getName()?.isEmpty() ||
                    getNumber()?.isEmpty() == true)
    }

    private fun getName(): String {
        return binding.editAddDocumentName.editableText.toString()
    }

    private fun getNumber(): String? {
        if (binding.editAddDocumentNumber.editableText.toString().isEmpty())
            return null
        else
            return binding.editAddDocumentNumber.editableText.toString()
    }

    private fun getDocType(): String {
        return binding.spinnerAddDocumentType.selectedItem.toString()
    }

    private fun getMedType(): String {
        return binding.editAddDocumentMedType.editableText.toString()
    }

    private fun getPresType(): String {
        return binding.editAddDocumentPresType.editableText.toString()
    }

    private fun getMedDate(): String {
        return binding.editAddDocumentMedDate.editableText.toString()
    }

    private fun getPresDate(): String {
        return binding.editAddDocumentPresDate.editableText.toString()
    }

    private fun getComment(): String {
        return binding.editAddDocumentComment.editableText.toString()
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddDocumentShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddDocumentShareNow.isChecked = isSet == 1
    }


}