package uz.mobiler.m1lesson84.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mobiler.m1lesson84.R
import uz.mobiler.m1lesson84.data.common.utils.SharedPrefs
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.login.remote.models.LoginRequest
import uz.mobiler.m1lesson84.data.login.remote.models.LoginResponse
import uz.mobiler.m1lesson84.databinding.FragmentLoginBinding
import uz.mobiler.m1lesson84.domain.login.models.LoginData
import uz.mobiler.m1lesson84.presentation.common.utils.gone
import uz.mobiler.m1lesson84.presentation.common.utils.visible
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setFragmentResultListener("register") { requestKey, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.emailEt.setText(email)
            binding.passwordEt.setText(password)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            login(email, password)
        }
        binding.registerTv.setOnClickListener { findNavController().navigate(R.id.registerFragment) }

        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: LoginEventState) {
        when (state) {
            is LoginEventState.ErrorLogin -> handleErrorLogin(state.rawResponse)
            is LoginEventState.Init -> Unit
            is LoginEventState.IsLoading -> handleLoading(state.isLoading)
            is LoginEventState.ShowToast -> showToast(state.message)
            is LoginEventState.SuccessLogin -> handleSuccessLogin(state.loginData)
        }
    }

    private fun handleSuccessLogin(loginData: LoginData) {
        sharedPrefs.saveToken(loginData.token ?: "")
        findNavController().navigate(R.id.homeFragment)
    }

    private fun handleErrorLogin(rawResponse: WrappedResponse<LoginResponse>) {
        Toast.makeText(requireContext(), rawResponse.message, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.progress.visible()
            binding.loginBtn.gone()
        } else {
            binding.progress.gone()
            binding.loginBtn.visible()
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(LoginRequest(email, password))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}