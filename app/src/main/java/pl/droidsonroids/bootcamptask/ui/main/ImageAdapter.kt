package pl.droidsonroids.bootcamptask.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.droidsonroids.bootcamptask.R
import pl.droidsonroids.bootcamptask.model.Image

class ImageAdapter(
        private val items: List<Image>,
        private val onItemClickListener: (Image, View, View) -> Unit
) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }

}