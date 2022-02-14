package com.app.movies.data


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.movies.R
import com.bumptech.glide.Glide

data class Data(
    val genre: String, val id: Int = 0, val poster: String, val title: String, val year: String
) {
    companion object DataBindingAdapter {
        @BindingAdapter("app:image_url")
        @JvmStatic
        fun loadImage(imageView: ImageView, image: String) {
            Glide.with(imageView).load(image).placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.file_not_found).into(imageView)
        }
    }
}