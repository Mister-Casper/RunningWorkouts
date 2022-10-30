package com.sgcdeveloper.runwork.presentation.screen.onboarding.registrationEmail

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.sgcdeveloper.chips.ui.ChipDefaults
import com.sgcdeveloper.chips.ui.TextChipsRow
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.ActionButtonBlock
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.component.InputField
import com.sgcdeveloper.runwork.presentation.component.PasswordInputField
import com.sgcdeveloper.runwork.presentation.theme.dark_gray
import com.sgcdeveloper.runwork.presentation.theme.light_blue
import com.sgcdeveloper.runwork.presentation.theme.white
import com.sgcdeveloper.runwork.presentation.util.navigableHiltViewModel

private val PickVisualImageOnlyRequest = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
fun RegistrationEmailScreen(registrationEmailViewModel: RegistrationEmailViewModel = navigableHiltViewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val screenState = registrationEmailViewModel.registrationEmailScreenState.collectAsState().value

    val getPicLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            registrationEmailViewModel.onEvent(RegistrationEvent.UpdateProfilePic(it))
        }
    }

    val readImagesPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getPicLauncher.launch(PickVisualImageOnlyRequest)
        } else {
            registrationEmailViewModel.onEvent(RegistrationEvent.PermissionFailed)
        }
    }

    FullscreenImage(
        gradientColors = listOf(
            dark_gray.copy(0.6f),
            dark_gray.copy(0.5f),
            dark_gray.copy(0.4f),
        )
    )

    var size by remember { mutableStateOf(IntSize.Zero) }

    Column(Modifier.padding(start = 6.dp, end = 6.dp, top = 16.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(dark_gray)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                                getPicLauncher.launch(PickVisualImageOnlyRequest)
                            }
                            else -> {
                                readImagesPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                    .onSizeChanged {
                        size = it
                    }
            ) {
                if (screenState.userPicUri == Uri.EMPTY) {
                    Image(
                        painter = painterResource(R.drawable.ic_photo),
                        contentDescription = "user icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(model = screenState.userPicUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .then(
                                with(LocalDensity.current) {
                                    Modifier.size(
                                        width = size.width.toDp(),
                                        height = size.height.toDp(),
                                    )
                                }
                            )
                            .clip(CircleShape)
                    )
                }
            }
            Column(Modifier.weight(2f)) {
                InputField(
                    label = stringResource(id = R.string.onboarding__first_name_registration),
                    value = screenState.firstName,
                    onValueChange = { newFirstName ->
                        registrationEmailViewModel.onEvent(
                            RegistrationEvent.UpdateFirstName(
                                newFirstName
                            )
                        )
                    },
                    errorText = screenState.firstNameError?.getString(),
                    focusManager = focusManager,
                )
                InputField(
                    label = stringResource(id = R.string.onboarding__second_name_registration),
                    value = screenState.lastName,
                    onValueChange = { newLastName ->
                        registrationEmailViewModel.onEvent(
                            RegistrationEvent.UpdateLastName(
                                newLastName
                            )
                        )
                    },
                    errorText = screenState.lastNameError?.getString(),
                    focusManager = focusManager,
                )
            }
        }
        TextChipsRow(
            textChips = screenState.genderChips,
            colors = ChipDefaults.chipColors(
                backgroundColor = light_blue.copy(alpha = 0.2f),
                contentColor = light_blue,
                disabledBackgroundColor = white.copy(alpha = 0.2f),
                disabledContentColor = white,
                borderColor = light_blue,
                disabledBorderColor = white
            ),
            onClick = { registrationEmailViewModel.onEvent(RegistrationEvent.UpdateGender(it)) },
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            chipPadding = PaddingValues(all = 8.dp)
        )
        InputField(
            label = stringResource(id = R.string.onboarding__email_registration),
            value = screenState.email,
            onValueChange = { newEmail ->
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdateEmail(newEmail))
            },
            errorText = screenState.emailError?.getString(),
            focusManager = focusManager,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        PasswordInputField(
            label = stringResource(id = R.string.onboarding__password_registration),
            value = screenState.password,
            onValueChange = { newPassword ->
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdatePassword(newPassword))
            },
            errorText = screenState.passwordError?.getString(),
            focusManager = focusManager,
            passwordVisibility = screenState.isPasswordVisible,
            changePasswordVisibility = {
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdatePasswordVisibility(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        PasswordInputField(
            label = stringResource(id = R.string.onboarding__confirm_password_registration),
            value = screenState.confirmPassword,
            onValueChange = { newPassword ->
                registrationEmailViewModel.onEvent(
                    RegistrationEvent.UpdateConfirmPassword(
                        newPassword
                    )
                )
            },
            errorText = screenState.confirmPasswordError?.getString(),
            focusManager = focusManager,
            passwordVisibility = screenState.isPasswordVisible,
            changePasswordVisibility = {
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdatePasswordVisibility(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.weight(1f))
        ActionButtonBlock(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 16.dp),
            actionTest = stringResource(id = R.string.onboarding__registration_action_button)
        ) {
            registrationEmailViewModel.onEvent(RegistrationEvent.Next)
        }
    }
}