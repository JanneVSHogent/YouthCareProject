package com.example.youthcareproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.youthcareproject.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var container: ConstraintLayout

    private lateinit var account: Auth0

    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = Auth0(
            getString(R.string.com_auth0_clientID),
            getString(R.string.com_auth0_domain)
        )

        val prefs = getSharedPreferences("login", MODE_PRIVATE)
        editor = prefs.edit()

        if (prefs.getString("user_email", "")?.isEmpty() == true) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
            container = binding.container

            val loginButton = findViewById<Button>(R.id.btn_login)

            loginButton.setOnClickListener {
                loginWithBrowser()
            }
        } else {
            Log.i("User Email", "User Email: " + prefs.getString("user_email", ""))
            finish()
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.
        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(exception: AuthenticationException) {
                    Toast.makeText(applicationContext,"Inloggen mislukt", Toast.LENGTH_SHORT).show()
                }

                // Called when authentication completed successfully
                override fun onSuccess(credentials: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    val accessToken = credentials.accessToken
                    editor.putString("user_email", credentials.user.email)
                    editor.commit()
                    Toast.makeText(applicationContext,"Succesvol ingelogd", Toast.LENGTH_SHORT).show()
                    showUserProfile(accessToken)

                    val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(mainIntent)
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