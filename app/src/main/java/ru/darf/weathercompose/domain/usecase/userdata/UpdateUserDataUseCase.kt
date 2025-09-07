package ru.darf.weathercompose.domain.usecase.userdata

import ru.darf.weathercompose.domain.model.UserData
import ru.darf.weathercompose.domain.repository.UserDataRepository
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke(onUpdate: (UserData) -> UserData) {
        userDataRepository.updateUserData(onUpdate)
    }
}