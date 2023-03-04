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
import com.mutefrog.myfolio.databinding.FragmentAddLoanBinding
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Loan
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddLoanViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddLoanFragment : Fragment() {

    private var _binding: FragmentAddLoanBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddLoanViewModel by viewModels()

    var selectedLoanItem: Loan? = null

    var selectedCategoryItem: Category? = null

    var categoryList: List<Category>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddLoanBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            var loanJson =
                AddLoanFragmentArgs.fromBundle(requireArguments()).loanJson
            selectedLoanItem = Gson().fromJson(loanJson, Loan::class.java)

            var categoryJson =
                AddLoanFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
        }

        manageMainActivityActionBar(activity, View.VISIBLE, "Add " + selectedCategoryItem?.loanType)
        hideMainActivityBottomBar(activity)

        val categoryListJson =
            ModelPreferencesManager.get<String>(getString(R.string.pref_category_list))
        categoryList =
            Gson().fromJson(categoryListJson, Array<Category>::class.java).toList()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categoryList!!.map { it.loanType }.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAddLoanType.adapter = adapter

        setHTMLText(binding.txtAddLoanType, getString(R.string.add_loan_type))
        setHTMLText(binding.txtAddLoanPurpose, getString(R.string.add_loan_purpose))
        setHTMLText(binding.txtAddLoanInstitute, getString(R.string.add_loan_inst_name))
        setHTMLText(binding.txtAddLoanAccHolderName, getString(R.string.add_loan_acc_holder_name))
        setHTMLText(binding.txtAddLoanAccNumber, getString(R.string.add_loan_account_number))
        setHTMLText(binding.txtAddLoanAmount, getString(R.string.add_loan_amount))
        setHTMLText(binding.txtAddLoanEmiValue, getString(R.string.add_loan_emi_value))
        setHTMLText(binding.txtAddLoanStartDate, getString(R.string.add_loan_start_date))
        setHTMLText(binding.txtAddLoanEndDate, getString(R.string.add_loan_end_date))
        setHTMLText(binding.txtAddLoanStatus, getString(R.string.add_loan_status))

        binding.imgShareNowInfo.setOnClickListener {
            showShareNowAlert(requireContext())
        }

        binding.editAddLoanStartDate.setOnClickListener {
            setDate(requireActivity(), binding.editAddLoanStartDate)
        }

        binding.editAddLoanEndDate.setOnClickListener {
            setDate(requireActivity(), binding.editAddLoanEndDate)
        }

        if (selectedLoanItem != null) {
            setPurpose(selectedLoanItem?.getPurposeOfLoanValue())
            setInstName(selectedLoanItem?.getLoanInstituteNameValue())
            setAccHolderName((selectedLoanItem?.getAccHolderNameValue()))
            setAccountNumber(selectedLoanItem?.getLoanAccountNoValue())
            setAmount(selectedLoanItem?.getLoanAmountValue())
            setEmiValue(selectedLoanItem?.getEmiValueValue())
            setStartDate(selectedLoanItem?.startDate)
            setEndDate(selectedLoanItem?.endDate)
            setStatus(selectedLoanItem?.status)
            setShareNowStatus(selectedLoanItem?.share)
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
                if (selectedLoanItem != null)
                    id = selectedLoanItem?.id

                viewModel.addUpdateLoan(
                    id,
                    getAccessToken(requireActivity()),
                    selectedCategoryItem?.id,
                    getPurpose().encrypt(),
                    getAccountNumber().encrypt(),
                    getAccHolderName().encrypt(),
                    getAmount().encrypt(),
                    getInstName().encrypt(),
                    getEmiValue().encrypt(),
                    getStartDate(),
                    getEndDate(),
                    getStatus(),
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

    private fun isValidForm(): Boolean {
        return !(getInstName().isEmpty() ||
                getAccHolderName().isEmpty() ||
                getAccountNumber().isEmpty())
    }

    private fun getStatus(): Int {
        return if (binding.rbActive.isChecked)
            0
        else
            1
    }

    private fun getComment(): String {
        return binding.editAddLoanComment.editableText.toString()
    }

    private fun getEndDate(): String {
        return binding.editAddLoanEndDate.editableText.toString()
    }

    private fun getStartDate(): String {
        return binding.editAddLoanStartDate.editableText.toString()
    }

    private fun getEmiValue(): String {
        return binding.editAddLoanEmiValue.editableText.toString()
    }

    private fun getAmount(): String {
        return binding.editAddLoanAmout.editableText.toString()
    }

    private fun getAccHolderName(): String {
        return binding.editAddLoanAccHolderName.editableText.toString()
    }

    private fun getInstName(): String {
        return binding.editAddLoanInstitute.editableText.toString()
    }

    private fun getAccountNumber(): String {
        return binding.editAddLoanAccNumber.editableText.toString()
    }

    private fun getPurpose(): String {
        return binding.editAddLoanPurpose.editableText.toString()
    }

    private fun setStatus(status: String?) {
        if (status == "1")
            binding.rbComplete.isChecked = true
        else
            binding.rbActive.isChecked = true
    }

    private fun setEndDate(endDate: String?) {
        binding.editAddLoanEndDate.setText(endDate)
    }

    private fun setStartDate(startDate: String?) {
        binding.editAddLoanStartDate.setText(startDate)
    }

    private fun setEmiValue(emiValue: String?) {
        binding.editAddLoanEmiValue.setText(emiValue)
    }

    private fun setAmount(loanAmount: String?) {
        binding.editAddLoanAmout.setText(loanAmount)
    }

    private fun setAccountNumber(loanAccountNo: String?) {
        binding.editAddLoanAccNumber.setText(loanAccountNo)
    }

    private fun setAccHolderName(accHolderName: String?) {
        binding.editAddLoanAccHolderName.setText(accHolderName)
    }

    private fun setInstName(loanInstituteName: String?) {
        binding.editAddLoanInstitute.setText(loanInstituteName)
    }

    private fun setPurpose(purposeOfLoan: String?) {
        binding.editAddLoanPurpose.setText(purposeOfLoan)
    }

//    private fun getType(): Int? {
//        return categoryList?.get(binding.spinnerAddLoanType.selectedItemPosition)?.id
//    }
//
//    private fun setType(type: Int?) {
//        binding.spinnerAddLoanType.setSelection(type!!.toInt() - 1)
//    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddLoanShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddLoanShareNow.isChecked = isSet == 1
    }



}