package com.sgcdeveloper.runwork.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
fun ColumnScope.InputField(
    value: String,
    onValueChange: (it: String) -> Unit,
    label: String,
    errorText: String?,
    focusManager: FocusManager? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    padding: Dp = 10.dp,
    keyboardActions: KeyboardActions = KeyboardActions { }
) {
    TextField(
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 12.dp, start = padding, end = padding)
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = white,
            textColor = white,
            disabledLabelColor = white,
            focusedLabelColor = white,
            unfocusedLabelColor = white,
            disabledIndicatorColor = white,
            unfocusedIndicatorColor = white,
            focusedIndicatorColor = white,
        ),
        isError = errorText != null,
    )

    Text(
        text = errorText.orEmpty(),
        color = MaterialTheme.colors.error,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(start = 20.dp)
    )
}