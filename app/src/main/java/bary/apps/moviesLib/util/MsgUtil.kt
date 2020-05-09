package bary.apps.moviesLib.util

import android.content.Context
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

object MsgUtil {
    fun showErrorToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        FancyToast.makeText(context, message, duration, FancyToast.ERROR, false).show()
    }
}