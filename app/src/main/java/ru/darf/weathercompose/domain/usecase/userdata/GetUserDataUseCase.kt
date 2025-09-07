package ru.darf.weathercompose.domain.usecase.userdata

import kotlinx.coroutines.flow.Flow
import ru.darf.weathercompose.domain.model.UserData
import ru.darf.weathercompose.domain.repository.UserDataRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
) {
    operator fun invoke(): Flow<UserData> {
        return userDataRepository.getUserData()
    }
}
