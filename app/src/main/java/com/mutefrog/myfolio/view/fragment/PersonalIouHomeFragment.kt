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
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentPersonalIouHomeBinding
import com.mutefrog.myfolio.model.IOU
import com.mutefrog.myfolio.model.User
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.BankAccountsAdapter
import com.mutefrog.myfolio.view.adapter.PIOUListItemMenuClickListener
import com.mutefrog.myfolio.view.adapter.PersonalIouBorrowedAdapter
import com.mutefrog.myfolio.view.adapter.PersonalIouLendAdapter
import com.mutefrog.myfolio.viewmodel.PersonalIouHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalIouHomeFragment : Fragment(), PIOUListItemMenuClickListener {

    private var _binding: FragmentPersonalIouHomeBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: PersonalIouHomeViewModel by viewModels()

    private var currentTab: Int? = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalIouHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.personal_ious))
        hideMainActivityBottomBar(activity)

        progressAlert = getProgressDialog(requireActivity())

        with(binding) {
            tabLayoutPersonalIou.addTab(
                tabLayoutPersonalIou.newTab().setText(
                    getString(
                        R.string.lend
                    )
                )
            );
            tabLayoutPersonalIou.addTab(
                tabLayoutPersonalIou.newTab().setText(
                    getString(
                        R.string.borrowed
                    )
                )
            );

            tabLayoutPersonalIou.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text.toString() == getString(
                            R.string.lend
                        )
                    ) {
                        currentTab = 1
                        viewModel.getPious(getAccessToken(requireActivity()), "1")
                    } else {
                        currentTab = 2
                        viewModel.getPious(getAccessToken(requireActivity()), "2")
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }

        binding.recyclerViewPersonalIousList.layoutManager = LinearLayoutManager(activity)

        viewModel.getPious(getAccessToken(requireActivity()), "1")

        viewModel.listData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var list: ArrayList<IOU> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    if (currentTab == 1) {
                        val adapter = PersonalIouLendAdapter(list)
                        binding.recyclerViewPersonalIousList.adapter = adapter
                        adapter.setItemMenuClickListener(this)
                    } else {
                        val adapter = PersonalIouBorrowedAdapter(list)
                        binding.recyclerViewPersonalIousList.adapter = adapter
                        adapter.setItemMenuClickListener(this)
                    }
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
                        viewModel.getPious(
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
            getString(R.string.add)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {

            val bundle = Bundle()
            if (currentTab == 1)
                bundle.putString(
                    "selectedType",
                    "Lent"
                )
            else
                bundle.putString(
                    "selectedType",
                    "Borrowed"
                )
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_personal_iou_home_to_navigation_add_personal_iou, bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayoutPersonalIou.getTabAt(currentTab!!.toInt()-1)?.select()
    }

    override fun onEditClick(iou: IOU) {
        val bundle = Bundle()
        bundle.putString(
            "dataJson",
            Gson().toJson(iou).toString()
        )

        if (currentTab == 1)
            bundle.putString(
                "selectedType",
                "Lent"
            )
        else
            bundle.putString(
                "selectedType",
                "Borrowed"
            )

        Navigation.findNavController(binding.root)
            .navigate(
                R.id.action_navigation_personal_iou_home_to_navigation_add_personal_iou,
                bundle
            )
    }

    override fun onDeleteClick(iou: IOU) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_custom_dialog)
        val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
        body.text = getString(R.string.delete_confirmation)
        val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
        val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
        yesBtn.setOnClickListener {
            viewModel.deletePiou(getAccessToken(requireActivity()), iou.id.toString())
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}