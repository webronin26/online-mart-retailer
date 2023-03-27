@file:OptIn(ExperimentalMaterial3Api::class)

package com.webronin_26.online_mart_retailer.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.webronin_26.online_mart_retailer.R
import com.webronin_26.online_mart_retailer.login.LoginViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign

data class LoginUiObject(
    var accountText: String,
    var passwordText: String)

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
) {
    val loginUiObject = LoginUiObject(accountText = "", passwordText = "")

    /**
     * set screen alert dialog, default is invisible
     * use viewModel layer to control visibility and alert text
     */
    ErrorAlertDialog(loginViewModel)

    /**
     * LoginScreen main UI
     * has one login image, two TextField to enter user account and password
     * and one button to send login request
     */
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
            ).align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
            )
            Column(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                androidx.compose.material3.Text(
                    text = "Login",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(8.dp))
                AccountTextField(loginUiObject)

                Spacer(modifier = Modifier.padding(3.dp))
                PasswordTextField(loginUiObject)

                Spacer(modifier = Modifier.padding(10.dp))
                LoginButton(
                    loginUiObject = loginUiObject,
                    loginViewModel = loginViewModel ,
                    gradientColors = listOf(Color(0xFF484BF1), Color(0xFF673AB7)),
                    cornerRadius = 16.dp,
                    nameButton = "Login",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
                )
            }
        }
    }
}
