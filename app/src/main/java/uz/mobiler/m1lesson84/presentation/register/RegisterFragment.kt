package uz.mobiler.m1lesson84.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mobiler.m1lesson84.data.common.utils.SharedPrefs
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterRequest
import uz.mobiler.m1lesson84.data.register.remote.models.RegisterResponse
import uz.mobiler.m1lesson84.databinding.FragmentRegisterBinding
import uz.mobiler.m1lesson84.domain.register.models.RegisterData
import uz.mobiler.m1lesson84.presentation.common.utils.gone
import uz.mobiler.m1lesson84.presentation.common.utils.visible
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val TAG = "RegisterFragment"
    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.registerBtn.setOnClickListener {
            val name = binding.fullNameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()
            if(password == confirmPassword) {
                register(name, email, password)
            }else{
                Toast.makeText(requireContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        }
        observe()
        return binding.root
    }

    private fun register(name: String, email: String, password: String) {
        viewModel.register(RegisterRequest(email, name, password))
    }

    private fun observe() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleStateChange(state)
            }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: RegisterState) {
        when (state) {
            is RegisterState.ErrorRegister -> handleErrorRegister(state.rawResponse)
            is RegisterState.Init -> Unit
            is RegisterState.IsLoading -> handleLoading(state.isLoading)
            is RegisterState.ShowToast -> showToast(state.message)
            is RegisterState.SuccessRegister -> handleSuccessRegister(state.registerData)
        }
    }

    private fun handleErrorRegister(rawResponse: WrappedResponse<RegisterResponse>) {
        Toast.makeText(requireContext(), rawResponse.message, Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccessRegister(registerData: RegisterData) {
        sharedPrefs.saveToken(registerData.token ?: "")
        val bundle = bundleOf("email" to registerData.email, "password" to registerData.password)
        setFragmentResult("register", bundle)
        findNavController().popBackStack()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.progress.visible()
            binding.registerBtn.gone()
        } else {
            binding.progress.gone()
            binding.registerBtn.visible()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}