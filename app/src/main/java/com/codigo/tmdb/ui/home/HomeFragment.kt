package com.codigo.tmdb.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.codigo.tmdb.R
import com.codigo.tmdb.databinding.FragmentHomeBinding
import com.codigo.tmdb.ui.adapter.MovieAdapter
import com.codigo.tmdb.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val upcomingMovieAdapter by lazy { MovieAdapter() }
    private val popularMovieAdapter by lazy { MovieAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        with(binding) {
            rvUpcomingMovies.adapter = upcomingMovieAdapter
            rvPopularMovies.adapter = popularMovieAdapter
        }

        viewModel.upcomingMovies.observe(viewLifecycleOwner) {
            with(binding) {
                progressBar.hide()
                tvUpcomingMovies.visibility = View.VISIBLE
                rvUpcomingMovies.visibility = View.VISIBLE
            }
            Log.d(TAG, "onViewCreated: upcomingMovies $it")
            upcomingMovieAdapter.submitList(it)
        }

        viewModel.popularMovies.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: popularMovies $it")
            with(binding) {
                progressBar.hide()
                tvPopularMovies.visibility = View.VISIBLE
                rvPopularMovies.visibility = View.VISIBLE
            }
            popularMovieAdapter.submitList(it)
        }

        upcomingMovieAdapter.setOnFavoriteClickListener {
            viewModel.toggleFavorite(it)
        }

        upcomingMovieAdapter.setOnItemClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it))
        }

        popularMovieAdapter.setOnFavoriteClickListener {
            viewModel.toggleFavorite(it)
        }

        popularMovieAdapter.setOnItemClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it)) }
    }

}