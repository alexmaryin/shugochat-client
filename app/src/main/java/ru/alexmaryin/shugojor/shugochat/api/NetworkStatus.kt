@file:Suppress("DEPRECATION")

package ru.alexmaryin.shugojor.shugochat.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log

class NetworkStatus(
    private val context: Context
) {

    init {
        val networkListener = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(context.applicationInfo.name, "Internet is available")
                status = true
                super.onAvailable(network)
            }

            override fun onLost(network: Network) {
                Log.d(context.applicationInfo.name, "Internet is not available")
                status = false
                super.onLost(network)
            }
        }
        with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerDefaultNetworkCallback(networkListener)
            }
        }
    }

    private var status = false

    fun isConnected() =
        with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                activeNetworkInfo?.isConnectedOrConnecting ?: false
            } else {
                status
            }
        }
}