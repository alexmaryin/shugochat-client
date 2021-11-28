package ru.alexmaryin.shugojor.shugochat.ui.components

import android.content.Context
import android.util.Log
import ru.alexmaryin.shugojor.shugochat.R
import ru.alexmaryin.shugojor.shugochat.data.result.ErrorType
import ru.alexmaryin.shugojor.shugochat.data.result.Result

fun errorLocalizedString(error: Result.Error, context: Context): String =
    when (error.type) {
        ErrorType.NO_CONNECTION -> context.getString(R.string.error_no_connection)
        ErrorType.UNAUTHORIZED -> context.getString(R.string.error_unauthorized)
        ErrorType.OTHER_CLIENT_ERROR -> context.getString(R.string.error_client_side)
        ErrorType.SERVER_UNAVAILABLE -> context.getString(R.string.error_server_unavailable)
        ErrorType.OTHER_SERVER_ERROR -> context.getString(R.string.error_server_side)
        ErrorType.BAD_USERNAME -> context.getString(R.string.failed_register_text)
        ErrorType.UNKNOWN -> {
            Log.d(context.applicationInfo.name, "Unknown error: ${error.message}")
            context.getString(R.string.error_unknown)
        }
    }