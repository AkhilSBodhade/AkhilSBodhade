package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentAddPiouBinding
import com.mutefrog.myfolio.model.IOU
import com.mutefrog.myfolio.model.User
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddPersonalIouViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddPiouFragment : Fragment() {

    private var _binding: FragmentAddPiouBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddPersonalIouViewModel by viewModels()

    var selectedPiou: IOU? = null

    var loggedInUser: User? = null

    var selectedType = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPiouBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.lend))
        hideMainActivityBottomBar(activity)

        progressAlert = getProgressDialog(requireActivity())

        loggedInUser =
            ModelPreferencesManager.get<User>(getString(R.string.pref_logged_in_user))

        if (arguments != null) {
            var dataJson = AddPiouFragmentArgs.fromBundle(requireArguments()).dataJson
            selectedPiou = Gson().fromJson(dataJson, IOU::class.java)
            selectedType = AddPiouFragmentArgs.fromBundle(requireArguments()).selectedType
        }

//        setHTMLText(binding.txtIAmThe, getString(R.string.add_lender_im_the))
        setHTMLText(binding.txtLenderReason, getString(R.string.add_lender_reason_for_lending))
        setHTMLText(binding.txtLenderAmount, getString(R.string.add_lender_total_amount))
        setHTMLText(binding.txtLenderBorrowerName, getString(R.string.add_lender_borrower_name))
        setHTMLText(binding.txtCurrency, getString(R.string.add_lender_currency))
        setHTMLText(binding.txtLenderAmountReturned, getString(R.string.add_lender_amount_returned))
        setHTMLText(binding.txtLenderPendingAmount, getString(R.string.add_lender_amount_pending))
        setHTMLText(binding.txtLenderDeadline, getString(R.string.add_lender_deadline))
        setHTMLText(binding.txtLenderComments, getString(R.string.add_comment))


        if(selectedType == "Lent"){
            manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.lend))

            setHTMLText(
                binding.txtLenderReason,
                getString(R.string.add_lender_reason_for_lending)
            )
            setHTMLText(
                binding.txtLenderBorrowerName,
                getString(R.string.add_lender_borrower_name)
            )
        }

         if(selectedType == "Borrowed")  {
             manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.borrowed))

             setHTMLText(
                 binding.txtLenderReason,
                 getString(R.string.add_borrower_reason_for_borrowing)
             )
             setHTMLText(
                 binding.txtLenderBorrowerName,
                 getString(R.string.add_borrower_lender_name)
             )
         }


