package com.dailyexpenses.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dailyexpenses.presentation.createtransaction.CREATE_EXPENSES_SCREEN_ROUTE
import com.dailyexpenses.presentation.createtransaction.CreateExpensesScreen
import com.dailyexpenses.presentation.home.HOME_SCREEN_ROUTE
import com.dailyexpenses.presentation.home.HomeScreen
import com.dailyexpenses.presentation.ui.theme.DailyExpensesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyExpensesTheme {
                SetupNavigation()
            }
        }
    }

    @Composable
    private fun SetupNavigation() {
        navController = rememberNavController()
        NavHost(
            navController = (navController as NavHostController),
            startDestination = HOME_SCREEN_ROUTE
        ) {
            composable(route = HOME_SCREEN_ROUTE) {
                HomeScreen { navController.navigate(CREATE_EXPENSES_SCREEN_ROUTE) }
            }

            composable(route = CREATE_EXPENSES_SCREEN_ROUTE) {
                CreateExpensesScreen({ navController.popBackStack() })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    DailyExpensesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(android.graphics.Color.parseColor("#edf1f7"))
        ) {
            HomeScreen {}
        }
    }
}
