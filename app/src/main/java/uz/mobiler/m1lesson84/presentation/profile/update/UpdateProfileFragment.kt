package uz.mobiler.m1lesson84.presentation.profile.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mobiler.m1lesson84.data.common.utils.WrappedResponse
import uz.mobiler.m1lesson84.data.profile.remote.models.ProfileUserResponse
import uz.mobiler.m1lesson84.databinding.FragmentUpdateProfileBinding
import uz.mobiler.m1lesson84.domain.product.models.ProductData
import uz.mobiler.m1lesson84.domain.product.models.ProductUserData
import uz.mobiler.m1lesson84.domain.profile.models.RequestProfile

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private var _binding: FragmentUpdateProfileBinding? = null
    val binding get() = _binding!!

    private val viewModel: UpdateProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)

        val profile = arguments?.getSerializable("profile") as ProductUserData
        binding.etFullName.setText(profile.name)
        binding.etEmailUser.setText(profile.email)

        binding.btnUpdate.setOnClickListener {
            val name = binding.etFullName.text.toString()
            val email = binding.etEmailUser.text.toString()
            viewModel.update(RequestProfile(name, email))
        }
        observe()


        return binding.root
    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: UpdateState) {
        when (state) {
            is UpdateState.Error -> handleError(state.rawResponse)
            is UpdateState.Init -> Unit
            is UpdateState.IsLoading -> handleLoading(state.isLoading)
            is UpdateState.ShowToast -> showToast(state.message)
            is UpdateState.Success -> handleSuccess(state.profileData)
        }
    }

    private fun handleSuccess(success: ProductUserData) {
        Toast.makeText(requireContext(), success.toString(), Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putSerializable("success_updateUser", success)
        setFragmentResult("success_updateUser", bundle)
        findNavController().popBackStack()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun handleLoading(loading: Boolean) {

    }

    private fun handleError(rawResponse: WrappedResponse<ProfileUserResponse>) {
        Toast.makeText(requireContext(), rawResponse.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}