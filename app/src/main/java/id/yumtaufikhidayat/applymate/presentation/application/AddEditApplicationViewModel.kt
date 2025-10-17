package id.yumtaufikhidayat.applymate.presentation.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yumtaufikhidayat.applymate.core.ext.autoNormalizeLink
import id.yumtaufikhidayat.applymate.core.ext.isValidDomainWithoutScheme
import id.yumtaufikhidayat.applymate.core.ext.isValidJobLink
import id.yumtaufikhidayat.applymate.core.ext.normalizeLinkIfNeeded
import id.yumtaufikhidayat.applymate.core.ext.toCurrencyFormat
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddEditApplicationViewModel @Inject constructor(
    private val useCases: ApplicationUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(AddEditApplicationState())
    val state: StateFlow<AddEditApplicationState> = _state.asStateFlow()

    private var jobLinkNormalizeJob: Job? = null
    private var interviewLinkNormalizeJob: Job? = null
    private var salaryNormalizeJob: Job? = null

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
                        isEditMode = true,
                        interviewDateTime = it.interviewDateTime,
                        interviewLink = it.interviewLink.orEmpty()
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
                jobLinkNormalizeJob = jobLinkNormalizeJob.normalizeLinkWithDebounce("jobLink", value)
            }
            "jobDesc" -> _state.update { it.copy(jobDesc = value) }
            "jobRequirement" -> _state.update { it.copy(jobRequirement = value) }
            "salary" -> {
                _state.update { it.copy(salary = value.filter { char -> char.isDigit() }) }
                salaryNormalizeJob = salaryNormalizeJob.normalizeSalaryWithDebounce(value)
            }

            "note" -> _state.update { it.copy(note = value) }
            "interviewLink" -> {
                _state.update { it.copy(interviewLink = value, interviewLinkError = null) }
                interviewLinkNormalizeJob = interviewLinkNormalizeJob.normalizeLinkWithDebounce("interviewLink", value)
            }
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

            if (selectedStatus == ApplicationStatus.INTERVIEW) {
                if (currentState.interviewDateTime == null) {
                    hasError = true
                    _state.update { it.copy(interviewDateTimeError = "Jadwal (tanggal & jam) wawancara wajib diisi") }
                }
                if (currentState.interviewLink.isBlank()) {
                    hasError = true
                    _state.update { it.copy(interviewLinkError = "Tautan wawancara wajib diisi") }
                }
            }

            if (hasError) return@launch

            val normalizedJobLink = currentState.jobLink.normalizeLinkIfNeeded()
            val normalizedInterviewLink = currentState.interviewLink.normalizeLinkIfNeeded()

            if (currentState.isEditMode) {
                val oldApplication = useCases.getApplicationByIdSync(currentState.id)
                val oldStatus = currentState.status?.toEnum<ApplicationStatus>()

                val updateApp = oldApplication?.copy(
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
                    initialStatus = oldApplication.initialStatus,
                    updatedAt = Instant.now(),
                    appliedAt = Instant.now(),
                    interviewDateTime = if (selectedStatus == ApplicationStatus.INTERVIEW) currentState.interviewDateTime else null,
                    interviewLink = if (selectedStatus == ApplicationStatus.INTERVIEW) normalizedInterviewLink else null
                ) ?: return@launch

                if (oldStatus != selectedStatus) {
                    useCases.updateApplicationStatus(
                        appId = currentState.id,
                        from = oldStatus,
                        to = selectedStatus,
                        note = "Perubahan status lamaran"
                    )
                }

                useCases.updateApplication(updateApp)
                _state.value = currentState.copy(snackbarMessage = "Lamaran berhasil diperbarui")
                onSaved(updateApp.id)
            } else {
                val newApp = Application(
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
                    initialStatus = selectedStatus,
                    appliedAt = Instant.now(),
                    updatedAt = Instant.now(),
                    interviewDateTime = if (selectedStatus == ApplicationStatus.INTERVIEW) currentState.interviewDateTime else null,
                    interviewLink = if (selectedStatus == ApplicationStatus.INTERVIEW) normalizedInterviewLink else null
                )

                useCases.addApplication(newApp)
                _state.value = currentState.copy(snackbarMessage = "Lamaran berhasil ditambahkan")
                onSaved(newApp.id)
            }
        }
    }

    fun updateInterviewDate(date: LocalDate) {
        val currentTime = _state.value.interviewDateTime?.toLocalTime() ?: LocalTime.now()
        _state.update {
            it.copy(
                interviewDateTime = LocalDateTime.of(date, currentTime),
                interviewDateTimeError = null
            )
        }
    }

    fun updateInterviewTime(hour: Int, minute: Int) {
        _state.update {
            val date = it.interviewDateTime?.toLocalDate() ?: LocalDate.now()
            val newDateTime = LocalDateTime.of(date, LocalTime.of(hour, minute))
            it.copy(interviewDateTime = newDateTime)
        }
    }

    fun consumeSnackbar() {
        _state.value = state.value.copy(snackbarMessage = null)
    }

    private fun Job?.normalizeLinkWithDebounce(
        fieldName: String,
        value: String,
    ): Job {
        this?.cancel()
        return viewModelScope.launch {
            delay(DELAY_COMPLETED_TYPING)
            val currentLink = value.trim().lowercase()
            if (currentLink.isValidDomainWithoutScheme() && !currentLink.startsWith("https://")) {
                val normalized = currentLink.autoNormalizeLink()
                _state.update {
                    when (fieldName) {
                        "jobLink" -> it.copy(jobLink = normalized)
                        "interviewLink" -> it.copy(interviewLink = normalized)
                        else -> it
                    }
                }
            }
        }
    }

    private fun Job?.normalizeSalaryWithDebounce(value: String): Job {
        this?.cancel()
        return viewModelScope.launch {
            delay(DELAY_COMPLETED_TYPING)
            val cleanValue = value.filter { it.isDigit() }
            val formatted = cleanValue.toCurrencyFormat()
            _state.update { it.copy(salary = cleanValue, salaryFormatted = formatted) }
        }
    }

    companion object {
        private const val DELAY_COMPLETED_TYPING = 500L
    }
}