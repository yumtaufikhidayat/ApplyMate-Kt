package id.yumtaufikhidayat.applymate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.yumtaufikhidayat.applymate.presentation.application.AddEditApplicationScreen
import id.yumtaufikhidayat.applymate.presentation.application.ApplicationDetailScreen
import id.yumtaufikhidayat.applymate.presentation.home.HomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }

        composable(Routes.ADD_APPLICATION) {
            AddEditApplicationScreen(navController = navController)
        }

        composable("${Routes.ADD_APPLICATION}/{appId}") { backstack ->
            val appId = backstack.arguments?.getString("appId")?.toLongOrNull()
            AddEditApplicationScreen(navController = navController, appId = appId)
        }

        composable("${Routes.APPLICATION_DETAIL}/{appId}") { backStack ->
            val appId = backStack.arguments?.getString("appId")?.toLongOrNull()
            appId?.let { ApplicationDetailScreen(navController, it) }
        }
    }
}