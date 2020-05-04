package bary.apps.moviesLib.ui.movies.details

import bary.apps.moviesLib.R
import bary.apps.moviesLib.data.network.response.Review
import bary.apps.moviesLib.databinding.ItemReviewBinding
import com.xwray.groupie.databinding.BindableItem

class ReviewItem(
    private val review: Review
) : BindableItem<ItemReviewBinding>(){
    override fun getLayout() = R.layout.item_review

    override fun bind(binding: ItemReviewBinding, position: Int) {
        binding.review = review
    }
}