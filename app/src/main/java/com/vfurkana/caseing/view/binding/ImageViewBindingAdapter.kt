package com.vfurkana.caseing.view.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(
    value = ["imageUrl", "errorDrawable"],
    requireAll = false
)
fun bindImageView(
    imageView: ImageView,
    imageUrl: String,
    errorDrawable: Drawable?
) {
    if (imageUrl.isNullOrEmpty()) return

    Glide.with(imageView)
        .load(imageUrl)
        .centerInside()
        .error(errorDrawable)
        .into(imageView)
}