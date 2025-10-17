package id.yumtaufikhidayat.applymate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.yumtaufikhidayat.applymate.core.ext.toReadableString
import id.yumtaufikhidayat.applymate.data.local.entity.StatusHistoryEntity
import id.yumtaufikhidayat.applymate.domain.model.Application

@Composable
fun ApplicationTimeline(
    historyList: List<StatusHistoryEntity?>,
    application: Application,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(top = 8.dp)
    ) {
        // gabungkan semua history + item terakhir (status awal dikirim)
        val itemList = buildList {
            if (historyList.isNotEmpty()) addAll(historyList)
            add(null)
        }

        itemList.forEachIndexed { index, history ->
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // === Timeline Dot + Line ===
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    // Titik bulat
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    )

                    // Garis penghubung antar item
                    if (index != itemList.lastIndex) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(32.dp)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                    }
                }

                // === Teks Keterangan ===
                Column(modifier = Modifier.weight(1f)) {
                    if (history != null) {
                        Text(
                            text = "Perubahan status ${history.fromStatus ?: "-"} menjadi ${history.toStatus}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = "pada ${history.changedAt.toReadableString()}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    } else {
                        val appliedAt = application.appliedAt.toReadableString()
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Lamaran dengan status ${application.initialStatus}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = "dikirim pada hari $appliedAt",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
