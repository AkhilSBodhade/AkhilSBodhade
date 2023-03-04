package com.mutefrog.myfolio.utils

import android.util.Base64
import com.mutefrog.myfolio.utils.Constants.INITIAL_VECTOR
import com.mutefrog.myfolio.utils.Constants.UNIQUE_KEY
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}

fun String.encrypt(): String {
    try {
        val srcBuff = this.toByteArray(charset("UTF8"))
        val skeySpec = SecretKeySpec(Base64.decode(UNIQUE_KEY, Base64.DEFAULT), "AES")
        val ivSpec = IvParameterSpec(Base64.decode(INITIAL_VECTOR, Base64.DEFAULT))
        val ecipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        ecipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec)
        val dstBuff: ByteArray = ecipher.doFinal(srcBuff)
        return Base64.encodeToString(dstBuff, Base64.DEFAULT)
    } catch (ex: Exception) { }
    return ""
}

fun String.decrypt(): String? {
    try {
        val skeySpec = SecretKeySpec(Base64.decode(UNIQUE_KEY, Base64.DEFAULT), "AES")
        val ivSpec = IvParameterSpec(Base64.decode(INITIAL_VECTOR, Base64.DEFAULT))
        val ecipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        ecipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec)
        val raw: ByteArray = Base64.decode(this, Base64.DEFAULT)
        val originalBytes: ByteArray = ecipher.doFinal(raw)
        return String(originalBytes, Charset.forName("UTF-8"))
    } catch (ex: Exception) {}
    return null
}