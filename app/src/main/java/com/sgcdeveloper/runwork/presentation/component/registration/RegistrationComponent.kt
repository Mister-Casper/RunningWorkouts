package com.sgcdeveloper.runwork.presentation.component.registration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.theme.black
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
fun RegistrationComponent(
    onGoogleSignInSuccessful: (account: GoogleSignInAccount) -> Unit,
    onGoogleSignInFailed: (error: Throwable) -> Unit,
    actionEmail: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, bottom = 32.dp)
    ) {
        GoogleSignInButton(
            onSuccessful = { account -> onGoogleSignInSuccessful(account) },
            onFailed = { error -> onGoogleSignInFailed(error) })
        Button(
            onClick = { actionEmail() },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = black,
                contentColor = Color.Unspecified
            ),
            border = BorderStroke(1.dp, white),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Row(Modifier.padding(bottom = 4.dp, top = 4.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "google icon",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    tint = white
                )
                Text(
                    text = stringResource(R.string.onboarding__email_registration),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 14.sp,
                    color = white,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}