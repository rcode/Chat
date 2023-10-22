package dev.rcode.android.feature.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rcode.android.core.base.interactor.UseCase
import dev.rcode.android.domain.interactor.GetThisUser
import dev.rcode.android.domain.interactor.SaveUserDetails
import dev.rcode.android.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val saveUserDetails: SaveUserDetails,
    private val getThisUser: GetThisUser
) : ViewModel() {

    val uiState: StateFlow<RegistrationUIState> =
        MutableStateFlow(RegistrationUIState(true, null, ""))

    var userLiveData = MutableLiveData<User>()
    var error = MutableLiveData<Throwable>()

    /*fun saveUserData(name: String, email: String) {
        viewModelScope.launch {
            saveUserDetails(User(0, name = name, email = email))
                .onEach {
                    uiState.value.copy(loading = false, user = User(0, name = name, email = email), errorMessage = "")
                }
                .catch {
                    uiState.value.copy(loading = false, user = null, errorMessage = it.message?: "")
                }.launchIn(viewModelScope)


        }
    }*/


    fun saveUserData(name: String, email: String) {
        saveUserDetails(User(0, name = name, email = email)) {
            viewModelScope.launch {
                it.map {
                    it.fold({
                        getUserData()
                        userLiveData.postValue(User(0, name = name, email = email))
                    }, { e ->
                        error.postValue(e)
                    })
                }.catch { throwable ->
                    showError(throwable)
                }.collect()
            }
        }
    }

    private fun getUserData() {
        getThisUser(UseCase.None()) {
            viewModelScope.launch {
                it.map {
                    it.fold({ user ->
                        userLiveData.postValue(user)
                    }, { e ->
                        error.postValue(e)
                    })
                }.catch { throwable ->
                    showError(throwable)
                }.collect()
            }
        }
    }

    private fun showError(throwable: Throwable) {
        error.postValue(throwable)
        throwable.message?.let { error ->
            Log.e(this::class.java.simpleName, error)
        }
    }
}