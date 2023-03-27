package com.webronin_26.online_mart_retailer.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.webronin_26.online_mart_retailer.login.LoginViewModel

@Composable
fun LoginButton(
    loginUiObject: LoginUiObject,
    loginViewModel: LoginViewModel,
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = { sendLoginRequest(loginUiObject, loginViewModel) },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape)
                .clip(roundedCornerShape)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White,
            )
        }
    }
}

fun sendLoginRequest(loginUiObject: LoginUiObject, loginViewModel: LoginViewModel) {
    if (loginUiObject.accountText.isEmpty() || loginUiObject.passwordText.isEmpty()) {
        loginViewModel.updateState(
            show = true,
            titleString = "警告",
            alertString = "帳密不能為空",
        )
    } else {
        loginViewModel.loginRequest(
            account = loginUiObject.accountText,
            password = loginUiObject.passwordText
        )
    }
}