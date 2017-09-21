package pl.droidsonroids.bootcamptask.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import pl.droidsonroids.bootcamptask.R
import pl.droidsonroids.bootcamptask.model.Image

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(image: Image, onItemClickListener: (Image, View, View) -> Unit) {
        itemView.titleTextView.apply {
            text = image.title
            animate().cancel()
            translationX = -500f
        }
        Picasso.with(itemView.context).load(image.url).into(itemView.imageView, object : Callback {
            override fun onSuccess() =
                itemView.titleTextView.animate().translationX(0f).setDuration(300).setInterpolator(DecelerateInterpolator()).start()

            override fun onError() = Unit
        })
        itemView.setOnClickListener {
            onItemClickListener.invoke(image, itemView.imageView, itemView.titleTextView)
        }
    }

}