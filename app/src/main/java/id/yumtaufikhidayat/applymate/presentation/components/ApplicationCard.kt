package id.yumtaufikhidayat.applymate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus

@Composable
fun ApplicationCard(
    application: Application,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = application.position,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = application.company,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${application.status}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = when (application.status) {
                        ApplicationStatus.APPLIED -> Color.Cyan
                        ApplicationStatus.SCREENING -> Color.Cyan
                        ApplicationStatus.INTERVIEW -> Color.Cyan
                        ApplicationStatus.ASSIGNMENT -> Color.Magenta
                        ApplicationStatus.OFFER -> Color.Yellow
                        ApplicationStatus.HIRED -> Color.Green
                        ApplicationStatus.REJECTED -> Color.Red
                    }
                )
            )
        }
    }
}