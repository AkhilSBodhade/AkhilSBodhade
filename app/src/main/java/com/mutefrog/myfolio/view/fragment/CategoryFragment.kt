package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentCategoryBinding
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.CategoryGridAdapter
import com.mutefrog.myfolio.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null

    private val binding get() = _binding!!

    val viewModel: CategoryViewModel by viewModels()

    private var progressAlert: AlertDialog? = null

    private var categoryName = ""

    private var categoryNameId = ""

    private var adapter: CategoryGridAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            categoryName = CategoryFragmentArgs.fromBundle(requireArguments()).categoryName
            categoryNameId = CategoryFragmentArgs.fromBundle(requireArguments()).categoryNameId
            viewModel.getCategories(categoryNameId, getAccessToken(requireActivity()))
        }

        binding.txtCategoryTitle.text = "$categoryName\nCategory"

        binding.imgCategoryClose.setOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.getCategoryData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    ModelPreferencesManager.put(
                        Gson().toJson(dataHandler.data?.data?.list),
                        getString(R.string.pref_category_list)
                    )
                    adapter =
                        CategoryGridAdapter(requireActivity(), dataHandler.data?.data?.list)
                    binding.gridCategory.adapter = adapter
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

        binding.gridCategory.setOnItemClickListener { adapterView, view, position, l ->
            if (categoryNameId == "loan") {

                val loanType = adapter?.getItem(position)?.id

                if (adapter?.getItem(position)?.docCount!! > 0) {
                    val bundle = Bundle()
                    bundle.putString(
                        "loanType",
                        loanType.toString()
                    )
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_loan_home,
                            bundle
                        )

                } else {
                    val bundle = Bundle()
                    bundle.putString(
                        "categoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_add_loan,
                            bundle
                        )
                }
            }

            if (categoryNameId == "asset") {

                val type = adapter?.getItem(position)?.id

                if (adapter?.getItem(position)?.docCount!! > 0) {
                    val bundle = Bundle()
                    bundle.putString(
                        "typeId",
                        type.toString()
                    )
                    bundle.putString(
                        "typeName",
                        adapter?.getItem(position)?.assetType
                    )
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_asset_home,
                            bundle
                        )
                } else {
                    val bundle = Bundle()
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_add_asset,
                            bundle
                        )
                }
            }

            if (categoryNameId == "insurance") {

                val type = adapter?.getItem(position)?.id

                if (adapter?.getItem(position)?.docCount!! > 0) {
                    val bundle = Bundle()
                    bundle.putString(
                        "typeId",
                        type.toString()
                    )
                    bundle.putString(
                        "typeName",
                        adapter?.getItem(position)?.insuranceType
                    )
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_insurance_home,
                            bundle
                        )
                } else {
                    val bundle = Bundle()
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_add_insurance,
                            bundle
                        )
                }
            }


            if (categoryNameId == "document") {

                val type = adapter?.getItem(position)?.id

                if (adapter?.getItem(position)?.docCount!! > 0) {
                    val bundle = Bundle()
                    bundle.putString(
                        "typeId",
                        type.toString()
                    )
                    bundle.putString(
                        "typeName",
                        adapter?.getItem(position)?.documentType
                    )
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_document_home,
                            bundle
                        )
                } else {
                    val bundle = Bundle()
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_add_document,
                            bundle
                        )
                }
            }

            if (categoryNameId == "finance") {

                val type = adapter?.getItem(position)?.id

                if (adapter?.getItem(position)?.docCount!! > 0) {
                    val bundle = Bundle()
                    bundle.putString(
                        "typeId",
                        type.toString()
                    )
                    bundle.putString(
                        "typeName",
                        adapter?.getItem(position)?.insuranceType
                    )
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_investment_home,
                            bundle
                        )
                } else {
                    val bundle = Bundle()
                    bundle.putString(
                        "selectedCategoryJson",
                        Gson().toJson(adapter?.getItem(position)).toString()
                    )
                    Navigation.findNavController(binding.root)
                        .navigate(
                            R.id.action_navigation_category_screen_to_navigation_add_investment,
                            bundle
                        )
                }
            }

        }

        return root
    }

    override fun onResume() {
        super.onResume()
        hideMainActivityActionBar(activity)
        hideMainActivityBottomBar(activity)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}