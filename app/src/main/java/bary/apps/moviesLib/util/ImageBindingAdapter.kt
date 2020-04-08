package bary.apps.moviesLib.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["imageUrl", "placeholderImage"], requireAll = false)
fun loadImage(view: ImageView, url: String?, placeholderImage: Drawable?) {
    if (placeholderImage != null) {
        Glide.with(view.context).load(url).placeholder(placeholderImage).into(view)
    } else {
        Glide.with(view.context).load(url).into(view)
    }
}