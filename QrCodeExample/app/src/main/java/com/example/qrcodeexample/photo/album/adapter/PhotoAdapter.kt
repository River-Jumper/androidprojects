package com.example.qrcodeexample.photo.album.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qrcodeexample.R
import com.example.qrcodeexample.photo.model.Photo

class PhotoAdapter(private val items: List<Photo>)
    : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>(){
    class PhotoViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
        val photoView: ImageView = itemView.findViewById(R.id.photoPreview)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_photo_preview, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PhotoViewHolder,
        position: Int
    ) {
        val currentItem = items[position]
        holder.photoView.let {
            Glide.with(it) // 'it' refers to the ImageView
                .load(currentItem.uri)
                .into(it)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}