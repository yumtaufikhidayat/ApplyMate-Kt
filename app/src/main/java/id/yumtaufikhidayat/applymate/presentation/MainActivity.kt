package id.yumtaufikhidayat.applymate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import id.yumtaufikhidayat.applymate.presentation.navigation.AppNavGraph
import id.yumtaufikhidayat.applymate.presentation.ui.theme.ApplyMateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApplyMateTheme {
                AppNavGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplyMatePreview() {
    ApplyMateTheme {
        AppNavGraph()
    }
}