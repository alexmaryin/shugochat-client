package ru.alexmaryin.shugojor.shugochat.data.result

sealed class Result<out T> {
    data class Success<out R>(val value: R) : Result<R>()
    data class Error(val type: ErrorType, val message: String? = null) : Result<Nothing>()
}

enum class ErrorType {
    NO_CONNECTION,
    UNAUTHORIZED,
    BAD_USERNAME,
    OTHER_CLIENT_ERROR,
    SERVER_UNAVAILABLE,
    OTHER_SERVER_ERROR,
    UNKNOWN
}

inline fun <reified T> Result<T>.forSuccess(callback: (value: T) -> Unit) {
    if (this is Result.Success) callback(value)
}

inline fun <reified T> Result<T>.forError(callback: (type: ErrorType, message: String?) -> Unit) {
    if (this is Result.Error) callback(type, message)
}

inline fun <reified T> Result<T>.forError(callback: (Result.Error) -> Unit) {
    if (this is Result.Error) callback(this)
}

inline fun <T> Result<T>.withDefault(value: () -> T): Result.Success<T> {
    return when (this) {
        is Result.Success -> this
        is Result.Error -> Result.Success(value())
    }
}