//        binding.spinnerIAmThe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                if (position == 0) {  // Lender
//                    selectedType = 1
//
//                    manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.lend))
//
//                    setHTMLText(
//                        binding.txtLenderReason,
//                        getString(R.string.add_lender_reason_for_lending)
//                    )
//                    setHTMLText(
//                        binding.txtLenderBorrowerName,
//                        getString(R.string.add_lender_borrower_name)
//                    )
//                }
//
//                if (position == 1) {  // Borrower
//                    selectedType = 2
//
//                    manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.borrowed))
//
//                    setHTMLText(
//                        binding.txtLenderReason,
//                        getString(R.string.add_borrower_reason_for_borrowing)
//                    )
//                    setHTMLText(
//                        binding.txtLenderBorrowerName,
//                        getString(R.string.add_borrower_lender_name)
//                    )
//                }
//            }
//        }

        binding.imgShareNowInfo.setOnClickListener {
            showShareNowAlert(requireContext())
        }

        binding.editLenderDeadline.setOnClickListener {
            showDatePicker()
        }

        if (selectedPiou != null) {
//            setType(selectedPiou?.type)
            setReason((selectedPiou?.reason))
            if (selectedPiou?.type == "1")
                setLenderOrBorrowerName(selectedPiou?.getBorrowerNameValue())
            else
                setLenderOrBorrowerName(selectedPiou?.getLenderNameValue())
            setCurrencyType(selectedPiou?.currencyTypeId)
            setAmount(selectedPiou?.getTotalAmountValue())
            setAmountReturned(selectedPiou?.getAmountReturnedValue())
            setPendingAmount(selectedPiou?.getPendingAmountValue())
            setDeadline(selectedPiou?.deadline)
            setComment(selectedPiou?.comment)
            setShareNowStatus(selectedPiou?.share)
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

                if (selectedPiou != null)
                    id = selectedPiou?.id

                viewModel.addPersonalIou(
                    id,
                    getAccessToken(requireActivity()),
                    getType(),
                    getReason().encrypt(),
                    getLenderName().encrypt(),
                    getLenderOrBorrowerName().encrypt(),
                    getAmount().encrypt(),
                    getCurrencyType(),
                    getAmountReturned().encrypt(),
                    getPendingAmount().encrypt(),
                    getDeadline(),
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
        return !(getLenderName().isEmpty() ||
                getReason().isEmpty() ||
                getAmount().isEmpty() ||
                getLenderOrBorrowerName().isEmpty())
    }

    private fun getType(): Int {
        return if (selectedType == "Lent")
            1
        else
            2
    }

//    private fun setType(accountType: String?) {
//        if (accountType == "1") {
//            binding.spinnerIAmThe.setSelection(0)
//        } else {
//            binding.spinnerIAmThe.setSelection(1)
//        }
//    }

    private fun getLenderName(): String {
        if (selectedType == "Lent")
            return loggedInUser?.name!!
        else
            return binding.editLenderBorrowerName.editableText.toString()
    }

    private fun getReason(): String {
        return binding.editLenderReason.editableText.toString()
    }

    private fun setReason(reason: String?) {
        binding.editLenderReason.setText(reason)
    }

    private fun getAmount(): String {
        return binding.editLenderAmount.editableText.toString()
    }

    private fun setAmount(amount: String?) {
        binding.editLenderAmount.setText(amount)
    }

    private fun getCurrencyType(): Int {
        return binding.spinnerCurrency.selectedItemPosition + 1
    }

    private fun setCurrencyType(currencyType: String?) {
        if (currencyType == "1") {
            binding.spinnerCurrency.setSelection(0)
        } else {
            binding.spinnerCurrency.setSelection(1)
        }
    }

    private fun getLenderOrBorrowerName(): String {
        if (selectedType == "Borrowed")
            return loggedInUser?.name!!
        else
            return binding.editLenderBorrowerName.editableText.toString()
    }

    private fun setLenderOrBorrowerName(name: String?) {
        binding.editLenderBorrowerName.setText(name)
    }

    private fun getAmountReturned(): String {
        return binding.editLenderAmountReturned.editableText.toString()
    }

    private fun setAmountReturned(amountReturned: String?) {
        binding.editLenderAmountReturned.setText(amountReturned)
    }

    private fun getPendingAmount(): String {
        return binding.editLenderPendingAmount.editableText.toString()
    }

    private fun setPendingAmount(pendingAmount: String?) {
        binding.editLenderPendingAmount.setText(pendingAmount)
    }

    private fun getDeadline(): String {
        return binding.editLenderDeadline.editableText.toString()
    }

    private fun setDeadline(deadline: String?) {
        binding.editLenderDeadline.setText(deadline)
    }

    private fun getComment(): String {
        return binding.editLenderComments.editableText.toString()
    }

    private fun setComment(comment: String?) {
        binding.editLenderComments.setText(comment)
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddPiouShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddPiouShareNow.isChecked = isSet == 1
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            requireActivity(),
            { view, year, monthOfYear, dayOfMonth ->
                val month = monthOfYear + 1
                val dateString = java.lang.String.format("%d-%02d-%02d", year, month, day)
                binding.editLenderDeadline.setText(dateString)
            },
            year,
            month,
            day
        )
        dpd.show()
    }

}