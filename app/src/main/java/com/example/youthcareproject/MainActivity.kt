package com.example.youthcareproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.ActionBar
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import com.example.youthcareproject.databinding.ActivityMainBinding
import com.auth0.android.callback.Callback
import com.auth0.android.result.UserProfile
import com.auth0.android.result.Credentials
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var container: ConstraintLayout
    private lateinit var appBarConfiguration: AppBarConfiguration;

    private lateinit var account: Auth0
    private var isLoggedIn = false

    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("login", MODE_PRIVATE)
        editor = prefs.edit()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        container = binding.container

        setUpNav()

        account = Auth0(
            getString(R.string.com_auth0_clientID),
            getString(R.string.com_auth0_domain)
        )
    }

    private fun setUpNav(){
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_favourites,
                R.id.navigation_search,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(this, object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    editor.clear()
                    editor.commit()
                    isLoggedIn = false
                    Toast.makeText(applicationContext,"Succesvol uitgelogd", Toast.LENGTH_SHORT).show()

                    val mainIntent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(mainIntent)
                }

                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(applicationContext,"Uitloggen mislukt", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showUserProfile(accessToken: String) {
        var client = AuthenticationAPIClient(account)

        // With the access token, call `userInfo` and get the profile from Auth0.
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    Log.i("MainActivity", "UserProfile onFailure() called $exception")
                }

                override fun onSuccess(profile: UserProfile) {
                    // We have the user's profile!

                    val email = profile.email
                    val name = profile.name
                    Log.i("MainActivity", "$email $name")
                }
            })
    }
}