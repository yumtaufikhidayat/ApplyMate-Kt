package id.yumtaufikhidayat.applymate.presentation.application

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yumtaufikhidayat.applymate.data.local.entity.StatusHistoryEntity
import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.repository.StatusHistoryRepository
import id.yumtaufikhidayat.applymate.domain.usecase.application.ApplicationUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationDetailViewModel @Inject constructor(
    private val appUseCase: ApplicationUseCases,
    private val historyRepo: StatusHistoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ApplicationDetailState())
    val state: StateFlow<ApplicationDetailState> = _state.asStateFlow()

    fun loadApplication(id: Long) {
        if (id <= 0) return

        viewModelScope.launch {
            appUseCase.getApplicationById(id).collectLatest { app ->
                _state.update { it.copy(application = app) }
            }
        }

        viewModelScope.launch {
            historyRepo.getByApplication(id).collectLatest { list ->
                _state.update { it.copy(history = list) }
            }
        }
    }

    fun delete(onDeleted: () -> Unit) {
        val app = _state.value.application ?: return
        viewModelScope.launch {
            appUseCase.deleteApplication(app)
            onDeleted()
        }
    }
}

data class ApplicationDetailState(
    val application: Application? = null,
    val history: List<StatusHistoryEntity> = emptyList()
)