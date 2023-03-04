package com.mutefrog.myfolio.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.databinding.FragmentAddAssetBinding
import com.mutefrog.myfolio.databinding.FragmentAddInvestmentBinding
import com.mutefrog.myfolio.model.Asset
import com.mutefrog.myfolio.model.Category
import com.mutefrog.myfolio.model.FinInvestment
import com.mutefrog.myfolio.utils.*
import com.mutefrog.myfolio.viewmodel.AddAssetViewModel
import com.mutefrog.myfolio.viewmodel.AddInvestmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddInvestmentFragment : Fragment() {

    private var _binding: FragmentAddInvestmentBinding? = null
    private val binding get() = _binding!!

    private var progressAlert: AlertDialog? = null

    val viewModel: AddInvestmentViewModel by viewModels()

    var selectedItem: FinInvestment? = null

    var selectedCategoryItem: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddInvestmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initScreen(root)
        return root
    }

    private fun initScreen(root: View) {

        progressAlert = getProgressDialog(requireActivity())

        if (arguments != null) {
            var dataJson =
                AddAssetFragmentArgs.fromBundle(requireArguments()).dataJson
            selectedItem = Gson().fromJson(dataJson, FinInvestment::class.java)

            var categoryJson =
                AddAssetFragmentArgs.fromBundle(requireArguments()).selectedCategoryJson
            selectedCategoryItem = Gson().fromJson(categoryJson, Category::class.java)
        }

        manageMainActivityActionBar(
            activity,
            View.VISIBLE,
            "Add " + selectedCategoryItem?.financeType.toString()
        )
        hideMainActivityBottomBar(activity)

        manageFormContentVisibility()

        setHTMLText(binding.txtAddInvestmentEquityAmtInvested, getString(R.string.add_investment_equity_amt_invested))
        setHTMLText(binding.txtAddInvestmentEquityAccHolderName, getString(R.string.add_investment_equity_acc_holder))
        setHTMLText(binding.txtAddInvestmentEquityName, getString(R.string.add_investment_equity_name))
        setHTMLText(binding.txtAddInvestmentEquityNoShares, getString(R.string.add_investment_equity_shares))
        setHTMLText(binding.txtAddInvestmentEquityCustomerId, getString(R.string.add_investment_equity_custid))
        setHTMLText(binding.txtAddInvestmentEquityBrokerName, getString(R.string.add_investment_equity_broker))

        setHTMLText(binding.txtAddInvestmentMfAccHolderName, getString(R.string.add_investment_mf_acc_holder))
        setHTMLText(binding.txtAddInvestmentMfAmtInvested, getString(R.string.add_investment_mf_amt_invested))
        setHTMLText(binding.txtAddInvestmentMfBrokerName, getString(R.string.add_investment_mf_broker))
        setHTMLText(binding.txtAddInvestmentMfFundName, getString(R.string.add_investment_mf_fund_name))
        setHTMLText(binding.txtAddInvestmentMfFundType, getString(R.string.add_investment_mf_fund_type))

        setHTMLText(binding.txtAddInvestmentSipName, getString(R.string.add_investment_sip_name))
        setHTMLText(binding.txtAddInvestmentSipAccHolderName, getString(R.string.add_investment_sip_acc_holder))
        setHTMLText(binding.txtAddInvestmentSipAmountInvested, getString(R.string.add_investment_sip_amt_invested))
        setHTMLText(binding.txtAddInvestmentSipStartDate, getString(R.string.add_investment_sip_start_date))
        setHTMLText(binding.txtAddInvestmentSipEndDate, getString(R.string.add_investment_sip_end_date))
        setHTMLText(binding.txtAddInvestmentSipBrokerName, getString(R.string.add_investment_sip_broker))

        setHTMLText(binding.txtAddInvestmentFdAmtDeposited, getString(R.string.add_investment_fd_deposit_amt))
        setHTMLText(binding.txtAddInvestmentFdAccHolderName, getString(R.string.add_investment_fd_acc_holder))
        setHTMLText(binding.txtAddInvestmentFdBankName, getString(R.string.add_investment_fd_bank))
        setHTMLText(binding.txtAddInvestmentFdNumber, getString(R.string.add_investment_fd_number))
        setHTMLText(binding.txtAddInvestmentFdBranchName, getString(R.string.add_investment_fd_branch))
        setHTMLText(binding.txtAddInvestmentFdMaturityDate, getString(R.string.add_investment_fd_maturity_date))
        setHTMLText(binding.txtAddInvestmentFdMaturityAmount, getString(R.string.add_investment_fd_maturity_amt))

        setHTMLText(binding.txtAddInvestmentCryptoAmtInvested, getString(R.string.add_investment_crypto_amt_invested))
        setHTMLText(binding.txtAddInvestmentCryptoAccHolderName, getString(R.string.add_investment_crypto_acc_holder))
        setHTMLText(binding.txtAddInvestmentCryptoBrokerName, getString(R.string.add_investment_crypto_broker))
        setHTMLText(binding.txtAddInvestmentCryptoName, getString(R.string.add_investment_crypto_name))
        setHTMLText(binding.txtAddInvestmentCryptoQty, getString(R.string.add_investment_crypto_qty))

        setHTMLText(binding.txtAddInvestmentNpsAmount, getString(R.string.add_investment_nps_amt))
        setHTMLText(binding.txtAddInvestmentNpsAccHolderName, getString(R.string.add_investment_nps_acc_holder))
        setHTMLText(binding.txtAddInvestmentNpsType, getString(R.string.add_investment_nps_type))
        setHTMLText(binding.txtAddInvestmentNpsPraNumber, getString(R.string.add_investment_nps_pra))

        setHTMLText(binding.txtAddInvestmentPpfAccHolderName, getString(R.string.add_investment_ppf_acc_holder))
        setHTMLText(binding.txtAddInvestmentPpfAccNumber, getString(R.string.add_investment_ppf_acc_number))
        setHTMLText(binding.txtAddInvestmentPpfMaturityAmount, getString(R.string.add_investment_ppf_maturity_amt))
        setHTMLText(binding.txtAddInvestmentPpfMaturityDate, getString(R.string.add_investment_ppf_maturity_date))

        setHTMLText(binding.txtAddInvestmentPfAmount, getString(R.string.add_investment_pf_amt))
        setHTMLText(binding.txtAddInvestmentPfUan, getString(R.string.add_investment_pf_uan))
        setHTMLText(binding.txtAddInvestmentPfAccHolderName, getString(R.string.add_investment_pf_acc_holder))
        setHTMLText(binding.txtAddInvestmentPfEmployer, getString(R.string.add_investment_pf_employer))

        setHTMLText(binding.txtAddInvestmentBondName, getString(R.string.add_investment_bond_name))
        setHTMLText(binding.txtAddInvestmentBondAccHolderName, getString(R.string.add_investment_bond_acc_holder))
        setHTMLText(binding.txtAddInvestmentBondNumber, getString(R.string.add_investment_bond_number))
        setHTMLText(binding.txtAddInvestmentBondPrincipalAmt, getString(R.string.add_investment_bond_p_amt))
        setHTMLText(binding.txtAddInvestmentBondMaturityAmt, getString(R.string.add_investment_bond_m_amt))

        setHTMLText(binding.txtAddInvestmentCertName, getString(R.string.add_investment_cert_name))
        setHTMLText(binding.txtAddInvestmentCertNumber, getString(R.string.add_investment_cert_number))
        setHTMLText(binding.txtAddInvestmentCertAccHolderName, getString(R.string.add_investment_cert_acc_holder))

        binding.imgShareNowInfo.setOnClickListener {
            showShareNowAlert(requireContext())
        }

        manageFormContentVisibility()

        if (selectedItem != null) {
            binding.editAddInvestmentEquityAmtInvested.setText(selectedItem?.financeData?.getAmountInvestedValue())
            binding.editAddInvestmentEquityAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentEquityName.setText(selectedItem?.financeData?.getEquityNameValue())
            binding.editAddInvestmentEquityNoShares.setText(selectedItem?.financeData?.getNoOfSharesValue())
            binding.editAddInvestmentEquityCustomerId.setText(selectedItem?.financeData?.getCustomerIdValue())
            binding.editAddInvestmentEquityBrokerName.setText(selectedItem?.financeData?.getBrokerNameValue())

            binding.editAddInvestmentSipName.setText(selectedItem?.financeData?.sipName)
            binding.editAddInvestmentSipAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentSipAmountInvested.setText(selectedItem?.financeData?.getAmountInvestedValue())
            binding.editAddInvestmentSipBrokerName.setText(selectedItem?.financeData?.getBrokerNameValue())
            binding.editAddInvestmentSipStartDate.setText(selectedItem?.financeData?.startDate)
            binding.editAddInvestmentSipEndDate.setText(selectedItem?.financeData?.endDate)

            binding.editAddInvestmentMfAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentMfAmtInvested.setText(selectedItem?.financeData?.getAmountInvestedValue())
            binding.editAddInvestmentMfBrokerName.setText(selectedItem?.financeData?.getBrokerNameValue())
            binding.editAddInvestmentMfFundName.setText(selectedItem?.financeData?.getFundNameValue())
            binding.editAddInvestmentMfFundType.setText(selectedItem?.financeData?.fundType)

            binding.editAddInvestmentFdAmtDeposited.setText(selectedItem?.financeData?.getAmountDepositedValue())
            binding.editAddInvestmentFdAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentFdBankName.setText(selectedItem?.financeData?.getBankNameValue())
            binding.editAddInvestmentFdNumber.setText(selectedItem?.financeData?.getFixedDepositNumberValue())
            binding.editAddInvestmentFdBranchName.setText(selectedItem?.financeData?.branchName)
            binding.editAddInvestmentFdMaturityDate.setText(selectedItem?.financeData?.maturityDate)

            binding.editAddInvestmentCryptoAmtInvested.setText(selectedItem?.financeData?.getAmountInvestedValue())
            binding.editAddInvestmentCryptoAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentCryptoBrokerName.setText(selectedItem?.financeData?.getBrokerNameValue())
            binding.editAddInvestmentCryptoName.setText(selectedItem?.financeData?.getCryptoNameValue())
            binding.editAddInvestmentCryptoQty.setText(selectedItem?.financeData?.getQuantityValue())

            binding.editAddInvestmentNpsAmount.setText(selectedItem?.financeData?.getNPSAmountValue())
            binding.editAddInvestmentNpsAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentNpsType.setText(selectedItem?.financeData?.getNPSTypeValue())
            binding.editAddInvestmentNpsPraNumber.setText(selectedItem?.financeData?.getPermanentRetirementAccNoValue())

            binding.editAddInvestmentPpfAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentPpfAccNumber.setText(selectedItem?.financeData?.getPPFAccountNumberValue())
            binding.editAddInvestmentPpfMaturityAmount.setText(selectedItem?.financeData?.getMaturityAmountValue())
            binding.editAddInvestmentPpfMaturityDate.setText(selectedItem?.financeData?.maturityDate)

            binding.editAddInvestmentPfAmount.setText(selectedItem?.financeData?.getPfAmountValue())
            binding.editAddInvestmentPfUan.setText(selectedItem?.financeData?.getUANValue())
            binding.editAddInvestmentPfAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentPfEmployer.setText(selectedItem?.financeData?.getEmployeeNumberValue())

            binding.editAddInvestmentBondName.setText(selectedItem?.financeData?.getBondNameValue())
            binding.editAddInvestmentBondAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())
            binding.editAddInvestmentBondNumber.setText(selectedItem?.financeData?.getBondNumberValue())
            binding.editAddInvestmentBondPrincipalAmt.setText(selectedItem?.financeData?.getPrincipalAmountValue())
            binding.editAddInvestmentBondMaturityAmt.setText(selectedItem?.financeData?.getMaturityAmountValue())

            binding.editAddInvestmentCertName.setText(selectedItem?.financeData?.getCertificateNameValue())
            binding.editAddInvestmentCertNumber.setText(selectedItem?.financeData?.getCertificateNumberValue())
            binding.editAddInvestmentCertAccHolderName.setText(selectedItem?.financeData?.getAccountHolderNameValue())

            binding.editAddInvestmentComment.setText(selectedItem?.financeData?.comment)

            setShareNowStatus(selectedItem!!.share)
        }

        binding.editAddInvestmentSipStartDate.setOnClickListener {
            setDate(requireActivity(), binding.editAddInvestmentSipStartDate)
        }

        binding.editAddInvestmentSipEndDate.setOnClickListener {
            setDate(requireActivity(), binding.editAddInvestmentSipEndDate)
        }

        viewModel.addData.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    progressAlert?.hide()
                    displaySnackbarAlert(requireActivity(), root, dataHandler.data?.message)
                    if (dataHandler.data?.success == true) {
                        activity?.onBackPressed()
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

        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).text = getString(R.string.save)
        root.findViewById<AppCompatButton>(R.id.btn_action_form_bottom).setOnClickListener {
            if (isValidForm()) {
                var id: Int? = null
                if (selectedItem != null)
                    id = selectedItem?.id

                viewModel.addUpdateInvestment(
                    id,
                    getAccessToken(requireActivity()),
                    getfinance_type_id(),
                    getacc_holder_name().encrypt(),
                    getbroker_name().encrypt(),
                    getequity_name().encrypt(),
                    getcustomer_id().encrypt(),
                    getno_of_shares().encrypt(),
                    getamount_invested().encrypt(),
                    getfund_type(),
                    getfund_name().encrypt(),
                    getsip_name(),
                    getstart_date(),
                    getend_date(),
                    getbank_name().encrypt(),
                    getbranch_name(),
                    getfixed_deposit_no().encrypt(),
                    getamount_deposit().encrypt(),
                    getmaturity_amount().encrypt(),
                    getmaturity_date(),
                    getrecurring_deposit_no().encrypt(),
                    getmontly_installment().encrypt(),
                    getcrypto_name().encrypt(),
                    getquantity().encrypt(),
                    getnps_type().encrypt(),
                    getretirement_account_no().encrypt(),
                    getnps_amount().encrypt(),
                    getppf_account_no().encrypt(),
                    getbond_name().encrypt(),
                    getbond_no().encrypt(),
                    getprinciple_amount().encrypt(),
                    getuan_number().encrypt(),
                    getpf_amount().encrypt(),
                    getemployee_number().encrypt(),
                    getcertificate_name().encrypt(),
                    getcertificate_no().encrypt(),
                    getinvestment_type().encrypt(),
                    getfolio_no().encrypt(),
                    getinvestment_amount().encrypt(),
                    getComment(),
                    getShareNowStatus()
                )
            } else
                displaySnackbarAlert(
                    requireActivity(),
                    root,
                    getString(R.string.msg_enter_valid_details)
                )
        }
    }

    private fun getinvestment_amount(): String {
        return ""
    }

    private fun getfolio_no(): String {
        return ""
    }

    private fun getinvestment_type(): String {
        return ""
    }

    private fun getcertificate_no(): String {
        return binding.editAddInvestmentCertNumber.editableText.toString()
    }

    private fun getcertificate_name(): String {
        return binding.editAddInvestmentCertName.editableText.toString()
    }

    private fun getemployee_number(): String {
        return binding.editAddInvestmentPfEmployer.editableText.toString()
    }

    private fun getpf_amount(): String {
        return binding.editAddInvestmentPfAmount.editableText.toString()
    }

    private fun getuan_number(): String {
        return binding.editAddInvestmentPfUan.editableText.toString()
    }

    private fun getprinciple_amount(): String {
        return binding.editAddInvestmentBondPrincipalAmt.editableText.toString()
    }

    private fun getbond_no(): String {
        return binding.editAddInvestmentBondNumber.editableText.toString()
    }

    private fun getbond_name(): String {
        return binding.editAddInvestmentBondName.editableText.toString()
    }

    private fun getppf_account_no(): String {
        return binding.editAddInvestmentPpfAccNumber.editableText.toString()
    }

    private fun getnps_amount(): String {
        return binding.editAddInvestmentNpsAmount.editableText.toString()
    }

    private fun getquantity(): String {
        return binding.editAddInvestmentCryptoQty.editableText.toString()
    }

    private fun getnps_type(): String {
        return binding.editAddInvestmentNpsType.editableText.toString()
    }

    private fun getretirement_account_no(): String {
        return binding.editAddInvestmentNpsPraNumber.editableText.toString()
    }

    private fun getcrypto_name(): String {
        return binding.editAddInvestmentCryptoName.editableText.toString()
    }

    private fun getmontly_installment(): String {
        return ""
    }

    private fun getmaturity_date(): String {
        return binding.editAddInvestmentFdMaturityDate.editableText.toString()
    }

    private fun getmaturity_amount(): String {
        return binding.editAddInvestmentFdMaturityAmount.editableText.toString()
    }

    private fun getamount_deposit(): String {
        return binding.editAddInvestmentFdAmtDeposited.editableText.toString()
    }

    private fun getrecurring_deposit_no(): String {
        return ""
    }

    private fun getbranch_name(): String {
        return binding.editAddInvestmentFdBranchName.editableText.toString()
    }

    private fun getfixed_deposit_no(): String {
        return binding.editAddInvestmentFdNumber.editableText.toString()
    }

    private fun getend_date(): String {
        return binding.editAddInvestmentSipEndDate.editableText.toString()
    }

    private fun getbank_name(): String {
        return binding.editAddInvestmentFdBankName.editableText.toString()
    }

    private fun getstart_date(): String {
        return binding.editAddInvestmentSipStartDate.editableText.toString()
    }

    private fun getsip_name(): String {
        return binding.editAddInvestmentSipName.editableText.toString()
    }

    private fun getfund_type(): String {
        return binding.editAddInvestmentMfFundType.editableText.toString()
    }

    private fun getamount_invested(): String {
        when (selectedCategoryItem?.id) {
            1 -> return binding.editAddInvestmentEquityAmtInvested.editableText.toString()
            2 -> return binding.editAddInvestmentMfAmtInvested.editableText.toString()
            3 -> return binding.editAddInvestmentSipAmountInvested.editableText.toString()
            6 -> return binding.editAddInvestmentCryptoAmtInvested.editableText.toString()
        }
        return binding.editAddInvestmentEquityBrokerName.editableText.toString()
    }

    private fun getno_of_shares(): String {
        return  binding.editAddInvestmentEquityNoShares.editableText.toString()
    }

    private fun getcustomer_id(): String {
        return  binding.editAddInvestmentEquityCustomerId.editableText.toString()
    }

    private fun getequity_name(): String {
        return  binding.editAddInvestmentEquityName.editableText.toString()
    }

    private fun getfund_name(): String {
        return  binding.editAddInvestmentMfFundName.editableText.toString()
    }

    private fun getbroker_name(): String {
        when (selectedCategoryItem?.id)
        {
            1 -> return  binding.editAddInvestmentEquityBrokerName.editableText.toString()

            2 -> return  binding.editAddInvestmentMfBrokerName.editableText.toString()

            3 -> return  binding.editAddInvestmentSipBrokerName.editableText.toString()

            6 -> return  binding.editAddInvestmentCryptoBrokerName.editableText.toString()
        }
        return  binding.editAddInvestmentEquityBrokerName.editableText.toString()
    }

    private fun getacc_holder_name(): String {
        when (selectedCategoryItem?.id) {
            1 -> return binding.editAddInvestmentEquityAccHolderName.editableText.toString()
            2 -> return binding.editAddInvestmentMfAccHolderName.editableText.toString()
            3 -> return binding.editAddInvestmentSipAccHolderName.editableText.toString()
            4 -> return binding.editAddInvestmentFdAccHolderName.editableText.toString()
            6 -> return binding.editAddInvestmentCryptoAccHolderName.editableText.toString()
            7 -> return binding.editAddInvestmentNpsAccHolderName.editableText.toString()
            8 -> return binding.editAddInvestmentPpfAccHolderName.editableText.toString()
            9 -> return binding.editAddInvestmentBondAccHolderName.editableText.toString()
            10 -> return binding.editAddInvestmentPfAccHolderName.editableText.toString()
            11 -> return binding.editAddInvestmentCertAccHolderName.editableText.toString()
        }
        return binding.editAddInvestmentEquityBrokerName.editableText.toString()
    }

    private fun getfinance_type_id(): Int? {
            return  selectedCategoryItem?.id
    }

    private fun manageFormContentVisibility() {
        binding.llAddInvestmentItemEquity.visibility = View.GONE
        binding.llAddInvestmentItemMf.visibility = View.GONE
        binding.llAddInvestmentItemSip.visibility = View.GONE
        binding.llAddInvestmentItemFd.visibility = View.GONE
        binding.llAddInvestmentItemCrypto.visibility = View.GONE
        binding.llAddInvestmentItemNps.visibility = View.GONE
        binding.llAddInvestmentItemPpf.visibility = View.GONE
        binding.llAddInvestmentItemBond.visibility = View.GONE
        binding.llAddInvestmentItemPf.visibility = View.GONE
        binding.llAddInvestmentItemCert.visibility = View.GONE

        when (selectedCategoryItem?.id) {

            1 -> binding.llAddInvestmentItemEquity.visibility = View.VISIBLE

            2 -> binding.llAddInvestmentItemMf.visibility = View.VISIBLE

            3 -> binding.llAddInvestmentItemSip.visibility = View.VISIBLE

            4 -> binding.llAddInvestmentItemFd.visibility = View.VISIBLE

            6 -> binding.llAddInvestmentItemCrypto.visibility = View.VISIBLE

            7 -> binding.llAddInvestmentItemNps.visibility = View.VISIBLE

            8 -> binding.llAddInvestmentItemPpf.visibility = View.VISIBLE

            9 -> binding.llAddInvestmentItemBond.visibility = View.VISIBLE

            10 ->binding.llAddInvestmentItemPf.visibility = View.VISIBLE

            11 ->binding.llAddInvestmentItemCert.visibility = View.VISIBLE

        }
    }

    private fun isValidForm(): Boolean {
        when (selectedCategoryItem?.id) {

            1 -> !(getequity_name().isEmpty() || getacc_holder_name().isEmpty() || getbroker_name().isEmpty() || getcustomer_id().isEmpty())

            2 -> !(getfund_type().isEmpty() || getacc_holder_name().isEmpty() || getfund_name().isEmpty() || getbroker_name().isEmpty())

            4 -> !(getacc_holder_name().isEmpty() || getbank_name().isEmpty() || getbranch_name().isEmpty() || getfixed_deposit_no().isEmpty())

            6 -> !(getacc_holder_name().isEmpty() || getcrypto_name().isEmpty() || getbroker_name().isEmpty())

            7 -> !(getacc_holder_name().isEmpty() || getnps_type().isEmpty())

            8 -> !(getacc_holder_name().isEmpty() || getppf_account_no().isEmpty())

            9 -> !(getacc_holder_name().isEmpty() || getbond_name().isEmpty())

            10 -> !(getacc_holder_name().isEmpty() || getuan_number().isEmpty())

            11 -> !(getacc_holder_name().isEmpty() || getcertificate_name().isEmpty())

        }
        return true
    }

    private fun getComment(): String {
        return binding.editAddInvestmentComment.editableText.toString()
    }

    private fun getShareNowStatus(): Int {
        return if (binding.switchAddInvestmentShareNow.isChecked)
            1
        else
            0
    }

    private fun setShareNowStatus(isSet: Int?) {
        binding.switchAddInvestmentShareNow.isChecked = isSet == 1
    }


}