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
import com.mutefrog.myfolio.databinding.FragmentDocumentHomeBinding
import com.mutefrog.myfolio.databinding.FragmentInsuranceHomeBinding
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.Document
import com.mutefrog.myfolio.model.Insurance
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.DocumentsAdapter
import com.mutefrog.myfolio.view.adapter.InsuranceAdapter
import com.mutefrog.myfolio.viewmodel.DocumentHomeViewModel
import com.mutefrog.myfolio.viewmodel.InsuranceHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentHomeFragment : Fragment(), DocumentsAdapter.ListItemMenuClickListener {

    private var _binding: FragmentDocumentHomeBinding? = null

    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: DocumentHomeViewModel by viewModels()

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDocumentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)

        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        binding.recyclerViewDocumentsList.layoutManager = LinearLayoutManager(activity)

        if (arguments != null) {
            var categoryJson =
                AddDocumentFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
            viewModel.getDocuments(
                getAccessToken(requireActivity()),
                selectedCategoryItem?.id.toString()
            )
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            selectedCategoryItem?.documentType.toString()
        )
        hideMainActivityBottomBar(activity)

        viewModel.listData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var list: ArrayList<Document> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = DocumentsAdapter(list)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewDocumentsList.adapter = adapter
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
                        viewModel.getDocuments(
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
            "Add " + selectedCategoryItem?.documentType.toString()

        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            val bundle = Bundle()
            bundle.putString(
                "selectedCategoryJson",
                Gson().toJson(selectedCategoryItem).toString()
            )
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_document_home_to_navigation_add_document, bundle)
        }
    }

    override fun onEditClick(item: Document) {
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
                R.id.action_navigation_document_home_to_navigation_add_document,
                bundle
            )
    }

    override fun onDeleteClick(item: Document) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_custom_dialog)
        val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
        body.text = getString(R.string.delete_confirmation)
        val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
        val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
        yesBtn.setOnClickListener {
            viewModel.deleteDocument(getAccessToken(requireActivity()), item.id.toString())
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}