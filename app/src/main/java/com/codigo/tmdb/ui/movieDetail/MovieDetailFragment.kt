package com.codigo.tmdb.ui.movieDetail

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codigo.tmdb.R
import com.codigo.tmdb.databinding.FragmentMovieDetailBinding
import com.codigo.tmdb.util.Constants.IMAGE_BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel by viewModels<MovieDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieDetailBinding.bind(view)

        with(binding) {
            var movie = args.movie
            val requestOptions = RequestOptions().transform(RoundedCorners(16))
            Glide.with(requireContext())
                .load(IMAGE_BASE_URL + movie.posterPath)
                .placeholder(R.drawable.tmdb)
                .error(R.drawable.tmdb)
                .apply(requestOptions)
                .into(ivMovie)
            Glide.with(requireContext())
                .load(if (movie.isFavorite) R.drawable.baseline_star_filled_24 else R.drawable.baseline_star_border_24)
                .into(ivFavorite)
            tvMovieName.text= movie.title
            tvReleaseDate.text = resources.getString(R.string.release_date, movie.releaseDate)
            tvOverview.text = movie.overview

            ivFavorite.setOnClickListener {
                viewModel.toggleFavorite(movie.id)
                movie = movie.copy(isFavorite = !movie.isFavorite)
                Glide.with(requireContext())
                    .load(if (movie.isFavorite) R.drawable.baseline_star_filled_24 else R.drawable.baseline_star_border_24)
                    .into(ivFavorite)
            }
        }
    }

}