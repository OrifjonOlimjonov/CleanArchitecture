package uz.mobiler.m1lesson84.presentation.main.product.add

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.navigation.fragment.findNavController
import uz.mobiler.m1lesson84.data.product.remote.models.CreateProductRequest
import uz.mobiler.m1lesson84.databinding.FragmentAddProductBinding

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        observe()
        createProduct()
        return binding.root
    }

    private fun createProduct() {
        binding.btnCreate.setOnClickListener {
            val name = binding.etNameProduct.text.toString().trim()
            val price = binding.etPriceProduct.text.toString().trim()
            if (validate(name)) {
                viewModel.createProduct(CreateProductRequest(name, price.toInt()))
            }
        }
    }

    private fun validate(name: String): Boolean {
        return name.isNotEmpty()
    }

    private fun observe() {
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: CreateProductProcess) {
        when (state) {
            is CreateProductProcess.Init -> Unit
            is CreateProductProcess.IsLoading -> {
                handleLoading(state.isLoading)
            }
            is CreateProductProcess.ShowToast -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            is CreateProductProcess.SuccessCreate -> {
                setResultOkToPreviousFragment()
                findNavController().navigateUp()
            }
        }

    }

    private fun setResultOkToPreviousFragment() {
        val r = Bundle().apply {
            putBoolean("success_create", true)
        }
        setFragmentResult("success_create", r)
    }

    private fun handleLoading(loading: Boolean) {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}