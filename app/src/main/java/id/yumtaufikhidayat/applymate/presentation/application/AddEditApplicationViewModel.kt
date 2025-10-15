package id.yumtaufikhidayat.applymate.presentation.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import id.yumtaufikhidayat.applymate.domain.usecase.application.ApplicationUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddEditApplicationViewModel @Inject constructor(private val useCases: ApplicationUseCases) : ViewModel() {

    private val _state = MutableStateFlow(AddEditApplicationState())
    val state: StateFlow<AddEditApplicationState> = _state.asStateFlow()

    fun loadApplication(id: Long?) {
        if (id == null) return
        viewModelScope.launch {
            useCases.getApplicationById(id).collect { application ->
                application?.let {
                    _state.value = state.value.copy(
                        id = it.id,
                        position = it.position,
                        company = it.company,
                        city = it.city.orEmpty(),
                        note = it.note.orEmpty(),
                        status = it.status.name,
                        isEditMode = true
                    )
                }
            }
        }
    }

    fun updateField(field: String, value: String) {
        _state.value = when (field) {
            "position" -> _state.value.copy(position = value)
            "company" -> _state.value.copy(company = value)
            "city" -> _state.value.copy(city = value)
            "note" -> _state.value.copy(note = value)
            else -> _state.value
        }
    }

    fun saveApplication(
        selectedStatus: ApplicationStatus,
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val valueState = _state.value
            if (valueState.position.isBlank() || valueState.company.isBlank()) {
                _state.value = valueState.copy(snackbarMessage = "Posisi dan Perusahaan wajib diisi")
                return@launch
            }

            val app = Application(
                id = valueState.id,
                position = valueState.position,
                company = valueState.company,
                city = valueState.city.ifBlank { null },
                salaryMin = null,
                salaryMax = null,
                jobLink = null,
                jobDesc = null,
                note = valueState.note.ifBlank { null },
                status = selectedStatus,
                appliedAt = Instant.now(),
                updatedAt = Instant.now()
            )

            if (valueState.isEditMode) {
                val oldStatus = try {
                    ApplicationStatus.valueOf(valueState.status ?: "APPLIED")
                } catch (_: Exception) {
                    ApplicationStatus.APPLIED
                }

                if (oldStatus != selectedStatus) {
                    useCases.updateApplicationStatus(app.id, oldStatus, selectedStatus, "Ubah status manual")
                }

                useCases.updateApplication(app)
                _state.value = valueState.copy(snackbarMessage = "Lamaran berhasil diperbarui")
            } else {
                useCases.addApplication(app)
                _state.value = valueState.copy(snackbarMessage = "Lamaran berhasil ditambahkan")
            }

            onSaved()
        }
    }

    fun consumeSnackbar() {
        _state.value = state.value.copy(snackbarMessage = null)
    }
}