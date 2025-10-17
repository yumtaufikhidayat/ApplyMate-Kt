package id.yumtaufikhidayat.applymate.presentation.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import id.yumtaufikhidayat.applymate.core.helper.ApplicationTextType
import id.yumtaufikhidayat.applymate.core.ext.toReadableString
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import id.yumtaufikhidayat.applymate.presentation.components.ApplicationTextInfo
import id.yumtaufikhidayat.applymate.presentation.navigation.Routes
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationDetailScreen(
    navController: NavController,
    appId: Long,
    viewModel: ApplicationDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val application = state.application
    val historyList = state.history

    LaunchedEffect(appId) {
        if (appId > 0) viewModel.loadApplication(appId)
    }

    val app = state.application ?: return Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Lamaran?") },
            text = { Text("Data ini akan dihapus secara permanen?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.delete {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Lamaran") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("${Routes.ADD_APPLICATION}/${app.id}")
                    }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Hapus")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ApplicationTextInfo(
                title = "Posisi Pekerjaan",
                description = app.position,
            )

            ApplicationTextInfo(
                title = "Perusahaan",
                description = app.company
            )

            ApplicationTextInfo(
                title = "Kota",
                description = app.city.ifBlank { "-" }
            )

            ApplicationTextInfo(
                title = "Tautan Lamaran",
                description = app.jobLink,
                type = ApplicationTextType.LINK
            )

            ApplicationTextInfo(
                title = "Deskripsi Pekerjaan",
                description = app.jobDesc.ifBlank { "-" }
            )

            ApplicationTextInfo(
                title = "Persyaratan Pekerjaan",
                description = app.jobRequirement.ifBlank { "-" }
            )

            ApplicationTextInfo(
                title = "Gaji",
                description = app.salary.ifBlank { "-" }
            )

            ApplicationTextInfo(
                title = "Catatan",
                description = app.note.ifBlank { "-" }
            )

            ApplicationTextInfo(
                title = "Status Lamaran",
                description = app.status.name
            )

            if (app.status == ApplicationStatus.INTERVIEW) {
                val localeId = Locale("id", "ID")
                val formattedInterviewDateTime = app.interviewDateTime?.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy 'pukul' HH:mm", localeId)) ?: "-"

                ApplicationTextInfo(
                    title = "Jadwal Wawancara",
                    description = formattedInterviewDateTime
                )

                if (!app.interviewLink.isNullOrBlank()) {
                    ApplicationTextInfo(
                        title = "Tautan Wawancara",
                        description = app.interviewLink,
                        type = ApplicationTextType.LINK
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            Text("Riwayat Status Lamaran", style = MaterialTheme.typography.titleMedium)
            if (application != null) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    if (historyList.isNotEmpty()) {
                        historyList.forEach { history ->
                            Text(
                                text = "• Perubahan status ${history.fromStatus ?: "-"} menjadi ${history.toStatus} pada ${history.changedAt.toReadableString()}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    val appliedAt = application.appliedAt.toReadableString()
                    Text(
                        text = "• Lamaran dengan status ${application.status} dikirim pada $appliedAt",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                Text(
                    text = "Data lamaran tidak ditemukan.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}