package id.yumtaufikhidayat.applymate.domain.usecase.application

import id.yumtaufikhidayat.applymate.domain.model.Application
import id.yumtaufikhidayat.applymate.domain.repository.ApplicationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetApplicationByIdUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(id: Long): Flow<Application?> = repository.getById(id)
}