package com.dailyexpenses.presentation.createtransaction

import android.util.Log
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dailyexpenses.presentation.ButtonGradient
import com.dailyexpenses.presentation.CloseButton
import com.dailyexpenses.presentation.CustomChip
import com.dailyexpenses.presentation.RoundedTextField
import com.dailyexpenses.presentation.ui.theme.angledGradient
import java.util.*

const val CREATE_EXPENSES_SCREEN_ROUTE = "CREATE_EXPENSES_SCREEN_ROUTE"

@Composable
fun CreateExpensesScreen(
    onClickClose: () -> Unit,
    viewModel: CreateExpansesViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.value.needDatePicker) DatePicker(uiState.value, viewModel)
    if (uiState.value.error.isNotBlank())
        Toast.makeText(LocalContext.current, uiState.value.error, Toast.LENGTH_LONG).show()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(android.graphics.Color.parseColor("#edf1f7"))
            )
            .padding(24.dp)
            .verticalScroll(scrollState),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(android.graphics.Color.parseColor("#edf1f7"))
                )
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            CloseButton(
                modifier = Modifier.align(Alignment.End),
                onClickClose
            )

            Text(
                text = "Add Expenses",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            AmountView(uiState, viewModel)

            TagList(uiState, viewModel)

            RoundedTextField(
                modifier = Modifier.fillMaxWidth(),
                textValue = uiState.value.title,
                onChange = {
                    viewModel.setTitle(it)
                },
                hint = "title"
            )

            DateView(viewModel, uiState)

            RoundedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(100.dp),
                textValue = uiState.value.note,
                onChange = {
                    viewModel.setNote(it)
                },
                hint = "note"
            )
        }

        ButtonGradient(
            text = "SAVE",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            viewModel.save(onClickClose)
        }
    }
}

@Composable
private fun DateView(
    viewModel: CreateExpansesViewModel,
    uiState: State<CreateExpansesUiState>
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            viewModel.showDatePicker()
                        }
                    }
                }
            },
        value = (
                uiState.value.day.toString().plus("-").plus(
                    uiState.value.month.toString().plus("-").plus(
                        uiState.value.year.toString()
                    )
                )
                ),
        readOnly = true,
        onValueChange = {
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(text = "date")
        }
    )
}

@Composable
@OptIn(ExperimentalTextApi::class)
private fun AmountView(
    uiState: State<CreateExpansesUiState>,
    viewModel: CreateExpansesViewModel
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .padding(16.dp),
        value = uiState.value.amount,
        readOnly = false,
        onValueChange = {
            viewModel.setAmount(it)
        },
        textStyle = TextStyle(
            fontSize = 32.sp,
            brush = angledGradient(),
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    uiState: State<CreateExpansesUiState>,
    viewModel: CreateExpansesViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
    ) {
        Text(text = "Tag: ", style = MaterialTheme.typography.labelMedium)
        FlowRow(
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            uiState.value.tagList.forEach { tag ->
                CustomChip(name = tag.name, onClick = { viewModel.setSelectedTag(tag, it) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    CreateExpensesScreen(onClickClose = {})
}

@Suppress("NAME_SHADOWING")
@Composable
fun DatePicker(uiState: CreateExpansesUiState, viewModel: CreateExpansesViewModel) {

    val mDatePickerDialog = android.app.DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            Log.d("DatePicker", "DatePicker: $mDayOfMonth/${mMonth + 1}/$mYear")
            viewModel.setDate(
                mDayOfMonth,
                mMonth + 1,
                mYear
            )
        }, uiState.year, uiState.month - 1, uiState.day
    )

    mDatePickerDialog.show()

    AndroidView(
        { CalendarView(it) },
        modifier = Modifier.wrapContentWidth(),
        update = { views ->
            views.setOnDateChangeListener { calendarView, i, i2, i3 ->
                Log.d("DatePicker", "DatePicker: $i $i2 $i3")
            }
        }
    )
}
