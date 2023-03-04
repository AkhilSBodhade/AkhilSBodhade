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
import com.mutefrog.myfolio.databinding.FragmentCardHomeBinding
import com.mutefrog.myfolio.model.Card
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.CardsAdapter
import com.mutefrog.myfolio.viewmodel.CardHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardHomeFragment : Fragment(), CardsAdapter.CardItemMenuClickListener {

    private var _binding: FragmentCardHomeBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: CardHomeViewModel by viewModels()

    private var currentTab: Int? = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        manageMainActivityActionBar(activity, View.VISIBLE, getString(R.string.card))
        hideMainActivityBottomBar(activity)

        progressAlert = getProgressDialog(requireActivity())

        with(binding) {
            tabLayoutCards.addTab(
                tabLayoutCards.newTab().setText(
                    getString(
                        R.string.debit
                    )
                )
            );
            tabLayoutCards.addTab(
                tabLayoutCards.newTab().setText(
                    getString(
                        R.string.credit
                    )
                )
            );

            tabLayoutCards.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text.toString() == "Debit") {
                        currentTab = 1
                        viewModel.getCards(getAccessToken(requireActivity()), "1")
                    } else {
                        currentTab = 2
                        viewModel.getCards(getAccessToken(requireActivity()), "2")
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }

        binding.recyclerViewCardsList.layoutManager = LinearLayoutManager(activity)

        viewModel.getCards(getAccessToken(requireActivity()), "1")

        viewModel.cardsData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var dataList: ArrayList<Card> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = CardsAdapter(dataList)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerViewCardsList.adapter = adapter
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

        viewModel.cardsDeleteData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    if (dataHandler.data?.success == true) {
                        viewModel.getCards(
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
            getString(R.string.add_card)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {

            val bundle = Bundle()
            if (currentTab == 1)
                bundle.putString(
                    "selectedType",
                    "Debit"
                )
            else
                bundle.putString(
                    "selectedType",
                    "Credit"
                )

            Navigation.findNavController(root)
                .navigate(R.id.action_navigation_card_home_to_navigation_add_card, bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tabLayoutCards.getTabAt(currentTab!!.toInt()-1)?.select()
    }

    override fun onEditClick(card: Card) {
        val bundle = Bundle()
        bundle.putString(
            "cardDataJson",
            Gson().toJson(card).toString()
        )
        if (currentTab == 1)
            bundle.putString(
                "selectedType",
                "Debit"
            )
        else
            bundle.putString(
                "selectedType",
                "Credit"
            )
        Navigation.findNavController(binding.root)
            .navigate(
                R.id.action_navigation_card_home_to_navigation_add_card,
                bundle
            )
    }

    override fun onDeleteClick(card: Card) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.view_custom_dialog)
        val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
        body.text = getString(R.string.delete_confirmation)
        val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
        val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
        yesBtn.setOnClickListener {
            viewModel.deleteCard(getAccessToken(requireActivity()), card.id.toString())
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

}