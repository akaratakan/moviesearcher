package com.atakanakar.challenge.ui.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atakanakar.challenge.commons.BaseViewModel
import com.atakanakar.challenge.network.OmbdApi
import com.atakanakar.challenge.network.model.id.DetailResponseObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by atakanakar on 11.02.2021.
 */
@HiltViewModel
class DetailVM @Inject constructor(val api: OmbdApi) : BaseViewModel() {

    private val _movieResponse = MutableLiveData<DetailResponseObject>()
    val movieResponse: LiveData<DetailResponseObject>
        get() = _movieResponse

    fun getSelectedMovieDetail(imdbId: String?) {
        sendRequest({ api.getMovieById(imdbId) }) {
            _movieResponse.postValue(it)
        }
    }

}