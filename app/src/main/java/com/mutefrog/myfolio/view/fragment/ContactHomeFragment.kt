package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentContactsHomeBinding
import com.mutefrog.myfolio.databinding.FragmentInsuranceHomeBinding
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Contact
import com.mutefrog.myfolio.model.Insurance
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.ContactsAdapter
import com.mutefrog.myfolio.view.adapter.InsuranceAdapter
import com.mutefrog.myfolio.viewmodel.CategoryViewModel
import com.mutefrog.myfolio.viewmodel.ContactHomeViewModel
import com.mutefrog.myfolio.viewmodel.InsuranceHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactHomeFragment : Fragment(), ContactsAdapter.ListItemMenuClickListener {

    private var _binding: FragmentContactsHomeBinding? = null

    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: ContactHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)

        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        binding.recyclerViewContactsList.layoutManager = LinearLayoutManager(activity)

        viewModel.getContacts(
            getAccessToken(requireActivity())
        )

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            getString(R.string.contact)
        )
        hideMainActivityBottomBar(activity)

        viewModel.listData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var list: ArrayList<Contact> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = ContactsAdapter(list)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewContactsList.adapter = adapter
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

        viewModel.deleteData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    if (dataHandler.data?.success == true) {
                        viewModel.getContacts(
                            getAccessToken(requireActivity())
                        )
                    } else {
                        displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
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
            "Add " + getString(R.string.contact)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            val bundle = Bundle()
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_contact_home_to_navigation_add_contact, bundle)
        }
    }

    override fun onEditClick(item: Contact) {
        val bundle = Bundle()
        bundle.putString(
            "dataJson",
            Gson().toJson(item).toString()
        )
        Navigation.findNavController(binding.root)
            .navigate(
                R.id.action_navigation_contact_home_to_navigation_add_contact,
                bundle
            )
    }

    override fun onDeleteClick(item: Contact) {
        viewModel.deleteContact(getAccessToken(requireActivity()), item.id.toString())
    }
}