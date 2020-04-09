package com.example.catapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.catapp.di.AppComponent
import com.example.catapp.network.NetworkCallback

class MainActivity : AppCompatActivity(), NetworkCallback.NetworkChangeListener {

    val appComponent: AppComponent by lazy {
        (application as MyApp).appComponent
    }
    private lateinit var appBarConfiguration: AppBarConfiguration


    private val networkCallback =
        NetworkCallback(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerNetworkCallback()

        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_cat_list_fragment))
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    private fun createNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        getConnectivityManager()
            .unregisterNetworkCallback(networkCallback)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onInactive() {
        Toast.makeText(applicationContext,getText(R.string.offline_info),Toast.LENGTH_LONG)
            .show()


    }

    private fun registerNetworkCallback() {
        val networkRequest = createNetworkRequest()

        getConnectivityManager()
            .registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun getConnectivityManager() =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


}
