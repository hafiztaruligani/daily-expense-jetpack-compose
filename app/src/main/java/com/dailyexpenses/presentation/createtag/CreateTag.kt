
package com.dailyexpenses.presentation.createtag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailyexpenses.presentation.ButtonGradient
import com.dailyexpenses.presentation.CloseButton
import com.dailyexpenses.presentation.RoundedTextField

@Composable
fun CreateTagScreen(
    onClose: () -> Unit
) {
    CreateTagDialog(onClickClose = onClose)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow)
    ) {
        CreateTagDialog {}
    }
}

@Composable
fun CreateTagDialog(
    viewModel: CreateTagViewModel = hiltViewModel(),
    onClickClose: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value.close) {
        onClickClose.invoke()
    }

    Dialog(
        onDismissRequest = {
            onClickClose.invoke()
        },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .aspectRatio(1f)
                .background(Color.White, shape = RoundedCornerShape(24.dp))
                .padding(top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CloseButton(
                modifier = Modifier.align(Alignment.End)
            ) {
                onClickClose.invoke()
            }

            RoundedTextField(
                textValue = uiState.value.name,
                onChange = {
                    viewModel.setTagName(it)
                },
                hint = "Tag Name"
            )

            ButtonGradient(
                text = "Add",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                viewModel.addTag()
            }
        }
    }
}
