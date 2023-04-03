package com.dailyexpenses.presentation.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailyexpenses.R
import com.dailyexpenses.domain.model.Expenses
import com.dailyexpenses.domain.model.MonthWithYear
import com.dailyexpenses.formatCurrencies
import com.dailyexpenses.presentation.createtag.CreateTagScreen
import com.dailyexpenses.presentation.ui.theme.angledGradient

const val HOME_SCREEN_ROUTE = "HOME_SCREEN_ROUTE"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel : HomeViewModel = hiltViewModel(),
    onClickAdd: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    var dialogState by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (dialogState) {
            CreateTagScreen {
                dialogState = false
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 40.dp),
                onClick = {
                    dialogState = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_setting),
                    contentDescription = null
                )
            }
            MainCard(viewModel)
            Transaction()
            TransactionList(uiState)
        }
        BottomNav(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClickAdd
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    HomeScreen {}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainCard(viewModel: HomeViewModel){
    val uiState = viewModel.uiState.collectAsState()
    val items = uiState.value.monthList
    val pagerState = rememberPagerState()
    LaunchedEffect(pagerState){
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.setSelectedMonth(page)
        }
    }
    HorizontalPager(
        pageCount = items.size,
        pageSpacing = 24.dp,
        state = pagerState
    ) { page ->
        val item = items[page]
        MainCardItem(item = item, uiState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainCardItem(item: MonthWithYear, uiState: State<HomeUiState>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .background(
                brush = angledGradient(),
                shape = RoundedCornerShape(32.dp)
            )
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total Expanses ${item.month}/${item.year}",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
        Row {
            Text(
                text = "Rp. ",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = uiState.value.getTotal().toString().formatCurrencies(),
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun Transaction() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Transaction", style = MaterialTheme.typography.titleLarge)
            Text(text = "View All", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun TransactionList(uiState: State<HomeUiState>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.value.expensesList?.expenses?.forEachIndexed { _, expenses ->
            item {
                TransactionItem(expenses = expenses)
            }
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionItem(modifier: Modifier = Modifier, expenses: Expenses) {

    Column(
        modifier = modifier.combinedClickable(
            onClick = {},
            onLongClick = { Log.d("javaClass.simpleName", "MainCardItem: ${expenses.id}") }
            // TODO: show editor pop up
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_launcher_foreground
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = expenses.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "-Rp. ${expenses.amount.formatCurrencies()}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = expenses.timeTitle,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun BottomNav(
    modifier: Modifier,
    onClickAdd: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.075f)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = { onClickAdd.invoke() },
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .align(Alignment.TopCenter),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            brush = angledGradient(),
                            shape = CircleShape
                        )
                        .aspectRatio(1f),
                    painter = painterResource(
                        id = R.drawable.ic_add
                    ),
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    }
}
