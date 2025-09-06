package ru.darf.weathercompose.domain.usecase.userdata

import ru.darf.weathercompose.domain.repository.UserDataRepository
import javax.inject.Inject

class DeleteUserDataUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke() {
        userDataRepository.deleteUserData()
    }
}