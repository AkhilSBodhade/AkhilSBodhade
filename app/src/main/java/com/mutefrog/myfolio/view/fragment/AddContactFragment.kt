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
import com.mutefrog.myfolio.databinding.FragmentAddContactBinding
import com.mutefrog.myfolio.databinding.FragmentAddInsuranceBinding
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Contact
import com.mutefrog.myfolio.model.Insurance
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.CategoryGridAdapter
import com.mutefrog.myfolio.viewmodel.AddContactViewModel
import com.mutefrog.myfolio.viewmodel.AddInsuranceViewModel
import com.mutefrog.myfolio.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : Fragment() {

    private var _binding: FragmentAddContactBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddContactViewModel by viewModels()

    val categoryViewModel: CategoryViewModel by viewModels()

    var selectedItem: Contact? = null

    var contactCatoryItems: ArrayList<Category> = arrayListOf()

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        categoryViewModel.getCategories("contact", getAccessToken(requireActivity()))

        categoryViewModel.getCategoryData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    contactCatoryItems = dataHandler.data?.data?.list!!
                    val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        dataHandler.data?.data?.list!!.map { it.contactType }.toList()
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerAddContactType.adapter = adapter
                    selectedCategoryItem = contactCatoryItems[0]
                }
                is DataHandler.ERROR -> {
                    progressAlert?.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
                    if (isAdded)
                        progressAlert?.show()
                }
            }
        }

        if (arguments != null) {
            var dataJson =
                AddContactFragmentArgs.fromBundle(requireArguments()).dataJson
            selectedItem = Gson().fromJson(dataJson, Contact::class.java)
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            getString(R.string.add_contact)
        )
        hideMainActivityBottomBar(activity)

        setHTMLText(binding.txtAddContactName, getString(R.string.add_contact_name))
        setHTMLText(binding.txtAddContactNumber, getString(R.string.add_contact_number))
        setHTMLText(binding.txtAddContactEmail, getString(R.string.add_contact_email))
        setHTMLText(binding.txtAddContactType, getString(R.string.add_contact_occupation))

        binding.imgShareNowInfo.setOnClickListener { }

        if (selectedItem != null) {
            binding.editAddContactName.setText(selectedItem!!.getNameValue())
            binding.editAddContactNumber.setText(selectedItem!!.getContactNumberValue())
            binding.editAddContactEmail.setText(selectedItem!!.email)
//            binding.editAddContactOccupation.setText(selectedItem!!.)
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

                viewModel.addUpdateContact(
                    id,
                    getAccessToken(requireActivity()),
                    getContactCategoryId(),
                    getName().encrypt(),
                    getNumber().encrypt(),
                    getEmail(),
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

    private fun getContactCategoryId(): Int? {
        return contactCatoryItems[binding.spinnerAddContactType.selectedItemPosition].id
    }

    private fun isValidForm(): Boolean {
        return !(getName().isEmpty() ||
                getNumber().isEmpty())
    }

    private fun getNumber(): String {
        return binding.editAddContactNumber.editableText.toString()
    }

    private fun getName(): String {
        return binding.editAddContactName.editableText.toString()
    }

    private fun getEmail(): String {
        return binding.editAddContactEmail.editableText.toString()
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddContactShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddContactShareNow.isChecked = isSet == 1
    }


}