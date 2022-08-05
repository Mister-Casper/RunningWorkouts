package com.sgcdeveloper.runwork.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class TextContainer {

    fun getString(context: Context): String {
        return when (this) {
            is StringTextContainer -> text
            is ResContainer -> context.getString(stringResId, *arg)
        }
    }

    @Composable
    fun getString(): String {
        return when (this) {
            is StringTextContainer -> text
            is ResContainer -> stringResource(id = stringResId, *arg)
        }
    }

    class StringTextContainer(val text: String) : TextContainer()
    class ResContainer(@StringRes val stringResId: Int, vararg val arg: Any) : TextContainer()

    companion object {
        fun String.toTextContainer(): TextContainer {
            return StringTextContainer(this)
        }

        fun getTextContainer(@StringRes stringResId: Int, vararg arg: Any): TextContainer {
            return ResContainer(stringResId, *arg)
        }
    }
}