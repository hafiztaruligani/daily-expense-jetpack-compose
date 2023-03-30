package com.dailyexpenses.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dailyexpenses.R

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onClickClose: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onClickClose.invoke() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null
        )
    }
}

@Composable
fun ButtonGradient(
    text: String,
    modifier: Modifier = Modifier,
    onCLick: () -> Unit
) {
    Button(
        onClick = { onCLick.invoke() },
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun RoundedTextField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    textValue: String,
    onChange: ((String) -> Unit),
    hint: String? = null
) {
    val shape = RoundedCornerShape(8.dp)
    TextField(
        modifier = modifier
            .background(Color.White, shape = shape)
            .padding(16.dp),
        value = textValue,
        readOnly = readOnly,
        onValueChange = {
            onChange.invoke(it)
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(text = hint ?: "")
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}

@Preview
@Composable
fun ChipPreview() {
    CustomChip(name = "Chip", onClick = {})
}

@Composable
fun CustomChip(
    name: String,
    modifier: Modifier = Modifier,
    onClick: (isActive: Boolean) -> Unit
) {
    var isActive by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .padding(end = 16.dp)
            .padding(vertical = 4.dp)
            .background(
                color = if (isActive) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondary
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable {
                isActive = !isActive
                onClick.invoke(isActive)
            }
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyMedium, color = Color.White)
    }
}
