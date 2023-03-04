package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentNotificationsBinding
import com.mutefrog.myfolio.model.Notification
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.view.adapter.NotificationsAdapter
import com.mutefrog.myfolio.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)

        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        binding.recyclerViewNotificationsList.layoutManager = LinearLayoutManager(activity)

        viewModel.getNotifications(
            getAccessToken(requireActivity())
        )

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            getString(R.string.title_notifications)
        )
        hideMainActivityBottomBar(activity)

        viewModel.listData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    var list: ArrayList<Notification> = if (dataHandler.data?.success == true) {
                        dataHandler.data.data?.list!!
                    } else {
                        arrayListOf()
                    }
                    val adapter = NotificationsAdapter(list)
                    binding.recyclerViewNotificationsList.adapter = adapter
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
    }
}