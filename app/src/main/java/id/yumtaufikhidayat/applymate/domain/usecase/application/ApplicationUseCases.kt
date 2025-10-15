package id.yumtaufikhidayat.applymate.domain.usecase.application

data class ApplicationUseCases(
    val addApplication: AddApplicationUseCase,
    val getApplications: GetApplicationsUseCase,
    val getApplicationById: GetApplicationByIdUseCase,
    val updateApplication: UpdateApplicationUseCase,
    val updateApplicationStatus: UpdateApplicationStatusUseCase,
    val deleteApplication: DeleteApplicationUseCase
)