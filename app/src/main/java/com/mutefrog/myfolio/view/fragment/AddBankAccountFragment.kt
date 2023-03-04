package com.mutefrog.myfolio.view.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentAddBankAccountBinding
import com.mutefrog.myfolio.model.BankAccount
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.activity.AppScanActivity
import com.mutefrog.myfolio.viewmodel.AddBankAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBankAccountFragment : Fragment() {

    private var _binding: FragmentAddBankAccountBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddBankAccountViewModel by viewModels()

    var selectedBankAccount: BankAccount? = null

    var selectedType = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBankAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            var bankAccountJson =
                AddBankAccountFragmentArgs.fromBundle(requireArguments()).bankAccountJson
            selectedBankAccount = Gson().fromJson(bankAccountJson, BankAccount::class.java)
            selectedType = AddBankAccountFragmentArgs.fromBundle(requireArguments()).selectedType
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            "Add " + selectedType + " " + getString(R.string.bank_account)
        )
        hideMainActivityBottomBar(activity)

//        setHTMLText(binding.txtAddBankType, getString(R.string.add_type_of_account))
        setHTMLText(binding.txtAddBankAccHolderName, getString(R.string.add_acc_holder_name))
        setHTMLText(binding.txtAddBankName, getString(R.string.add_bank_name))
        setHTMLText(binding.txtAddBankBranchName, getString(R.string.add_branch_name))
        setHTMLText(binding.txtAddBankAccNumber, getString(R.string.add_bank_acc_number))
        setHTMLText(binding.txtAddBankIfscCode, getString(R.string.add_ifsc_code))
        setHTMLText(binding.txtAddBankCustomerId, getString(R.string.add_customer_id))
        setHTMLText(binding.txtAddBankRelationshipMgr, getString(R.string.add_relationship_manager))
        setHTMLText(binding.txtAddBankComment, getString(R.string.add_comment))

        binding.imgShareNowInfo.setOnClickListener {
            showShareNowAlert(requireContext())
        }

        if (selectedBankAccount != null) {
//            setAccountType(selectedBankAccount?.accountType)
            setAccHolderName((selectedBankAccount?.getAccountHolderNameValue()))
            setBankName(selectedBankAccount?.getBankNameValue())
            setBranchName(selectedBankAccount?.branchName)
            setAccountNumber(selectedBankAccount?.getBankAccountNumberValue())
            setAccountIFSCCode(selectedBankAccount?.getIFSCCodeValue())
            setAccountCustId(selectedBankAccount?.getCustomerIdValue())
            setAccountRelationshipMgr(selectedBankAccount?.relationshipManager)
            setAccountComment(selectedBankAccount?.comment)
        }

        viewModel.addBankAccountData.observe(viewLifecycleOwner) { dataHandler ->
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


        root.findViewById<AppCompatImageView>(R.id.img_upload_file).setOnClickListener {
//            openDocScanner(requireContext())
            val intent = Intent(context, AppScanActivity::class.java)
            getResult.launch(intent)
        }

        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).text =
            getString(R.string.save)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            if (isValidForm()) {
                var bankAccountId: Int? = null
                if (selectedBankAccount != null)
                    bankAccountId = selectedBankAccount?.id

                viewModel.addBankAccount(
                    bankAccountId,
                    getAccessToken(requireActivity()),
                    getAccountType(),
                    getAccHolderName().encrypt(),
                    getBankName().encrypt(),
                    getBranchName(),
                    getAccountNumber().encrypt(),
                    getAccountIFSCCode().encrypt(),
                    getAccountCustId().encrypt(),
                    getAccountRelationshipMgr(),
                    getAccountComment(),
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

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val value = it.data?.getSerializableExtra("image")
            }
        }

    private fun isValidForm(): Boolean {
        return !(getAccHolderName().isEmpty() ||
                getBankName().isEmpty() ||
                getBranchName().isEmpty() ||
                getAccountNumber().isEmpty())
    }

    private fun getAccountType(): Int {
        return if (selectedType == "Saving")
            1
        else
            2
    }

//    private fun setAccountType(accountType: String?) {
//        if (accountType == "1") {
//            binding.spinnerAddBankType.setSelection(0)
//        } else {
//            binding.spinnerAddBankType.setSelection(1)
//        }
//    }

    private fun getAccHolderName(): String {
        return binding.editAddBankAccHolderName.editableText.toString()
    }

    private fun setAccHolderName(accHolderName: String?) {
        binding.editAddBankAccHolderName.setText(accHolderName)
    }

    private fun getBankName(): String {
        return binding.editAddBankName.editableText.toString()
    }

    private fun setBankName(bankName: String?) {
        binding.editAddBankName.setText(bankName)
    }

    private fun getBranchName(): String {
        return binding.editAddBranchName.editableText.toString()
    }

    private fun setBranchName(branchName: String?) {
        binding.editAddBranchName.setText(branchName)
    }

    private fun getAccountNumber(): String {
        return binding.editAddBankAccNumber.editableText.toString()
    }

    private fun setAccountNumber(accountNumber: String?) {
        binding.editAddBankAccNumber.setText(accountNumber)
    }

    private fun getAccountIFSCCode(): String {
        return binding.editAddBankIfscCode.editableText.toString()
    }

    private fun setAccountIFSCCode(ifscCode: String?) {
        binding.editAddBankIfscCode.setText(ifscCode)
    }

    private fun getAccountCustId(): String {
        return binding.editAddBankCustomerId.editableText.toString()
    }

    private fun setAccountCustId(customerId: String?) {
        binding.editAddBankCustomerId.setText(customerId)
    }

    private fun getAccountRelationshipMgr(): String {
        return binding.editAddBankRelationshipMgr.editableText.toString()
    }

    private fun setAccountRelationshipMgr(relationshipMgr: String?) {
        binding.editAddBankRelationshipMgr.setText(relationshipMgr)
    }

    private fun getAccountComment(): String {
        return binding.editAddBankComment.editableText.toString()
    }

    private fun setAccountComment(comment: String?) {
        binding.editAddBankComment.setText(comment)
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddBankShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddBankShareNow.isChecked = isSet == 1
    }


}