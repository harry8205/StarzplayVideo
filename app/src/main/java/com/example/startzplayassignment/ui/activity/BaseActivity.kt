package com.example.startzplayassignment.ui.activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.startzplayassignment.R
import java.util.Objects

open class BaseActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null

    public fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            networkInfo.isConnected
        }
    }
    fun redirectToNextActivity(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
        finish() // Optionally finish this activity so user can't navigate back to it
    }
    fun internetConnectionDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.information_dialog)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        dialog.show()

        val tvCancel: TextView = dialog.findViewById(R.id.tvCancel)
        val deleteAccounts: TextView = dialog.findViewById(R.id.tvSubmit)
        val title: TextView = dialog.findViewById(R.id.tvTitle)
        val divider = dialog.findViewById<View>(R.id.view_divider)
        divider.visibility= View.GONE
        tvCancel.visibility= View.GONE
        title.setText(getString(R.string.an_error_occur_please_check_your_internet_connection))//msg update
        deleteAccounts.text = getString(R.string.ok)
        deleteAccounts.setOnClickListener(View.OnClickListener { // Handle cancel button click
            //dialog.dismiss()
            System.exit(0)
        })

    }
    fun showProgressDialog(context: Context) {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
            progressDialog = ProgressDialog(context, R.style.ProgressDialogTheme).apply {
                setProgressStyle(android.R.style.Widget_ProgressBar_Small)

                show()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun dismissProgressDialog() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


}