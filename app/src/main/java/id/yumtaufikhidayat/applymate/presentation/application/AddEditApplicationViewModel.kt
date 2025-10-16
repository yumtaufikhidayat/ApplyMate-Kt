package id.yumtaufikhidayat.applymate.presentation.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yumtaufikhidayat.applymate.core.ext.autoNormalizeLink
import id.yumtaufikhidayat.applymate.core.ext.isValidDomainWithoutScheme
import id.yumtaufikhidayat.applymate.core.ext.isValidJobLink
import id.yumtaufikhidayat.applymate.core.ext.toEnum
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.model.ApplicationStatus
import id.yumtaufikhidayat.applymate.domain.usecase.application.ApplicationUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddEditApplicationViewModel @Inject constructor(private val useCases: ApplicationUseCases) : ViewModel() {

    private val _state = MutableStateFlow(AddEditApplicationState())
    val state: StateFlow<AddEditApplicationState> = _state.asStateFlow()

    private var jobLinkNormalizeJob: Job? = null

    fun loadApplication(id: Long?) {
        if (id == null) return
        viewModelScope.launch {
            useCases.getApplicationById(id).collect { application ->
                application?.let {
                    _state.value = state.value.copy(
                        id = it.id,
                        position = it.position,
                        company = it.company,
                        companyAbout = it.companyAbout,
                        city = it.city,
                        salary = it.salary,
                        jobLink = it.jobLink,
                        jobDesc = it.jobDesc,
                        jobRequirement = it.jobRequirement,
                        note = it.note,
                        status = it.status.name,
                        isEditMode = true
                    )
                }
            }
        }
    }

    fun updateField(field: String, value: String) {
        when (field) {
            "position" -> _state.update { it.copy(position = value, positionError = null) }
            "company" -> _state.update { it.copy(company = value, companyError = null) }
            "companyAbout" -> _state.update { it.copy(companyAbout = value) }
            "city" -> _state.update { it.copy(city = value) }
            "jobLink" -> {
                _state.update { it.copy(jobLink = value, jobLinkError = null) }

                jobLinkNormalizeJob?.cancel()
                jobLinkNormalizeJob = viewModelScope.launch {
                    delay(500)
                    val currentLink = _state.value.jobLink
                    if (currentLink.isValidDomainWithoutScheme() && !currentLink.startsWith("https://")) {
                        val normalized = currentLink.autoNormalizeLink()
                        _state.update { it.copy(jobLink = normalized) }
                    }
                }
            }
            "jobDesc" -> _state.update { it.copy(jobDesc = value) }
            "jobRequirement" -> _state.update { it.copy(jobRequirement = value) }
            "salary" -> _state.update { it.copy(salary = value) }
            "note" -> _state.update { it.copy(note = value) }
        }
    }

    fun saveApplication(
        selectedStatus: ApplicationStatus,
        onSaved: (Long) -> Unit
    ) {
        viewModelScope.launch {
            var hasError = false
            val currentState = _state.value

            _state.update {
                currentState.copy(
                    positionError = null,
                    companyError = null,
                    jobLinkError = null
                )
            }

            var positionError: String? = null
            var companyError: String? = null
            var jobLinkError: String? = null

            if (currentState.position.isBlank()) {
                hasError = true
                positionError = "Posisi pekerjaan wajib diisi"
            }

            if (currentState.company.isBlank()) {
                hasError = true
                companyError = "Nama perusahaan wajib diisi"
            }

            val jobLink = currentState.jobLink.trim()

            if (jobLink.isBlank()) {
                hasError = true
                jobLinkError = "Tautan lamaran wajib diisi"
            } else if (!jobLink.isValidJobLink()) {
                hasError = true
                jobLinkError = "Format tautan tidak valid"
            }

            _state.value = currentState.copy(
                positionError = positionError,
                companyError = companyError,
                jobLinkError = jobLinkError
            )

            if (hasError) return@launch

            val normalizedJobLink = currentState.jobLink.autoNormalizeLink()
            val app = Application(
                id = currentState.id,
                position = currentState.position,
                company = currentState.company,
                companyAbout = currentState.companyAbout,
                city = currentState.city,
                salary = currentState.salary,
                jobLink = normalizedJobLink,
                jobDesc = currentState.jobDesc,
                jobRequirement = currentState.jobRequirement,
                note = currentState.note,
                status = selectedStatus,
                appliedAt = Instant.now(),
                updatedAt = Instant.now()
            )

            if (currentState.isEditMode) {
                val oldStatus = currentState.status?.toEnum<ApplicationStatus>()
                if (oldStatus != selectedStatus) {
                    useCases.updateApplicationStatus(
                        appId = currentState.id,
                        from = oldStatus,
                        to = selectedStatus,
                        note = "Perubahan status lamaran"
                    )
                }
                useCases.updateApplication(app)
                _state.value = currentState.copy(snackbarMessage = "Lamaran berhasil diperbarui")
                onSaved(app.id)
            } else {
                useCases.addApplication(app)
                _state.value = currentState.copy(snackbarMessage = "Lamaran berhasil ditambahkan")
                onSaved(app.id)
            }
        }
    }

    fun consumeSnackbar() {
        _state.value = state.value.copy(snackbarMessage = null)
    }
}