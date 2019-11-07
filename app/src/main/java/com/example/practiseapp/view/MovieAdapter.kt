package com.example.practiseapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiseapp.R
import com.example.practiseapp.model.Movie

class MovieAdapter(val blogList: List<Movie>?): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun getItemCount() = blogList!!.size

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        this.mContext = parent.context

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.blog_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mMovie = blogList!!.get(position)

        if(mMovie.posterPath != null){
            Glide.with(mContext!!)
                .load(mMovie.posterPath)
                .into(holder.ivThumbnail)
        }
        if(mMovie.title != null){
            holder.tvTitle.setText(mMovie.title)
        }
        if(mMovie.overview != null){
            holder.tvDescription.setText(mMovie.overview)
        }
        if(mMovie.voteAverage != null){
            holder.tvLink.setText(mMovie.overview);
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvLink: TextView = itemView.findViewById(R.id.tvLink)
    }
}