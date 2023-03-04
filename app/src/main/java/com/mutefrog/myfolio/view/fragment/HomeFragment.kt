package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentHomeBinding
import com.mutefrog.myfolio.databinding.ViewAddFirstNomineeBinding
import com.mutefrog.myfolio.model.DidYouKnow
import com.mutefrog.myfolio.model.HomeCategoryModel
import com.mutefrog.myfolio.model.User
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.DidYouKnowAdapter
import com.mutefrog.myfolio.view.adapter.HomeCategoryGridAdapter
import com.mutefrog.myfolio.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment(), DidYouKnowAdapter.ListItemMenuClickListener {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    val viewModel: HomeViewModel by viewModels()

    private var progressAlert: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressAlert = getProgressDialog(requireActivity())

        val loggedInUser =
            ModelPreferencesManager.get<User>(getString(R.string.pref_logged_in_user))
        binding.txtHomeTitleUsername.text = loggedInUser?.name

        binding.txtHomeTitleMyfolio.text = HtmlCompat.fromHtml(
            getString(R.string.txt_myfolio_logo),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        viewModel.getSubscriptionInfo(getAccessToken(requireActivity()))
        viewModel.subscriptionInfoData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()

                    if (dataHandler.data?.data?.nomineeCount == 0) {
                        binding.llAddNominee.visibility = View.VISIBLE
                    } else {
                        binding.llAddNominee.visibility = View.GONE
                    }

//                    val localDate = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                    } else {
//                        val subscriptionEndDate = LocalDate.parse(dataHandler.data?.data?.subscriptionInfo?.subscriptionEndDate)
//                        val today =
//
//                        val diff: Long = subscriptionEndDate.getTime() - Date()
//                        val seconds = diff / 1000
//                        val minutes = seconds / 60
//                        val hours = minutes / 60
//                        val days = hours / 24
//                    }

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

        binding.recyclerviewDidYouKnow.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        viewModel.getDidYouKnow(getAccessToken(requireActivity()))
        viewModel.didYouKnowData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()

                    var list: ArrayList<DidYouKnow> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = DidYouKnowAdapter(context, list)
                    adapter.setItemMenuClickListener(this)
                    binding.recyclerviewDidYouKnow.adapter = adapter
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

        binding.btnHomeUpgradeSubscription.setOnClickListener {
            Navigation.findNavController(binding.btnHomeUpgradeSubscription)
                .navigate(R.id.action_navigation_home_to_navigation_subscription)
        }

        val adapter = HomeCategoryGridAdapter(requireActivity(), getHomeCategoriesList())
        binding.gridViewHomeCategories.adapter = adapter

        binding.gridViewHomeCategories.setOnItemClickListener { adapterView, view, position, l ->
            var selectedHomeCategoryObj = getHomeCategoriesList()[position]
            if (selectedHomeCategoryObj.getCategoryName() == "Bank Accounts") {
                Navigation.findNavController(binding.gridViewHomeCategories)
                    .navigate(R.id.action_navigation_home_to_navigation_bank_account_home)
            } else if (selectedHomeCategoryObj.getCategoryName() == "Personal \nIOUs") {
                Navigation.findNavController(binding.gridViewHomeCategories)
                    .navigate(R.id.action_navigation_home_to_navigation_personal_iou_home)
            } else if (selectedHomeCategoryObj.getCategoryName() == "Cards") {
                Navigation.findNavController(binding.gridViewHomeCategories)
                    .navigate(R.id.action_navigation_home_to_navigation_card_home)
            } else if (selectedHomeCategoryObj.getCategoryName() == "Contacts") {
                Navigation.findNavController(binding.gridViewHomeCategories)
                    .navigate(R.id.action_navigation_home_to_navigation_contact_home)
            } else {
                val bundle = Bundle()
                bundle.putString(
                    "categoryName",
                    selectedHomeCategoryObj.getCategoryName()
                )
                bundle.putString(
                    "categoryNameId",
                    selectedHomeCategoryObj.getCategoryNameId()
                )
                Navigation.findNavController(binding.gridViewHomeCategories)
                    .navigate(R.id.action_navigation_home_to_navigation_category_screen, bundle)
            }
        }

        binding.root.findViewById<AppCompatButton>(R.id.btn_home_add_nominee).setOnClickListener {
            Navigation.findNavController(binding.btnHomeUpgradeSubscription)
                .navigate(R.id.action_navigation_home_to_navigation_add_nominee)
        }

        return root
    }


    override fun onResume() {
        super.onResume()
        hideMainActivityActionBar(activity)
        showMainActivityBottomBar(activity)
    }

    private fun getHomeCategoriesList(): ArrayList<HomeCategoryModel> {

        val categoriesArrayList = ArrayList<HomeCategoryModel>()

        categoriesArrayList.add(
            HomeCategoryModel(
                "Bank Accounts",
                R.drawable.img_home_bank_accounts,
                "bankaccounts"
            )
        )
        categoriesArrayList.add(
            HomeCategoryModel(
                "Loans",
                R.drawable.img_home_loans,
                "loan"
            )
        )

        categoriesArrayList.add(
            HomeCategoryModel(
                "Financial \nInvestments",
                R.drawable.img_home_financial_investments,
                "finance"
            )
        )
        categoriesArrayList.add(
            HomeCategoryModel(
                "Physical \nAssets",
                R.drawable.img_home_physical_assets,
                "asset"
            )
        )
        categoriesArrayList.add(
            HomeCategoryModel(
                "Insurance",
                R.drawable.img_home_insurance,
                "insurance"
            )
        )

        categoriesArrayList.add(
            HomeCategoryModel(
                "Personal \nIOUs",
                R.drawable.img_home_personal_ious,
                "ious"
            )
        )
        categoriesArrayList.add(
            HomeCategoryModel(
                "Contacts",
                R.drawable.img_home_contacts,
                "contact"
            )
        )

        categoriesArrayList.add(
            HomeCategoryModel(
                "Document \nRepository",
                R.drawable.img_home_document_repository,
                "document"
            )
        )
        categoriesArrayList.add(
            HomeCategoryModel(
                "Cards",
                R.drawable.img_home_cards,
                "cards"
            )
        )

        return categoriesArrayList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: DidYouKnow) {
        hideMainActivityBottomBar(requireActivity())

        if (item.type == "Video") {
            val bundle = Bundle()
            bundle.putString(
                "videoUrl",
                item.url
            )
            Navigation.findNavController(binding.gridViewHomeCategories)
                .navigate(R.id.action_navigation_home_to_navigation_video_player, bundle)
        }

        if (item.type == "Url") {
            val bundle = Bundle()
            bundle.putString(
                "url",
                item.url
            )
            Navigation.findNavController(binding.gridViewHomeCategories)
                .navigate(R.id.action_navigation_home_to_navigation_inapp_browser, bundle)
        }
    }

}