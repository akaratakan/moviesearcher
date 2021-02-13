package com.atakanakar.challenge.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.atakanakar.challenge.R
import com.atakanakar.challenge.commons.Constants
import com.atakanakar.challenge.commons.setImage
import com.atakanakar.challenge.databinding.FragmentDetailBinding
import com.atakanakar.challenge.databinding.FragmentSearchBinding
import com.atakanakar.challenge.ui.activity.MainActivity
import com.atakanakar.challenge.ui.dialog.InfoDialog
import com.atakanakar.challenge.ui.fragment.search.SearchVM
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by atakanakar on 11.02.2021.
 */
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailVM by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSelectedMovieDetail(arguments?.getString(Constants.idArg))
        observeLiveData()
        prepareTransitionAnimation()
    }

    private fun observeLiveData() {
        viewModel.movieResponse.observe(viewLifecycleOwner) {
            binding.movieCover.setImage(it.poster ?: "", glide)
            binding.movieName.text = it.title
            binding.directorName.text = it.director
            binding.imdbRateText.text = it.imdbRating
            binding.movieGenre.text = it.genre
            binding.movieActors.text = it.actors?.replace(", ", "\n")
            binding.moviePlot.text = it.plot
        }
        viewModel.loadingDetection.observe(viewLifecycleOwner) {
            (activity as MainActivity).showLoading(it)
        }
        viewModel.networkErrorDetection.observe(viewLifecycleOwner) {
            InfoDialog(getString(R.string.error), it).show(childFragmentManager, "")
        }
    }

    private fun prepareTransitionAnimation() {
        val position = arguments?.getInt(Constants.positionArg)
        ViewCompat.setTransitionName(binding.movieCover, "image_$position")
        ViewCompat.setTransitionName(binding.movieName, "title_$position")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}