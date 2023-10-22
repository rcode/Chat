package dev.rcode.android.feature.registration

import dev.rcode.android.domain.model.User

data class RegistrationUIState (
    var loading : Boolean = true,
    var user: User?,
    var errorMessage: String
)
