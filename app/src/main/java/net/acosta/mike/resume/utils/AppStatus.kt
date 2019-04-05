package net.acosta.mike.resume.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppStatus  @Inject constructor(private val context: Context) {
    private lateinit var connectivityManager: ConnectivityManager
    private var isConnected = false

    val isOnline: Boolean
        get() {
            try {
                connectivityManager = context
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val networkInfo = connectivityManager.activeNetworkInfo
                isConnected = networkInfo != null && networkInfo.isConnected
                return isConnected

            } catch (e: Exception) {
                println("ConnectionChecker Exception: " + e.message)
            }

            return isConnected
        }
}