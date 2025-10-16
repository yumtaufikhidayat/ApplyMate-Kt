package id.yumtaufikhidayat.applymate.presentation.application

import java.time.LocalDate

data class AddEditApplicationState(
    val id: Long = 0,
    val position: String = "",
    val company: String = "",
    val companyAbout: String = "",
    val city: String = "",
    val jobLink: String = "",
    val jobDesc: String = "",
    val jobRequirement: String = "",
    val salary: String = "",
    val note: String = "",
    val status: String? = null,
    val isEditMode: Boolean = false,
    val snackbarMessage: String? = null,

    // State Error
    val positionError: String? = null,
    val companyError: String? = null,
    val jobLinkError: String? = null,

    // Interview State
    val interviewDate: LocalDate? = null,
    val interviewLink: String = "",
    val interviewDateError: String? = null,
    val interviewLinkError: String? = null,
)