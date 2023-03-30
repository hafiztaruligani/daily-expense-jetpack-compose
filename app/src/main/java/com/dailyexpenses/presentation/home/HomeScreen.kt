package com.dailyexpenses.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dailyexpenses.R
import com.dailyexpenses.presentation.createtag.CreateTagScreen
import com.dailyexpenses.presentation.ui.theme.angledGradient

const val HOME_SCREEN_ROUTE = "HOME_SCREEN_ROUTE"

@Composable
fun HomeScreen(
    onClickAdd: () -> Unit
) {
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
            MainCard()
            Transaction()
            TransactionList()
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

@Composable
fun MainCard() {
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
            text = "Total Expanses",
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
                text = "10.000",
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
fun TransactionList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(10) {
            item {
                TransactionItem()
            }
        }
    }
}

@Composable
fun TransactionItem() {
    Column {
        Row(
            modifier = Modifier
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
                    text = "Food",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "-Rp. 10.000",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Today",
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
