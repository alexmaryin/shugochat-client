package ru.alexmaryin.shugojor.shugochat.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.alexmaryin.shugojor.shugochat.ui.theme.ShugochatTheme
import ru.alexmaryin.shugojor.shugochat.ui.theme.onPrimary
import ru.alexmaryin.shugojor.shugochat.ui.theme.primary

@Composable
fun UserMenu(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = modifier
            .width(300.dp)
            .background(primary)
            .border(BorderStroke(width = 2.dp, color = onPrimary))
    ) {
        items.forEach {
            DropdownMenuItem(onClick = { it.click(); expanded = false }) {
                Text(it.text, style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
fun UserMenuPreview() {
    ShugochatTheme {
        Scaffold() {
            Column() {
                UserMenu(
                    listOf(
                        MenuItem("Clear") {},
                        MenuItem("Do something") {},
                        MenuItem("Cocococo") {}
                    )) {}
            }
        }
    }
}

data class MenuItem(
    val text: String,
    val click: () -> Unit
)