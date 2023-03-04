package com.mutefrog.myfolio.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.kishandonga.csbx.CustomSnackbar
import com.mutefrog.myfolio.R
import com.mutefrog.myfolio.model.Token
import com.mutefrog.myfolio.view.activity.AppScanActivity
import com.mutefrog.myfolio.view.activity.MainActivity
import com.mutefrog.myfolio.view.activity.OnboardingActivity
import dmax.dialog.SpotsDialog
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import javax.crypto.Cipher
import javax.crypto.CipherOutputStream
import javax.crypto.spec.SecretKeySpec

fun manageActionBar(activity: FragmentActivity?, visibility: Int, title: String) {
    val actionBarCustomView: View? =
        (activity as AppCompatActivity).supportActionBar?.customView
    actionBarCustomView?.findViewById<ImageView>(R.id.img_action_bar_back)?.visibility = visibility
    actionBarCustomView?.findViewById<AppCompatTextView>(R.id.txt_action_bar_title)?.text = title
}

fun manageMainActivityActionBar(activity: FragmentActivity?, visibility: Int, title: String) {
    val supportActionBar = (activity as AppCompatActivity).supportActionBar
    if (supportActionBar != null && supportActionBar!!.customView != null) {
        supportActionBar.show()
        supportActionBar.customView.findViewById<TextView>(R.id.txt_main_act_action_bar_title).text =
            title
        supportActionBar.customView.findViewById<ImageView>(R.id.img_main_act_action_bar_back).visibility =
            visibility
    }
}

fun getProgressDialog(requireActivity: FragmentActivity): AlertDialog? {
    return SpotsDialog.Builder()
        .setContext(requireActivity)
        .setMessage(requireActivity.getString(R.string.msg_loading))
        .build()
}

fun getAccessToken(activity: FragmentActivity): String? {
    val loggedInUserToken =
        ModelPreferencesManager.get<Token>(activity.getString(R.string.pref_logged_in_user_token))
    return "Bearer " + loggedInUserToken?.accessToken
}

fun hideMainActivityActionBar(activity: FragmentActivity?) {
    (activity as AppCompatActivity).supportActionBar?.hide()
}

fun showMainActivityBottomBar(activity: FragmentActivity?) {
    (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
}

fun hideMainActivityBottomBar(activity: FragmentActivity?) {
    (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.GONE
}

fun setHTMLText(textView: AppCompatTextView, text: String) {
    textView.text = HtmlCompat.fromHtml(
        text,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
}

fun navigateToHomeScreen(activity: FragmentActivity?) {
    val intent = Intent(activity, MainActivity::class.java)
    activity?.startActivity(intent)
    activity?.finish()
}

fun navigateToOnboardingScreen(activity: FragmentActivity?) {
    val intent = Intent(activity, OnboardingActivity::class.java)
    activity?.startActivity(intent)
    activity?.finish()
}

fun isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun displaySnackbarAlert(requireActivity: FragmentActivity, root: View, msg: String?) {
    CustomSnackbar(requireActivity, root).show {
        duration(Snackbar.LENGTH_LONG)
        if (msg != null) {
            message(msg)
        }
        withAction(android.R.string.ok) {
            it.dismiss()
        }
    }
}

fun getDeviceId(activity: FragmentActivity?): String? {
    return Settings.Secure.getString(activity?.contentResolver, Settings.Secure.ANDROID_ID)
}

fun openDocScanner(requireContext: Context) {
    AppScanActivity.start(requireContext)
}

fun encryptFile(requireContext: Context){
}

val key: String = "KERQIRUDYTH"

// on below line creating a method to encrypt an image.
fun encrypt(file: File) {
//    // on below line creating a
//    // variable for file input stream
    val fis = FileInputStream(file.path)

//    // on below line creating a variable for context  wrapper.
//    val contextWrapper = ContextWrapper(requireContext)
//
//    // on below line creating a variable for file
//    val photoDir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM)
//
//    // on below line creating a file for encrypted file.
//    val file = File(photoDir, "encfile" + ".png")

    // on below line creating a variable for file output stream.
    val fos = FileOutputStream(file.path)

    // on below line creating a variable for secret key.
    // creating a variable for secret key and passing our
    // secret key and algorithm for encryption.
    val sks = SecretKeySpec(key.toByteArray(), "AES")

    // on below line creating a variable for
    // cipher and initializing it
    val cipher = Cipher.getInstance("AES")

    // on below line initializing cipher and
    // specifying decrypt mode to encrypt.
    cipher.init(Cipher.ENCRYPT_MODE, sks)

    // on below line creating cos
    val cos = CipherOutputStream(fos, cipher)
    var b: Int
    val d = ByteArray(8)
    while (fis.read(d) != -1) {
        fos.write(d, 0, fis.read(d))
    }

    // on below line
    // closing our cos and fis.
    cos.flush()
    cos.close()
    fis.close()
}

 fun setDate(requireActivity: FragmentActivity, editText: AppCompatEditText) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val dpd = DatePickerDialog(
        requireActivity,
        { view, year, monthOfYear, dayOfMonth ->
            val month = monthOfYear + 1
            val dateString = java.lang.String.format("%d-%02d-%02d", year, month, dayOfMonth)
            editText.setText(dateString)
        },
        year,
        month,
        day
    )
    dpd.show()
}

fun showCustomDialog(requireContext: Context, title: String) {
    val dialog = Dialog(requireContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.view_custom_dialog)
    val body = dialog.findViewById(R.id.txt_custom_dialog_title) as AppCompatTextView
    body.text = title
    val yesBtn = dialog.findViewById(R.id.btn_custom_dialog_positive) as AppCompatButton
    val noBtn = dialog.findViewById(R.id.btn_custom_dialog_negative) as AppCompatButton
    yesBtn.setOnClickListener {
        dialog.dismiss()
    }
    noBtn.setOnClickListener { dialog.dismiss() }
    dialog.show()
}


fun showShareNowAlert(requireContext: Context){
    val dialog = Dialog(requireContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.view_share_now_dialog)
    val closeBtn = dialog.findViewById(R.id.img_close) as AppCompatImageView
    closeBtn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}





