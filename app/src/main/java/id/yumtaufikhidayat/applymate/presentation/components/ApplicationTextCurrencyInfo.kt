package id.yumtaufikhidayat.applymate.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ApplicationTextCurrencyInfo(
    modifier: Modifier = Modifier,
    title: String = "",
    salary: String = "",
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ApplicationTitle(title = title)
        ApplicationCurrencyDescription(
            salary = salary,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun ApplicationCurrencyDescription(
    salary: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = if (salary.isEmpty()) "-" else "Rp $salary",
            color = color,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
        )
    }
}