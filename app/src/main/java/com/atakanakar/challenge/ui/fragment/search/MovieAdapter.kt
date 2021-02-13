package com.atakanakar.challenge.ui.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.atakanakar.challenge.commons.setImage
import com.atakanakar.challenge.databinding.ItemMovieBinding
import com.atakanakar.challenge.network.model.search.Search
import com.bumptech.glide.RequestManager


/**
 * Created by atakanakar on 11.02.2021.
 */
class MovieAdapter(
    private val movieList: MutableList<Search>,
    val glide: RequestManager,
    val onClick: (Search, Int, FragmentNavigator.Extras) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieItemHolder>() {

    inner class MovieItemHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Search, position: Int) {
            binding.movieCover.setImage(item.poster ?: "", glide)

            binding.movieName.text = item.title
            binding.card.apply {
                ViewCompat.setTransitionName(binding.movieCover, "image_$position")
                ViewCompat.setTransitionName(binding.movieName, "title_$position")
                setOnClickListener {
                    val extras = FragmentNavigatorExtras(
                        binding.movieCover to "image_$position",
                        binding.movieName to "title_$position"
                    )
                    onClick(item, position, extras)
                }
            }
        }
    }

    fun clearList() {
        movieList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemHolder {
        return MovieItemHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: MovieItemHolder, position: Int) {
        val item = movieList[position]
        holder.bind(item, position)
    }


    override fun getItemCount(): Int = movieList.size
}