package com.atakanakar.challenge.commons

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.atakanakar.challenge.R
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions


/**
 * Created by atakanakar on 12.02.2021.
 */
@SuppressLint("CheckResult")
fun ImageView.getGlideRequestOptions(): RequestOptions {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(R.drawable.ic_glasses)
    return requestOptions
}

/**
 * String url, File,
 */
fun ImageView.setImage(any: String,glide:RequestManager) {
    glide.load(any)
        .apply(getGlideRequestOptions())
        .into(this)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}