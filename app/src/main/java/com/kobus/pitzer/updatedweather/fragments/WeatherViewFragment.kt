package com.kobus.pitzer.updatedweather.fragments

import android.Manifest
import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kobus.pitzer.updatedweather.R
import com.kobus.pitzer.updatedweather.adapters.WeatherForecastAdapter
import com.kobus.pitzer.updatedweather.databinding.FragmentWeatherViewBinding
import com.kobus.pitzer.updatedweather.repository.constant.Status
import com.kobus.pitzer.updatedweather.repository.constant.WeatherTypes
import com.kobus.pitzer.updatedweather.repository.models.Daily
import com.kobus.pitzer.updatedweather.repository.preferences.CorePreferences
import com.kobus.pitzer.updatedweather.repository.viewmodels.WeatherViewModel
import org.koin.android.ext.android.inject
import timber.log.Timber

class WeatherViewFragment : Fragment() {
    private var hasAddedObserver = false
    private lateinit var rootView: View
    private lateinit var binding: FragmentWeatherViewBinding
    private val viewModel: WeatherViewModel by inject()
    private lateinit var weatherAdapter: WeatherForecastAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var networkDialog: AlertDialog? = null

    private val networkCallback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Timber.i("Network available")
                if (networkDialog != null && networkDialog?.isShowing!!) {
                    networkDialog?.dismiss()
                }
                requireActivity().runOnUiThread {
                    binding.ivNetworkState.visibility = View.GONE
                }
                getUserLocation()
            }

            override fun onLost(network: Network) {
                Timber.i("Network Unavailable")
                showNoNetworkErrorDialog()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_weather_view,
                container,
                false
            )


        rootView = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.lifecycleOwner = requireActivity()
        binding.viewModel = viewModel

        setupThemeObserver()
        getUserLocation()
        registerNetworkCallback()
    }

    private fun setupThemeObserver() {
        viewModel.weatherType.observe(requireActivity(), Observer { weatherTypes ->
            when (weatherTypes) {
                WeatherTypes.SUNNY -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireActivity(), R.color.sunnyBackground)
                WeatherTypes.RAINY -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireActivity(), R.color.rainyBackground)
                WeatherTypes.CLOUDY -> requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireActivity(), R.color.cloudyBackground)
                else -> ContextCompat.getColor(requireActivity(), R.color.sunnyBackground)
            }
        })
    }

    private fun getUserLocation() {
        TedPermission.with(requireActivity())
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    Timber.i("Permission granted")
                    fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(requireActivity())
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener(requireActivity()) { loc: Location? ->
                            // Got last known location. In some rare situations this can be null.
                            if (loc != null) {
                                Timber.i((loc.latitude + loc.longitude).toString())
                                CorePreferences.setLastLat(loc.latitude.toString())
                                CorePreferences.setLastLong(loc.longitude.toString())
                                viewModel.lat = loc.latitude.toString()
                                viewModel.long = loc.longitude.toString()
                                getWeather()
                            }
                        }
                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    Timber.i("Permission denied")
                    binding.currentTemp.text = ":("
                }
            })
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
    }

    private fun getWeather() {
        if (!isNetworkAvailable()) {
            showNoNetworkErrorDialog()
        }
        viewModel.getLocationWeather()
        setRecyclerViewObserver()
    }

    private fun setupRecyclerView() {
        weatherAdapter = WeatherForecastAdapter(requireActivity())
        binding.rvWeatherForecast.adapter = weatherAdapter
        binding.rvWeatherForecast.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setRecyclerViewObserver() {
        viewModel.currentDailyForecastWeather.observe(requireActivity(), Observer {
            if (it.status == Status.SUCCESS || it.status == Status.CACHE && it.data != null) {
                val weatherList = mutableListOf<Daily>()
                weatherList.addAll(it.data?.daily!!)
                weatherList.removeAt(0)
                weatherAdapter.setWeatherList(weatherList)
                Timber.i("getWeather location data returned")
            }
        })
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showNoNetworkErrorDialog() {
        requireActivity().runOnUiThread {
            binding.ivNetworkState.visibility = View.VISIBLE
        }
        if (networkDialog != null) {
            networkDialog!!.dismiss()
        }
        val builder = AlertDialog.Builder(requireActivity())
        builder.setCancelable(false)
        builder.setTitle(this.getString(R.string.network_issue_title))
        builder.setMessage(this.getString(R.string.network_error_and_flight_mode))
        builder.setPositiveButton(this.getString(R.string.ok)) { _, _ ->
            if (networkDialog != null) {
                requireActivity().runOnUiThread {
                    networkDialog!!.dismiss()
                }
            }
        }
        networkDialog = builder.create()
        networkDialog?.show()
    }


    private fun registerNetworkCallback() {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }
}