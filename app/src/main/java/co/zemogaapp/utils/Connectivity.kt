package co.zemogaapp.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by diego.urrea on 10/3/2020.
 */

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}