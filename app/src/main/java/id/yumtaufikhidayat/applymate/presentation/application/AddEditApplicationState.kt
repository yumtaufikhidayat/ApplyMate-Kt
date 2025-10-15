package id.yumtaufikhidayat.applymate.presentation.application

data class AddEditApplicationState(
    val id: Long = 0,
    val position: String = "",
    val company: String = "",
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
    val jobLinkError: String? = null
)