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
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentNomineeHomeBinding
import com.mutefrog.myfolio.model.Nominators
import com.mutefrog.myfolio.model.Nominees
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.NomineeAdapter
import com.mutefrog.myfolio.viewmodel.NomineeHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NomineeHomeFragment : Fragment(), NomineeAdapter.NomineeItemMenuClickListener {

    private var _binding: FragmentNomineeHomeBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: NomineeHomeViewModel by viewModels()

    private var currentTab: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNomineeHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            getString(R.string.nominee_nominator_details)
        )
        hideMainActivityBottomBar(activity)

        progressAlert = getProgressDialog(requireActivity())

        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).text =
            getString(R.string.add_nominee)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_nominee_to_navigation_add_nominee)
        }

        with(binding) {
            tabLayoutNominee.addTab(
                tabLayoutNominee.newTab().setText(
                    getString(
                        R.string.nominee
                    )
                )
            );
            tabLayoutNominee.addTab(
                tabLayoutNominee.newTab().setText(
                    getString(
                        R.string.nominators
                    )
                )
            );


            tabLayoutNominee.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text.toString() == getString(
                            R.string.nominee
                        )
                    ) {
                        currentTab = 1
                        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).visibility =
                            View.VISIBLE
                    } else {
                        currentTab = 2
                        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).visibility =
                            View.GONE
                    }
                    viewModel.getNomineeNominators(getAccessToken(requireActivity()))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }

        currentTab = 1
        binding.recyclerViewNomineeList.layoutManager = LinearLayoutManager(activity)
        viewModel.getNomineeNominators(getAccessToken(requireActivity()))

        viewModel.listData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var nomineesList: ArrayList<Nominees> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.nominees!!
                    } else {
                        arrayListOf()
                    }

                    var nominatorsList: ArrayList<Nominators> =
                        if (dataHandler.data?.success == true) {
                            dataHandler.data.data?.nominators!!
                        } else {
                            arrayListOf()
                        }

                    val adapter = NomineeAdapter(nomineesList, nominatorsList, currentTab)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewNomineeList.adapter = adapter
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
                        viewModel.getNomineeNominators(
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
    }

    override fun onEditClick(item: Nominees, nominator: Nominators) {
        if (currentTab == 1) {
            val bundle = Bundle()
            bundle.putString(
                "nomineeDataJson",
                Gson().toJson(item).toString()
            )
            Navigation.findNavController(binding.root)
                .navigate(
                    R.id.action_navigation_nominee_to_navigation_add_nominee,
                    bundle
                )
        }
    }

    override fun onDeleteClick(item: Nominees, nominator: Nominators) {
        if (currentTab == 1) {
            viewModel.deleteNominee(getAccessToken(requireActivity()), item.id.toString())
        }
    }

    override fun onReqInfoClick(item: Nominees, nominator: Nominators) {
    }

}