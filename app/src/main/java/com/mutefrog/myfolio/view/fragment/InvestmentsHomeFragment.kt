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
import com.mutefrog.myfolio.databinding.FragmentAssetHomeBinding
import com.mutefrog.myfolio.databinding.FragmentInvestmentHomeBinding
import com.mutefrog.myfolio.model.Asset
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.FinInvestment
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.AssetsAdapter
import com.mutefrog.myfolio.view.adapter.InvestmentsAdapter
import com.mutefrog.myfolio.viewmodel.AssetHomeViewModel
import com.mutefrog.myfolio.viewmodel.InvestmentHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvestmentsHomeFragment : Fragment(), InvestmentsAdapter.ListItemMenuClickListener {

    private var _binding: FragmentInvestmentHomeBinding? = null

    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: InvestmentHomeViewModel by viewModels()

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvestmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)

        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        binding.recyclerViewInvestmentList.layoutManager = LinearLayoutManager(activity)

        if (arguments != null) {
            var categoryJson =
                AddAssetFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
            viewModel.getFinInvestments(
                getAccessToken(requireActivity()),
                selectedCategoryItem?.id.toString()
            )
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            selectedCategoryItem?.financeType.toString()
        )
        hideMainActivityBottomBar(activity)

        viewModel.listData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var list: ArrayList<FinInvestment> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = InvestmentsAdapter(list)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewInvestmentList.adapter = adapter
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
                        viewModel.getFinInvestments(
                            getAccessToken(requireActivity()),
                            selectedCategoryItem?.id.toString()
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
            "Add " + selectedCategoryItem?.financeType.toString()
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                "selectedCategoryJson",
                Gson().toJson(selectedCategoryItem).toString()
            )
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_investment_home_to_navigation_add_investment, bundle)
        }
    }

    override fun onEditClick(item: FinInvestment) {
        val bundle = Bundle()
        bundle.putString(
            "dataJson",
            Gson().toJson(item).toString()
        )
        bundle.putString(
            "selectedCategoryJson",
            Gson().toJson(selectedCategoryItem).toString()
        )
        Navigation.findNavController(binding.root)
            .navigate(
                R.id.action_navigation_investment_home_to_navigation_add_investment,
                bundle
            )
    }

    override fun onDeleteClick(item: FinInvestment) {
        viewModel.deleteFinInvestments(getAccessToken(requireActivity()), item.id.toString())
    }
}