package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentPersonalInformationBinding
import com.mutefrog.myfolio.model.User
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.CompleteSignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class PersonalInformationFragment : Fragment() {

    private var _binding: FragmentPersonalInformationBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: CompleteSignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalInformationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            getString(R.string.personal_information)
        )
        hideMainActivityBottomBar(activity)

        progressAlert = getProgressDialog(requireActivity())

        val loggedInUser =
            ModelPreferencesManager.get<User>(getString(R.string.pref_logged_in_user))

        if (loggedInUser != null) {
            setFullName(loggedInUser.name)
            setDOB(loggedInUser.dob)
            setGender(loggedInUser.gender)
             setMobile(loggedInUser.mobile,  loggedInUser.countryCode)
        }

        viewModel.updatePersonalInfoData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    if(dataHandler.data?.success == true){
                        ModelPreferencesManager.put(dataHandler.data?.data?.user, getString(R.string.pref_logged_in_user))
                        activity?.onBackPressed()
                    }
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
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

        binding.txtPersonalInfoName.text = HtmlCompat.fromHtml(
            getString(R.string.enter_full_name),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtPersonalInfoMobileNumber.text = HtmlCompat.fromHtml(
            getString(R.string.enter_mobile_number),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtPersonalInfoDOB.text = HtmlCompat.fromHtml(
            getString(R.string.enter_date_of_birth),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtPersonalInfoGender.text = HtmlCompat.fromHtml(
            getString(R.string.enter_gender),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtPersonalInfoCountryOfResidence.text = HtmlCompat.fromHtml(
            getString(R.string.enter_country_of_residence),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.editTextRegFormDOB.setOnClickListener {
            showDatePicker()
        }

        GlobalScope.launch(Dispatchers.Main) {   //Your Main UI Thread
            val countries: List<String>
            withContext(Dispatchers.IO) {
                countries = binding.editTextRegFormMobileNumber.currentCountries.map { it.name }
            }
            val adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_list_item_activated_1, countries
            )
            binding.spinnerPersonalInfoCountry.adapter = adapter

            val index =  countries.indexOfFirst {
                it.startsWith(loggedInUser?.countryOfResidence.toString())
            }
            binding.spinnerPersonalInfoCountry.setSelection(index)
        }

        binding.btnPersonalInfoSave.setOnClickListener {
            if (isValidForm())
                viewModel.updatePersonalInformation(
                    getAccessToken(requireActivity()),
                    getFullName(),
                    getPhoneCode(),
                    getCountryCode(),
                    getMobile(),
                    getDOB(),
                    getGender(),
                    getCountryOfResidence()
                )
            else
                displaySnackbarAlert(
                    requireActivity(),
                    root,
                    getString(R.string.msg_enter_valid_details)
                )
        }

        return root
    }

    private fun getFullName(): String {
        return binding.editTextRegFormFullName.editableText.toString()
    }

    private fun setFullName(name: String?) {
        binding.editTextRegFormFullName.setText(name)
    }

    private fun getPhoneCode(): String {
        return binding.editTextRegFormMobileNumber.getSelectedCountry().countryCodeFormatted
    }

    private fun getCountryCode(): String {
        return binding.editTextRegFormMobileNumber.getSelectedCountry().iso2
    }

    private fun getMobile(): String {
        return binding.editTextRegFormMobileNumber.getFullPhoneNumber()
    }

    private fun setMobile(number: String?, countryCode: String?) {
        binding.editTextRegFormMobileNumber.setDefaultCountry(countryCode!!)
        binding.editTextRegFormMobileNumber.findViewById<AppCompatEditText>(com.yasserakbbach.phonenumberpicker.R.id.phone_number).setText(number.toString())
    }

    private fun getDOB(): String {
        return binding.editTextRegFormDOB.editableText.toString()
    }

    private fun setDOB(dob: String?) {
        binding.editTextRegFormDOB.setText(dob)
    }

    private fun getGender(): String {
        return binding.spinnerRegFormGender.selectedItem.toString()
    }

    private fun setGender(gender: String?) {
        if (gender == "Male") {
            binding.spinnerRegFormGender.setSelection(0)
        } else if (gender == "Female") {
            binding.spinnerRegFormGender.setSelection(1)
        } else {
            binding.spinnerRegFormGender.setSelection(2)
        }
    }

    private fun getCountryOfResidence(): String {
        return binding.spinnerPersonalInfoCountry.selectedItem.toString()
    }

    private fun isValidForm(): Boolean {
        return !(getFullName().isEmpty() ||
                getMobile().isEmpty() ||
                getCountryOfResidence().isEmpty())
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            requireActivity(),
            { view, year, monthOfYear, dayOfMonth ->
                val selectedMonth = monthOfYear + 1
                val dateString = java.lang.String.format("%d-%02d-%02d", year, selectedMonth, dayOfMonth)
                binding.editTextRegFormDOB.setText(dateString)
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}