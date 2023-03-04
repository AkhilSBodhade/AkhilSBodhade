package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
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
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentLoanHomeBinding
import com.mutefrog.myfolio.databinding.ViewAssetsListRowBinding
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Loan
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.AssetsAdapter
import com.mutefrog.myfolio.view.adapter.BankAccountsAdapter
import com.mutefrog.myfolio.view.adapter.LoansAdapter
import com.mutefrog.myfolio.viewmodel.LoanHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanHomeFragment : Fragment(), LoansAdapter.LoanItemMenuClickListener {

    private var _binding: FragmentLoanHomeBinding? = null

    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: LoanHomeViewModel by viewModels()

    private var loanType = ""

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoanHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        binding.recyclerViewLoansList.layoutManager = LinearLayoutManager(activity)

        if (arguments != null) {

            var categoryJson =
                LoanHomeFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)

            loanType = LoanHomeFragmentArgs.fromBundle(requireArguments()).loanType
            viewModel.getLoans(getAccessToken(requireActivity()), loanType)
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            selectedCategoryItem?.loanType.toString()
        )
        hideMainActivityBottomBar(activity)

        viewModel.loansData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var list: ArrayList<Loan> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = LoansAdapter(list)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewLoansList.adapter = adapter
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

        viewModel.loansDeleteData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    if (dataHandler.data?.success == true) {
                        viewModel.getLoans(getAccessToken(requireActivity()), loanType)
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
            getString(R.string.add_new_loan)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                "selectedCategoryJson",
                Gson().toJson(selectedCategoryItem).toString()
            )
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_loan_home_to_navigation_add_loan, bundle)
        }
    }

    override fun onEditClick(loan: Loan) {
        val bundle = Bundle()
        bundle.putString(
            "loanJson",
            Gson().toJson(loan).toString()
        )
        bundle.putString(
            "selectedCategoryJson",
            Gson().toJson(selectedCategoryItem).toString()
        )
        Navigation.findNavController(binding.root)
            .navigate(
                R.id.action_navigation_loan_home_to_navigation_add_loan,
                bundle
            )
    }

    override fun onDeleteClick(loan: Loan) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_custom_dialog)
        val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
        body.text = getString(R.string.delete_confirmation)
        val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
        val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
        yesBtn.setOnClickListener {
            viewModel.deleteLoan(getAccessToken(requireActivity()), loan.id.toString())
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}