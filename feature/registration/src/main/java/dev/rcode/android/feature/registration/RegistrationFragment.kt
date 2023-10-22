package dev.rcode.android.feature.registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.rcode.android.core.ui.navigation.ChatNavigator
import dev.rcode.android.feature.registration.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels()

    @Inject
    lateinit var  navigator: ChatNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        binding.buttonRegister.setOnClickListener {
            viewModel.saveUserData(binding.edittextName.text.toString(), binding.edittextEmail.text.toString())
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when {
                        it.loading -> {}
                        it.user != null -> {
                            Log.d("Registration", "User created successfully")
                        }
                        it.errorMessage != "" -> {}
                    }
                }
            }
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            if(it != null) {
                findNavController().popBackStack()
            }
        }
    }
}