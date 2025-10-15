package id.yumtaufikhidayat.applymate.presentation.application

data class AddEditApplicationState(
    val id: Long = 0,
    val position: String = "",
    val company: String = "",
    val city: String = "",
    val note: String = "",
    val status: String? = null,
    val isEditMode: Boolean = false,
    val snackbarMessage: String? = null
)