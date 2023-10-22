package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.User
import dev.rcode.android.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThisUser @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) : UseCase<Flow<Result<User>>, Any>() { //
    //suspend operator fun invoke(): Flow<Result<User>> = userPreferenceRepository.getThisUser()
    override suspend fun run(params: Any): Flow<Result<User>> {
        return userPreferenceRepository.getThisUser()
    }
}

