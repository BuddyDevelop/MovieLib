package bary.apps.moviesLib.util

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bary.apps.moviesLib.ui.base.BaseMoviesViewModel
import retrofit2.HttpException

interface RecyclerViewScrollListener {
    var mRecyclerScrollListener: RecyclerView.OnScrollListener
    val viewModel: BaseMoviesViewModel
    val mRecyclerView: RecyclerView
    val mBottomProgressBar: ProgressBar

    fun setRecyclerViewScrollListener(page: Int, funToFetchData: (page: Int) -> Unit){
        mRecyclerScrollListener = object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager
                val totalItemsCount = layoutManager?.itemCount
                val lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                if(totalItemsCount == lastVisibleItemPosition + 1) {
                    mBottomProgressBar.visibility = View.VISIBLE   //show progress bar

                    try {
                        funToFetchData.invoke(page) //invoke method to get next page of data
                    } catch (e: HttpException){
                        Log.e("ScrollListener", e.message())
                    }

                    //remove scroll listener to avoid loading multiple data
                    mRecyclerView.removeOnScrollListener(mRecyclerScrollListener)
                }
            }
        }
        mRecyclerView.addOnScrollListener(mRecyclerScrollListener)
        mBottomProgressBar.visibility = View.GONE //hide progress bar
    }
}