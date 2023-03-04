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
import com.mutefrog.myfolio.databinding.FragmentInsuranceHomeBinding
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Insurance
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.InsuranceAdapter
import com.mutefrog.myfolio.viewmodel.InsuranceHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsuranceHomeFragment : Fragment(), InsuranceAdapter.ListItemMenuClickListener {

    private var _binding: FragmentInsuranceHomeBinding? = null

    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: InsuranceHomeViewModel by viewModels()

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsuranceHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)

        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        binding.recyclerViewInsuranceList.layoutManager = LinearLayoutManager(activity)

        if (arguments != null) {
            var categoryJson =
                AddAssetFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
            viewModel.getInsurance(
                getAccessToken(requireActivity()),
                selectedCategoryItem?.id.toString()
            )
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            selectedCategoryItem?.insuranceType.toString()
        )
        hideMainActivityBottomBar(activity)

        viewModel.assetsData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var list: ArrayList<Insurance> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = InsuranceAdapter(list)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewInsuranceList.adapter = adapter
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

        viewModel.assetsDeleteData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    if (dataHandler.data?.success == true) {
                        viewModel.getInsurance(
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
            "Add " + selectedCategoryItem?.insuranceType.toString()
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                "selectedCategoryJson",
                Gson().toJson(selectedCategoryItem).toString()
            )
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_insurance_home_to_navigation_add_insurance, bundle)
        }
    }

    override fun onEditClick(item: Insurance) {
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
                R.id.action_navigation_insurance_home_to_navigation_add_insurance,
                bundle
            )
    }

    override fun onDeleteClick(item: Insurance) {
        viewModel.deleteAsset(getAccessToken(requireActivity()), item.id.toString())
    }
}