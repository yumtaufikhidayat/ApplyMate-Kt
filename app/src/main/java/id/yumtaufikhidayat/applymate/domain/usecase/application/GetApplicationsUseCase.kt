package id.yumtaufikhidayat.applymate.domain.usecase.application

import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetApplicationsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(): Flow<List<Application>> = repository.getAll()
}