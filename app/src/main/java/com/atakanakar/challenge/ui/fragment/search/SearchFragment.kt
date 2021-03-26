package com.atakanakar.challenge.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.atakanakar.challenge.R
import com.atakanakar.challenge.commons.Constants
import com.atakanakar.challenge.commons.hideKeyboard
import com.atakanakar.challenge.databinding.FragmentSearchBinding
import com.atakanakar.challenge.network.model.search.Search
import com.atakanakar.challenge.ui.activity.MainActivity
import com.atakanakar.challenge.ui.dialog.InfoDialog
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by atakanakar on 11.02.2021.
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    lateinit var glide: RequestManager

    lateinit var movieAdapter: MovieAdapter

    private val viewModel: SearchVM by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initListener()
        binding.searchButton.isEnabled = false
        viewModel.searchedMovieResponse.observe(viewLifecycleOwner) {
            if (this::movieAdapter.isInitialized) {
                movieAdapter.updateList(it.search.toMutableList())
            } else {
                prepareRecyclerView(it.search.toMutableList())
            }
        }
        viewModel.loadingDetection.observe(viewLifecycleOwner) {
            (activity as MainActivity).showLoading(it)
        }
        viewModel.networkErrorDetection.observe(viewLifecycleOwner) {
            InfoDialog(getString(R.string.error), it).show(childFragmentManager, "")
        }
    }

    private fun initListener() {
        binding.searchKeyword.doOnTextChanged { text, _, _, _ ->
            binding.searchButton.isEnabled = text.toString().length >= 3
        }
        binding.searchButton.setOnClickListener {
            it.hideKeyboard()
            viewModel.getMovies(binding.searchKeyword.text.toString())
        }
    }

    private fun prepareRecyclerView(movieList: MutableList<Search>) {
        movieAdapter = MovieAdapter(movieList, glide, ::onMovieItemClick)
        binding.movieRv.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun onMovieItemClick(
        searchResponse: Search,
        position: Int,
        extras: FragmentNavigator.Extras
    ) {
        Bundle().apply {
            putInt(Constants.positionArg, position)
            putString(Constants.coverArg, searchResponse.poster)
            putString(Constants.titleArg, searchResponse.title)
            putSerializable(Constants.idArg, searchResponse.imdbID)
            findNavController().navigate(
                R.id.action_searchFragment_to_detailFragment,
                this,
                null,
                extras
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}