package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentCompleteRegistrationBinding
import com.mutefrog.myfolio.databinding.FragmentListBinding
import com.mutefrog.myfolio.model.Token
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.MyFolioListAdapter
import com.mutefrog.myfolio.viewmodel.CompleteSignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class CompleteRegistrationFragment : Fragment() {

    private var _binding: FragmentCompleteRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressAlert: AlertDialog

    val viewModel: CompleteSignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteRegistrationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        manageActionBar(activity, View.VISIBLE, getString(R.string.complete_registration))

        progressAlert = SpotsDialog.Builder()
            .setContext(requireContext())
            .setMessage(getString(R.string.msg_loading))
            .build()

        viewModel.completeSignupData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert.hide()
                    if (dataHandler.data?.success == true) {
                        ModelPreferencesManager.put(dataHandler.data?.data?.user, getString(R.string.pref_logged_in_user))
                        navigateToHomeScreen(activity)
                    } else {
                        displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                    }
                }
                is DataHandler.ERROR -> {
                    progressAlert.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                }
                is DataHandler.LOADING -> {
                    progressAlert.show()
                }
            }
        }

        binding.txtSignupName.text = HtmlCompat.fromHtml(
            getString(R.string.enter_full_name),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtSignupMobileNumber.text = HtmlCompat.fromHtml(
            getString(R.string.enter_mobile_number),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtSignupDOB.text = HtmlCompat.fromHtml(
            getString(R.string.enter_date_of_birth),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtSignupGender.text = HtmlCompat.fromHtml(
            getString(R.string.enter_gender),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.txtSignupCountryOfResidence.text = HtmlCompat.fromHtml(
            getString(R.string.enter_country_of_residence),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.editTextRegFormDOB.setOnClickListener {
            showDatePicker()
        }

        binding.editTextRegFormCountry.setOnClickListener {
            countriesBS.apply {
                setContentView(countriesListItemBinding.root)
                show()
            }
        }

        GlobalScope.launch(Dispatchers.Main) {   //Your Main UI Thread
            val countries: List<String>
            withContext(Dispatchers.IO) {
                countries = binding.editTextRegFormMobileNumber.currentCountries.map { it.name }
            }

            initCountryList(countries)
        }

        binding.completeRegCtaTnc.setOnClickListener {
            Navigation.findNavController(binding.completeRegCtaTnc)
                .navigate(R.id.action_navigation_complete_navigation_to_navigation_signup_tnc)
        }

        binding.btnCompleteRegNext.setOnClickListener {
            if (isValidForm())
                viewModel.completeSignup(
                    getAccessToken(),
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

        binding.editTextRegFormMobileNumber.setDefaultCountry("In")

        return root
    }

    private fun getFullName(): String {
        return binding.editTextRegFormFullName.editableText.toString()
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

    private fun getDOB(): String {
        return binding.editTextRegFormDOB.editableText.toString()
    }

    private fun getGender(): String {
        return binding.spinnerRegFormGender.selectedItem.toString()
    }

    private fun getCountryOfResidence(): String {
        //return binding.spinnerRegFormCountry.selectedItem.toString()
        return binding.editTextRegFormCountry.editableText.toString()
    }

    private fun getAccessToken(): String? {
        val loggedInUserToken = ModelPreferencesManager.get<Token>(getString(R.string.pref_logged_in_user_token))
        return "Bearer " +loggedInUserToken?.accessToken
    }

    private fun isTnCChecked(): Boolean{
        return binding.checkBoxRegTnc.isChecked
    }

    private fun isValidForm(): Boolean {
        return !(getFullName().isEmpty() ||
                getMobile().isEmpty() ||
                getCountryOfResidence().isEmpty() ||
                !isTnCChecked())
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

    /**
     * Bottom sheet layout of country list
     */
    private val countriesListItemBinding: FragmentListBinding by lazy {
        FragmentListBinding.inflate(LayoutInflater.from(context), binding.completeRegFragment, false)
    }

    /**
     * Bottom sheet dialog the wraps the countries + search of country by name
     */
    private val countriesBS: BottomSheetDialog by lazy {
        BottomSheetDialog(requireContext()).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = true
            behavior.isFitToContents = false

        }
    }

    /**
     * Init country list
     */
    private fun initCountryList(countries: List<String>) {

        val countriesAdapter = MyFolioListAdapter { selectedCountry ->
            setCountryData(selectedCountry as String)
            countriesBS.dismiss()
        }
        if (!countries.isNullOrEmpty()) {
            val index =  countries.indexOfFirst {
                it.startsWith("India")
            }
            setCountryData(countries[index])
            countriesAdapter.replaceData(countries as ArrayList<Any>?)
        }
        countriesListItemBinding.apply {
            rvList.setDivider(R.drawable.recycler_view_divider)
            rvList.adapter = countriesAdapter
            searchView.setQuery("", false)
        }

        // Search query
        countriesListItemBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {

                    countriesAdapter.replaceData(countries?.filter {
                        it?.lowercase(Locale.ENGLISH)!!.contains(
                            query.lowercase(
                                Locale.ENGLISH
                            )
                        )
                    } as ArrayList<Any>?)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    countriesAdapter.replaceData(countries?.filter {
                        it?.lowercase(Locale.ENGLISH)!!.contains(
                            newText.lowercase(
                                Locale.ENGLISH
                            )
                        )
                    } as ArrayList<Any>?)
                }
                return true
            }
        })
    }

    private fun setCountryData(strCountry: String) {
        binding.editTextRegFormCountry.setText(strCountry)
    }
}