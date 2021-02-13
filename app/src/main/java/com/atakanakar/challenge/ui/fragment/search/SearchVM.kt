package com.atakanakar.challenge.ui.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atakanakar.challenge.commons.BaseViewModel
import com.atakanakar.challenge.network.OmbdApi
import com.atakanakar.challenge.network.model.search.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by atakanakar on 11.02.2021.
 */
@HiltViewModel
class SearchVM @Inject constructor(val api: OmbdApi): BaseViewModel() {


    private val _searchedMovieResponse = MutableLiveData<SearchResponse>()
    val searchedMovieResponse: LiveData<SearchResponse>
        get() =  _searchedMovieResponse


    fun getMovies(keyword: String) {
        sendRequest( {api.searchMovie(keyword)} ) {
            _searchedMovieResponse.postValue(it)
        }
    }
}