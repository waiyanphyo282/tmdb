package com.codigo.tmdb.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codigo.tmdb.R
import com.codigo.tmdb.data.Movie
import com.codigo.tmdb.databinding.ItemMovieBinding
import com.codigo.tmdb.util.Constants.IMAGE_BASE_URL
import com.codigo.tmdb.util.TAG


class MovieAdapter: ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiff) {

    private var onItemClickListener: ((Movie) -> Unit)? = null
    private var onFavoriteClickListener: ((Int) -> Unit)? = null

    fun setOnFavoriteClickListener(listener:(Int) -> Unit) {
        onFavoriteClickListener = listener
    }

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    object MovieDiff: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding): ViewHolder(binding.root) {
        fun bindData(movie: Movie) {
            with(binding) {
                tvMovieName.text = movie.originalTitle
                val requestOptions = RequestOptions().transform(RoundedCorners(16))
                Glide.with(binding.root)
                    .load(IMAGE_BASE_URL + movie.posterPath)
                    .placeholder(R.drawable.tmdb)
                    .error(R.drawable.tmdb)
                    .apply(requestOptions)
                    .into(ivMovie)
                root.setOnClickListener {
                    onItemClickListener?.invoke(movie)
                }
                ivFavorite.setOnClickListener {
                    Log.d(TAG, "bindData: onFavorite Click ${movie.title}")
                    onFavoriteClickListener?.invoke(movie.id)
                }
                Log.d(TAG, "bindData: isFavorite ${movie.isFavorite}")
                Glide.with(binding.root)
                    .load(if (movie.isFavorite) R.drawable.baseline_star_filled_24 else R.drawable.baseline_star_border_24)
                    .into(ivFavorite)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}