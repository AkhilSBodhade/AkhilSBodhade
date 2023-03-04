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
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentAddNomineeBinding
import com.mutefrog.myfolio.model.Nominees
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddNomineeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddNomineeFragment : Fragment() {

    private var _binding: FragmentAddNomineeBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddNomineeViewModel by viewModels()

    var selectedItem: Nominees? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNomineeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.add_nominee_details))
        hideMainActivityBottomBar(activity)

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            var dataJson = AddNomineeFragmentArgs.fromBundle(requireArguments()).nomineeDataJson
            selectedItem = Gson().fromJson(dataJson, Nominees::class.java)
        }

        setHTMLText(binding.txtAddNomineeName, getString(R.string.add_nominee_name))
        setHTMLText(binding.txtAddNomineeDob, getString(R.string.add_nominee_dob))
        setHTMLText(binding.txtAddNomineeGender, getString(R.string.add_nominee_gender))
        setHTMLText(binding.txtAddNomineeMobile, getString(R.string.add_nominee_mobile))
        setHTMLText(binding.txtAddNomineeEmail, getString(R.string.add_nominee_email))
        setHTMLText(binding.txtAddNomineeAadhar, getString(R.string.add_nominee_aadhar))
        setHTMLText(binding.txtAddNomineeRelationship, getString(R.string.add_nominee_relationship))

        binding.editAddNomineeDob.setOnClickListener {
            showDatePicker()
        }

        if (selectedItem != null) {
            binding.editAddNomineeName.setText(selectedItem?.nomineeName)
            binding.editAddNomineeDob.setText(selectedItem?.nomineeDob)
            when (selectedItem?.nomineeGender) {
                "Male" -> binding.spinnerAddNomineeGender.setSelection(0)
                "Female" -> binding.spinnerAddNomineeGender.setSelection(1)
                else -> binding.spinnerAddNomineeGender.setSelection(2)
            }
            binding.editAddNomineeMobile.setText(selectedItem?.nomineeMobile)
            binding.editAddNomineeEmail.setText(selectedItem?.nomineeEmail)
            binding.editAddNomineeAadhar.setText(selectedItem?.nomineeAadhaarCard)
            binding.editAddNomineeRelationship.setText(selectedItem?.relationshipWithNominee)
        }

        viewModel.addData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                    if (dataHandler.data?.success == true) {
                        val bundle = Bundle()
                        bundle.putString(
                            "id",
                            dataHandler.data?.data?.id.toString()
                        )
                        Navigation.findNavController(binding.root)
                            .navigate(
                                R.id.action_navigation_add_nominee_to_navigation_nominee_otp,
                                bundle
                            )
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

                viewModel.addNominee(
                    id,
                    getAccessToken(requireActivity()),
                    getName(),
                    getDob(),
                    getGender(),
                    getMobile(),
                    getEmail(),
                    getAadhar(),
                    getRelationship()
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
        return !(getName().isEmpty() ||
                getDob().isEmpty() ||
                getMobile().isEmpty() ||
                getEmail().isEmpty())
    }

    private fun getName(): String {
        return binding.editAddNomineeName.editableText.toString()
    }

    private fun getGender(): String {
        return when (binding.spinnerAddNomineeGender.selectedItemPosition) {
            0 -> "Male"
            1 -> "Female"
            else -> "Other"
        }
    }

    private fun getDob(): String {
        return binding.editAddNomineeDob.editableText.toString()
    }

    private fun getMobile(): String {
        return binding.editAddNomineeMobile.editableText.toString()
    }

    private fun getEmail(): String {
        return binding.editAddNomineeEmail.editableText.toString()
    }

    private fun getAadhar(): String {
        return binding.editAddNomineeAadhar.editableText.toString()
    }

    private fun getRelationship(): String {
        return binding.editAddNomineeRelationship.editableText.toString()
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
                val dateString = java.lang.String.format("%d-%02d-%02d", year, month, dayOfMonth)
                binding.editAddNomineeDob.setText(dateString)
            },
            year,
            month,
            day
        )
        dpd.show()
    }


}