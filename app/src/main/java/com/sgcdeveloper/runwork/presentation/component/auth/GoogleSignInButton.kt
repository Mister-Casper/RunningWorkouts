package com.sgcdeveloper.runwork.presentation.component.auth

import androidx.activity.compose.rememberLauncherForActivityResult
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
import com.google.android.gms.common.api.ApiException
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.util.google.GoogleApiContract
import com.sgcdeveloper.runwork.presentation.theme.black
import com.sgcdeveloper.runwork.presentation.theme.white

private const val GOOGLE_SIGN_IN_CANCELED = 12501

@Composable
fun GoogleSignInButton(onSuccessful: (gsa: GoogleSignInAccount) -> Unit, onFailed: (error: Throwable) -> Unit) {
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            try {
                val googleAccount = task?.getResult(ApiException::class.java)

                if (googleAccount != null) {
                    onSuccessful(googleAccount)
                }
            } catch (e: ApiException) {
                if (e.statusCode != GOOGLE_SIGN_IN_CANCELED) {
                    onFailed(e)
                }
            }
        }

    Button(
        onClick = { authResultLauncher.launch(signInRequestCode) },
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = white,
            contentColor = Color.Unspecified
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(bottom = 4.dp, top = 4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "google icon",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = stringResource(R.string.onboarding__continue_registration),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically),
                fontSize = 14.sp,
                color = black,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}