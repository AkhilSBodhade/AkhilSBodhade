package com.mutefrog.myfolio.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentSubscriptionBinding
import com.mutefrog.myfolio.utils.Constants
import com.mutefrog.myfolio.utils.displaySnackbarAlert
import com.mutefrog.myfolio.utils.hideMainActivityActionBar
import com.mutefrog.myfolio.utils.hideMainActivityBottomBar
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class SubscriptionFragment : Fragment(), PaymentResultWithDataListener, ExternalWalletListener {

    private var _binding: FragmentSubscriptionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Checkout.preload(activity?.applicationContext)
        val co = Checkout()
        co.setKeyID(Constants.RAZORPAY_KEY_ID)
        try {
            var options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("currency", "INR")
            options.put("amount", "100")
            options.put("send_sms_hash", true);
            val prefill = JSONObject()
            prefill.put("email", "test@razorpay.com")
            prefill.put("contact", "9900990099")
            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            displaySnackbarAlert(
                requireActivity(),
                root,
                e.message
            )
            e.printStackTrace()
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

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        displaySnackbarAlert(
            requireActivity(),
            binding.root,
            "Payment Successful : Payment ID: $p0\nPayment Data: ${p1?.data}"
        )
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        displaySnackbarAlert(
            requireActivity(),
            binding.root,
            "Payment Failed : Payment Data: ${p2?.data}"
        )
        activity?.onBackPressed()
    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        displaySnackbarAlert(
            requireActivity(),
            binding.root,
            "External wallet was selected : Payment Data: ${p1?.data}"
        )
    }


}