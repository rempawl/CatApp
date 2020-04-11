package com.example.catapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.catapp.di.AppComponent
import com.example.catapp.network.NetworkCallback
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    val appComponent: AppComponent by lazy {
        (application as MyApp).appComponent
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    @JvmField
    var networkConnectionListener: NetworkCallback.NetworkConnectionListener? = null

    @Inject
    @JvmField
    var networkCallback: NetworkCallback? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
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
            .unregisterNetworkCallback(networkCallback!!)
        networkConnectionListener = null
        networkCallback = null
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun registerNetworkCallback() {
        val networkRequest = createNetworkRequest()

        observeNetworkState()

        if (networkCallback != null) {
            getConnectivityManager()
                .registerNetworkCallback(networkRequest, networkCallback!!)



        }
    }

    private fun observeNetworkState() {
        networkConnectionListener!!.isConnectionActive.observe(this, Observer { isActive ->
            offline_info.visibility = if (!isActive) View.VISIBLE else View.GONE
        })
    }


    private fun getConnectivityManager() =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


}
