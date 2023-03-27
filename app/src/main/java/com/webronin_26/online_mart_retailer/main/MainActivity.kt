package com.webronin_26.online_mart_retailer.main

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.webronin_26.online_mart_retailer.R
import com.webronin_26.online_mart_retailer.data.source.TokenManager
import com.webronin_26.online_mart_retailer.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initNavController()
        initActionBar()
    }

    private fun initNavController() {
        navController = findNavController(R.id.main_container)
        appBarConfiguration = AppBarConfiguration(navController.graph, findViewById(R.id.main_drawer_layout))
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.main_navigation_view).setupWithNavController(navController)
    }

    private fun initActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = Html.fromHtml("<font color='#FF7744'> DashBoard </font>")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun checkLoginState() {
        val token = TokenManager.getToken(this)
        if (token == "") startActivity(Intent(this, LoginActivity::class.java))
    }
}