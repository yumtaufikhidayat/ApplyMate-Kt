package id.yumtaufikhidayat.applymate.presentation.application

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import id.yumtaufikhidayat.applymate.presentation.components.FormDateField
import id.yumtaufikhidayat.applymate.presentation.components.FormMultiLineTextField
import id.yumtaufikhidayat.applymate.presentation.components.FormTextField
import id.yumtaufikhidayat.applymate.presentation.components.FormTextFieldLink
import id.yumtaufikhidayat.applymate.presentation.navigation.Routes
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditApplicationScreen(
    navController: NavController,
    viewModel: AddEditApplicationViewModel = hiltViewModel(),
    appId: Long? = null
) {
    val state by viewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val allStatus = ApplicationStatus.entries.toTypedArray()
    var selectedStatus by remember { mutableStateOf(ApplicationStatus.APPLIED) }
    val snackbarHostState = remember { SnackbarHostState() }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.interviewDate
                ?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
                ?: Instant.now().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val localDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        viewModel.updateInterviewDate(localDate)
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    LaunchedEffect(appId) {
        viewModel.loadApplication(appId)
    }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.consumeSnackbar()
        }
    }

    LaunchedEffect(state.isEditMode) {
        if (state.isEditMode) {
            selectedStatus = try {
                ApplicationStatus.valueOf(state.status ?: ApplicationStatus.APPLIED.name)
            } catch (_: Exception) {
                ApplicationStatus.APPLIED
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEditMode) "Edit Lamaran" else "Tambah Lamaran") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveApplication(selectedStatus = selectedStatus) { id ->
                        if (state.isEditMode) {
                            navController.navigate("${Routes.APPLICATION_DETAIL}/$id") {
                                popUpTo("${Routes.APPLICATION_DETAIL}/$id") { inclusive = true }
                            }
                        } else {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.HOME) { inclusive = true }
                            }
                        }
                    }
                }
            ) {
                Icon(Icons.Filled.Check, contentDescription = "Save")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FormTextField(
                label = "Posisi Pekerjaan",
                value = state.position,
                error = state.positionError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { viewModel.updateField("position", it) }
            )
            FormTextField(
                label = "Perusahaan",
                value = state.company,
                error = state.companyError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { viewModel.updateField("company", it) }
            )
            FormMultiLineTextField(
                label = "Tentang Perusahaan (Opsional)",
                value = state.companyAbout,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Default
                ),
                onValueChange = { viewModel.updateField("companyAbout", it) }
            )
            FormTextField(
                label = "Kota (Opsional)",
                value = state.city,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { viewModel.updateField("city", it) }
            )
            FormTextFieldLink(
                label = "Tautan Lamaran",
                value = state.jobLink,
                error = state.jobLinkError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { viewModel.updateField("jobLink", it) }
            )
            FormMultiLineTextField(
                label = "Deskripsi Pekerjaan (Opsional)",
                value = state.jobDesc,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Default
                ),
                onValueChange = { viewModel.updateField("jobDesc", it) }
            )
            FormMultiLineTextField(
                label = "Persyaratan Pekerjaan (Opsional)",
                value = state.jobRequirement,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Default
                ),
                onValueChange = { viewModel.updateField("jobRequirement", it) }
            )
            FormTextField(
                label = "Gaji (Opsional)",
                value = state.salary,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { viewModel.updateField("salary", it) }
            )
            FormMultiLineTextField(
                label = "Catatan (Opsional)",
                value = state.note,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
//                placeholder = "Contoh: alamat kantor, link wawancara, jadwal wawancara, dsb",
                onValueChange = { viewModel.updateField("note", it) }
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    value = selectedStatus.name,
                    onValueChange = {},
                    label = { Text("Status Lamaran") },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    shape = RoundedCornerShape(8.dp),
                    onDismissRequest = { expanded = false },
                ) {
                    allStatus.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.name) },
                            onClick = {
                                selectedStatus = status
                                expanded = false
                            }
                        )
                    }
                }
            }
            AnimatedVisibility(visible = selectedStatus == ApplicationStatus.INTERVIEW) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    FormDateField(
                        label = "Tanggal Wawancara",
                        valueText = state.interviewDate
                            ?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                            .orEmpty(),
                        error = state.interviewDateError,
                        onClick = { showDatePicker = true }
                    )

                    FormTextField(
                        label = "Tautan Wawancara",
                        value = state.interviewLink,
                        onValueChange = { viewModel.updateField("interviewLink", it) },
                        error = state.interviewLinkError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
                    )
                }
            }
        }
    }
}