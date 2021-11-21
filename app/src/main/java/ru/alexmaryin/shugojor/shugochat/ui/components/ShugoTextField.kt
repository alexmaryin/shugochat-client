package ru.alexmaryin.shugojor.shugochat.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.alexmaryin.shugojor.shugochat.ui.theme.activeSurface
import ru.alexmaryin.shugojor.shugochat.ui.theme.surface

@Composable
fun ShugoTextField(
    value: String,
    hint: String? = null,
    isPassword: Boolean = false,
    isMandatory: Boolean = false,
    onChange: (String) -> Unit,
) {
    var isPassVisible by remember { mutableStateOf(false) }
    var isActive by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onChange,
        placeholder = { hint?.let { Text(text = it, style = MaterialTheme.typography.body2) } },
        textStyle = MaterialTheme.typography.body1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .onFocusEvent {
                isActive = it.isFocused
            },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isActive) activeSurface else surface,
            focusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        visualTransformation = if (isPassword && isPassVisible.not()) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            when {
                isMandatory && value.isBlank() -> Icon(imageVector = Icons.Default.Warning, contentDescription = "Mandatory field")
                isPassword -> IconButton(onClick = { isPassVisible = !isPassVisible }) {
                    val icon =
                        if (isPassVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    Icon(
                        imageVector = icon,
                        contentDescription = if (isPassVisible) "hide password" else "show password",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
    )
}