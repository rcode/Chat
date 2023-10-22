package dev.rcode.android.domain.interactor

import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.model.User
import dev.rcode.android.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserDetails @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) : UseCase<Flow<Result<Boolean>>, User>() {
    //suspend operator fun invoke(user: User): Flow<Result<Boolean>> = userPreferenceRepository.saveThisUser(user)
    override suspend fun run(user: User): Flow<Result<Boolean>> {
        return userPreferenceRepository.saveThisUser(user)
    }
}