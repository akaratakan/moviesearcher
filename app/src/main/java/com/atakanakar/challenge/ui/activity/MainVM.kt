package com.atakanakar.challenge.ui.activity

import androidx.lifecycle.ViewModel
import com.atakanakar.challenge.network.OmbdApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by atakanakar on 10.02.2021.
 */
@HiltViewModel
class MainVM @Inject constructor(val api: OmbdApi) : ViewModel() {

}