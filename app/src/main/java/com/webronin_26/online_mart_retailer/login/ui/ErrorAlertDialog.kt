package com.webronin_26.online_mart_retailer.login.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.webronin_26.online_mart_retailer.login.LoginViewModel

@Composable
fun ErrorAlertDialog(
    loginViewModel : LoginViewModel
) {
    val state by loginViewModel.state.collectAsState()

    if (state.showAlertDialog) {
        AlertDialog(
            onDismissRequest = {
                loginViewModel.updateState(
                    show = false,
                    titleString = "",
                    alertString = "",
                )
            },
            title = {
                Text( text = state.errorTitle)
            },
            text = {
                Text( text = state.errorText)
            },
            confirmButton = {
                Button( onClick = {
                    loginViewModel.updateState(
                        show = false,
                        titleString = "",
                        alertString = "",)
                }) {
                    Text("CONFIRM")
                }
            }
        )
    }
}