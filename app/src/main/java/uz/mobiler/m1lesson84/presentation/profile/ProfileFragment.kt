package uz.mobiler.m1lesson84.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.databinding.FragmentProfileBinding
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        observe()
        viewModel.getProfile()

        return binding.root
    }


    private fun observe() {
        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleStateChange(state)
            }.launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: ProfileProcess) {
        Toast.makeText(requireContext(), state.toString(), Toast.LENGTH_SHORT).show()
        when (state) {
            is ProfileProcess.Error -> handleError(state.rawResponse)
            is ProfileProcess.Init -> Unit
            is ProfileProcess.IsLoading -> handleLoading(state.isLoading)
            is ProfileProcess.ShowToast -> showToast(state.message)
            is ProfileProcess.Success -> handleSuccess(state.profileData)
        }
    }

    private fun handleSuccess(profileData: ProductUserData) {
        binding.etEmailUser.text = profileData.email
        binding.etNameUser.text = profileData.name
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun handleLoading(loading: Boolean) {
        //  Toast.makeText(requireContext(), "salom $loading", Toast.LENGTH_SHORT).show()
    }

    private fun handleError(rawResponse: WrappedResponse<ProfileUserResponse>) {
        Toast.makeText(requireContext(), rawResponse.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}