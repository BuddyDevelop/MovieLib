package bary.apps.moviesLib.util

import android.app.AlertDialog
import android.content.Context
import bary.apps.moviesLib.R
import com.shashank.sony.fancytoastlib.FancyToast

interface RemovalAlertDialog {
    fun alertDialog(context: Context, dialogTitle: String, dialogMsg: String, toastMsg: String, movieId: Int, positiveBtnAction: (movieId: Int) -> Unit){
        val positiveBtnText = context.getString(R.string.yes)
        val neutralBtnText = context.getString(R.string.no)

        val alertDialogBuilder = AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder.setMessage(dialogMsg)

        alertDialogBuilder.setPositiveButton(positiveBtnText){ dialogInterface, i ->
            dialogInterface.dismiss()
            positiveBtnAction.invoke(movieId)
            FancyToast.makeText(context, toastMsg, FancyToast.LENGTH_LONG, FancyToast.INFO, false).show()
        }

        //not using negative button cuz negative and positive buttons has no margin between them and it looks ugly
        alertDialogBuilder.setNeutralButton(neutralBtnText){ dialogInterface, i -> dialogInterface.cancel()  }

        val dialog = alertDialogBuilder.create()
        dialog.show()
    }
}