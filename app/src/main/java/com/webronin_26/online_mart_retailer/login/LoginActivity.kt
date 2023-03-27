package com.webronin_26.online_mart_retailer.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.online_mart_retailer.data.source.TokenManager
import com.webronin_26.online_mart_retailer.login.ui.LoginScreen
import com.webronin_26.online_mart_retailer.EventObserver
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart_retailer.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart_retailer.data.source.UserInformationManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        /**
         * Because viewModel Layer should not hold any Application / Activity context
         * so we use status event to call activity
         * in order to save our token from viewModel Layer's internet response to local storage
         *
         * After saving information to local, we finished current activity and run back to main page
         */
        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS -> {
                    setLogin(
                        token = viewModel.loginInformation.token,
                        id = viewModel.loginInformation.id,
                        name = viewModel.loginInformation.name,
                        account = viewModel.loginInformation.currentAccount,
                        passWord = viewModel.loginInformation.currentPassword
                    )

                    finish()
                }
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION -> {
                    // do nothing
                }
                VIEW_MODEL_INTERNET_ERROR -> {
                    // do nothing
                }
            }
        })

        setContent {
            LoginScreen(viewModel)
        }
    }

    private fun setLogin(token: String, id: Int, name: String, account: String, passWord: String) {
        TokenManager.setToken(this, token)
        UserInformationManager.saveUserID(this, id)
        UserInformationManager.saveUserName(this, name)
        UserInformationManager.saveUserAccount(this, account)
        UserInformationManager.saveUserPassword(this, passWord)
    }
}