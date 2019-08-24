package com.example.sample.views.weather

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.data.models.DailyWeather
import com.example.sample.data.models.HourlyWeather
import com.example.sample.data.repo.WeatherRepo
import com.example.sample.databinding.ActivityHomeBinding
import com.example.sample.utils.ResourceUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.no_permission_view.*
import kotlinx.android.synthetic.main.weather_bottom_sheet.*
import kotlinx.android.synthetic.main.weather_data_layout.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "HomeActivity"


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.grant_permission -> {
                makePermissionRequest()
            }
            R.id.home -> {
                home.isSelected = true
                notification.isSelected = false
            }
            R.id.notification -> {
                home.isSelected = false
                notification.isSelected = true
            }
            R.id.anchor -> {
                if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                else
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    lateinit var weatherViewModel: WeatherViewModel

    @Inject
    lateinit var weatherrepo: WeatherRepo
    @Inject
    lateinit var resourceUtils: ResourceUtils
    @Inject
    lateinit var hourlyAdapter: HourlyAdapter
    @Inject
    lateinit var dailyDapater: DailyAdapter
    private val MY_PERMISSIONS_REQUEST_COARSE_LOCATION: Int = 121
    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        weatherViewModel =
            ViewModelProviders.of(this, getViewModelFactory())[WeatherViewModel::class.java]
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        )
        binding.weatherViewModel = weatherViewModel
        binding.lifecycleOwner = this
        initializeBottomSheet()
        setupLocation()

        val colorList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_selected),  // Disabled
                intArrayOf(android.R.attr.state_selected)    // Enabled
            ),
            intArrayOf(
                resourceUtils.getColor(R.color.white),     // The color for the Disabled state
                resourceUtils.getColor(R.color.colorAccent)       // The color for the Enabled state
            )
        )
        home.iconTint = colorList
        notification.iconTint = colorList
        home.isSelected = true
        setUpClickListeners()
        setupRecyclerViews()
        setUpNavBars()
    }

    private fun setUpNavBars() {
        weather_bar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {
                    0 -> {
                        hourly.adapter = hourlyAdapter
                    }
                    1 -> {
                        hourly.adapter = dailyDapater
                    }
                }
            }

        })
    }

    private fun setupRecyclerViews() {

        hourly.adapter = hourlyAdapter
        hourly.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        weatherViewModel.hourlyWeather.observe(this,
            Observer<List<HourlyWeather>> { t -> t?.let { hourlyAdapter.submitList(t) } })
        weatherViewModel.dailyWeather.observe(this,
            Observer<List<DailyWeather>> { t -> t?.let { dailyDapater.submitList(t) } })
    }

    private fun getViewModelFactory(): ViewModelProvider.Factory? {
        return object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel(
                    weatherRepo = weatherrepo,
                    resourceUtils = resourceUtils
                ) as T
            }
        }
    }

    private fun setUpClickListeners() {
        grant_permission.setOnClickListener(this)
        anchor.setOnClickListener(this)
        home.setOnClickListener(this)
        notification.setOnClickListener(this)
    }

    private fun setupLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously*
                val dialog = AlertDialog.Builder(this).setTitle(getString(R.string.location))
                    .setMessage(getString(R.string.location_rationale))
                    .setPositiveButton(R.string.grant_permission) { it, _ ->
                        it.dismiss()
                        makePermissionRequest()
                    }
                    .setNegativeButton(R.string.cancel) { it, _ ->
                        it.dismiss()
                    }
                    .create()
                dialog.setOnDismissListener {
                    no_permission_view.visibility = View.VISIBLE
                    weather_data.visibility = View.GONE
                }
                dialog.show()

            } else {
                makePermissionRequest()
            }

        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    weatherViewModel.latitude = location?.latitude.toString()
                    weatherViewModel.longitude = location?.longitude.toString()
                    weatherViewModel.getWeather()
                    weatherViewModel.getLocationName(this)
                }
        }

    }

    override fun onResume() {
        super.onResume()
        if (weatherViewModel.isLocationvalid()) {
            weatherViewModel.getWeather()
        }
    }


    private fun makePermissionRequest() {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSIONS_REQUEST_COARSE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_COARSE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                    weather_data.visibility = View.VISIBLE
                    no_permission_view.visibility = View.GONE
                    setupLocation()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    no_permission_view.visibility = View.VISIBLE
                    weather_data.visibility = View.GONE
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun initializeBottomSheet() {
        // init the bottom sheet behavior

        bottomSheetBehavior = BottomSheetBehavior.from<View>(weather_sheet)

        // set callback for changes
        bottomSheetBehavior?.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // Called every time when the bottom sheet changes its state.
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                animateAnchor(slideOffset)
                animateAppBarVisibility(slideOffset)
            }

        })
        bottomSheetBehavior?.let {
            resourceUtils.getDimen(R.dimen.dimen_80dp)?.toInt()
                ?.let(it::setPeekHeight)
        }

    }

    private fun animateAppBarVisibility(slideOffset: Float) {

        bottom_app_bar.backgroundTint = ColorStateList.valueOf(
            ColorUtils.blendARGB(
                resourceUtils.getColor(R.color.colorPrimary)
                , resourceUtils.getColor(R.color.background_white)
                , slideOffset
            )
        )

        home.setBackgroundColor(
            ColorUtils.blendARGB(
                resourceUtils.getColor(R.color.colorPrimary)
                , resourceUtils.getColor(R.color.background_white)
                , slideOffset
            )
        )
        notification.setBackgroundColor(
            ColorUtils.blendARGB(
                resourceUtils.getColor(R.color.colorPrimary)
                , resourceUtils.getColor(R.color.background_white)
                , slideOffset
            )
        )
    }

    private fun animateAnchor(slideOffset: Float) {
        //0 to 0 degrees
        // 1 to 180 degrees
        anchor.rotation = slideOffset * 180
    }
}
