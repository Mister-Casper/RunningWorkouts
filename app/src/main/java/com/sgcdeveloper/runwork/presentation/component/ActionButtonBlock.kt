package com.sgcdeveloper.runwork.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgcdeveloper.runwork.presentation.theme.dark_blue
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
fun ActionButtonBlock(modifier: Modifier = Modifier, actionTest: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = dark_blue.copy(alpha = 0.8f),
            contentColor = white
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = actionTest,
            modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
