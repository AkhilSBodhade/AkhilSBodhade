package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentBankAccountHomeBinding
import com.mutefrog.myfolio.model.BankAccount
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.BankAccountsAdapter
import com.mutefrog.myfolio.viewmodel.BankAccountHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankAccountHomeFragment : Fragment(), BankAccountsAdapter.BankItemMenuClickListener {

    private var _binding: FragmentBankAccountHomeBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: BankAccountHomeViewModel by viewModels()

    private var currentTab: Int? = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankAccountHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.bank_account))
        hideMainActivityBottomBar(activity)

        progressAlert = getProgressDialog(requireActivity())

        with(binding) {
            tabLayoutBankAccount.addTab(
                tabLayoutBankAccount.newTab().setText(
                    getString(
                        R.string.saving
                    )
                )
            );
            tabLayoutBankAccount.addTab(
                tabLayoutBankAccount.newTab().setText(
                    getString(
                        R.string.current
                    )
                )
            );

            tabLayoutBankAccount.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text.toString() == "Saving") {
                        currentTab = 1
                        viewModel.getBankAccounts(getAccessToken(requireActivity()), "1")
                    } else {
                        currentTab = 2
                        viewModel.getBankAccounts(getAccessToken(requireActivity()), "2")
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }

        binding.recyclerViewBankAccountsList.layoutManager = LinearLayoutManager(activity)

        viewModel.getBankAccounts(getAccessToken(requireActivity()), "1")

        viewModel.bankAccountsData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var bankAccountsList: ArrayList<BankAccount> =
                        if (dataHandler.data?.success == true) {
                            dataHandler.data.data?.list!!
                        } else {
                            arrayListOf()
                        }
                    val adapter = BankAccountsAdapter(bankAccountsList)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewBankAccountsList.adapter = adapter
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

        viewModel.bankAccountsDeleteData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    if (dataHandler.data?.success == true) {
                        viewModel.getBankAccounts(
                            getAccessToken(requireActivity()),
                            currentTab?.toString()
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
            getString(R.string.add_bank_account)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {

            val bundle = Bundle()
            if (currentTab == 1)
                bundle.putString(
                    "selectedType",
                    "Saving"
                )
            else
                bundle.putString(
                    "selectedType",
                    "Current"
                )
            Navigation.findNavController(root)
                .navigate(
                    R.id.action_navigation_bank_account_home_to_navigation_add_bank_account,
                    bundle
                )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayoutBankAccount.getTabAt(currentTab!!.toInt()-1)?.select()
    }

    override fun onEditClick(bankAccount: BankAccount) {
        val bundle = Bundle()
        bundle.putString(
            "bankAccountJson",
            Gson().toJson(bankAccount).toString()
        )
        if (currentTab == 1)
            bundle.putString(
                "selectedType",
                "Saving"
            )
        else
            bundle.putString(
                "selectedType",
                "Current"
            )
        Navigation.findNavController(binding.root)
            .navigate(
                R.id.action_navigation_bank_account_home_to_navigation_add_bank_account,
                bundle
            )
    }

    override fun onDeleteClick(bankAccount: BankAccount) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_custom_dialog)
        val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
        body.text = getString(R.string.delete_confirmation)
        val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
        val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
        yesBtn.setOnClickListener {
            viewModel.deleteBankAccount(getAccessToken(requireActivity()), bankAccount.id.toString())
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

}