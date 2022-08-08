package com.sgcdeveloper.runwork.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    keyboardActions: KeyboardActions = KeyboardActions { focusManager?.moveFocus(FocusDirection.Down) },
    modifier: Modifier = Modifier
) {
    TextField(
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
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

@Composable
fun ColumnScope.PasswordInputField(
    value: String,
    onValueChange: (it: String) -> Unit,
    label: String,
    errorText: String?,
    focusManager: FocusManager,
    passwordVisibility: Boolean,
    changePasswordVisibility: (isVisible: Boolean) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions { },
    modifier: Modifier = Modifier,
    trillingIconColor: Color = white
) {
    InputField(
        value, onValueChange, label, errorText, focusManager,
        trailingIcon = {
            val image = if (passwordVisibility)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            IconButton(onClick = {
                changePasswordVisibility(!passwordVisibility)
            }) {
                Icon(imageVector = image, "", tint = trillingIconColor)
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
    )
}
