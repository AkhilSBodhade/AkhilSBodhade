package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentAddCardBinding
import com.mutefrog.myfolio.model.Card
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddCardFragment : Fragment() {

    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddCardViewModel by viewModels()

    var selectedItem: Card? = null

    var selectedType = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
                var dataJson = AddCardFragmentArgs.fromBundle(requireArguments()).cardDataJson
                selectedItem = Gson().fromJson(dataJson, Card::class.java)
            selectedType = AddCardFragmentArgs.fromBundle(requireArguments()).selectedType
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            "Add " + selectedType + " " + getString(R.string.card)
        )
        hideMainActivityBottomBar(activity)

//        setHTMLText(binding.txtAddCardType, getString(R.string.add_card_type_of_account))
        setHTMLText(binding.txtAddCardInstituteName, getString(R.string.add_card_institute_name))
        setHTMLText(binding.txtAddCardNumber, getString(R.string.add_card_number))
        setHTMLText(binding.txtAddCardExpiryDate, getString(R.string.add_card_expiry_date))
        setHTMLText(binding.txtAddCardLimit, getString(R.string.add_card_limit))
        setHTMLText(binding.txtAddCardComment, getString(R.string.add_comment))

        binding.imgShareNowInfo.setOnClickListener {
            showShareNowAlert(requireContext())
        }

        binding.editAddCardExpiryDate.setOnClickListener {
            showDatePicker()
        }

        if (selectedItem != null) {
//            setCardType(selectedItem?.cardType)
            setCardInstituteName((selectedItem?.getCardBankNameValue()))
            setCardNumber(selectedItem?.getCardNoValue())
            setExpiryDate(selectedItem?.expiryDate)
            setCardLimit(selectedItem?.getCardLimitValue())
            setComment(selectedItem?.comment)
            setShareNowStatus(selectedItem?.share)
        }

        if(selectedType == "Debit"){
            binding.editAddCardLimit.visibility = View.GONE
            binding.txtAddCardLimit.visibility = View.GONE
        }else{
            binding.editAddCardLimit.visibility = View.VISIBLE
            binding.txtAddCardLimit.visibility = View.VISIBLE
        }

        viewModel.addCardData.observe(viewLifecycleOwner) { dataHandler ->
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

                viewModel.addUpdateCard(
                    id,
                    getAccessToken(requireActivity()),
                    getCardType(),
                    getCardInstituteName().encrypt(),
                    getCardNumber().encrypt(),
                    getExpiryDate(),
                    getCardLimit().encrypt(),
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
        return !(getCardInstituteName().isEmpty() ||
                getCardNumber().isEmpty() ||
                getCardNumber().length < 4)
    }

    private fun getCardType(): Int {
        return if (selectedType == "Debit")
            1
        else
            2
    }

//    private fun setCardType(accountType: String?) {
//        if (accountType == "1") {
//            binding.spinnerAddCardType.setSelection(0)
//        } else {
//            binding.spinnerAddCardType.setSelection(1)
//        }
//    }

    private fun getCardInstituteName(): String {
        return binding.editAddCardInstituteName.editableText.toString()
    }

    private fun setCardInstituteName(instName: String?) {
        binding.editAddCardInstituteName.setText(instName)
    }

    private fun getCardNumber(): String {
        return binding.editAddCardNumber.editableText.toString()
    }

    private fun setCardNumber(cardNumber: String?) {
        binding.editAddCardNumber.setText(cardNumber)
    }

    private fun getExpiryDate(): String {
        return binding.editAddCardExpiryDate.editableText.toString()
    }

    private fun setExpiryDate(expDate: String?) {
        binding.editAddCardExpiryDate.setText(expDate)
    }

    private fun getCardLimit(): String {
        return binding.editAddCardLimit.editableText.toString()
    }

    private fun setCardLimit(cardLimit: String?) {
        binding.editAddCardLimit.setText(cardLimit)
    }

    private fun getComment(): String {
        return binding.editAddBankComment.editableText.toString()
    }

    private fun setComment(comment: String?) {
        binding.editAddBankComment.setText(comment)
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddCardShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddCardShareNow.isChecked = isSet == 1
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
                binding.editAddCardExpiryDate.setText(dateString)
            },
            year,
            month,
            day
        )
        dpd.show()
    }


}